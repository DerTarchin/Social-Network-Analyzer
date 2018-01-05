package edu.cmu.cs214.hw5.web;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Assumes input to al lthe objects are correct. If they arent, then an
 * exception will be thrown and the console will get an error message.
 * 
 * @author JordanBrown
 *
 */
public class SimpleJsonTests {
  private final String simpleObj1 = "{\"field\":\"value\"}";
  private final String simpleObj2 = "{\"field2\":2}";
  private final String simpleArr = "[" + simpleObj1 + "," + simpleObj2 + "]";
  private final String objWithArr = "{\"arr\":" + simpleArr + "}";
  private final String objWithObj = "{\"obj\":" + simpleObj1 + "}";
  private final String objWithDouble = "{\"double\":4.22}";
  private final String arrWithArrs = "[" + simpleArr + "," + simpleArr + "]";

  /**
   * Tests that the simple json works as expected.
   */
  @Test
  public void testSimpleJsonObj() {
    SimpleJsonObject simple = new SimpleJsonObject(simpleObj1);
    assertTrue(simple.getString("field").equals("value"));
  }

  /**
   * Tests that the simple array works as expected.
   */
  @Test
  public void testSimpleArr() {
    SimpleJsonArray simple = new SimpleJsonArray(simpleArr);
    assertTrue(simple.getJsonObject(0).getString("field").equals("value"));
    assertTrue(simple.getJsonObject(1).getInt("field2") == 2);
  }

  /**
   * Tests that arrays can be stored as fields.
   */
  @Test
  public void testArrayField() {
    SimpleJsonObject simple = new SimpleJsonObject(objWithArr);
    assertTrue(simple.getJsonArray("arr").getJsonObject(0).getString("field").equals("value"));
    assertTrue(simple.getJsonArray("arr").size() == 2);
  }

  /**
   * Tests that the to string method works as expected.
   */
  @Test
  public void testToString() {
    SimpleJsonObject simple = new SimpleJsonObject(objWithArr);
    assertTrue(simple.toString().equals(objWithArr));
  }

  /**
   * tests that SimpleJsonArrays are converted to String arrays properly.
   */
  @Test
  public void testAsStringArray() {
    SimpleJsonArray simple = new SimpleJsonArray(simpleArr);
    assertTrue(simple.asStringArray().size() == 2);
    assertTrue(simple.asStringArray().get(0).equals(simpleObj1));
  }

  /**
   * Tests that Json objects can be nested.
   */
  @Test
  public void testObjWithObj() {
    SimpleJsonObject simple = new SimpleJsonObject(objWithObj);
    assertTrue(simple.getJsonObject("obj").getString("field").equals("value"));
  }

  /**
   * Tests that parsing doubles works as expected.
   */
  @Test
  public void testObjWithDouble() {
    SimpleJsonObject simple = new SimpleJsonObject(objWithDouble);
    assertTrue(simple.getDouble("double") == 4.22);
  }

  /**
   * Tests that nested arrays work as expected.
   */
  @Test
  public void testNestedArray() {
    SimpleJsonArray simple = new SimpleJsonArray(arrWithArrs);
    assertTrue(simple.getJsonArray(0).getJsonObject(1).getInt("field2") == 2);
  }
}
