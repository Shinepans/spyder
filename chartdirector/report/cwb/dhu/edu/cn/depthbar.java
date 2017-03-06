package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class depthbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Depth Bar Chart"; }

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

        // Add a legend box at (405, 100)
        c.addLegend(405, 100);

        // Add a title to the chart
        c.addTitle("Weekday Network Load");

        // Add a title to the y axis. Draw the title upright (font angle = 0)
        c.yAxis().setTitle("Average\nWorkload\n(MBytes\nPer Hour)").setFontAngle(0);

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        // Add three bar layers, each representing one data set. The bars are drawn
        // in semi-transparent colors.
        c.addBarLayer(data0, 0x808080ff, "Server # 1", 5);
        c.addBarLayer(data1, 0x80ff0000, "Server # 2", 5);
        c.addBarLayer(data2, 0x8000ff00, "Server # 3", 5);

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
        DemoModule demo = new depthbar();

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

