package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class ticks implements DemoModule
{
    //Name of demo program
    public String toString() { return "Tick Density"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 2; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data = {100, 125, 265, 147, 67, 105};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun"};

        // Create a XYChart object of size 250 x 250 pixels
        XYChart c = new XYChart(250, 250);

        // Set the plot area at (27, 25) and of size 200 x 200 pixels
        c.setPlotArea(27, 25, 200, 200);

        if (index == 1) {
            // High tick density, uses 10 pixels as tick spacing
            c.addTitle("Tick Density = 10 pixels");
            c.yAxis().setTickDensity(10);
        } else {
            // Normal tick density, just use the default setting
            c.addTitle("Default Tick Density");
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
            "title='Revenue for {xLabel}: US${value}M'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new ticks();

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

