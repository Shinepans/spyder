package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class percentbar implements DemoModule
{
    //Name of demo program
    public String toString() { return "Percentage Bar Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the bar chart
        double[] data0 = {100, 125, 245, 147, 67};
        double[] data1 = {85, 156, 179, 211, 123};
        double[] data2 = {97, 87, 56, 267, 157};

        // The labels for the bar chart
        String[] labels = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create a XYChart object of size 500 x 320 pixels. Use a vertical gradient
        // color from pale blue (e8f0f8) to sky blue (aaccff) spanning half the chart
        // height as background. Set border to blue (88aaee). Use rounded corners.
        // Enable soft drop shadow.
        XYChart c = new XYChart(500, 320);
        c.setBackground(c.linearGradientColor(0, 0, 0, c.getHeight() / 2, 0xe8f0f8,
            0xaaccff), 0x88aaee);
        c.setRoundedFrame();
        c.setDropShadow();

        // Add a title to the chart using 15 points Arial Italic. Set top/bottom
        // margins to 15 pixels.
        TextBox title = c.addTitle("Weekly Product Sales", "Arial Italic", 15);
        title.setMargin2(0, 0, 15, 15);

        // Tentatively set the plotarea to 50 pixels from the left edge, and to just
        // under the title. Set the width to 60% of the chart width, and the height
        // to 50 pixels from the bottom edge. Use pale blue (e8f0f8) background,
        // transparent border, and grey (aaaaaa) grid lines.
        c.setPlotArea(50, title.getHeight(), c.getWidth() * 6 / 10, c.getHeight() -
            title.getHeight() - 50, 0xe8f0f8, -1, Chart.Transparent, 0xaaaaaa);

        // Add a legend box where the top-right corner is anchored at 10 pixels from
        // the right edge, and just under the title. Use vertical layout and 8 points
        // Arial font.
        LegendBox legendBox = c.addLegend(c.getWidth() - 10, title.getHeight(), true,
            "Arial", 8);
        legendBox.setAlignment(Chart.TopRight);

        // Set the legend box background and border to transparent
        legendBox.setBackground(Chart.Transparent, Chart.Transparent);

        // Set the legend box icon size to 16 x 32 pixels to match with custom icon
        // size
        legendBox.setKeySize(16, 32);

        // Set axes to transparent
        c.xAxis().setColors(Chart.Transparent);
        c.yAxis().setColors(Chart.Transparent);

        // Set the labels on the x axis
        c.xAxis().setLabels(labels);

        // Add a percentage bar layer
        BarLayer layer = c.addBarLayer2(Chart.Percentage);

        // Add the three data sets to the bar layer, using icons images with labels
        // as data set names
        layer.addDataSet(data0, 0x66aaee,
            "<*block,valign=absmiddle*><*img=service.png*> Service<*/*>");
        layer.addDataSet(data1, 0xeebb22,
            "<*block,valign=absmiddle*><*img=software.png*> Software<*/*>");
        layer.addDataSet(data2, 0xcc88ff,
            "<*block,valign=absmiddle*><*img=computer.png*> Hardware<*/*>");

        // Use soft lighting effect with light direction from top
        layer.setBorderColor(Chart.Transparent, Chart.softLighting(Chart.Top));

        // Enable data label at the middle of the the bar
        layer.setDataLabelStyle().setAlignment(Chart.Center);

        // For a vertical stacked chart with positive data only, the last data set is
        // always on top. However, in a vertical legend box, the last data set is at
        // the bottom. This can be reversed by using the setLegend method.
        layer.setLegend(Chart.ReverseLegend);

        // Adjust the plot area size, such that the bounding box (inclusive of axes)
        // is 15 pixels from the left edge, just below the title, 10 pixels to the
        // right of the legend box, and 15 pixels from the bottom edge.
        c.packPlotArea(15, title.getHeight(), c.layoutLegend().getLeftX() - 10,
            c.getHeight() - 15);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{dataSetName} revenue on {xLabel}: US${value}K ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new percentbar();

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

