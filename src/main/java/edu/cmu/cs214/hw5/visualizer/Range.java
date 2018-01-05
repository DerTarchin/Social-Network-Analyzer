package edu.cmu.cs214.hw5.visualizer;

/**
 * Range object has a minimum and maximum field
 * 
 * @author Hizal
 *
 */
public class Range {
	private String min;
	private String max;

	/**
	 * initializes a range with default values
	 */
	public Range() {
		min = "";
		max = "";
	}

	/**
	 * initializes a range with given values
	 * 
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 */
	public Range(String min, String max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * sets the minimum field value
	 * 
	 * @param min
	 *            minimum
	 */
	public void setMin(String min) {
		this.min = min;
	}

	/**
	 * sets the maximum field value
	 * 
	 * @param max
	 *            maximum
	 */
	public void setMax(String max) {
		this.max = max;
	}

	/**
	 * sets a range of minimum and maximum values
	 * 
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 */
	public void setRange(String min, String max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * get the minimum field value
	 * @return minimum value
	 */
	public String getMin() {
		return min;
	}

	/**
	 * get the maximum field value
	 * @return maximum value
	 */
	public String getMax() {
		return max;
	}

	@Override
	public String toString() {
		return min + " to " + max;
	}
}
