package debugger.architecture;

public class Diagrama {

	public Figura root = null;
	public int largo = 0;
	public int ciclos=0;
	public int y=0;

	public void add(String line){
		Figura fig = new Figura();
		fig.setLine(line);
		fig.setY(largo*50+ciclos*30);
		largo++;
		if (root==null){
			root = fig;
			return;
		}
		else{
			Figura current = root;
			while (current.getNext()!=null)
				current=current.getNext();
			current.setNext(fig);
			fig.setPrev(current);
		}
	}
	public Figura find(String line){
		if (root == null)
			return null;
		else if (line.contains(root.getLine()))
			return root;
		else{
			Figura current = root.getNext();
			while (current!=null){
				if (line.contains(current.getLine()))
					return current;
				current=current.getNext();
			}
			return null;
		}
	}
	public Figura figura (int pos){
		int i = 0;
		Figura current = root;
		if (pos==0)
			return current;
		else{
			while (i<pos){
				current=current.getNext();
				i++;
			}
			return current;
		}
	}
	int lineaLarga(){
		int posLineaLarga=0;
		String lineaLarga="";
		for(int i = 0; i<largo; i++){
			if (figura(i).getLine().length()>lineaLarga.length()){
				lineaLarga=figura(i).getLine();
				posLineaLarga=i;
			}
		}
		return posLineaLarga;
	}

}
