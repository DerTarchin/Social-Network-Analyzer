package edu.cmu.cs214.hw5.visualizer;

import java.awt.Dimension;
import java.util.ArrayList;

import edu.cmu.cs214.hw5.gui.GUIResources;

/**
 * Chart interface
 * 
 * @author Hizal
 *
 */
public interface Chart {

	/**
	 * sets the chart title
	 * 
	 * @param title
	 *            title
	 */
	void setTitle(String title);

	/**
	 * gets the chart title
	 * 
	 * @return title
	 */
	String getTitle();

	/**
	 * adds new data to the chart
	 * 
	 * @param data
	 *            DataPoint
	 */
	void addData(DataPoint data);

	/**
	 * adds new data to the chart at the given order index
	 * 
	 * @param index
	 *            index
	 * @param data
	 *            DataPoint
	 */
	void addData(int index, DataPoint data);

	/**
	 * removes the provided datapoint from the chart
	 * 
	 * @param data
	 *            DataPoint
	 */
	void removeData(DataPoint data);

	/**
	 * removes the datapoint at the given order index
	 * 
	 * @param index
	 *            index
	 */
	void removeData(int index);

	/**
	 * removes all data from the chart
	 */
	void removeAllData();

	/**
	 * sets if the chart should be detailed (display labels and grid lines)
	 * 
	 * @param isDetailed
	 *            true shows labels and grid lines, false hides
	 */
	void setDetailed(boolean isDetailed);

	/**
	 * returns if chart is detailed or not
	 * 
	 * @return true if it is detailed, false otherwise
	 */
	boolean isDetailed();

	/**
	 * gets the datapoint at the given order index
	 * 
	 * @param index
	 *            index
	 * @return Datapoint
	 */
	DataPoint getData(int index);

	/**
	 * gets the entire dataset for the given groupname, or all data if groupname
	 * is null
	 * 
	 * @param groupName
	 *            group
	 * @return list of datapoints
	 */
	ArrayList<DataPoint> getDataSet(String groupName);

	/**
	 * returns the filepath for the HTML file generated, representing the chart
	 * 
	 * @param filename
	 *            filename to save to
	 * @param resources
	 *            GUIResources
	 * @param boundary
	 *            dimensions of chart
	 * @return filepath
	 */
	String getChartFile(String filename, GUIResources resources,
			Dimension boundary);
}
