package debugger.architecture;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
public class JavaToTxt {
	public static void addCode(String clase){
		String file = "C:\\Users\\este0\\eclipse-workspace\\Debugger\\src\\debugger\\architecture\\"+clase+".java";
		String outputFile = String.format("%stxt", file.replace(".java", "."));
		try (BufferedReader br = new BufferedReader(new FileReader(file));
				PrintWriter pw = new PrintWriter(new FileWriter(outputFile))){
			String line;
			while ((line = br.readLine()) != null){
				if (!(line.equals(""))){
					pw.printf(line);
					pw.printf("%n");
					System.out.println(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}