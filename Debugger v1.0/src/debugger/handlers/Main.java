package debugger.handlers;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import debugger.architecture.Figuras;
import debugger.interfaces.VentanaO;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class Main extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		JTextField clase = new JTextField(5);
		JTextField proyecto = new JTextField(5);

        JPanel openDebugger = new JPanel();
        openDebugger.add(new JLabel("Clase:"));
        openDebugger.add(clase);
        openDebugger.add(Box.createHorizontalStrut(15));
        openDebugger.add(new JLabel("Proyecto:"));
        openDebugger.add(proyecto);

        int result = JOptionPane.showConfirmDialog(null, openDebugger,
            "Ingrese el proyecto y la clase a debuggear:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
        	if (false)
        	    System.err.println("Usage: runTrace <program>");
        	//else
        	    //new SimpleTrace();
        }
		return null;
	}

	public static void main (String[] args){

		JTextField clase = new JTextField(5);
		JTextField proyecto = new JTextField(5);

        JPanel openDebugger = new JPanel();
        openDebugger.add(new JLabel("Clase:"));
        openDebugger.add(clase);
        openDebugger.add(Box.createHorizontalStrut(15));
        openDebugger.add(new JLabel("Proyecto:"));
        openDebugger.add(proyecto);

        int result = JOptionPane.showConfirmDialog(null, openDebugger,
            "Ingrese el proyecto y la clase a debuggear:", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
        	new VentanaO("Clase2", "Debugger");
        }

	}
}
