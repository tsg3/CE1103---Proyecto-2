import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Figuras extends JPanel{

	public static String[] texto;

	public void create(Graphics g, int altura, String linea){
		g.drawString(linea,200-10*(linea.length()/4)-20 + (this.getWidth()-2*(200-10*(linea.length()/4)-20) - g.getFontMetrics(g.getFont()).stringWidth(linea)) / 2, altura - 20 + ((30 - g.getFontMetrics(g.getFont()).getHeight()) / 2) + g.getFontMetrics(g.getFont()).getAscent());
		g.drawOval(200-10*(linea.length()/4)-20,altura-20, this.getWidth()-2*(200-10*(linea.length()/4)-20),30);
	}

	public void paint(Graphics g){

		int altura = 30;
		int i = 0;
		g.setFont(new Font("TimesRoman",Font.PLAIN,20));
		g.setColor(Color.BLUE);

		/*FontMetrics metrics = g.getFontMetrics(g.getFont());
		Rectangle rect;
		String text = texto[i];
		int x = 100-10*(text.length()/4)-20 + (this.getWidth()-2*(100-10*(text.length()/4)-20) - metrics.stringWidth(text)) / 2;
		int y = 10 + ((30 - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, x, y);*/


		while (i<texto.length){
			create(g,altura,texto[i]);
			if (!(i==texto.length-1)){
				//linea que une grafico
				g.drawLine(250, altura+10, 250, altura+30);
				//linea izquierda que une ciclos
				g.drawLine(200-10*(texto[i].length()/4)-20, altura-5, 200-10*(texto[i].length()/4)-20-30, altura-5);
				g.drawLine(200-10*(texto[i].length()/4)-20-30, altura-5, 200-10*(texto[i].length()/4)-20-30, altura+80);
				g.drawLine(200-10*(texto[i].length()/4)-20-30, altura+80, 250, altura+80);
				g.drawLine(250, altura+80, 250, altura+60);
				//linea derecha que une ciclos
				g.drawLine(200-10*(texto[i+1].length()/4)-20+this.getWidth()-2*(200-10*(texto[i+1].length()/4)-20), altura+45, 200-10*(texto[i+1].length()/4)-20+this.getWidth()-2*(200-10*(texto[i+1].length()/4)-20)+30, altura+45);
				g.drawLine(200-10*(texto[i+1].length()/4)-20+this.getWidth()-2*(200-10*(texto[i+1].length()/4)-20)+30, altura+45, 200-10*(texto[i+1].length()/4)-20+this.getWidth()-2*(200-10*(texto[i+1].length()/4)-20)+30, altura+90);
				g.drawLine(200-10*(texto[i+1].length()/4)-20+this.getWidth()-2*(200-10*(texto[i+1].length()/4)-20)+30, altura+90, 250, altura+90);
				g.drawLine(250, altura+90, 250, altura+110);
			}
			altura+=50;
			i++;
		}

		/*String linea1 = texto[i];
		int alineacion1 = 100-10*(linea1.length()/4);
		g.drawString(linea1,100,altura);
		g.drawOval(100-10*(texto[0].length()/4)-20,10,this.getWidth()-2*(100-10*(texto[0].length()/4)-20),30);
		altura+=50;
		i++;
		String linea2 = texto[i];
		int alineacion2 = 100-10*(linea2.length()/4);
		g.drawString(linea2,alineacion2,altura);
		g.drawRect(alineacion2-20,60,linea2.length()*10,30);
		altura+=50;
		i++;
		String linea3 = texto[i];
		int alineacion3 = 100-10*(linea3.length()/4);
		g.drawString(linea3,100,altura);
		g.drawOval(alineacion3-20,110,linea3.length()*20,30);*/

	}

	public static void leer(int n){
		Scanner s = new Scanner(System.in);
		String[] array=new String[n];
		int i = 0;
		while(i<n){
			array[i]=s.nextLine();
			i++;
		}
		s.close();
		texto = array;
	}
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		leer(s.nextInt());
		s.close();
		JFrame frame = new JFrame();
		frame.getContentPane().add(new Figuras());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setVisible(true);
	}
}