package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class simpleradar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Simple Radar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data = {90, 60, 65, 75, 40};

        // The labels for the chart
        String[] labels = {"Speed", "Reliability", "Comfort", "Safety", "Efficiency"}
            ;

        // Create a PolarChart object of size 450 x 350 pixels
        PolarChart c = new PolarChart(450, 350);

        // Set center of plot area at (225, 185) with radius 150 pixels
        c.setPlotArea(225, 185, 150);

        // Add an area layer to the polar chart
        c.addAreaLayer(data, 0x9999ff);

        // Set the labels to the angular axis as spokes
        c.angularAxis().setLabels(labels);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: score = {value}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new simpleradar();

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

