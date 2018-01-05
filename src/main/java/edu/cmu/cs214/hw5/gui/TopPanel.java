package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Menu panel for the main window, displays the settings, refresh and exit
 * buttons as well as the title
 * 
 * @author Hizal
 *
 */
public class TopPanel extends JPanel implements Window {
	private static final long serialVersionUID = 1L;
	private GUIResources resources;
	private PluginManager manager;

	/**
	 * initializes the window and ther GUIResources
	 * 
	 * @param resources GUIResources
	 */
	public TopPanel(GUIResources resources) {
		this.resources = resources;
		resources.addRefreshablePanel(this);
		createPluginManager();
		drawPanel(initComponents());
	}

	/**
	 * creates a new Plugin Manager window
	 */
	public void createPluginManager() {
		manager = new PluginManager(resources);
	}

	/**
	 * initializes components
	 * 
	 * @return JPanel
	 */
	public JPanel initComponents() {
		// init variables
		int iconSize = resources.getSize().height / 16;
		int verticalPadding = resources.getSize().height / 50;
		int horizontalPadding = resources.getSize().width / 50;

		// init labels/imges
		JLabel settings = createJLabelImg("settings.png", iconSize, iconSize,
				1f);
		JLabel refresh = createJLabelImg("refresh.png", iconSize, iconSize, 1f);
		JLabel exit = createJLabelImg("exit.png", iconSize, iconSize, 1f);
		JLabel titlebold = new JLabel("JMH    ");
		JLabel titlelight = new JLabel("SNAnalyzer");

		// format title
		titlebold.setFont(resources.getFont("extrabold", 38f));
		titlelight.setFont(resources.getFont("light", 38f));
		titlebold.setForeground(resources.getTextColor());
		titlelight.setForeground(resources.getTextColor());

		// add mouse listeners to labels
		exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manager.setVisible(true);
			}
		});
		refresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		refresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resources.getFramework().refresh();
			}
		});

		JPanel sideButtons = new JPanel(new FlowLayout(FlowLayout.LEFT,
				horizontalPadding, verticalPadding));
		sideButtons.setOpaque(false);
		sideButtons.add(settings);
		sideButtons.add(refresh);

		JPanel space = new JPanel();
		space.setOpaque(false);
		space.setPreferredSize(new Dimension(iconSize, iconSize));

		JPanel exitWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT,
				horizontalPadding, verticalPadding));
		exitWrapper.setOpaque(false);
		exitWrapper.add(space);
		exitWrapper.add(exit);

		JPanel title = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,
				verticalPadding));
		title.setOpaque(false);
		title.add(titlebold);
		title.add(titlelight);

		int spacing = resources.getSize().height / 18;
		JPanel dividerSpace = new JPanel();
		dividerSpace.setOpaque(false);
		dividerSpace.setPreferredSize(new Dimension(spacing, spacing));

		JPanel topBar = new JPanel(new BorderLayout());
		topBar.setOpaque(false);
		topBar.add(sideButtons, BorderLayout.WEST);
		topBar.add(title, BorderLayout.CENTER);
		topBar.add(exitWrapper, BorderLayout.EAST);
		topBar.add(dividerSpace, BorderLayout.SOUTH);
		return topBar;
	}

	/**
	 * draws the panel
	 * 
	 * @param topBar
	 *            JPanel
	 * 
	 */
	public void drawPanel(JPanel topBar) {
		setLayout(new BorderLayout());
		setOpaque(false);
		validate();
		add(topBar, BorderLayout.CENTER);
		resources.setAnalysisPaneSize(new Dimension(resources
				.getAnalysisPaneSize().width, resources.getSize().height
				- this.getPreferredSize().height));
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
	}

}
