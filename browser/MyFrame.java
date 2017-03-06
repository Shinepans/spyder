package browser;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
import org.apache.http.client.ClientProtocolException;
 

public class MyFrame extends JFrame implements ActionListener{
 
 
 
 JTextField txtField = new JTextField(30);
 
 JButton startButton = new JButton("Search");
 //JButton exitButton = new JButton("Exit");
 
 SearchEngine searchEngine;
 
 public MyBrowser myBrowser;
 
 public MyFrame(String title){
  super(title);
    
 }
 
 public MyFrame(){
  super();  
 } 
 
 public void init(MyBrowser myBrowser){
  
  this.myBrowser = myBrowser; 

  
  searchEngine = new SearchEngine();
  
  JPanel northPanel = new JPanel();
  northPanel.add(txtField);
  northPanel.add(startButton);
  //northPanel.add(exitButton);
  this.add(northPanel,BorderLayout.NORTH);
  
  startButton.addActionListener(this);
  //exitButton.addActionListener(this); 
  
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
 
 public void actionPerformed(ActionEvent e){
  if(e.getSource()==startButton){
   String text = txtField.getText();
   String responseBody;
   try {
    responseBody = searchEngine.search(text);
    myBrowser.run(responseBody);
   } catch (ClientProtocolException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   } catch (IOException e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   }
   
   
  }
  
 }
}
