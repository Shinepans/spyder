package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class logaxis implements DemoModule
{
    //Name of demo program
    public String toString() { return "Log Scale Axis"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 2; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data = {100, 125, 260, 147, 67};
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 200 x 180 pixels
        XYChart c = new XYChart(200, 180);

        // Set the plot area at (30, 10) and of size 140 x 130 pixels
        c.setPlotArea(30, 10, 140, 130);

        // Ise log scale axis if required
        if (index == 1) {
            c.yAxis().setLogScale3();
        }

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        // Add a color bar layer using the given data. Use a 1 pixel 3D border for
        // the bars.
        c.addBarLayer3(data).setBorderColor(-1, 1);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Mileage on {xLabel}: {value} miles'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new logaxis();

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

