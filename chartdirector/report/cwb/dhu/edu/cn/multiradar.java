package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class multiradar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Multi Radar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data0 = {90, 60, 85, 75, 55};
        double[] data1 = {60, 80, 70, 80, 85};

        // The labels for the chart
        String[] labels = {"Speed", "Reliability", "Comfort", "Safety", "Efficiency"}
            ;

        // Create a PolarChart object of size 480 x 380 pixels. Set background color
        // to gold, with 1 pixel 3D border effect
        PolarChart c = new PolarChart(480, 380, Chart.goldColor(), 0x000000, 1);

        // Add a title to the chart using 15 pts Times Bold Italic font. The title
        // text is white (ffffff) on a deep blue (000080) background
        c.addTitle("Space Travel Vehicles Compared", "Times New Roman Bold Italic",
            15, 0xffffff).setBackground(0x000080);

        // Set plot area center at (240, 210), with 150 pixels radius, and a white
        // (ffffff) background.
        c.setPlotArea(240, 210, 150, 0xffffff);

        // Add a legend box at top right corner (470, 35) using 10 pts Arial Bold
        // font. Set the background to silver, with 1 pixel 3D border effect.
        LegendBox b = c.addLegend(470, 35, true, "Arial Bold", 10);
        b.setAlignment(Chart.TopRight);
        b.setBackground(Chart.silverColor(), Chart.Transparent, 1);

        // Add an area layer to the chart using semi-transparent blue (0x806666cc).
        // Add a blue (0x6666cc) line layer using the same data with 3 pixel line
        // width to highlight the border of the area.
        c.addAreaLayer(data0, 0x806666cc, "Model Saturn");
        c.addLineLayer(data0, 0x6666cc).setLineWidth(3);

        // Add an area layer to the chart using semi-transparent red (0x80cc6666).
        // Add a red (0xcc6666) line layer using the same data with 3 pixel line
        // width to highlight the border of the area.
        c.addAreaLayer(data1, 0x80cc6666, "Model Jupiter");
        c.addLineLayer(data1, 0xcc6666).setLineWidth(3);

        // Set the labels to the angular axis as spokes.
        c.angularAxis().setLabels(labels);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='[{dataSetName}] {label}: score = {value}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new multiradar();

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

