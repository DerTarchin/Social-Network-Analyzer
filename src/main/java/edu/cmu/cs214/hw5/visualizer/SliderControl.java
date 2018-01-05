package edu.cmu.cs214.hw5.visualizer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Slider control
 * 
 * @author Hizal
 *
 */
public class SliderControl extends Control<Integer> {
	private GUIResources resources;
	private String fieldName;
	private int value;
	private JSlider slider = null;
	private int min;
	private int max;
	private JLabel valueLabel;

	/**
	 * initializes control, default values, and minimum and maximum values for
	 * the slider
	 * 
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 */
	public SliderControl(int min, int max) {
		fieldName = "Untitled Control";
		value = Integer.MAX_VALUE;
		this.min = min;
		this.max = max;
	}

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
	 * get label
	 *
	 * @return label
	 */
	public String getLabel() {
		return fieldName;
	}

	/**
	 * set the default value
	 * 
	 * @param value
	 *            value
	 */
	public void setDefaultValue(Integer value) {
		this.value = value;
	}

	/**
	 * get value from slider
	 * 
	 * @return value
	 */
	public Integer getValue() {
		if (slider == null)
			return value;
		value = slider.getValue();
		return value;
	}

	@Override
	public JPanel draw(GUIResources resources) {
		this.resources = resources;
		return getPanel();
	}

	/**
	 * draws the panel containing the control
	 * 
	 * @return panel
	 */
	private JPanel getPanel() {
		int width = resources.getSize().width / 5;
		int height = resources.getSize().height / 14;

		JLabel limit = createJLabelImg("textfield.png", width - width / 15,
				height, 1f);
		int limitWidth = limit.getIcon().getIconWidth();
		int limitHeight = limit.getIcon().getIconHeight();

		String fieldStr = Util.clipText("  " + fieldName + " ",
				resources.getFont("regular", 20f), limitWidth);
		JLabel title = new JLabel(fieldStr);
		title.setFont(resources.getFont("regular", 20f));
		title.setForeground(resources.getTextColor());

		JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titleWrapper.setOpaque(false);
		titleWrapper.add(title);

		if (min == max)
			max++;
		else if (min > max) {
			int temp = min;
			min = max;
			max = temp;
		}
		slider = new JSlider(min, max);
		if (value > max || value < min)
			value = slider.getValue();
		else
			slider.setValue(value);
		Util.formatSlider(slider, resources);
		slider.setValue(value);
		slider.setCursor(new Cursor(Cursor.HAND_CURSOR));
		slider.setFocusable(false);
		slider.setBorder(null);
		slider.setOpaque(false);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() == slider)
					valueLabel.setText(slider.getValue() + "");
			}
		});

		String maxValue = slider.getMaximum() + "";
		if ((slider.getMinimum() + "").length() > maxValue.length())
			maxValue = slider.getMinimum() + "";
		JLabel maxLength = new JLabel(maxValue);
		maxLength.setFont(resources.getFont("bold", 15f));
		maxLength.setForeground(resources.getPalette(0));
		int maxWidth = maxLength.getPreferredSize().width;

		JPanel sliderWrapper = new JPanel(new BorderLayout());
		sliderWrapper.setOpaque(false);
		sliderWrapper.setPreferredSize(new Dimension(limitWidth - maxWidth,
				limitHeight));
		sliderWrapper.setMaximumSize(sliderWrapper.getPreferredSize());
		sliderWrapper.add(slider, BorderLayout.CENTER);

		valueLabel = new JLabel(slider.getValue() + "");
		valueLabel.setFont(resources.getFont("bold", 15f));
		valueLabel.setForeground(resources.getPalette(0));

		JPanel sliderPaneWrapper = new JPanel(new BorderLayout());
		sliderPaneWrapper.setOpaque(false);
		sliderPaneWrapper.setPreferredSize(new Dimension(limitWidth,
				limitHeight));
		sliderPaneWrapper.setMaximumSize(sliderPaneWrapper.getPreferredSize());
		sliderPaneWrapper.add(sliderWrapper, BorderLayout.WEST);
		sliderPaneWrapper.add(valueLabel, BorderLayout.EAST);

		JPanel controlComponents = new JPanel();
		controlComponents.setOpaque(false);
		controlComponents.setLayout(new BoxLayout(controlComponents,
				BoxLayout.Y_AXIS));
		controlComponents.setPreferredSize(new Dimension(limitWidth,
				limitHeight));
		controlComponents.setMaximumSize(controlComponents.getPreferredSize());
		controlComponents.add(titleWrapper);
		controlComponents.add(sliderPaneWrapper);

		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.setPreferredSize(new Dimension(width, height));
		container.setMaximumSize(container.getPreferredSize());
		container.add(controlComponents, BorderLayout.WEST);

		return container;
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
