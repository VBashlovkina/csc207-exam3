import java.io.PrintWriter;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Dictionaries implemented as binary search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class BST<K, V>
    implements Dictionary<K, V>
{

  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
   
     This code is closely based on code by Samuel A. Rebelsky from
     exam 2 of the fall 2013 section of Grinnell's CSC 207.  That
     code, in turn, is likely based on code from the Tao of Java
     laboratory on binary search trees.  The latest version of 
     that code is available at 
       <https://github.com/Grinnell-CSC207/bst>.

     We implement dictionaries using binary search trees. Each node
     in the search tree contains a key, a value, and links to
     (potentially null) left and right subtrees. The left subtree
     contains entries with keys smaller than the key of the node.
     The right subtree contains entries with keys larger than the
     key of the node.

   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The root of the tree.
   */
  BSTNode root;

  /**
   * The comparator used to give the ordering.
   */
  Comparator<K> order;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new BST
   */
  public BST(Comparator<K> order)
  {
    this.root = null;
    this.order = order;
  } // BST

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  public V get(K key)
    throws Exception
  {
    BSTNode node = find(this.root, key);
    if (node == null)
      {
        throw new NoSuchElementException();
      } // if not found
    return node.value;
  } // get(K)

  public boolean containsKey(K key)
  {
    BSTNode node = find(this.root, key);
    return node != null;
  } // containsKey(K)

  /**
   * Dump a simple textual representation of the tree.
   */
  public void dump(PrintWriter pen)
  {
    dump(pen, this.root, "");
  } // dump(PrintWriter)

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  public void set(K key, V value)
  {
    this.root = insert(this.root, key, value);
  } // set

  public void remove(K key)
  {
    this.root = remove(this.root, key);
  } // remove(K)

  public void clear()
  {
    // I love garbage collection. In C, we'd have to individually
    // free all of the nodes.
    this.root = null;
  } // clear

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterable<K> keys()
  {
    return new Iterable<K>()
      {
        public Iterator<K> iterator()
        {
          return BST.this.keysIterator();
        } // iterator()
      }; // new Iterable<K>
  } // keys

  public Iterator<K> keysIterator()
  {
    return new Iterator<K>()
      {
        Iterator<BSTNode> it = new BSTNodeIterator(BST.this.root);

        public K next()
          throws NoSuchElementException
        {
          return it.next().key;
        } // next()

        public boolean hasNext()
        {
          return it.hasNext();
        } // hasNext

        public void remove()
          throws UnsupportedOperationException,
            IllegalStateException
        {
          it.remove();
        } // remove
      }; // new Iterator<K>
  } // keysIterator()

  public Iterator<V> iterator()
  {
    return new Iterator<V>()
      {
        Iterator<BSTNode> it = new BSTNodeIterator(BST.this.root);

        public V next()
          throws NoSuchElementException
        {
          return it.next().value;
        } // next()

        public boolean hasNext()
        {
          return it.hasNext();
        } // hasNext

        public void remove()
          throws UnsupportedOperationException,
            IllegalStateException
        {
          it.remove();
        } // remove
      }; // new Iterator<V>
  } // iterator()

  // +-----------------+-------------------------------------------------
  // | Local Utilities |
  // +-----------------+

  /**
   * Print a simple representation of a BST using pen, indenting the BST the
   * specified amount.
   */
  void dump(PrintWriter pen, BSTNode tree, String indent)
  {
    if (tree == null)
      {
        // Special case: For the empty tree, we just print a special
        // symbol
        pen.println(indent + "<>");
      }
    else
      {
        // Normal case: Print the key/value pair and the subtrees
        pen.println(indent + "[" + tree.key + ":" + tree.value + "]");
        dump(pen, tree.smaller, indent + "    ");
        dump(pen, tree.larger, indent + "    ");
      } // if it's a real node
  } // dump(PrintWriter, BSTNode, String)

  /**
   * Find the node in a BST with the given key.
   * 
   * @return the node containing the given key, or null if no such node
   *         exists.
   */
  BSTNode find(BSTNode tree, K key)
  {
    // Special case: Empty tree. Give up
    if (tree == null)
      {
        return null;
      } // if (tree == null)
    else
      {
        int tmp = order.compare(key, tree.key);
        if (tmp == 0)
          {
            return tree;
          }
        else if (tmp < 0)
          {
            return find(tree.smaller, key);
          }
        else
          {
            return find(tree.larger, key);
          } // if the key is larger than the key at the node
      } // if the tree is nonempty
  } // find(BSTNode, K, V)

  /**
   * Insert a key/value pair in the tree.
   * 
   * @return newtree, the updated tree
   */
  BSTNode insert(BSTNode tree, K key, V value)
  {
    // Special case: Empty tree. Build a new node.
    if (tree == null)
      {
        return new BSTNode(key, value);
      } // if (tree == null)
    else
      {
        int tmp = order.compare(key, tree.key);
        if (tmp == 0)
          {
            tree.value = value;
            return tree;
          }
        else if (tmp < 0)
          {
            tree.smaller = insert(tree.smaller, key, value);
            return tree;
          }
        else
          {
            tree.larger = insert(tree.larger, key, value);
            return tree;
          } // if the key is larger than the key at the node
      } // if the tree is nonempty
  } // insert(BSTNode, K, V)

  /**
   * Remove the element with the specified key, assuming that the
   * element appears in the tree.  Returns the modified tree.
   */
  BSTNode remove(BSTNode tree, K key)
  {
    // You can't remove from the empty tree.
    if (tree == null)
      return tree;
    // Determine the relationship of the key to the root of the subtree.
    int tmp = order.compare(key, tree.key);
    // If we've found the item to remove, ...
    if (tmp == 0)
      {
        // STUB.  
        return null;
      } // if we've found the item to remove
    else 
      {
        // STUB.
        return tree;
      } // if (tmp != 0)
  } // remove(BSTNode, K)

  // +---------------+---------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * Nodes in a linked dictionary.
   */
  class BSTNode
  {
    // +--------+----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The key in the key/value pair.
     */
    K key;

    /**
     * The value in the key/value pair.
     */
    V value;

    /**
     * The left subtree, which should contain the smaller values.
     */
    BSTNode smaller;

    /**
     * The right subtree, which should contain the larger values.
     */
    BSTNode larger;

    // +--------------+----------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new node. (We set smaller and larger by using the fields.)
     */
    public BSTNode(K key, V value)
    {
      this.key = key;
      this.value = value;
      this.smaller = null;
      this.larger = null;
    } // BSTNode(K,V)
  } // BSTNode

  /**
   * An iterator for BSTNodes.
   */
  class BSTNodeIterator
      implements Iterator<BSTNode>
  {
    // +--------+----------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The nodes in the tree that we have left to process.
     */
    Stack<BSTNode> remaining;

    // +--------------+----------------------------------------------------
    // | Constructors |
    // +--------------+

    public BSTNodeIterator(BSTNode root)
    {
      remaining = new Stack<BSTNode>();
      if (root != null)
        {
          remaining.push(root);
        } // if (root != null)
    } // BSTNodeIterator(BSTNode)

    public BSTNode next()
      throws NoSuchElementException
    {
      if (!this.hasNext())
        {
          throw new NoSuchElementException();
        } // if there are no more elements
      BSTNode temp = this.remaining.pop();
      if (temp.larger != null)
        {
          this.remaining.push(temp.larger);
        } // if there's a larger subtree
      if (temp.smaller != null)
        {
          this.remaining.push(temp.smaller);
        } // if there's a smaller subtree
      return temp;
    } // next()

    public boolean hasNext()
    {
      return !(this.remaining.empty());
    } // hasNext

    public void remove()
      throws UnsupportedOperationException,
        IllegalStateException
    {
      throw new UnsupportedOperationException();
    } // remove
  } // BSTNodeIterator<K,V>
} // BST<K,V>
