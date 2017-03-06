package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class polarvector implements DemoModule
{
    //Name of demo program
    public String toString() { return "Polar Vector Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Coordinates of the starting points of the vectors
        double[] radius = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10,
            10, 10, 10, 10, 10, 10, 10, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
            15, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 25, 25, 25, 25, 25,
            25, 25, 25, 25, 25, 25, 25};
        double[] angle = {0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 0,
            30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 0, 30, 60, 90, 120,
            150, 180, 210, 240, 270, 300, 330, 0, 30, 60, 90, 120, 150, 180, 210,
            240, 270, 300, 330, 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330
            };

        // Magnitude and direction of the vectors
        double[] magnitude = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 4, 4, 4, 4, 4,
            4, 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
            2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        double[] direction = {60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 0, 30,
            60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 0, 30, 60, 90, 120, 150,
            180, 210, 240, 270, 300, 330, 0, 30, 60, 90, 120, 150, 180, 210, 240,
            270, 300, 330, 0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 0,
            30};

        // Create a PolarChart object of size 460 x 460 pixels
        PolarChart c = new PolarChart(460, 460);

        // Add a title to the chart at the top left corner using 15pts Arial Bold
        // Italic font
        c.addTitle("Polar Vector Chart Demonstration", "Arial Bold Italic", 15);

        // Set center of plot area at (230, 240) with radius 180 pixels
        c.setPlotArea(230, 240, 180);

        // Set the grid style to circular grid
        c.setGridStyle(false);

        // Set angular axis as 0 - 360, with a spoke every 30 units
        c.angularAxis().setLinearScale(0, 360, 30);

        // Add a polar vector layer to the chart with blue (0000ff) vectors
        c.addVectorLayer(radius, angle, magnitude, direction, Chart.RadialAxisScale,
            0x0000ff);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Vector at ({value}, {angle} deg): Length = {len}, Angle = " +
            "{dir} deg'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new polarvector();

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

