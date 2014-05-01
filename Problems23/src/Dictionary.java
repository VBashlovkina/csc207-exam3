import java.util.Iterator;

/**
 * Simple dictionaries (aka maps, tables, hashes, associative arrays). We've
 * taken a relatively minimalist approach in this design.
 * 
 * @author Samuel A. Rebelsky
 * @author CSC 207 2013F
 */
public interface Dictionary<K, V>
    extends Iterable<V>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
     This interface is derived from interfaces that appear at various 
     places in The Tao of Java and Grinnell's CSC 207, both by Samuel
     A. Rebelsky.  This particular instance was copied from exam 2
     of the fall 2013 section of CSC 207.
   */

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  /**
   * Get the value associated with a particular key.
   * 
   * @throws Exception
   *             if the key is not in the dictionary.
   */
  public V get(K key)
    throws Exception;

  /**
   * Determine if the dictionary contains a value with a particular key.
   * (Useful in checking the precondition for get.)
   */
  public boolean containsKey(K key);

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  /**
   * Set the value associated with a particular key. If there was previously a
   * value with that key, replaces it.
   */
  public void set(K key, V value);

  /**
   * Remove the value associated with a particular key.  If
   * there are no values associated with the * key, does nothing.
   */
  public void remove(K key);

  /**
   * Remove all of the items from the dictionary.
   */
  public void clear();

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  /**
   * Get an iterable for the values in the dictionary. The values are iterated
   * in no particular order. If a value appears more than once in the
   * dictionary (i.e., if it is associated with more than one key), it appears
   * more than once in the iteration.
   */
  public Iterator<V> iterator();

  /**
   * Get an iterable for the keys in the dictionary. The keys are iterated in
   * no particular order. (We want an iterable so that we can use it with
   * Java's for-each form.)
   */
  public Iterable<K> keys();

  /**
   * Get an iterator for the keys in the dictionary.
   */
  public Iterator<K> keysIterator();

} // Dictionary<K,V>
