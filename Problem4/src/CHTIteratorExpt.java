import java.io.PrintWriter;
import java.util.Iterator;

/**
 * A simple experiment with ChainedHashTable iterators
 *
 */
public class CHTIteratorExpt
{

  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    ChainedHashTable<Character, String> cht = new ChainedHashTable<>();
    cht.set('a', "apple");
    cht.set('b', "banana");
    cht.set('c', "cherry");
    cht.set('d', "dun-dun-dun");
    cht.dump(pen);
    Iterator<String> itVals = cht.iterator();
    Iterator<Character> itKeys = cht.keysIterator();
    while (itVals.hasNext() || itKeys.hasNext())
      {
        pen.println("Next value is " + itVals.next() + ", next key is "
                    + itKeys.next());
      }// while
  }// main

}// CHTIteratorExpt class
