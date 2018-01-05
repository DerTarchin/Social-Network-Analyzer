package edu.cmu.cs214.hw5.visualizer;

/**
 * DataPoint stores data information
 * 
 * @author Hizal
 *
 */
public class DataPoint {
	private String label;
	private double value;
	private String group;

	/**
	 * initializes the datapoint's default parameters
	 */
	public DataPoint() {
		label = "";
		value = 0;
		group = "";
	}

	/**
	 * initializes the datapoint with the given parameters
	 * 
	 * @param label
	 *            label
	 * @param group
	 *            group name
	 * @param value
	 *            value
	 */
	public DataPoint(String label, String group, double value) {
		if (label != null)
			this.label = label;
		else
			this.label = "";
		this.value = value;
		if (group != null)
			this.group = group;
		else
			this.group = "";
	}

	/**
	 * set the label
	 * 
	 * @param label
	 *            label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * get the label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * set the group name
	 * 
	 * @param group
	 *            group name
	 */
	public void setGroup(String group) {
		if (group != null)
			this.group = group;
	}

	/**
	 * get the group name
	 * 
	 * @return group name
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * set the value
	 * 
	 * @param value
	 *            value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * get the value
	 * 
	 * @return value
	 */
	public double getValue() {
		return value;
	}

	// TODO add equals
}
