package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class scattersymbols implements DemoModule
{
    //Name of demo program
    public String toString() { return "Custom Scatter Symbols"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The XY points for the scatter chart
        double[] dataX = {200, 400, 300, 250, 500};
        double[] dataY = {40, 100, 50, 150, 250};

        // The custom symbols for the points
        String[] symbols = {"robot1.png", "robot2.png", "robot3.png", "robot4.png",
            "robot5.png"};

        // Create a XYChart object of size 450 x 400 pixels
        XYChart c = new XYChart(450, 400);

        // Set the plotarea at (55, 40) and of size 350 x 300 pixels, with a light
        // grey border (0xc0c0c0). Turn on both horizontal and vertical grid lines
        // with light grey color (0xc0c0c0)
        c.setPlotArea(55, 40, 350, 300, -1, -1, 0xc0c0c0, 0xc0c0c0, -1);

        // Add a title to the chart using 18 pts Times Bold Itatic font.
        c.addTitle("Battle Robots", "Times New Roman Bold Italic", 18);

        // Add a title to the y axis using 12 pts Arial Bold Italic font
        c.yAxis().setTitle("Speed (km/s)", "Arial Bold Italic", 12);

        // Add a title to the y axis using 12 pts Arial Bold Italic font
        c.xAxis().setTitle("Range (km)", "Arial Bold Italic", 12);

        // Set the axes line width to 3 pixels
        c.xAxis().setWidth(3);
        c.yAxis().setWidth(3);

        // Add each point of the data as a separate scatter layer, so that they can
        // have a different symbol
        for (int i = 0; i < dataX.length; ++i) {
            c.addScatterLayer(new double[]{dataX[i]}, new double[]{dataY[i]}
                ).getDataSet(0).setDataSymbol2(symbols[i]);
        }

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Range = {x} km, Speed = {value} km/s'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new scattersymbols();

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

