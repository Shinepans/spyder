package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class pyramidelevation implements DemoModule
{
    //Name of demo program
    public String toString() { return "Pyramid Elevation"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 7; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pyramid chart
        double[] data = {156, 123, 211, 179};

        // The colors for the pyramid layers
        int[] colors = {0x66aaee, 0xeebb22, 0xcccccc, 0xcc88ff};

        // The elevation angle
        int angle = index * 15;

        // Create a PyramidChart object of size 200 x 200 pixels, with white (ffffff)
        // background and grey (888888) border
        PyramidChart c = new PyramidChart(200, 200, 0xffffff, 0x888888);

        // Set the pyramid center at (100, 100), and width x height to 60 x 120
        // pixels
        c.setPyramidSize(100, 100, 60, 120);

        // Set the elevation angle
        c.addTitle("Elevation = " + angle, "Arial Italic", 15);
        c.setViewAngle(angle);

        // Set the pyramid data
        c.setData(data);

        // Set the layer colors to the given colors
        c.setColors2(Chart.DataColor, colors);

        // Leave 1% gaps between layers
        c.setLayerGap(0.01);

        // Output the chart
        viewer.setImage(c.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new pyramidelevation();

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

