package debugger.architecture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SingleGraphics extends JPanel {

	private static SingleGraphics instance = null;
	public ListaVariables vars = new ListaVariables();
	public static Diagrama diagram;
	public String clase;
	public String[] codigo;
	public Stack<Integer> stack = new Stack<Integer>();
	public AbstractLinesFactory abstractFactory = new AbstractLinesFactory();

	public static SingleGraphics getInstance(String clase,Diagrama diagram,String[] codigo){
		if (instance==null){
			try {
				instance = new SingleGraphics( clase, diagram, codigo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}

	protected SingleGraphics(String clase,Diagrama diagram,String[] codigo) throws IOException{
		this.clase = clase;
		this.diagram=diagram;
		this.codigo = codigo;
	}

	public void paint(Graphics g){
		int i = 0;
		g.setFont(new Font("Lucida",Font.PLAIN,14));
		g.setColor(Color.BLACK);
		while (i<vars.largo){
			g.drawString(vars.variable(i).getName()+"="+vars.variable(i).getValue(),10,600+vars.variable(i).getY());
			i++;
		}
		int k = 0;
		int renglon = 1;
		while (k<codigo.length){
			g.drawString(codigo[k],0, 14*renglon);
			k++;
			renglon++;
		}
		this.diagram=diagram;
		g.setFont(new Font("TimesRoman",Font.PLAIN,20));
		g.setColor(Color.BLUE);
		i=0;
		int j=0;
		int lineaLarga=diagram.lineaLarga();
		while (i<diagram.largo){
			g.drawString(diagram.figura(i).getLine(),400+415-10*(diagram.figura(i).getLine().length()/4)-20 + (this.getWidth()-2*(415-10*(diagram.figura(i).getLine().length()/4)-20)-400 - g.getFontMetrics(g.getFont()).stringWidth(diagram.figura(i).getLine())) / 2,diagram.figura(i).getY() - 20 + ((30 - g.getFontMetrics(g.getFont()).getHeight()) / 2) + g.getFontMetrics(g.getFont()).getAscent()+30);
			g.drawOval(400+380-10*(diagram.figura(i).getLine().length()/4)-20,diagram.figura(i).getY()+10,this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400,30);
			if (diagram.figura(i+1)!=null){
				g.drawLine(400+380-10*(diagram.figura(i).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400)/2, diagram.figura(i).getY()+40, 400+380-10*(diagram.figura(i).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400)/2, diagram.figura(i).getY()+60);
				if (diagram.figura(i+1).getY()==(i+1)*50+(j+1)*30){
					int startLine = stack.pop();
					int finalLine = i;
					abstractFactory.createLines(g, diagram.figura(startLine).getLine(), diagram.figura(lineaLarga).getLine(), diagram.figura(finalLine).getY(), diagram.figura(startLine).getY(), this.getWidth(), j);
					/*if ((diagram.figura(startLine).getLine().contains("while"))||(diagram.figura(startLine).getLine().contains("for"))){
						g.drawLine(400+380-10*(diagram.figura(startLine).getLine().length()/4)-20, diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20-30-(j*30), diagram.figura(startLine).getY()+25);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20-30-(j*30), diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20-30-(j*30), diagram.figura(finalLine).getY()+60);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20-30-(j*30), diagram.figura(finalLine).getY()+60, 400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+60);

						g.drawLine(400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400), diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(startLine).getY()+25);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(finalLine).getY()+70);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(finalLine).getY()+70, 400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+70);
						g.drawLine(400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+70, 400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+90);
					}

					else if (diagram.figura(startLine).getLine().contains("if")){
						g.drawLine(400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400), diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(startLine).getY()+25);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(startLine).getY()+25, 400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(finalLine).getY()+60);
						g.drawLine(400+380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(lineaLarga).getLine().length()/4)-20)-400)+30+(j*30), diagram.figura(finalLine).getY()+60, 400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+60);
						g.drawLine(400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+60, 400+380-10*(diagram.figura(startLine).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(startLine).getLine().length()/4)-20)-400)/2, diagram.figura(finalLine).getY()+90);
					}*/
					j++;
				}
			}
			if ((diagram.figura(i).getLine().contains("if"))||(diagram.figura(i).getLine().contains("for"))||(diagram.figura(i).getLine().contains("while"))){
				stack.push(i);
			}
			i++;
		}
		Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\este0\\Downloads\\arrow.gif");
		g.drawImage(image,1150,diagram.y+10,50,30,null);
	}
}
