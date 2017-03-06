package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class stackradar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Stacked Radar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data0 = {100, 100, 100, 100, 100};
        double[] data1 = {90, 85, 85, 80, 70};
        double[] data2 = {80, 65, 65, 75, 45};

        // The labels for the chart
        String[] labels = {"Population<*br*><*font=Arial*>6 millions",
            "GDP<*br*><*font=Arial*>120 billions",
            "Export<*br*><*font=Arial*>25 billions",
            "Import<*br*><*font=Arial*>24 billions",
            "Investments<*br*><*font=Arial*>20 billions"};

        // Create a PolarChart object of size 480 x 460 pixels. Set background color
        // to silver, with 1 pixel 3D border effect
        PolarChart c = new PolarChart(480, 460, Chart.silverColor(), 0x000000, 1);

        // Add a title to the chart using 15 pts Times Bold Italic font. The title
        // text is white (ffffff) on a deep green (008000) background
        c.addTitle("Economic Growth", "Times New Roman Bold Italic", 15, 0xffffff
            ).setBackground(0x008000);

        // Set plot area center at (240, 270), with 150 pixels radius
        c.setPlotArea(240, 270, 150);

        // Use 1 pixel width semi-transparent black (c0000000) lines as grid lines
        c.setGridColor(0xc0000000, 1, 0xc0000000, 1);

        // Add a legend box at top-center of plot area (240, 35) using horizontal
        // layout. Use 10 pts Arial Bold font, with silver background and 1 pixel 3D
        // border effect.
        LegendBox b = c.addLegend(240, 35, false, "Arial Bold", 10);
        b.setAlignment(Chart.TopCenter);
        b.setBackground(Chart.silverColor(), Chart.Transparent, 1);

        // Add area layers of different colors to represent the data
        c.addAreaLayer(data0, 0xcc8880, "Year 2004");
        c.addAreaLayer(data1, 0xffd080, "Year 1994");
        c.addAreaLayer(data2, 0xa0bce0, "Year 1984");

        // Set the labels to the angular axis as spokes.
        c.angularAxis().setLabels(labels);

        // Set radial axis from 0 - 100 with a tick every 20 units
        c.radialAxis().setLinearScale(0, 100, 20);

        // Just show the radial axis as a grid line. Hide the axis labels by setting
        // the label color to Transparent
        c.radialAxis().setColors(0xc0000000, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Current {label}: {value}% in {dataSetName}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new stackradar();

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

