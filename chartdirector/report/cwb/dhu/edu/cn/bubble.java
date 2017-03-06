package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class bubble implements DemoModule
{
    //Name of demo program
    public String toString() { return "Bubble Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The XYZ points for the bubble chart
        double[] dataX0 = {150, 300, 1000, 1700};
        double[] dataY0 = {12, 60, 25, 65};
        double[] dataZ0 = {20, 50, 50, 85};

        double[] dataX1 = {500, 1000, 1300};
        double[] dataY1 = {35, 50, 75};
        double[] dataZ1 = {30, 55, 95};

        // Create a XYChart object of size 450 x 420 pixels
        XYChart c = new XYChart(450, 420);

        // Set the plotarea at (55, 65) and of size 350 x 300 pixels, with a light
        // grey border (0xc0c0c0). Turn on both horizontal and vertical grid lines
        // with light grey color (0xc0c0c0)
        c.setPlotArea(55, 65, 350, 300, -1, -1, 0xc0c0c0, 0xc0c0c0, -1);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 12 pts Times Bold Italic font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Times New Roman Bold Italic", 12).setBackground(
            Chart.Transparent);

        // Add a title to the chart using 18 pts Times Bold Itatic font.
        c.addTitle("Product Comparison Chart", "Times New Roman Bold Italic", 18);

        // Add a title to the y axis using 12 pts Arial Bold Italic font
        c.yAxis().setTitle("Capacity (tons)", "Arial Bold Italic", 12);

        // Add a title to the x axis using 12 pts Arial Bold Italic font
        c.xAxis().setTitle("Range (miles)", "Arial Bold Italic", 12);

        // Set the axes line width to 3 pixels
        c.xAxis().setWidth(3);
        c.yAxis().setWidth(3);

        // Add (dataX0, dataY0) as a scatter layer with semi-transparent red
        // (0x80ff3333) circle symbols, where the circle size is modulated by dataZ0.
        // This creates a bubble effect.
        c.addScatterLayer(dataX0, dataY0, "Technology AAA", Chart.CircleSymbol, 9,
            0x80ff3333, 0x80ff3333).setSymbolScale(dataZ0);

        // Add (dataX1, dataY1) as a scatter layer with semi-transparent green
        // (0x803333ff) circle symbols, where the circle size is modulated by dataZ1.
        // This creates a bubble effect.
        c.addScatterLayer(dataX1, dataY1, "Technology BBB", Chart.CircleSymbol, 9,
            0x803333ff, 0x803333ff).setSymbolScale(dataZ1);

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
        DemoModule demo = new bubble();

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

