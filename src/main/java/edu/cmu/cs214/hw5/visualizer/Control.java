package edu.cmu.cs214.hw5.visualizer;

import javax.swing.JPanel;

import edu.cmu.cs214.hw5.gui.GUIResources;

/**
 * Control abstract
 * 
 * @author Hizal
 *
 * @param <Type>
 */
public abstract class Control<Type> extends JPanel {
	private String fieldName;
	private GUIResources resources;

	/**
	 * sets the label
	 * 
	 * @param fieldName
	 *            label
	 */
	public void setLabel(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * gets the label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return fieldName;
	}

	/**
	 * sets the default value
	 * 
	 * @param value
	 *            value
	 */
	public abstract void setDefaultValue(Type value);

	/**
	 * gets the value
	 * 
	 * @return value
	 */
	public abstract Type getValue();

	/**
	 * displays the control object
	 * 
	 * @param resources
	 *            GUIResources
	 * @return jpanel
	 */
	public JPanel draw(GUIResources resources) {
		this.resources = resources;
		return null;
	}
}
