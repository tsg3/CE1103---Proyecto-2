package debugger.interfaces;

import javax.swing.*;

import debugger.architecture.Animacion;
import debugger.architecture.Codigo;
import debugger.architecture.Diagrama;
import debugger.architecture.SingleGraphics;
import debugger.handlers.JavaToTxt;
import debugger.handlers.SimpleTrace;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VentanaO implements ActionListener {

	static SimpleTrace s;
	public JButton over;
	public JButton into;
	public static Animacion animacion;
	public static Diagrama diagram = new Diagrama();
	static JavaToTxt txt = new JavaToTxt();
	static Codigo code = txt.addCode("Clase2");
	static String[] codigo = new String[code.largo];
	JFrame frame;
	SingleGraphics g;

	public VentanaO(String clase, String proyecto){
		source();
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
					createGui();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		animacion = new Animacion(diagram);
        s = new SimpleTrace(clase,proyecto,diagram,animacion);
	}

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaO().createGui();
            }
        });
        s = new SimpleTrace();
    }*/

    public void createGui() throws IOException {
        frame = new JFrame("Debugger");
        frame.setSize(1300, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel southBorderLayoutPanel = new JPanel();

        JButton button1 = new JButton("Step Into");
        button1.addActionListener(this);
        into=button1;

        JButton button2 = new JButton("Step Over");
        button2.addActionListener(this);
        over=button2;

        southBorderLayoutPanel.add(button1);
        southBorderLayoutPanel.add(button2);

        frame.add(southBorderLayoutPanel, BorderLayout.PAGE_END);

        g = g.getInstance("Clase2",diagram,codigo);


        frame.add(g);
    }

    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == into){
    		s.watcher.value=true;
    		frame.add(g);
    	}
    	else
    		s.watcher.into=false;
    }
    public static void source (){
		int k = 0;
		while (k<code.largo){
			codigo[k]=code.linea(k);
			k++;
		}
		k=0;
		int renglon=1;
		for (int j=0;j<codigo.length;j++){
			if ((codigo[j].contains("if("))||(codigo[j].contains("if ("))){
				diagram.add(codigo[j].replace("{",""));
			}
			else if ((codigo[j].contains("for("))||(codigo[j].contains("for ("))){
				diagram.add(codigo[j].replace("{",""));
			}
			else if ((codigo[j].contains("while("))||(codigo[j].contains("while ("))){
				diagram.add(codigo[j].replace("{",""));
			}
			else {
				if (((declaracion(codigo[j])) || (codigo[j].contains("new")))&&!((codigo[j].contains("import"))||((codigo[j].contains("package"))))){
					diagram.add(codigo[j].replace(";",""));
				}
				else if ((codigo[j].contains("="))||(codigo[j].contains("++"))||(codigo[j].contains("--"))){
					diagram.add(codigo[j].replace(";",""));
				}
				else {
				if ((codigo[j].contains("("))&&(codigo[j].contains(")"))&&(codigo[j].contains(";"))){
					diagram.add(codigo[j].replace(";",""));
				}
				}
			}
			if (codigo[j].contains("}"))
				diagram.ciclos++;
		}
    }
	public static boolean declaracion(String linea){
		String[] lineas = linea.split(" ");
		if (lineas.length == 2)
			return true;
		return false;
	}
}