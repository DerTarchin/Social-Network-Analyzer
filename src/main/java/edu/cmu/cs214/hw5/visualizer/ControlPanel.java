package edu.cmu.cs214.hw5.visualizer;

import java.util.ArrayList;

/**
 * Control Panel displays a group of Controls
 * 
 * @author Hizal
 *
 */
public class ControlPanel {

	private String panelName;
	private ArrayList<Control> controls = new ArrayList<Control>();

	/**
	 * initializes the control panel
	 */
	public ControlPanel() {
		panelName = null;
	}

	/**
	 * set the label
	 * 
	 * @param label
	 *            label
	 */
	public void setLabel(String label) {
		panelName = label;
	}

	/**
	 * get the label
	 * 
	 * @return label
	 */
	public String getLabel() {
		return panelName;
	}

	/**
	 * adds a Control
	 * 
	 * @param control
	 *            control
	 */
	public void addControl(Control control) {
		controls.add(control);
	}

	/**
	 * adds a control at the given order index
	 * 
	 * @param index
	 *            index
	 * @param control
	 *            control
	 */
	public void addControl(int index, Control control) {
		controls.add(index, control);
	}

	/**
	 * gets the control at the given order index
	 * 
	 * @param index
	 *            index
	 * @return control
	 */
	public Control getControl(int index) {
		if (index < 0 || index >= controls.size())
			return null;
		return controls.get(index);
	}

	/**
	 * removes the given control from the control panel
	 * 
	 * @param control
	 *            control
	 */
	public void removeControl(Control control) {
		if (!controls.contains(control))
			return;
		controls.remove(control);
	}

	/**
	 * removes the control at the given order index
	 * 
	 * @param index
	 *            index
	 */
	public void removeControl(int index) {
		if (index < 0 || index >= controls.size())
			return;
		controls.remove(index);
	}

	/**
	 * gets the number of controls in the panel
	 * 
	 * @return number of controls
	 */
	public int getNumberOfControls() {
		return controls.size();
	}

	/**
	 * gets a list of all controls in the panel
	 * 
	 * @return list of controls
	 */
	public ArrayList<Control> getControls() {
		return controls;
	}
}
