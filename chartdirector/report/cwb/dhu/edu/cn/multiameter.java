package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class multiameter implements DemoModule
{
    //Name of demo program
    public String toString() { return "Multi-Pointer Angular Meter"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Create an AugularMeter object of size 200 x 200 pixels
        AngularMeter m = new AngularMeter(200, 200);

        // Use white on black color palette for default text and line colors
        m.setColors(Chart.whiteOnBlackPalette);

        // Set the meter center at (100, 100), with radius 85 pixels, and span from 0
        // to 360 degress
        m.setMeter(100, 100, 85, 0, 360);

        // Meter scale is 0 - 100, with major tick every 10 units, minor tick every 5
        // units, and micro tick every 1 units
        m.setScale(0, 100, 10, 5, 1);

        // Set angular arc, major tick and minor tick line widths to 2 pixels.
        m.setLineWidth(2, 2, 2);

        // Add a blue (9999ff) ring between radii 88 - 90 as decoration
        m.addRing(88, 90, 0x9999ff);

        // Set 0 - 60 as green (00AA00) zone, 60 - 80 as yellow (CCCC00) zone, and 80
        // - 100 as red (AA0000) zone
        m.addZone(0, 60, 0x00aa00);
        m.addZone(60, 80, 0xcccc00);
        m.addZone(80, 100, 0xaa0000);

        // Add a text label centered at (100, 70) with 12 pts Arial Bold font
        m.addText(100, 70, "PSI", "Arial Bold", 12, Chart.TextColor, Chart.Center);

        // Add a semi-transparent blue (806666FF) pointer    using the default shape
        m.addPointer(25, 0x806666ff, 0x6666ff);

        // Add a semi-transparent red (80FF6666) pointer using the arrow shape
        m.addPointer(9, 0x80ff6666, 0xff6666).setShape(Chart.ArrowPointer2);

        // Add a semi-transparent yellow (80FFFF66) pointer using another arrow shape
        m.addPointer(51, 0x80ffff66, 0xffff66).setShape(Chart.ArrowPointer);

        // Add a semi-transparent green (8066FF66) pointer using the line shape
        m.addPointer(72, 0x8066ff66, 0x66ff66).setShape(Chart.LinePointer);

        // Add a semi-transparent grey (80CCCCCC) pointer using the pencil shape
        m.addPointer(85, 0x80cccccc, 0xcccccc).setShape(Chart.PencilPointer);

        // Output the chart
        viewer.setImage(m.makeImage());
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new multiameter();

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

