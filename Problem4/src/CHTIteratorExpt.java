import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Experiments for ChainedHashTable iterators
 * @author bashlovk
 *
 */
public class CHTIteratorExpt
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    ChainedHashTable<Character, String> cht = new ChainedHashTable<>();
    cht.set('a', "apple");
    cht.set('b', "banana");
    cht.set('c', "cherry");
    Iterator<String> it = cht.iterator();
    for (int i = 0; i < 3; i++)
      {
        pen.println("Has next? " + it.hasNext());
        pen.println("Next is " + it.next());
      }// for
    pen.println("Has next? " + it.hasNext());
  }// main

}// CHTIteratorExpt class
