package chartdirector.report.cwb.dhu.edu.cn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class boxwhisker2 implements DemoModule
{
    //Name of demo program
    public String toString() { return "Horizontal Box-Whisker Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Sample data for the Box-Whisker chart. Represents the minimum, 1st
        // quartile, medium, 3rd quartile and maximum values of some quantities
        double[] Q0Data = {40, 45, 35};
        double[] Q1Data = {55, 60, 50};
        double[] Q2Data = {62, 70, 60};
        double[] Q3Data = {70, 80, 65};
        double[] Q4Data = {80, 90, 75};

        // The labels for the chart
        String[] labels = {"<*img=robot1.png*><*br*>Bipedal Type",
            "<*img=robot2.png*><*br*>Wolf Type", "<*img=robot5.png*><*br*>Bird Type"}
            ;

        // Create a XYChart object of size 540 x 320 pixels
        XYChart c = new XYChart(540, 320);

        // swap the x and y axes to create a horizontal box-whisker chart
        c.swapXY();

        // Set the plotarea at (75, 25) and of size 440 x 270 pixels. Enable both
        // horizontal and vertical grids by setting their colors to grey (0xc0c0c0)
        c.setPlotArea(75, 25, 440, 270).setGridColor(0xc0c0c0, 0xc0c0c0);

        // Add a title to the chart
        c.addTitle("           Robot Shooting Accuracy Scores");

        // Set the labels on the x axis and the font to Arial Bold
        c.xAxis().setLabels(labels).setFontStyle("Arial Bold");

        // Disable x axis ticks by setting the length to 0
        c.xAxis().setTickLength(0);

        // Set the font for the y axis labels to Arial Bold
        c.yAxis().setLabelStyle("Arial Bold");

        // Add a Box Whisker layer using light blue 0x9999ff as the fill color and
        // blue (0xcc) as the line color. Set the line width to 2 pixels
        c.addBoxWhiskerLayer2(Q3Data, Q1Data, Q4Data, Q0Data, Q2Data).setLineWidth(2)
            ;

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel}: min/med/max = {min}/{med}/{max}  Inter-quartile " +
            "range: {bottom} to {top}'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new boxwhisker2();

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

