import java.io.PrintWriter;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Some simple experiments with binary search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class BSTExpt
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
     This code is closely based on code by Samuel A. Rebelsky from
     exam 2 of the fall 2013 section of Grinnell's CSC 207.  
   */

  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
    throws Exception
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    BST<String, String> dict = new BST<String, String>(new Comparator<String>()
      {
        public int compare(String left, String right)
        {
          return left.compareTo(right);
        } // compare(String, String)
      });

    String[] values =
        new String[] { "gorilla", "dingo", "chimp", "emu", "elephant", "beta",
                      "aardark", "chinchilla", "yeti", "gibbon", "horse",
                      "elephant", "duck", "emu" };

    // Add each element and make sure that it's there.
    for (String value : values)
      {
        String key = value.substring(0, 1);
        pen.println("Setting " + key + " to " + value);
        dict.set(key, value);
        if (!dict.containsKey(key))
          {
            pen.println("  FAILURE");
          }
        else
          {
            String val = dict.get(key);
            if (!value.equals(val))
              {
                pen.println("Contains " + val + "rather than " + value);
              } // if the dictionary value is not the added value
          } // if the dictionary does not contain the key
      } // for

    // A quick printout for fun
    pen.println("After setting elements");
    iterate(pen, dict);

    // Delete all of the elements
    for (String value : values)
      {
        String key = value.substring(0, 1);
        pen.println("Removing " + key);
        dict.remove(key);
        if (dict.containsKey(key))
          {
            pen.println("  FAILED TO REMOVE KEY");
            String val = dict.get(key);
            pen.println("  Dictionary has " + key + ":" + val);
          } // if (dict.containsKey(key))
      } // for

    // A quick printout for fun
    pen.println("After deleting elements");
    iterate(pen, dict);
  } // main(String[])

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Iterate the keys of the dictionary.
   */
  public static void iterate(PrintWriter pen, Dictionary<String, String> dict)
  {
    for (String key : dict.keys())
      {
        pen.print(key);
        pen.print(":");
        try
          {
            pen.println(dict.get(key));
          }
        catch (Exception e)
          {
            pen.println("FAILED " + e.toString());
          } // catch
      } // for each key
  } // iterate()
} // BSTExpt
