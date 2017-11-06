package debugger.architecture;

public class Codigo {

	Linea root=null;
	public int largo=0;

	public void add(String texto){
		Linea linea = new Linea(texto);
		largo++;
		if (root==null)
			root=linea;
		else{
			Linea current = root;
			while(current.getNext()!=null)
				current=current.getNext();
			current.setNext(linea);

		}


	}

	public String linea(int pos){
		int i = 0;
		Linea current = root;
		if (pos==0)
			return current.getText();
		else{
			while (i<pos){
				current=current.getNext();
				i++;
			}
			return current.getText();
		}
	}

}
