import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for our open hash table class.
 *
 * @author Samuel A. Rebelsky
 */
public class OpenHashTableTest
{
  /**
   * Our handy dandy factory.
   */
  public DictionaryFactory<Character,String> ohtFactory = 
    new DictionaryFactory<Character,String>()
      {
        public Dictionary<Character, String> build(int capacity)
        {
          return new OpenHashTable<Character, String>(capacity);
        } // build(int)
      }; // new DictionaryFactory

  /**
   * Random tests are always a good starting point.
   */
  @Test
  public void randomTest()
    throws Exception
  {
    DictionaryIteratorTests.randomTest(ohtFactory);
  } // test

} // OpenHashTableTest
