package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class stackedbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Stacked Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data0 = {100, 125, 245, 147, 67};
        double[] data1 = {85, 156, 179, 211, 123};
        double[] data2 = {97, 87, 56, 267, 157};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 500 x 320 pixels
        XYChart c = new XYChart(500, 320);

        // Set the plotarea at (100, 40) and of size 280 x 240 pixels
        c.setPlotArea(100, 40, 280, 240);

        // Add a legend box at (400, 100)
        c.addLegend(400, 100);

        // Add a title to the chart using 14 points Times Bold Itatic font
        c.addTitle("Weekday Network Load", "Times New Roman Bold Italic", 14);

        // Add a title to the y axis. Draw the title upright (font angle = 0)
        c.yAxis().setTitle("Average\nWorkload\n(MBytes\nPer Hour)").setFontAngle(0);

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        // Add a stacked bar layer and set the layer 3D depth to 8 pixels
        BarLayer layer = c.addBarLayer2(Chart.Stack, 8);

        // Add the three data sets to the bar layer
        layer.addDataSet(data0, 0xff8080, "Server # 1");
        layer.addDataSet(data1, 0x80ff80, "Server # 2");
        layer.addDataSet(data2, 0x8080ff, "Server # 3");

        // Enable bar label for the whole bar
        layer.setAggregateLabelStyle();

        // Enable bar label for each segment of the stacked bar
        layer.setDataLabelStyle();

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{dataSetName} on {xLabel}: {value} MBytes/hour'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new stackedbar();

        //Create and set up the main window
        JFrame frame = new JFrame(demo.toString());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);} });
        frame.getContentPane().setBackground(Color.white);

        // Create the chart and put them in the content pane
        ChartViewer viewer = new ChartViewer();
        demo.createChart(viewer, 0);
        frame.getContentPane().add(viewer);

        // Display the window
        frame.pack();
        frame.setVisible(true);
    }
}

