package edu.cmu.cs214.hw5.visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Visualizer
 * 
 * @author Hizal
 *
 */
public class Visualizer extends JPanel {
	private String pluginName;
	private GUIResources resources;
	private Dimension maxSize;
	private ArrayList<ControlPanel> controls = new ArrayList<ControlPanel>();
	private ArrayList<Chart> charts = new ArrayList<Chart>();
	private Map<Chart, SimpleSwingBrowser> chartWindows = new HashMap<Chart, SimpleSwingBrowser>();
	private JScrollPane controlsWindow;
	private JPanel controlPanels;
	private JPanel chartsContainer;
	private Random rand = new Random();

	/**
	 * initializes visualizer
	 */
	public Visualizer() {
		pluginName = null;
		resources = new GUIResources();
		resources.setAnalysisPaneSize(getMaxSize());
		chartsContainer = null;
	}

	/**
	 * sets the title for the visualizer
	 * 
	 * @param name
	 *            title
	 */
	public void setTitle(String name) {
		pluginName = name + " ";
	}

	/**
	 * add a control panel
	 * 
	 * @param controlPanel
	 *            control panel
	 */
	public void addControlPanel(ControlPanel controlPanel) {
		controls.add(controlPanel);
	}

	/**
	 * add a control panel to the given index
	 * 
	 * @param index
	 *            index
	 * @param controlPanel
	 *            control panel
	 */
	public void addControlPanel(int index, ControlPanel controlPanel) {
		controls.add(index, controlPanel);
	}

	/**
	 * get the control panel at the given index
	 * 
	 * @param index
	 *            index
	 * @return control panel
	 */
	public ControlPanel getControlPanel(int index) {
		if (index < 0 || index >= controls.size())
			return null;
		return controls.get(index);
	}

	/**
	 * remove the control panel at the given index
	 * 
	 * @param index
	 *            index
	 */
	public void removeControlPanel(int index) {
		if (index < 0 || index >= controls.size())
			return;
		controls.remove(index);
	}

	/**
	 * remove the specified control panel
	 * 
	 * @param controlPanel
	 *            control panel
	 */
	public void removeControlPanel(ControlPanel controlPanel) {
		if (!controls.contains(controlPanel))
			return;
		controls.remove(controlPanel);
	}

	/**
	 * gets the number of control panels
	 * 
	 * @return int
	 */
	public int getNumberOfControlPanels() {
		return controls.size();
	}

	/**
	 * adds a chart, ignores any chart added after 4 charts exist
	 * 
	 * @param c
	 *            chart
	 */
	public void addChart(Chart c) {
		if (charts.size() != 4)
			charts.add(c);
	}

	/**
	 * adds a chart to the given index, ignores any chart after the 4th index
	 * 
	 * @param index
	 *            index
	 * @param c
	 *            chart
	 */
	public void addChart(int index, Chart c) {
		charts.add(index, c);
		while (charts.size() >= 4)
			charts.remove(charts.size() - 1);
	}

	/**
	 * gets the chart at the given index
	 * 
	 * @param index
	 *            index
	 * @return chart
	 */
	public Chart getChart(int index) {
		if (index < 0 || index >= charts.size())
			return null;
		return charts.get(index);
	}

	/**
	 * remove the specified chart
	 * 
	 * @param c
	 *            chart
	 */
	public void removeChart(Chart c) {
		if (!charts.contains(c))
			return;
		charts.remove(c);
	}

	/**
	 * remove the chart from at the given index
	 * 
	 * @param index
	 *            index
	 */
	public void removeChart(int index) {
		if (index < 0 || index >= charts.size())
			return;
		charts.remove(index);
	}

	/**
	 * gets the number of charts
	 * 
	 * @return int
	 */
	public int getNumberOfCharts() {
		return charts.size();
	}

	/**
	 * display the visualizer (for init purposes only!)
	 */
	public void display() {
		maxSize = resources.getAnalysisPaneSize();
		setLayout(new BorderLayout());
		setOpaque(false);
		setPreferredSize(maxSize);
		setMaximumSize(getPreferredSize());
		add(drawPluginName(), BorderLayout.NORTH);
		if (controls.size() > 0)
			add(drawControlPane(), BorderLayout.WEST);
		add(drawChartPane(), BorderLayout.CENTER);
	}

	/**
	 * update the visualizer displayed
	 */
	public void refresh() {
		refreshCharts();
		if (controlPanels != null) {
			controlPanels.removeAll();
			controlPanels.add(displayControlPanels(), BorderLayout.CENTER);
			controlPanels.validate();
			controlPanels.repaint();
			controlsWindow.validate();
		}
	}

	/**
	 * draw the plugin name bar
	 * 
	 * @return jpanel
	 */
	private JPanel drawPluginName() {
		String titleName;
		if (pluginName == null)
			titleName = "Untitled Plugin";
		else
			titleName = pluginName;
		int textWidth = resources.getSize().width - resources.getSize().width
				/ 4;
		textWidth -= textWidth / 5;
		titleName = Util.clipText(titleName,
				resources.getFont("semibold", 30f), textWidth);
		JLabel title = new JLabel(titleName);
		title.setFont(resources.getFont("semibold", 30f));
		title.setForeground(resources.getPalette(1));

		JLabel titleSpace = new JLabel(" ");
		titleSpace.setFont(resources.getFont("bold", 20f));

		JPanel titleWrapper = new JPanel();
		titleWrapper.setOpaque(false);
		titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.Y_AXIS));
		titleWrapper.add(title);
		titleWrapper.add(titleSpace);
		titleWrapper.validate();
		titleWrapper.setPreferredSize(new Dimension(titleWrapper
				.getPreferredSize().width,
				titleWrapper.getPreferredSize().height
						- (titleWrapper.getPreferredSize().height / 8)));
		titleWrapper.setMaximumSize(titleWrapper.getPreferredSize());

		return titleWrapper;
	}

	/**
	 * get maximum size for the JPanel
	 * 
	 * @return Dimension
	 */
	private Dimension getMaxSize() {
		int width = resources.getSize().width;
		int height = resources.getSize().height;
		int maxWidth = (width - width / 4) - (width - width / 4) / 25;
		int[] heightSizes = { height / 16, (height / 50) * 2, height / 18,
				getSpaceHeight() };
		int maxHeight = resources.getSize().height;
		for (int i = 0; i < heightSizes.length; i++)
			maxHeight -= heightSizes[i];
		return new Dimension(maxWidth, maxHeight);
	}

	/**
	 * draw the control pane
	 * 
	 * @return jpanel
	 */
	private JPanel drawControlPane() {
		// add each data plugin to list of installed plugins to be
		// displayed
		controlPanels = new JPanel(new BorderLayout());
		controlPanels.setOpaque(false);
		controlPanels.add(displayControlPanels(), BorderLayout.CENTER);

		controlsWindow = new JScrollPane(controlPanels,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		controlsWindow.setComponentZOrder(
				controlsWindow.getVerticalScrollBar(), 0);
		controlsWindow.setComponentZOrder(controlsWindow.getViewport(), 1);
		controlsWindow.getVerticalScrollBar().setOpaque(false);
		controlsWindow.getViewport().setOpaque(false);
		controlsWindow.setOpaque(false);
		controlsWindow.setBorder(null);
		Util.formatScrollBar(controlsWindow, resources);

		JPanel footer = new JPanel();
		footer.setOpaque(false);
		footer.setPreferredSize(new Dimension(0,
				resources.getSize().height / 18));

		JPanel content = new JPanel(new BorderLayout());
		content.setOpaque(false);
		content.add(controlsWindow, BorderLayout.CENTER);
		content.add(footer, BorderLayout.SOUTH);
		content.validate();
		content.setMaximumSize(content.getPreferredSize());

		int width = resources.getSize().width / 4;
		int spacing = width / 10 - 1;
		width -= spacing;
		// add spacing to the right...
		JPanel eastSpace = new JPanel();
		eastSpace.setPreferredSize(new Dimension(spacing, 0));
		eastSpace.setOpaque(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(width, 0));
		panel.add(content, BorderLayout.CENTER);
		panel.add(eastSpace, BorderLayout.EAST);
		panel.validate();
		panel.setMaximumSize(panel.getPreferredSize());
		return panel;
	}

	/**
	 * draw the individual control panels
	 * 
	 * @return jpanel
	 */
	private JPanel displayControlPanels() {
		// create plugins window
		JPanel pane = new JPanel();
		pane.setOpaque(false);
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		int amount = controls.size();
		// add each plugin
		for (int i = 0; i < amount; i++) {
			if (controls.get(i).getNumberOfControls() > 0) {
				JPanel controlPanel = new JPanel();
				controlPanel.setOpaque(false);
				controlPanel.setLayout(new BoxLayout(controlPanel,
						BoxLayout.Y_AXIS));

				// control panel name
				int textWidth = resources.getSize().width / 4;
				textWidth -= textWidth / 5;
				String panelName = Util.clipText(controls.get(i).getLabel(),
						resources.getFont("semibold", 20f), textWidth);
				JLabel name = new JLabel(panelName);
				name.setFont(resources.getFont("semibold", 20f));
				name.setForeground(resources.getPalette(0));

				JPanel nameWrapper = new JPanel(new BorderLayout());
				nameWrapper.setBackground(Color.WHITE);
				nameWrapper.setOpaque(false);
				nameWrapper.add(name, BorderLayout.CENTER);
				nameWrapper.validate();
				nameWrapper.setMaximumSize(new Dimension(
						resources.getSize().width, nameWrapper
								.getPreferredSize().height));
				controlPanel.add(nameWrapper);

				// add control panel controls
				for (Control c : controls.get(i).getControls()) {
					JPanel controlWrapper = new JPanel(new FlowLayout(
							FlowLayout.LEFT, 0, 0));
					controlWrapper.setOpaque(false);
					controlWrapper.add(c.draw(resources));
					controlWrapper.validate();
					controlWrapper.setMaximumSize(controlWrapper
							.getPreferredSize());
					controlPanel.add(controlWrapper);
				}
				pane.add(controlPanel);
				JLabel footer = new JLabel(" ");
				footer.setFont(resources.getFont("regular", 15f));
				if (i != amount - 1)
					pane.add(footer);
			}
		}
		pane.validate();
		pane.setMaximumSize(pane.getPreferredSize());
		return pane;
	}

	/**
	 * gets the size for a chart
	 * 
	 * @param index
	 *            index of the chart in the list of charts
	 * @return dimension
	 */
	private Dimension getChartFrame(int index) {
		int chartNum = index + 1;
		// titlebar size
		JLabel title = new JLabel(" ");
		title.setFont(resources.getFont("semibold", 30f));

		JLabel titleSpace = new JLabel(" ");
		titleSpace.setFont(resources.getFont("bold", 20f));

		int titleHeight = title.getPreferredSize().height
				+ titleSpace.getPreferredSize().height;

		// frame
		int width = 0;
		if (controls.size() > 0)
			width = maxSize.width - resources.getSize().width / 4
					+ resources.getSize().width / 48;
		else
			width = maxSize.width;
		int height = maxSize.height - titleHeight - resources.getSize().height
				/ 30;
		int border = height / 6 + height / 55;
		if (charts.size() > 1) {
			height /= 2;
			if (chartNum != 3 && charts.size() > 2)
				width = width / 2 - width / 10;
			else if (chartNum == 3 && charts.size() > 3)
				width = width / 2 - width / 10;
		} else
			width -= width / 38;
		return new Dimension(width, height - border);
	}

	/**
	 * sets the constraints for the chart panel
	 * 
	 * @param index
	 *            index
	 * @return gridbagconstraint
	 */
	private GridBagConstraints getGridBagConstraints(int index) {
		int chartNum = index + 1;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		if (chartNum == 2 && charts.size() == 2)
			gbc.gridy++;
		else if (chartNum == 2 && charts.size() > 2)
			gbc.gridx++;
		if (chartNum == 3 && charts.size() == 3) {
			gbc.gridy++;
			gbc.gridwidth = 2;
		} else if (chartNum > 2)
			gbc.gridy++;
		if (chartNum == 4)
			gbc.gridx++;
		return gbc;
	}

	/**
	 * updates the charts
	 */
	private void refreshCharts() {
		for (int i = 0; i < charts.size(); i++) {
			Chart c = charts.get(i);
			SimpleSwingBrowser browser = chartWindows.get(c);
			if (browser != null) {
				String fileName = "chart_" + charts.indexOf(c) + "_"
						+ rand.nextInt(Integer.MAX_VALUE);
				String file = c.getChartFile(fileName, resources,
						getChartFrame(i));
				String path = Util.getURLFromFile(file);
				browser.loadURL(path);
			} else if (browser == null) {
				browser = new SimpleSwingBrowser();
				String fileName = "chart_" + charts.indexOf(c) + "_"
						+ rand.nextInt(Integer.MAX_VALUE);
				String file = c.getChartFile(fileName, resources,
						getChartFrame(i));
				String path = Util.getURLFromFile(file);
				browser.loadURL(path);
				chartWindows.put(c, browser);

				JLabel title = new JLabel(c.getTitle());
				title.setFont(resources.getFont("bold", 20f));
				title.setForeground(resources.getPalette(1));
				title.setHorizontalAlignment(JLabel.CENTER);

				JPanel section = new JPanel(new BorderLayout());
				section.setOpaque(false);
				section.add(title, BorderLayout.NORTH);
				section.add(browser, BorderLayout.CENTER);

				GridBagConstraints gbc = getGridBagConstraints(i);
				chartsContainer.add(section, gbc);
			}
		}
	}

	/**
	 * draws the charts (initialization only)
	 * 
	 * @return jpanel
	 */
	private JPanel drawChartPane() {
		JPanel chartPane = new JPanel(new BorderLayout());
		chartPane.setBackground(Color.PINK);
		chartPane.setOpaque(false);
		chartsContainer = new JPanel(new GridBagLayout());
		chartsContainer.setOpaque(false);

		for (int i = 0; i < charts.size(); i++) {
			// if (i > 3)
			// break;
			Chart c = charts.get(i);
			SimpleSwingBrowser b = chartWindows.get(c);
			if (b == null) {
				SimpleSwingBrowser browser = new SimpleSwingBrowser();
				// adding unique random number to end to prevent duplicate
				// files. collision chances are very very very low
				String fileName = "chart_" + charts.indexOf(c) + "_"
						+ rand.nextInt(Integer.MAX_VALUE);
				String file = c.getChartFile(fileName, resources,
						getChartFrame(i));
				String path = Util.getURLFromFile(file);
				browser.loadURL(path);
				chartWindows.put(c, browser);

				JLabel title = new JLabel(c.getTitle());
				title.setFont(resources.getFont("bold", 20f));
				title.setForeground(resources.getPalette(1));
				title.setHorizontalAlignment(JLabel.CENTER);

				JPanel section = new JPanel(new BorderLayout());
				section.setOpaque(false);
				section.add(title, BorderLayout.NORTH);
				section.add(browser, BorderLayout.CENTER);

				GridBagConstraints gbc = getGridBagConstraints(i);
				chartsContainer.add(section, gbc);
			}
		}

		JPanel footer = new JPanel();
		footer.setOpaque(false);
		footer.setPreferredSize(new Dimension(footer.getPreferredSize().width,
				resources.getSize().height / 18));
		footer.setMaximumSize(footer.getPreferredSize());

		chartPane.add(chartsContainer, BorderLayout.CENTER);
		chartPane.add(footer, BorderLayout.SOUTH);
		return chartPane;
	}

	/**
	 * creates and returns a JLabel with an icon instead of text
	 * 
	 * @param file
	 *            file to draw icon
	 * @param w
	 *            width to scale icon to
	 * @param h
	 *            height to scale icon to
	 * @param opacity
	 *            opacity to draw icon at
	 * @return jlabel with icon
	 */
	private JLabel createJLabelImg(String file, int w, int h, float opacity) {
		String path = resources.resources();
		JLabel label = new JLabel();
		if (file.contains(path))
			file = file.replace(path, "");
		Image image = new ImageIcon(path + file).getImage();
		Dimension scaled = Util.getScaledDimension(
				new Dimension(image.getWidth(this), image.getHeight(this)),
				new Dimension(w, h));
		label.setIcon(Util.fixImage(
				new ImageIcon(Util.getScaledImage(image, scaled.width,
						scaled.height)), label, opacity));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(false);
		return label;
	}

	/**
	 * get height of the space for the title
	 * 
	 * @return height
	 */
	private int getSpaceHeight() {
		JLabel space1 = new JLabel(" ");
		space1.setFont(resources.getFont("bold", 30f));
		JLabel space2 = new JLabel(" ");
		space2.setFont(resources.getFont("bold", 20f));
		JPanel spaceFull = new JPanel();
		spaceFull.setLayout(new BoxLayout(spaceFull, BoxLayout.Y_AXIS));
		spaceFull.add(space1);
		spaceFull.add(space2);
		return spaceFull.getPreferredSize().height;
	}
}
