package debugger.architecture;

// SimpleTrace.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, March 2009

/* This program traces the execution of a target program using the
   Java Debug Interface (JDI) API, which interfaces to
   the Java Platform Debugger Architecture (JPDA).

   It is based on the Trace.java example included in the demo/jpda/examples.jar
   file in the JDK. The other two JPDA examples are the jdb
   Java Debugger and a prototype GUI debugger.

   Compilation of this code is carried out with compile.bat

   Execution of this code is carried out with runTrace.bat

   Usage:
      runTrace Comparison     // traces Comparison.java
      runTrace TestStack      // traces TestStack and Stack class


   For more information on JPDA and JDI, see:
      JPDA docs:         Java/docs/technotes/guides/jpda/index.html
      JDI specification (API pages):   Java/docs/jdk/api/jpda/jdi/index.html

      JPDA forum: http://forums.sun.com/forum.jspa?forumID=543
 */


import com.sun.jdi.*;
import com.sun.jdi.connect.*;

import java.util.*;
import java.io.*;
import java.net.*;


public class SimpleTrace
{

  public SimpleTrace(String[] args)
  {
    VirtualMachine vm = launchConnect(args);
    monitorJVM(vm);
  }  // end of SimpleTrace()


  private VirtualMachine launchConnect(String[] args)
  // Set up a launching connection to the JVM
  {
    VirtualMachine vm = null;
    LaunchingConnector conn = getCommandLineConnector();
    Map<String,Connector.Argument> connArgs = conn.defaultArguments();

    Connector.Argument main = connArgs.get("main");
    Connector.Argument option = connArgs.get("options");

    main.setValue("debugger.architecture.Clase2");
    option.setValue("-cp C:\\Users\\este0\\eclipse-workspace\\Debugger\\bin");

    try {
      vm = conn.launch(connArgs);
      // launch the JVM and connect to it
    }
    catch (IOException e) {
      throw new Error("Unable to launch JVM: " + e);
    }
    catch (IllegalConnectorArgumentsException e) {
      throw new Error("Internal error: " + e);
    }
    catch (VMStartException e) {
      throw new Error("JVM failed to start: " + e.getMessage());
    }

    return vm;

  }  // end of launchConnect()



  private LaunchingConnector getCommandLineConnector()
  // find a command line launch connector
  {
    List<Connector> conns = Bootstrap.virtualMachineManager().allConnectors();

    for (Connector conn: conns) {
      if (conn.name().equals("com.sun.jdi.CommandLineLaunch"))
        return (LaunchingConnector) conn;
    }
    throw new Error("No launching connector found");
  } // end of getCommandLineConnector()



  private Map<String,Connector.Argument> setMainArgs(
                                 LaunchingConnector conn, String[] args)
  // make the tracer's input arguments the program's main() arguments
  {
    // get the connector argument for the program's main() method
    Map<String,Connector.Argument> connArgs = conn.defaultArguments();
    Connector.Argument mArgs = (Connector.Argument) connArgs.get("main");
    if (mArgs == null)
      throw new Error("Bad launching connector");

    // concatenate all the tracer's input arguments into a single string
    StringBuffer sb = new StringBuffer();
    for (int i=0; i < args.length; i++)
      sb.append(args[i] + " ");

    mArgs.setValue(sb.toString());   // assign input args to application's main()
    return connArgs;
  }  // end of setMainArgs()



  private void monitorJVM(VirtualMachine vm)
  // monitor the JVM running the application
  {
    // start JDI event handler which displays trace info
    JDIEventMonitor watcher = new JDIEventMonitor(vm);
    watcher.start();

    /* redirect VM's output and error streams
       to the system output and error streams */
    Process process = vm.process();
    Thread errRedirect = new StreamRedirecter("error reader",
                                   process.getErrorStream(), System.err);
    Thread outRedirect = new StreamRedirecter("output reader",
                                   process.getInputStream(), System.out);
    errRedirect.start();
    outRedirect.start();

    vm.resume();           // start the application

    try {
      watcher.join();      // Wait. Shutdown begins when the JDI watcher terminates
      errRedirect.join();  // make sure all the stream outputs have been forwarded before we exit
      outRedirect.join();
    }
    catch (InterruptedException e) { }
  }  // end of monitorJVM()



  // ------------------------------------------------


  public static void main(String[] args)
  {
    if (args.length != 0)
      System.err.println("Usage: runTrace <program>");
    else
      new SimpleTrace(args);
  }

}  // end of SimpleTrace class
