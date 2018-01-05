package edu.cmu.cs214.hw5.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * Scrabble GUI Util class is a group of useful methods used by the classes that
 * make up the GUI
 * 
 * @author Hizal
 *
 */
public class Util {
	/**
	 * fixes PNG transparency issues if present
	 * 
	 * @param image
	 *            image to check
	 * @param label
	 *            jlabel it belongs to
	 * @param opacity
	 *            opacity to change it to
	 * @return fixed ImageIcon
	 */
	public static ImageIcon fixImage(ImageIcon image, JLabel label,
			float opacity) {
		BufferedImage buffered = new BufferedImage(image.getIconWidth(),
				image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = buffered.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				opacity));
		g2.drawImage(image.getImage(), 0, 0, image.getIconWidth(),
				image.getIconHeight(), label);
		g2.dispose();

		return new ImageIcon(buffered);
	}

	/**
	 * gets the font by the filename given
	 * 
	 * @param name
	 *            filepath of the font
	 * @param resources
	 *            GUIResources
	 * @return Font object
	 */
	public static Font getFont(String name, String resources) {
		Font font;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(resources
					+ name + ".ttf"));
			GraphicsEnvironment genv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			return font;
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * author: http://stackoverflow.com/questions/6714045/how-to-resize-jlabel-
	 * imageicon
	 * 
	 * scales images and returns an Image object
	 * 
	 * @param srcImg
	 *            source Image to scale
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @return new Image object
	 */
	public static Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	/**
	 * author:
	 * http://stackoverflow.com/questions/10245220/java-image-resize-maintain
	 * -aspect-ratio
	 * 
	 * gets a scaled dimention that fits within the given boundary, fitting into
	 * the smallest side
	 * 
	 * @param imgSize
	 *            size of original image to scale
	 * @param boundary
	 *            boundary to fit inside
	 * @return scaled dimension boundaries
	 */
	public static Dimension getScaledDimension(Dimension imgSize,
			Dimension boundary) {

		int originalWidth = imgSize.width;
		int originalHeight = imgSize.height;
		int boundWidth = boundary.width;
		int boundHeight = boundary.height;
		int newWidth = originalWidth;
		int newHeight = originalHeight;

		// first check if we need to scale width
		if (originalWidth > boundWidth) {
			// scale width to fit
			newWidth = boundWidth;
			// scale height to maintain aspect ratio
			newHeight = (newWidth * originalHeight) / originalWidth;
		}

		// then check if we need to scale even with the new height
		if (newHeight > boundHeight) {
			// scale height to fit instead
			newHeight = boundHeight;
			// scale width to maintain aspect ratio
			newWidth = (newHeight * originalWidth) / originalHeight;
		}
		return new Dimension(newWidth, newHeight);
	}

	/**
	 * taken from: http://pastebin.com/i6bBn3K7
	 * 
	 * thread sleep method for non-thread operations. Not used, I believe.
	 * 
	 * @param millis
	 *            milliseconds to sleep
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Formats the scrollbar to a custom UI
	 * 
	 * Custom scrollbar design code was found and modified from
	 * http://stackoverflow
	 * .com/questions/16373459/java-jscrollbar-design/16375805#16375805
	 * 
	 * @param pane
	 *            scrollpane to format
	 * @param resources
	 *            GUIResources
	 */
	public static void formatScrollBar(JScrollPane pane,
			final GUIResources resources) {
		pane.setLayout(new ScrollPaneLayout() {
			@Override
			public void layoutContainer(Container parent) {
				JScrollPane scrollPane = (JScrollPane) parent;

				Rectangle availR = scrollPane.getBounds();
				availR.x = 0;
				availR.y = 0;

				Insets insets = parent.getInsets();
				availR.x = insets.left;
				availR.y = insets.top;
				availR.width -= insets.left + insets.right;
				availR.height -= insets.top + insets.bottom;

				Rectangle vsbR = new Rectangle();
				vsbR.width = 12;
				vsbR.height = availR.height;
				vsbR.x = availR.x + availR.width - vsbR.width;
				vsbR.y = availR.y;

				if (viewport != null) {
					viewport.setBounds(availR);
				}
				if (vsb != null) {
					vsb.setVisible(true);
					vsb.setBounds(vsbR);
				}
			}
		});
		pane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			private final Dimension d = new Dimension();

			@Override
			protected JButton createDecreaseButton(int orientation) {
				return new JButton() {
					private static final long serialVersionUID = 1L;

					@Override
					public Dimension getPreferredSize() {
						return d;
					}
				};
			}

			@Override
			protected JButton createIncreaseButton(int orientation) {
				return new JButton() {
					private static final long serialVersionUID = 1L;

					@Override
					public Dimension getPreferredSize() {
						return d;
					}
				};
			}

			@Override
			protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				Color color = null;
				JScrollBar sb = (JScrollBar) c;

				int red = resources.getPalette(0).getRed();
				int green = resources.getPalette(0).getGreen();
				int blue = resources.getPalette(0).getBlue();
				if (!sb.isEnabled() || r.width > r.height) {
					return;
				} else if (isDragging) {
					color = resources.getPalette(0);
				} else if (isThumbRollover()) {
					color = new Color(red, green, blue, 185);
				} else {
					color = new Color(red, green, blue, 100);
				}
				g2.setPaint(color);
				g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
				g2.dispose();
			}

			@Override
			protected void setThumbBounds(int x, int y, int width, int height) {
				super.setThumbBounds(x, y, width, height);
				scrollbar.repaint();
			}

			@Override
			protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				Color color = null;
				g2.setPaint(color);
				g2.dispose();
			}
		});
	}

	/**
	 * formats the JSlider slider
	 * 
	 * @param slider
	 *            JSlider
	 * @param resources
	 *            GUIResources
	 */
	public static void formatSlider(JSlider slider, final GUIResources resources) {
		slider.setUI(new BasicSliderUI(slider) {
			private BasicStroke stroke = new BasicStroke(4f,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f);

			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				super.paint(g, c);
			}

			@Override
			protected Dimension getThumbSize() {
				return new Dimension(15, 15);
			}

			@Override
			public void paintTrack(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Stroke old = g2d.getStroke();
				g2d.setStroke(stroke);
				g2d.setPaint(resources.getPalette(0));
				if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
					g2d.drawLine(trackRect.x, trackRect.y + trackRect.height
							/ 2, trackRect.x + trackRect.width, trackRect.y
							+ trackRect.height / 2);
				} else {
					g2d.drawLine(trackRect.x + trackRect.width / 2,
							trackRect.y, trackRect.x + trackRect.width / 2,
							trackRect.y + trackRect.height);
				}
				g2d.setStroke(old);
			}

			@Override
			public void paintThumb(Graphics g) {
				Color color;
				if (isDragging()) {
					color = resources.getTextColor();
				} else {
					color = resources.getPalette(0);
				}
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setPaint(color);
				int radius = thumbRect.height;
				g2d.fillOval(thumbRect.x, thumbRect.y, radius, radius);
			}
		});
	}

	/**
	 * shortens text if it exceeds the given width (appends "..." to end of
	 * string)
	 * 
	 * @param text
	 *            text to check
	 * @param font
	 *            font to check with
	 * @param width
	 *            width to compare with
	 * @return new string
	 */
	public static String clipText(String text, Font font, int width) {
		JLabel test = new JLabel(text);
		test.setFont(font);
		while (test.getPreferredSize().width > width) {
			text = text.substring(0, text.length() - 1);
			test.setText(text + "...");
		}
		return test.getText();
	}

	/**
	 * converts the contents of a file into a string
	 * 
	 * @param file
	 *            file to read
	 * @return contents
	 */
	public static String fileToString(String file) {
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = contentBuilder.toString();
		return content;
	}

	/**
	 * gets the absolute path / URL of a given file
	 * 
	 * @param name
	 *            File
	 * @return path
	 */
	public static String getURLFromFile(String name) {
		File file = new File(name);
		String path = file.getAbsolutePath();
		if (path.charAt(0) != '/')
			path = "/" + path;
		return "file://" + path;
	}
}
