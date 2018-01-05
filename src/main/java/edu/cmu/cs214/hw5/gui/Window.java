package edu.cmu.cs214.hw5.gui;

import javax.swing.JPanel;

/**
 * Window section interface
 * 
 * @author Hizal
 *
 */
public interface Window {
	/**
	 * adds components to a JPanel
	 * 
	 * @return a JPanel
	 */
	JPanel initComponents();

	/**
	 * draws or adds the given JPanel to the given frame
	 * 
	 * @param panel
	 *            JPanel to draw
	 */
	void drawPanel(JPanel panel);

	/**
	 * refreshes any items or panels in the window panel that needs refreshing
	 */
	void refresh();
}
