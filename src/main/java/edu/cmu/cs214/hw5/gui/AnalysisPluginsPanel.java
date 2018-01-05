package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Section of the window that displays all the analysis plugins, creating a
 * scrolling pane of the provided JPanels for each plugin
 * 
 * @author Hizal
 *
 */
public class AnalysisPluginsPanel extends JPanel implements Window {
	private GUIResources resources;
	private int width;
	private JScrollPane pluginsWindow;
	private JPanel plugins;
	private JPanel pluginsContainer;

	/**
	 * initializes the window, saving the GUIresources
	 * 
	 * @param resources
	 *            GUIResources
	 */
	public AnalysisPluginsPanel(GUIResources resources) {
		this.resources = resources;
		resources.addRefreshablePanel(this);
		width = resources.getSize().width - resources.getSize().width / 4;
		drawPanel(initComponents());
	}

	/**
	 * put together a jpanel of all the components
	 * 
	 * @return JPanel
	 */
	public JPanel initComponents() {
		JLabel title = new JLabel("Analysis");
		title.setFont(resources.getFont("bold", 30f));
		title.setForeground(resources.getPalette(0));

		JLabel titleSpace = new JLabel(" ");
		titleSpace.setFont(resources.getFont("bold", 20f));

		JPanel titleWrapper = new JPanel();
		titleWrapper.setOpaque(false);
		titleWrapper.setLayout(new BoxLayout(titleWrapper, BoxLayout.Y_AXIS));
		titleWrapper.add(title);
		titleWrapper.add(titleSpace);
		titleWrapper.validate();
		titleWrapper.setMaximumSize(titleWrapper.getPreferredSize());

		// add spacing to the right
		JPanel eastSpace = new JPanel();
		eastSpace.setPreferredSize(new Dimension(width / 25, 0));
		eastSpace.setOpaque(false);

		int paneWidth = width - eastSpace.getPreferredSize().width;
		resources.setAnalysisPaneSize(new Dimension(paneWidth, resources
				.getAnalysisPaneSize().height
				- titleWrapper.getPreferredSize().height));

		// add each data plugin to list of installed plugins to be
		// displayed
		plugins = new JPanel(new BorderLayout());
		plugins.setOpaque(false);

		pluginsWindow = new JScrollPane(plugins,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pluginsWindow.setComponentZOrder(pluginsWindow.getVerticalScrollBar(),
				0);
		pluginsWindow.setComponentZOrder(pluginsWindow.getViewport(), 1);
		pluginsWindow.getVerticalScrollBar().setOpaque(false);
		pluginsWindow.getViewport().setOpaque(false);
		pluginsWindow.setOpaque(false);
		pluginsWindow.setBorder(null);
		Util.formatScrollBar(pluginsWindow, resources);

		plugins.add(drawAnalysisPanes(), BorderLayout.CENTER);

		JPanel content = new JPanel(new BorderLayout());
		content.setOpaque(false);
		content.add(titleWrapper, BorderLayout.NORTH);
		content.add(pluginsWindow, BorderLayout.CENTER);
		content.validate();
		content.setMaximumSize(content.getPreferredSize());

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
	 * gets the JPanel containing the scroll pane of analysis plugins
	 * 
	 * @return jpanel of plugins
	 */
	public JPanel drawAnalysisPanes() {
		int maxWidth = resources.getAnalysisPaneSize().width;
		int maxHeight = resources.getAnalysisPaneSize().height;
		// create plugins resources
		pluginsContainer = new JPanel();
		pluginsContainer.setOpaque(false);
		pluginsContainer.setLayout(new BoxLayout(pluginsContainer,
				BoxLayout.Y_AXIS));

		// add each plugin
		for (int i = 0; i < resources.getFramework().getAnalysisPlugins()
				.size(); i++) {
			if (i != 0) {
				JPanel divider = new JPanel();
				divider.setBackground(resources.getPalette(0));
				divider.setPreferredSize(new Dimension(maxWidth,
						maxHeight / 110));
				divider.setMaximumSize(divider.getPreferredSize());
				JPanel dividerWrapper = new JPanel(new FlowLayout(
						FlowLayout.LEFT, 0, 0));
				dividerWrapper.setOpaque(false);
				dividerWrapper.setPreferredSize(new Dimension(maxWidth, divider
						.getPreferredSize().height * 5));
				dividerWrapper
						.setMaximumSize(dividerWrapper.getPreferredSize());
				dividerWrapper.add(divider);
				pluginsContainer.add(dividerWrapper);
			}
			JPanel plugin = resources.getFramework().getJPanelForPlugin(
					resources.getFramework().getAnalysisPlugins().get(i));
			plugin.setBackground(resources.getPalette(i % 5));
			plugin.setOpaque(false);
			plugin.setPreferredSize(resources.getAnalysisPaneSize());
			plugin.setMaximumSize(plugin.getPreferredSize());
			pluginsContainer.add(plugin);
		}
		return pluginsContainer;
	}

	/**
	 * draw window
	 * 
	 * @param analysisPluginsPanel
	 *            analysis plugins to display
	 */
	public void drawPanel(JPanel analysisPluginsPanel) {
		setLayout(new BorderLayout());
		setOpaque(false);
		validate();
		add(analysisPluginsPanel, BorderLayout.CENTER);
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

	@Override
	public void refresh() {
		 plugins.removeAll();
		 plugins.add(drawAnalysisPanes(), BorderLayout.CENTER);
		 plugins.validate();
		 plugins.repaint();
		pluginsWindow.validate();
//		pluginsContainer.remove(0);
	}
}
