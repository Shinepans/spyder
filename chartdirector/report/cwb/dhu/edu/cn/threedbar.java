package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class threedbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "3D Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data = {85, 156, 179.5, 211, 123};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 300 x 280 pixels
        XYChart c = new XYChart(300, 280);

        // Set the plotarea at (45, 30) and of size 200 x 200 pixels
        c.setPlotArea(45, 30, 200, 200);

        // Add a title to the chart
        c.addTitle("Weekly Server Load");

        // Add a title to the y axis
        c.yAxis().setTitle("MBytes");

        // Add a title to the x axis
        c.xAxis().setTitle("Work Week 25");

        // Add a bar chart layer with green (0x00ff00) bars using the given data
        c.addBarLayer(data, 0x00ff00).set3D();

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel}: {value} MBytes'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new threedbar();

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

