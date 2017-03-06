package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class axisscale implements DemoModule
{
    //Name of demo program
    public String toString() { return "Y-Axis Scaling"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 5; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data = {5.5, 3.5, -3.7, 1.7, -1.4, 3.3};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};

        // Create a XYChart object of size 200 x 190 pixels
        XYChart c = new XYChart(200, 190);

        // Set the plot area at (30, 20) and of size 140 x 140 pixels
        c.setPlotArea(30, 20, 140, 140);

        // Configure the axis as according to the input parameter
        if (index == 0) {
            c.addTitle("No Axis Extension", "Arial", 8);
        } else if (index == 1) {
            c.addTitle("Top/Bottom Extensions = 0/0", "Arial", 8);
            // Reserve 20% margin at top of plot area when auto-scaling
            c.yAxis().setAutoScale(0, 0);
        } else if (index == 2) {
            c.addTitle("Top/Bottom Extensions = 0.2/0.2", "Arial", 8);
            // Reserve 20% margin at top and bottom of plot area when auto-scaling
            c.yAxis().setAutoScale(0.2, 0.2);
        } else if (index == 3) {
            c.addTitle("Axis Top Margin = 15", "Arial", 8);
            // Reserve 15 pixels at top of plot area
            c.yAxis().setMargin(15);
        } else {
            c.addTitle("Manual Scale -5 to 10", "Arial", 8);
            // Set the y axis to scale from -5 to 10, with ticks every 5 units
            c.yAxis().setLinearScale(-5, 10, 5);
        }

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        // Add a color bar layer using the given data. Use a 1 pixel 3D border for
        // the bars.
        c.addBarLayer3(data).setBorderColor(-1, 1);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='ROI for {xLabel}: {value}%'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new axisscale();

        //Create and set up the main window
        JFrame frame = new JFrame(demo.toString());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);} });
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setSize(800, 450);

        // Create the charts and put them in the content pane
        for (int i = 0; i < demo.getNoOfCharts(); ++i)
        {
            ChartViewer viewer = new ChartViewer();
            demo.createChart(viewer, i);
            frame.getContentPane().add(viewer);
        }

        // Display the window
        frame.setVisible(true);
    }
}

