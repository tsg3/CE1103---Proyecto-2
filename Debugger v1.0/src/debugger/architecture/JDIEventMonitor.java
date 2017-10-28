package debugger.architecture;

// JDIEventMonitor.java

/* Robert Field and Minoru Terada, September 2005
   Iman_S, June 2008  */
// Andrew Davison, ad@fivedots.coe.psu.ac.th, March 2009

/* Monitor incoming JDI events for a program running in the JVM
   and print out trace/debugging information.

   This is a simplified version of EventThread.java from the Trace.java
   example in the demo/jpda/examples.jar file in the JDK.

   The main addition is the use of the ShowCodes and ShowLines classes
   to list the line being currently executed.
*/


import com.sun.jdi.*;
import com.sun.jdi.request.*;
import com.sun.jdi.event.*;

import java.util.*;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class JDIEventMonitor extends Thread
{
  // exclude events generated for these classes
  private final String[] excludes = { "java.*", "javax.*", "sun.*", "com.sun.*"};

  private final VirtualMachine vm;   // the JVM
  private boolean connected = true;  // connected to VM?
  private boolean vmDied;            // has VM death occurred?

  private ShowCode showCode;


  public JDIEventMonitor(VirtualMachine jvm)
  {
    super("JDIEventMonitor");
    vm = jvm;
    showCode = new ShowCode();

    setEventRequests();
  }  // end of JDIEventMonitor()



  private void setEventRequests()
  /* Create and enable the event requests for the events
     we want to monitor in the running program. */
  {
    EventRequestManager mgr = vm.eventRequestManager();

    MethodEntryRequest menr = mgr.createMethodEntryRequest(); // report method entries
    for (int i = 0; i < excludes.length; ++i)
      menr.addClassExclusionFilter(excludes[i]);
    menr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
    menr.enable();

    MethodExitRequest mexr = mgr.createMethodExitRequest();   // report method exits
    for (int i = 0; i < excludes.length; ++i)
      mexr.addClassExclusionFilter(excludes[i]);
    mexr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
    mexr.enable();

    ClassPrepareRequest cpr = mgr.createClassPrepareRequest(); // report class loads
    for (int i = 0; i < excludes.length; ++i)
      cpr.addClassExclusionFilter(excludes[i]);
    // cpr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
    cpr.enable();

    ClassUnloadRequest cur = mgr.createClassUnloadRequest();  // report class unloads
    for (int i = 0; i < excludes.length; ++i)
      cur.addClassExclusionFilter(excludes[i]);
    // cur.setSuspendPolicy(EventRequest.SUSPEND_ALL);
    cur.enable();

    ThreadStartRequest tsr = mgr.createThreadStartRequest();  // report thread starts
    tsr.enable();

    ThreadDeathRequest tdr = mgr.createThreadDeathRequest();  // report thread deaths
    tdr.enable();

  }  // end of setEventRequests()



  public void run()
  // process JDI events as they arrive on the event queue
  {
    EventQueue queue = vm.eventQueue();
    while (connected) {
      try {
        EventSet eventSet = queue.remove();
        for(Event event : eventSet)
          handleEvent(event);

        eventSet.resume();
      }
      catch (InterruptedException e) { }  // Ignore
      catch (VMDisconnectedException discExc) {
        handleDisconnectedException();
        break;
      }
    }
  }  // end of run()



  private void handleEvent(Event event)
  // process a JDI event
  {
    // method events
    if (event instanceof MethodEntryEvent)
      methodEntryEvent((MethodEntryEvent) event);
    else if (event instanceof MethodExitEvent)
      methodExitEvent((MethodExitEvent) event);

    // class events
    else if (event instanceof ClassPrepareEvent)
      classPrepareEvent((ClassPrepareEvent) event);
    else if (event instanceof ClassUnloadEvent)
      classUnloadEvent((ClassUnloadEvent) event);

    // thread events
    else if (event instanceof ThreadStartEvent)
      threadStartEvent((ThreadStartEvent) event);
    else if (event instanceof ThreadDeathEvent)
      threadDeathEvent((ThreadDeathEvent) event);

    // step event -- a line of code is about to be executed
    else if (event instanceof StepEvent)
      stepEvent((StepEvent) event);

    // modified field event  -- a field is about to be changed
    else if (event instanceof ModificationWatchpointEvent)
      fieldWatchEvent((ModificationWatchpointEvent) event);

    // VM events
    else if (event instanceof VMStartEvent)
      vmStartEvent((VMStartEvent) event);
    else if (event instanceof VMDeathEvent)
      vmDeathEvent((VMDeathEvent) event);
    else if (event instanceof VMDisconnectEvent)
      vmDisconnectEvent((VMDisconnectEvent) event);

    else
      throw new Error("Unexpected event type");
  }  // end of handleEvent()


  private synchronized void handleDisconnectedException()
  /* A VMDisconnectedException has occurred while dealing with
     another event. Flush the event queue, dealing only
     with exit events (VMDeath, VMDisconnect) so that things
     terminate correctly. */
  {
    EventQueue queue = vm.eventQueue();
    while (connected) {
      try {
        EventSet eventSet = queue.remove();
        for(Event event : eventSet) {
          if (event instanceof VMDeathEvent)
            vmDeathEvent((VMDeathEvent) event);
          else if (event instanceof VMDisconnectEvent)
            vmDisconnectEvent((VMDisconnectEvent) event);
        }
        eventSet.resume(); // resume the VM
      }
      catch (InterruptedException e) { }  // ignore
    }
  }  // end of handleDisconnectedException()



  // -------------------- method event handling  ---------------



  private void methodEntryEvent(MethodEntryEvent event)
  // entered a method but no code executed yet
  {
    Method meth = event.method();
    String className = meth.declaringType().name();

    System.out.println();
    if (meth.isConstructor())
      System.out.println("entered " + className + " constructor");
    else
      System.out.println("entered " + className +  "." + meth.name() +"()");
  }  // end of methodEntryEvent()



  private void methodExitEvent(MethodExitEvent event)
  // all code in the method has been executed, and we are about to return
  {
    Method meth = event.method();
    String className = meth.declaringType().name();

    if (meth.isConstructor())
      System.out.println("exiting " + className + " constructor");
    else
      System.out.println("exiting " + className + "." + meth.name() + "()" );
    System.out.println();

  }  // end of methodExitEvent()


  // -------------------- class event handling  ---------------


  private void classPrepareEvent(ClassPrepareEvent event)
  // a new class has been loaded
  {
    ReferenceType ref = event.referenceType();

    List<Field> fields = ref.fields();
    List<Method> methods = ref.methods();

    String fnm;
    try {
      fnm = ref.sourceName();  // get filename of the class
      showCode.add(fnm);
    }
    catch (AbsentInformationException e)
    {  fnm = "??"; }

    System.out.println("loaded class: " + ref.name() + " from " + fnm +
                    " - fields=" + fields.size() + ", methods=" + methods.size() );

    System.out.println("  method names: ");
    for(Method m : methods)
      System.out.println("    | " + m.name() +   "()" );

    setFieldsWatch(fields);
  }  // end of classPrepareEvent()



  private void setFieldsWatch(List<Field> fields)
  // Set modification watchpoints on each of the loaded class's fields
  {
    EventRequestManager mgr = vm.eventRequestManager();

    for (Field field : fields) {
      ModificationWatchpointRequest req =
                mgr.createModificationWatchpointRequest(field);
      for (int i = 0; i < excludes.length; i++)
        req.addClassExclusionFilter(excludes[i]);
      req.setSuspendPolicy(EventRequest.SUSPEND_NONE);
      req.enable();
    }
  }  // end of setFieldsWatch()



  private void classUnloadEvent(ClassUnloadEvent event)
  // a class has been unloaded
  {
    if (!vmDied)
      System.out.println("unloaded class: " + event.className());
  }



  // ---------------------- modified field event handling ----------------------------------


  private void fieldWatchEvent(ModificationWatchpointEvent event)
  {
     Field f = event.field();
     Value value = event.valueToBe();   // value that _will_ be assigned
     System.out.println("    > " + f.name() + " = " + value);
  }  // end of fieldWatchEvent()



  // -------------------- thread event handling  ---------------

  private void threadStartEvent(ThreadStartEvent event)
  // a new thread has started running -- switch on single stepping
  {
    ThreadReference thr = event.thread();

    if (thr.name().equals("Signal Dispatcher") ||
        thr.name().equals("DestroyJavaVM") ||
        thr.name().startsWith("AWT-") )     // AWT threads
      return;

    if (thr.threadGroup().name().equals("system"))   // ignore system threads
      return;

    System.out.println(thr.name() + " thread started");

    setStepping(thr);
  } // end of threadStartEvent()



  private void setStepping(ThreadReference thr)
  // start single stepping through the new thread
  {
    EventRequestManager mgr = vm.eventRequestManager();

    StepRequest sr = mgr.createStepRequest(thr, StepRequest.STEP_LINE,
                                                StepRequest.STEP_INTO);
    sr.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);

    for (int i = 0; i < excludes.length; ++i)
      sr.addClassExclusionFilter(excludes[i]);
    sr.enable();
  }  // end of setStepping()




  private void threadDeathEvent(ThreadDeathEvent event)
  // the thread is about to terminate
  {
    ThreadReference thr = event.thread();
    if (thr.name().equals("DestroyJavaVM") ||
        thr.name().startsWith("AWT-") )
      return;

    if (thr.threadGroup().name().equals("system"))   // ignore system threads
      return;

    System.out.println(thr.name() + " thread about to die");
  }  // end of threadDeathEvent()


  // -------------------- step event handling  ---------------


  private void stepEvent(StepEvent event)
  /* Print the line that's about to be executed.
     If this is the first line in a method then also print
     the local variables and the object's fields.
  */
  { Location loc = event.location();

    try {   // print the line
      String fnm = loc.sourceName();  // get filename of code
      System.out.println(fnm + ": " + showCode.show(fnm, loc.lineNumber()) );

    }
    catch (AbsentInformationException e) {}

    if (loc.codeIndex() == 0)   // at the start of a method
      printInitialState( event.thread() );
  }  // end of stepEvent()


  private void printInitialState(ThreadReference thr)
  /* called to print the locals this object's fields when a method
     is first called */
  {
    // get top-most current stack frame
    StackFrame currFrame = null;
    try {
      currFrame = thr.frame(0);
    }
    catch (Exception e) {
      return;
    }

    printLocals(currFrame);

    // print fields info for the 'this' object
    ObjectReference objRef = currFrame.thisObject();   // get 'this' object
    if (objRef != null) {
      System.out.println("  object: " + objRef.toString());
      printFields(objRef);
    }
  }  // end of printInitialState()


  private void printLocals(StackFrame currFrame)
  /* Print local variables that are currently visible in the method
     being executed. Since we only call printLocals() when a method
     is first entered, the only visible locals will be the
     parameters of the method. */
  {
    List<LocalVariable> locals = null;
    try {
      locals = currFrame.visibleVariables();
    }
    catch (Exception e) {
      return;
    }

    if (locals.size() == 0)   // no local vars in the list
      return;

    System.out.println("  locals: ");
    for(LocalVariable l : locals)
      System.out.println("    | " + l.name() +
                               " = " + currFrame.getValue(l) );
  }  // end of printLocals()




  private void printFields(ObjectReference objRef)
  // print the fields in the object
  {
    ReferenceType ref = objRef.referenceType();  // get type (class) of object
    List<Field> fields = null;
    try {
      fields = ref.fields();      // only this object's fields
            // could use allFields() to include inherited fields
    }
    catch (ClassNotPreparedException e) {
      return;
    }

    System.out.println("  fields: ");
    for(Field f : fields)
      System.out.println("    | " + f.name() + " = " + objRef.getValue(f) );
  }  // end of printFields()


  // ---------------------- VM event handling ----------------------------------

  private void vmStartEvent(VMStartEvent event)
  /* Notification of initialization of a target VM. This event is received
     before the main thread is started and before any application code has
     been executed. */
  { vmDied = false;
    System.out.println("-- VM Started --");
  }


  private void vmDeathEvent(VMDeathEvent event)
  // Notification of VM termination
  { vmDied = true;
    System.out.println("-- The application has exited --");
  }


  private void vmDisconnectEvent(VMDisconnectEvent event)
  /* Notification of disconnection from the VM, either through normal termination
     or because of an exception/error. */
  { connected = false;
    if (!vmDied)
      System.out.println("-- The application has been disconnected --");
  }

}  // end of JDIEventMonitor class
