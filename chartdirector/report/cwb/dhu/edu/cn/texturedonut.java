package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class texturedonut implements DemoModule
{
    //Name of demo program
    public String toString() { return "Texture Donut Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {18, 45, 28};

        // The labels for the pie chart
        String[] labels = {"Marble", "Wood", "Granite"};

        // The icons for the sectors
        String[] texture = {"marble3.png", "wood.png", "rock.png"};

        // Create a PieChart object of size 400 x 330 pixels, with a metallic green
        // (88EE88) background, black border and 1 pixel 3D border effect
        PieChart c = new PieChart(400, 330, Chart.metalColor(0x88ee88), 0x000000, 1);

        // Set donut center at (200, 160), and outer/inner radii as 120/60 pixels
        c.setDonutSize(200, 160, 120, 60);

        // Add a title box using 15 pts Times Bold Italic font and metallic deep
        // green (008000) background color
        c.addTitle("Material Composition", "Times New Roman Bold Italic", 15
            ).setBackground(Chart.metalColor(0x008000));

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Set the colors of the sectors to the 3 texture patterns
        c.setColor(Chart.DataColor + 0, c.patternColor2(texture[0]));
        c.setColor(Chart.DataColor + 1, c.patternColor2(texture[1]));
        c.setColor(Chart.DataColor + 2, c.patternColor2(texture[2]));

        // Draw the pie in 3D with a 3D depth of 30 pixels
        c.set3D(30);

        // Use 12 pts Arial Bold Italic as the sector label font
        c.setLabelStyle("Arial Bold Italic", 12);

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
        DemoModule demo = new texturedonut();

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

