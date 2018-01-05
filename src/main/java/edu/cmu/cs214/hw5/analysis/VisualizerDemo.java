package edu.cmu.cs214.hw5.analysis;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.cmu.cs214.hw5.core.AnalysisPlugin;
import edu.cmu.cs214.hw5.core.Data;
import edu.cmu.cs214.hw5.core.Filter;
import edu.cmu.cs214.hw5.visualizer.BarChart;
import edu.cmu.cs214.hw5.visualizer.Chart;
import edu.cmu.cs214.hw5.visualizer.Control;
import edu.cmu.cs214.hw5.visualizer.ControlPanel;
import edu.cmu.cs214.hw5.visualizer.DataPoint;
import edu.cmu.cs214.hw5.visualizer.DonutChart;
import edu.cmu.cs214.hw5.visualizer.LineChart;
import edu.cmu.cs214.hw5.visualizer.PieChart;
import edu.cmu.cs214.hw5.visualizer.PolarChart;
import edu.cmu.cs214.hw5.visualizer.RadarChart;
import edu.cmu.cs214.hw5.visualizer.Range;
import edu.cmu.cs214.hw5.visualizer.RangeControl;
import edu.cmu.cs214.hw5.visualizer.SliderControl;
import edu.cmu.cs214.hw5.visualizer.TextFieldControl;
import edu.cmu.cs214.hw5.visualizer.ToggleControl;
import edu.cmu.cs214.hw5.visualizer.Visualizer;

/**
 * A demo usage of the Visualizer library tools. Note, this analysis plugin does
 * not actually take in any posts or use filters. This is purely to demonstrate
 * the use of the Visualizer plugin.
 * 
 * @author Hizal
 *
 */
public class VisualizerDemo implements AnalysisPlugin {
	@SuppressWarnings("rawtypes")
  private ArrayList<Control> controls = new ArrayList<Control>();
	private ArrayList<String> filters = new ArrayList<String>();
	private Visualizer vis;
	private ControlPanel cp2;

	private ArrayList<Chart> charts = new ArrayList<Chart>();

	@Override
	public void doAnalysis(Data data) {
//		for (String f : filters)
//			System.out.println(f);
		for (Chart chart : charts)
			if (chart.isDetailed())
				chart.setDetailed(false);
			else
				chart.setDetailed(true);
		vis.refresh();
	}

	@Override
	public void onRegister(JPanel panel) {
		vis = new Visualizer();
		vis.setTitle(getName());

		// FIRST CONTROL PANE
		ControlPanel cp = new ControlPanel();
		cp.setLabel("Basic Controls");
		vis.addControlPanel(cp);

		TextFieldControl tfc = new TextFieldControl();
		tfc.setLabel("TextField Control");
		tfc.setDefaultValue("new default");
		cp.addControl(tfc);
		controls.add(tfc);

		ToggleControl tc = new ToggleControl();
		tc.setLabel("Toggle Control");
		cp.addControl(tc);
		controls.add(tc);

		SliderControl sc = new SliderControl(0, 100);
		sc.setLabel("Slider Control");
		sc.setDefaultValue(75);
		cp.addControl(sc);
		controls.add(sc);

		RangeControl rc = new RangeControl();
		rc.setLabel("Range Control");
		rc.setDefaultValue(new Range("min", "max"));
		cp.addControl(rc);
		controls.add(rc);

		// SECOND CONTROL PANE
		cp2 = new ControlPanel();
		cp2.setLabel("Toggle Pane");
		vis.addControlPanel(cp2);

		for (int i = 0; i < 30; i++) {
			ToggleControl tc2 = new ToggleControl();
			tc2.setLabel("Toggle Item  " + (i + 1));
			if (i % 3 == 0)
				tc2.setDefaultValue(true);
			cp2.addControl(tc2);
			controls.add(tc2);
		}

		Chart chart1 = new BarChart();
		chart1.setTitle("Example Chart 1");
		chart1.setDetailed(false);
		chart1.addData(new DataPoint("Item 0", "Group 1", 1));
		chart1.addData(new DataPoint("Item 1", "Group 1", 2));
		chart1.addData(new DataPoint("Item 2", "Group 1", 2));
		chart1.addData(new DataPoint("Item 3", "Group 1", 4));
		chart1.addData(new DataPoint("Item 4", "Group 1", 5));
		chart1.addData(new DataPoint("Item 0", "Group 2", 5));
		chart1.addData(new DataPoint("Item 1", "Group 2", 4));
		chart1.addData(new DataPoint("Item 2", "Group 2", 3));
		chart1.addData(new DataPoint("Item 3", "Group 2", 2));
		chart1.addData(new DataPoint("Item 4", "Group 2", 1));
		chart1.addData(new DataPoint("Item 0", "Group 3", 2));
		chart1.addData(new DataPoint("Item 1", "Group 3", 1));
		chart1.addData(new DataPoint("Item 2", "Group 3", 4));
		chart1.addData(new DataPoint("Item 3", "Group 3", 5));
		chart1.addData(new DataPoint("Item 4", "Group 3", 3));
		charts.add(chart1);
		 vis.addChart(chart1);

		Chart chart2 = new LineChart();
		chart2.setTitle("Example Chart 2");
		chart2.setDetailed(false);
		chart2.addData(new DataPoint("Item 0", "Group 1", 1));
		chart2.addData(new DataPoint("Item 1", "Group 1", 2));
		chart2.addData(new DataPoint("Item 2", "Group 1", 4));
		chart2.addData(new DataPoint("Item 3", "Group 1", 3));
		chart2.addData(new DataPoint("Item 4", "Group 1", 5));
		chart2.addData(new DataPoint("Item 0", "Group 2", 2));
		chart2.addData(new DataPoint("Item 1", "Group 2", 3));
		chart2.addData(new DataPoint("Item 2", "Group 2", 3));
		chart2.addData(new DataPoint("Item 3", "Group 2", 4));
		chart2.addData(new DataPoint("Item 4", "Group 2", 2));
		charts.add(chart2);
		 vis.addChart(chart2);

		Chart chart3 = new RadarChart();
		chart3.setTitle("Radar Chart");
		chart3.setDetailed(false);
		chart3.addData(new DataPoint("Item 0", "Group 1", 1));
		chart3.addData(new DataPoint("Item 1", "Group 1", 2));
		chart3.addData(new DataPoint("Item 2", "Group 1", 4));
		chart3.addData(new DataPoint("Item 3", "Group 1", 3));
		chart3.addData(new DataPoint("Item 4", "Group 1", 5));
		chart3.addData(new DataPoint("Item 0", "Group 2", 2));
		chart3.addData(new DataPoint("Item 1", "Group 2", 3));
		chart3.addData(new DataPoint("Item 2", "Group 2", 3));
		chart3.addData(new DataPoint("Item 3", "Group 2", 4));
		chart3.addData(new DataPoint("Item 4", "Group 2", 2));
		charts.add(chart3);
//		vis.addChart(chart3);
		// vis.addChart(1, chart3);

		Chart chart4 = new PolarChart();
		chart4.setTitle("Polar Chart");
		chart4.setDetailed(false);
		chart4.addData(new DataPoint("Item 0", null, 1));
		chart4.addData(new DataPoint("Item 1", null, 2));
		chart4.addData(new DataPoint("Item 2", null, 4));
		chart4.addData(new DataPoint("Item 3", null, 3));
		chart4.addData(new DataPoint("Item 4", null, 5));
//		vis.addChart(chart4);

		Chart chart5 = new DonutChart();
		chart5.setTitle("Donut Chart");
		chart5.setDetailed(false);
		chart5.addData(new DataPoint("Item 0", null, 1));
		chart5.addData(new DataPoint("Item 1", null, 2));
		chart5.addData(new DataPoint("Item 2", null, 4));
		chart5.addData(new DataPoint("Item 3", null, 3));
		chart5.addData(new DataPoint("Item 4", null, 5));
//		vis.addChart(chart5);

		Chart chart6 = new PieChart();
		chart6.setTitle("Pie Chart");
		chart6.setDetailed(false);
		chart6.addData(new DataPoint("Item 0", null, 1));
		chart6.addData(new DataPoint("Item 1", null, 2));
		chart6.addData(new DataPoint("Item 2", null, 4));
		chart6.addData(new DataPoint("Item 3", null, 3));
		chart6.addData(new DataPoint("Item 4", null, 5));
//		vis.addChart(chart6);

		vis.display();
		panel.add(vis);
	}

	@SuppressWarnings("rawtypes")
  @Override
	public List<Filter> getFilters() {
		filters.clear();
		for (Control c : controls)
			filters.add(c.getLabel() + ": " + c.getValue());
		return null;
	}

	@Override
	public String getName() {
		return "Visualizer Demo";
	}

}
