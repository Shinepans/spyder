package browser;

import java.awt.BorderLayout;
import java.awt.Canvas;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
public class MyBrowser extends JFrame {
 
 public static final int BOARD_WIDTH = 600;
 public static final int BOARD_HEIGHT = 500;
 public static final int LOCATION_X = 100;
 public static final int LOCATION_Y = 50;
 
 JTextField txtField = new JTextField(30);
 
 JButton startButton = new JButton("Search");
 JButton exitButton = new JButton("Exit");
 
 public Shell shell;
 public Browser browser;
 public Display display;
 public Canvas canvas;
 
 public MyFrame frame;
 
 public void init(){
  System.setProperty("sun.awt.xembedserver", "true");
  display = Display.getDefault();  
  canvas = new Canvas();
     frame = new MyFrame("BrowserListener");
     frame.init(this);
       frame.add(canvas,BorderLayout.CENTER);
       frame.pack();  
       
     shell = SWT_AWT.new_Shell(display, canvas);
     shell.setLayout(new FillLayout(SWT.DOWN));
     browser = new Browser(shell, SWT.EMBEDDED);
 
     //browser.setUrl("www.google.com");
    String html = "<html><head>"+ 
    "<base href=/"http://www.eclipse.org/swt//" >"+ 
    "<title>HTML Test</title></head>"+ 
    "<body><a href=/"faq.php/">local link</a></body></html>"; 
     browser.setText(html);
     browser.setVisible(true);
   
     shell.open();
  
    frame.setSize(800, 600); 
    
    frame.setVisible(true);
    
    while (!shell.isDisposed()) {
          if (!display.readAndDispatch())
            display.sleep();
        }
     display.dispose();
  
 }
 
 public void run(final String script){
 
    String html = "<html><head>"+ 
    "<base href=/"http://www.eclipse.org/swt//" >"+ 
    "<title>HTML Test</title></head>"+ 
    "<body><a href=/"faq.php/">local link</a></body></html>";
    this.display.asyncExec(new Runnable(){
     public void run(){
      //browser.setUrl("www.google.com");
      browser.setText(script);
     }
    });
   
 }
 
 
 
 public static void main(String[] args)
 {
  MyBrowser myBrowser = new MyBrowser();
  myBrowser.init();
 }
 
}
