import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for our association list class.
 *
 * @author Samuel A. Rebelsky
 */
public class AssociationListTest
{
  /**
   * Our handy dandy factory.
   */
  public DictionaryFactory<Character,String> alFactory = 
    new DictionaryFactory<Character,String>()
      {
        public Dictionary<Character, String> build(int capacity)
        {
          return new AssociationList<Character, String>();
        } // build(int)
      }; // new DictionaryFactory

  /**
   * Random tests are always a good starting point.
   */
  @Test
  public void randomTest()
    throws Exception
  {
    DictionaryIteratorTests.randomTest(alFactory);
  } // test

} // AssociationListTest
