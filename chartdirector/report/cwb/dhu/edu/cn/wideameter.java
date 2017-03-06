package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class wideameter implements DemoModule
{
    //Name of demo program
    public String toString() { return "Wide Angular Meters"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 6; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The value to display on the meter
        double value = 6.5;

        // Create an AugularMeter object of size 200 x 100 pixels with rounded
        // corners
        AngularMeter m = new AngularMeter(200, 100);
        m.setRoundedFrame();

        // Set meter background according to a parameter
        if (index == 0) {
            // Use gold background color
            m.setBackground(Chart.goldColor(), 0x000000, -2);
        } else if (index == 1) {
            // Use silver background color
            m.setBackground(Chart.silverColor(), 0x000000, -2);
        } else if (index == 2) {
            // Use metallic blue (9898E0) background color
            m.setBackground(Chart.metalColor(0x9898e0), 0x000000, -2);
        } else if (index == 3) {
            // Use a wood pattern as background color
            m.setBackground(m.patternColor2("wood.png"), 0x000000, -2);
        } else if (index == 4) {
            // Use a marble pattern as background color
            m.setBackground(m.patternColor2("marble.png"), 0x000000, -2);
        } else {
            // Use a solid light purple (EEBBEE) background color
            m.setBackground(0xeebbee, 0x000000, -2);
        }

        // Set the meter center at (100, 235), with radius 210 pixels, and span from
        // -24 to +24 degress
        m.setMeter(100, 235, 210, -24, 24);

        // Meter scale is 0 - 100, with a tick every 1 unit
        m.setScale(0, 10, 1);

        // Set 0 - 6 as green (99ff99) zone, 6 - 8 as yellow (ffff00) zone, and 8 -
        // 10 as red (ff3333) zone
        m.addZone(0, 6, 0x99ff99, 0x808080);
        m.addZone(6, 8, 0xffff00, 0x808080);
        m.addZone(8, 10, 0xff3333, 0x808080);

        // Add a title at the bottom of the meter using 10 pts Arial Bold font
        m.addTitle2(Chart.Bottom, "OUTPUT POWER LEVEL\n", "Arial Bold", 10);

        // Add a semi-transparent black (80000000) pointer at the specified value
        m.addPointer(value, 0x80000000);

        // Output the chart
        viewer.setImage(m.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new wideameter();

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

