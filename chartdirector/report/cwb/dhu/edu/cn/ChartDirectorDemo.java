package chartdirector.report.cwb.dhu.edu.cn;

/////////////////////////////////////////////////////////////////////////////
//
//Copyright 2008 Advanced Software Engineering Limited
//All rights reserved
//
/////////////////////////////////////////////////////////////////////////////
import ChartDirector.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


/////////////////////////////////////////////////////////////////////////////
//
//	JButtonMouseListener
//
//	A simply utility class to enable hot-tracking effects on buttons. It
//	works by enabling the border only when the mouse is over the button.
//
/////////////////////////////////////////////////////////////////////////////
class JButtonMouseListener extends MouseAdapter
{
	public void mouseEntered(MouseEvent e) {
		((JButton)e.getSource()).setBorderPainted(true);
	}
	public void mouseExited(MouseEvent e) {
		((JButton)e.getSource()).setBorderPainted(false);
	}
}


/////////////////////////////////////////////////////////////////////////////
//
//	The main ChartDirector demonstration program
//
//	A simply utility class to enable hot-tracking effects on buttons. It
//	works by enabling the border only when the mouse is over the button.
//
/////////////////////////////////////////////////////////////////////////////
public class ChartDirectorDemo extends JFrame
{
	//The default font we use in this demo
	private Font defaultFont = new Font("sanserif", Font.PLAIN, 11);

	//The tool bar
	private JToolBar toolBar;
	
	//Buttons in the tool bar
	private JButton backPB;
	private JButton forwardPB;
	private JButton previousPB;
	private JButton nextPB;
	private JButton viewCodePB;
	
	//The status bar
	private JLabel statusBar;

	//The tree on the left side of the window
	private JTree tree;

	//The container on the right side of the window to hold the ChartViewers
	private JPanel picturePane;

	//The ChartViewer objects
	private ChartViewer[] viewers;

	//
	// Data structure to handle the Back / Forward buttons. We support up to
	// 100 histories. We store histories as the nodes being selected.
	//
	private DefaultMutableTreeNode[] history = new DefaultMutableTreeNode[100];
			
	// The array index of the currently selected node in the history array.
	private int currentHistoryIndex = -1;

	// The array index of the last valid entry in the history array so that we
	// know if we can use the Forward button.
	private int lastHistoryIndex = -1;
	

	///////////////////////////////////////////////////////////////////////////
	//	Entry Point
	///////////////////////////////////////////////////////////////////////////
	public static void main(java.lang.String[] args)
	{
		new ChartDirectorDemo().setVisible(true);
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Constructor and performs initialization
	///////////////////////////////////////////////////////////////////////////
	private ChartDirectorDemo()
	{
		//Set up the main window title
		setTitle("ChartDirector Sample Charts");
			
		//End application upon closing main window
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {System.exit(0);} });
	
		//
		//Set up the tool bar
		//
		toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
	
		//Create buttons and add them to tool bar
		backPB = new JButton("Back", loadImageIcon("leftarrow.gif"));
		forwardPB = new JButton("Forward", loadImageIcon("rightarrow.gif"));
		previousPB = new JButton("Previous", loadImageIcon("uparrow.gif"));
		nextPB = new JButton("Next", loadImageIcon("downarrow.gif"));
		viewCodePB = new JButton("View Code", loadImageIcon("source.gif"));
	
		//Add buttons to tool bar
		addToToolBar(backPB);
		addToToolBar(forwardPB);
		addToToolBar(previousPB);
		addToToolBar(nextPB);
		addToToolBar(viewCodePB);
		
		//Set up handlers for the buttons
		backPB.addMouseListener(new JButtonMouseListener() {
			public void mouseClicked(MouseEvent e) {
				onBackPBClick();
		}});
		forwardPB.addMouseListener(new JButtonMouseListener() {
			public void mouseClicked(MouseEvent e) {
				onForwardPBClick();
		}});
		previousPB.addMouseListener(new JButtonMouseListener() {
			public void mouseClicked(MouseEvent e) {
				onPreviousPBClick();
		}});
		nextPB.addMouseListener(new JButtonMouseListener() {
			public void mouseClicked(MouseEvent e) {
				onNextPBClick();
		}});
		viewCodePB.addMouseListener(new JButtonMouseListener() {
			public void mouseClicked(MouseEvent e) {
				onViewCodePBClick();
		}});
			
		//
		//Set up status bar
		//
		statusBar = new JLabel(" ");
		statusBar.setBorder(new javax.swing.border.EtchedBorder(
			javax.swing.border.EtchedBorder.LOWERED));
		statusBar.setFont(defaultFont);
		getContentPane().add(statusBar, BorderLayout.SOUTH);
	
		//
		//Set up the tree on the left
		//
		DefaultMutableTreeNode rootNode = createNodes();
		tree = new JTree(rootNode);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setFont(defaultFont);
		tree.getSelectionModel().setSelectionMode(
			TreeSelectionModel.SINGLE_TREE_SELECTION);
	
		//Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				onTreeClick(evt);
		}});

		//
		//Use a JSrollPane to contain the tree to make it scrollable
		//
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBorder(null);
		treeView.setMinimumSize(new Dimension(150, 150));

		//
		//We use a JPanel to contain the ChartViewers on the right
		//
		picturePane = new JPanel();
		picturePane.setBackground(Color.white);
	
		//
		//Some demo modules can display up to 8 charts concurrently, so we
		//need up to 8 ChartViewers.
		//
		viewers = new ChartViewer[8];
		for (int i = 0; i < viewers.length; ++i)
		{
			viewers[i] = new ChartViewer();
			viewers[i].setVisible(false);
			picturePane.add(viewers[i]);
			
			//We display a hand cursor when the mouse is over a hot spot 
			//to demostrate clickable charts
			viewers[i].setHotSpotCursor(Cursor.getPredefinedCursor(
				Cursor.HAND_CURSOR));
			
			//Define the code to handle mouse clicks on hot spots
			viewers[i].addHotSpotListener(new HotSpotAdapter() {
				public void hotSpotClicked(HotSpotEvent e) {
					onChartClick(e);
			}});
		}		

		//
		//We use a scroll pane to support scolling. We want to flow layout
		//the charts like flowing text in a web page. Unluckily, the standard
		//FlowLayout is not suitable, so we use our own layout routine.
		//
		JScrollPane chartView =  new JScrollPane(picturePane);
		picturePane.setLayout(null);
		chartView.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				flowLayoutCharts();
			}
		});
				
		//
		//We use a split pane to contain a tree on the left and 
		//ChartViewers on the right
		//
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
			treeView, chartView);		
		splitPane.setDividerLocation(220);
		splitPane.setPreferredSize(new Dimension(850, 460));
		getContentPane().add(splitPane);
		
		//Layout the Window
		pack();

		//Set initial focus to the first sample code module
		tree.setSelectionPath(new TreePath(rootNode.getFirstLeaf().getPath()));
		tree.requestFocus();
	}

	///////////////////////////////////////////////////////////////////////////
	//	A utility to load an image icon from the Java class path
	///////////////////////////////////////////////////////////////////////////
	private ImageIcon loadImageIcon(String path)
	{
		try { return new ImageIcon(getClass().getClassLoader().getResource(path)); }
		catch (Exception e) { return null; }
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Add a button to the tool bar with a consistent look and feel
	///////////////////////////////////////////////////////////////////////////
	private void addToToolBar(JButton button)
	{
		// Set up look and feel of the button in the tool bar
		Dimension d = new Dimension(72, 48);
		button.setMinimumSize(d);
		button.setMaximumSize(d);
		button.setPreferredSize(d);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setFont(defaultFont);
		
		//add button to tool bar
		toolBar.add(button);		
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Create a tree with all available demo code modules
	///////////////////////////////////////////////////////////////////////////
	private DefaultMutableTreeNode createNodes() 
	{
		DefaultMutableTreeNode root = 
			new DefaultMutableTreeNode("Demo Charts");
		DefaultMutableTreeNode category;
		    
		root.add(category = new DefaultMutableTreeNode("Pie Charts"));
		category.add(new DefaultMutableTreeNode(new simplepie()));
		category.add(new DefaultMutableTreeNode(new threedpie()));
		category.add(new DefaultMutableTreeNode(new multidepthpie()));
		category.add(new DefaultMutableTreeNode(new sidelabelpie()));
		category.add(new DefaultMutableTreeNode(new circlelabelpie()));
		category.add(new DefaultMutableTreeNode(new legendpie()));
		category.add(new DefaultMutableTreeNode(new legendpie2()));
		category.add(new DefaultMutableTreeNode(new explodedpie()));
		category.add(new DefaultMutableTreeNode(new iconpie()));
		category.add(new DefaultMutableTreeNode(new iconpie2()));
		category.add(new DefaultMutableTreeNode(new multipie()));
		category.add(new DefaultMutableTreeNode(new donut()));
		category.add(new DefaultMutableTreeNode(new threeddonut()));
		category.add(new DefaultMutableTreeNode(new icondonut()));
		category.add(new DefaultMutableTreeNode(new texturedonut()));
		category.add(new DefaultMutableTreeNode(new concentric()));
		category.add(new DefaultMutableTreeNode(new pieshading()));
		category.add(new DefaultMutableTreeNode(new threedpieshading()));
		category.add(new DefaultMutableTreeNode(new donutshading()));
		category.add(new DefaultMutableTreeNode(new threeddonutshading()));
		category.add(new DefaultMutableTreeNode(new fontpie()));
		category.add(new DefaultMutableTreeNode(new threedanglepie()));
		category.add(new DefaultMutableTreeNode(new threeddepthpie()));
		category.add(new DefaultMutableTreeNode(new shadowpie()));
		category.add(new DefaultMutableTreeNode(new anglepie()));
		category.add(new DefaultMutableTreeNode(new donutwidth()));
		
		root.add(category = new DefaultMutableTreeNode("Bar Charts"));
		category.add(new DefaultMutableTreeNode(new simplebar()));
		category.add(new DefaultMutableTreeNode(new colorbar()));
		category.add(new DefaultMutableTreeNode(new softlightbar()));
		category.add(new DefaultMutableTreeNode(new glasslightbar()));
		category.add(new DefaultMutableTreeNode(new gradientbar()));
		category.add(new DefaultMutableTreeNode(new cylinderlightbar()));
		category.add(new DefaultMutableTreeNode(new threedbar()));
		category.add(new DefaultMutableTreeNode(new cylinderbar()));
		category.add(new DefaultMutableTreeNode(new polygonbar()));
		category.add(new DefaultMutableTreeNode(new stackedbar()));
		category.add(new DefaultMutableTreeNode(new percentbar()));
		category.add(new DefaultMutableTreeNode(new multibar()));
		category.add(new DefaultMutableTreeNode(new softmultibar()));
		category.add(new DefaultMutableTreeNode(new glassmultibar()));
		category.add(new DefaultMutableTreeNode(new gradientmultibar()));
		category.add(new DefaultMutableTreeNode(new multicylinder()));
		category.add(new DefaultMutableTreeNode(new multishapebar()));
		category.add(new DefaultMutableTreeNode(new overlapbar()));
		category.add(new DefaultMutableTreeNode(new multistackbar()));
		category.add(new DefaultMutableTreeNode(new depthbar()));
		category.add(new DefaultMutableTreeNode(new posnegbar()));
		category.add(new DefaultMutableTreeNode(new hbar()));
		category.add(new DefaultMutableTreeNode(new dualhbar()));
		category.add(new DefaultMutableTreeNode(new markbar()));
		category.add(new DefaultMutableTreeNode(new pareto()));
		category.add(new DefaultMutableTreeNode(new varwidthbar()));
		category.add(new DefaultMutableTreeNode(new gapbar()));
		
		root.add(category = new DefaultMutableTreeNode("Line Charts"));
		category.add(new DefaultMutableTreeNode(new simpleline()));
		category.add(new DefaultMutableTreeNode(new compactline()));
		category.add(new DefaultMutableTreeNode(new threedline()));
		category.add(new DefaultMutableTreeNode(new multiline()));
		category.add(new DefaultMutableTreeNode(new symbolline()));
		category.add(new DefaultMutableTreeNode(new symbolline2()));
		category.add(new DefaultMutableTreeNode(new missingpoints()));
		category.add(new DefaultMutableTreeNode(new unevenpoints()));
		category.add(new DefaultMutableTreeNode(new splineline()));
		category.add(new DefaultMutableTreeNode(new stepline()));
		category.add(new DefaultMutableTreeNode(new linefill()));
		category.add(new DefaultMutableTreeNode(new linecompare()));
		category.add(new DefaultMutableTreeNode(new errline()));
		category.add(new DefaultMutableTreeNode(new multisymbolline()));
		category.add(new DefaultMutableTreeNode(new binaryseries()));
		category.add(new DefaultMutableTreeNode(new customsymbolline()));
		category.add(new DefaultMutableTreeNode(new rotatedline()));
		category.add(new DefaultMutableTreeNode(new xyline()));

		root.add(category = new DefaultMutableTreeNode(
			"Trending and Curve Fitting"));
		category.add(new DefaultMutableTreeNode(new trendline()));
		category.add(new DefaultMutableTreeNode(new scattertrend()));
		category.add(new DefaultMutableTreeNode(new confidenceband()));
		category.add(new DefaultMutableTreeNode(new paramcurve()));
		category.add(new DefaultMutableTreeNode(new curvefitting()));

		root.add(category = new DefaultMutableTreeNode("Scatter/Bubble/Vector Charts"));
		category.add(new DefaultMutableTreeNode(new scatter()));
		category.add(new DefaultMutableTreeNode(new scattersymbols()));
		category.add(new DefaultMutableTreeNode(new scatterlabels()));
		category.add(new DefaultMutableTreeNode(new bubble()));
		category.add(new DefaultMutableTreeNode(new threedbubble()));
		category.add(new DefaultMutableTreeNode(new threedbubble2()));
		category.add(new DefaultMutableTreeNode(new threedbubble3()));
		category.add(new DefaultMutableTreeNode(new bubblescale()));
		category.add(new DefaultMutableTreeNode(new vector()));

		root.add(category = new DefaultMutableTreeNode("Area Charts"));
		category.add(new DefaultMutableTreeNode(new simplearea()));
		category.add(new DefaultMutableTreeNode(new enhancedarea()));
		category.add(new DefaultMutableTreeNode(new threedarea()));
		category.add(new DefaultMutableTreeNode(new patternarea()));
		category.add(new DefaultMutableTreeNode(new stackedarea()));
		category.add(new DefaultMutableTreeNode(new threedstackarea()));
		category.add(new DefaultMutableTreeNode(new percentarea()));
		category.add(new DefaultMutableTreeNode(new deptharea()));
		category.add(new DefaultMutableTreeNode(new rotatedarea()));

		root.add(category = new DefaultMutableTreeNode("Box Charts"));
		category.add(new DefaultMutableTreeNode(new boxwhisker()));
		category.add(new DefaultMutableTreeNode(new boxwhisker2()));
		category.add(new DefaultMutableTreeNode(new floatingbox()));
		category.add(new DefaultMutableTreeNode(new waterfall()));
		category.add(new DefaultMutableTreeNode(new posnegwaterfall()));

		root.add(category = new DefaultMutableTreeNode("Gannt Charts"));
		category.add(new DefaultMutableTreeNode(new gantt()));
		category.add(new DefaultMutableTreeNode(new colorgantt()));
		category.add(new DefaultMutableTreeNode(new layergantt()));
		
		root.add(category = new DefaultMutableTreeNode("Contour Charts/Heat Maps"));
		category.add(new DefaultMutableTreeNode(new contour()));
		category.add(new DefaultMutableTreeNode(new smoothcontour()));
		category.add(new DefaultMutableTreeNode(new scattercontour()));
		category.add(new DefaultMutableTreeNode(new contourinterpolate()));

		root.add(category = new DefaultMutableTreeNode("Finance Charts"));
		category.add(new DefaultMutableTreeNode(new hloc()));
		category.add(new DefaultMutableTreeNode(new candlestick()));
		category.add(new DefaultMutableTreeNode(new finance()));
		category.add(new DefaultMutableTreeNode(new finance2()));
		category.add(new DefaultMutableTreeNode(new FinanceDemo()));
		
		root.add(category = new DefaultMutableTreeNode(
			"Other XY Chart Features"));
		category.add(new DefaultMutableTreeNode(new markzone()));
		category.add(new DefaultMutableTreeNode(new markzone2()));
		category.add(new DefaultMutableTreeNode(new yzonecolor()));
		category.add(new DefaultMutableTreeNode(new xzonecolor()));
		category.add(new DefaultMutableTreeNode(new dualyaxis()));
		category.add(new DefaultMutableTreeNode(new dualxaxis()));
		category.add(new DefaultMutableTreeNode(new multiaxes()));
		category.add(new DefaultMutableTreeNode(new fourq()));
		category.add(new DefaultMutableTreeNode(new datatable()));
		category.add(new DefaultMutableTreeNode(new datatable2()));
		category.add(new DefaultMutableTreeNode(new fontxy()));
		category.add(new DefaultMutableTreeNode(new background()));
		category.add(new DefaultMutableTreeNode(new logaxis()));
		category.add(new DefaultMutableTreeNode(new axisscale()));
		category.add(new DefaultMutableTreeNode(new ticks()));

		root.add(category = new DefaultMutableTreeNode("Surface Charts"));
		category.add(new DefaultMutableTreeNode(new surface()));
		category.add(new DefaultMutableTreeNode(new surface2()));
		category.add(new DefaultMutableTreeNode(new surface3()));
		category.add(new DefaultMutableTreeNode(new scattersurface()));
		category.add(new DefaultMutableTreeNode(new surfaceaxis()));
		category.add(new DefaultMutableTreeNode(new surfacelighting()));
		category.add(new DefaultMutableTreeNode(new surfaceshading()));
		category.add(new DefaultMutableTreeNode(new surfacewireframe()));
		category.add(new DefaultMutableTreeNode(new surfaceperspective()));

		root.add(category = new DefaultMutableTreeNode("Polar Charts"));
		category.add(new DefaultMutableTreeNode(new simpleradar()));
		category.add(new DefaultMutableTreeNode(new multiradar()));
		category.add(new DefaultMutableTreeNode(new stackradar()));
		category.add(new DefaultMutableTreeNode(new polarline()));
		category.add(new DefaultMutableTreeNode(new polararea()));
		category.add(new DefaultMutableTreeNode(new polarspline()));
		category.add(new DefaultMutableTreeNode(new polarscatter()));
		category.add(new DefaultMutableTreeNode(new polarbubble()));
		category.add(new DefaultMutableTreeNode(new polarvector()));
		category.add(new DefaultMutableTreeNode(new rose()));
		category.add(new DefaultMutableTreeNode(new stackrose()));
		category.add(new DefaultMutableTreeNode(new polarzones()));
		category.add(new DefaultMutableTreeNode(new polarzones2()));
		
		root.add(category = new DefaultMutableTreeNode("Pyramids/Cones/Funnels"));
		category.add(new DefaultMutableTreeNode(new simplepyramid()));
		category.add(new DefaultMutableTreeNode(new threedpyramid()));
		category.add(new DefaultMutableTreeNode(new rotatedpyramid()));
		category.add(new DefaultMutableTreeNode(new cone()));
		category.add(new DefaultMutableTreeNode(new funnel()));
		category.add(new DefaultMutableTreeNode(new pyramidelevation()));
		category.add(new DefaultMutableTreeNode(new pyramidrotation()));
		category.add(new DefaultMutableTreeNode(new pyramidgap()));

		root.add(category = new DefaultMutableTreeNode("Meter and Guages"));
		category.add(new DefaultMutableTreeNode(new semicirclemeter()));
		category.add(new DefaultMutableTreeNode(new roundmeter()));
		category.add(new DefaultMutableTreeNode(new wideameter()));
		category.add(new DefaultMutableTreeNode(new squareameter()));
		category.add(new DefaultMutableTreeNode(new multiameter()));
		category.add(new DefaultMutableTreeNode(new iconameter()));
		category.add(new DefaultMutableTreeNode(new hlinearmeter()));
		category.add(new DefaultMutableTreeNode(new vlinearmeter()));
		category.add(new DefaultMutableTreeNode(new multihmeter()));
		category.add(new DefaultMutableTreeNode(new multivmeter()));
		category.add(new DefaultMutableTreeNode(new linearzonemeter()));

		root.add(category = new DefaultMutableTreeNode("Zooming and Scrolling"));
		category.add(new DefaultMutableTreeNode(new ZoomScrollDemo()));
		category.add(new DefaultMutableTreeNode(new ZoomScrollDemo2()));

		root.add(category = new DefaultMutableTreeNode("Realtime Charts"));
		category.add(new DefaultMutableTreeNode(new RealTimeDemo()));

		return root;
	}
 	
	///////////////////////////////////////////////////////////////////////////
	//	Handler when a node (sample code module) is selected in the tree
	///////////////////////////////////////////////////////////////////////////
	private void onTreeClick(TreeSelectionEvent e) 
	{
		//Get selected node (if any)
		DefaultMutableTreeNode node = 
			(DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if (node == null) 
			return;
		
		if (node.isLeaf()) 
		{
			//Get the demo module selected
			DemoModule m = (DemoModule)node.getUserObject();

			//Hide all previous charts
			for (int i = 0; i < viewers.length; ++i)
			{
				viewers[i].setVisible(false);
				viewers[i].setImage(null);
			}
			
			//Add node to history array to support Back/Forward browsing
			addHistory(node);

			//Update status bar to show the source code file name
			statusBar.setText(m.getClass().getName() + ".java");

			//Enable/Disable buttons depending whether they can be used
			backPB.setEnabled(currentHistoryIndex > 0);
			forwardPB.setEnabled(lastHistoryIndex > currentHistoryIndex);
			nextPB.setEnabled(getNextNode() != null);
			previousPB.setEnabled(getPreviousNode() != null);
			
			//Create new demo charts and show it in ChartViewers
			for (int i = 0; i < m.getNoOfCharts(); ++i)
			{
				m.createChart(viewers[i], i);
				viewers[i].setVisible(true);
			}
						
			//Flow layout the charts
			flowLayoutCharts();
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Handler when a hot spot on a chart is clicked
	///////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("rawtypes")
	private void onChartClick(HotSpotEvent e)
	{
		//
		//In this sample code, we just display all parameters associated with
		//the hot spot
		//
		
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
	
	///////////////////////////////////////////////////////////////////////////
	//	Handler for the next button
	///////////////////////////////////////////////////////////////////////////
	private void onNextPBClick()
	{
		//get the next node (if any), and select that node and scroll the tree
		//if necessary to make it visible
		DefaultMutableTreeNode node = getNextNode();
		if (node != null)
		{
			tree.setSelectionPath(new TreePath(node.getPath()));
			tree.scrollPathToVisible(tree.getSelectionPath());
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Helper method to get the next node
	///////////////////////////////////////////////////////////////////////////
	private DefaultMutableTreeNode getNextNode()
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			tree.getLastSelectedPathComponent();
		if (node != null)
		{
			if (node.isLeaf())
				node = node.getNextLeaf();
			else
				node = node.getFirstLeaf();
		}
		return node;
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Handler for the Previous button
	///////////////////////////////////////////////////////////////////////////
	private void onPreviousPBClick()
	{
		//get the previous node (if any), and select that node and scroll the 
		//tree if necessary to make it visible
		DefaultMutableTreeNode node = getPreviousNode();
		if (node != null)
		{
			tree.setSelectionPath(new TreePath(node.getPath()));
			tree.scrollPathToVisible(tree.getSelectionPath());
		}
	}

	///////////////////////////////////////////////////////////////////////////
	//	Helper method to get the previous node
	///////////////////////////////////////////////////////////////////////////
	private DefaultMutableTreeNode getPreviousNode()
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			tree.getLastSelectedPathComponent();
		if (node != null)
			node = node.getPreviousLeaf();
		return node;		
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Handler for the Back button
	///////////////////////////////////////////////////////////////////////////
	private void onBackPBClick()
	{
		// Select the previous node in the history array
		if (currentHistoryIndex > 0)
			tree.setSelectionPath(
				new TreePath(history[--currentHistoryIndex].getPath()));
	}
	
	///////////////////////////////////////////////////////////////////////////
	//	Handler for the Forward button
	///////////////////////////////////////////////////////////////////////////
	private void onForwardPBClick()
	{
		// Select the next node in the history array
		if (lastHistoryIndex > currentHistoryIndex)
			tree.setSelectionPath(
				new TreePath(history[++currentHistoryIndex].getPath()));
	}

	///////////////////////////////////////////////////////////////////////////
	//	Handler for the View Code button
	///////////////////////////////////////////////////////////////////////////
	private void onViewCodePBClick()
	{
		//The status bar always contain the source code file name
		String filename = statusBar.getText();
		
		//
		//Create a window with a scrollable text area to display the source code
		//
		JTextArea p = new JTextArea();
		p.setEditable(false);
		p.setTabSize(4);
		p.setFont(new Font("monospaced", Font.PLAIN, 13));

		JFrame f = new JFrame(filename + " (use ctrl-C to copy selection)");
		f.setSize(720, 450);
		f.getContentPane().add(new JScrollPane(p));
		
		//
		//Load the source code from file
		//
		try
		{
			//We load icons the source code as resource (instead of using 
			//File IO) so that the code will work even if the source files are
			//packaged inside JAR (and hence is invisible to the file system)
			java.io.InputStream s = 
				getClass().getClassLoader().getResourceAsStream(filename);
			if (s == null)
				throw new IOException("Cannot locate " + filename + ".");
			
			BufferedReader b = new BufferedReader(new InputStreamReader(s));

			//Read line by line into buffer
			StringBuffer content = new StringBuffer();
			String line;
			while ((line = b.readLine()) != null)
			{
				content.append(line);
				content.append('\n');
			}
			
			s.close();
			
			//Show source code in text area
			p.setText(content.toString());
		}
		catch (IOException e)
		{
			p.setText(e.toString());
		}

		//ensure the cursor is at the first line of the source code
		p.setCaretPosition(0);
		f.setVisible(true);	
	}

	///////////////////////////////////////////////////////////////////////////
	// Helper method to flow layout the charts
	///////////////////////////////////////////////////////////////////////////
	private void flowLayoutCharts()
	{
		int margin = 5;
		
		//initial cursor position
		int cursorX = margin;
		int cursorY = margin;
		int pageWidth = picturePane.getParent().getWidth();
		int pageHeight = 0;
		
		for (int i = 0; i < viewers.length; ++i)
		{
			//only layout visible charts
			if (!viewers[i].isVisible())
				continue;
				
			int chartWidth =  viewers[i].getIcon().getIconWidth();
			int chartHeight = viewers[i].getIcon().getIconHeight();
			
			if (cursorX + margin + chartWidth > pageWidth)
			{
				//no enough position in current line, so move to next line
				cursorX = margin;
				cursorY = pageHeight + margin;
			}
	
			//layout the charts
			viewers[i].setBounds(cursorX, cursorY, chartWidth, chartHeight);
				
			//advance cursor
			cursorX += chartWidth + margin;
			pageHeight = Math.max(pageHeight, cursorY + chartHeight);
		}

		//resize the picturePane to ensure the charts are visible
		picturePane.setPreferredSize(new Dimension(pageWidth, pageHeight));
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Helper method to a selected node to the history array
	///////////////////////////////////////////////////////////////////////////
	private void addHistory(DefaultMutableTreeNode node)
	{
		// Don't add if selected node is current node to avoid duplication.
		if ((currentHistoryIndex >= 0) && 
			(node == history[currentHistoryIndex]))
			return;

		// Check if the history array is full
		if (currentHistoryIndex + 1 >= history.length)
		{
			// History array is full. Remove oldest 25% from the history array.
			// We add 1 to make sure at least 1 item is removed.
			int itemsToDiscard = history.length / 4 + 1;

			// Remove the oldest items by shifting the array. 
			for (int i = itemsToDiscard; i < history.length; ++i)
				history[i - itemsToDiscard] = history[i];
				
			// Adjust index because array is shifted.
			currentHistoryIndex = history.length - itemsToDiscard;
		}
			
		// Add node to history array
		history[++currentHistoryIndex] = node;

		// After adding a new node, the forward button is always disabled. (This
		// is consistent with normal browser behaviour.) That means the last 
		// history node is always assumed to be the current node. 
		lastHistoryIndex = currentHistoryIndex;
	}
}
