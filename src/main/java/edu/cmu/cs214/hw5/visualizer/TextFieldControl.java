package edu.cmu.cs214.hw5.visualizer;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * TextField Control
 * 
 * @author Hizal
 *
 */
public class TextFieldControl extends Control<String> {
	private GUIResources resources;
	private String fieldName;
	private String value;
	private JTextField text = null;
	private JPasswordField textPW = null;
	private boolean isPasswordField;

	/**
	 * initializes control and default values
	 */
	public TextFieldControl() {
		fieldName = "Untitled Control";
		value = "";
		isPasswordField = false;
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
	public void setDefaultValue(String value) {
		this.value = value;
	}

	/**
	 * get the value of the control, returns a Range object with minimum and
	 * maximum field values
	 * 
	 * @return values
	 */
	public String getValue() {
		if (text == null)
			return value;
		else if (isPasswordField)
			value = new String(textPW.getPassword());
		else
			value = text.getText();
		return value;
	}

	@Override
	public JPanel draw(GUIResources resources) {
		this.resources = resources;
		return getPanel();
	}

	/**
	 * sets if textfield is passwordfield (by default, false)
	 * 
	 * @param isPasswordField
	 *            true if textfield should be a password field
	 */
	public void setPasswordField(boolean isPasswordField) {
		this.isPasswordField = isPasswordField;
	}

	/**
	 * draws the panel containing the control
	 * 
	 * @return panel
	 */
	private JPanel getPanel() {
		int width = resources.getSize().width / 5;
		int height = resources.getSize().height / 14;

		JLabel textfieldBg = createJLabelImg("textfield.png", width - width
				/ 15, height, 1f);
		int textfieldWidth = textfieldBg.getIcon().getIconWidth();
		int textfieldHeight = textfieldBg.getIcon().getIconHeight();

		String fieldStr = Util.clipText("  " + fieldName + " ",
				resources.getFont("regular", 20f), textfieldWidth);
		JLabel title = new JLabel(fieldStr);
		title.setFont(resources.getFont("regular", 20f));
		title.setForeground(resources.getTextColor());

		JPanel bgWrapper = new JPanel(new BorderLayout());
		bgWrapper.setOpaque(false);
		bgWrapper.setBounds(0, 0, textfieldWidth, textfieldHeight);
		bgWrapper.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		bgWrapper.setMaximumSize(bgWrapper.getPreferredSize());
		bgWrapper.add(textfieldBg, BorderLayout.CENTER);

		textPW = new JPasswordField(5);
		textPW.setFont(resources.getFont("bold", 15f));
		textPW.setForeground(resources.getTextColor());
		textPW.setText(value);
		textPW.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		textPW.setEditable(true);
		textPW.setFocusable(false);
		textPW.setFocusable(true);
		textPW.setBorder(null);
		textPW.setOpaque(false);
		textPW.setEchoChar('*');

		text = new JTextField(5);
		text.setFont(resources.getFont("italic", 15f));
		text.setForeground(resources.getTextColor());
		text.setText(value);
		text.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		text.setEditable(true);
		text.setFocusable(false);
		text.setFocusable(true);
		text.setBorder(null);
		text.setOpaque(false);

		int indent = textfieldWidth / 20;
		JPanel textWrapper = new JPanel(new BorderLayout());
		textWrapper.setOpaque(false);
		textWrapper.setBounds(indent, 0, textfieldWidth - indent * 2,
				textfieldHeight);
		textWrapper.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		textWrapper.setMaximumSize(textWrapper.getPreferredSize());
		if (isPasswordField)
			textWrapper.add(textPW, BorderLayout.CENTER);
		else
			textWrapper.add(text, BorderLayout.CENTER);

		JLayeredPane textfieldWrapper = new JLayeredPane();
		textfieldWrapper.setOpaque(false);
		textfieldWrapper.setPreferredSize(new Dimension(textfieldWidth * 2,
				textfieldHeight));
		textfieldWrapper.setMaximumSize(textfieldWrapper.getPreferredSize());
		textfieldWrapper.add(bgWrapper, Integer.valueOf(0), 0);
		textfieldWrapper.add(textWrapper, Integer.valueOf(1), 0);

		JPanel controlComponents = new JPanel();
		controlComponents.setOpaque(false);
		controlComponents.setLayout(new BoxLayout(controlComponents,
				BoxLayout.Y_AXIS));
		controlComponents.add(title);
		controlComponents.add(textfieldWrapper);

		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.setPreferredSize(new Dimension(width, height));
		container.setMaximumSize(container.getPreferredSize());
		container.add(controlComponents, BorderLayout.CENTER);

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
