package debugger.architecture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SingleGraphics extends JPanel {

	public ListaVariables vars = new ListaVariables();
	public Diagrama diagram = new Diagrama();
	public String clase;

	public SingleGraphics(String clase){
		this.clase = clase;
	}

	public static boolean declaracion(String linea){
		String[] lineas = linea.split(" ");
		if (lineas.length == 2)
			return true;
		return false;
	}

	public void paint(Graphics g){

		int i = 0;
		g.setFont(new Font("Lucida",Font.PLAIN,14));
		g.setColor(Color.BLACK);
		while (i<vars.largo){
			g.drawString(vars.variable(i).getName()+"="+vars.variable(i).getValue(),10,600+vars.variable(i).getY());
			i++;
		}
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
		g.setFont(new Font("TimesRoman",Font.PLAIN,20));
		g.setColor(Color.BLUE);
		i=0;
		while (i<diagram.largo){
			g.drawString(diagram.figura(i).getLine(),400+415-10*(diagram.figura(i).getLine().length()/4)-20 + (this.getWidth()-2*(415-10*(diagram.figura(i).getLine().length()/4)-20)-400 - g.getFontMetrics(g.getFont()).stringWidth(diagram.figura(i).getLine())) / 2,diagram.figura(i).getY() - 20 + ((30 - g.getFontMetrics(g.getFont()).getHeight()) / 2) + g.getFontMetrics(g.getFont()).getAscent()+30);
			g.drawOval(400+380-10*(diagram.figura(i).getLine().length()/4)-20,diagram.figura(i).getY()+10,this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400,30);
			if (diagram.figura(i+1)!=null)
				g.drawLine(400+380-10*(diagram.figura(i).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400)/2, diagram.figura(i).getY()+40, 400+380-10*(diagram.figura(i).getLine().length()/4)-20+(this.getWidth()-2*(380-10*(diagram.figura(i).getLine().length()/4)-20)-400)/2, diagram.figura(i).getY()+60);
			i++;
		}

	}


}
