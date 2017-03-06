package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class threedbubble2 implements DemoModule
{
    //Name of demo program
    public String toString() { return "3D Bubble Chart (2)"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The XYZ points for the bubble chart
        double[] dataX0 = {170, 300, 1000, 1700};
        double[] dataY0 = {16, 69, 16, 75};
        double[] dataZ0 = {52, 105, 88, 140};

        double[] dataX1 = {500, 1000, 1300};
        double[] dataY1 = {40, 58, 85};
        double[] dataZ1 = {140, 202, 84};

        // Create a XYChart object of size 540 x 480 pixels
        XYChart c = new XYChart(540, 480);

        // Set the plotarea at (70, 65) and of size 400 x 350 pixels. Turn on both
        // horizontal and vertical grid lines with light grey color (0xc0c0c0)
        c.setPlotArea(70, 65, 400, 350, -1, -1, Chart.Transparent, 0xc0c0c0, -1);

        // Add a legend box at (70, 30) (top of the chart) with horizontal layout.
        // Use 12 pts Times Bold Italic font. Set the background and border color to
        // Transparent.
        c.addLegend(70, 30, false, "Times New Roman Bold Italic", 12).setBackground(
            Chart.Transparent);

        // Add a title to the chart using 18 pts Times Bold Itatic font.
        c.addTitle("Product Comparison Chart", "Times New Roman Bold Italic", 18);

        // Add titles to the axes using 12 pts Arial Bold Italic font
        c.yAxis().setTitle("Capacity (tons)", "Arial Bold Italic", 12);
        c.xAxis().setTitle("Range (miles)", "Arial Bold Italic", 12);

        // Set the axes line width to 3 pixels
        c.xAxis().setWidth(3);
        c.yAxis().setWidth(3);

        // Add (dataX0, dataY0) as a scatter layer with red (ff3333) glass spheres,
        // where the sphere size is modulated by dataZ0. This creates a bubble
        // effect.
        c.addScatterLayer(dataX0, dataY0, "Technology AAA", Chart.GlassSphere2Shape,
            15, 0xff3333).setSymbolScale(dataZ0);

        // Add (dataX1, dataY1) as a scatter layer with blue (0000ff) glass spheres,
        // where the sphere size is modulated by dataZ1. This creates a bubble
        // effect.
        c.addScatterLayer(dataX1, dataY1, "Technology BBB", Chart.GlassSphere2Shape,
            15, 0x0000ff).setSymbolScale(dataZ1);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='[{dataSetName}] Range = {x} miles, Capacity = {value} tons, " +
            "Length = {z} meters'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new threedbubble2();

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

