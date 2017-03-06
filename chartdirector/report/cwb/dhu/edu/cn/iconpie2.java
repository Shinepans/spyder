package chartdirector.report.cwb.dhu.edu.cn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import ChartDirector.*;

public class iconpie2 implements DemoModule
{
    //Name of demo program
    public String toString() { return "Icon Pie Chart (2)"; }

    //Number of charts produced in this demo
    public int getNoOfCharts() { return 1; }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // The data for the pie chart
        double[] data = {28, 45, 5, 1, 12};

        // The labels for the pie chart
        String[] labels = {"Excellent", "Good", "Bad", "Very Bad", "Neutral"};

        // The icons for the sectors
        String[] icons = {"laugh.png", "smile.png", "sad.png", "angry.png",
            "nocomment.png"};

        // Create a PieChart object of size 560 x 300 pixels, with a silver
        // background, black border, 1 pxiel 3D border effect and rounded corners
        PieChart c = new PieChart(560, 300, Chart.silverColor(), 0x000000, 1);
//        c.setLicenseCode("");
        c.setRoundedFrame();

        // Set the center of the pie at (280, 150) and the radius to 120 pixels
        c.setPieSize(280, 150, 120);

        // Add a title box with title written in CDML, on a sky blue (A0C8FF)
        // background with glass effect
        c.addTitle(
            "<*block,valign=absmiddle*><*img=doc.png*> Customer Survey: " +
            "<*font=Times New Roman Italic,color=000000*>Do you like our " +
            "<*font,color=dd0000*>Hyper<*super*>TM<*/font*> molecules?",
            "Times New Roman Bold Italic", 15, 0x000080).setBackground(0xa0c8ff,
            0x000000, Chart.glassEffect());

        // Add a logo to the chart written in CDML as the bottom title aligned to the
        // bottom right
        c.addTitle2(Chart.BottomRight,
            "<*block,valign=absmiddle*><*img=molecule.png*> <*block*><*color=FF*>" +
            "<*font=Times New Roman Bold Italic,size=12*>Molecular Engineering\n" +
            "<*font=Arial,size=10*>Creating better molecules");

        // Set the pie data and the pie labels
        c.setData(data, labels);

        // Set 3D style
        c.set3D();

        // Use the side label layout method
        c.setLabelLayout(Chart.SideLayout);

        // Set the label background color to transparent
        c.setLabelStyle().setBackground(Chart.Transparent);

        // Add icons to the chart as a custom field
        c.addExtraField(icons);

        // Configure the sector labels using CDML to include the icon images
        c.setLabelFormat(
            "<*block,valign=absmiddle*><*img={field0}*> {label} ({percent|0}%)");

        // Explode the 3rd and 4th sectors as a group (index = 2 and 3)
        c.setExplodeGroup(2, 3);

        // Set the start angle to 135 degrees may improve layout when there are many
        // small sectors at the end of the data array (that is, data sorted in
        // descending order). It is because this makes the small sectors position
        // near the horizontal axis, where the text label has the least tendency to
        // overlap. For data sorted in ascending order, a start angle of 45 degrees
        // can be used instead.
        c.setStartAngle(135);

        // Output the chart
        viewer.setImage(c.makeImage());

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
            "title='{label}: {value} responses ({percent}%)'"));
    }

    //Allow this module to run as standalone program for easy testing
    public static void main(String[] args)
    {
    	Chart.setLicenseCode("SXZVFNRN9MZ9L8LGA0E2B1BB");
        //Instantiate an instance of this demo module
        DemoModule demo = new iconpie2();

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

