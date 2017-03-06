package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class missingpoints implements DemoModule
{
    //Name of demo program
    public String toString() { return "Missing Data Points"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the chart
        double[] data0 = {42, 49, Chart.NoValue, 38, 64, 56, 29, 41, 44, 57};
        double[] data1 = {65, 75, 47, 34, 42, 49, 73, Chart.NoValue, 90, 69, 66, 78};
        double[] data2 = {Chart.NoValue, Chart.NoValue, 25, 28, 38, 20, 22,
            Chart.NoValue, 25, 33, 30, 24};
        String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"};

        // Create a XYChart object of size 600 x 360 pixels. Set background color to
        // brushed silver, with a 2 pixel 3D border. Use rounded corners.
        XYChart c = new XYChart(600, 360, Chart.brushedSilverColor(),
            Chart.Transparent, 2);
        c.setRoundedFrame();

        // Add a title using 18 pts Times New Roman Bold Italic font. #Set top/bottom
        // margins to 6 pixels.
        TextBox title = c.addTitle("Product Line Global Revenue",
            "Times New Roman Bold Italic", 18);
        title.setMargin2(0, 0, 6, 6);

        // Add a separator line just under the title
        c.addLine(10, title.getHeight(), c.getWidth() - 11, title.getHeight(),
            Chart.LineColor);

        // Add a legend box where the top-center is anchored to the horizontal center
        // of the chart, just under the title. Use horizontal layout and 10 points
        // Arial Bold font, and transparent background and border.
        LegendBox legendBox = c.addLegend(c.getWidth() / 2, title.getHeight(), false,
            "Arial Bold", 10);
        legendBox.setAlignment(Chart.TopCenter);
        legendBox.setBackground(Chart.Transparent, Chart.Transparent);

        // Tentatively set the plotarea at (70, 75) and of 460 x 240 pixels in size.
        // Use transparent border and black (000000) grid lines
        c.setPlotArea(70, 75, 460, 240, -1, -1, Chart.Transparent, 0x000000, -1);

        // Set the x axis labels
        c.xAxis().setLabels(labels);

        // Show the same scale on the left and right y-axes
        c.syncYAxis();

        // Set y-axis tick density to 30 pixels. ChartDirector auto-scaling will use
        // this as the guideline when putting ticks on the y-axis.
        c.yAxis().setTickDensity(30);

        // Set all axes to transparent
        c.xAxis().setColors(Chart.Transparent);
        c.yAxis().setColors(Chart.Transparent);
        c.yAxis2().setColors(Chart.Transparent);

        // Set the x-axis margins to 15 pixels, so that the horizontal grid lines can
        // extend beyond the leftmost and rightmost vertical grid lines
        c.xAxis().setMargin(15, 15);

        // Set axis label style to 8pts Arial Bold
        c.xAxis().setLabelStyle("Arial Bold", 8);
        c.yAxis().setLabelStyle("Arial Bold", 8);
        c.yAxis2().setLabelStyle("Arial Bold", 8);

        // Add axis title using 10pts Arial Bold Italic font
        c.yAxis().setTitle("Revenue in USD millions", "Arial Bold Italic", 10);
        c.yAxis2().setTitle("Revenue in USD millions", "Arial Bold Italic", 10);

        // Add the first line. The missing data will be represented as gaps in the
        // line (the default behaviour)
        LineLayer layer0 = c.addLineLayer2();
        layer0.addDataSet(data0, 0xff0000, "Quantum Computer").setDataSymbol(
            Chart.GlassSphere2Shape, 11);
        layer0.setLineWidth(3);

        // Add the second line. The missing data will be represented by using dash
        // lines to bridge the gap
        LineLayer layer1 = c.addLineLayer2();
        layer1.addDataSet(data1, 0x00ff00, "Atom Synthesizer").setDataSymbol(
            Chart.GlassSphere2Shape, 11);
        layer1.setLineWidth(3);
        layer1.setGapColor(c.dashLineColor(0x00ff00));

        // Add the third line. The missing data will be ignored - just join the gap
        // with the original line style.
        LineLayer layer2 = c.addLineLayer2();
        layer2.addDataSet(data2, 0xff6600, "Proton Cannon").setDataSymbol(
            Chart.GlassSphere2Shape, 11);
        layer2.setLineWidth(3);
        layer2.setGapColor(Chart.SameAsMainColor);

        // layout the legend so we can get the height of the legend box
        c.layoutLegend();

        // Adjust the plot area size, such that the bounding box (inclusive of axes)
        // is 15 pixels from the left edge, just under the legend box, 16 pixels from
        // the right edge, and 25 pixels from the bottom edge.
        c.packPlotArea(15, legendBox.getTopY() + legendBox.getHeight(), c.getWidth()
             - 16, c.getHeight() - 25);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='Revenue of {dataSetName} in {xLabel}: US$ {value}M'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new missingpoints();

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

