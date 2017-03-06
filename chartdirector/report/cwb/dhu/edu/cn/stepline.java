package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import ChartDirector.*;

public class stepline implements DemoModule
{
    //Name of demo program
    public String toString() { return "Step Line Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] dataY0 = {4, 4.5, 5, 5.25, 5.75, 5.25, 5, 4.5, 4, 3, 2.5, 2.5};
        Date[] dataX0 = {new GregorianCalendar(1997, 0, 1).getTime(),
            new GregorianCalendar(1998, 5, 25).getTime(), new GregorianCalendar(1999,
            8, 6).getTime(), new GregorianCalendar(2000, 1, 6).getTime(),
            new GregorianCalendar(2000, 8, 21).getTime(), new GregorianCalendar(2001,
            2, 4).getTime(), new GregorianCalendar(2001, 5, 8).getTime(),
            new GregorianCalendar(2002, 1, 4).getTime(), new GregorianCalendar(2002,
            4, 19).getTime(), new GregorianCalendar(2002, 7, 16).getTime(),
            new GregorianCalendar(2002, 11, 1).getTime(), new GregorianCalendar(2003,
            0, 1).getTime()};

        double[] dataY1 = {7, 6.5, 6, 5, 6.5, 7, 6, 5.5, 5, 4, 3.5, 3.5};
        Date[] dataX1 = {new GregorianCalendar(1997, 0, 1).getTime(),
            new GregorianCalendar(1997, 6, 1).getTime(), new GregorianCalendar(1997,
            11, 1).getTime(), new GregorianCalendar(1999, 0, 15).getTime(),
            new GregorianCalendar(1999, 5, 9).getTime(), new GregorianCalendar(2000,
            2, 3).getTime(), new GregorianCalendar(2000, 7, 13).getTime(),
            new GregorianCalendar(2001, 4, 5).getTime(), new GregorianCalendar(2001,
            8, 16).getTime(), new GregorianCalendar(2002, 2, 16).getTime(),
            new GregorianCalendar(2002, 5, 1).getTime(), new GregorianCalendar(2003,
            0, 1).getTime()};

        // Create a XYChart object of size 500 x 270 pixels, with a pale blue
        // (e0e0ff) background, black border, 1 pixel 3D border effect and rounded
        // corners
        XYChart c = new XYChart(600, 300, 0xe0e0ff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 60) and of size 520 x 200 pixels, with white
        // (ffffff) background. Set horizontal and vertical grid lines to grey
        // (cccccc).
        c.setPlotArea(50, 60, 525, 200, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (55, 32) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(55, 32, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font. The text
        // is white (ffffff) on a deep blue (000088) background, with soft lighting
        // effect from the right side.
        c.addTitle("Long Term Interest Rates", "Times New Roman Bold Italic", 15,
            0xffffff).setBackground(0x000088, -1, Chart.softLighting(Chart.Right));

        // Set the y axis label format to display a percentage sign
        c.yAxis().setLabelFormat("{value}%");

        // Add a red (ff0000) step line layer to the chart and set the line width to
        // 2 pixels
        StepLineLayer layer0 = c.addStepLineLayer(dataY0, 0xff0000, "Country AAA");
        layer0.setXData(dataX0);
        layer0.setLineWidth(2);

        // Add a blue (0000ff) step line layer to the chart and set the line width to
        // 2 pixels
        StepLineLayer layer1 = c.addStepLineLayer(dataY1, 0x0000ff, "Country BBB");
        layer1.setXData(dataX1);
        layer1.setLineWidth(2);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{dataSetName} change to {value}% on {x|mmm dd, yyyy}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new stepline();

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

