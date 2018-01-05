package edu.cmu.cs214.hw5.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;

import edu.cmu.cs214.hw5.core.Framework;

/**
 * GUIResources object stores important and useful variables and information for
 * the main GUI
 * 
 * @author Hizal
 *
 */
public class GUIResources {

	// private static final float TEMPLATE_WIDTH = 1440f;
	private static final float TEMPLATE_HEIGHT = 900f;
	private static final int WIDTH = Toolkit.getDefaultToolkit()
			.getScreenSize().width
			- (Toolkit.getDefaultToolkit().getScreenSize().width / 4);
	private static final int HEIGHT = Toolkit.getDefaultToolkit()
			.getScreenSize().height
			- (Toolkit.getDefaultToolkit().getScreenSize().height / 4);

	private Framework framework;
	private Dimension windowSize;
	private String resources = "src/resources/";
	private String lib = "libs/";
	private String bin = "bin/";

	private Font light;
	private Font regular;
	private Font italic;
	private Font semibold;
	private Font bold;
	private Font extrabold;

	private Color bgColor;
	private Color textColor;
	private ArrayList<Color> palette = new ArrayList<Color>();

	private ArrayList<Window> toRefresh = new ArrayList<Window>();
	private Dimension analysisPaneSize = new Dimension(0, 0);

	/**
	 * initialzies GUIResouces defaults
	 */
	public GUIResources() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(242, 243, 230));
		setTextColor(new Color(170, 167, 148));
		addPalette(new Color(221, 217, 192));
		addPalette(new Color(121, 191, 180));
		addPalette(new Color(26, 165, 172));
		addPalette(new Color(4, 38, 63));
		addPalette(new Color(1, 15, 41));
		setFont("OpenSans-Light", "light");
		setFont("OpenSans-Regular", "regular");
		setFont("OpenSans-Italic", "italic");
		setFont("OpenSans-Semibold", "semibold");
		setFont("OpenSans-Bold", "bold");
		setFont("OpenSans-Extrabold", "extrabold");
	}

	/**
	 * sets the framework
	 * 
	 * @param f
	 *            framework
	 */
	public void setFramework(Framework f) {
		framework = f;
	}

	/**
	 * gets the framework
	 * 
	 * @return framework
	 */
	public Framework getFramework() {
		return framework;
	}

	/**
	 * sets the size of the JFrame
	 * 
	 * @param size
	 *            jframe dimensions
	 */
	public void setSize(Dimension size) {
		windowSize = size;
	}

	/**
	 * gets the size of the JFrame
	 * 
	 * @return dimension
	 */
	public Dimension getSize() {
		return windowSize;
	}

	/**
	 * sets the background color
	 * 
	 * @param color
	 *            background color
	 */
	public void setBackground(Color color) {
		bgColor = color;
	}

	/**
	 * get background color
	 * 
	 * @return color
	 */
	public Color getBackground() {
		return bgColor;
	}

	/**
	 * sets the text color
	 * 
	 * @param color
	 *            text color
	 */
	public void setTextColor(Color color) {
		textColor = color;
	}

	/**
	 * get text color
	 * 
	 * @return text color
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * adds a new color to the color palette
	 * 
	 * @param color
	 *            color
	 */
	public void addPalette(Color color) {
		palette.add(color);
	}

	/**
	 * gets the color at the index of color palette
	 * 
	 * @param index
	 *            index of color
	 * @return color
	 */
	public Color getPalette(int index) {
		return palette.get(index);
	}

	/**
	 * gets the path to the resources folder
	 * 
	 * @return path
	 */
	public String resources() {
		return resources;
	}

	/**
	 * gets the path to the library folder
	 * 
	 * @return path
	 */
	public String lib() {
		return lib;
	}

	/**
	 * gets the path to the bin folder
	 * 
	 * @return path
	 */
	public String bin() {
		return bin;
	}

	/**
	 * sets the dimension for the analysis plugin JPanel
	 * 
	 * @param d
	 *            dimension
	 */
	public void setAnalysisPaneSize(Dimension d) {
		analysisPaneSize = d;
	}

	/**
	 * gets the dimension for the analysis plugin JPanel
	 * 
	 * @return dimension
	 */
	public Dimension getAnalysisPaneSize() {
		return analysisPaneSize;
	}

	/**
	 * sets the font name and type for the GUI
	 * 
	 * @param name
	 *            font name
	 * @param type
	 *            font type
	 */
	public void setFont(String name, String type) {
		if (type.equals("light"))
			light = Util.getFont(name, resources);
		else if (type.equals("regular"))
			regular = Util.getFont(name, resources);
		else if (type.equals("italic"))
			italic = Util.getFont(name, resources);
		else if (type.equals("semibold"))
			semibold = Util.getFont(name, resources);
		else if (type.equals("bold"))
			bold = Util.getFont(name, resources);
		else if (type.equals("extrabold"))
			extrabold = Util.getFont(name, resources);
	}

	/**
	 * gets the requested Font
	 * 
	 * @param type
	 *            type of font
	 * @param size
	 *            size of font
	 * @return Font object
	 */
	public Font getFont(String type, float size) {
		size = resizeFont(size);
		if (type.equals("light"))
			return light.deriveFont(size);
		else if (type.equals("regular"))
			return regular.deriveFont(size);
		else if (type.equals("italic"))
			return italic.deriveFont(size);
		else if (type.equals("semibold"))
			return semibold.deriveFont(size);
		else if (type.equals("bold"))
			return bold.deriveFont(size);
		else if (type.equals("extrabold"))
			return extrabold.deriveFont(size);
		return null;
	}

	/**
	 * adds a panel to refresh on click of the refresh button
	 * 
	 * @param panel
	 *            window to add
	 */
	public void addRefreshablePanel(Window panel) {
		toRefresh.add(panel);
	}

	/**
	 * refreshes all windows in the refreshable list
	 */
	public void refresh() {
		for (Window panel : toRefresh)
			panel.refresh();
	}

	/**
	 * resizes the Font based on the resolution of the screen (to preserve
	 * correct formatting relationships)
	 * 
	 * @param size
	 *            old size
	 * @return new size
	 */
	private float resizeFont(float size) {
		return (size * windowSize.height) / TEMPLATE_HEIGHT;
	}
}
