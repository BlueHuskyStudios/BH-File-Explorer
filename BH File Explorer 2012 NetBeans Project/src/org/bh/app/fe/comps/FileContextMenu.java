package org.bh.app.fe.comps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 * FileContextMenu, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012.<hr/>
 * A class that contains actions to be done to a file in a JPopupMenu
 * @author Supuhstar of Blue Husky Programming
 * @since Jan 7, 2012
 * @version 1.0.0
 * @see JPopupMenu
 */
public class FileContextMenu extends JPopupMenu
{
  private ActionListener renAL, delAL, openAL;
  private File[] target;
  private JMenuItem renameMenuItem, deleteMenuItem, openMenuItem;
  private Separator openSep;
  
  public FileContextMenu(File... initTarget)
  {
    target = initTarget;
    initGUI();
  }
  
  private void initGUI()
  {
    //<editor-fold defaultstate="collapsed" desc="Open">
    openMenuItem = new JMenuItem("Open", KeyEvent.VK_ACCEPT);
    openMenuItem.setVisible(false);
    openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ACCEPT, 0));
    openMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (openAL != null)
          openAL.actionPerformed(e);
      }
    });
    add(openMenuItem);
    //</editor-fold>
    
    (openSep = new Separator()).setVisible(false);
    add(openSep);
    
    //<editor-fold defaultstate="collapsed" desc="Rename">
    renameMenuItem = new JMenuItem("Rename", KeyEvent.VK_R);
    renameMenuItem.setVisible(false);
    renameMenuItem.setAccelerator(KeyStroke.getKeyStroke('R', InputEvent.CTRL_DOWN_MASK));
    renameMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (renAL != null)
          renAL.actionPerformed(e);
      }
    });
    add(renameMenuItem);
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete">
    deleteMenuItem = new JMenuItem("Delete", KeyEvent.VK_DELETE);
    deleteMenuItem.setVisible(false);
    deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
    deleteMenuItem.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if (delAL != null)
          delAL.actionPerformed(e);
      }
    });
    add(deleteMenuItem);
    //</editor-fold>
  }

  public File[] getTarget()
  {
    return target;
  }

  public void setTarget(File... newTarget)
  {
    target = newTarget;
  }
  
  public void setRenameActionListener(ActionListener al)
  {
    renAL = al;
    checkVis();
  }
  
  public ActionListener getRenameActionListener()
  {
    return renAL;
  }
  
  public void setDeleteActionListener(ActionListener al)
  {
    delAL = al;
    checkVis();
  }
  
  public ActionListener getDeleteActionListener()
  {
    return delAL;
  }
  
  public void setOpenActionListener(ActionListener al)
  {
    openAL = al;
    checkVis();
  }
  
  public ActionListener getOpenActionListener()
  {
    return openAL;
  }

  private void checkVis()
  {
    boolean r = renAL != null, d = delAL != null, o = openAL != null;
    renameMenuItem.setVisible(r);
    deleteMenuItem.setVisible(d);
    openMenuItem.setVisible(o);
    openSep.setVisible(o || (!d && !r));
  }
}
