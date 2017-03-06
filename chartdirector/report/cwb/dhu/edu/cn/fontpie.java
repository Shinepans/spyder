package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class fontpie implements DemoModule
{
    //Name of demo program
    public String toString() { return "Text Style and Colors"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {25, 18, 15, 12, 8, 30, 35};

        // The labels for the pie chart
        String[] labels = {"Labor", "Licenses", "Taxes", "Legal", "Insurance",
            "Facilities", "Production"};

        // Create a PieChart object of size 480 x 300 pixels
        PieChart c = new PieChart(480, 300);

        // Use a blue marble pattern as the background wallpaper, with a black
        // border, and 1 pixel 3D border effect
        c.setBackground(c.patternColor("marble.png"), 0x000000, 1);

        // Set the center of the pie at (150, 150) and the radius to 100 pixels
        c.setPieSize(150, 150, 100);

        // Add a title to the pie chart using Times Bold Italic/15 points/deep blue
        // (0x000080) as font, with a wood pattern as the title background
        c.addTitle("Project Cost Breakdown", "Times New Roman Bold Italic", 15,
            0x000080).setBackground(c.patternColor("wood.png"));

        // Draw the pie in 3D
        c.set3D();

        // Add a legend box using Arial Bold Italic/11 points font. Use a pink marble
        // pattern as the background wallpaper, with a 1 pixel 3D border. The legend
        // box is top-right aligned relative to the point (465, 70)
        LegendBox b = c.addLegend(465, 70, true, "Arial Bold Italic", 11);
        b.setBackground(c.patternColor("marble2.png"), Chart.Transparent, 1);
        b.setAlignment(Chart.TopRight);

        // Set the default font for all sector labels to Arial Bold/8 pts/dark green
        // (0x008000).
        c.setLabelStyle("Arial Bold", 8, 0x008000);

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Explode the 3rd sector
        c.setExplode(2, 40);

        // Use Arial Bold/12 pts/red as label font for the 3rd sector
        c.sector(2).setLabelStyle("Arial Bold", 12, 0xff0000);

        // Use Arial/8 pts/deep blue as label font for the 5th sector. Add a
        // background box using the sector fill color (SameAsMainColor), with a black
        // (0x000000) edge and 2 pixel 3D border.
        c.sector(4).setLabelStyle("Arial", 8, 0x000080).setBackground(
            Chart.SameAsMainColor, 0x000000, 2);

        // Use Arial Italic/8 pts/light red (0xff9999) as label font for the 6th
        // sector. Add a dark blue (0x000080) background box with a 2 pixel 3D
        // border.
        c.sector(0).setLabelStyle("Arial Italic", 8, 0xff9999).setBackground(
            0x000080, Chart.Transparent, 2);

        // Use Times Bold Italic/8 pts/deep green (0x008000) as label font for 7th
        // sector. Add a yellow (0xFFFF00) background box with a black (0x000000)
        // edge.
        c.sector(6).setLabelStyle("Times New Roman Bold Italic", 8, 0x008000
            ).setBackground(0xffff00, 0x000000);

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
        DemoModule demo = new fontpie();

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

