package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class colorbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Multi-Color Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data = {85, 156, 179.5, 211, 123};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // The colors for the bar chart
        int[] colors = {0xb8bc9c, 0xa0bdc4, 0x999966, 0x333366, 0xc3c3e6};

        // Create a XYChart object of size 300 x 220 pixels. Use golden background
        // color. Use a 2 pixel 3D border.
        XYChart c = new XYChart(300, 220, Chart.goldColor(), -1, 2);

        // Add a title box using 10 point Arial Bold font. Set the background color
        // to metallic blue (9999FF) Use a 1 pixel 3D border.
        c.addTitle("Daily Network Load", "Arial Bold", 10).setBackground(
            Chart.metalColor(0x9999ff), -1, 1);

        // Set the plotarea at (40, 40) and of 240 x 150 pixels in size
        c.setPlotArea(40, 40, 240, 150);

        // Add a multi-color bar chart layer using the given data and colors. Use a 1
        // pixel 3D border for the bars.
        c.addBarLayer3(data, colors).setBorderColor(-1, 1);

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel}: {value} GBytes'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new colorbar();

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

