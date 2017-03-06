package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class surfacelighting implements DemoModule
{
    //Name of demo program
    public String toString() { return "Surface Lighting"; }

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

        // Demonstrate various lighting parameters
        if (index == 0) {
            c.addTitle(
                "Default Lighting<*br*><*size=10*>Ambient = 0.5, Diffuse = 0.5, " +
                "Specular = 1, Shininess = 8");
        } else if (index == 1) {
            c.addTitle(
                "Matte (Non-Glossy) Lighting<*br*><*size=10*>Ambient = 0.5, " +
                "Diffuse = 0.5, Specular = 0, Shininess = 0");
            c.setLighting(0.5, 0.5, 0, 0);
        } else if (index == 2) {
            c.addTitle(
                "Flat Lighting<*br*><*size=10*>Ambient = 1, Diffuse = 0, Specular " +
                "= 0, Shininess = 0");
            c.setLighting(1, 0, 0, 0);
        } else {
            c.addTitle(
                "Strong Glossy Lighting<*br*><*size=10*>Ambient = 0.5, Diffuse = " +
                "0.5, Specular = 4, Shininess = 32");
            c.setLighting(0.5, 0.5, 4, 32);
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

        // Spline interpolate data to a 50 x 50 grid for a smooth surface
        c.setInterpolation(50, 50);

        // Set contour lines to semi-transparent black (c0000000)
        c.setContourColor(0xc0000000);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new surfacelighting();

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

