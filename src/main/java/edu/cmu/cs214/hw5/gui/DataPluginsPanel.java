package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import edu.cmu.cs214.hw5.visualizer.Control;
import edu.cmu.cs214.hw5.visualizer.TextFieldControl;

/**
 * initializes the window that displays the data plugins in a scroll pane
 * 
 * @author Hizal
 *
 */
public class DataPluginsPanel extends JPanel implements Window {
	private GUIResources resources;
	private int width;

	private JScrollPane pluginsWindow;
	private JPanel plugins;
	private ArrayList<ApplyButton> applyButtons = new ArrayList<ApplyButton>();
	private ArrayList<ArrayList<Control>> controlsList = new ArrayList<ArrayList<Control>>();
	private Map<Control, String> controlsMap;

	/**
	 * initizalizes the panel, saves resources
	 * 
	 * @param resources GUIResources
	 */
	public DataPluginsPanel(GUIResources resources) {
		this.resources = resources;
		controlsMap = new HashMap<Control, String>();
		resources.addRefreshablePanel(this);
		width = resources.getSize().width / 4;
		drawPanel(initComponents());
	}

	/**
	 * initialize components
	 * @return jpanel
	 */
	public JPanel initComponents() {
		// add each data plugin to list of installed plugins to be
		// displayed
		plugins = new JPanel(new BorderLayout());
		plugins.setOpaque(false);
		plugins.add(drawPluginsList(), BorderLayout.CENTER);

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

		JLabel title = new JLabel("Data Info");
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

		JPanel content = new JPanel(new BorderLayout());
		content.setOpaque(false);
		content.add(titleWrapper, BorderLayout.NORTH);
		content.add(pluginsWindow, BorderLayout.CENTER);
		content.validate();
		content.setMaximumSize(content.getPreferredSize());

		int spacing = width / 10 - 1;
		// add spacing to the right...
		JPanel eastSpace = new JPanel();
		eastSpace.setPreferredSize(new Dimension(spacing, 0));
		eastSpace.setOpaque(false);

		// ...and left of the scrollpane
		JPanel westSpace = new JPanel();
		westSpace.setPreferredSize(new Dimension(spacing, 0));
		westSpace.setOpaque(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(width, 0));
		panel.add(content, BorderLayout.CENTER);
		panel.add(eastSpace, BorderLayout.EAST);
		panel.add(westSpace, BorderLayout.WEST);
		panel.validate();
		panel.setMaximumSize(panel.getPreferredSize());
		return panel;
	}

	/**
	 * draws the panel of data plugins
	 * 
	 * @return jpanel
	 */
	public JPanel drawPluginsList() {
		controlsMap = new HashMap<Control, String>();

		// create plugins resources
		JPanel plugins = new JPanel();
		plugins.setOpaque(false);
		plugins.setLayout(new BoxLayout(plugins, BoxLayout.Y_AXIS));

		int displayedPlugins = 0;

		// add each plugin
		for (int i = 0; i < resources.getFramework().getDataPlugins().size(); i++) {
			Map<String, String> queryParams = resources.getFramework()
					.getQueryParametersForPlugin(
							resources.getFramework().getDataPlugins().get(i));
			if (queryParams != null && queryParams.size() > 0) {

				// divider bar
				if (displayedPlugins != 0) {
					int dividerWidth = (resources.getSize().width / 5)
							- (resources.getSize().width / 5) / 15;
					JLabel bottomSpace = new JLabel(" ");
					bottomSpace.setFont(resources.getFont("bold", 25f));
					JLabel divider = createJLabelImg("divider.png",
							dividerWidth, resources.getSize().height, 1f);
					JPanel dividerWrapper = new JPanel(new FlowLayout(
							FlowLayout.LEFT, 0, 0));
					dividerWrapper.setOpaque(false);
					dividerWrapper.add(divider);
					dividerWrapper.setPreferredSize(new Dimension((resources
							.getSize().width / 5), dividerWrapper
							.getPreferredSize().height));
					dividerWrapper.setMaximumSize(dividerWrapper
							.getPreferredSize());
					plugins.add(dividerWrapper);
					plugins.add(bottomSpace);
				}

				JPanel plugin = new JPanel();
				plugin.setOpaque(false);
				plugin.setLayout(new BoxLayout(plugin, BoxLayout.Y_AXIS));

				// plugin name
				String pluginName = resources.getFramework().getDataPlugins()
						.get(i).getName();
				if (pluginName == null)
					pluginName = "Unnamed Plugin";
				int textWidth = (resources.getSize().width / 5)
						- (resources.getSize().width / 5) / 15;
				pluginName = Util.clipText(pluginName,
						resources.getFont("semibold", 30f), textWidth);
				JLabel name = new JLabel(pluginName);
				name.setFont(resources.getFont("semibold", 30f));
				name.setForeground(resources.getPalette(1));

				JLabel spaceTop = new JLabel(" ");
				spaceTop.setFont(resources.getFont("regular", 15f));

				JPanel nameWrapper = new JPanel(new BorderLayout());
				nameWrapper.setBackground(Color.WHITE);
				nameWrapper.setOpaque(false);
				nameWrapper.add(name, BorderLayout.CENTER);
				nameWrapper.add(spaceTop, BorderLayout.SOUTH);
				nameWrapper.validate();
				nameWrapper.setMaximumSize(new Dimension(
						resources.getSize().width, nameWrapper
								.getPreferredSize().height));
				plugin.add(nameWrapper);

				// Warning irrevlevant.
				@SuppressWarnings("rawtypes")
				ArrayList<Control> controls = new ArrayList<Control>();
				// add plugin control parameters
				for (Map.Entry<String, String> entry : queryParams.entrySet()) {
					TextFieldControl control = new TextFieldControl();
					controls.add(control);
					controlsMap.put(control, entry.getKey());
					control.setLabel(entry.getValue());
					if (entry.getKey().equals("password"))
						control.setPasswordField(true);
					JPanel controlPanel = new JPanel(new FlowLayout(
							FlowLayout.LEFT, 0, 0));
					controlPanel.setOpaque(false);
					controlPanel.add(control.draw(resources));
					controlPanel.validate();
					controlPanel
							.setMaximumSize(controlPanel.getPreferredSize());
					plugin.add(controlPanel);
				}
				controlsList.add(controls);

				JLabel spaceBottom = new JLabel(" ");
				spaceBottom.setFont(resources.getFont("regular", 15f));

				plugin.add(spaceBottom);

				ApplyButton apply = new ApplyButton(resources, resources
						.getFramework().getDataPlugins().get(i));
				apply.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int index = applyButtons.indexOf(e.getComponent());
						Map<String, String> query = new HashMap<String, String>();
						for (Control control : controlsList.get(index)) {
							query.put(controlsMap.get(control),
									control.getValue() + "");
						}
						resources.getFramework().loadData(
								applyButtons.get(index).getDataPlugin(), query);
					}
				});
				applyButtons.add(apply);
				JPanel applyWrapper = new JPanel(new FlowLayout(
						FlowLayout.LEFT, 0, 0));
				applyWrapper.setOpaque(false);
				applyWrapper.add(apply);
				applyWrapper.setPreferredSize(new Dimension((resources
						.getSize().width / 5),
						applyWrapper.getPreferredSize().height));
				applyWrapper.setMaximumSize(applyWrapper.getPreferredSize());
				plugin.add(applyWrapper);

				JLabel footerSpace = new JLabel(" ");
				footerSpace.setFont(resources.getFont("bold", 35f));
				plugin.add(footerSpace);

				plugins.add(plugin);
				displayedPlugins++;
			}
		}
		plugins.validate();
		plugins.setMaximumSize(plugins.getPreferredSize());
		return plugins;
	}

	/**
	 * draws the panel
	 * 
	 * @param dataPluginsPanel
	 *            panel with plugins
	 */
	public void drawPanel(JPanel dataPluginsPanel) {
		setLayout(new BorderLayout());
		setOpaque(false);
		validate();
		add(dataPluginsPanel, BorderLayout.CENTER);
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
		applyButtons.clear();
		plugins.removeAll();
		plugins.add(drawPluginsList(), BorderLayout.CENTER);
		plugins.validate();
		plugins.repaint();
		pluginsWindow.validate();
	}
}
