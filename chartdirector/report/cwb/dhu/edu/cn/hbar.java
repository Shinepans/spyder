
package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class hbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Borderless Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data = {3.9, 8.1, 10.9, 14.2, 18.1, 19.0, 21.2, 23.2, 25.7, 36};

        // The labels for the bar chart
        String[] labels = {"Bastic Group", "Simpa", "YG Super", "CID", "Giga Tech",
            "Indo Digital", "Supreme", "Electech", "THP Thunder", "Flash Light"};

        // Create a XYChart object of size 600 x 250 pixels
        XYChart c = new XYChart(600, 250);

        // Add a title to the chart using Arial Bold Italic font
        c.addTitle("Revenue Estimation - Year 2002", "Arial Bold Italic");

        // Set the plotarea at (100, 30) and of size 400 x 200 pixels. Set the
        // plotarea border, background and grid lines to Transparent
        c.setPlotArea(100, 30, 400, 200, Chart.Transparent, Chart.Transparent,
            Chart.Transparent, Chart.Transparent, Chart.Transparent);

        // Add a bar chart layer using the given data. Use a gradient color for the
        // bars, where the gradient is from dark green (0x008000) to white (0xffffff)
        BarLayer layer = c.addBarLayer(data, c.gradientColor(100, 0, 500, 0,
            0x008000, 0xffffff));

        // Swap the axis so that the bars are drawn horizontally
        c.swapXY(true);

        // Set the bar gap to 10%
        layer.setBarGap(0.1);

        // Use the format "US$ xxx millions" as the bar label
        layer.setAggregateLabelFormat("US$ {value} millions");

        // Set the bar label font to 10 pts Times Bold Italic/dark red (0x663300)
        layer.setAggregateLabelStyle("Times New Roman Bold Italic", 10, 0x663300);

        // Set the labels on the x axis
        TextBox textbox = c.xAxis().setLabels(labels);

        // Set the x axis label font to 10pt Arial Bold Italic
        textbox.setFontStyle("Arial Bold Italic");
        textbox.setFontSize(10);

        // Set the x axis to Transparent, with labels in dark red (0x663300)
        c.xAxis().setColors(Chart.Transparent, 0x663300);

        // Set the y axis and labels to Transparent
        c.yAxis().setColors(Chart.Transparent, Chart.Transparent);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{xLabel}: US${value} millions'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new hbar();

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

