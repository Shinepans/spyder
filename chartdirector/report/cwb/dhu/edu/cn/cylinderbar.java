package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class cylinderbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Cylinder Bar Shape"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data = {85, 156, 179.5, 211, 123};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 400 x 240 pixels.
        XYChart c = new XYChart(400, 240);

        // Add a title to the chart using 14 pts Times Bold Italic font
        c.addTitle("Weekly Server Load", "Times New Roman Bold Italic", 14);

        // Set the plotarea at (45, 40) and of 300 x 160 pixels in size. Use
        // alternating light grey (f8f8f8) / white (ffffff) background.
        c.setPlotArea(45, 40, 300, 160, 0xf8f8f8, 0xffffff);

        // Add a multi-color bar chart layer
        BarLayer layer = c.addBarLayer3(data);

        // Set layer to 3D with 10 pixels 3D depth
        layer.set3D(10);

        // Set bar shape to circular (cylinder)
        layer.setBarShape(Chart.CircleShape);

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Add a title to the y axis
        c.yAxis().setTitle("MBytes");

        // Add a title to the x axis
        c.xAxis().setTitle("Work Week 25");

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
        DemoModule demo = new cylinderbar();

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

