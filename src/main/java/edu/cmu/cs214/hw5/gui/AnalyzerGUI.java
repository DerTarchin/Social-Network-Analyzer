package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.cmu.cs214.hw5.core.Framework;

/**
 * main GUI initializer class
 * 
 * @author Hizal
 *
 */
public class AnalyzerGUI {
	private Framework framework;
	private GUIResources resources;
	private JFrame frame;

	/**
	 * assigns framework to GUI
	 * 
	 * @param framework
	 *            framework
	 */
	public AnalyzerGUI(Framework framework) {
		this.framework = framework;
		initGUIResources();
	}

	/**
	 * display the GUI
	 */
	public void display() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				initWindow();
				frame.setVisible(true);
			}
		}).start();
	}

	/**
	 * initialize the GUIResources
	 */
	private void initGUIResources() {
		resources = new GUIResources();
		resources.setFramework(framework);
	}

	/**
	 * initializes GUI window
	 */
	private void initWindow() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(resources.getSize().width, resources.getSize().height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		JPanel window = new JPanel(new BorderLayout());
		window.setBackground(resources.getBackground());
		window.add(new TopPanel(resources), BorderLayout.NORTH);
		window.add(new DataPluginsPanel(resources), BorderLayout.WEST);
		window.add(new AnalysisPluginsPanel(resources), BorderLayout.EAST);
		window.setVisible(true);
		window.validate();
		window.repaint();
		frame.add(window);
		frame.validate();
		frame.setVisible(false);
	}
}
