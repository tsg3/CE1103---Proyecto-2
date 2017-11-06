package debugger.architecture;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Ventana {

	public static void main (String[] args) {
		SingleGraphics g = new SingleGraphics("Clase");
		g.vars.add("x",0);
		g.vars.add("y",1);

		JFrame frame = new JFrame("Diagrama");
		frame.setLayout(new GridLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1300,700);
		frame.setVisible(true);
		frame.add(g);

		g.vars.add("y",2);

		frame.add(g);
	}
}
