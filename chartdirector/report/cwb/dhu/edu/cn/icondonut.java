package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class icondonut implements DemoModule
{
    //Name of demo program
    public String toString() { return "Icon Donut Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {72, 18, 15, 12};

        // The depths for the sectors
        double[] depths = {30, 20, 10, 10};

        // The labels for the pie chart
        String[] labels = {"Sunny", "Cloudy", "Rainy", "Snowy"};

        // The icons for the sectors
        String[] icons = {"sun.png", "cloud.png", "rain.png", "snowy.png"};

        // Create a PieChart object of size 400 x 300 pixels
        PieChart c = new PieChart(400, 300);

        // Use the semi-transparent palette for this chart
        c.setColors(Chart.transparentPalette);

        // Set the background to metallic light blue (CCFFFF), with a black border
        // and 1 pixel 3D border effect,
        c.setBackground(Chart.metalColor(0xccccff), 0x000000, 1);

        // Set donut center at (200, 175), and outer/inner radii as 100/50 pixels
        c.setDonutSize(200, 175, 100, 50);

        // Add a title box using 15 pts Times Bold Italic font and metallic blue
        // (8888FF) background color
        c.addTitle("Weather Profile in Wonderland", "Times New Roman Bold Italic", 15
            ).setBackground(Chart.metalColor(0x8888ff));

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Add icons to the chart as a custom field
        c.addExtraField(icons);

        // Configure the sector labels using CDML to include the icon images
        c.setLabelFormat(
            "<*block,valign=absmiddle*><*img={field0}*> <*block*>{label}\n" +
            "{percent}%<*/*><*/*>");

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
            "title='{label}: {value} days ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new icondonut();

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

