package debugger.architecture;
public class Clase2 {
	public static void main(String[] args) {
		Clase2 c = new Clase2();
		String mensaje = "Hola Mundo";
		int i = 0;
		int j = 0;
		if(true){
			System.out.println(mensaje);
			while (j<3){
				System.out.println(mensaje);
				j++;
			}
			i++;
		}
		System.out.println("Fin");
		System.out.println(".");
	}
}