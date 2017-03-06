package chartdirector.report.cwb.dhu.edu.cn;
/////////////////////////////////////////////////////////////////////////////
//
//   DemoModule
//   ==========
//   Represents the function each demo chart module must provide
//
/////////////////////////////////////////////////////////////////////////////


public interface DemoModule
{
    // A human readable name for the module
    public String toString();

    // The number of demo charts generated
    public int getNoOfCharts();

    // Generate a demo chart. The index argument indicate which demo chart to
    // generate. It must be a number from 0 to (noOfCharts - 1).
    public void createChart(ChartDirector.ChartViewer viewer, int index);
   
    
}


