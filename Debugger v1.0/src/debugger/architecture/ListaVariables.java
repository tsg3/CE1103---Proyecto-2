package debugger.architecture;

public class ListaVariables {

	public Variable root = null;
	public int largo = 0;

	public void add(String name, Object value){
		if (find(name)!=null){
			Variable node = find(name);
			node.setValue(value);
		}
		else{
		Variable node = new Variable();
		node.setName(name);
		node.setValue(value);
		node.setY(largo*14);
		largo++;
		if (root==null){
			root = node;
			return;
		}
		else{
			Variable current = root;
			while (current.getNext()!=null)
				current=current.getNext();
			current.setNext(node);
		}
		}
	}
	public Variable find(String name){
		if (root == null)
			return null;
		else if (root.getName().equals(name))
			return root;
		else{
			Variable current = root;
			while (current!=null){
				if (current.getName().equals(name))
					return current;
				current=current.getNext();
			}
			return null;
		}
	}
	Variable variable (int pos){
		int i = 0;
		Variable current = root;
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
}
