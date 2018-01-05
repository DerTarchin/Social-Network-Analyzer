package edu.cmu.cs214.hw5.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import edu.cmu.cs214.hw5.core.DataPlugin;

/**
 * Apply button object
 * 
 * @author Hizal
 *
 */
public class ApplyButton extends JPanel {
	private DataPlugin dataplugin;
	private GUIResources resources;

	/**
	 * initialize the button
	 * 
	 * @param resources
	 *            GUIResources
	 * @param dataplugin
	 *            dataplugin the button is assigned to
	 */
	public ApplyButton(GUIResources resources, DataPlugin dataplugin) {
		this.dataplugin = dataplugin;
		this.resources = resources;
		draw();
	}

	/**
	 * gets the assigned dataplugin
	 * @return dataplugin
	 */
	public DataPlugin getDataPlugin() {
		return dataplugin;
	}

	/**
	 * draws the apply button
	 */
	private void draw() {
		int width = resources.getSize().width / 12;
		int height = resources.getSize().height / 13;

		JLabel applybg = createJLabelImg("apply.png", width, height, 1f);
		int applyWidth = applybg.getIcon().getIconWidth();
		int applyHeight = applybg.getIcon().getIconHeight();

		JPanel applybgWrapper = new JPanel(new BorderLayout());
		applybgWrapper.setOpaque(false);
		applybgWrapper.setBounds(0, 0, applyWidth, applyHeight);
		applybgWrapper.setPreferredSize(new Dimension(applyWidth, applyHeight));
		applybgWrapper.setMaximumSize(applybgWrapper.getPreferredSize());
		applybgWrapper.add(applybg, BorderLayout.CENTER);

		JLabel text = new JLabel("Apply");
		text.setFont(resources.getFont("semibold", 20f));
		text.setForeground(resources.getPalette(1));
		text.setHorizontalAlignment(JLabel.CENTER);
		text.setVerticalAlignment(JLabel.CENTER);
		text.setVerticalTextPosition(JLabel.CENTER);

		JPanel textWrapper = new JPanel(new BorderLayout());
		textWrapper.setOpaque(false);
		textWrapper.setBounds(0, -applyHeight / 20, applyWidth, applyHeight);
		textWrapper.setPreferredSize(new Dimension(applyWidth, applyHeight));
		textWrapper.setMaximumSize(textWrapper.getPreferredSize());
		textWrapper.add(text, BorderLayout.CENTER);

		JLayeredPane textfieldWrapper = new JLayeredPane();
		textfieldWrapper.setOpaque(false);
		textfieldWrapper.setPreferredSize(new Dimension(applyWidth * 2,
				applyHeight));
		textfieldWrapper.setMaximumSize(textfieldWrapper.getPreferredSize());
		textfieldWrapper.add(applybgWrapper, Integer.valueOf(0), 0);
		textfieldWrapper.add(textWrapper, Integer.valueOf(1), 0);

		JPanel apply = new JPanel(new BorderLayout());
		apply.setOpaque(false);
		apply.setMaximumSize(apply.getPreferredSize());
		apply.add(textfieldWrapper, BorderLayout.CENTER);
		apply.setCursor(new Cursor(Cursor.HAND_CURSOR));

		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		setOpaque(false);
		add(apply);
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
