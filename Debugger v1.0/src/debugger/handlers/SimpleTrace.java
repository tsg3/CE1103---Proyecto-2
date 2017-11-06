package debugger.handlers;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;

import debugger.architecture.Animacion;
import debugger.architecture.Diagrama;

import java.util.*;

import javax.swing.JButton;

import java.io.*;
import java.net.*;


public class SimpleTrace
{
	public static JDIEventMonitor watcher;
  public SimpleTrace(String clase, String proyecto ,Diagrama diagram,Animacion animacion)

  {
    VirtualMachine vm = launchConnect(clase, proyecto);
    monitorJVM(vm,diagram,animacion);
  }


  private VirtualMachine launchConnect(String clase, String proyecto)
  {
    VirtualMachine vm = null;
    LaunchingConnector conn = getCommandLineConnector();
    Map<String,Connector.Argument> connArgs = conn.defaultArguments();

    Connector.Argument main = connArgs.get("main");
    Connector.Argument option = connArgs.get("options");

    main.setValue("debugger.architecture." + clase);
    option.setValue("-cp C:\\Users\\este0\\eclipse-workspace\\"+proyecto+"\\bin");

    try {
      vm = conn.launch(connArgs);
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

  }



  private LaunchingConnector getCommandLineConnector()
  {
    List<Connector> conns = Bootstrap.virtualMachineManager().allConnectors();

    for (Connector conn: conns) {
      if (conn.name().equals("com.sun.jdi.CommandLineLaunch"))
        return (LaunchingConnector) conn;
    }
    throw new Error("No launching connector found");
  }

  private void monitorJVM(VirtualMachine vm,Diagrama diagram,Animacion animacion)
  {
    JDIEventMonitor watcher = new JDIEventMonitor(vm);
    this.watcher=watcher;
    watcher.diagram=diagram;
    watcher.animacion=animacion;
    watcher.start();
    Process process = vm.process();
    Thread errRedirect = new StreamRedirecter("error reader",process.getErrorStream(), System.err);
    Thread outRedirect = new StreamRedirecter("output reader",process.getInputStream(), System.out);
    errRedirect.start();
    outRedirect.start();
    try {
      watcher.join();
      errRedirect.join();
      outRedirect.join();
    }
    catch (InterruptedException e) { }
  }

  /*public static void main(String[] args)
  {
    if (args.length != 0)
      System.err.println("Usage: runTrace <program>");
    else
      new SimpleTrace("Clase2","Debugger");
  }*/

}
