package edu.cmu.cs214.hw5.web;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Takes care of all error handling that would've had to be done by using
 * JSONArray directly. The goal of this class is to make the code much cleaner.
 * 
 * @author JordanBrown
 *
 */
public class SimpleJsonArray {
  private JSONArray jsonArr;

  /**
   * Constructs a SimpleJsonArray from a string of JSON.
   * 
   * @param json
   *          The JSON array.
   */
  public SimpleJsonArray(String json) {
    try {
      this.jsonArr = new JSONArray(json);
    } catch (JSONException e) {
      System.out.println("Invalid json array: " + json);
    }
  }

  /**
   * Gets the object at the given index as a SimpleJsonObject.
   * 
   * @param index
   *          Index to fetch from.
   * @return The SimpleJsonObject at the index. Null Otherwise.
   */
  public SimpleJsonObject getJsonObject(int index) {
    try {
      return new SimpleJsonObject(this.jsonArr.get(index).toString());
    } catch (JSONException e) {
      System.out.println("Failed to load object at index: " + index);
    }
    return null;
  }

  /**
   * Gets the object at the given index as a SimpleJsonArray.
   * 
   * @param index
   *          Index to fetch from.
   * @return The SimpleJsonArray at the index. Null otherwise.
   */
  public SimpleJsonArray getJsonArray(int index) {
    try {
      return new SimpleJsonArray(this.jsonArr.get(index).toString());
    } catch (JSONException e) {
      System.out.println("Failed to load object at index: " + index);
    }
    return null;
  }

  /**
   * Returns the amount of SimpleJsonObjects in the array.
   * 
   * @return the size of the array.
   */
  public int size() {
    return jsonArr.length();
  }

  /**
   * Gets the SimpleJsonArray as an array of strings.
   * 
   * @return the String array representation.
   */
  public List<String> asStringArray() {
    List<String> strings = new ArrayList<String>();
    for (int i = 0; i < this.size(); i++) {
      strings.add(this.getJsonObject(i).toString());
    }
    return strings;
  }
}
