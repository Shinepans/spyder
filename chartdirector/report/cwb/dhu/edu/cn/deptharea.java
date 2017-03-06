package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class deptharea implements DemoModule
{
    //Name of demo program
    public String toString() { return "Depth Area Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the area chart
        double[] data0 = {42, 49, 33, 38, 51, 46, 29, 41, 44, 57, 59, 52, 37, 34, 51,
            56, 56, 60, 70, 76, 63, 67, 75, 64, 51};
        double[] data1 = {50, 55, 47, 34, 42, 49, 63, 62, 73, 59, 56, 50, 64, 60, 67,
            67, 58, 59, 73, 77, 84, 82, 80, 84, 89};
        double[] data2 = {87, 89, 85, 66, 53, 39, 24, 21, 37, 56, 37, 22, 21, 33, 13,
            17, 4, 23, 16, 25, 9, 10, 5, 7, 6};
        String[] labels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24"};

        // Create a XYChart object of size 350 x 230 pixels
        XYChart c = new XYChart(350, 230);

        // Set the plotarea at (50, 30) and of size 250 x 150 pixels.
        c.setPlotArea(50, 30, 250, 150);

        // Add a legend box at (55, 0) (top of the chart) using 8 pts Arial Font. Set
        // background and border to Transparent.
        c.addLegend(55, 0, false, "", 8).setBackground(Chart.Transparent);

        // Add a title to the x axis
        c.xAxis().setTitle("Network Load for Jun 12");

        // Add a title to the y axis
        c.yAxis().setTitle("MBytes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 2 labels on the x-axis. Show minor ticks for remaining
        // labels.
        c.xAxis().setLabelStep(2, 1);

        // Add three area layers, each representing one data set. The areas are drawn
        // in semi-transparent colors.
        c.addAreaLayer(data2, 0x808080ff, "Server #1", 3);
        c.addAreaLayer(data0, 0x80ff0000, "Server #2", 3);
        c.addAreaLayer(data1, 0x8000ff00, "Server #3", 3);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{dataSetName} load at hour {xLabel}: {value} MBytes'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new deptharea();

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

