package edu.cmu.cs214.hw5.visualizer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Toggle Control
 * 
 * @author Hizal
 *
 */
public class ToggleControl extends Control<Boolean> {
	private GUIResources resources;
	private String fieldName;
	private boolean value;
	private JCheckBox toggle = null;
	private Icon on;
	private Icon off;

	/**
	 * initializes control and default values
	 */
	public ToggleControl() {
		fieldName = "Untitled Control";
		value = false;
	}

	/**
	 * set the label
	 * 
	 * @param fieldName
	 *            label
	 */
	public void setLabel(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * get the label
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
	public void setDefaultValue(Boolean value) {
		this.value = value;
	}

	/**
	 * get the value
	 * 
	 * @return value
	 */
	public Boolean getValue() {
		if (toggle == null)
			return value;
		value = toggle.isSelected();
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

		on = createJLabelImg("toggle_on.png", limitWidth, limitHeight, 1f)
				.getIcon();
		off = createJLabelImg("toggle_off.png", limitWidth, limitHeight, 1f)
				.getIcon();

		toggle = new JCheckBox();
		toggle.setSelectedIcon(on);
		toggle.setIcon(off);
		toggle.setEnabled(true);
		toggle.setSelected(value);
		toggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		toggle.setFocusable(false);
		toggle.setBorder(null);
		toggle.setOpaque(false);

		int indent = limitWidth / 25;
		int textWidthLimit = limitWidth - indent * 2 - on.getIconWidth();
		String fieldStr = Util.clipText(fieldName + " ",
				resources.getFont("italic", 15f), textWidthLimit);
		JLabel label = new JLabel(fieldStr);
		label.setFont(resources.getFont("italic", 15f));
		label.setForeground(resources.getTextColor());

		JPanel toggleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT,
				indent, 0));
		toggleWrapper.setOpaque(false);
		toggleWrapper.setPreferredSize(new Dimension(limitWidth, limitHeight));
		toggleWrapper.setMaximumSize(toggleWrapper.getPreferredSize());
		toggleWrapper.add(toggle);
		toggleWrapper.add(label);

		JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,
				height / 7));
		container.setOpaque(false);
		container.add(toggleWrapper);
		container.setPreferredSize(new Dimension(width, container
				.getPreferredSize().height));
		container.setMaximumSize(container.getPreferredSize());

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
