package org.bh.app.fe.comps;

import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

/**
 *
 * @author Kyli
 */
public class ExtendedTabbedPane extends JTabbedPane
{
  JButton addButton;
  
  public ExtendedTabbedPane()
  {
    this(TOP, WRAP_TAB_LAYOUT);
  }

  public ExtendedTabbedPane(int tabPlacement)
  {
    this(tabPlacement, WRAP_TAB_LAYOUT);
  }
  
  public ExtendedTabbedPane(int tabPlacement, int tabLayoutPolicy)
  {
    super(tabPlacement, tabLayoutPolicy);
    addButton = new JButton("+");
  }

  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    
    addButton.paint(g);
  }
}