package debugger.architecture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Lectura {

	public static boolean declaracion(String linea){
		String[] lineas = linea.split(" ");
		if (lineas.length == 2)
			return true;
		return false;
	}

	public static void buscar (String line){
			if ((line.contains("if("))||(line.contains("if ("))){
				System.out.println("if: ");
			}
			else if ((line.contains("for("))||(line.contains("for ("))){
				System.out.println("for: ");
			}
			else if ((line.contains("while("))||(line.contains("while ("))){
				System.out.println("while: ");
			}
			else {
				if ((declaracion(line)) || (line.contains("new"))){
					System.out.println("Declaración de Variable: ");
				}
				else if (line.contains("=")){
					System.out.println("Asignación de Variable: ");
				}
				else {
				if ((line.contains("("))&&(line.contains(")"))&&(line.contains(";")))
					System.out.println("Accion:");
				else
					return;
				}
			}
	}
}
