package debugger.architecture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JFrame;

public class Figuras extends JPanel{

	public static String[] texto;
	public static String[] ciclos = {"while","for"};
	public static boolean ciclo = false;
	public static boolean entraCiclo = false;
	public static boolean entraJerarquia = false;
	public static boolean jerarquia = false;
	public String clase;

	public Figuras(String clase){
		this.clase=clase;
	}

	public void create(Graphics g, int altura, String linea){
		g.drawString(linea,400+415-10*(linea.length()/4)-20 + (this.getWidth()-2*(415-10*(linea.length()/4)-20)-400 - g.getFontMetrics(g.getFont()).stringWidth(linea)) / 2, altura - 20 + ((30 - g.getFontMetrics(g.getFont()).getHeight()) / 2) + g.getFontMetrics(g.getFont()).getAscent());
		g.drawOval(400+380-10*(linea.length()/4)-20,altura-20, this.getWidth()-2*(380-10*(linea.length()/4)-20)-400,30);
	}

	public static boolean existePalabra(String linea, String palabra){
		String[] palabras = linea.split("\\W+");
		for (String palabra2 : palabras){
			if (palabra2.equals(palabra)){
				return true;
			}
		}
		return false;
	}

	public static boolean existePalabra(String linea, String[] palabra){
		String[] palabras = linea.split("\\W+");
		for (String palabra2 : palabras){
			int i=0;
			while (i<palabra.length){
				if (palabra2.equals(palabra[i]))
					return true;
				i++;
				}
			i=0;
			}
		return false;
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
				g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+10, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+30);
				}
				if (ciclo == true){
					altura+=30;
					ciclo=false;
				}
				if (jerarquia == true){
					altura+=20;
					jerarquia=false;
				}
				if (existePalabra(texto[i],ciclos)&&(ciclo==false)){
					//linea izquierda que une ciclos
						g.drawLine(400+380-10*(texto[i].length()/4)-20, altura-5, 400+380-10*(texto[i].length()/4)-20-30, altura-5);
						g.drawLine(400+380-10*(texto[i].length()/4)-20-30, altura-5, 400+380-10*(texto[i].length()/4)-20-30, altura+80);
						g.drawLine(400+380-10*(texto[i].length()/4)-20-30, altura+80, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+80);
						g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+80, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+60);
						//linea derecha que une ciclos
						g.drawLine(400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400), altura+45, 400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)+30, altura+45);
						g.drawLine(400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)+30, altura+45, 400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)+30, altura+90);
						g.drawLine(400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)+30, altura+90, 400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)/2, altura+90);
						g.drawLine(400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)/2, altura+90, 400+380-10*(texto[i+1].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i+1].length()/4)-20)-400)/2, altura+110);
						entraCiclo=true;
				}
				if (existePalabra(texto[i],"if")){
					//linea derecha que define jerarquia
					g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400), altura-5, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)+30, altura-5);
					g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)+30, altura-5, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)+30, altura+80);
					g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)+30, altura+80, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+80);
					g.drawLine(400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+80, 400+380-10*(texto[i].length()/4)-20+(this.getWidth()-2*(380-10*(texto[i].length()/4)-20)-400)/2, altura+100);
					entraJerarquia=true;
				}
				if ((ciclo==false)&&(jerarquia==false))
					altura+=50;
				if (entraCiclo==true){
					entraCiclo=false;
					ciclo=true;
				}
				if(entraJerarquia == true){
					entraJerarquia=false;
					jerarquia=true;
				}
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

		g.setFont(new Font("Lucida",Font.PLAIN,14));
		g.setColor(Color.BLACK);

		JavaToTxt txt = new JavaToTxt();
		Codigo code = txt.addCode(clase);
		int k = 0;
		String[] codigo = new String[code.largo];
		while (k<code.largo){
			codigo[k]=code.linea(k);
			k++;
		}
		k=0;
		int renglon=1;
		while (k<codigo.length){
			g.drawString(codigo[k],0, 14*renglon);
			k++;
			renglon++;
		}

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
		String clase = s.next();
		leer(s.nextInt());
		s.close();
		JFrame frame = new JFrame("Debugger");
		Figuras fig = new Figuras(clase);
		frame.setContentPane(fig);
		frame.setLayout(new GridLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1300,700);
		frame.setVisible(true);
		System.out.println(frame.getGraphics());
		Graphics g = frame.getGraphics();
		g.drawString("LOLOLOLOL",400,400);
		frame.update(g);
	}
}