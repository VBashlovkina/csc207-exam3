import java.io.PrintWriter;

import java.util.Iterator;
import java.util.Random;

/**
 * A simple implementation of hash tables using open addressing (aka
 * probing).
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class OpenHashTable<K, V>
    implements Dictionary<K,V>, Iterable<V>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
      Based closely on the file HashTable.java from the Tao of Java's
      laboratory on hash tables by Samuel A. Rebelsky, available at
         <https://github.com/Grinnell-CSC207/hashtables>
      Changes include: renaming the class, squashing some bugs,
      implementing some unimplemented or partially implemented methods
      (such as find and expand), removing the reporting code, adding
      a constructor that takes an initial capacity, and more.

      We use linear probing to handle collisions.  

      We expand the hash table when the load factor is greater than
      LOAD_FACTOR (see constants below).

      Since some combinations of data and hash function may lead to
      a situation in which we get a surprising relationship between values
      (e.g., all the hash values are 0 mod 32), when expanding the hash
      table, we incorporate a random number.  (Is this likely to make a
      big difference?  Who knows.  But it's likely to be fun.)
   */

  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The load factor for expanding the table.
   */
  static final double LOAD_FACTOR = 0.5;

  /**
   * The offset to use in linear probes.  (We choose a prime because
   * that helps ensure that we cover all of the spaces.)
   */
  static final int PROBE_OFFSET = 17;

  /**
   * The default initial capacity of the hash table.  
   */
  static final int DEFAULT_CAPACITY = 41;

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of values currently stored in the hash table.
   * We use this to determine when to expand the hash table.
   */
  int size = 0;

  /**
   * The capacity of the table.
   */
  int capacity = 0;

  /**
   * The array that we use to store the key/value pairs.  (We
   * use an array, rather than a vector, because we want to
   * control expansion.  We store objects, rather than KVPairs,
   * because of the wonder of Java generics.)
   */
  Object[] pairs;

  /**
   * Our helpful random number generator, used primarily when 
   * expanding the size of the table..
   */
  Random rand;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new hash table with the default initial capacity.
   */
  public OpenHashTable()
  {
    this(DEFAULT_CAPACITY);
  } // OpenHashTable()

  /**
   * Create a new hash table with a specified initial capacity.
   */
  public OpenHashTable(int capacity)
  {
    this.rand = new Random();
    this.capacity = capacity;
    this.clear();
  } // OpenHashTable(int)

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  /**
   * Determine if the hash table contains a particular key.
   */
  public boolean containsKey(K key)
  {
    return (pairs[this.find(key)] != null);
  } // containsKey(K)

  /**
   * Dump the hash table.
   */
  public void dump(PrintWriter pen)
  {
    pen.print("{");
    int printed = 0; // Number of elements printed
    for (int i = 0; i < this.pairs.length; i++)
      {
        KVPair pair = this.get(i);
        if (pair != null)
          {
            pen.print(i + ":" + pair.key + "(" + pair.key.hashCode() + "):"
                      + pair.value);
            if (++printed < this.size)
              {
                pen.print(", ");
              } // if the number printed is less than the size
          } // if the current element is not null
      } // for
    pen.println("}");
  } // dump(PrintWriter)

  /**
   * Get the value for a particular key.
   */
  public V get(K key)
    throws Exception
  {
    int index = this.find(key);
    KVPair pair = this.get(index);
    if (pair == null)
      {
        throw new Exception("Invalid key: " + key);
      } // if (pair == null)
    else // if (pair != null)
      {
        return pair.value;
      } // if (pair != null)
  } // get(K)

  /**
   * Get the size of the dictionary - the number of values stored.
   */
  public int size()
  {
    return this.size;
  } // size()

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  /**
   * Clear the whole table.
   */
  public void clear()
  {
    this.pairs = new Object[this.capacity];
    this.size = 0;
  } // clear()

  /**
   * Remove a key/value pair.
   */
  public void remove(K key)
  {
    // STUB
  } // remove(K)

  /**
   * Set a value.
   */
  public void set(K key, V value)
  {
    // If there are too many entries, expand the table.
    if (this.size > (this.pairs.length * LOAD_FACTOR))
      {
        expand();
      } // if there are too many entries
    // Find out where the key belongs and put the pair there.
    int index = this.find(key);
    if (this.pairs[index] == null)
      {
        ++this.size;
        this.pairs[index] = new KVPair(key, value);
      } // if (this.pairs[index] == null)
    else // if (this.pairs[index] != null)
      {
        this.get(index).value = value;
      } // if (this.pairs[index] != null)
  } // set(K,V)

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  /**
   * Get an iterator for the values.
   */
  public Iterator<V> iterator()
  {
    return new Iterator<V>()
      {
        public boolean hasNext()
        {
          // STUB
          return false;
        } // hasNext()

        public V next()
        {
          // STUB
          return null;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          throw new UnsupportedOperationException();
        } // remove()
      }; // new Iterator<V>
  } // iterator()

  /**
   * Get an iterator for the keys
   */
  public Iterator<K> keysIterator()
  {
    return new Iterator<K>()
      {
        public boolean hasNext()
        {
          // STUB
          return false;
        } // hasNext()

        public K next()
        {
          // STUB
          return null;
        } // next()

        public void remove()
          throws UnsupportedOperationException
        {
          throw new UnsupportedOperationException();
        } // remove()
      }; // new Iterator<K>
  } // keysIterator()

  /**
   * Get an Iterable for the keys.  See explanation in Dictionary.java.
   */
  public Iterable<K> keys()
  {
    return new Iterable<K>()
      {
        public Iterator<K> iterator()
        {
          return OpenHashTable.this.keysIterator();
        } // iterator()
      }; // new Iterable<K>
  } // keys()

  // +---------+---------------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Expand the size of the table.
   */
  @SuppressWarnings("unchecked")
  void expand()
  {
    // Remember the old table.
    Object[] old = this.pairs;

    // Figure out the size of the new table, making it somewhat
    // unpredictable and relatively prime to the probe offset..
    int newSize = 2 * this.pairs.length + rand.nextInt(10);
    if ((newSize % PROBE_OFFSET) == 0)
      newSize += 1;

    // Create a new table of that size.
    this.pairs = new Object[newSize];

    // Move all the values from the old table to their appropriate
    // location in the new table.
    for (int i = 0; i < old.length; i++)
      {
        KVPair pair;
        if ((pair = (KVPair) old[i]) != null)
          {
            // We add directly, rather than calling set, because
            // we don't want to build new pairs.
            this.pairs[this.find(pair.key)] = pair;
          } // if old[i] != null
      } // for
  } // expand()

  /**
   * Find the index of the entry with a given key.  If there is no such
   * entry, return the index of an entry we can use to store that key.
   */
  int find(K key)
  {
    // Note: Since we've ensured that the size of the hash table is
    // relatively prime to the linear probe, and we've ensured that
    // there's always some free space in the table, this loop is
    // guaranteed to terminate.

    int index = Math.abs(key.hashCode() % this.pairs.length);;
    while ((pairs[index] != null) && (!key.equals(this.get(index).key)))
      {
        index = (index + PROBE_OFFSET) % this.pairs.length;
      } // while
    return index;
  } // find(K)

  /**
   * Get the ith element of the table.  Included mostly so that the
   * unchecked conversions to KVPairs is all in one place.
   */
  @SuppressWarnings("unchecked")
  KVPair get(int i)
  {
    return (KVPair) pairs[i];
  } // get (int)
    

  // +---------------+---------------------------------------------------
  // | Inner Classes |
  // +---------------+

  /**
   * An easy way to hold a key/value pair.
   */
  class KVPair
  {
    K key;
    V value;

    KVPair(K key, V value)
    {
      this.key = key;
      this.value = value;
    } // KVPair(K,V)

    public String toString()
    {
      return "KVPair(" + key + "," + value + ")";
    } // toString()
  } // class KVPair

} // class OpenHashTable<K,V>

