package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class circlelabelpie implements DemoModule
{
    //Name of demo program
    public String toString() { return "Circular Label Layout"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 2; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {42, 18, 8};

        // The labels for the pie chart
        String[] labels = {"Agree", "Disagree", "Not Sure"};

        // The colors to use for the sectors
        int[] colors = {0x66ff66, 0xff6666, 0xffff00};

        // Create a PieChart object of size 300 x 300 pixels. Set the background to a
        // gradient color from blue (aaccff) to sky blue (ffffff), with a grey
        // (888888) border. Use rounded corners and soft drop shadow.
        PieChart c = new PieChart(300, 300);
        c.setBackground(c.linearGradientColor(0, 0, 0, c.getHeight() / 2, 0xaaccff,
            0xffffff), 0x888888);
        c.setRoundedFrame();
        c.setDropShadow();

        if (index == 0) {
        //============================================================
        //    Draw a pie chart where the label is on top of the pie
        //============================================================

            // Set the center of the pie at (150, 150) and the radius to 120 pixels
            c.setPieSize(150, 150, 120);

            // Set the label position to -40 pixels from the perimeter of the pie
            // (-ve means label is inside the pie)
            c.setLabelPos(-40);

        } else {
        //============================================================
        //    Draw a pie chart where the label is outside the pie
        //============================================================

            // Set the center of the pie at (150, 150) and the radius to 80 pixels
            c.setPieSize(150, 150, 80);

            // Set the sector label position to be 20 pixels from the pie. Use a join
            // line to connect the labels to the sectors.
            c.setLabelPos(20, Chart.LineColor);

        }

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Set the sector colors
        c.setColors2(Chart.DataColor, colors);

        // Use local gradient shading, with a 1 pixel semi-transparent black
        // (bb000000) border
        c.setSectorStyle(Chart.LocalGradientShading, 0xbb000000, 1);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: {value} responses ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
        //Instantiate an instance of this demo module
        DemoModule demo = new circlelabelpie();

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

