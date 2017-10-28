package debugger.architecture;

public class Linea {

	Linea next=null;
	String text;

	public Linea(String texto){
		text=texto;
	}

	public Linea getNext() {
		return next;
	}
	public void setNext(Linea next) {
		this.next = next;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}



}
