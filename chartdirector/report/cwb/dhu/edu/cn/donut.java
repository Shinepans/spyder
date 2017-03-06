package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class donut implements DemoModule
{
    //Name of demo program
    public String toString() { return "Donut Chart"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {25, 18, 15, 12, 8, 30, 35};

        // The labels for the pie chart
        String[] labels = {"Labor", "Licenses", "Taxes", "Legal", "Insurance",
            "Facilities", "Production"};

        // Create a PieChart object of size 600 x 320 pixels. Set background color to
        // brushed silver, with a 2 pixel 3D border. Use rounded corners of 20 pixels
        // radius.
        PieChart c = new PieChart(600, 320, Chart.brushedSilverColor(),
            Chart.Transparent, 2);
        c.setRoundedFrame(0xffffff, 20);

        // Add a title using 18 pts Times New Roman Bold Italic font. #Set top/bottom
        // margins to 8 pixels.
        TextBox title = c.addTitle("Donut Chart Demonstration",
            "Times New Roman Bold Italic", 18);
        title.setMargin2(0, 0, 8, 8);

        // Add a 2 pixels wide separator line just under the title
        c.addLine(10, title.getHeight(), c.getWidth() - 11, title.getHeight(),
            Chart.LineColor, 2);

        // Set donut center at (160, 175), and outer/inner radii as 110/55 pixels
        c.setDonutSize(160, 175, 110, 55);

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Use ring shading effect for the sectors
        c.setSectorStyle(Chart.RingShading);

        // Use the side label layout method, with the labels positioned 16 pixels
        // from the donut bounding box
        c.setLabelLayout(Chart.SideLayout, 16);

        // Show only the sector number as the sector label
        c.setLabelFormat("{={sector}+1}");

        // Set the sector label style to Arial Bold 10pt, with a dark grey (444444)
        // border
        c.setLabelStyle("Arial Bold", 10).setBackground(Chart.Transparent, 0x444444);

        // Add a legend box, with the center of the left side anchored at (330, 175),
        // and using 10 pts Arial Bold Italic font
        LegendBox b = c.addLegend(330, 175, true, "Arial Bold Italic", 10);
        b.setAlignment(Chart.Left);

        // Set the legend box border to dark grey (444444), and with rounded conerns
        b.setBackground(Chart.Transparent, 0x444444);
        b.setRoundedCorners();

        // Set the legend box margin to 16 pixels, and the extra line spacing between
        // the legend entries as 5 pixels
        b.setMargin(16);
        b.setKeySpacing(0, 5);

        // Set the legend text to show the sector number, followed by a 120 pixels
        // wide block showing the sector label, and a 40 pixels wide block showing
        // the percentage
        b.setText(
            "<*block,valign=top*>{={sector}+1}.<*advanceTo=22*><*block,width=120*>" +
            "{label}<*/*><*block,width=40,halign=right*>{percent}<*/*>%");

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: US${value}K ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new donut();

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

