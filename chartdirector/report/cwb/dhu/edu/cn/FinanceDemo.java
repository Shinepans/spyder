package chartdirector.report.cwb.dhu.edu.cn;
import ChartDirector.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FinanceDemo extends JFrame implements DemoModule
{
	// The ticker symbol, timeStamps, volume, high, low, open and close data    
	String tickerKey = "";
    private Date[] timeStamps;  
    private double[] volData;       //The volume values.
    private double[] highData;      //The high values.
    private double[] lowData;       //The low values.
    private double[] openData;      //The open values.
    private double[] closeData;     //The close values.

    // An extra data series to compare with the close data
	String compareKey = "";
    double[] compareData = null;

    // The resolution of the data in seconds. 1 day = 86400 seconds.
    int resolution = 86400;
 
    // The moving average periods
    private int avgPeriod1;
    private int avgPeriod2;

    // Will set to true at the end of initialization
    private boolean hasFinishedInitialization;
    
    //
    // Controls in the JFrame
    //
	private JTextField tickerSymbol;
	private JTextField compareWith;
	private JComboBox timeRange;
	private JComboBox chartSize;
	private JCheckBox volumeBars;
	private JCheckBox parabolicSAR;
	private JCheckBox logScale;
	private JCheckBox percentageScale;
	private JComboBox chartType;
	private JComboBox priceBand;
    private JComboBox avgType1;
	private JTextField movAvg1;
    private JComboBox avgType2;
	private JTextField movAvg2;
    private JComboBox indicator1;
    private JComboBox indicator2;
    private JComboBox indicator3;
    private JComboBox indicator4;
 	private ChartViewer chartViewer1;
  
    /// <summary>
    /// A utility class for adding items to ComboBox
    /// </summary>
    private static class ListItem
    {
        private String m_key;
        private String m_value;

        private ListItem(String key, String val)
        {
            m_key = key;
            m_value = val;
        }

        private String getKey()
        {
            return m_key;
        }

        public String toString()
        {
            return m_value;
        }
    }
        
    /// <summary>
    /// The main method to allow this demo to run as a standalone program.
    /// </summary>
    public static void main(String args[]) 
    {
        FinanceDemo d = new FinanceDemo();
        d.initComponents(true);
        d.setVisible(true);
    }
    
    /// <summary>
    /// Create the JFrame and put controls in it. 
    /// </summary>
    private void initComponents(boolean closeOnExit) 
    {
        // Do nothing if already initialized
        if (hasFinishedInitialization)
            return;
        
        setTitle("ChartDirector Financial Chart Demonstration");

        // End application upon closing main window
        if (closeOnExit)
        {
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {System.exit(0);} });
        }

        // Top label bar
        JLabel topLabel = new JLabel("Advanced Software Engineering");
        topLabel.setForeground(new java.awt.Color(255, 255, 51));
        topLabel.setBackground(new java.awt.Color(0, 0, 128));
        topLabel.setBorder(new javax.swing.border.EmptyBorder(2, 0, 2, 5));
        topLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        topLabel.setOpaque(true);
        getContentPane().add(topLabel, java.awt.BorderLayout.NORTH);

        // Left panel
        JPanel leftPanel = new JPanel(null);

		// Ticker Symbol
        leftPanel.add(new JLabel("Ticker Symbol")).setBounds(8, 4, 140, 18);
        tickerSymbol = new JTextField("ASE.SYMBOL");
        leftPanel.add(tickerSymbol).setBounds(8, 22, 140, 20);
        
        // Compare With
        leftPanel.add(new JLabel("Compare With")).setBounds(8, 46, 140, 18);
        compareWith = new JTextField();
        leftPanel.add(compareWith).setBounds(8, 64, 140, 20);

        // Time Period
        leftPanel.add(new JLabel("Time Period")).setBounds(8, 88, 140, 18);
        timeRange = new JComboBox();
        leftPanel.add(timeRange).setBounds(8, 106, 140, 20);
        
        // Chart Size
        leftPanel.add(new JLabel("Chart Size")).setBounds(8, 130, 140, 18);
        chartSize = new JComboBox();
        leftPanel.add(chartSize).setBounds(8, 148, 140, 20);

        // Value bars/Log Scale/Grid Lines
        volumeBars = new JCheckBox("Show Volume Bars", true);
        parabolicSAR = new JCheckBox("Parabolic SAR", false);
		logScale = new JCheckBox("Log Scale", false);
        percentageScale = new JCheckBox("Percentage Grid", false);
        leftPanel.add(volumeBars).setBounds(8, 172, 140, 20);
        leftPanel.add(parabolicSAR).setBounds(8, 192, 140, 20);
        leftPanel.add(logScale).setBounds(8, 212, 140, 20);
        leftPanel.add(percentageScale).setBounds(8, 232, 140, 20);
         
        // Chart Type
        leftPanel.add(new JLabel("Chart Type")).setBounds(8, 256, 140, 18);
        chartType = new JComboBox();
        leftPanel.add(chartType).setBounds(8, 274, 140, 20);

        // Price Bands
        leftPanel.add(new JLabel("Price Bands")).setBounds(8, 298, 140, 18);
        priceBand = new JComboBox();
        leftPanel.add(priceBand).setBounds(8, 316, 140, 20);
        
        // Moving Averages
        leftPanel.add(new JLabel("Moving Averages")).setBounds(8, 340, 140, 18);
        avgType1 = new JComboBox();
        avgType2 = new JComboBox();
        movAvg1 = new JTextField("10");
        movAvg2 = new JTextField("25");
        leftPanel.add(avgType1).setBounds(8, 358, 105, 20);
        leftPanel.add(avgType2).setBounds(8, 378, 105, 20);
        leftPanel.add(movAvg1).setBounds(113, 358, 35, 20);
        leftPanel.add(movAvg2).setBounds(113, 378, 35, 20);

        // Technical Indicators
        leftPanel.add(new JLabel("Technical Indicators")).setBounds(8, 402, 140, 18);
        indicator1 = new JComboBox();
        indicator2 = new JComboBox();
        indicator3 = new JComboBox();
        indicator4 = new JComboBox();
        leftPanel.add(indicator1).setBounds(8, 420, 140, 20);
        leftPanel.add(indicator2).setBounds(8, 440, 140, 20);
        leftPanel.add(indicator3).setBounds(8, 460, 140, 20);
        leftPanel.add(indicator4).setBounds(8, 480, 140, 20);

        // Put Left Panel in a scroll pane
        leftPanel.setPreferredSize(new Dimension(150, 510));
        JScrollPane leftScrollPane = new JScrollPane(leftPanel, 
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setPreferredSize(new Dimension(170, 515));
        
        // Chart Viewer
        chartViewer1 = new ChartViewer();
        chartViewer1.setBackground(new java.awt.Color(255, 255, 255));
        chartViewer1.setHorizontalAlignment(SwingConstants.CENTER);
        chartViewer1.setOpaque(true);
        
        // Put Chart Viewer in a scroll pane
        JScrollPane rightScrollPane = new JScrollPane(chartViewer1);
        rightScrollPane.setPreferredSize(new Dimension(800, 490));

        // Put the LeftPanel and RightScrollPane on the JFrame
        getContentPane().add(leftScrollPane, java.awt.BorderLayout.WEST);
        getContentPane().add(rightScrollPane, java.awt.BorderLayout.CENTER);

        // Font to use for user interface elements
        Font controlFont = new Font("Dialog", Font.PLAIN, 11);
        Font labelFont = new Font("Dialog", Font.BOLD, 11);
        
        // Use the same action hanlder for all controls
        ActionListener actionHandler = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSelection(evt); }};
                
        // Text field connects to the textChangeHandler for validating the text
        FocusAdapter textChangeHandler = new FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textChanged(evt); }};

		for (int i = 0; i < leftPanel.getComponentCount(); ++i)
        {
            // Set all UI fonts (except labels) to uiFont
            Component c = leftPanel.getComponent(i);
            if (!(c instanceof JLabel))
                c.setFont(controlFont);
            else
            	c.setFont(labelFont);
            
            // Connect controls to event handlers
            if (c instanceof JComboBox)
                ((JComboBox)c).addActionListener(actionHandler);
            if (c instanceof JCheckBox)
                ((JCheckBox)c).addActionListener(actionHandler);
            if (c instanceof JTextField)
            {
                ((JTextField)c).addActionListener(actionHandler);
                ((JTextField)c).addFocusListener(textChangeHandler);
            }
        }
        
        //
        // Fill the contents of the combo boxes
        //

        timeRange.setModel(new DefaultComboBoxModel(new ListItem[]
        {
             new ListItem("1", "1 day"),
             new ListItem("2", "2 days"),
             new ListItem("5", "5 days"),
             new ListItem("10", "10 days"),
             new ListItem("30", "1 month"),
             new ListItem("60", "2 months"),
             new ListItem("90", "3 months"),
             new ListItem("180", "6 months"),
             new ListItem("360", "1 year"),
             new ListItem("720", "2 years"),
             new ListItem("1080", "3 years"),
             new ListItem("1440", "4 years"),
             new ListItem("1800", "5 years"),
             new ListItem("3600", "10 years")
        }));
        timeRange.setSelectedIndex(7);
        
        chartSize.setModel(new DefaultComboBoxModel(new ListItem[]
        {
             new ListItem("S", "Small"),
             new ListItem("M", "Medium"),
             new ListItem("L", "Large"),
             new ListItem("H", "Huge")
        }));
        chartSize.setSelectedIndex(2);
        
        chartType.setModel(new DefaultComboBoxModel(new ListItem[]
        {
             new ListItem("None", "None"),
             new ListItem("CandleStick", "CandleStick"),
             new ListItem("Close", "Closing Price"),
             new ListItem("Median", "Median Price"),
             new ListItem("OHLC", "OHLC"),
             new ListItem("TP", "Typical Price"),
             new ListItem("WC", "Weighted Close")
        }));
        chartType.setSelectedIndex(1);

        priceBand.setModel(new DefaultComboBoxModel(new ListItem[]
         {
             new ListItem("None", "None"),
             new ListItem("BB", "Bollinger Band"),
             new ListItem("DC", "Donchain Channel"),
             new ListItem("Envelop", "Envelop (SMA 20 +/- 10%)")
         }));
        priceBand.setSelectedIndex(1);
            
        avgType1.setModel(new DefaultComboBoxModel(new ListItem[]
        {
            new ListItem("None", "None"),
            new ListItem("SMA", "Simple"),
            new ListItem("EMA", "Exponential"),
            new ListItem("TMA", "Triangular"),
            new ListItem("WMA", "Weighted")
        }));
        avgType1.setSelectedIndex(1);

        avgType2.setModel(new DefaultComboBoxModel(new ListItem[]
        {
            new ListItem("None", "None"),
            new ListItem("SMA", "Simple"),
            new ListItem("EMA", "Exponential"),
            new ListItem("TMA", "Triangular"),
            new ListItem("WMA", "Weighted")
        }));
        avgType2.setSelectedIndex(1);
        
        ListItem[] indicators = 
        {
            new ListItem("None", "None"),
            new ListItem("AccDist", "Accumulation/Distribution"),
            new ListItem("AroonOsc", "Aroon Oscillator"),
            new ListItem("Aroon", "Aroon Up/Down"),
            new ListItem("ADX", "Avg Directional Index"),
            new ListItem("ATR", "Avg True Range"),
            new ListItem("BBW", "Bollinger Band Width"),
            new ListItem("CMF", "Chaikin Money Flow"),
            new ListItem("COscillator", "Chaikin Oscillator"),
            new ListItem("CVolatility", "Chaikin Volatility"),
            new ListItem("CLV", "Close Location Value"),
            new ListItem("CCI", "Commodity Channel Index"),
            new ListItem("DPO", "Detrended Price Osc"),
            new ListItem("DCW", "Donchian Channel Width"),
            new ListItem("EMV", "Ease of Movement"),
            new ListItem("FStoch", "Fast Stochastic"),
            new ListItem("MACD", "MACD"),
            new ListItem("MDX", "Mass Index"),
            new ListItem("Momentum", "Momentum"),
            new ListItem("MFI", "Money Flow Index"),
            new ListItem("NVI", "Neg Volume Index"),
            new ListItem("OBV", "On Balance Volume"),
            new ListItem("Performance", "Performance"),
            new ListItem("PPO", "% Price Oscillator"),
            new ListItem("PVO", "% Volume Oscillator"),
            new ListItem("PVI", "Pos Volume Index"),
            new ListItem("PVT", "Price Volume Trend"),
            new ListItem("ROC", "Rate of Change"),
            new ListItem("RSI", "RSI"),
            new ListItem("SStoch", "Slow Stochastic"),
            new ListItem("StochRSI", "StochRSI"),
            new ListItem("TRIX", "TRIX"),
            new ListItem("UO", "Ultimate Oscillator"),
            new ListItem("Vol", "Volume"),
            new ListItem("WilliamR", "William's %R")
        };

        indicator1.setModel(new DefaultComboBoxModel(indicators));
        indicator2.setModel(new DefaultComboBoxModel(indicators));
        indicator3.setModel(new DefaultComboBoxModel(indicators));
        indicator4.setModel(new DefaultComboBoxModel(indicators));

        for (int i = 0; i < indicators.length; ++i)
        {
            if (indicators[i].getKey() == "RSI")
                indicator1.setSelectedIndex(i);
            else if (indicators[i].getKey() == "MACD")
                indicator2.setSelectedIndex(i);
        }
        indicator3.setSelectedIndex(0);
        indicator4.setSelectedIndex(0);
        
        // Layout and display the JFrame
        pack();
        hasFinishedInitialization = true;
         
        // Update the chart
        drawChart(chartViewer1);
    }
    
    /// <summary>
    /// Get the timeStamps, highData, lowData, openData, closeData and volData.
    /// </summary>
    /// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    /// <param name="durationInDays">The number of trading days to get.</param>
    /// <param name="extraPoints">The extra leading data points needed in order to
    /// compute moving averages.</param>
    /// <returns>True if successfully obtain the data, otherwise false.</returns>
    protected boolean getData(String ticker, GregorianCalendar startDate,
	    GregorianCalendar endDate, int durationInDays, int extraPoints)
    {
        // This method should return false if the ticker symbol is invalid. In this
        // sample code, as we are using a random number generator for the data, all
        // ticker symbol is allowed, but we still assumed an empty symbol is invalid.
        if (ticker.equals("")) {
            return false;
        }

        // In this demo, we can get 15 min, daily, weekly or monthly data depending on
        // the time range.
        resolution = 86400;
        if (durationInDays <= 10) {
            // 10 days or less, we assume 15 minute data points are available
            resolution = 900;

            // We need to adjust the startDate backwards for the extraPoints. We assume
            // 6.5 hours trading time per day, and 5 trading days per week.
            double dataPointsPerDay = 6.5 * 3600 / resolution;
            GregorianCalendar adjustedStartDate = new GregorianCalendar(startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
            adjustedStartDate.add(Calendar.DAY_OF_MONTH,
                -(int)Math.ceil(extraPoints / dataPointsPerDay * 7 / 5) - 2);

            // Get the required 15 min data
            get15MinData(ticker, adjustedStartDate.getTime(), endDate.getTime());

        } else if (durationInDays >= 4.5 * 360) {
            // 4 years or more - use monthly data points.
            resolution = 30 * 86400;

            // Adjust startDate backwards to cater for extraPoints
            GregorianCalendar adjustedStartDate = (GregorianCalendar)startDate.clone();
            adjustedStartDate.add(Calendar.MONTH, -extraPoints);

            // Get the required monthly data
            getMonthlyData(ticker, adjustedStartDate.getTime(), endDate.getTime());

        } else if (durationInDays >= 1.5 * 360) {
            // 1 year or more - use weekly points.
            resolution = 7 * 86400;

            // Adjust startDate backwards to cater for extraPoints
            GregorianCalendar adjustedStartDate = (GregorianCalendar)startDate.clone();
                adjustedStartDate.add(Calendar.DAY_OF_MONTH, -extraPoints * 7 - 6);

            // Get the required weekly data
            getWeeklyData(ticker, adjustedStartDate.getTime(), endDate.getTime());

        } else {
            // Default - use daily points
            resolution = 86400;

            // Adjust startDate backwards to cater for extraPoints. We multiply the days
            // by 7/5 as we assume 1 week has 5 trading days.
            GregorianCalendar adjustedStartDate = new GregorianCalendar(startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
            adjustedStartDate.add(Calendar.DAY_OF_MONTH, - (extraPoints * 7 + 4) / 5 - 2);
		  
            // Get the required daily data
            getDailyData(ticker, adjustedStartDate.getTime(), endDate.getTime());
        }

        return true;
    }

    /// <summary>
    /// Get 15 minutes data series for timeStamps, highData, lowData, openData, closeData
    /// and volData.
    /// </summary>
	/// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    private void get15MinData(String ticker, Date startDate, Date endDate)
    {
        //
        // In this demo, we use a random number generator to generate the data. In practice,
        // you may get the data from a database or by other means. If you do not have 15 
        // minute data, you may modify the "drawChart" method below to not using 15 minute
        // data.
        //
        generateRandomData(ticker, startDate, endDate, 900);
    }

    /// <summary>
    /// Get daily data series for timeStamps, highData, lowData, openData, closeData
    /// and volData.
    /// </summary>
	/// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    private void getDailyData(String ticker, Date startDate, Date endDate)
    {
        //
        // In this demo, we use a random number generator to generate the data. In practice,
        // you may get the data from a database or by other means. A typical database code
        // example is like below. (This only shows a general idea. The exact details may differ
        // depending on your database brand and schema.)
        //
        //   //Connect to database using a suitable JDBC driver and the connect string
        //   Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        //   java.sql.Connection dbConn = java.sql.DriverManager.getConnection
        //        ("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=example.mdb");
        //
        //   //SQL statement to get the data. We use prepared statements in the example as it
        //   //offers a portable way to handle date/time formats.
        //   String sql = "Select recordDate, highData, lowData, openData, closeDate, volData " + 
        //       "From dailyFinanceTable Where ticker = '" + ticker + "' And recordDate >= ? " +
        //       " And recordDate <= ? Order By recordDate";
        //   java.sql.PreparedStatement stmt = dbConn.prepareStatement(sql);
        //   stmt.setDate(1, new java.sql.Date(startDate.getTime()));
        //   stmt.setDate(2, new java.sql.Date(endDate.getTime()));
        //
        //   //The most convenient way to read the SQL result into arrays is to use the 
        //   //ChartDirector DBTable utility.
        //   DBTable table = new DBTable(stmt.executeQuery());
        //   stmt.close();
        //   dbConn.close();
        //
        //   //Now get the data into arrays
        //   timeStamps = table.getColAsDateTime(0);
        //   highData = table.getCol(1);
        //   lowData = table.getCol(2);
        //   openData = table.getCol(3);
        //   closeData = table.getCol(4);
        //   volData = table.getCol(5);
        //
        
        generateRandomData(ticker, startDate, endDate, 86400);
    }

    /// <summary>
    /// Get weekly data series for timeStamps, highData, lowData, openData, closeData
    /// and volData.
    /// </summary>
	/// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    private void getWeeklyData(String ticker, Date startDate, Date endDate)
    {
        //
        // In this demo, we use a random number generator to generate the data. In practice,
        // you may get the data from a database or by other means. If you do not have weekly
        // data, you may call "getDailyData" to get daily data first, and then call 
        // "convertDailyToWeeklyData" to convert it to weekly data, like:
        //
        //      getDailyData(startDate, endDate);
        //      convertDailyToWeeklyData();
        //
        generateRandomData(ticker, startDate, endDate, 86400 * 7);
    }

    /// <summary>
    /// Get monthly data series for timeStamps, highData, lowData, openData, closeData
    /// and volData.
    /// </summary>
	/// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    private void getMonthlyData(String ticker, Date startDate, Date endDate)
    {
        //
        // In this demo, we use a random number generator to generate the data. In practice,
        // you may get the data from a database or by other means. If you do not have monthly
        // data, you may call "getDailyData" to get daily data first, and then call 
        // "convertDailyToMonthlyData" to convert it to monthly data, like:
        //
        //      getDailyData(startDate, endDate);
        //      convertDailyToMonthlyData();
        //
        generateRandomData(ticker, startDate, endDate, 86400 * 30);
    }

    /// <summary>
    /// A random number generator designed to generate realistic financial data.
    /// </summary>
	/// <param name="ticker">The ticker symbol for the data series.</param>
    /// <param name="startDate">The starting date/time for the data series.</param>
    /// <param name="endDate">The ending date/time for the data series.</param>
    /// <param name="resolution">The period of the data series.</param>
    private void generateRandomData(String ticker, Date startDate, Date endDate, int resolution)
    {
        FinanceSimulator db = new FinanceSimulator(ticker, startDate, endDate, resolution);
        timeStamps = db.getTimeStamps();
        highData = db.getHighData();
        lowData = db.getLowData();
        openData = db.getOpenData();
        closeData = db.getCloseData();
        volData = db.getVolData();
    }

    /// <summary>
    /// A utility to convert daily to weekly data.
    /// </summary>
    private void convertDailyToWeeklyData()
    {
        aggregateData(new ArrayMath(timeStamps).selectStartOfWeek());
    }
        
    /// <summary>
    /// A utility to convert daily to monthly data.
    /// </summary>
    private void convertDailyToMonthlyData()
    {
        aggregateData(new ArrayMath(timeStamps).selectStartOfMonth());
    }

    /// <summary>
    /// An internal method used to aggregate daily data.
    /// </summary>
    private void aggregateData(ArrayMath aggregator)
    {
        timeStamps = Chart.NTime(aggregator.aggregate(Chart.CTime(timeStamps), Chart.AggregateFirst));
        highData = aggregator.aggregate(highData, Chart.AggregateMax);
        lowData = aggregator.aggregate(lowData, Chart.AggregateMin);
        openData = aggregator.aggregate(openData, Chart.AggregateFirst);
        closeData = aggregator.aggregate(closeData, Chart.AggregateLast);
        volData = aggregator.aggregate(volData, Chart.AggregateSum);
    }    
    
    /// <summary>
    /// Handler for modifications to the text boxes
    /// </summary>
   private void textChanged(java.awt.event.FocusEvent evt) 
    {
    	//
    	// Redraw chart if the text box is changed
    	//
    	
       	JTextField src = (JTextField)evt.getSource();
        
		int period;
	    try { period = Integer.parseInt(src.getText()); }
	    catch (NumberFormatException e) { period = 0; }

        if (((src == tickerSymbol) && !tickerSymbol.getText().trim().equals(tickerKey)) ||
			((src == compareWith) && !compareWith.getText().trim().equals(compareKey)) ||
			((src == movAvg1) && (period != avgPeriod1)) ||
			((src == movAvg2) && (period != avgPeriod2)))
	        drawChart(chartViewer1);
    }

    /// <summary>
    /// Handler for the actionPerformed event for the controls
    /// </summary>
    private void handleSelection(java.awt.event.ActionEvent evt) 
    {
        if (hasFinishedInitialization)
            drawChart(chartViewer1);
    }
        
    /// <summary>
    /// Draw the chart according to user selection and display it in the WebChartViewer.
    /// </summary>
    /// <param name="viewer">The ChartViewer object to display the chart.</param>
    private void drawChart(ChartViewer viewer)
    {
        // In this demo, we just assume we plot up to the latest time. So endDate is now.
        GregorianCalendar endDate = new GregorianCalendar();

		// If the trading day has not yet started (before 9:30am), or if the end date is on
		// on Sat or Sun, we set the end date to 4:00pm of the last trading day		
		while ((endDate.get(Calendar.HOUR_OF_DAY) * 60 + endDate.get(Calendar.MINUTE) < 
			9 * 60 + 30) || (endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ||
			(endDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
		{
			endDate.add(Calendar.DAY_OF_MONTH, -1);
			endDate.set(Calendar.HOUR_OF_DAY, 16);
			endDate.set(Calendar.MINUTE, 0);
			endDate.set(Calendar.SECOND, 0);
		}
            
        // The duration selected by the user
        int durationInDays = Integer.parseInt(((ListItem)timeRange.getSelectedItem()).getKey());

        // Compute the start date by subtracting the duration from the end date.
        GregorianCalendar startDate;
        if (durationInDays >= 30)
        {
            // More or equal to 30 days - so we use months as the unit
            startDate = new GregorianCalendar(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), 1);
            startDate.add(Calendar.MONTH, -durationInDays / 30);
        }
        else
        {
            // Less than 30 days - use day as the unit. The starting point of the axis is
            // always at the start of the day (9:30am). Note that we use trading days, so
            // we skip Sat and Sun in counting the days.
            startDate = new GregorianCalendar(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH), 9, 30, 0);
            for (int i = 1; i < durationInDays; ++i)
                startDate.add(Calendar.DAY_OF_MONTH, (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) ? -3 : -1);
        }

        // The moving average periods selected by the user.
        int avgPeriod1;
        try { avgPeriod1 = Integer.parseInt(movAvg1.getText()); }
        catch (NumberFormatException e) { avgPeriod1 = 0;}
        int avgPeriod2;
        try { avgPeriod2 = Integer.parseInt(movAvg2.getText()); }
        catch (NumberFormatException e) { avgPeriod2 = 0;}

        avgPeriod1 = Math.max(0, Math.min(300, avgPeriod1));
        avgPeriod2 = Math.max(0, Math.min(300, avgPeriod2));
            
        // We need extra leading data points in order to compute moving averages.
        int extraPoints = Math.max(20, Math.max(avgPeriod1, avgPeriod2));

		// Get the data series to compare with, if any.
		compareKey = compareWith.getText().trim();
		compareData = null;
		if (getData(compareKey, startDate, endDate, durationInDays, extraPoints)) 
			compareData = closeData;

		// The data series we want to get.
		tickerKey = tickerSymbol.getText().trim();
		if (!getData(tickerKey, startDate, endDate, durationInDays, extraPoints)) 
		{
			errMsg(viewer, "Please enter a valid ticker symbol");
			return;
		}
        
        // We now confirm the actual number of extra points (data points that are before
        // the start date) as inferred using actual data from the database.
        extraPoints = timeStamps.length;
        Date cutOff = startDate.getTime();
        for (int i = 0; i < timeStamps.length; ++i)
        {
            if (!timeStamps[i].before(cutOff))
            {
                extraPoints = i;
                break;
            }
        }

        // Check if there is any valid data
        if (extraPoints >= timeStamps.length)
        {
			// No data - just display the no data message.
			errMsg(viewer, "No data available for the specified time period");
			return;
        }

        // In some finance chart presentation style, even if the data for the latest day 
        // is not fully available, the axis for the entire day will still be drawn, where
        // no data will appear near the end of the axis.
        if (resolution <= 86400)
        {
            // Add extra points to the axis until it reaches the end of the day. The end
            // of day is assumed to be 4:00pm (it depends on the stock exchange).
            GregorianCalendar lastTime = new GregorianCalendar();
            lastTime.setTime(timeStamps[timeStamps.length - 1]);
            int extraTrailingPoints = (int)((16 * 3600 - lastTime.get(Calendar.HOUR_OF_DAY) * 3600 
                - lastTime.get(Calendar.MINUTE) * 60 - lastTime.get(Calendar.SECOND)) / resolution);
            if (extraTrailingPoints > 0)
            {
                Date[] extendedTimeStamps = new Date[timeStamps.length + extraTrailingPoints];
                System.arraycopy(timeStamps, 0, extendedTimeStamps, 0, timeStamps.length);
                for (int i = 0; i < extraTrailingPoints; ++i)
                {
                    lastTime.add(Calendar.SECOND, resolution);
                    extendedTimeStamps[i + timeStamps.length] = (Date)lastTime.getTime().clone();
                }
                timeStamps = extendedTimeStamps;
            }               
        }

        //
        // At this stage, all data is available. We can draw the chart as according to 
        // user input.
        //

        //
        // Determine the chart size. In this demo, user can select 4 different chart sizes.
        // Default is the large chart size.
        //
        int width = 780;
        int mainHeight = 255;
        int indicatorHeight = 80;
        
        String selectedSize = ((ListItem)chartSize.getSelectedItem()).getKey();
        if (selectedSize == "S")
        {
            // Small chart size
            width = 450;
            mainHeight = 160;
            indicatorHeight = 60;
        }
        else if (selectedSize == "M")
        {
            // Medium chart size
            width = 620;
            mainHeight = 215;
            indicatorHeight = 70;
        }
        else if (selectedSize == "H")
        {
            // Huge chart size
            width = 1000;
            mainHeight = 320;
            indicatorHeight = 90;
        }

        // Create the chart object using the selected size
        FinanceChart m = new FinanceChart(width);

        // Set the data into the chart object
        m.setData(timeStamps, highData, lowData, openData, closeData, volData, extraPoints);

        //
        // We configure the title of the chart. In this demo chart design, we put the 
        // company name as the top line of the title with left alignment.
        //
        m.addPlotAreaTitle(Chart.TopLeft, tickerKey);

        // We displays the current date as well as the data resolution on the next line.
        String resolutionText = "";
        if (resolution == 30 * 86400)
            resolutionText = "Monthly";
        else if (resolution == 7 * 86400)
            resolutionText = "Weekly";
        else if (resolution == 86400)
            resolutionText = "Daily";
        else if (resolution == 900)
            resolutionText = "15-min";
       
        m.addPlotAreaTitle(Chart.BottomLeft, "<*font=arial.ttf,size=8*>" 
            + m.formatValue(new Date(), "mmm dd, yyyy") + " - " + resolutionText + " chart");

        // A copyright message at the bottom left corner the title area
        m.addPlotAreaTitle(Chart.BottomRight, "<*font=arial.ttf,size=8*>(c) Advanced Software Engineering");

		//
		// Add the first techical indicator according. In this demo, we draw the first
		// indicator on top of the main chart.
		//
		addIndicator(m, ((ListItem)indicator1.getSelectedItem()).getKey(), indicatorHeight);

		//
		// Add the main chart
		//
		m.addMainChart(mainHeight);

		//
		// Set log or linear scale according to user preference
		//
		m.setLogScale(logScale.isSelected());

		//
		// Set axis labels to show data values or percentage change to user preference
		//
		if (percentageScale.isSelected())
			m.setPercentageAxis();

        //
        // Draw the main chart depending on the chart type the user has selected
        //
        String selectedType = ((ListItem)chartType.getSelectedItem()).getKey();
        if (selectedType == "Close")
            m.addCloseLine(0x000040);
        else if (selectedType == "TP")
            m.addTypicalPrice(0x000040);
        else if (selectedType == "WC")
            m.addWeightedClose(0x000040);
        else if (selectedType == "Median")
            m.addMedianPrice(0x000040);

		//
		// Add comparison line if there is data for comparison
		//
		if (compareData != null) {
			if (compareData.length > extraPoints) {
				m.addComparison(compareData, 0x0000ff, compareKey);
			}
		}

        //
        // Add moving average lines.
        //
        addMovingAvg(m, ((ListItem)avgType1.getSelectedItem()).getKey(), avgPeriod1, 0x663300);
        addMovingAvg(m, ((ListItem)avgType2.getSelectedItem()).getKey(), avgPeriod2, 0x9900ff);

        //
        // Draw the main chart if the user has selected CandleStick or OHLC. We draw it
        // here to make sure it is drawn behind the moving average lines (that is, the
        // moving average lines stay on top.)
        //
        if (selectedType == "CandleStick")
            m.addCandleStick(0x33ff33, 0xff3333);
        else if (selectedType == "OHLC")
            m.addHLOC(0x8000, 0x800000);

		//
		// Add parabolic SAR if necessary
		//
		if (parabolicSAR.isSelected())
			m.addParabolicSAR(0.02, 0.02, 0.2, Chart.DiamondShape, 5, 0x008800, 0x000000);

        //
        // Add price band/channel/envelop to the chart according to user selection
        //
        String selectedBand = ((ListItem)priceBand.getSelectedItem()).getKey();
        if (selectedBand == "BB")
            m.addBollingerBand(20, 2, 0x9999ff, 0xc06666ff);
        else if (selectedBand == "DC")
            m.addDonchianChannel(20, 0x9999ff, 0xc06666ff);
        else if (selectedBand == "Envelop")
            m.addEnvelop(20, 0.1, 0x9999ff, 0xc06666ff);

        //
        // Add volume bars to the main chart if necessary
        //
        if (volumeBars.isSelected())
            m.addVolBars(indicatorHeight, 0x99ff99, 0xff9999, 0xc0c0c0);

        //
        // Add additional indicators as according to user selection.
        //
        addIndicator(m, ((ListItem)indicator2.getSelectedItem()).getKey(), indicatorHeight);
        addIndicator(m, ((ListItem)indicator3.getSelectedItem()).getKey(), indicatorHeight);
        addIndicator(m, ((ListItem)indicator4.getSelectedItem()).getKey(), indicatorHeight);

        // Output chart to ChartViewer
        viewer.setImage(m.makeImage());
        viewer.setImageMap(m.getHTMLImageMap("", "", "title='" + m.getToolTipDateFormat() + 
            " {value|P}'"));
    }

    /// <summary>
    /// Add a moving average line to the FinanceChart object.
    /// </summary>
    /// <param name="m">The FinanceChart object to add the line to.</param>
    /// <param name="avgType">The moving average type (SMA/EMA/TMA/WMA).</param>
    /// <param name="avgPeriod">The moving average period.</param>
    /// <param name="color">The color of the line.</param>
    protected LineLayer addMovingAvg(FinanceChart m, String avgType, int avgPeriod, int color)
    {
        if (avgPeriod > 1)
        {
            if (avgType == "SMA")
                return m.addSimpleMovingAvg(avgPeriod, color);
            else if (avgType == "EMA")
                return m.addExpMovingAvg(avgPeriod, color);
            else if (avgType == "TMA")
                return m.addTriMovingAvg(avgPeriod, color);
            else if (avgType == "WMA")
                return m.addWeightedMovingAvg(avgPeriod, color);
        }
        return null;
    }

    /// <summary>
    /// Add an indicator chart to the FinanceChart object. In this demo example, the indicator
    /// parameters (such as the period used to compute RSI, colors of the lines, etc.) are hard
    /// coded to commonly used values. You are welcome to design a more complex user interface 
    /// to allow users to set the parameters.
    /// </summary>
    /// <param name="m">The FinanceChart object to add the line to.</param>
    /// <param name="indicator">The selected indicator.</param>
    /// <param name="height">Height of the chart in pixels</param>
	/// <returns>The XYChart object representing indicator chart.</returns>
    protected XYChart addIndicator(FinanceChart m, String indicator, int height)
    {
        if (indicator == "RSI")
			return m.addRSI(height, 14, 0x800080, 20, 0xff6666, 0x6666ff);
        else if (indicator == "StochRSI")
			return m.addStochRSI(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
        else if (indicator == "MACD")
			return m.addMACD(height, 26, 12, 9, 0xff, 0xff00ff, 0x8000);
        else if (indicator == "FStoch")
			return m.addFastStochastic(height, 14, 3, 0x6060, 0x606000);
        else if (indicator == "SStoch")
			return m.addSlowStochastic(height, 14, 3, 0x6060, 0x606000);
        else if (indicator == "ATR")
			return m.addATR(height, 14, 0x808080, 0xff);
        else if (indicator == "ADX")
			return m.addADX(height, 14, 0x8000, 0x800000, 0x80);
        else if (indicator == "DCW")
			return m.addDonchianWidth(height, 20, 0xff);
        else if (indicator == "BBW")
			return m.addBollingerWidth(height, 20, 2, 0xff);
        else if (indicator == "DPO")
			return m.addDPO(height, 20, 0xff);
        else if (indicator == "PVT")
			return m.addPVT(height, 0xff);
        else if (indicator == "Momentum")
			return m.addMomentum(height, 12, 0xff);
        else if (indicator == "Performance")
			return m.addPerformance(height, 0xff);
        else if (indicator == "ROC")
			return m.addROC(height, 12, 0xff);
        else if (indicator == "OBV")
			return m.addOBV(height, 0xff);
        else if (indicator == "AccDist")
			return m.addAccDist(height, 0xff);
        else if (indicator == "CLV")
			return m.addCLV(height, 0xff);
        else if (indicator == "WilliamR")
			return m.addWilliamR(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
        else if (indicator == "Aroon")
			return m.addAroon(height, 14, 0x339933, 0x333399);
        else if (indicator == "AroonOsc")
			return m.addAroonOsc(height, 14, 0xff);
        else if (indicator == "CCI")
			return m.addCCI(height, 20, 0x800080, 100, 0xff6666, 0x6666ff);
        else if (indicator == "EMV")
			return m.addEaseOfMovement(height, 9, 0x6060, 0x606000);
        else if (indicator == "MDX")
			return m.addMassIndex(height, 0x800080, 0xff6666, 0x6666ff);
        else if (indicator == "CVolatility")
			return m.addChaikinVolatility(height, 10, 10, 0xff);
        else if (indicator == "COscillator")
			return m.addChaikinOscillator(height, 0xff);
        else if (indicator == "CMF")
			return m.addChaikinMoneyFlow(height, 21, 0x8000);
        else if (indicator == "NVI")
			return m.addNVI(height, 255, 0xff, 0x883333);
        else if (indicator == "PVI")
			return m.addPVI(height, 255, 0xff, 0x883333);
        else if (indicator == "MFI")
			return m.addMFI(height, 14, 0x800080, 30, 0xff6666, 0x6666ff);
        else if (indicator == "PVO")
			return m.addPVO(height, 26, 12, 9, 0xff, 0xff00ff, 0x8000);
        else if (indicator == "PPO")
			return m.addPPO(height, 26, 12, 9, 0xff, 0xff00ff, 0x8000);
        else if (indicator == "UO")
			return m.addUltimateOscillator(height, 7, 14, 28, 0x800080, 20, 0xff6666, 0x6666ff);
        else if (indicator == "Vol")
			return m.addVolIndicator(height, 0x99ff99, 0xff9999, 0xc0c0c0);
        else if (indicator == "TRIX")
			return m.addTRIX(height, 12, 0xff);
		return null;
    }
   
    /// <summary>
    /// Creates a dummy chart to show an error message.
    /// </summary>
    /// <param name="viewer">The WinChartViewer to display the error message.</param>
    /// <param name="msg">The error message</param>
    protected void errMsg(ChartViewer viewer, String msg)
    {
         MultiChart m = new MultiChart(400, 200);
         m.addTitle2(Chart.Center, msg, "Arial", 10).setMaxWidth(m.getWidth());
         viewer.setImage(m.makeImage());
    }
   
    //
    // Implementation of the DemoModule interface to allow this demo to run inside the 
    // ChartDirectorDemo browser
    //
    
    // Name of demo program
    public String toString() 
    { 
        return "Interactive Financial Chart"; 
    }

    // Number of charts produced in this demo
    public int getNoOfCharts() 
    { 
        // This demo open its own frame instead of using the right pane of the ChartDirectorDemo 
        // browser for display, so we just load the frame, then returns 0.
        initComponents(false); 
        setVisible(true); 
        return 0; 
    }

    // Main code for creating charts
    public void createChart(ChartViewer viewer, int index)
    {
        // Do nothing, as the ChartDirectorDemo browser right pane is not used.
    }
}
