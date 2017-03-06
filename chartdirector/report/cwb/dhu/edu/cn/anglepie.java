package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class anglepie implements DemoModule
{
    //Name of demo program
    public String toString() { return "Start Angle and Direction"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 2; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Determine the starting angle and direction based on input parameter
        int angle = 0;
        boolean clockwise = true;
        if (index != 0) {
            angle = 90;
            clockwise = false;
        }

        // The data for the pie chart
        double[] data = {25, 18, 15, 12, 8, 30, 35};

        // The labels for the pie chart
        String[] labels = {"Labor", "Licenses", "Taxes", "Legal", "Insurance",
            "Facilities", "Production"};

        // Create a PieChart object of size 280 x 240 pixels
        PieChart c = new PieChart(280, 240);

        // Set the center of the pie at (140, 130) and the radius to 80 pixels
        c.setPieSize(140, 130, 80);

        // Add a title to the pie to show the start angle and direction
        if (clockwise) {
            c.addTitle("Start Angle = " + angle + " degrees\nDirection = Clockwise");
        } else {
            c.addTitle("Start Angle = " + angle +
                " degrees\nDirection = AntiClockwise");
        }

        // Set the pie start angle and direction
        c.setStartAngle(angle, clockwise);

        // Draw the pie in 3D
        c.set3D();

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Explode the 1st sector (index = 0)
        c.setExplode(0);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: US${value}K ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
    	Chart.setLicenseCode("SXZVFNRN9MZ9L8LGA0E2B1BB");
        //Instantiate an instance of this demo module
        DemoModule demo = new anglepie();

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

