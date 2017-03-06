package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class polygonbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Polygon Bar Shapes"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data = {85, 156, 179.5, 211, 123, 176, 195};

        // The labels for the bar chart
        String[] labels = {"Square", "Star(8)", "Polygon(6)", "Cross", "Cross2",
            "Diamond", "Custom"};

        // Create a XYChart object of size 500 x 280 pixels.
        XYChart c = new XYChart(500, 280);

        // Set the plotarea at (50, 40) with alternating light grey (f8f8f8) / white
        // (ffffff) background
        c.setPlotArea(50, 40, 400, 200, 0xf8f8f8, 0xffffff);

        // Add a title to the chart using 14 pts Arial Bold Italic font
        c.addTitle("    Bar Shape Demonstration", "Arial Bold Italic", 14);

        // Add a multi-color bar chart layer
        BarLayer layer = c.addBarLayer3(data);

        // Set layer to 3D with 10 pixels 3D depth
        layer.set3D(10);

        // Set bar shape to circular (cylinder)
        layer.setBarShape(Chart.CircleShape);

        // Set the first bar (index = 0) to square shape
        layer.setBarShape(Chart.SquareShape, 0, 0);

        // Set the second bar to 8-pointed star
        layer.setBarShape(Chart.StarShape(8), 0, 1);

        // Set the third bar to 6-sided polygon
        layer.setBarShape(Chart.PolygonShape(6), 0, 2);

        // Set the next 3 bars to cross shape, X shape and diamond shape
        layer.setBarShape(Chart.CrossShape(), 0, 3);
        layer.setBarShape(Chart.Cross2Shape(), 0, 4);
        layer.setBarShape(Chart.DiamondShape, 0, 5);

        // Set the last bar to a custom shape, specified as an array of (x, y) points
        // in normalized coordinates
        layer.setBarShape2(new int[]{-500, 0, 0, 500, 500, 0, 500, 1000, 0, 500,
            -500, 1000}, 0, 6);

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Add a title to the y axis
        c.yAxis().setTitle("Frequency");

        // Add a title to the x axis
        c.xAxis().setTitle("Shapes");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel}: {value}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new polygonbar();

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

