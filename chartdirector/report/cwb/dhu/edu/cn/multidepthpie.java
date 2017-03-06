package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class multidepthpie implements DemoModule
{
    //Name of demo program
    public String toString() { return "Multi-Depth Pie Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {72, 18, 15, 12};

        // The labels for the pie chart
        String[] labels = {"Labor", "Machinery", "Facilities", "Computers"};

        // The depths for the sectors
        double[] depths = {30, 20, 10, 10};

        // Create a PieChart object of size 360 x 300 pixels, with a light blue
        // (DDDDFF) background and a 1 pixel 3D border
        PieChart c = new PieChart(360, 300, 0xddddff, -1, 1);

        // Set the center of the pie at (180, 175) and the radius to 100 pixels
        c.setPieSize(180, 175, 100);

        // Add a title box using 15 pts Times Bold Italic font and blue (AAAAFF) as
        // background color
        c.addTitle("Project Cost Breakdown", "Times New Roman Bold Italic", 15
            ).setBackground(0xaaaaff);

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Draw the pie in 3D with variable 3D depths
        c.set3D2(depths);

        // Set the start angle to 225 degrees may improve layout when the depths of
        // the sector are sorted in descending order, because it ensures the tallest
        // sector is at the back.
        c.setStartAngle(225);

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
        DemoModule demo = new multidepthpie();

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

