package chartdirector.report.cwb.dhu.edu.cn;

import ChartDirector.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZoomScrollDemo extends JFrame implements DemoModule
{
	// Data arrays for the scrollable / zoomable chart.
	private Date[] timeStamps;
	private double[] dataSeriesA;
	private double[] dataSeriesB;
	private double[] dataSeriesC;
		
	// The earliest date and the duration in seconds for horizontal scrolling
	private Date minDate;
	private double dateRange;
		
	// The vertical range of the chart for vertical scrolling
	private double maxValue;
	private double minValue;
		
	// The current visible duration of the view port in seconds
	private double currentDuration = 360 * 86400;
	// In this demo, the maximum zoom-in is set to 10 days
	private double minDuration = 10 * 86400;
	
	// Will set to true at the end of initialization
	private boolean hasFinishedInitialization;
	
	//
	// Controls in the JFrame
	//
	private ChartViewer chartViewer1;
	private JComboBox duration;
	private JScrollBar hScrollBar1;
	private JButton pointerPB;
	private JComboBox startDay;
	private JComboBox startMonth;
	private JComboBox startYear;
	private JScrollBar vScrollBar1;
	private JButton xZoomPB;
	private JButton xyZoomPB;
	private JButton zoomInPB;
	private JButton zoomOutPB;

	/// <summary>
	/// The main method to allow this demo to run as a standalone program.
	/// </summary>
    public static void main(String args[]) 
	{
		ZoomScrollDemo d = new ZoomScrollDemo();
		d.initComponents(true);
		d.setVisible(true);
	}
	
	/// <summary>
	/// Create the JFrame and put controls in it. 
	/// </summary>
	private void initComponents(final boolean closeOnExit) 
	{
		// Do nothing if already initialized
		if (hasFinishedInitialization)
			return;
    	
		setTitle("ChartDirector Zooming and Scrolling Demonstration");
		setResizable(false);

		// End application upon closing main window
		if (closeOnExit)
		{
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) { System.exit(0); }});
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
		leftPanel.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());
		
		// Pointer push button
		pointerPB = new JButton("Pointer", loadImageIcon("pointer.gif"));
		pointerPB.setHorizontalAlignment(SwingConstants.LEFT);
		pointerPB.setMargin(new Insets(5, 5, 5, 5));
		pointerPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				pointerPB_Clicked();
			}});
		leftPanel.add(pointerPB).setBounds(1, 0, 148, 24);
        
        // Zoom In push button
		zoomInPB = new JButton("Zoom In", loadImageIcon("zoomin.gif"));
		zoomInPB.setHorizontalAlignment(SwingConstants.LEFT);
		zoomInPB.setMargin(new Insets(5, 5, 5, 5));
		zoomInPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				zoomInPB_Clicked();
			}});		
		leftPanel.add(zoomInPB).setBounds(1, 24, 148, 24);

		// Zoom out push button
		zoomOutPB = new JButton("Zoom Out", loadImageIcon("zoomout.gif"));
		zoomOutPB.setHorizontalAlignment(SwingConstants.LEFT);
		zoomOutPB.setMargin(new Insets(5, 5, 5, 5));
		zoomOutPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				zoomOutPB_Clicked();
			}});		
		leftPanel.add(zoomOutPB).setBounds(1, 48, 148, 24);

		// Zoom mode label
		leftPanel.add(new JLabel("Zoom Mode")).setBounds(5, 80, 130, 20);
	
		// X Zoom push button
		xZoomPB = new JButton("X Zoom / Y Auto", loadImageIcon("xrange.gif"));
		xZoomPB.setHorizontalAlignment(SwingConstants.LEFT);
		xZoomPB.setMargin(new Insets(5, 5, 5, 5));
		xZoomPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				xZoomPB_Clicked();
			}});		
		leftPanel.add(xZoomPB).setBounds(1, 100, 148, 25);

		// XY Zoom push button
		xyZoomPB = new JButton("XY Zoom", loadImageIcon("xyrange.gif"));
		xyZoomPB.setHorizontalAlignment(SwingConstants.LEFT);
		xyZoomPB.setMargin(new Insets(5, 5, 5, 5));
		xyZoomPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				xyZoomPB_Clicked();
			}});	
		leftPanel.add(xyZoomPB).setBounds(1, 124, 148, 24);
			
		// Start Date label
		leftPanel.add(new JLabel("Start Date")).setBounds(5, 160, 130, 20);
		
		// Start Year, Start Month, Start Day combo boxes
		startYear = new JComboBox();
		startMonth = new JComboBox(new Object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", 
			"10", "11", "12"} );
		startDay = new JComboBox(new Object[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", 
			"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
			"24", "25", "26", "27", "28", "29", "30", "31"} );
		leftPanel.add(startYear).setBounds(1, 180, 60, 20);
		leftPanel.add(startMonth).setBounds(61, 180, 44, 20);
		leftPanel.add(startDay).setBounds(105, 180, 44, 20);
		ActionListener startDateHandler = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startDate_ValueChanged(); 
			}};
		startYear.addActionListener(startDateHandler);
		startMonth.addActionListener(startDateHandler);
		startDay.addActionListener(startDateHandler);
		
		// Duration label
		leftPanel.add(new JLabel("Duration (Days)")).setBounds(5, 212, 130, 20);
		
		// Duration combo box
		duration = new JComboBox(new Object[] { "10", "20", "30", "60", "90", "180", "360", "720",
			"1080", "1440" });
		duration.setEditable(true);
		duration.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				duration_ValueChanged(); 
			}			
		});
		leftPanel.add(duration).setBounds(1, 232, 148, 20);
		
		// Total expected panel size
		leftPanel.setPreferredSize(new Dimension(150, 264));
		
		// Chart Viewer
		chartViewer1 = new ChartViewer();
		chartViewer1.setBackground(new java.awt.Color(255, 255, 255));
		chartViewer1.setOpaque(true);
		chartViewer1.setPreferredSize(new Dimension(616, 316));
		chartViewer1.setHorizontalAlignment(SwingConstants.CENTER);
		chartViewer1.setHotSpotCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chartViewer1.addViewPortListener(new ViewPortAdapter() {
			public void viewPortChanged(ViewPortChangedEvent e) {
				chartViewer1_ViewPortChanged(e);
			}
		});
		chartViewer1.addHotSpotListener(new HotSpotAdapter() {
			public void hotSpotClicked(HotSpotEvent e) {
				chartViewer1_HotSpotClicked(e);
		}});
		chartViewer1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				chartViewer1_MouseEntered();
			}
		});
		
		// Vertical Scroll Bar
		vScrollBar1 = new JScrollBar(JScrollBar.VERTICAL, 0, 100000000, 0, 1000000000);
		vScrollBar1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				vScrollBar1_ValueChanged();		 
			}
		});

		// Horizontal Scroll bar
		hScrollBar1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 100000000, 0, 1000000000);
		hScrollBar1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				hScrollBar1_ValueChanged();		 
			}
		});

		// We need to put the horizontal scroll bar inside a panel with a filler, because
		// the horizontal scroll bar does not exactly touch the right edge of the right panel
		JPanel filler = new JPanel();
		filler.setPreferredSize(new Dimension(vScrollBar1.getPreferredSize().width, 1));
		JPanel bottomScrollPanel = new JPanel(new BorderLayout());
		bottomScrollPanel.add(hScrollBar1, java.awt.BorderLayout.CENTER);
		bottomScrollPanel.add(filler, java.awt.BorderLayout.EAST);

		// Put the ChartViewer and the scroll bars in the right panel
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.add(chartViewer1, java.awt.BorderLayout.CENTER);
		rightPanel.add(vScrollBar1, java.awt.BorderLayout.EAST);
		rightPanel.add(bottomScrollPanel, java.awt.BorderLayout.SOUTH);
		
		// Put the leftPanel and rightPanel on the JFrame
		getContentPane().add(leftPanel, java.awt.BorderLayout.WEST);
		getContentPane().add(rightPanel, java.awt.BorderLayout.CENTER);
		
		// Set all UI fonts (except labels)
		Font uiFont = new Font("Dialog", Font.PLAIN, 11);
		for (int i = 0; i < leftPanel.getComponentCount(); ++i)
		{
			Component c = leftPanel.getComponent(i);
			if (!(c instanceof JLabel))
				c.setFont(uiFont);
		}

		// Load the data
		loadData();

		// In this demo, we obtain the horizontal scroll range from the actual data.
		minDate = timeStamps[0];
		dateRange = (timeStamps[timeStamps.length - 1].getTime() - minDate.getTime()) / 1000;

		// Set the date range of the startYear combo box to match the scrollable date range.
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(minDate);
		int minYear = calendar.get(Calendar.YEAR);
		calendar.add(Calendar.SECOND, (int)dateRange);
		int maxYear = calendar.get(Calendar.YEAR);

		startYear.removeAllItems();
		for (int i = minYear; i <= maxYear; ++i)
			startYear.addItem(new Integer(i));

		// Set ChartViewer to reflect the visible and minimum duration
		chartViewer1.setZoomInWidthLimit(minDuration / dateRange);
		chartViewer1.setViewPortWidth(currentDuration / dateRange);
		chartViewer1.setViewPortLeft(1 - chartViewer1.getViewPortWidth());
		
		// Initially choose the pointer mode (draw to scroll mode)
		pointerPB.doClick();
		xZoomPB.doClick();		

		// Can update the chart now
		pack();
		hasFinishedInitialization = true;
		chartViewer1.updateViewPort(true, true);
	}

	/// <summary>
	/// A utility to load an image icon from the Java class path
	/// </summary>
	private ImageIcon loadImageIcon(String path)
	{
		try { return new ImageIcon(getClass().getClassLoader().getResource(path)); }
		catch (Exception e) { return null; }
	}
	
	/// <summary>
	/// Load the data
	/// </summary>
	private void loadData()
	{
		// In this demo, we allow scrolling the chart for the last 5 years
		GregorianCalendar lastDate = new GregorianCalendar();
		lastDate.set(lastDate.get(Calendar.YEAR), lastDate.get(Calendar.MONTH), 
			lastDate.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		GregorianCalendar firstDate = (GregorianCalendar)lastDate.clone();
		firstDate.add(Calendar.YEAR, -5);

		// The initial view port is to show 1 year of data.
		GregorianCalendar viewPortStartDate = (GregorianCalendar)lastDate.clone();
		viewPortStartDate.add(Calendar.YEAR, -1);
		currentDuration = (lastDate.getTime().getTime() - viewPortStartDate.getTime().getTime()) 
			/ 1000;
		
		//
		// Get the data and stores them in a memory buffer for fast scrolling / zooming. In 
		// this demo, we just use a random number generator. In practice, you may get the data
		// from a database or XML or by other means. (See the ChartDirector documentation on 
		// "Using Data Sources with ChartDirector" if you need some sample code on how to read
		// data from database to array variables.)
		//

		// Set up random number generator
		int noOfDays = (int)((lastDate.getTime().getTime() - firstDate.getTime().getTime()) 
			/ 86400000) + 1;
		RanTable r = new RanTable(127, 4, noOfDays);
		r.setDateCol(0, firstDate.getTime(), 86400);
		r.setCol(1, 150, -10, 10);
		r.setCol(2, 200, -10, 10);
		r.setCol(3, 250, -10, 10);
			
		// Read random data into the data arrays
		timeStamps = Chart.NTime(r.getCol(0));
		dataSeriesA = r.getCol(1);
		dataSeriesB = r.getCol(2);
		dataSeriesC = r.getCol(3);
	}
	
	/// <summary>
	/// Click event for the pointerPB.
	/// </summary>
	private void pointerPB_Clicked()
	{
		pointerPB.setBackground(new Color(0x80, 0xff, 0x80));
		zoomInPB.setBackground(null);
		zoomOutPB.setBackground(null);
		chartViewer1.setMouseUsage(Chart.MouseUsageScrollOnDrag);
	}

	/// <summary>
	/// Click event for the zoomInPB.
	/// </summary>
	private void zoomInPB_Clicked()
	{
		pointerPB.setBackground(null);
		zoomInPB.setBackground(new Color(0x80, 0xff, 0x80));
		zoomOutPB.setBackground(null);
		chartViewer1.setMouseUsage(Chart.MouseUsageZoomIn);
	}

	/// <summary>
	/// Click event for the zoomOutPB.
	/// </summary>
	private void zoomOutPB_Clicked()
	{
		pointerPB.setBackground(null);
		zoomInPB.setBackground(null);
		zoomOutPB.setBackground(new Color(0x80, 0xff, 0x80));
		chartViewer1.setMouseUsage(Chart.MouseUsageZoomOut);
	}
	
	/// <summary>
	/// Click event for the xZoomPB. Sets X-Zoom / Y-Axis auto-scaled mode.
	/// </summary>
	private void xZoomPB_Clicked()
	{
		xZoomPB.setBackground(new Color(0x80, 0xff, 0x80));
		xyZoomPB.setBackground(null);

		// Horizontal zooming and scrolling only
		chartViewer1.setZoomDirection(Chart.DirectionHorizontal);
		chartViewer1.setScrollDirection(Chart.DirectionHorizontal);
				
		// Viewport is always unzoomed as y-axis is auto-scaled
		chartViewer1.setViewPortTop(0);
		chartViewer1.setViewPortHeight(1);
				
		// Update chart to auto-scale axis
		if (hasFinishedInitialization)
			chartViewer1.updateViewPort(true, false);
	}

	/// <summary>
	/// Click event for the xZoomPB. Set XY-Zoom mode.
	/// </summary>
	private void xyZoomPB_Clicked()
	{
		xZoomPB.setBackground(null);
		xyZoomPB.setBackground(new Color(0x80, 0xff, 0x80));
		
		// Horizontal and Vertical zooming and scrolling
		chartViewer1.setZoomDirection(Chart.DirectionHorizontalVertical);
		chartViewer1.setScrollDirection(Chart.DirectionHorizontalVertical);		
	}
		
	/// <summary>
	/// Event handler for startYear, startMonth and startDay.
	/// </summary>
	private void startDate_ValueChanged()
	{
		if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent())
		{
			// Get the date represented by the combo boxes
			GregorianCalendar startYMD = new GregorianCalendar(
				Integer.parseInt(startYear.getSelectedItem().toString()), 
				startMonth.getSelectedIndex(), startDay.getSelectedIndex() + 1);
			Date startDate = startYMD.getTime();
			
			// Set the view port to reflect the selected date
			chartViewer1.setViewPortLeft((startDate.getTime() - minDate.getTime()) / dateRange / 1000);
			chartViewer1.updateViewPort(true, false);
		}
	}
	
	/// <summary>
	/// Sets the startYear, startMonth and startDay to the given date.
	/// </summary>
	private void setStartDate(Date d)
	{
		GregorianCalendar startYMD = new GregorianCalendar();
		startYMD.setTime(d);
		
		startYear.setSelectedItem(new Integer(startYMD.get(Calendar.YEAR)));
		startMonth.setSelectedIndex(startYMD.get(Calendar.MONTH));
		normalizeDate();
		startDay.setSelectedIndex(startYMD.get(Calendar.DAY_OF_MONTH) - 1);
	}
	
	/// <summary>
	/// Normalize the startDay combo box to ensure it only shows the valid days for the month.
	/// </summary>
	private void normalizeDate()
	{
		final int[] daysTable = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int daysInMonth = daysTable[Integer.parseInt(startMonth.getSelectedItem().toString()) - 1];
		if ((daysInMonth == 28) && (Integer.parseInt(startYear.getSelectedItem().toString()) % 4 == 0))
			++daysInMonth; 
		
		int currentDay = Integer.parseInt(startDay.getSelectedItem().toString());
		while (daysInMonth < startDay.getItemCount())
			startDay.removeItemAt(startDay.getItemCount() - 1);
		while (daysInMonth > startDay.getItemCount())
			startDay.addItem(new Integer(startDay.getItemCount() + 1));
		if (currentDay > daysInMonth)
			startDay.setSelectedIndex(daysInMonth - 1);
	}
	
	/// <summary>
	/// Event handler for duration.
	/// </summary>
	private void duration_ValueChanged()
	{
		if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent())
		{
			try 
			{ 
				//get the duration chosen or entered by the user
				double enteredDuration = Double.parseDouble(duration.getSelectedItem().toString()) 
					* 86400;
				if (enteredDuration <= minDuration)
					enteredDuration = minDuration;
					
				// Check if duration has really changed - sometimes the combo box may issue 
				// redundant value changed events when value has not actually changed.
				double newViewPortWidth = enteredDuration / dateRange;
				if (Math.abs(chartViewer1.getViewPortWidth() - newViewPortWidth) > 
					0.00001 * chartViewer1.getViewPortWidth())
				{
					// Set the view port based on the duration
					chartViewer1.setViewPortWidth(newViewPortWidth);
					chartViewer1.updateViewPort(true, false);
				}				
			}
			catch (Exception e)
			{
				// If user entered invalid text as duration, we just reset the duration
				duration.setSelectedItem("" + 
					Math.round(chartViewer1.getViewPortWidth() * dateRange / 86400));
			}
		}		
	}

	/// <summary>
	/// Horizontal ScrollBar ValueChanged event handler
	/// </summary>
	private void hScrollBar1_ValueChanged()
	{
		if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent())
		{
			// Get the view port left as according to the scroll bar
			double newViewPortLeft = ((double)(hScrollBar1.getValue() - hScrollBar1.getMinimum())) 
				/ (hScrollBar1.getMaximum() - hScrollBar1.getMinimum());

			// Check if view port has really changed - sometimes the scroll bar may issue redundant
			// value changed events when value has not actually changed.
			if (Math.abs(chartViewer1.getViewPortLeft() - newViewPortLeft) > 
				0.00001 * chartViewer1.getViewPortWidth())
			{
				// Set the view port based on the scroll bar
				chartViewer1.setViewPortLeft(newViewPortLeft);
	
				// Update the chart display without updating the image maps. We delay updating
				// the image map because the chart may still be unstable (still scrolling).
				chartViewer1.updateViewPort(true, false);
			}
		}
	}

	/// <summary>
	/// Vertical ScrollBar ValueChanged event handler
	/// </summary>
	private void vScrollBar1_ValueChanged()
	{
		if (hasFinishedInitialization && !chartViewer1.isInViewPortChangedEvent())
		{
			// Get the view port top as according to the scroll bar
			double scrollBarTop = ((double)(vScrollBar1.getValue() - vScrollBar1.getMinimum())) 
				/ (vScrollBar1.getMaximum() - vScrollBar1.getMinimum());
			
			// Check if view port has really changed - sometimes the scroll bar may issue redundant
			// value changed events when value has not actually changed.
			if (Math.abs(chartViewer1.getViewPortTop() - scrollBarTop) >  
				0.00001 * chartViewer1.getViewPortHeight())
			{
				// Set the view port based on the scroll bar
				chartViewer1.setViewPortTop(scrollBarTop);
	
				// Update the chart display without updating the image maps. We delay updating
				// the image map because the chart may still be unstable (still scrolling).
				chartViewer1.updateViewPort(true, false);
			}
		}		
	}
	
	/// <summary>
	/// The MouseEntered handler for the ChartViewer. In this demo, we delay creating the image 
	/// map until the mouse actually enters the chart. This avoids creating unnecessary image
	/// maps while the chart is still scrolling.
	/// </summary>
	private void chartViewer1_MouseEntered()
	{
		updateImageMap(chartViewer1);
	}

	/// <summary>
	///	Handler when a hot spot on a chart is clicked. In this demo, we just list out the hot spot
	/// information in a pop up dialog.
	/// </summary>
	private void chartViewer1_HotSpotClicked(HotSpotEvent e)
	{
		// We show the pop up dialog only when the mouse action is not in zoom-in or zoom-out mode.
		if ((chartViewer1.getMouseUsage() != Chart.MouseUsageZoomIn) && 
			(chartViewer1.getMouseUsage() != Chart.MouseUsageZoomOut))
			showHotSpotParam(e);
	}
	
	/// <summary>
	///	Utility to list out all hot spot parameters on a pop-up dialog
	/// </summary>
	private void showHotSpotParam(HotSpotEvent e)
	{
		//Get all parameters and sort them by key
		Hashtable parameters = e.getAttrValues();
		Object[] attributes = parameters.keySet().toArray();
		Arrays.sort(attributes);
	
		//Create a JTable to show the hot spot attribute/value pairs
		Object[][] rows = new Object[parameters.size()][2];
		for (int i = 0; i < attributes.length; ++i)
		{
			rows[i][0] = attributes[i];
			rows[i][1] = parameters.get(attributes[i]);
		}
		JTable table = new JTable(rows, new Object[] {"Parameter", "Value"});

		//Show the table in a dialog
		JDialog d = new JDialog(this, "Hot Spot Parameters", true);
		d.setSize(300, 300);
		Container container = d.getContentPane();

		//Just add some descriptive text to the dialg
		JTextArea t = new JTextArea ("This dialog is for demonstration only." +
			" In this demo, we simply list out all hot spot parameters.");
		t.setLineWrap(true);
		t.setWrapStyleWord(true);
		t.setEditable(false);
		t.setOpaque(false);
		t.setMargin(new Insets(5, 5, 5, 5));
		container.add(t, BorderLayout.NORTH);
	
		//Create the scroll pane on the dialog and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		container.add(scrollPane);

		//Show the dialog on where the mouse click occur	
		Point topLeft = ((ChartViewer)e.getSource()).getLocationOnScreen();
		d.setLocation(topLeft.x + e.getX(), topLeft.y + e.getY());
		d.setVisible(true);
	}
	
	/// <summary>
	/// The ViewPortChanged event handler. This event occurs when the user changes the ChartViewer
	/// view port by dragging scrolling, or by zoom in/out, or the ChartViewer.updateViewPort method
	/// is being called.
	/// </summary>
	private void chartViewer1_ViewPortChanged(ViewPortChangedEvent e)
	{
		// Compute the view port start date and duration
		Date currentStartDate = new Date(minDate.getTime() + Math.round(chartViewer1.getViewPortLeft() * 
			dateRange) * 1000);
		currentDuration = Math.round(chartViewer1.getViewPortWidth() * dateRange);

		// Synchronize the startDate and duration controls
		setStartDate(currentStartDate);
		duration.setSelectedItem("" + Math.round(currentDuration / 86400));

		// Synchronize the horizontal scroll bar with the ChartViewer
		hScrollBar1.setEnabled(chartViewer1.getViewPortWidth() < 1);
		hScrollBar1.setVisibleAmount((int)Math.ceil(chartViewer1.getViewPortWidth() * 
			(hScrollBar1.getMaximum() - hScrollBar1.getMinimum())));
		hScrollBar1.setBlockIncrement(hScrollBar1.getVisibleAmount());
		hScrollBar1.setUnitIncrement((int)Math.ceil(hScrollBar1.getVisibleAmount() * 0.1));
		hScrollBar1.setValue((int)Math.round(chartViewer1.getViewPortLeft() * 
			(hScrollBar1.getMaximum() - hScrollBar1.getMinimum())) + hScrollBar1.getMinimum());
		
		// Synchronize the vertical scroll bar with the ChartViewer
		vScrollBar1.setEnabled(chartViewer1.getViewPortHeight() < 1);
		vScrollBar1.setVisibleAmount((int)Math.ceil(chartViewer1.getViewPortHeight() * 
			(vScrollBar1.getMaximum() - vScrollBar1.getMinimum())));
		vScrollBar1.setBlockIncrement(vScrollBar1.getVisibleAmount());
		vScrollBar1.setUnitIncrement((int)Math.ceil(vScrollBar1.getVisibleAmount() * 0.1));
		vScrollBar1.setValue((int)Math.round(chartViewer1.getViewPortTop() * 
			(vScrollBar1.getMaximum() - vScrollBar1.getMinimum())) + vScrollBar1.getMinimum());
		
		// Update chart and image map if necessary
 		if (e.needUpdateChart())
			drawChart(chartViewer1);
		if (e.needUpdateImageMap())
			updateImageMap(chartViewer1);	
	}

	/// <summary>
	/// Draw the chart.
	/// </summary>
	private void drawChart(ChartViewer viewer)
	{ 
		//
		// In this demo, we copy the visible part of the data to a separate buffer for chart
		// plotting. 
		//
		// Note that if you only have a small amount of data (a few hundred data points), it
		// may be easier to just plot all data in any case (so the following copying code is 
		// not needed), and let ChartDirector "clip" the chart to the plot area. 
		//

		// Using ViewPortLeft and ViewPortWidth, get the start and end dates of the view port.
		Date viewPortStartDate = new Date(minDate.getTime() + Math.round(viewer.getViewPortLeft() *
			dateRange) * 1000);
		Date viewPortEndDate = new Date(viewPortStartDate.getTime() + 
			Math.round(viewer.getViewPortWidth() * dateRange) * 1000);
				
		// Get the starting index of the array using the start date
		int startIndex = Arrays.binarySearch(timeStamps, viewPortStartDate);
		if (startIndex < 0) 
			startIndex = (~startIndex) - 1;
			
		// Get the ending index of the array using the end date
		int endIndex = Arrays.binarySearch(timeStamps, viewPortEndDate);
		if (endIndex < 0) 
			endIndex = ((~endIndex) < timeStamps.length) ? ~endIndex : timeStamps.length - 1;

		// Get the length
		int noOfPoints = endIndex - startIndex + 1;

		// Now, we can just copy the visible data we need into the view port data series
		Date[] viewPortTimeStamps = new Date[noOfPoints];
		double[] viewPortDataSeriesA = new double[noOfPoints];
		double[] viewPortDataSeriesB = new double[noOfPoints];
		double[] viewPortDataSeriesC = new double[noOfPoints];
		System.arraycopy(timeStamps, startIndex, viewPortTimeStamps, 0, noOfPoints);
		System.arraycopy(dataSeriesA, startIndex, viewPortDataSeriesA, 0, noOfPoints);
		System.arraycopy(dataSeriesB, startIndex, viewPortDataSeriesB, 0, noOfPoints);
		System.arraycopy(dataSeriesC, startIndex, viewPortDataSeriesC, 0, noOfPoints);

		if (viewPortTimeStamps.length >= 520)
		{
			//
			// Zoomable chart with high zooming ratios often need to plot many thousands of 
			// points when fully zoomed out. However, it is usually not needed to plot more
			// data points than the resolution of the chart. Plotting too many points may cause
			// the points and the lines to overlap. So rather than increasing resolution, this 
			// reduces the clarity of the chart. So it is better to aggregate the data first if
			// there are too many points.
			//
			// In our current example, the chart only has 520 pixels in width and is using a 2
			// pixel line width. So if there are more than 520 data points, we aggregate the 
			// data using the ChartDirector aggregation utility method.
			//
			// If in your real application, you do not have too many data points, you may 
			// remove the following code altogether.
			//

			// Set up an aggregator to aggregate the data based on regular sized slots
			ArrayMath m = new ArrayMath(viewPortTimeStamps);
			m.selectRegularSpacing(viewPortTimeStamps.length / 260);
				
			// For the timestamps, take the first timestamp on each slot
			viewPortTimeStamps = m.aggregate(viewPortTimeStamps, Chart.AggregateFirst);

			// For the data values, aggregate by taking the averages
			viewPortDataSeriesA = m.aggregate(viewPortDataSeriesA, Chart.AggregateAvg);
			viewPortDataSeriesB = m.aggregate(viewPortDataSeriesB, Chart.AggregateAvg);
			viewPortDataSeriesC = m.aggregate(viewPortDataSeriesC, Chart.AggregateAvg);
		}

		//
		// Now we have obtained the data, we can plot the chart. 
		//

		///////////////////////////////////////////////////////////////////////////////////////
		// Step 1 - Configure overall chart appearance. 
		///////////////////////////////////////////////////////////////////////////////////////

		// Create an XYChart object 600 x 300 pixels in size, with pale blue (0xf0f0ff) 
		// background, black (000000) border, 1 pixel raised effect, and with a rounded frame.
		XYChart c = new XYChart(600, 300, 0xf0f0ff, 0, 1);
		c.setRoundedFrame();
		
		// Re-cycle the resources of the existing chart, if any. This can improve performance 
		// by reducing the frequency of garbage collections. 		
		c.recycle(chartViewer1.getChart());
									
		// Set the plotarea at (52, 60) and of size 520 x 192 pixels. Use white (ffffff) 
		// background. Enable both horizontal and vertical grids by setting their colors to 
		// grey (cccccc). Set clipping mode to clip the data lines to the plot area.
		c.setPlotArea(52, 60, 520, 192, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
		c.setClipping();

		// Add a top title to the chart using 15 pts Times New Roman Bold Italic font, with a 
		// light blue (ccccff) background, black (000000) border, and a glass like raised effect.
		c.addTitle("Zooming and Scrolling Demonstration", "Times New Roman Bold Italic", 15
			).setBackground(0xccccff, 0x0, Chart.glassEffect());

		// Add a bottom title to the chart to show the date range of the axis, with a light blue 
		// (ccccff) background.
		c.addTitle2(Chart.Bottom, "From <*font=Arial Bold Italic*>"
			+ c.formatValue(viewPortStartDate, "{value|mmm dd, yyyy}") 
			+ "<*/font*> to <*font=Arial Bold Italic*>"
			+ c.formatValue(viewPortEndDate, "{value|mmm dd, yyyy}") 
			+ "<*/font*> (Duration <*font=Arial Bold Italic*>" 
			+ Math.round((viewPortEndDate.getTime() - viewPortStartDate.getTime()) / 86400000.0)
			+ "<*/font*> days)", "Arial Italic", 10).setBackground(0xccccff);

		// Add a legend box at the top of the plot area with 9pts Arial Bold font with flow layout. 
		c.addLegend(50, 33, false, "Arial Bold", 9).setBackground(Chart.Transparent, 
			Chart.Transparent);

		// Set axes width to 2 pixels
		c.yAxis().setWidth(2);
		c.xAxis().setWidth(2);

		// Add a title to the y-axis
		c.yAxis().setTitle("Price (USD)", "Arial Bold", 9);

		///////////////////////////////////////////////////////////////////////////////////////
		// Step 2 - Add data to chart
		///////////////////////////////////////////////////////////////////////////////////////
		
		// 
		// In this example, we represent the data by lines. You may modify the code below if 
		// you want to use other representations (areas, scatter plot, etc).
		//

		// Add a line layer for the lines, using a line width of 2 pixels
		Layer layer = c.addLineLayer2();
		layer.setLineWidth(2);

		// Now we add the 3 data series to a line layer, using the color red (ff0000), green
		// (00cc00) and blue (0000ff)
		layer.setXData(viewPortTimeStamps);
		layer.addDataSet(viewPortDataSeriesA, 0xff0000, "Product Alpha");
		layer.addDataSet(viewPortDataSeriesB, 0x00cc00, "Product Beta");
		layer.addDataSet(viewPortDataSeriesC, 0x0000ff, "Product Gamma");

		///////////////////////////////////////////////////////////////////////////////////////
		// Step 3 - Set up x-axis scale
		///////////////////////////////////////////////////////////////////////////////////////
			
		// Set x-axis date scale to the view port date range. 
		c.xAxis().setDateScale(viewPortStartDate, viewPortEndDate);

		//
		// In the current demo, the x-axis range can be from a few years to a few days. We can 
		// let ChartDirector auto-determine the date/time format. However, for more beautiful 
		// formatting, we set up several label formats to be applied at different conditions. 
		//

		// If all ticks are yearly aligned, then we use "yyyy" as the label format.
		c.xAxis().setFormatCondition("align", 360 * 86400);
		c.xAxis().setLabelFormat("{value|yyyy}");
			
		// If all ticks are monthly aligned, then we use "mmm yyyy" in bold font as the first 
		// label of a year, and "mmm" for other labels.
		c.xAxis().setFormatCondition("align", 30 * 86400);
		c.xAxis().setMultiFormat(Chart.StartOfYearFilter(), "<*font=bold*>{value|mmm yyyy}", 
			Chart.AllPassFilter(), "{value|mmm}");
			
		// If all ticks are daily algined, then we use "mmm dd<*br*>yyyy" in bold font as the 
		// first label of a year, and "mmm dd" in bold font as the first label of a month, and
		// "dd" for other labels.
		c.xAxis().setFormatCondition("align", 86400);
		c.xAxis().setMultiFormat(
			Chart.StartOfYearFilter(), "<*block,halign=left*><*font=bold*>{value|mmm dd<*br*>yyyy}", 
			Chart.StartOfMonthFilter(), "<*font=bold*>{value|mmm dd}");
		c.xAxis().setMultiFormat2(Chart.AllPassFilter(), "{value|dd}");

		// For all other cases (sub-daily ticks), use "hh:nn<*br*>mmm dd" for the first label of
		// a day, and "hh:nn" for other labels.
		c.xAxis().setFormatCondition("else");
		c.xAxis().setMultiFormat(Chart.StartOfDayFilter(), "<*font=bold*>{value|hh:nn<*br*>mmm dd}", 
			Chart.AllPassFilter(), "{value|hh:nn}");
			
		///////////////////////////////////////////////////////////////////////////////////////
		// Step 4 - Set up y-axis scale
		///////////////////////////////////////////////////////////////////////////////////////
			
		if ((viewer.getZoomDirection() == Chart.DirectionHorizontal) || (minValue == maxValue))
		{
			// y-axis is auto-scaled - save the chosen y-axis scaled to support xy-zoom mode
			c.layout();
			minValue = c.yAxis().getMinValue();
			maxValue = c.yAxis().getMaxValue();
		}
		else
		{
			// xy-zoom mode - compute the actual axis scale in the view port 
			double axisLowerLimit =  maxValue - (maxValue - minValue) * 
				(viewer.getViewPortTop() + viewer.getViewPortHeight());
			double axisUpperLimit =  maxValue - (maxValue - minValue) * viewer.getViewPortTop();
			// *** use the following formula if you are using a log scale axis ***
			// double axisLowerLimit = maxValue * Math.pow(minValue / maxValue, 
			//    viewer.getViewPortTop() + viewer.getViewPortHeight());
			// double axisUpperLimit = maxValue * Math.pow(minValue / maxValue, 
			//    viewer.getViewPortTop());

			// use the zoomed-in scale
			c.yAxis().setLinearScale(axisLowerLimit, axisUpperLimit);
			c.yAxis().setRounding(false, false);
		}

		///////////////////////////////////////////////////////////////////////////////////////
		// Step 5 - Display the chart
		///////////////////////////////////////////////////////////////////////////////////////
		
		chartViewer1.setChart(c);
	}
	
	/// <summary>
	/// Update the image map used on the chart.
	/// </summary>
	private void updateImageMap(ChartViewer viewer)
	{
		// Include tool tip for the chart
		if (viewer.getImageMap() == null)
		{
			viewer.setImageMap(viewer.getChart().getHTMLImageMap("clickable", "",
				"title='[{dataSetName}] {x|mmm dd, yyyy}: USD {value|2}'"));
		}
	}
	
    //
    // Implementation of the DemoModule interface to allow this demo to run inside the 
    // ChartDirectorDemo browser
    //
    
	// Name of demo program
	public String toString() 
	{ 
		return "Zoom/Scroll Demo"; 
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
		// do nothing, as the ChartDirectorDemo browser right pane is not used
	}
}
