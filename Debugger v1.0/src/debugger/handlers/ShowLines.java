package debugger.handlers;

// ShowLines.java
// Andrew Davison, March 2009, ad@fivedots.coe.psu.ac.th

/* Load a file line-by-line into an ArrayList.
   The show() method displays the specified line
   (counting from 1, not 0).
*/

import java.io.*;
import java.util.*;


public class ShowLines
{
  private ArrayList<String> code;


  public ShowLines(String fileName)
  {
    code = new ArrayList<String>();

    String line = null;
    BufferedReader in = null;

    try {
      in = new BufferedReader(new FileReader("C:\\Users\\este0\\eclipse-workspace\\Debugger\\src\\debugger\\architecture\\" + fileName));
      while ((line = in.readLine()) != null)
         code.add(line);
    }
    catch (IOException ex) {
      System.out.println("Problem reading " + fileName);
    }
    finally {
      try {
        if (in != null)
          in.close();
      }
      catch (IOException e) {}
    }
  }  // end of showLines()


  public String show(int lineNum)
  // lineNum starts at 1, not 0
  {
    if (code == null)
       return "No code to show";

    if ((lineNum < 1) || (lineNum > code.size()))
      return "Line no. out of range";

    return ( "" + lineNum + ".\t" + code.get(lineNum-1));
  }  // end of show()


  // --------------------------------------------------

  public static void main(String[] args)
  {
     ShowLines sl = new ShowLines("Clase2.java");
     System.out.println( sl.show(18));
     System.out.println( sl.show(1));
     System.out.println( sl.show(2000));
  }


}  // end of ShowLines class
