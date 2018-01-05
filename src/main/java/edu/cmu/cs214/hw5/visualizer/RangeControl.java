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
import javax.swing.JTextField;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Range Control Has a minimum and maximum field
 * 
 * @author Hizal
 *
 */
public class RangeControl extends Control<Range> {

  private static final long serialVersionUID = 1L;
  private GUIResources resources;
	private String fieldName;
	private Range value;
	private JTextField textMin = null;
	private JTextField textMax = null;

	/**
	 * initializes control and default values
	 */
	public RangeControl() {
		fieldName = "Untitled Control";
		value = new Range();
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
	 * set the default Range values
	 * 
	 * @param values
	 *            Range
	 */
	public void setDefaultValue(Range values) {
		value = values;
	}

	/**
	 * get the value of the control, returns a Range object with minimum and
	 * maximum field values
	 * 
	 * @return values
	 */
	public Range getValue() {
		if (textMin == null || textMax == null)
			return value;
		value.setRange(textMin.getText(), textMax.getText());
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

		int fulltextfieldWidth = (width - width / 15);
		int textfieldWidth = (width - width / 15) / 3;

		JLabel textfieldBgMin = createJLabelImg("rangetextfield.png",
				textfieldWidth, height, 1f);
		textfieldWidth = textfieldBgMin.getIcon().getIconWidth();
		int textfieldHeight = textfieldBgMin.getIcon().getIconHeight();
		JLabel textfieldBgMax = createJLabelImg("rangetextfield.png",
				textfieldWidth, height, 1f);

		String fieldStr = Util.clipText("  " + fieldName + " ",
				resources.getFont("regular", 20f), fulltextfieldWidth);
		JLabel title = new JLabel(fieldStr);
		title.setFont(resources.getFont("regular", 20f));
		title.setForeground(resources.getTextColor());

		JPanel bgWrapperMin = new JPanel(new BorderLayout());
		bgWrapperMin.setOpaque(false);
		bgWrapperMin.setBounds(0, 0, textfieldWidth, textfieldHeight);
		bgWrapperMin.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		bgWrapperMin.setMaximumSize(bgWrapperMin.getPreferredSize());
		bgWrapperMin.add(textfieldBgMin, BorderLayout.CENTER);

		JPanel bgWrapperMax = new JPanel(new BorderLayout());
		bgWrapperMax.setOpaque(false);
		bgWrapperMax.setBounds(0, 0, textfieldWidth, textfieldHeight);
		bgWrapperMax.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		bgWrapperMax.setMaximumSize(bgWrapperMax.getPreferredSize());
		bgWrapperMax.add(textfieldBgMax, BorderLayout.CENTER);

		textMin = new JTextField(5);
		textMin.setFont(resources.getFont("italic", 15f));
		textMin.setForeground(resources.getTextColor());
		textMin.setText(value.getMin());
		textMin.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		textMin.setEditable(true);
		textMin.setFocusable(false);
		textMin.setFocusable(true);
		textMin.setBorder(null);
		textMin.setOpaque(false);
		textMin.setHorizontalAlignment(JTextField.CENTER);

		textMax = new JTextField(5);
		textMax.setFont(resources.getFont("italic", 15f));
		textMax.setForeground(resources.getTextColor());
		textMax.setText(value.getMax());
		textMax.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		textMax.setEditable(true);
		textMax.setFocusable(false);
		textMax.setFocusable(true);
		textMax.setBorder(null);
		textMax.setOpaque(false);
		textMax.setHorizontalAlignment(JTextField.CENTER);

		int indent = fulltextfieldWidth / 20;
		JPanel textWrapperMin = new JPanel(new BorderLayout());
		textWrapperMin.setOpaque(false);
		textWrapperMin.setBounds(indent, 0, textfieldWidth - indent * 2,
				textfieldHeight);
		textWrapperMin.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		textWrapperMin.setMaximumSize(textWrapperMin.getPreferredSize());
		textWrapperMin.add(textMin, BorderLayout.CENTER);

		JPanel textWrapperMax = new JPanel(new BorderLayout());
		textWrapperMax.setOpaque(false);
		textWrapperMax.setBounds(indent, 0, textfieldWidth - indent * 2,
				textfieldHeight);
		textWrapperMax.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		textWrapperMax.setMaximumSize(textWrapperMax.getPreferredSize());
		textWrapperMax.add(textMax, BorderLayout.CENTER);

		JLayeredPane textfieldWrapperMin = new JLayeredPane();
		textfieldWrapperMin.setOpaque(false);
		textfieldWrapperMin.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		textfieldWrapperMin.setMaximumSize(textfieldWrapperMin
				.getPreferredSize());
		textfieldWrapperMin.add(bgWrapperMin, Integer.valueOf(0), 0);
		textfieldWrapperMin.add(textWrapperMin, Integer.valueOf(1), 0);

		JLayeredPane textfieldWrapperMax = new JLayeredPane();
		textfieldWrapperMax.setOpaque(false);
		textfieldWrapperMax.setPreferredSize(new Dimension(textfieldWidth,
				textfieldHeight));
		textfieldWrapperMax.setMaximumSize(textfieldWrapperMax
				.getPreferredSize());
		textfieldWrapperMax.add(bgWrapperMax, Integer.valueOf(0), 0);
		textfieldWrapperMax.add(textWrapperMax, Integer.valueOf(1), 0);

		JLabel toLabel = new JLabel("TO");
		toLabel.setFont(resources.getFont("bold", 15f));
		toLabel.setForeground(resources.getPalette(0));
		toLabel.setHorizontalAlignment(JLabel.CENTER);

		JPanel rangefield = new JPanel(new BorderLayout());
		rangefield.setPreferredSize(new Dimension(fulltextfieldWidth,
				textfieldHeight));
		rangefield.setMaximumSize(rangefield.getPreferredSize());
		rangefield.setOpaque(false);
		rangefield.add(textfieldWrapperMin, BorderLayout.WEST);
		rangefield.add(toLabel, BorderLayout.CENTER);
		rangefield.add(textfieldWrapperMax, BorderLayout.EAST);

		JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titleWrapper.setOpaque(false);
		titleWrapper.add(title);

		JPanel rangefieldWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT,
				0, 0));
		rangefieldWrapper.setOpaque(false);
		rangefieldWrapper.add(rangefield);

		JPanel controlComponents = new JPanel();
		controlComponents.setOpaque(false);
		controlComponents.setLayout(new BoxLayout(controlComponents,
				BoxLayout.Y_AXIS));
		controlComponents.add(titleWrapper);
		controlComponents.add(rangefieldWrapper);

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
