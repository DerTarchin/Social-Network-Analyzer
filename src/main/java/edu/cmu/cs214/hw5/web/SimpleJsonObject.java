package edu.cmu.cs214.hw5.web;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A SimpleJsonObject that does error checking around the org.json.JSONObject
 * class.
 * 
 * @author JordanBrown
 *
 */
public class SimpleJsonObject {
  private JSONObject jsonObj;

  /**
   * Constructs a SimpleJsonObject from a string of json.
   * 
   * @param json
   *          The json to construct a SimpleJsonObject from.
   */
  public SimpleJsonObject(String json) {
    this.jsonObj = null;
    try {
      this.jsonObj = new JSONObject(json);
    } catch (JSONException e) {
      System.out.println(e.getLocalizedMessage());
    }
  }

  /**
   * Constructs a SimpleJsonObject from a JSONObject.
   * 
   * @param jsonObj
   *          the org.json.JSONObject.
   */
  public SimpleJsonObject(JSONObject jsonObj) {
    this.jsonObj = jsonObj;
  }

  /**
   * Gets the Json object with the field name as a SimpleJsonObject.
   * 
   * @param field
   *          Field name for the Json object.
   * @return The SimpleJsonObject representation.
   */
  public SimpleJsonObject getJsonObject(String field) {
    try {
      return new SimpleJsonObject(this.jsonObj.getJSONObject(field));
    } catch (JSONException e) {
      System.out.println("Invalid field: " + field);
    }
    return null;
  }

  /**
   * Gets the string from the Json object by the field name.
   * 
   * @param field
   *          The field to get the string from.
   * @return The string for the field key.
   */
  public String getString(String field) {
    try {
      return this.jsonObj.getString(field);
    } catch (JSONException e) {
      System.out.println("Invalid field: " + field);
    }
    return null;
  }

  /**
   * Gets the int from the Json object by the name of the field.
   * 
   * @param field
   *          The name of the field.
   * @return The int at the field.
   */
  public int getInt(String field) {
    try {
      return this.jsonObj.getInt(field);
    } catch (JSONException e) {
      System.out.println("Invalid field: " + field);
    }

    return 0;
  }

  /**
   * Gets the double from the Json object by the name of the field.
   * 
   * @param field
   *          Name of the field.
   * @return double at the field.
   */
  public double getDouble(String field) {
    try {
      return this.jsonObj.getDouble(field);
    } catch (JSONException e) {
      System.out.println("Invalid field: " + field);
    }

    return 0.0;
  }

  /**
   * Gets the Json array at the field name as a SimpleJsonArray
   * 
   * @param field
   *          Name of the field.
   * @return The SimpleJsonArray at the field.
   */
  public SimpleJsonArray getJsonArray(String field) {
    try {
      return new SimpleJsonArray(this.jsonObj.getJSONArray(field).toString());
    } catch (JSONException e) {
      System.out.println("Invalid field: " + field);
    }

    return null;
  }

  /**
   * Gets the string represented in the Json Object. Note that this String may
   * not equal your input string, but if passed to another SimpleJsonObject it
   * will be equivalent.
   * 
   * @return The object as a string.
   */
  @Override
  public String toString() {
    return this.jsonObj.toString();
  }
}
