package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class surfaceshading implements DemoModule
{
    //Name of demo program
    public String toString() { return "Surface Shading"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 4; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The x and y coordinates of the grid
        double[] dataX = {-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10};
        double[] dataY = {-10, -8, -6, -4, -2, 0, 2, 4, 6, 8, 10};

        // The values at the grid points. In this example, we will compute the values
        // using the formula z = x * sin(y) + y * sin(x).
        double[] dataZ = new double[(dataX.length) * (dataY.length)];
        for (int yIndex = 0; yIndex < dataY.length; ++yIndex) {
            double y = dataY[yIndex];
            for (int xIndex = 0; xIndex < dataX.length; ++xIndex) {
                double x = dataX[xIndex];
                dataZ[yIndex * (dataX.length) + xIndex] = x * Math.sin(y) + y *
                    Math.sin(x);
            }
        }

        // Create a SurfaceChart object of size 380 x 400 pixels, with white (ffffff)
        // background and grey (888888) border.
        SurfaceChart c = new SurfaceChart(380, 400, 0xffffff, 0x888888);

        // Demonstrate various shading methods
        if (index == 0) {
            c.addTitle("11 x 11 Data Points\nSmooth Shading");
        } else if (index == 1) {
            c.addTitle("11 x 11 Points - Spline Fitted to 50 x 50\nSmooth Shading");
            c.setInterpolation(50, 50);
        } else if (index == 2) {
            c.addTitle("11 x 11 Data Points\nRectangular Shading");
            c.setShadingMode(Chart.RectangularShading);
        } else {
            c.addTitle("11 x 11 Data Points\nTriangular Shading");
            c.setShadingMode(Chart.TriangularShading);
        }

        // Set the center of the plot region at (175, 200), and set width x depth x
        // height to 200 x 200 x 160 pixels
        c.setPlotRegion(175, 200, 200, 200, 160);

        // Set the plot region wall thichness to 5 pixels
        c.setWallThickness(5);

        // Set the elevation and rotation angles to 45 and 60 degrees
        c.setViewAngle(45, 60);

        // Set the perspective level to 35
        c.setPerspective(35);

        // Set the data to use to plot the chart
        c.setData(dataX, dataY, dataZ);

        // Set contour lines to semi-transparent black (c0000000)
        c.setContourColor(0xc0000000);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new surfaceshading();

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

