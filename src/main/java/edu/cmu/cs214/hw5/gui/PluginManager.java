package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;

import edu.cmu.cs214.hw5.core.AnalysisPlugin;

/**
 * Plugin Manager, allows you to add or remove plugins, and assign dataplugins
 * to analysis plugins
 */
public class PluginManager extends JFrame implements Window {

	private GUIResources resources;
	private JFrame thisFrame;
	private boolean visible;

	private AnalysisPlugin manage;

	private ArrayList<JLabel> manageIcons = new ArrayList<JLabel>();

	private int width;
	private int height;

	private JScrollPane pluginsWindow;
	private JPanel plugins;

	private JPanel main;

	/**
	 * init the plugin Manager window
	 * 
	 * @param resources
	 *            GUIResources
	 */
	public PluginManager(GUIResources resources) {
		this.resources = resources;
		manage = null;
		resources.addRefreshablePanel(this);
		thisFrame = this;
		width = resources.getSize().height / 3 + resources.getSize().height / 8;
		height = resources.getSize().height / 2 + resources.getSize().height
				/ 10;
		main = new JPanel(new BorderLayout());
		main.add(initComponents(), BorderLayout.CENTER);
		drawPanel(main);
	}

	/**
	 * init componenets
	 * 
	 * @return jpanel
	 */
	public JPanel initComponents() {
		// create title bar for resources
		JLabel title = new JLabel("ANALYSIS  PLUGINS");
		title.setFont(resources.getFont("bold", 30f));
		title.setForeground(resources.getBackground());

		if (manage != null)
			title.setText(Util.clipText(manage.getName().toUpperCase(),
					title.getFont(), width));

		// add spacing to the title bar
		int titleSpace = height / 18;
		JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,
				titleSpace));
		titleWrapper.setOpaque(false);
		titleWrapper.add(title);

		// add each plugin (data or analyzer) to list of installed plugins to be
		// displayed
		plugins = new JPanel(new BorderLayout());
		plugins.setOpaque(false);
		if (manage == null)
			plugins.add(drawPluginsList(), BorderLayout.CENTER);
		else
			plugins.add(drawManageList(), BorderLayout.CENTER);

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

		// add spacing to the right...
		JPanel eastSpace = new JPanel();
		eastSpace.setPreferredSize(new Dimension(width / 50, width / 25));
		eastSpace.setOpaque(false);

		// ...and left of the scrollpane
		JPanel westSpace = new JPanel();
		westSpace.setPreferredSize(new Dimension(width / 50, width / 25));
		westSpace.setOpaque(false);

		// add control buttons with listeners
		int iconSize = width / 8;
		int iconSpace = iconSize - iconSize / 3;
		JLabel back = createJLabelImg("back.png", iconSize, iconSize, 1f);
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manage = null;
				refresh();
			}
		});
		JLabel close = createJLabelImg("close.png", iconSize, iconSize, 1f);
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manage = null;
				thisFrame.setVisible(false);
				refresh();
			}
		});

		JPanel buttonSpace = new JPanel();
		buttonSpace.setOpaque(false);
		buttonSpace.setPreferredSize(new Dimension(iconSize, iconSize));

		// add buttons to panel with spacing
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,
				iconSpace - (iconSpace / 7)));
		buttonPanel.setOpaque(false);
		if (manage != null) {
			buttonPanel.add(back);
			buttonPanel.add(buttonSpace);
		}
		buttonPanel.add(close);

		// put all together into one resources
		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(resources.getPalette(0));
		main.add(titleWrapper, BorderLayout.NORTH);
		main.add(westSpace, BorderLayout.WEST);
		main.add(eastSpace, BorderLayout.EAST);
		main.add(pluginsWindow, BorderLayout.CENTER);
		main.add(buttonPanel, BorderLayout.SOUTH);
		return main;
	}

	/**
	 * draws list of plugins
	 * 
	 * @return jpanel
	 */
	private JPanel drawPluginsList() {
		JPanel plugins = new JPanel();
		plugins.setOpaque(false);
		plugins.setLayout(new BoxLayout(plugins, BoxLayout.Y_AXIS));

		for (int i = 0; i < resources.getFramework().getAnalysisPlugins()
				.size(); i++) {
			JLabel bg = createJLabelImg("pluginbg.png", width - width / 7,
					width, 1f);
			int pluginWidth = bg.getIcon().getIconWidth();
			int pluginHeight = bg.getIcon().getIconHeight();

			JPanel bgWrapper = new JPanel(new BorderLayout());
			bgWrapper.setOpaque(false);
			bgWrapper.setBounds(0, 0, pluginWidth, pluginHeight);
			bgWrapper
					.setPreferredSize(new Dimension(pluginWidth, pluginHeight));
			bgWrapper.setMaximumSize(bgWrapper.getPreferredSize());
			bgWrapper.add(bg, BorderLayout.CENTER);

			int scaleFactor = pluginHeight / 5;

			JLabel icon = createJLabelImg("modify.png", pluginHeight
					- pluginHeight / 4, pluginHeight - pluginHeight / 4, 1f);
			String pluginName = Util.clipText(resources.getFramework()
					.getAnalysisPlugins().get(i).getName(),
					resources.getFont("regular", 30f), pluginWidth
							- icon.getIcon().getIconWidth()
							- (icon.getIcon().getIconWidth() / 3));
			JLabel name = new JLabel(pluginName);
			name.setFont(resources.getFont("regular", 30f));
			name.setForeground(resources.getTextColor());

			manageIcons.add(icon);
			icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
			icon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int index = manageIcons.indexOf(e.getComponent());
					manage = resources.getFramework().getAnalysisPlugins()
							.get(index);
					refresh();
				}
			});

			JPanel textWrapper = new JPanel(new BorderLayout());
			textWrapper.setOpaque(false);
			textWrapper.setBounds(scaleFactor, 0,
					pluginWidth - scaleFactor * 2, pluginHeight);
			textWrapper.setPreferredSize(new Dimension(pluginWidth
					- scaleFactor * 2, pluginHeight));
			textWrapper.setMaximumSize(textWrapper.getPreferredSize());
			textWrapper.add(name, BorderLayout.WEST);
			textWrapper.add(icon, BorderLayout.EAST);

			JLayeredPane plugin = new JLayeredPane();
			plugin.setOpaque(false);
			plugin.setPreferredSize(new Dimension(pluginWidth, pluginHeight));
			plugin.setMaximumSize(plugin.getPreferredSize());
			plugin.add(bgWrapper, Integer.valueOf(0), 0);
			plugin.add(textWrapper, Integer.valueOf(1), 0);

			JPanel space = new JPanel();
			if (i != 0)
				space.setPreferredSize(new Dimension(height / 50, height / 25));
			else
				space.setPreferredSize(new Dimension(height / 50, 0));
			space.setMaximumSize(space.getPreferredSize());
			space.setOpaque(false);
			plugins.add(space);
			plugins.add(plugin);
		}
		return plugins;
	}

	/**
	 * draws manage list for selected plugin
	 * 
	 * @return jpanel
	 */
	private JPanel drawManageList() {
		JPanel plugins = new JPanel();
		plugins.setOpaque(false);
		plugins.setLayout(new BoxLayout(plugins, BoxLayout.Y_AXIS));

		for (int i = 0; i < resources.getFramework().getDataPlugins().size(); i++) {
			JLabel bg = createJLabelImg("pluginbg.png", width - width / 7,
					width, 1f);
			int pluginWidth = bg.getIcon().getIconWidth();
			int pluginHeight = bg.getIcon().getIconHeight();

			JPanel bgWrapper = new JPanel(new BorderLayout());
			bgWrapper.setOpaque(false);
			bgWrapper.setBounds(0, 0, pluginWidth, pluginHeight);
			bgWrapper
					.setPreferredSize(new Dimension(pluginWidth, pluginHeight));
			bgWrapper.setMaximumSize(bgWrapper.getPreferredSize());
			bgWrapper.add(bg, BorderLayout.CENTER);

			int scaleFactor = pluginHeight / 5;
			JLabel icon;
			if (resources.getFramework()
					.getDataSourcesForAnalysisPlugin(manage) != null
					&& resources
							.getFramework()
							.getDataSourcesForAnalysisPlugin(manage)
							.contains(
									resources.getFramework().getDataPlugins()
											.get(i))) {
				icon = createJLabelImg("remove.png", pluginHeight
						- pluginHeight / 4, pluginHeight - pluginHeight / 4, 1f);
				icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
				icon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int index = manageIcons.indexOf(e.getComponent());
						resources.getFramework().removeDataSourceForAnalysisPlugin(manage, resources.getFramework()
								.getDataPlugins().get(index));
						refresh();
					}
				});
			} else {
				icon = createJLabelImg("add.png", pluginHeight - pluginHeight
						/ 4, pluginHeight - pluginHeight / 4, 1f);
				icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
				icon.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int index = manageIcons.indexOf(e.getComponent());
						resources.getFramework()
								.addDataSourceForAnalysisPlugin(
										manage,
										resources.getFramework()
												.getDataPlugins().get(index));
						refresh();
					}
				});
			}
			manageIcons.add(icon);

			String pluginName = Util.clipText(resources.getFramework()
					.getDataPlugins().get(i).getName(),
					resources.getFont("regular", 30f), pluginWidth
							- icon.getIcon().getIconWidth()
							- (icon.getIcon().getIconWidth() / 3));
			JLabel name = new JLabel(pluginName);
			name.setFont(resources.getFont("regular", 30f));
			name.setForeground(resources.getTextColor());

			JPanel textWrapper = new JPanel(new BorderLayout());
			textWrapper.setOpaque(false);
			textWrapper.setBounds(scaleFactor, 0,
					pluginWidth - scaleFactor * 2, pluginHeight);
			textWrapper.setPreferredSize(new Dimension(pluginWidth
					- scaleFactor * 2, pluginHeight));
			textWrapper.setMaximumSize(textWrapper.getPreferredSize());
			textWrapper.add(name, BorderLayout.WEST);
			textWrapper.add(icon, BorderLayout.EAST);

			JLayeredPane plugin = new JLayeredPane();
			plugin.setOpaque(false);
			plugin.setPreferredSize(new Dimension(pluginWidth, pluginHeight));
			plugin.setMaximumSize(plugin.getPreferredSize());
			plugin.add(bgWrapper, Integer.valueOf(0), 0);
			plugin.add(textWrapper, Integer.valueOf(1), 0);

			JPanel space = new JPanel();
			if (i != 0)
				space.setPreferredSize(new Dimension(height / 50, height / 25));
			else
				space.setPreferredSize(new Dimension(height / 50, 0));
			space.setMaximumSize(space.getPreferredSize());
			space.setOpaque(false);
			plugins.add(space);
			plugins.add(plugin);
		}
		return plugins;
	}

	/**
	 * refresh the window
	 */
	public void refresh() {
		manageIcons.clear();
		main.removeAll();
		main.add(initComponents(), BorderLayout.CENTER);
		main.revalidate();
		main.repaint();
	}

	/**
	 * draw the window
	 * 
	 * @param main
	 *            components
	 */
	public void drawPanel(JPanel main) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		add(main);
		validate();
		setVisible(false);
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
}
