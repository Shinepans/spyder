package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class background implements DemoModule
{
    //Name of demo program
    public String toString() { return "Background and Wallpaper"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 4; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data = {85, 156, 179.5, 211, 123};
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 270 x 270 pixels
        XYChart c = new XYChart(270, 270);

        // Set the plot area at (40, 32) and of size 200 x 200 pixels
        PlotArea plotarea = c.setPlotArea(40, 32, 200, 200);

        // Set the background style based on the input parameter
        if (index == 0) {
            // Has wallpaper image
            c.setWallpaper("tile.gif");
        } else if (index == 1) {
            // Use a background image as the plot area background
            plotarea.setBackground2("bg.png");
        } else if (index == 2) {
            // Use white (0xffffff) and grey (0xe0e0e0) as two alternate plotarea
            // background colors
            plotarea.setBackground(0xffffff, 0xe0e0e0);
        } else {
            // Use a dark background palette
            c.setColors(Chart.whiteOnBlackPalette);
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
            "title='Revenue for {xLabel}: US${value}K'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new background();

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

