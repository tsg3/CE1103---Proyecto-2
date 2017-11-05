package debugger.architecture;

public class Figura {

	private Figura next;
	private String line;
	private int y;

	public Figura getNext() {
		return next;
	}
	public void setNext(Figura next) {
		this.next = next;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

}
