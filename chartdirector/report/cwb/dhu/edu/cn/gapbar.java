package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class gapbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Bar Gap"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 6; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        double bargap = index * 0.25 - 0.25;

        // The data for the bar chart
        double[] data = {100, 125, 245, 147, 67};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 150 x 150 pixels
        XYChart c = new XYChart(150, 150);

        // Set the plotarea at (27, 20) and of size 120 x 100 pixels
        c.setPlotArea(27, 20, 120, 100);

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        if (bargap >= 0) {
            // Add a title to display to bar gap using 8 pts Arial font
            c.addTitle("      Bar Gap = " + bargap, "Arial", 8);
        } else {
            // Use negative value to mean TouchBar
            c.addTitle("      Bar Gap = TouchBar", "Arial", 8);
            bargap = Chart.TouchBar;
        }

        // Add a bar chart layer using the given data and set the bar gap
        c.addBarLayer(data).setBarGap(bargap);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Production on {xLabel}: {value} kg'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new gapbar();

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

