import static org.junit.Assert.*;

import org.junit.Test;

import java.io.PrintWriter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * Randomized tests for binary search trees.
 */
public class DictionaryIteratorTests
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+
  
  /*
     The toArray method is based on unit tests from unit tests for
     exam 2 of the 2014S section of Grinnell's CSC 207.  That exam
     is unlikely to be available online.

     Portions of the design of these unit tests come from unit tests
     for binary search trees taken from exam 2 of the 2013F section of
     Grinnell's CSC 207, as updated for problems 2 and 3 of exam 3 of
     the 2014S section.
   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of operations we do in each iteration of the randomized test.
   */
  static final int NUMOPS = 50;

  /**
   * The number of iterations we do in the randomized test.
   */
  static final int ITERATIONS = 50;

  /**
   * The minimum initial capacity of dictionaries.
   */
  static final int MINCAP = 4;

  /**
   * The variation in the initial capacity.
   */
  static final int VARCAP = 10;

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Conduct a whole bunch of unpredictable tests. A strength of this approach
   * is that we have a bunch of tests. A weakness is that when something
   * fails, we don't necessarily know what failed.
   */
  public static void randomTest(DictionaryFactory<Character,String> factory)
    throws Exception
  {
    // The words we'll put in the dictionary. And yes, there are
    // intentionally some duplicate first letters.
    String[] words =
        { "aardvark", "anteater", "antelope", "bear", "bison", "buffalo",
         "chinchilla", "cat", "dingo", "elpehant", "eel", "flying squirrel",
         "fox", "goat", "gnu", "goose", "hippo", "horse", "iguana",
         "jackalope", "kestrel", "llama", "moose", "mongoose", "nilgai",
         "orangutan", "opossum", "platapus", "red fox", "snake", "tarantula", 
         "tiger", "vicuna", "vulture", "wombat", "yak", "zebra", "zorilla" };
   
    Random rand = new Random();

    for (int i = 0; i < ITERATIONS; i++)
      {
        // Create a new dictionary.
        Dictionary<Character, String> dict = 
            factory.build(MINCAP + rand.nextInt(VARCAP));

        // Create a new hashmap.  (Okay, this is a bit of a hack.  We
        // compare our results to those of the built in Java hashmap.)
        HashMap<Character, String> map = new HashMap<Character, String>();

        // Create a list of operations so that we can report
        // on the operations that lead to an error.
        ArrayList<String> ops = new ArrayList<String>();

        // The current operation.
        String operation;

        // Add lots of elements.
        for (int o = 0; o < NUMOPS; o++)
          {
            String value = words[rand.nextInt(words.length)];
            char key = value.charAt(0);
            operation = "add(" + key + "," + value + ")";
            ops.add(operation);
            dict.set(key, value);
            map.put(key, value);
          } // for each operation

        // Make sure that the two dictionaries are the same by comparing
        // their keys and values.
        String[] strings = new String[0];
        Character[] chars = new Character[0];
        String[] dictValues = toArray(dict.iterator(), strings);
        String[] mapValues = toArray(map.values().iterator(), strings);
        Character[] dictKeys = toArray(dict.keys().iterator(), chars);
        Character[] mapKeys = toArray(map.keySet().iterator(), chars);
        Arrays.sort(dictValues);
        Arrays.sort(mapValues);
        Arrays.sort(dictKeys);
        Arrays.sort(mapKeys);
        if (! Arrays.equals(dictValues, mapValues))
          {
            System.err.println("dictValues: " + Arrays.toString(dictValues));
            System.err.println("mapValues:  " + Arrays.toString(mapValues));
            reportError(dict, ops, "Different sets of values!");
          } // if dictValues != mapValues
        if (! Arrays.equals(dictKeys, mapKeys))
          {
            System.err.println("dictKeys: " + Arrays.toString(dictKeys));
            System.err.println("mapKeys:  " + Arrays.toString(mapKeys));
            reportError(dict, ops, "Different sets of keys!");
          } // if dictKeys != mapKeys
      } // for each iteration
  } // randomTest()

  // +-------+-----------------------------------------------------------
  // | Utils |
  // +-------+

  /**
   * Convert an iterator to an array.
   */
  @SuppressWarnings({"unchecked"})
  static <T> T[] toArray(Iterator<T> it, T[] base)
  {
    ArrayList<T> array = new ArrayList<T>();
    while (it.hasNext())
      {
        array.add(it.next());
      } // while
    return (T[]) array.toArray(base);
  } // toArray(Iterator<T>)

  /**
   * Report an error, giving some information about what led to the failure.
   */
  static void reportError(Dictionary<Character, String> dict, 
      ArrayList<String> ops, String message)
  {
    System.err.println(ops);
    dict.dump(new PrintWriter(System.err, true));
    fail(message);
  } // reportError

} // class DictionaryIteratorTests
