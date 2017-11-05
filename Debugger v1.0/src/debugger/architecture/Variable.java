package debugger.architecture;

public class Variable {

	private Variable next;
	private String name;
	private Object value;
	private int y;

	public Variable getNext() {
		return next;
	}
	public void setNext(Variable next) {
		this.next = next;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
