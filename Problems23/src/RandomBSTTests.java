import static org.junit.Assert.*;

import org.junit.Test;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

/**
 * Randomized tests for binary search trees.
 *
 * @author Samuel A. Rebelsky
 * @author Randy
 */
public class RandomBSTTests
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+ 

  /*
     This code is closely based on code by Samuel A. Rebelsky from
     exam 2 of the fall 2013 section of Grinnell's CSC 207.  In 
     particular, most of the testing code comes from SamTests.java from
     problem 3 of that exam.  Reporting on errors has been improved
     slightly, and the systematic tests have been removed.
   */

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The number of operations we do in each iteration of the randomized test.
   */
  final int NUMOPS = 500;

  /**
   * The number of iterations we do in the randomized test.
   */
  final int ITERATIONS = 20;

  // +-------+-----------------------------------------------------------
  // | Tests |
  // +-------+

  /**
   * Conduct a whole bunch of unpredictable tests. A strength of this approach
   * is that we have a bunch of tests. A weakness is that when something
   * fails, we don't necessarily know what failed.
   */
  @Test
  public void randomTest()
    throws Exception
  {
    // The words we'll put in the dictionary. And yes, there are
    // intentionally some missing first letters.
    String[] words =
        { "aardvark", "anteater", "antelope", "bear", "bison", "buffalo",
         "chinchilla", "cat", "dingo", "elpehant", "eel", "flying squirrel",
         "fox", "goat", "gnu", "goose", "hippo", "horse", "iguana",
         "jackalope", "kestrel", "llama", "moose", "mongoose", "nilgai",
         "orangutan", "opossum", "red fox", "snake", "tarantula", "tiger",
         "vicuna", "vulture", "wombat", "yak", "zebra", "zorilla" };
    int wordslen = words.length;

    // The keys we use
    String keys = "abcdefghijklmnopqrstuvwxyz";
    int keyslen = keys.length();

    // A helpful array list
    HashSet<Character> activeKeys = new HashSet<Character>();

    Random rand = new Random();

    for (int i = 0; i < ITERATIONS; i++)
      {
        // Create a new tree.
        BST<Character, String> dict =
            new BST<Character, String>(new Comparator<Character>()
              {
                public int compare(Character left, Character right)
                {
                  return left.compareTo(right);
                } // compare(Character, Character)
              });

        // Create a list of operations so that we can report
        // on the operations that lead to an error.
        ArrayList<String> ops = new ArrayList<String>();

        // The current operation.
        String operation;

        // Set up our set of active keys
        activeKeys.clear();

        // Add/delete lots of elements, checking the status of the
        // BST after each operation.
        for (int o = 0; o < NUMOPS; o++)
          {
            // We delete 1/4 of the time
            if (rand.nextInt(4) == 0)
              {
                // Get the key
                char key = keys.charAt(rand.nextInt(keyslen));
                // Note that we're removing
                operation = "remove(" + key + ")";
                ops.add(operation);
                // Do the actual work
                dict.remove(key);
                // Also remove it from our set
                activeKeys.remove(key);
                // Is it gone?
                if (dict.containsKey(key))
                  {
                    reportError(dict, ops,
                                "After deletion, dictionary still contains key "
                                    + key);
                  } // if the key is still there
              }
            else
              {
                String value = words[rand.nextInt(wordslen)];
                char key = value.charAt(0);
                operation = "add(" + key + "," + value + ")";
                ops.add(operation);
                activeKeys.add(key);
                dict.set(key, value);
                // Is it there?
                if (!dict.containsKey(key))
                  {
                    reportError(dict, ops,
                                "After insertion, dictionary lacks key "
                                    + key);
                  } // if the key is not there
                // Does it contain the right value?
                if (!dict.get(key).equals(value))
                  {
                    reportError(dict, ops, "dict[" + key + "] != " + value);
                  } // if the key has the wrong the value
              } // add case

            // Are all the active keys still active?
            for (Character active : activeKeys)
              {
                if (!dict.containsKey(active))
                  {
                    reportError(dict, ops,
                                "After " + operation
                                    + ", dictionary no longer contains key "
                                    + active);
                  } // if
              } // for
          } // for (o)
      } // for (i)
  } // randomTest()

  // +-------+-----------------------------------------------------------
  // | Utils |
  // +-------+

  /**
   * Report an error, giving some information about what led to the failure.
   */
  static void reportError(BST<Character, String> dict, ArrayList<String> ops,
                          String message)
  {
    System.err.println(ops);
    dict.dump(new PrintWriter(System.err, true));
    fail(message);
  } // reportError

} // class RandomBSTRemoveTests
