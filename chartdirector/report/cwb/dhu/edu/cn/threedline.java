package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class threedline implements DemoModule
{
    //Name of demo program
    public String toString() { return "3D Line Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the line chart
        double[] data = {30, 28, 40, 55, 75, 68, 54, 60, 50, 62, 75, 65, 75, 91, 60,
            55, 53, 35, 50, 66, 56, 48, 52, 65, 62};

        // The labels for the line chart
        String[] labels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24"};

        // Create a XYChart object of size 300 x 280 pixels
        XYChart c = new XYChart(300, 280);

        // Set the plotarea at (45, 30) and of size 200 x 200 pixels
        c.setPlotArea(45, 30, 200, 200);

        // Add a title to the chart using 12 pts Arial Bold Italic font
        c.addTitle("Daily Server Utilization", "Arial Bold Italic", 12);

        // Add a title to the y axis
        c.yAxis().setTitle("MBytes");

        // Add a title to the x axis
        c.xAxis().setTitle("June 12, 2001");

        // Add a blue (0x6666ff) 3D line chart layer using the give data
        c.addLineLayer(data, 0x6666ff).set3D();

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        c.xAxis().setLabelStep(3);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Hour {xLabel}: {value} MBytes'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new threedline();

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

