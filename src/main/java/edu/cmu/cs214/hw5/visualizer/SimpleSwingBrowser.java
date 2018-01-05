package edu.cmu.cs214.hw5.visualizer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import edu.cmu.cs214.hw5.gui.Util;
import static javafx.concurrent.Worker.State.FAILED;

/**
 * HTML/Javascript browser window, displays HTML files and web pages
 * 
 * @author Hizal
 *
 */
public class SimpleSwingBrowser extends JPanel {

	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;

	private final JPanel panel = new JPanel(new BorderLayout());

	private final JButton btnGo = new JButton("Go");
	private final JTextField txtURL = new JTextField();

	/**
	 * initializes javafx browser
	 */
	public SimpleSwingBrowser() {
		initComponents();
	}

	/**
	 * initializes javafx browser and components
	 */
	private void initComponents() {
		createScene();

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadURL(txtURL.getText());
			}
		};

		btnGo.addActionListener(al);
		txtURL.addActionListener(al);

		JPanel topBar = new JPanel(new BorderLayout(5, 0));
		topBar.setOpaque(false);
		topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		topBar.add(txtURL, BorderLayout.CENTER);
		topBar.add(btnGo, BorderLayout.EAST);

		// panel.add(topBar, BorderLayout.NORTH);
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.setOpaque(false);

		setLayout(new BorderLayout());
		setOpaque(false);
		add(panel, BorderLayout.CENTER);
	}

	/**
	 * creates javafx scene
	 */
	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.locationProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> ov,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										txtURL.setText(newValue);
									}
								});
							}
						});
				engine.getLoadWorker().exceptionProperty()
						.addListener(new ChangeListener<Throwable>() {

							public void changed(
									ObservableValue<? extends Throwable> o,
									Throwable old, final Throwable value) {
								if (engine.getLoadWorker().getState() == FAILED)
									System.out.println("Error loading page");
							}
						});
				engine.locationProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, String newValue) {
								Desktop d = Desktop.getDesktop();
								try {
									URI address = new URI(observable.getValue());
									if (address.toString().contains("https://")) {
										String name = address.toString()
												.replace("https://", "");
										String path = Util.getURLFromFile(name);
										d.browse(new URI(path));
									}
								} catch (IOException | URISyntaxException e) {
									e.printStackTrace();
								}
							}
						});
				jfxPanel.setScene(new Scene(view));
			}
		});
	}

	/**
	 * display the given URL
	 * 
	 * @param url
	 *            URL
	 */
	public void loadURL(final String url) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);

				if (tmp == null) {
					tmp = toURL("http://" + url);
				}
				engine.load(tmp);
			}
		});
	}

	/**
	 * converts a string path to URL
	 * 
	 * @param str
	 *            path
	 * @return URL
	 */
	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
}
