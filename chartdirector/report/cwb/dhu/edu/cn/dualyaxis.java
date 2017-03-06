package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class dualyaxis implements DemoModule
{
    //Name of demo program
    public String toString() { return "Dual Y-Axis"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data0 = {0.05, 0.06, 0.48, 0.1, 0.01, 0.05};
        double[] data1 = {100, 125, 265, 147, 67, 105};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};

        // Create a XYChart object of size 300 x 180 pixels
        XYChart c = new XYChart(300, 180);

        // Set the plot area at (50, 20) and of size 200 x 130 pixels
        c.setPlotArea(50, 20, 200, 130);

        // Add a title to the chart using 8 pts Arial Bold font
        c.addTitle("Independent Y-Axis Demo", "Arial Bold", 8);

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Add a title to the primary (left) y axis
        c.yAxis().setTitle("Packet Drop Rate (pps)");

        // Set the axis, label and title colors for the primary y axis to red
        // (0xc00000) to match the first data set
        c.yAxis().setColors(0xc00000, 0xc00000, 0xc00000);

        // Add a title to the secondary (right) y axis
        c.yAxis2().setTitle("Throughtput (MBytes)");

        // set the axis, label and title colors for the primary y axis to green
        // (0x008000) to match the second data set
        c.yAxis2().setColors(0x008000, 0x008000, 0x008000);

        // Add a line layer to for the first data set using red (0xc00000) color with
        // a line width to 3 pixels
        LineLayer lineLayer = c.addLineLayer(data0, 0xc00000);
        lineLayer.setLineWidth(3);

        // tool tip for the line layer
        lineLayer.setHTMLImageMap("", "",
            "title='Packet Drop Rate on {xLabel}: {value} pps'");

        // Add a bar layer to for the second data set using green (0x00C000) color.
        // Bind the second data set to the secondary (right) y axis
        BarLayer barLayer = c.addBarLayer(data1, 0x00c000);
        barLayer.setUseYAxis2();

        // tool tip for the bar layer
        barLayer.setHTMLImageMap("", "",
            "title='Throughput on {xLabel}: {value} MBytes'");

        // Output the chart
        viewer.setImage(c.makeImage());

        // include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new dualyaxis();

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

