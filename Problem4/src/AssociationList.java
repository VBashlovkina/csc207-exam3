import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Dictionaries implemented as linked structures.  
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class AssociationList<K, V>
    implements Dictionary<K, V>
{

  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
      Code in this file is closely based on LinkedDictionary.java, which
      is part of the Tao of Java lab on association lists by Samuel A.
      Rebelsky.  That code is available at
        <ulink url="https://github.com/Grinnell-CSC207/association-lists"/>
      Among other things, I've renamed the class, added implementations
      for the iterators, and corrected (intentional) bugs.

      We implement dictionaries using unordered linked lists with a dummy
      node at the front.  New elements are added at the front because
      we predict that get is more frequently called on recent elements
      (or maybe just because we're lazy).

      To help with various operations, an internal find method finds
      a key and returns the node immediately preceding the key.
   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The front of the linked list.
   */
  Node front;

  /**
   * The number of values in the association list.
   */
  int size;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new association list.
   */
  public AssociationList()
  {
    this.front = new Node(null, null);
    this.size = 0;
  } // AssociationList

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  public void dump(PrintWriter pen)
  {
    Node current = this.front.next;
    while (current != null)
      {
        pen.println(current.key + ": " + current.value);
        current = current.next;
      } // while
  } // dump(PrintWriter)

  public V get(K key)
    throws Exception
  {
    Node prev = find(key);
    return prev.next.value;
  } // get(K)

  public boolean containsKey(K key)
  {
    try
      {
        // If find succeeds, the key is there.
        find(key);
        return true;
      }
    catch (Exception e)
      {
        // If find doesn't succeed, the key is not there.
        return false;
      } // try/catch
  } // containsKey(K)

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  public void set(K key, V value)
  {
    try
      {
        Node prev = find(key);
        // The key is already there.  Update the value.
        prev.next.value = value;
      } // try 
    catch (Exception e)
      {
        // New key/value pairs go at the front.
        this.front.next = new Node(key, value, this.front.next);
        ++this.size;
      } // catch (Exception)
  } // set(K,V)

  public void remove(K key)
  {
    try 
      {
        // Find the predecessor to the node containing the key.
        Node prev = find(key);
        // Skip over the node.  Yay garbage collection!
        prev.next = prev.next.next;
        // Decrement the size
        --this.size;
      } // try
    catch (Exception e)
      { 
        // Do nothing.  We just aren't allowed to throw an exception.
      } // catch(Exception)
  } // remove(K)

  public void clear()
  {
    // I love garbage collection.  In C, we'd have to individually
    // free all of the nodes.
    front.next = null;
    this.size = 0;
  } // clear

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<V> iterator()
  {
    return new Iterator<V>()
      {
        Iterator<Node> nodes = nodeIterator();

        public boolean hasNext()
        {
          return nodes.hasNext();
        } // hasNext

        public V next()
        {
          return nodes.next().value;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          nodes.remove();
        } // remove
      }; // new Iterator<V>
  } // iterator()

  public Iterator<K> keysIterator()
  {
    return new Iterator<K>()
      {
        Iterator<Node> nodes = nodeIterator();

        public boolean hasNext()
        {
          return nodes.hasNext();
        } // hasNext

        public K next()
        {
          return nodes.next().key;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          nodes.remove();
        } // remove
      }; // new Iterator<K>
  } // keyIterator()

  /**
   * Get an Iterable for the keys.  See explanation in Dictionary.java.
   */
  public Iterable<K> keys()
  {
    return new Iterable<K>()
      {
        public Iterator<K> iterator()
        {
          return AssociationList.this.keysIterator();
        } // iterator()
      }; // new Iterable<K>
  } // keys()

  // +-----------------+-------------------------------------------------
  // | Local Utilities |
  // +-----------------+

  /**
   * Find the node with a specified key.
   *
   * @return prev, the node immediately before the found node
   * @throws Exception if no node has the given key.
   */
  public Node find(K key)
    throws Exception
  {
    Node prev = this.front;
    while (prev.next != null)
      {
        if (key.equals(prev.next.key))
          {
            return prev;
          } // if
        prev = prev.next; // Thanks EW, who doesn't care
      } // if

    // If we've gotten through the while loop, no elements
    // remain, and so it's not there.
    throw new Exception("No element with key '" + key + "'");
  } // find

  /**
   * A simple iterator for nodes.  This iterator is probably not
   * necessary because of how easy iteration is in association
   * lists.  However, it is included because it provides a good
   * model of how to write the keys and values iterators - both just
   * call this iterator.
   */
  public Iterator<Node> nodeIterator()
  {
    return new Iterator<Node>()
      {
        /**
         * The predecessor to the next element.
         */
        Node current = front;

        public boolean hasNext()
        {
          return current.next != null;
        } // hasNext

        public Node next()
        {
          current = current.next;
          return current;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          throw new UnsupportedOperationException();
        } // remove
      }; // new Iterator<V>
  } // nodeIterator()

  // +---------------+---------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * Nodes in a linked dictionary.
   */
  class Node
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
     * The next node.
     */
    Node next;

    // +--------------+----------------------------------------------------
    // | Constructors |
    // +--------------+

    /**
     * Create a new key/value pair with no successor.
     */
    public Node(K key, V value)
    {
      this(key, value, null);
    } // Node(K,V)

    /**
     * Create a new key/value pair with a designated successor.
     */
    public Node(K key, V value, Node next)
    {
      this.key = key;
      this.value = value;
      this.next = next;
    } // Node(K,V,Node<K,V>)

    // +---------+---------------------------------------------------------
    // | Methods |
    // +---------+

    /**
     * A simple string representation.
     */
    public String toString()
    {
      return "Node(" + this.key + "," + this.value + ")";
    } // toString()
  } // Node   
} // AssociationList<K,V>

