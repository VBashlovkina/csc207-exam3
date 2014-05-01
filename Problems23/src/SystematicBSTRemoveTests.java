import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Comparator;

/**
 * Systematic ("white box") tests for the remove method in binary 
 * search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class SystematicBSTRemoveTests
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+ 

  /*
     The methods buildTree and check are closely based on code by 
     Samuel A.  Rebelsky from exam 2 of the fall 2013 section of 
     Grinnell's CSC 207.  
   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+
  
  /**
   * A quick test of removing from the empty tree.
   */
  @Test
  public void removeFromNull()
  {
    BST<Character, String> tree = buildTree("");
    assertFalse(tree.containsKey('a'));
    tree.remove('a');
    assertFalse(tree.containsKey('a')); 
  } // removeFromNull

  /**
   * A quick test of removing from the singleton tree.
   */
  @Test
  public void removeFromSingleton()
  {
    BST<Character, String> tree = buildTree("a");
    assertTrue(tree.containsKey('a'));
    tree.remove('a');
    assertFalse(tree.containsKey('a'));
  } // removeFromSingleton()
  
  /**
   * A quick test of removing a leaf.  Needs to be extended.
   */
  @Test
  public void removeLeaf()
  {
    BST<Character, String> tree = buildTree("ab");
    tree.remove('b');
    assertTrue(tree.containsKey('a'));
    assertFalse(tree.containsKey('b'));
  } // removeLeaf
  
  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

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

  /**
   * Determine if a tree contains all of the characters in str.
   */
  public static void check(BST<Character, String> tree, String str)
  {
    // Check all of the characters in order
    int len = str.length();
    for (int i = 0; i < len; i++)
      {
        char c = str.charAt(i);
        if (!tree.containsKey(c))
          {
            fail("Tree does not contain " + c);
          } // if (! tree.containsKey(c))
      } // for
  } // check(BST<Character,String>, String)
} // class SystematicBSTRemoveTests
