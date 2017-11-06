package debugger.handlers;

// ShowCode.java
// Andrew Davison, March 2009, ad@fivedots.coe.psu.ac.th

/*  Store a map of filename-ShowLine objects, so
    a line from a specified file can be listed.
*/

import java.io.*;
import java.util.*;


public class ShowCode
{
  private TreeMap<String,ShowLines> listings;


  public ShowCode()
  {  listings = new TreeMap<String,ShowLines>();  }


  public void add(String fnm)
  // add fnm-ShowLines pair to map
  {
    if (listings.containsKey(fnm)) {
      System.out.println(fnm + "already listed");
      return;
    }

    listings.put(fnm, new ShowLines(fnm));
    System.out.println(fnm + " added to listings");
  }  // end of add()


  public String show(String fnm, int lineNum)
  // return the specified line from fnm
  {
    ShowLines lines = listings.get(fnm);
    if (lines == null)
      return (fnm + "not listed");

    return lines.show(lineNum);
  }  // end of show()

    
}  // end of ShowCode class
