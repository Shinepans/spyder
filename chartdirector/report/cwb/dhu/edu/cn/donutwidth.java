package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class donutwidth implements DemoModule
{
    //Name of demo program
    public String toString() { return "Donut Width"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 5; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Determine the donut inner radius (as percentage of outer radius) based on
        // input parameter
        int donutRadius = index * 25;

        // The data for the pie chart
        double[] data = {10, 10, 10, 10, 10};

        // The labels for the pie chart
        String[] labels = {"Marble", "Wood", "Granite", "Plastic", "Metal"};

        // Create a PieChart object of size 150 x 120 pixels, with a grey (EEEEEE)
        // background, black border and 1 pixel 3D border effect
        PieChart c = new PieChart(150, 120, 0xeeeeee, 0x000000, 1);

        // Set donut center at (75, 65) and the outer radius to 50 pixels. Inner
        // radius is computed according donutWidth
        c.setDonutSize(75, 60, 50, (int)(50 * donutRadius / 100));

        // Add a title to show the donut width
        c.addTitle("Inner Radius = " + donutRadius + " %", "Arial", 10
            ).setBackground(0xcccccc, 0);

        // Draw the pie in 3D
        c.set3D(12);

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Disable the sector labels by setting the color to Transparent
        c.setLabelStyle("", 8, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: {value}kg ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new donutwidth();

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

