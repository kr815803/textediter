/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

/**
 *
 * @author Kelsey
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextEditor extends JFrame {
  private JTextArea area = new JTextArea(20,120);
  private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
  private String currentFile = "Untitled";
  private boolean changed = false;

  public TextEditor(){
    area.setFont(new Font("Monospaced", Font.PLAIN, 12));
    JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    add(scroll, BorderLayout.CENTER);
    
    JMenuBar menu = new JMenuBar();
    setJMenuBar(menu);
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    menu.add(file);
    menu.add(edit);
    
    file.add(New);
    file.add(Open);
    file.add(Save);
    file.add(Quit);
    file.add(SaveAs);
    
    for(int i=0; i<4; i++)
      file.getItem(i).setIcon(null);
    
    edit.add(Cut);
    edit.add(Copy);
    edit.add(Paste);
    
    edit.getItem(0).setText("cut out");
    edit.getItem(1).setText("Copy");
    edit.getItem(2).setText("Paste");
    
    JToolBar tool = new JToolBar();
    add(tool, BorderLayout.NORTH);
    tool.add(New);
    tool.add(Open);
    tool.add(Save);
    tool.addSeparator();
    
    JButton cut = tool.add(Cut), cop = tool.add(Copy), pas = tool.add(Paste);
    cut.setText("Cut");
    cop.setText("Copy");
    pas.setText("Paste");
    
    Save.setEnabled(false);
    SaveAs.setEnabled(false);
    
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    pack();
    area.addKeyListener(kListen);
    setTitle(currentFile);
    setVisible(true);
  }
  
  private KeyListener kListen = new KeyAdapter(){
    public void keyPressed(KeyEvent e){
      changed = true;
      Save.setEnabled(true);
      SaveAs.setEnabled(true);
    }
  };
  
  Action Open = new AbstractAction("Open"){
    public void actionPerformed(ActionEvent e){
      saveOld();
      if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
      readInFile(dialog.getSelectedFile().getAbsolutePath());
      }
      SaveAs.setEnabled(true);
    }
  };

Action Save = new AbstractAction("Save"){
  public void actionPerformed(ActionEvent e){
    if(!currentFile.equals("Untitled")){
      saveFile(currentFile);
    }else{
      saveFileAs();
    }
  }
          
};


Action SaveAs = new AbstractAction("Save as..."){
  public void actionPerformed(ActionEvent e){
    saveFileAs();
  }
};

Action Quit = new AbstractAction("Quit"){
  public void actionPerformed(ActionEvent e){
    saveOld();
    System.exit(0);
  }
};

Action New = new AbstractAction("New"){
  public void actionPerformed(ActionEvent e){
    New(currentFile);
  }
};

ActionMap m = area.getActionMap();
Action Cut = m.get(DefaultEditorKit.cutAction);
Action Copy = m.get(DefaultEditorKit.copyAction);
Action Paste = m.get(DefaultEditorKit.pasteAction);

private void New(String fileName){
  if( currentFile == currentFile)
  try{
    FileReader r = new FileReader(fileName);
    area.read(r, null);
    r.close();    
    new TextEditor();
    changed = false;
  }
  catch(IOException e){
  }
}

private void saveFileAs(){
  if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
      saveFile(dialog.getSelectedFile().getAbsolutePath());
  }
}

private void saveOld(){
  if(changed){
    if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
      saveFile(currentFile);
  }
}

private void readInFile(String fileName){
  try{
    FileReader r = new FileReader(fileName);
    area.read(r, null);
    r.close();
    currentFile = fileName;
    setTitle(currentFile);
    changed = false;
  }
  catch(IOException e){
    Toolkit.getDefaultToolkit().beep();
    JOptionPane.showMessageDialog(this, "Editor cant find the file called "+fileName);
  }
}
  
  private void saveFile(String fileName){
    try{
      FileWriter w = new FileWriter(fileName);
      area.write(w);
      w.close();
      currentFile = fileName;
      setTitle(currentFile);
      changed = false;
      Save.setEnabled(false);
    }
    catch(IOException e){
    }
  }
  
  
  public static void main(String[] arg){
     new TextEditor();
  }
}