package debugger.architecture;

import java.awt.Graphics;

public class LinesFactory {

	public LinesFactory (Graphics g, String text1, String text2, int y3, int y1, int w, int j){

		if (text1.contains("while")||text1.contains("for")){
			g.drawLine(400+380-10*(text1.length()/4)-20, y1+25, 400+380-10*(text2.length()/4)-20-30-(j*30), y1+25);
			g.drawLine(400+380-10*(text2.length()/4)-20-30-(j*30), y1+25, 400+380-10*(text2.length()/4)-20-30-(j*30), y3+60);
			g.drawLine(400+380-10*(text2.length()/4)-20-30-(j*30), y3+60, 400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+60);

			g.drawLine(400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400), y1+25, 400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y1+25);
			g.drawLine(400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y1+25, 400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y3+70);
			g.drawLine(400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y3+70, 400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+70);
			g.drawLine(400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+70, 400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+90);

		}
		else{
			g.drawLine(400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400), y1+25, 400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y1+25);
			g.drawLine(400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y1+25, 400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y3+60);
			g.drawLine(400+380-10*(text2.length()/4)-20+(w-2*(380-10*(text2.length()/4)-20)-400)+30+(j*30), y3+60, 400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+60);
			g.drawLine(400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+60, 400+380-10*(text1.length()/4)-20+(w-2*(380-10*(text1.length()/4)-20)-400)/2, y3+90);
		}

	}

}
