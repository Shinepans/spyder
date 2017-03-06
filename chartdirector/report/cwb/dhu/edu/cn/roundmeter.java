package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class roundmeter implements DemoModule
{
    //Name of demo program
    public String toString() { return "Round Meter"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The value to display on the meter
        double value = 45.17;

        // Create an AugularMeter object of size 200 x 200 pixels, with silver
        // background, black border, 2 pixel 3D depressed border and rounded corners.
        AngularMeter m = new AngularMeter(200, 200, Chart.silverColor(), 0x000000, -2
            );
        m.setRoundedFrame();

        // Set the meter center at (100, 100), with radius 85 pixels, and span from
        // -135 to +135 degress
        m.setMeter(100, 100, 85, -135, 135);

        // Meter scale is 0 - 100, with major tick every 10 units, minor tick every 5
        // units, and micro tick every 1 units
        m.setScale(0, 100, 10, 5, 1);

        // Disable default angular arc by setting its width to 0. Set 2 pixels line
        // width for major tick, and 1 pixel line width for minor ticks.
        m.setLineWidth(0, 2, 1);

        // Set the circular meter surface as metallic blue (9999DD)
        m.addRing(0, 90, Chart.metalColor(0x9999dd));

        // Add a blue (6666FF) ring between radii 88 - 90 as decoration
        m.addRing(88, 90, 0x6666ff);

        // Set 0 - 60 as green (99FF99) zone, 60 - 80 as yellow (FFFF00) zone, and 80
        // - 100 as red (FF3333) zone
        m.addZone(0, 60, 0x99ff99);
        m.addZone(60, 80, 0xffff00);
        m.addZone(80, 100, 0xff3333);

        // Add a text label centered at (100, 135) with 15 pts Arial Bold font
        m.addText(100, 135, "CPU", "Arial Bold", 15, Chart.TextColor, Chart.Center);

        // Add a text box centered at (100, 165) showing the value formatted to 2
        // decimal places, using white text on a black background, and with 1 pixel
        // 3D depressed border
        m.addText(100, 165, m.formatValue(value, "2"), "Arial", 8, 0xffffff,
            Chart.Center).setBackground(0x000000, 0x000000, -1);

        // Add a semi-transparent blue (40333399) pointer at the specified value
        m.addPointer(value, 0x40333399);

        // Output the chart
        viewer.setImage(m.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new roundmeter();

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

