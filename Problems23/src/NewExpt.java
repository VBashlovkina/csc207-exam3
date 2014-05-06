import java.io.PrintWriter;
import java.util.Comparator;
/**
 * 
 * Test the alphabet string
 *
 */

public class NewExpt
{
  /**
   * Build a tree by adding the characters in str, one at a time.
   */
  public static BST<Character, String> buildTree(String str)
  {
    // Build the empty tree.
    BST<Character, String> tree =
        new BST<Character, String>(new Comparator<Character>()
          {
            public int compare(Character left, Character right)
            {
              return left.compareTo(right);
            } // compare(Character, Character)
          });

    // Add all of the characters in order.
    int len = str.length();
    for (int i = 0; i < len; i++)
      {
        char c = str.charAt(i);
        tree.set(c, Character.toString(c));
      } // for

    // And we're done.
    return tree;
  } // buildTree(String)
  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    BST<Character, String> tree = buildTree("phxdltzbfj");
    tree.dump(pen);
    tree.remove('p');
    tree.dump(pen);
    pen.println("contains p? " + tree.containsKey('p'));
    pen.println("contains b? " + tree.containsKey('b'));
    pen.println("contains c? " + tree.containsKey('c'));
  }// main
}// NewExpt class
