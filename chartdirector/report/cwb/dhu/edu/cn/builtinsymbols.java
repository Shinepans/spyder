package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class builtinsymbols implements DemoModule
{
    //Name of demo program
    public String toString() { return "Built-in Symbols"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Some ChartDirector built-in symbols
        int[] symbols = {Chart.CircleShape, Chart.GlassSphereShape,
            Chart.GlassSphere2Shape, Chart.SolidSphereShape, Chart.SquareShape,
            Chart.DiamondShape, Chart.TriangleShape, Chart.RightTriangleShape,
            Chart.LeftTriangleShape, Chart.InvertedTriangleShape, Chart.StarShape(3),
            Chart.StarShape(4), Chart.StarShape(5), Chart.StarShape(6),
            Chart.StarShape(7), Chart.StarShape(8), Chart.StarShape(9),
            Chart.StarShape(10), Chart.PolygonShape(5), Chart.Polygon2Shape(5),
            Chart.PolygonShape(6), Chart.Polygon2Shape(6), Chart.CrossShape(0.1),
            Chart.CrossShape(0.2), Chart.CrossShape(0.3), Chart.CrossShape(0.4),
            Chart.CrossShape(0.5), Chart.CrossShape(0.6), Chart.CrossShape(0.7),
            Chart.Cross2Shape(0.1), Chart.Cross2Shape(0.2), Chart.Cross2Shape(0.3),
            Chart.Cross2Shape(0.4), Chart.Cross2Shape(0.5), Chart.Cross2Shape(0.6),
            Chart.Cross2Shape(0.7)};

        // Create a XYChart object of size 450 x 400 pixels
        XYChart c = new XYChart(450, 400);

        // Set the plotarea at (55, 40) and of size 350 x 300 pixels, with a light
        // grey border (0xc0c0c0). Turn on both horizontal and vertical grid lines
        // with light grey color (0xc0c0c0)
        c.setPlotArea(55, 40, 350, 300, -1, -1, 0xc0c0c0, 0xc0c0c0, -1);

        // Add a title to the chart using 18 pts Times Bold Itatic font.
        c.addTitle("Built-in Symbols", "Times New Roman Bold Italic", 18);

        // Set the axes line width to 3 pixels
        c.xAxis().setWidth(3);
        c.yAxis().setWidth(3);

        // Ensure the ticks are at least 1 unit part (integer ticks)
        c.xAxis().setMinTickInc(1);
        c.yAxis().setMinTickInc(1);

        // Add each symbol as a separate scatter layer.
        for (int i = 0; i < symbols.length; ++i) {
            c.addScatterLayer(new double[]{i % 6 + 1}, new double[]{(int)(i / 6 + 1)
                }, "", symbols[i], 15);
        }

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='(x, y) = ({x}, {value})'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new builtinsymbols();

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

