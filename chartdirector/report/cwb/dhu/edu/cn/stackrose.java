package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class stackrose implements DemoModule
{
    //Name of demo program
    public String toString() { return "Stacked Rose Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Data for the chart
        double[] data0 = {5, 3, 10, 4, 3, 5, 2, 5};
        double[] data1 = {12, 6, 17, 6, 7, 9, 4, 7};
        double[] data2 = {17, 7, 22, 7, 18, 13, 5, 11};

        double[] angles = {0, 45, 90, 135, 180, 225, 270, 315};
        String[] labels = {"North", "North\nEast", "East", "South\nEast", "South",
            "South\nWest", "West", "North\nWest"};

        // Create a PolarChart object of size 460 x 500 pixels, with a grey (e0e0e0)
        // background and a 1 pixel 3D border
        PolarChart c = new PolarChart(460, 500, 0xe0e0e0, 0x000000, 1);

        // Add a title to the chart at the top left corner using 15pts Arial Bold
        // Italic font. Use white text on deep blue background.
        c.addTitle("Wind Direction", "Arial Bold Italic", 15, 0xffffff
            ).setBackground(0x000080);

        LegendBox legendBox = c.addLegend(230, 35, false, "Arial Bold", 9);
        legendBox.setAlignment(Chart.TopCenter);
        legendBox.setBackground(Chart.Transparent, Chart.Transparent, 1);

        legendBox.addKey("5 m/s or above", 0xff3333);
        legendBox.addKey("1 - 5 m/s", 0x33ff33);
        legendBox.addKey("less than 1 m/s", 0x3333ff);

        // Set plot area center at (230, 280) with radius 180 pixels and white
        // background
        c.setPlotArea(230, 280, 180, 0xffffff);

        // Set the grid style to circular grid
        c.setGridStyle(false);

        // Set angular axis as 0 - 360, with a spoke every 30 units
        c.angularAxis().setLinearScale2(0, 360, labels);

        for (int i = 0; i < angles.length; ++i) {
            c.angularAxis().addZone(angles[i] - 10, angles[i] + 10, 0, data0[i],
                0x3333ff, 0);
            c.angularAxis().addZone(angles[i] - 10, angles[i] + 10, data0[i], data1[i
                ], 0x33ff33, 0);
            c.angularAxis().addZone(angles[i] - 10, angles[i] + 10, data1[i], data2[i
                ], 0xff3333, 0);
        }

        // Add an Transparent invisible layer to ensure the axis is auto-scaled using
        // the data
        c.addLineLayer(data2, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new stackrose();

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

