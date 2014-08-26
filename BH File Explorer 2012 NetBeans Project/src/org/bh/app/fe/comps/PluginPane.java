package org.bh.app.fe.comps;

import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * PluginPane, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 10, 2012
 * @version 1.0.0
 */
public class PluginPane extends JPanel //NOTE: Must be compiled in UTF-8
{

  public PluginPane()
  {
    initGUI();
  }
  
  
  public static boolean alwaysRespondsTo(File f){return true;}
  public boolean respondsTo(File f){return true;}
  public void alertOfDisplay(File... f){};

  private void initGUI()
  {
    setLayout(new BorderLayout());
    add(new JLabel("No preview available.", JLabel.CENTER), BorderLayout.CENTER);
  }
}
