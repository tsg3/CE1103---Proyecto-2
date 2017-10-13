import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Lectura {

	public static boolean existePalabra(String linea, String palabra){
		String[] palabras = linea.split("\\W+");
		for (String palabra2 : palabras){
			if (palabra2.equals(palabra)){
				return true;
			}
		}
		return false;
	}

	public static boolean declaracion(String linea){
		String[] lineas = linea.split(" ");
		if (lineas.length == 2)
			return true;
		return false;
	}

	public static void anidacion(int ciclos){
		while (ciclos != 0){
			System.out.print("\t");
			ciclos--;
		}
	}

	public static void main (String[] args) throws IOException{
		String file = "C:\\Users\\este0\\eclipse-workspace\\Lectura\\src\\Clase2.txt";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		int ciclos = 0;
		while ((line = br.readLine()) != null){
			if (line.equals("}")){
				if (ciclos == 0)
					System.out.println("Fin de jerarquía");
				else {
					System.out.println("Fin de ciclo");
					ciclos--;
				}
			}
			else if (existePalabra(line,"if")){
				System.out.print("if: ");
				anidacion(ciclos);
				System.out.println("\t\t\t\t" + line.substring(0,line.length()-1));
				ciclos++;
			}
			else if (existePalabra(line,"for")){
				System.out.print("for: ");
				anidacion(ciclos);
				System.out.println("\t\t\t\t" + line.substring(0,line.length()-1));
				ciclos++;
			}
			else if (existePalabra(line,"while")){
				System.out.print("while: ");
				anidacion(ciclos);
				System.out.println("\t\t\t\t" + line.substring(0,line.length()-1));
				ciclos++;
			}
			else {
				if (line.contains("args"))
					System.out.println("Crea el main");
				else if (line.contains("=")){
					System.out.print("Asignación de Variable: ");
					anidacion(ciclos);
					System.out.println("\t" + line.substring(0,line.length()-1));
				}
				else if ((declaracion(line)) || (line.contains("new"))){
					System.out.print("Declaración de Variable: ");
					anidacion(ciclos);
					System.out.println("\t" + line.substring(0,line.length()-1));
				}
				else if (existePalabra(line,"class"))
					System.out.println("Crea la clase");
				else {
					System.out.print("Acción: ");
					anidacion(ciclos);
					System.out.println("\t\t\t" + line.substring(0,line.length()-1));
				}
			}
		}
	}
}
