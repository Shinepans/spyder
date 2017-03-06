package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class paramcurve implements DemoModule
{
    //Name of demo program
    public String toString() { return "Parametric Curve Fitting"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The XY data of the first data series
        double[] dataX0 = {10, 35, 17, 4, 22, 29, 45, 52, 63, 39};
        double[] dataY0 = {2.0, 3.2, 2.7, 1.2, 2.8, 2.9, 3.1, 3.0, 2.3, 3.3};

        // The XY data of the second data series
        double[] dataX1 = {30, 35, 17, 4, 22, 59, 43, 52, 63, 39};
        double[] dataY1 = {1.0, 1.3, 0.7, 0.6, 0.8, 3.0, 1.8, 2.3, 3.4, 1.5};

        // The XY data of the third data series
        double[] dataX2 = {28, 35, 15, 10, 22, 60, 46, 64, 39};
        double[] dataY2 = {2.0, 2.2, 1.2, 0.4, 1.8, 2.7, 2.4, 2.8, 2.4};

        // Create a XYChart object of size 540 x 480 pixels
        XYChart c = new XYChart(540, 480);

        // Set the plotarea at (70, 65) and of size 400 x 350 pixels, with white
        // background and a light grey border (0xc0c0c0). Turn on both horizontal and
        // vertical grid lines with light grey color (0xc0c0c0)
        c.setPlotArea(70, 65, 400, 350, 0xffffff, -1, 0xc0c0c0, 0xc0c0c0, -1);

        // Add a legend box with the top center point anchored at (270, 30). Use
        // horizontal layout. Use 10 pts Arial Bold Italic font. Set the background
        // and border color to Transparent.
        LegendBox legendBox = c.addLegend(270, 30, false, "Arial Bold Italic", 10);
        legendBox.setAlignment(Chart.TopCenter);
        legendBox.setBackground(Chart.Transparent, Chart.Transparent);

        // Add a title to the chart using 18 point Times Bold Itatic font.
        c.addTitle("Parametric Curve Fitting", "Times New Roman Bold Italic", 18);

        // Add titles to the axes using 12 pts Arial Bold Italic font
        c.yAxis().setTitle("Axis Title Placeholder", "Arial Bold Italic", 12);
        c.xAxis().setTitle("Axis Title Placeholder", "Arial Bold Italic", 12);

        // Set the axes line width to 3 pixels
        c.yAxis().setWidth(3);
        c.xAxis().setWidth(3);

        // Add a scatter layer using (dataX0, dataY0)
        c.addScatterLayer(dataX0, dataY0, "Polynomial", Chart.GlassSphere2Shape, 11,
            0xff0000);

        // Add a degree 2 polynomial trend line layer for (dataX0, dataY0)
        TrendLayer trend0 = c.addTrendLayer2(dataX0, dataY0, 0xff0000);
        trend0.setLineWidth(3);
        trend0.setRegressionType(Chart.PolynomialRegression(2));
        trend0.setHTMLImageMap("{disable}");

        // Add a scatter layer for (dataX1, dataY1)
        c.addScatterLayer(dataX1, dataY1, "Exponential", Chart.GlassSphere2Shape, 11,
            0x00aa00);

        // Add an exponential trend line layer for (dataX1, dataY1)
        TrendLayer trend1 = c.addTrendLayer2(dataX1, dataY1, 0x00aa00);
        trend1.setLineWidth(3);
        trend1.setRegressionType(Chart.ExponentialRegression);
        trend1.setHTMLImageMap("{disable}");

        // Add a scatter layer using (dataX2, dataY2)
        c.addScatterLayer(dataX2, dataY2, "Logarithmic", Chart.GlassSphere2Shape, 11,
            0x0000ff);

        // Add a logarithmic trend line layer for (dataX2, dataY2)
        TrendLayer trend2 = c.addTrendLayer2(dataX2, dataY2, 0x0000ff);
        trend2.setLineWidth(3);
        trend2.setRegressionType(Chart.LogarithmicRegression);
        trend2.setHTMLImageMap("{disable}");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='[{dataSetName}] ({x}, {value})'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new paramcurve();

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

