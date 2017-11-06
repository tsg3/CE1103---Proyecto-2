package debugger.architecture;

public class Animacion {

	public Diagrama diagram;
	public Figura current;

	public Animacion(Diagrama diagram){
		this.diagram = diagram;
		this.current = diagram.root;
	}

	public Figura search (String line) {
		if (line.contains(current.getLine()))
			return current;
		else if (current.getNext()!=null){
			if (line.contains(current.getNext().getLine())){
				current=current.getNext();
				return current;
			}
		}
		else{
			current = diagram.root;
			for (int i = 0; i<diagram.largo;i++){
				if (line.contains(diagram.figura(i).getLine())){
					current = diagram.figura(i);
				}
			}

		}
		return current;
	}

}
