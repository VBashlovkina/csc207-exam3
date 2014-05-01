/**
 * Simple factories for dictionaries.  Intended primarily to support
 * unit tests of hash tables.
 *
 * @author Samuel A. Rebelsky
 */
public interface DictionaryFactory<K, V>
{
  /**
   * Build a new dictionary with a suggested initial capacity.  Note that
   * in some cases, that suggestion may be ignored.
   */
  public Dictionary<K,V> build(int capacity);
} // interface DictionaryFactory<K, V>
