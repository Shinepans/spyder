package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class rose implements DemoModule
{
    //Name of demo program
    public String toString() { return "Simple Rose Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Data for the chart
        double[] data = {9.4, 1.8, 2.1, 2.3, 3.5, 7.7, 8.8, 6.1, 5.0, 3.1, 6.0, 4.3,
            5.1, 2.6, 1.5, 2.2, 5.1, 4.3, 4.0, 9.0, 1.7, 8.8, 9.9, 9.5};
        double[] angles = {0, 15, 30, 45, 60, 75, 90, 105, 120, 135, 150, 165, 180,
            195, 210, 225, 240, 255, 270, 285, 300, 315, 330, 345};

        // Create a PolarChart object of size 460 x 460 pixels, with a silver
        // background and a 1 pixel 3D border
        PolarChart c = new PolarChart(460, 460, Chart.silverColor(), 0x000000, 1);

        // Add a title to the chart at the top left corner using 15pts Arial Bold
        // Italic font. Use white text on deep blue background.
        c.addTitle("Polar Vector Chart Demonstration", "Arial Bold Italic", 15,
            0xffffff).setBackground(0x000080);

        // Set plot area center at (230, 240) with radius 180 pixels and white
        // background
        c.setPlotArea(230, 240, 180, 0xffffff);

        // Set the grid style to circular grid
        c.setGridStyle(false);

        // Set angular axis as 0 - 360, with a spoke every 30 units
        c.angularAxis().setLinearScale(0, 360, 30);

        // Add sectors to the chart as sector zones
        for (int i = 0; i < data.length; ++i) {
            c.angularAxis().addZone(angles[i], angles[i] + 15, 0, data[i], 0x33ff33,
                0x008000);
        }

        // Add an Transparent invisible layer to ensure the axis is auto-scaled using
        // the data
        c.addLineLayer(data, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new rose();

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

