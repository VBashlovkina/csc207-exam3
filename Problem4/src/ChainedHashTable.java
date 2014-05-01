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
public class ChainedHashTable<K, V>
    implements Dictionary<K,V>, Iterable<V>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
      We store an association list in each cell.  

      We expand the hash table when the load factor is greater than
      LOAD_FACTOR (see constants below).
   */

  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The load factor for expanding the table.
   */
  static final double LOAD_FACTOR = 0.5;

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
   * The array that we use to store the buckets (association lists).
   * We use an array, rather than a vector, because we want to control
   * expansion.  We store objects, rather than association lists
   * because of the wonder of Java generics.
   */
  Object[] buckets;

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
  public ChainedHashTable()
  {
    this(DEFAULT_CAPACITY);
  } // ChainedHashTable()

  /**
   * Create a new hash table with a specified initial capacity.
   */
  public ChainedHashTable(int capacity)
  {
    this.rand = new Random();
    this.capacity = capacity;
    this.clear();
  } // ChainedHashTable(int)

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  /**
   * Determine if the hash table contains a particular key.
   */
  public boolean containsKey(K key)
  {
    AssociationList<K, V> bucket = this.get(this.find(key));
    return (bucket != null) && (bucket.containsKey(key));
  } // containsKey(K)

  /**
   * Dump the hash table.
   */
  public void dump(PrintWriter pen)
  {
    pen.println("DUMPING ...");
    for (int i = 0; i < this.buckets.length; i++)
      {
        AssociationList<K, V> bucket = this.get(i);
        if (bucket != null)
          {
            pen.println("BUCKET " + i);
            bucket.dump(pen);
          } // if the current bucket is not null
      } // for each bucket
    pen.println("... DONE");
  } // dump(PrintWriter)

  /**
   * Get the value for a particular key.
   */
  public V get(K key)
    throws Exception
  {
    AssociationList<K,V> bucket = this.get(this.find(key));
    if (bucket == null)
      {
        throw new Exception("Invalid key: " + key);
      } // if (bucket == null)
    else // if (bucket != null)
      {
        return bucket.get(key);
      } // if (bucket != null)
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
    this.buckets = new Object[this.capacity];
    this.size = 0;
  } // clear()

  /**
   * Remove a key/value pair.
   */
  public void remove(K key)
  {
    AssociationList<K,V> bucket = this.get(this.find(key));
    if (bucket != null)
      {
        bucket.remove(key);
      } // if (bucket != null)
  } // remove(K)

  /**
   * Set a value.
   */
  public void set(K key, V value)
  {
    // If there are too many entries, expand the table.
    if (this.size > (this.buckets.length * LOAD_FACTOR))
      {
        expand();
      } // if there are too many entries
    // Find out where the key belongs.
    int index = this.find(key);
    // Create a new association list, if necessary.
    if (buckets[index] == null)
      {
        this.buckets[index]= new AssociationList<K,V>();
      } // if (buckets[index] == null)
    // Add the entry.
    AssociationList<K,V> bucket = this.get(index);
    int oldsize = bucket.size;
    bucket.set(key, value);
    // Update the size
    this.size += bucket.size - oldsize;
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
          return ChainedHashTable.this.keysIterator();
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
    Object[] old = this.buckets;

    // Figure out the capacity of the new table, making it somewhat
    // unpredictable.
    int newCapacity = 2 * this.buckets.length + rand.nextInt(10);

    // Create a new table of that capacity.
    this.buckets = new Object[newCapacity];

    // Reset the size since we'll be adding elements in a moment.
    this.size = 0;

    // Move all the values from the old table to their appropriate
    // location in the new table.
    for (int i = 0; i < old.length; i++)
      {
        AssociationList<K,V> bucket = (AssociationList<K,V>) old[i];
        if (bucket != null)
          {
            AssociationList<K,V>.Node current = bucket.front.next;
            while (current != null)
              {
                this.set(current.key, current.value);
                current = current.next;
              } // while
          } // if (bucket != null)
      } // for
  } // expand()

  /**
   * Find the index of the entry with a given key.  If there is no such
   * entry, return the index of an entry we can use to store that key.
   */
  int find(K key)
  {
    return Math.abs(key.hashCode() % this.buckets.length);
  } // find(K)

  /**
   * Get the ith bucket.  Included mostly so that the unchecked conversions 
   * to association lists are all in one place.
   */
  @SuppressWarnings("unchecked")
  AssociationList<K,V> get(int i)
  {
    return (AssociationList<K,V>) buckets[i];
  } // get (int)

} // class ChainedHashTable<K,V>

