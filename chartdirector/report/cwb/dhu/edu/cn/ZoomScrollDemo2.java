package chartdirector.report.cwb.dhu.edu.cn;

import ChartDirector.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ZoomScrollDemo2 extends JFrame implements DemoModule
{
	// XY data points for the chart
	private double[] dataX0;
	private double[] dataY0;
	private double[] dataX1;
	private double[] dataY1;
	private double[] dataX2;
	private double[] dataY2;
		
	// The full x-axis and y-axis scales at no zooming. 
	private double minX = 0;
	private double maxX = 0;
	private double minY = 0;
	private double maxY = 0;

	// Internal variables to keep track of the currently pressed navigation button.
	private JButton activeNavigateButton = null;

	// Internal variables to keep track of mouse positions during dragging of navigateWindow. 
	private boolean isDraggingNavigatePad;
	private int mouseDownXCoor;
	private int mouseDownYCoor;
			
	// Will set to true at the end of initialization
	private boolean hasFinishedInitialization;
	
	//
	// Controls in the JFrame
	//
	private ChartViewer chartViewer1;
	private JButton centerPB;
	private JButton downPB;
	private JButton downRightPB;
	private JButton downLeftPB;
	private JButton leftPB;
	private JLabel navigatePad;
	private JPanel navigateWindow;
	private JButton pointerPB;
	private JButton rightPB;
	private javax.swing.Timer buttonRepeatTimer;
	private JButton upRightPB;
	private JButton upLeftPB;
	private JButton upPB;
	private JSlider zoomBar;
	private JButton zoomInPB;
	private JButton zoomOutPB;

	/// <summary>
	/// The main method to allow this demo to run as a standalone program.
	/// </summary>
 	public static void main(String args[]) 
	{
		ZoomScrollDemo2 d = new ZoomScrollDemo2();
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
    	
		setTitle("ChartDirector Zooming and Scrolling Demonstration (2)");

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
		leftPanel.setBorder(javax.swing.BorderFactory.createRaisedBevelBorder());

		// Pointer push button
		pointerPB = new JButton("Pointer", loadImageIcon("pointer.gif"));
		pointerPB.setHorizontalAlignment(SwingConstants.LEFT);
		pointerPB.setMargin(new Insets(5, 5, 5, 5));
		pointerPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				pointerPB_Clicked();
			}});
		leftPanel.add(pointerPB).setBounds(1, 0, 118, 24);
        
		// Zoom In push button
		zoomInPB = new JButton("Zoom In", loadImageIcon("zoomin.gif"));
		zoomInPB.setHorizontalAlignment(SwingConstants.LEFT);
		zoomInPB.setMargin(new Insets(5, 5, 5, 5));
		zoomInPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				zoomInPB_Clicked();
			}});		
		leftPanel.add(zoomInPB).setBounds(1, 24, 118, 24);

		// Zoom out push button
		zoomOutPB = new JButton("Zoom Out", loadImageIcon("zoomout.gif"));
		zoomOutPB.setHorizontalAlignment(SwingConstants.LEFT);
		zoomOutPB.setMargin(new Insets(5, 5, 5, 5));
		zoomOutPB.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt)	{
				zoomOutPB_Clicked();
			}});		
		leftPanel.add(zoomOutPB).setBounds(1, 48, 118, 24);

		// Navigation buttons
		JPanel buttons = new JPanel(new GridLayout(3, 3));
		buttons.add(upLeftPB = new JButton(loadImageIcon("upleftpb.gif")));
		buttons.add(upPB = new JButton(loadImageIcon("uppb.gif")));
		buttons.add(upRightPB = new JButton(loadImageIcon("uprightpb.gif")));
		buttons.add(leftPB = new JButton(loadImageIcon("leftpb.gif")));
		buttons.add(centerPB = new JButton(loadImageIcon("dot.gif")));
		buttons.add(rightPB = new JButton(loadImageIcon("rightpb.gif")));
		buttons.add(downLeftPB = new JButton(loadImageIcon("downleftpb.gif")));
		buttons.add(downPB = new JButton(loadImageIcon("downpb.gif")));
		buttons.add(downRightPB = new JButton(loadImageIcon("downrightpb.gif")));
		MouseListener buttonsHandler = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				navigateButton_MouseDown(e);
			}
			public void mouseReleased(MouseEvent e) {
				navigateButton_MouseUp(e);
			}
		};
		for (int i = 0; i < buttons.getComponentCount(); ++i)
		{
			if (i != 4)
				buttons.getComponent(i).addMouseListener(buttonsHandler);			
		}
		centerPB.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				centerPB_MouseDown(e);	
			}
		});
		buttonRepeatTimer = new javax.swing.Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonRepeatTimer_Tick();	
			}
		});
		leftPanel.add(buttons).setBounds(5, 100, 110, 110);

		// Zoom level label
		JLabel zoomLabel = new JLabel("Zoom Level");
		zoomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leftPanel.add(zoomLabel).setBounds(5, 230, 110, 20);
		
		// Zoom level bar
		zoomBar = new JSlider(1, 100, 50);
		zoomBar.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				zoomBar_ValueChanged(e);
			}
		});
		leftPanel.add(zoomBar).setBounds(5, 250, 110, 20);		

		// Navigation Pad
		navigatePad = new JLabel();
		navigatePad.setBackground(new Color(0xe0, 0xe0, 0xe0));
		navigatePad.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
		navigateWindow = new JPanel();
		navigateWindow.setBackground(new Color(0xc0, 0xc0, 0xff));		
		navigateWindow.setBorder(javax.swing.BorderFactory.createLineBorder(Color.black));
		navigateWindow.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				navigateWindow_MouseDown(e);
			}
			public void mouseReleased(MouseEvent e) {
				navigateWindow_MouseUp(e);
			}
		});
		navigateWindow.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e)  {
				navigateWindow_MouseMove(e);
			}
		});
		navigatePad.add(navigateWindow);
		leftPanel.add(navigatePad).setBounds(1, 310, 118, 118);
				
		// Total expected panel size
		leftPanel.setPreferredSize(new java.awt.Dimension(120, 440));
			
		// Chart Viewer
		chartViewer1 = new ChartViewer();
		chartViewer1.setBackground(new java.awt.Color(0xc0, 0xc0, 0xff));
		chartViewer1.setOpaque(true);
		chartViewer1.setHotSpotCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chartViewer1.setScrollDirection(Chart.DirectionHorizontalVertical);
		chartViewer1.setZoomDirection(Chart.DirectionHorizontalVertical);
		chartViewer1.setPreferredSize(new Dimension(500, 480));
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
		
		// Put the leftPanel and rightPanel on the JFrame
		getContentPane().add(leftPanel, java.awt.BorderLayout.WEST);
		getContentPane().add(chartViewer1, java.awt.BorderLayout.CENTER);

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

		// Initially choose the pointer mode (draw to scroll mode)
		pointerPB.doClick();

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
		//
		// For simplicity, in this demo, we just use hard coded data. In a real application,
		// the data probably read from a dynamic source such as a database. (See the
		// ChartDirector documentation on "Using Data Sources with ChartDirector" if you need
		// some sample code on how to read data from database to array variables.)
		//
		dataX0 = new double[] {10, 15, 6, -12, 14, -8, 13, -3, 16, 12, 10.5, -7, 3, -10, -5, 2, 5};
		dataY0 = new double[] {130, 150, 80, 110, -110, -105, -130, -15, -170, 125,  125, 60, 25, 
			150, 150, 15, 120};
		dataX1 = new double[] {6, 7, -4, 3.5, 7, 8, -9, -10, -12, 11, 8, -3, -2, 8, 4, -15, 15};
		dataY1 = new double[] {65, -40, -40, 45, -70, -80, 80, 10, -100, 105, 60, 50, 20, 170, -25,
			50, 75};
		dataX2 = new double[] {-10, -12, 11, 8, 6, 12, -4, 3.5, 7, 8, -9, 3, -13, 16, -7.5, -10,
			-15};
		dataY2 = new double[] {65, -80, -40, 45, -70, -80, 80, 90, -100, 105, 60, -75, -150, -40, 
			120, -50, -30};
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
	/// MouseDown event handler for the navigator buttons.
	/// </summary>
	private void navigateButton_MouseDown(MouseEvent e)
	{
		activeNavigateButton = (JButton)e.getSource();
		buttonRepeatTimer.start();
	}

	/// <summary>
	/// MouseUp event handler for the navigator buttons.
	/// </summary>
	private void navigateButton_MouseUp(MouseEvent e)
	{
		activeNavigateButton = null;
		buttonRepeatTimer.stop();
	}
	
	/// <summary>
	/// Tick event handler for the buttonRepeatTimer. This is the clock used to constantly 
	/// scroll the chart when a navigator button is pressed 
	/// </summary>
	private void buttonRepeatTimer_Tick()
	{
		if (activeNavigateButton != null)
		{
			double vpLeft = chartViewer1.getViewPortLeft();
			double vpTop = chartViewer1.getViewPortTop();

			// Each tick scroll the chart by 5% in the button direction
			if ((activeNavigateButton == leftPB) || (activeNavigateButton == upLeftPB) ||
				(activeNavigateButton == downLeftPB))
				vpLeft -= chartViewer1.getViewPortWidth() * 0.05;
			if ((activeNavigateButton == rightPB) || (activeNavigateButton == upRightPB) || 
				(activeNavigateButton == downRightPB))
				vpLeft += chartViewer1.getViewPortWidth() * 0.05;
			if ((activeNavigateButton == upPB) || (activeNavigateButton == upLeftPB) || 
				(activeNavigateButton == upRightPB))
				vpTop -= chartViewer1.getViewPortHeight() * 0.05;
			if ((activeNavigateButton == downPB) || (activeNavigateButton == downLeftPB) || 
				(activeNavigateButton == downRightPB))
				vpTop += chartViewer1.getViewPortHeight() * 0.05;

			// Update the chart, but no need to update the image map yet, as the chart is still 
			// Scrolling and is unstable
			scrollChartTo(chartViewer1, vpLeft, vpTop);
		}
	}

	/// <summary>
	/// MouseDown event handler for the center button.
	/// </summary>
	private void centerPB_MouseDown(EventObject e)
	{
		// Center the view port at the origin (0, 0) 
		scrollChartTo(chartViewer1, 0.5 - chartViewer1.getViewPortWidth() / 2, 
			0.5 - chartViewer1.getViewPortHeight() / 2);
	}

	/// <summary>
	/// ValueChanged event handler for zoomBar. Zoom in around the center point and try to 
	/// maintain the aspect ratio
	/// </summary>
	private void zoomBar_ValueChanged(javax.swing.event.ChangeEvent e)
	{
		// Remember the center point
		double centerX = chartViewer1.getViewPortLeft() + chartViewer1.getViewPortWidth() / 2;
		double centerY = chartViewer1.getViewPortTop() + chartViewer1.getViewPortHeight() / 2;

		// Aspect ratio and zoom factor
		double aspectRatio = chartViewer1.getViewPortWidth() / chartViewer1.getViewPortHeight();
		double zoomTo = ((double)zoomBar.getValue()) / zoomBar.getMaximum();

		// Zoom while respecting the aspect ratio
		chartViewer1.setViewPortWidth(zoomTo * Math.max(1, aspectRatio));
		chartViewer1.setViewPortHeight(zoomTo * Math.max(1, 1 / aspectRatio));
			
		// Adjust ViewPortLeft and ViewPortTop to keep center point unchanged
		chartViewer1.setViewPortLeft(centerX - chartViewer1.getViewPortWidth() / 2);
		chartViewer1.setViewPortTop(centerY - chartViewer1.getViewPortHeight() / 2);
						
		// Update the chart, but no need to update the image map yet, as the chart is still 
		// zooming and is unstable
		chartViewer1.updateViewPort(true, false);
	}
	
	/// <summary>
	/// MouseDown event handler for the navigateWindow
	/// </summary>
	private void navigateWindow_MouseDown(MouseEvent e)
	{
		if (e.getModifiers() == InputEvent.BUTTON1_MASK)
		{
			// Save the mouse coordinates to keep track of how far the navigateWindow has been 
			// dragged.
			isDraggingNavigatePad = true;
			mouseDownXCoor = e.getX();
			mouseDownYCoor = e.getY();
		}
	}

	/// <summary>
	/// MouseMove event handler for the navigateWindow
	/// </summary>
	private void navigateWindow_MouseMove(MouseEvent e)
	{
		if (isDraggingNavigatePad)
		{
			// Is currently dragging - move the navigateWindow based on the distances dragged
			int newLabelLeft = Math.max(2, navigateWindow.getX() + e.getX() - mouseDownXCoor);
			int newLabelTop = Math.max(2, navigateWindow.getY() + e.getY() - mouseDownYCoor);

			// Ensure the navigateWindow is within the navigatePad container
			if (newLabelLeft + navigateWindow.getWidth() > navigatePad.getWidth() - 2)
				newLabelLeft = navigatePad.getWidth() - navigateWindow.getWidth() - 2;
			if (newLabelTop + navigateWindow.getHeight() > navigatePad.getHeight() - 2)
				newLabelTop = navigatePad.getHeight() - navigateWindow.getHeight() - 2;

			// Update the navigateWindow position as it is being dragged
			navigateWindow.setLocation(newLabelLeft, newLabelTop);

			// Update the WinChartViewer ViewPort as well
			chartViewer1.setViewPortLeft(((double)navigateWindow.getX() - 2) / 
				(navigatePad.getWidth() - 4));
			chartViewer1.setViewPortTop(((double)navigateWindow.getY() - 2) / 
				(navigatePad.getHeight() - 4));

			// Update the chart, but no need to update the image map yet, as the chart is still 
			// scrolling and is unstable
			chartViewer1.updateViewPort(true, false);
		}
	}

	/// <summary>
	/// MouseUp event handler for the navigateWindow
	/// </summary>
	private void navigateWindow_MouseUp(MouseEvent e)
	{
		if (e.getModifiers() == InputEvent.BUTTON1_MASK)
			isDraggingNavigatePad = false;
	}
	
	/// <summary>
	/// Scroll the view port to the given position if necessary
	/// </summary>
	private void scrollChartTo(ChartViewer viewer, double vpLeft, double vpTop)
	{
		// Validate the parameters
		vpLeft = Math.max(0, Math.min(vpLeft, 1 - viewer.getViewPortWidth()));
		vpTop = Math.max(0, Math.min(vpTop, 1 - viewer.getViewPortHeight()));

		if ((vpLeft != viewer.getViewPortLeft()) || (vpTop != viewer.getViewPortTop()))
		{
			// Update chart only if the view port has changed
			viewer.setViewPortLeft(vpLeft);
			viewer.setViewPortTop(vpTop);
			viewer.updateViewPort(true, false);
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
		// Get all parameters and sort them by key
		Hashtable parameters = e.getAttrValues();
		Object[] attributes = parameters.keySet().toArray();
		Arrays.sort(attributes);
	
		// Create a JTable to show the hot spot attribute/value pairs
		Object[][] rows = new Object[parameters.size()][2];
		for (int i = 0; i < attributes.length; ++i)
		{
			rows[i][0] = attributes[i];
			rows[i][1] = parameters.get(attributes[i]);
		}
		JTable table = new JTable(rows, new Object[] {"Parameter", "Value"});

		// Show the table in a dialog
		JDialog d = new JDialog(this, "Hot Spot Parameters", true);
		d.setSize(300, 300);
		Container container = d.getContentPane();

		// Just add some descriptive text to the dialg
		JTextArea t = new JTextArea ("This dialog is for demonstration only." +
			" In this demo, we simply list out all hot spot parameters.");
		t.setLineWrap(true);
		t.setWrapStyleWord(true);
		t.setEditable(false);
		t.setOpaque(false);
		t.setMargin(new Insets(5, 5, 5, 5));
		container.add(t, BorderLayout.NORTH);
	
		// Create the scroll pane on the dialog and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		container.add(scrollPane);

		// Show the dialog on where the mouse click occur	
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
		// Update the navigator window size and position.
		if (!isDraggingNavigatePad)
		{
			navigateWindow.setBounds(
				(int)Math.round(chartViewer1.getViewPortLeft() * (navigatePad.getWidth() - 4) + 2),
				(int)Math.round(chartViewer1.getViewPortTop() * (navigatePad.getHeight() - 4) + 2),
				(int)Math.max(1.0, chartViewer1.getViewPortWidth() * (navigatePad.getWidth() - 4)),
				(int)Math.max(1.0, chartViewer1.getViewPortHeight() * (navigatePad.getHeight() - 4))
			);
		}

		// Synchronize the zoom bar value with the view port width/height
		zoomBar.setValue((int)Math.round(Math.min(chartViewer1.getViewPortWidth(), 
			chartViewer1.getViewPortHeight()) * zoomBar.getMaximum()));
			
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
		// Create an XYChart object 500 x 480 pixels in size, with light blue (c0c0ff) as 
		// background color
		XYChart c = new XYChart(500, 480, 0xc0c0ff);
		
		// Re-cycle the resources of the existing chart, if any. This can improve performance 
		// by reducing the frequency of garbage collections. 		
		c.recycle(chartViewer1.getChart());

		// Set the plotarea at (50, 40) and of size 400 x 400 pixels. Use light grey (c0c0c0)
		// horizontal and vertical grid lines. Set 4 quadrant coloring, where the colors of 
		// the quadrants alternate between lighter and deeper grey (dddddd/eeeeee). 
		c.setPlotArea(50, 40, 400, 400, -1, -1, -1, 0xc0c0c0, 0xc0c0c0
			).set4QBgColor(0xdddddd, 0xeeeeee, 0xdddddd, 0xeeeeee, 0x000000);

		// Enable clipping mode to clip the part of the data that is outside the plot area.
		c.setClipping();

		// Set 4 quadrant mode, with both x and y axes symetrical around the origin
		c.setAxisAtOrigin(Chart.XYAxisAtOrigin, Chart.XAxisSymmetric + Chart.YAxisSymmetric);

		// Add a legend box at (450, 40) (top right corner of the chart) with vertical layout
		// and 8 pts Arial Bold font. Set the background color to semi-transparent grey.
		LegendBox legendBox = c.addLegend(450, 40, true, "arialbd.ttf", 8);
		legendBox.setAlignment(Chart.TopRight);
		legendBox.setBackground(0x40dddddd);

		// Add a titles to axes
		c.xAxis().setTitle("Alpha Index");
		c.yAxis().setTitle("Beta Index");

		// Set axes width to 2 pixels
		c.xAxis().setWidth(2);
		c.yAxis().setWidth(2);

		// The default ChartDirector settings has a denser y-axis grid spacing and less-dense
		// x-axis grid spacing. In this demo, we want the tick spacing to be symmetrical.
		// We use around 50 pixels between major ticks and 25 pixels between minor ticks.
		c.xAxis().setTickDensity(50, 25);
		c.yAxis().setTickDensity(50, 25);

		//
		// In this example, we represent the data by scatter points. If you want to represent
		// the data by somethings else (lines, bars, areas, floating boxes, etc), just modify
		// the code below to use the layer type of your choice. 
		//

		// Add scatter layer, using 11 pixels red (ff33333) X shape symbols
		c.addScatterLayer(dataX0, dataY0, "Group A", Chart.Cross2Shape(), 11, 0xff3333);

		// Add scatter layer, using 11 pixels green (33ff33) circle symbols
		c.addScatterLayer(dataX1, dataY1, "Group B", Chart.CircleShape, 11,	0x33ff33);

		// Add scatter layer, using 11 pixels blue (3333ff) triangle symbols
		c.addScatterLayer(dataX2, dataY2, "Group C", Chart.TriangleSymbol, 11, 0x3333ff);

		if (maxX == minX)
		{
			// The axis scale has not yet been set up. So this is the first time the chart is
			// drawn and it is drawn with no zooming. We can use auto-scaling to determine the
			// axis-scales, then remember them for future use. 

			// Explicitly auto-scale axes so we can get the axis scales
			c.layout();

			// Save the axis scales for future use
			minX = c.xAxis().getMinValue();
			maxX = c.xAxis().getMaxValue();
			minY = c.yAxis().getMinValue();
			maxY = c.yAxis().getMaxValue();
		}
		else
		{
			// Compute the zoomed-in axis scales using the overall axis scales and ViewPort size
			double xScaleMin = minX + (maxX - minX) * viewer.getViewPortLeft();
			double xScaleMax = minX + (maxX - minX) * (viewer.getViewPortLeft() + 
				viewer.getViewPortWidth());
			double yScaleMin = maxY - (maxY - minY) * (viewer.getViewPortTop() + 
				viewer.getViewPortHeight());
			double yScaleMax = maxY - (maxY - minY) * viewer.getViewPortTop();
			// *** use the following formula if you are using a log scale axis ***
			// double xScaleMin = minX * Math.pow(maxX / minX, viewer.getViewPortLeft());
			// double xScaleMax = minX * Math.pow(maxX / minX, viewer.getViewPortLeft() + 
			//     viewer.getViewPortWidth());
			// double yScaleMin = maxY * Math.pow(minY / maxY, viewer.getViewPortTop() + 
			//     viewer.getViewPortHeight());
			// double yScaleMax = maxY * Math.pow(minY / maxY, viewer.getViewPortTop());

			// Set the axis scales
			c.xAxis().setLinearScale(xScaleMin, xScaleMax);
			c.xAxis().setRounding(false, false);
			c.yAxis().setLinearScale(yScaleMin, yScaleMax);
			c.yAxis().setRounding(false, false);
		}

		// Set the chart image to the ChartViewer
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
				"title='[{dataSetName}] Alpha = {x}, Beta = {value}'"));
		}
	}
	
	//
	// Implementation of the DemoModule interface to allow this demo to run inside the 
	// ChartDirectorDemo browser
	//
    
	// Name of demo program
	public String toString() 
	{ 
		return "Zoom/Scroll Demo (2)"; 
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
