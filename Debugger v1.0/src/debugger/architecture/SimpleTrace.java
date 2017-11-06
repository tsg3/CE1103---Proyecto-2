package debugger.architecture;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;

import java.util.*;

import javax.swing.JButton;

import java.io.*;
import java.net.*;


public class SimpleTrace
{
	public static JDIEventMonitor watcher;
  public SimpleTrace()
  {
    VirtualMachine vm = launchConnect();
    monitorJVM(vm);
  }


  private VirtualMachine launchConnect()
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

  private void monitorJVM(VirtualMachine vm)
  {
    JDIEventMonitor watcher = new JDIEventMonitor(vm);
    this.watcher=watcher;
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

  public static void main(String[] args)
  {
    if (args.length != 0)
      System.err.println("Usage: runTrace <program>");
    else
      new SimpleTrace();
  }

}
