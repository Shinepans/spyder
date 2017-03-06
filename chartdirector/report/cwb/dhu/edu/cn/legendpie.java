package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class legendpie implements DemoModule
{
    //Name of demo program
    public String toString() { return "Pie Chart with Legend (1)"; }

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

        // Create a PieChart object of size 450 x 270 pixels
        PieChart c = new PieChart(450, 270);

        // Set the center of the pie at (150, 100) and the radius to 80 pixels
        c.setPieSize(150, 135, 100);

        // add a legend box where the top left corner is at (330, 50)
        c.addLegend(330, 60);

        // modify the sector label format to show percentages only
        c.setLabelFormat("{percent}%");

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Use rounded edge shading, with a 1 pixel white (FFFFFF) border
        c.setSectorStyle(Chart.RoundedEdgeShading, 0xffffff, 1);

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
        DemoModule demo = new legendpie();

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

