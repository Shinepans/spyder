package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class linearzonemeter implements DemoModule
{
    //Name of demo program
    public String toString() { return "Linear Zone Meter"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The value to display on the meter
        double value = 85;

        // Create an LinearMeter object of size 210 x 45 pixels, using silver
        // background with a 2 pixel black 3D depressed border.
        LinearMeter m = new LinearMeter(210, 45, Chart.silverColor(), 0, -2);

        // Set the scale region top-left corner at (5, 5), with size of 200 x 20
        // pixels. The scale labels are located on the bottom (implies horizontal
        // meter)
        m.setMeter(5, 5, 200, 20, Chart.Bottom);

        // Set meter scale from 0 - 100
        m.setScale(0, 100);

        // Add a title at the bottom of the meter with a 1 pixel raised 3D border
        m.addTitle2(Chart.Bottom, "Battery Level", "Arial Bold", 8).setBackground(
            Chart.Transparent, -1, 1);

        // Set 3 zones of different colors to represent Good/Weak/Bad data ranges
        m.addZone(50, 100, 0x99ff99, "Good");
        m.addZone(20, 50, 0xffff66, "Weak");
        m.addZone(0, 20, 0xffcccc, "Bad");

        // Add empty labels (just need the ticks) at 0/20/50/80 as separators for
        // zones
        m.addLabel(0, " ");
        m.addLabel(20, " ");
        m.addLabel(50, " ");
        m.addLabel(100, " ");

        // Add a semi-transparent blue (800000ff) pointer at the specified value,
        // using triangular pointer shape
        m.addPointer(value, 0x800000ff).setShape(Chart.TriangularPointer);

        // Output the chart
        viewer.setImage(m.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new linearzonemeter();

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

