package edu.cmu.cs214.hw5.visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import edu.cmu.cs214.hw5.gui.GUIResources;
import edu.cmu.cs214.hw5.gui.Util;

/**
 * Line Chart
 * 
 * @author Hizal
 *
 */
public class LineChart implements Chart {

	private String title;
	private ArrayList<DataPoint> data = new ArrayList<DataPoint>();
	private ArrayList<String> groups = new ArrayList<String>();
	private boolean detailed;

	/**
	 * initializes chart, sets defaults
	 */
	public LineChart() {
		detailed = false;
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
	public void setDetailed(boolean isDetailed) {
		detailed = isDetailed;
	}

	@Override
	public boolean isDetailed() {
		return detailed;
	}

	@Override
	public void addData(DataPoint data) {
		this.data.add(data);
		if (!groups.contains(data.getGroup()))
			groups.add(data.getGroup());
	}

	@Override
	public void addData(int index, DataPoint data) {
		this.data.add(index, data);
		if (!groups.contains(data.getGroup()))
			groups.add(data.getGroup());
	}

	@Override
	public void removeData(DataPoint data) {
		if (!this.data.contains(data))
			return;
		this.data.remove(data);
		removeDataGroup(data);
	}

	@Override
	public void removeData(int index) {
		if (index < 0 || index >= data.size())
			return;
		removeDataGroup(data.get(index));
		data.remove(index);
	}

	@Override
	public void removeAllData() {
		data.clear();
		groups.clear();
	}

	/**
	 * removes a data group name if no data exists belonging to it
	 * 
	 * param data possibly last datapoint in its group
	 */
	private void removeDataGroup(DataPoint data) {
		for (DataPoint dp : this.data)
			if (!dp.equals(data) && dp.getGroup().equals(data.getGroup()))
				return;
		groups.remove(data.getGroup());
	}

	@Override
	public DataPoint getData(int index) {
		if (index < 0 || index >= data.size())
			return null;
		return data.get(index);
	}

	@Override
	public ArrayList<DataPoint> getDataSet(String groupName) {
		if (groupName == null)
			return data;
		ArrayList<DataPoint> dataset = new ArrayList<DataPoint>();
		for (DataPoint dp : data)
			if (dp.getGroup().equals(groupName))
				dataset.add(dp);
		return dataset;
	}

	/**
	 * gets the dataset with the most data
	 * 
	 * @return group name
	 */
	private String getLargestDataSet() {
		String largest = null;
		for (String group : groups) {
			if (largest == null)
				largest = group;
			if (getDataSet(group).size() > getDataSet(largest).size()) {
				largest = group;
			}
		}
		return largest;
	}

	@Override
	public String getChartFile(String filename, GUIResources resources,
			Dimension boundary) {
		String template = Util.fileToString(resources.resources()
				+ "template_html/line_template.html");
		String chartJS = "'../" + resources.lib() + "Chartjs/Chart.js'";
		StringBuilder dataBuilder = new StringBuilder();
		StringBuilder labelBuilder = new StringBuilder();
		Color color;
		String largestDataSet = getLargestDataSet();
		if (largestDataSet != null) {
			for (int d = 0; d < this.data.size(); d++) {
				if (this.data.get(d).getGroup().equals(largestDataSet)) {
					if (d != 0)
					  labelBuilder.append(",");
					labelBuilder.append("'" + this.data.get(d).getLabel() + "'");
				}
			}
		}
		ArrayList<Color> groupColors = new ArrayList<Color>();
		for (int g = 0; g < groups.size(); g++) {
			if (g == 0)
				dataBuilder.append("{");
			else
				dataBuilder.append(",{");
			dataBuilder.append("label:'" + groups.get(g) + "'");
			int colorIndex = 1 - g;
			if (colorIndex < 0)
				colorIndex = 4;
			color = resources.getPalette(g % 5);
			if (groups.size() == 1)
				color = resources.getPalette(2);
			groupColors.add(color);
			float opacity = 0;
			if (detailed)
				opacity = 0.25f;
			dataBuilder.append(",fillColor: 'rgba(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + "," + opacity
					+ ")'");
			dataBuilder.append(",strokeColor: 'rgba(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + ",1)'");
			dataBuilder.append(",pointColor: 'rgba(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + ",1)'");
			dataBuilder.append(",pointStrokeColor: 'rgba(" + color.getRed() + ","
					+ color.getGreen() + "," + color.getBlue() + ",0)'");
			dataBuilder.append(",pointHighlightFill: '#fff'");
			dataBuilder.append(",pointHighlightStroke: '#fff'");
			boolean dataAdded = false;
			for (int d = 0; d < this.data.size(); d++)
				if (this.data.get(d).getGroup().equals(groups.get(g))) {
					if (!dataAdded)
						dataBuilder.append(",data:[" + this.data.get(d).getValue());
					else
						dataBuilder.append("," + this.data.get(d).getValue());
					dataAdded = true;
				}
			if (dataAdded)
				dataBuilder.append("]");
				dataBuilder.append("}");
		}
		StringBuilder legendBuilder = new StringBuilder();
		legendBuilder.append("<ul class='legend'>");
		for (int i = 0; i < groups.size(); i++) {
			color = groupColors.get(i);
			legendBuilder.append("<li><span style='background-color:rgb(" + color.getRed()
					+ "," + color.getGreen() + "," + color.getBlue()
					+ ")'></span>");
			legendBuilder.append("   " + groups.get(i) + "</li>");
		}
		legendBuilder.append("</ul>");
		String legend = legendBuilder.toString();
		if (groups.size() == 1)
			legend = "";
		boolean scaleStart = true;
		for (DataPoint dp : this.data)
			if (dp.getValue() > 100)
				scaleStart = false;

		template = template.replace("{CHART.JS}", chartJS);
		template = template.replace("{WIDTH}", boundary.width + "px");
		template = template.replace("{HEIGHT}", boundary.height + "px");
		template = template.replace("{SHOWSCALE}", detailed + "");
		template = template.replace("{SHOWLABEL}", detailed + "");
		template = template.replace("{SCALESTART}", scaleStart + "");
		template = template.replace("{LEGEND-MARGIN}", "0px");
		template = template.replace("{LABELS}", labelBuilder.toString());
		template = template.replace("{DATASETS}", dataBuilder.toString());
		template = template.replace("{STROKEWIDTH}", "3");
		template = template.replace("{LEGEND}", legend);

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
		return file;
	}
}
