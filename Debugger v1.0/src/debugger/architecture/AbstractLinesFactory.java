package debugger.architecture;

import java.awt.Graphics;

public class AbstractLinesFactory {

	public void createLines(Graphics g, String text1, String text2, int y3, int y1, int w, int j){
		new LinesFactory( g,  text1,  text2,  y3,  y1,  w,  j);
	}

}
