package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class threeddepthpie implements DemoModule
{
    //Name of demo program
    public String toString() { return "3D Depth"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 5; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // the tilt angle of the pie
        int depth = index * 5 + 5;

        // The data for the pie chart
        double[] data = {25, 18, 15, 12, 8, 30, 35};

        // The labels for the pie chart
        String[] labels = {"Labor", "Licenses", "Taxes", "Legal", "Insurance",
            "Facilities", "Production"};

        // Create a PieChart object of size 100 x 110 pixels
        PieChart c = new PieChart(100, 110);

        // Set the center of the pie at (50, 55) and the radius to 38 pixels
        c.setPieSize(50, 55, 38);

        // Set the depth of the 3D pie
        c.set3D(depth);

        // Add a title showing the depth
        c.addTitle("Depth = " + depth + " pixels", "Arial", 8);

        // Set the pie data
        c.setData(data, labels);

        // Disable the sector labels by setting the color to Transparent
        c.setLabelStyle("", 8, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: US${value}K ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new threeddepthpie();

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

