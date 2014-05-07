import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Comparator;

/**
 * Systematic ("white box") tests for the remove method in binary 
 * search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Vasilisa Bashlovkina
 * 
 * Referred to http://www.asciitable.com/ to come up with characters
 *  larger and smaller than letters
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
     
     Tests are strategically named: case*number of case*x*number of position*
     Cases and positions are borrowed from the assignment and Q&A for Problem 2.
     Cases:
     00 - leaf (no subtrees)
     01 - no right subtree
     02 - no left subtree
     04 - both subtrees, left subtree has right subtree with no right subtree 
     05 - both subtrees, left subtree has right subtree with right subtree 
          (l is the rightmost key in the left subtree, and has no left subtree) 
     06 - both subtrees, left subtree has right subtree with right subtree 
          (l is the rightmost key in the left subtree, but has a left subtree) 
     Positions:
     00 - root of the tree
     01 - root of the left subtree
     02 - root of the right subtree
     03 - root of the left subtree of the left subtree
     04 - root of the right subtree of the left subtree
     05 - root of the left subtree of the right subtree
     06 - root of the right subtree of the right subtree
     
   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * A string that gives a nice acceptably balanced tree.
   * I relied on the tree produced by it in making the test cases
   * but I don't actually use it directly in the code.
   */
  static final String alphabet = "phxdltzbfjnrvacegikmoqsuw";

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
   * A quick test of removing from the singleton tree. (case 00x00)
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
   * A quick test of removing a larger leaf (case 00, position 02)
   */
  @Test
  public void removeLeafLarger()
  {
    BST<Character, String> tree = buildTree("ab");
    tree.remove('b');
    assertTrue(tree.containsKey('a'));
    assertFalse(tree.containsKey('b'));
  } // removeLeafLarger

  /**
   * A quick test of removing a smaller leaf (case 00, position 02)
   */
  @Test
  public void removeLeafSmaller()
  {
    BST<Character, String> tree = buildTree("ba");
    tree.remove('a');
    assertTrue(tree.containsKey('b'));
    assertFalse(tree.containsKey('a'));
  } // removeLeafSmaller

  /**
   * General tests
   * 
   * Cases 00-06 tested at position 00 - root of the tree
   */
  @Test
  public void case01x00()
  {
    generalTest("ph", 0);
  }// case 01x00

  @Test
  public void case02x00()
  {
    generalTest("px", 0);
  }// case 02x00

  @Test
  public void case03x00()
  {
    generalTest("phxd", 0);
  }// case 03x00

  @Test
  public void case04x00()
  {
    generalTest("phxdltzbfj", 0);
  }// case 04x00

  @Test
  public void case05x00()
  {
    generalTest("phxdltzbfjn", 0);
  }// case 05x00

  @Test
  public void case06x00()
  {
    generalTest("phxdltzbfjnrvacegikm", 0);
  }// case 06x00

  /**
   * Same cases, position of deleted element - left subtree (01)
   */
  @Test
  public void case01x01()
  {
    generalTest("|ph", 1);
  }// case 01x01

  @Test
  public void case02x01()
  {
    generalTest("|px", 1);
  }// case 02x01

  @Test
  public void case03x01()
  {
    generalTest("|phxd", 1);
  }// case 03x01

  @Test
  public void case04x01()
  {
    generalTest("|phxdltzbfj", 1);
  }// case 04x01

  @Test
  public void case05x01()
  {
    generalTest("|phxdltzbfjn", 1);
  }// case 05x01

  @Test
  public void case06x01()
  {
    generalTest("|phxdltzbfjnrvacegikm", 1);
  }// case 06x01

  /**
   * Same cases, position of deleted element - right subtree (02)
   */
  @Test
  public void case01x02()
  {
    generalTest("0ph", 1);
  }// case 01x02

  @Test
  public void case02x02()
  {
    generalTest("0px", 1);
  }// case 02x02

  @Test
  public void case03x02()
  {
    generalTest("0phxd", 1);
  }// case 03x02

  @Test
  public void case04x02()
  {
    generalTest("0phxdltzbfj", 1);
  }// case 04x02

  @Test
  public void case05x02()
  {
    generalTest("0phxdltzbfjn", 1);
  }// case 05x02

  @Test
  public void case06x02()
  {
    generalTest("0phxdltzbfjnrvacegikm", 1);
  }// case 06x02

  /**
   * Same cases, position of deleted element - at the root of
   *  the left subtree of the left subtree (03)
   */
  @Test
  public void case00x03()
  {
    generalTest("|{p", 2);
  }// case 01x03

  @Test
  public void case01x03()
  {
    generalTest("|{ph", 2);
  }// case 01x03

  @Test
  public void case02x03()
  {
    generalTest("|{px", 2);
  }// case 02x03

  @Test
  public void case03x03()
  {
    generalTest("|{phxd", 2);
  }// case 03x03

  @Test
  public void case04x03()
  {
    generalTest("|{phxdltzbfj", 2);
  }// case 04x03

  @Test
  public void case05x03()
  {
    generalTest("|{phxdltzbfjn", 2);
  }// case 05x03

  @Test
  public void case06x03()
  {
    generalTest("|{phxdltzbfjnrvacegikm", 2);
  }// case 06x03

  /**
   * Same cases, position of deleted element - at the 
   * root of the right subtree of the left subtree (04)
   */
  @Test
  public void case00x04()
  {
    generalTest("|0p", 2);
  }// case 00x04

  @Test
  public void case01x04()
  {
    generalTest("|0ph", 2);
  }// case 01x04

  @Test
  public void case02x04()
  {
    generalTest("|0px", 2);
  }// case 02x04

  @Test
  public void case03x04()
  {
    generalTest("|0phxd", 2);
  }// case 03x04

  @Test
  public void case04x04()
  {
    generalTest("|0phxdltzbfj", 2);
  }// case 04x04

  @Test
  public void case05x04()
  {
    generalTest("|0phxdltzbfjn", 2);
  }// case 05x04

  @Test
  public void case06x04()
  {
    generalTest("|0phxdltzbfjnrvacegikm", 2);
  }// case 06x04

  /**
   * Same cases, position of deleted element - 
   * at the root of the left subtree of the right subtree. (05)
   */
  @Test
  public void case00x05()
  {
    generalTest("0|p", 2);
  }// case 00x05

  @Test
  public void case01x05()
  {
    generalTest("0|ph", 2);
  }// case 01x05

  @Test
  public void case02x05()
  {
    generalTest("0|px", 2);
  }// case 02x05

  @Test
  public void case03x05()
  {
    generalTest("0|phxd", 2);
  }// case 03x05

  @Test
  public void case04x05()
  {
    generalTest("0|phxdltzbfj", 2);
  }// case 04x05

  @Test
  public void case05x05()
  {
    generalTest("0|phxdltzbfjn", 2);
  }// case 05x05

  @Test
  public void case06x05()
  {
    generalTest("0|phxdltzbfjnrvacegikm", 2);
  }// case 06x06

  /**
   * Same cases, position of deleted element - 
   * at the root of the right subtree of the right subtree (06)
   */
  @Test
  public void case00x06()
  {
    generalTest("01p", 2);
  }// case 00x06

  @Test
  public void case01x06()
  {
    generalTest("01ph", 2);
  }// case 01x06

  @Test
  public void case02x06()
  {
    generalTest("01px", 2);
  }// case 02x06

  @Test
  public void case03x06()
  {
    generalTest("01phxd", 2);
  }// case 03x06

  @Test
  public void case04x06()
  {
    generalTest("01phxdltzbfj", 2);
  }// case 04x06

  @Test
  public void case05x06()
  {
    generalTest("01phxdltzbfjn", 2);
  }// case 05x06

  @Test
  public void case06x06()
  {
    generalTest("01phxdltzbfjnrvacegikm", 2);
  }// case 06x06

  // +-----------+-------------------------------------------------------
  // | Utilities |
  // +-----------+

  /**
   * Determine whether the character at a given position was successfully deleted
   * @param str
   *    a string consisting of chars that will be put in a tree
   * @param index
   *    index of the char to be deleted from the tree
   * @pre 0 => index > str.length
   * 
   */
  public static void generalTest(String str, int index)
  {
    // Build a tree
    BST<Character, String> tree = buildTree(str);
    // Find the char to delete and remove it
    tree.remove(str.charAt(index));
    // Check if all chars except for the deleted one are present
    checkSkipChar(tree, str, index);
  }// generalTest

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

  /**
   * Determine if a tree contains all of the characters in a given string 
   * but doesn't contain str.charAt(index).
   * @pre 0 => index > str.length
   * @param str
   *    a string consisting of chars that comprised the original tree
   * @param index
   *    index of the char deleted from the tree
   */
  public static void checkSkipChar(BST<Character, String> tree, String str,
                                   int index)
  {
    // Check all of the characters in order
    int len = str.length();
    for (int i = 0; i < len; i++)
      {
        char c = str.charAt(i);
        if (i != index)
          {
            if (!tree.containsKey(c))
              {
                fail("Tree does not contain " + c);
              } // if (! tree.containsKey(c))
          }// if not the index of the deleted character
        else
          assertFalse(tree.containsKey(c));
      } // for
  } // check(BST<Character,String>, String)

} // class SystematicBSTRemoveTests
