package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class contourinterpolate implements DemoModule
{
    //Name of demo program
    public String toString() { return "Contour Interpolation"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 4; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The x and y coordinates of the grid
        double[] dataX = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
        double[] dataY = {-4, -3, -2, -1, 0, 1, 2, 3, 4};

        // The values at the grid points. In this example, we will compute the values
        // using the formula z = Sin(x * pi / 3) * Sin(y * pi / 3).
        double[] dataZ = new double[(dataX.length) * (dataY.length)];
        for (int yIndex = 0; yIndex < dataY.length; ++yIndex) {
            double y = dataY[yIndex];
            for (int xIndex = 0; xIndex < dataX.length; ++xIndex) {
                double x = dataX[xIndex];
                dataZ[yIndex * (dataX.length) + xIndex] = Math.sin(x * 3.1416 / 3) *
                    Math.sin(y * 3.1416 / 3);
            }
        }

        // Create a XYChart object of size 360 x 360 pixels
        XYChart c = new XYChart(360, 360);

        // Set the plotarea at (30, 25) and of size 300 x 300 pixels. Use
        // semi-transparent black (c0000000) for both horizontal and vertical grid
        // lines
        c.setPlotArea(30, 25, 300, 300, -1, -1, -1, 0xc0000000, -1);

        // Add a contour layer using the given data
        ContourLayer layer = c.addContourLayer(dataX, dataY, dataZ);

        // Set the x-axis and y-axis scale
        c.xAxis().setLinearScale(-4, 4, 1);
        c.yAxis().setLinearScale(-4, 4, 1);

        if (index == 0) {
            // Discrete coloring, spline surface interpolation
            c.addTitle("Spline Surface - Discrete Coloring", "Arial Bold Italic", 12)
                ;
        } else if (index == 1) {
            // Discrete coloring, linear surface interpolation
            c.addTitle("Linear Surface - Discrete Coloring", "Arial Bold Italic", 12)
                ;
            layer.setSmoothInterpolation(false);
        } else if (index == 2) {
            // Smooth coloring, spline surface interpolation
            c.addTitle("Spline Surface - Continuous Coloring", "Arial Bold Italic",
                12);
            layer.setContourColor(Chart.Transparent);
            layer.colorAxis().setColorGradient(true);
        } else {
            // Discrete coloring, linear surface interpolation
            c.addTitle("Linear Surface - Continuous Coloring", "Arial Bold Italic",
                12);
            layer.setSmoothInterpolation(false);
            layer.setContourColor(Chart.Transparent);
            layer.colorAxis().setColorGradient(true);
        }

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new contourinterpolate();

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

