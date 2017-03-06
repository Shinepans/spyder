package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class surfacewireframe implements DemoModule
{
    //Name of demo program
    public String toString() { return "Surface Wireframe"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 6; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The x and y coordinates of the grid
        double[] dataX = {-2, -1, 0, 1, 2};
        double[] dataY = {-2, -1, 0, 1, 2};

        // The values at the grid points. In this example, we will compute the values
        // using the formula z = square_root(15 - x * x - y * y).
        double[] dataZ = new double[(dataX.length) * (dataY.length)];
        for (int yIndex = 0; yIndex < dataY.length; ++yIndex) {
            double y = dataY[yIndex];
            for (int xIndex = 0; xIndex < dataX.length; ++xIndex) {
                double x = dataX[xIndex];
                dataZ[yIndex * (dataX.length) + xIndex] = Math.sqrt(15 - x * x - y *
                    y);
            }
        }

        // Create a SurfaceChart object of size 380 x 340 pixels, with white (ffffff)
        // background and grey (888888) border.
        SurfaceChart c = new SurfaceChart(380, 340, 0xffffff, 0x888888);

        // Demonstrate various wireframes with and without interpolation
        if (index == 0) {
            // Original data without interpolation
            c.addTitle("5 x 5 Data Points\nStandard Shading", "Arial Bold", 12);
            c.setContourColor(0x80ffffff);
        } else if (index == 1) {
            // Original data, spline interpolated to 40 x 40 for smoothness
            c.addTitle("5 x 5 Points - Spline Fitted to 40 x 40\nStandard Shading",
                "Arial Bold", 12);
            c.setContourColor(0x80ffffff);
            c.setInterpolation(40, 40);
        } else if (index == 2) {
            // Rectangular wireframe of original data
            c.addTitle("5 x 5 Data Points\nRectangular Wireframe");
            c.setShadingMode(Chart.RectangularFrame);
        } else if (index == 3) {
            // Rectangular wireframe of original data spline interpolated to 40 x 40
            c.addTitle(
                "5 x 5 Points - Spline Fitted to 40 x 40\nRectangular Wireframe");
            c.setShadingMode(Chart.RectangularFrame);
            c.setInterpolation(40, 40);
        } else if (index == 4) {
            // Triangular wireframe of original data
            c.addTitle("5 x 5 Data Points\nTriangular Wireframe");
            c.setShadingMode(Chart.TriangularFrame);
        } else {
            // Triangular wireframe of original data spline interpolated to 40 x 40
            c.addTitle(
                "5 x 5 Points - Spline Fitted to 40 x 40\nTriangular Wireframe");
            c.setShadingMode(Chart.TriangularFrame);
            c.setInterpolation(40, 40);
        }

        // Set the center of the plot region at (200, 170), and set width x depth x
        // height to 200 x 200 x 150 pixels
        c.setPlotRegion(200, 170, 200, 200, 150);

        // Set the plot region wall thichness to 5 pixels
        c.setWallThickness(5);

        // Set the elevation and rotation angles to 20 and 30 degrees
        c.setViewAngle(20, 30);

        // Set the data to use to plot the chart
        c.setData(dataX, dataY, dataZ);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new surfacewireframe();

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

