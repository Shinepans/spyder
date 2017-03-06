package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class pieshading implements DemoModule
{
    //Name of demo program
    public String toString() { return "2D Pie Shading"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 6; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {18, 30, 20, 15};

        // The labels for the pie chart
        String[] labels = {"Labor", "Licenses", "Facilities", "Production"};

        // The colors to use for the sectors
        int[] colors = {0x66aaee, 0xeebb22, 0xbbbbbb, 0x8844ff};

        // Create a PieChart object of size 200 x 220 pixels. Use a vertical gradient
        // color from blue (0000cc) to deep blue (000044) as background. Use rounded
        // corners of 16 pixels radius.
        PieChart c = new PieChart(200, 220);
        c.setBackground(c.linearGradientColor(0, 0, 0, c.getHeight(), 0x0000cc,
            0x000044));
        c.setRoundedFrame(0xffffff, 16);

        // Set the center of the pie at (100, 120) and the radius to 80 pixels
        c.setPieSize(100, 120, 80);

        // Set the pie data
        c.setData(data, labels);

        // Set the sector colors
        c.setColors2(Chart.DataColor, colors);

        // Demonstrates various shading modes
        if (index == 0) {
            c.addTitle("Default Shading", "bold", 12, 0xffffff);
        } else if (index == 1) {
            c.addTitle("Local Gradient", "bold", 12, 0xffffff);
            c.setSectorStyle(Chart.LocalGradientShading);
        } else if (index == 2) {
            c.addTitle("Global Gradient", "bold", 12, 0xffffff);
            c.setSectorStyle(Chart.GlobalGradientShading);
        } else if (index == 3) {
            c.addTitle("Concave Shading", "bold", 12, 0xffffff);
            c.setSectorStyle(Chart.ConcaveShading);
        } else if (index == 4) {
            c.addTitle("Rounded Edge", "bold", 12, 0xffffff);
            c.setSectorStyle(Chart.RoundedEdgeShading);
        } else if (index == 5) {
            c.addTitle("Radial Gradient", "bold", 12, 0xffffff);
            c.setSectorStyle(Chart.RadialShading);
        }

        // Disable the sector labels by setting the color to Transparent
        c.setLabelStyle("", 8, Chart.Transparent);

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
        DemoModule demo = new pieshading();

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

