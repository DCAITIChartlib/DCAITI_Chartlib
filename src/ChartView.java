

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

/**
 * 
 * @author Dima Mezinov (377581)
 * Zum Testen die Main-Methode ausführen
 */
public class ChartView {
	
	/**
	 * Chart Types Enum for extensibility
	 * 
	 *
	 */
	public enum ChartType {
		
	   LINECHART (1),
	   BARCHART (2),
	   PIECHART (3);
	   //...add more types
		
	   private final int typeCode;
		
		private ChartType (int typeCode) {
			this.typeCode = typeCode;
		}
		
	}
	
	
	
    private String viewName;
    private List<XYChart> charts = new ArrayList<XYChart>();
    private JFrame sw;
    
	public ChartView(String viewName) {
		//init blank UI with controls
		 this.viewName = viewName;
	}
	
	/**
	 * Add charts to UI
	 * @param Chart Type
	 * @param Chart name
	 * @xData x-Axis data
	 * @yData y-Axis Data
	 * TODO: input data type wrapper
	 */
	private void addChartToView(ChartType chartType,String name,double[]xData,double[]yData) {
		
		switch(chartType) {
		  
		case LINECHART: addLineChart(name,xData,yData);break;
		case BARCHART: addPieChart();break;
		case PIECHART:  addPieChart();break;
		//..... add more types
		default: 
		}
		
	}
	
	/**
	 * 
	 */
	private void addPieChart() {
		//TODO: add other chart types
		// for Architecture-Show purposes
	}

	/**
	 * Add Line chart to UI-Window
	 * @param yData 
	 * @param xData 
	 * @param type
	 */
	private void addLineChart(String name, double[] xData, double[] yData) {
		  XYChart chart = QuickChart.getChart("Sample Chart", "Seconds", "Y", name, xData, yData);
		  chart.setTitle(name);
		  this.charts.add(chart);
		 // new SwingWrapper(chart).displayChart();
		
	}
 
	
	private XYChart getChartByName(String chartName) {
		for(XYChart chart: this.charts) {
		 
			if (chart.getTitle().equalsIgnoreCase(chartName)) {
				return chart;
			}
		}
		return null;
	}
	
	/**
	 * Update data of particular chart
	 * @param chartName
	 * @param xData
	 * @param yData
	 */
    private void updateChartData(String chartName, double []xData, double[] yData) {
    	XYChart chart = this.getChartByName(chartName);
    	if(chart != null) {
   
    	chart.updateXYSeries(chartName, xData, yData, null);
    	 this.sw.repaint();
    	}
    }
	
    
	/**
	 * Function to display Charts after initialization
	 */
    private void displayCharts() {
 	  this.sw   = new SwingWrapper<XYChart>(charts).displayChartMatrix();
    }
    
    
   
    
	public static void main(String[] args) {
		 
		//Sample initial Data
		  double[] xData = new double[]{ 2.0, 1.0, 0.0 };
		  double[] yData = new double[] { 2.0, 1.0, 0.0 };
		 
		// Create UI
		  ChartView simpleChartUI = new ChartView("DCAITI Starter Task");
		  //Add Charts to UI
		  simpleChartUI.addChartToView(ChartType.LINECHART, "Vehicle Location", xData, yData);
		  simpleChartUI.addChartToView(ChartType.LINECHART, "Vehicle Velocity", xData, yData);
		  
		  
		  //Render UI
		  simpleChartUI.displayCharts();
		
		  //TODO: Bind Live data stream to chart
		  
	      //Test showcase for live data
		  //Close UI to kill Thread
		  Random r = new Random();
		  
		  Timer t = new Timer();
		  t.schedule(new TimerTask() {
			  
			  double time = 0.0; 
			  @Override
			  public void run() {
				  
				  double[] velocityArr =  r.doubles(3, 70.0, 120.0).toArray();
				  double[] timeArr = new double[] {time,time+1.0,time+2.0};
				  double[] locArr =  r.doubles(3, 5000.0, 7000.0).toArray();
				  
				   
				  double velocity = r.nextDouble() *100;
				  double location = r.nextDouble()*1000;
				  System.out.println(time +" "+velocity+" "+location);
				  
				  timeArr[0] = time;
				  velocityArr[0] = velocity;
				  locArr[0] = location;
				  simpleChartUI.updateChartData("Vehicle Location",timeArr,locArr);
				  simpleChartUI.updateChartData("Vehicle Velocity",timeArr,velocityArr); 
				  time++;
			  }
		  }, 0,100);
		  
		  
		  
	}

}
