package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class overlapbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Overlapping Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data0 = {100, 125, 156, 147, 87, 124, 178, 109, 140, 106, 192, 122};
        double[] data1 = {122, 156, 179, 211, 198, 177, 160, 220, 190, 188, 220, 270}
            ;
        double[] data2 = {167, 190, 213, 267, 250, 320, 212, 199, 245, 267, 240, 310}
            ;
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sept", "Oct", "Nov", "Dec"};

        // Create a XYChart object of size 580 x 280 pixels
        XYChart c = new XYChart(580, 280);

        // Add a title to the chart using 14 pts Arial Bold Italic font
        c.addTitle("Product Revenue For Last 3 Years", "Arial Bold Italic", 14);

        // Set the plot area at (50, 50) and of size 500 x 200. Use two alternative
        // background colors (f8f8f8 and ffffff)
        c.setPlotArea(50, 50, 500, 200, 0xf8f8f8, 0xffffff);

        // Add a legend box at (50, 25) using horizontal layout. Use 8pts Arial as
        // font, with transparent background.
        c.addLegend(50, 25, false, "Arial", 8).setBackground(Chart.Transparent);

        // Set the x axis labels
        c.xAxis().setLabels(labels);

        // Draw the ticks between label positions (instead of at label positions)
        c.xAxis().setTickOffset(0.5);

        // Add a multi-bar layer with 3 data sets
        BarLayer layer = c.addBarLayer2(Chart.Side);
        layer.addDataSet(data0, 0xff8080, "Year 2003");
        layer.addDataSet(data1, 0x80ff80, "Year 2004");
        layer.addDataSet(data2, 0x8080ff, "Year 2005");

        // Set 50% overlap between bars
        layer.setOverlapRatio(0.5);

        // Add a title to the y-axis
        c.yAxis().setTitle("Revenue (USD in millions)");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel} Revenue on {dataSetName}: {value} millions'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new overlapbar();

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

