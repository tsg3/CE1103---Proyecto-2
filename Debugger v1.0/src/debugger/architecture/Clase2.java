package debugger.architecture;
public class Clase2 {
	static String JULS = "asdasd";
	void imprimir(String texto){
		System.out.println(texto);
	}
	public static void main(String[] args) {
		Clase2 clase = new Clase2();
		String mensaje;
		mensaje = "Hola Mundo";
		if (mensaje.equals("Hola Mundo")){
			mensaje += "!";
			for (int i = 0; i<3; i++)
				clase.imprimir(mensaje);
			int j=0;
			while (j<3){
				clase.imprimir(mensaje);
				j++;
			}
		}
	}
}