package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class multivmeter implements DemoModule
{
    //Name of demo program
    public String toString() { return "Multi-Pointer Vertical Meter"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The values to display on the meter
        double value0 = 30.99;
        double value1 = 45.35;
        double value2 = 77.64;

        // Create an LinearMeter object of size 60 x 245 pixels, using silver
        // background with a 2 pixel black 3D depressed border.
        LinearMeter m = new LinearMeter(60, 245, Chart.silverColor(), 0, -2);

        // Set the scale region top-left corner at (25, 30), with size of 20 x 200
        // pixels. The scale labels are located on the left (default - implies
        // vertical meter)
        m.setMeter(25, 30, 20, 200);

        // Set meter scale from 0 - 100, with a tick every 10 units
        m.setScale(0, 100, 10);

        // Set 0 - 50 as green (99ff99) zone, 50 - 80 as yellow (ffff66) zone, and 80
        // - 100 as red (ffcccc) zone
        m.addZone(0, 50, 0x99ff99);
        m.addZone(50, 80, 0xffff66);
        m.addZone(80, 100, 0xffcccc);

        // Add deep red (000080), deep green (008000) and deep blue (800000) pointers
        // to reflect the values
        m.addPointer(value0, 0x000080);
        m.addPointer(value1, 0x008000);
        m.addPointer(value2, 0x800000);

        // Add a text box label at top-center (30, 5) using Arial Bold/8 pts/deep
        // blue (000088), with a light blue (9999ff) background
        m.addText(30, 5, "Temp C", "Arial Bold", 8, 0x000088, Chart.TopCenter
            ).setBackground(0x9999ff);

        // Output the chart
        viewer.setImage(m.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new multivmeter();

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

