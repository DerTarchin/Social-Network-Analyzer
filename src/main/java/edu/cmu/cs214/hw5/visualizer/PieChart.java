package edu.cmu.cs214.hw5.visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Pie Chart
 * 
 * >> HAS KNOWN JAVA SWING DISPLAY GLITCHES (involving javascript)
 * 
 * @author Hizal
 *
 */
public class PieChart implements Chart {

	private String title;
	private ArrayList<DataPoint> data = new ArrayList<DataPoint>();
	private Random rand = new Random();
	private boolean detailed;

	@Override
	public void setDetailed(boolean isDetailed) {
		detailed = isDetailed;
	}

	@Override
	public boolean isDetailed() {
		return detailed;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void addData(DataPoint data) {
		this.data.add(data);
	}

	@Override
	public void addData(int index, DataPoint data) {
		this.data.add(index, data);
	}

	@Override
	public void removeData(DataPoint data) {
		if (!this.data.contains(data))
			return;
		this.data.remove(data);
	}

	@Override
	public void removeData(int index) {
		if (index < 0 || index >= data.size())
			return;
		data.remove(index);
	}

	@Override
	public void removeAllData() {
		data.clear();
	}

	@Override
	public DataPoint getData(int index) {
		if (index < 0 || index >= data.size())
			return null;
		return data.get(index);
	}

	@Override
	public ArrayList<DataPoint> getDataSet(String groupName) {
		if (groupName != null)
			for (DataPoint dp : data)
				if (!dp.getGroup().equals(groupName))
					return null;
		return data;
	}

	@Override
	public String getChartFile(String filename, GUIResources resources,
			Dimension boundary) {
		String template = Util.fileToString(resources.resources()
				+ "template_html/pie_template.html");
		String chartJS = "'../" + resources.lib() + "Chartjs/Chart.js'";
		StringBuffer dataBuilder = new StringBuffer();
		for (int i = 0; i < this.data.size(); i++) {
			DataPoint dp = this.data.get(i);
			if (i != 0)
				dataBuilder.append(",");
			Color color = resources.getPalette(i % 5);
			dataBuilder.append("{value:" + dp.getValue());
			dataBuilder.append(",color:'rgba(" + color.getRed() + "," + color.getGreen()
					+ "," + color.getBlue() + ",1)'");
			dataBuilder.append(",highlight:'rgba(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + ",0.5)'");
			dataBuilder.append(",label:'" + dp.getLabel() + "'}");
		}

		template = template.replace("{CHART.JS}", chartJS + "'");
		template = template.replace("{WIDTH}", boundary.width + "px");
		template = template.replace("{HEIGHT}", boundary.height + "px");
		template = template.replace("{DATA}", dataBuilder.toString());
		template = template.replace("{STROKEWIDTH}", "3");

		String file = resources.bin() + filename + ".html";
		FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
			fWriter = new FileWriter(file);
			writer = new BufferedWriter(fWriter);
			writer.write(template);
			writer.close(); // make sure you close the writer object
		} catch (Exception e) {
			e.printStackTrace();
		}
		File deleteMe = new File(file);
		deleteMe.deleteOnExit();

		// TO HANDLE GLITCH ERRORS, REDIRECTING TO REAL BROWSER
		String redirect = Util.fileToString(resources.resources()
				+ "template_html/redirect_template.html");
		String webLink = "'https://" + resources.bin() + filename + ".html'";
		String contLink = "'" + filename + ".html'";
		String webImg = "'../" + resources.resources() + "web.png'";
		String contImg = "'../" + resources.resources() + "cont.png'";
		redirect = redirect.replace("{WEBLINK}", webLink);
		redirect = redirect.replace("{CONTLINK}", contLink);
		redirect = redirect.replace("{WEBIMG}", webImg);
		redirect = redirect.replace("{CONTIMG}", contImg);

		file = resources.bin() + "redirect_donut_" + rand.nextInt(1000)
				+ ".html";
		try {
			fWriter = new FileWriter(file);
			writer = new BufferedWriter(fWriter);
			writer.write(redirect);
			writer.close(); // make sure you close the writer object
		} catch (Exception e) {
			e.printStackTrace();
		}

		deleteMe = new File(file);
		deleteMe.deleteOnExit();
		return file;
	}
}
