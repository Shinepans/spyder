package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class polararea implements DemoModule
{
    //Name of demo program
    public String toString() { return "Polar Area Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Data for the chart
        double[] data0 = {5, 3, 10, 4, 3, 5, 2, 5};
        double[] data1 = {12, 6, 17, 6, 7, 9, 4, 7};
        double[] data2 = {17, 7, 22, 7, 18, 13, 5, 11};

        String[] labels = {"North", "North<*br*>East", "East", "South<*br*>East",
            "South", "South<*br*>West", "West", "North<*br*>West"};

        // Create a PolarChart object of size 460 x 500 pixels, with a grey (e0e0e0)
        // background and 1 pixel 3D border
        PolarChart c = new PolarChart(460, 500, 0xe0e0e0, 0x000000, 1);

        // Add a title to the chart at the top left corner using 15pts Arial Bold
        // Italic font. Use a wood pattern as the title background.
        c.addTitle("Polar Area Chart Demo", "Arial Bold Italic", 15).setBackground(
            c.patternColor("wood.png"));

        // Set center of plot area at (230, 280) with radius 180 pixels, and white
        // (ffffff) background.
        c.setPlotArea(230, 280, 180, 0xffffff);

        // Set the grid style to circular grid
        c.setGridStyle(false);

        // Add a legend box at top-center of plot area (230, 35) using horizontal
        // layout. Use 10 pts Arial Bold font, with 1 pixel 3D border effect.
        LegendBox b = c.addLegend(230, 35, false, "Arial Bold", 9);
        b.setAlignment(Chart.TopCenter);
        b.setBackground(Chart.Transparent, Chart.Transparent, 1);

        // Set angular axis using the given labels
        c.angularAxis().setLabels(labels);

        // Specify the label format for the radial axis
        c.radialAxis().setLabelFormat("{value}%");

        // Set radial axis label background to semi-transparent grey (40cccccc)
        c.radialAxis().setLabelStyle().setBackground(0x40cccccc, 0);

        // Add the data as area layers
        c.addAreaLayer(data2, -1, "5 m/s or above");
        c.addAreaLayer(data1, -1, "1 - 5 m/s");
        c.addAreaLayer(data0, -1, "less than 1 m/s");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='[{label}] {dataSetName}: {value}%'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new polararea();

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

