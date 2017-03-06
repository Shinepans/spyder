package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class cone implements DemoModule
{
    //Name of demo program
    public String toString() { return "Cone Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pyramid chart
        double[] data = {156, 123, 211, 179};

        // The labels for the pyramid chart
        String[] labels = {"Funds", "Bonds", "Stocks", "Cash"};

        // The semi-transparent colors for the pyramid layers
        int[] colors = {0x60000088, 0x6066aaee, 0x60ffbb00, 0x60ee6622};

        // Create a PyramidChart object of size 480 x 400 pixels
        PyramidChart c = new PyramidChart(480, 400);

        // Set the cone center at (280, 180), and width x height to 150 x 300 pixels
        c.setConeSize(280, 180, 150, 300);

        // Set the elevation to 15 degrees
        c.setViewAngle(15);

        // Set the pyramid data and labels
        c.setData(data, labels);

        // Set the layer colors to the given colors
        c.setColors2(Chart.DataColor, colors);

        // Leave 1% gaps between layers
        c.setLayerGap(0.01);

        // Add labels at the left side of the pyramid layers using Arial Bold font.
        // The labels will have 3 lines showing the layer name, value and percentage.
        c.setLeftLabel("{label}\nUS ${value}K\n({percent}%)", "Arial Bold");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: US$ {value}K ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new cone();

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

