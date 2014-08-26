package org.bh.app.fe.comps;

import bht.tools.comps.BHImageComp;
import bht.tools.util.StringPP;
import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * ImagePreviewPane, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 10, 2012
 * @version 1.0.0
 */
public class ImagePreviewPane extends PluginPane //NOTE: Must be compiled in UTF-8
{
  private BHImageComp icon;
  private JLabel message;
  
  public ImagePreviewPane(){this(null);}
  public ImagePreviewPane(File imagePath)
  {
    icon = new BHImageComp(imagePath == null ? null : new ImageIcon(imagePath.toString()), BHImageComp.BEHAVIOR_DEFAULT);
    initGUI();
  }

  private void initGUI()
  {
    removeAll();
    setLayout(new BorderLayout());
    add(message = new JLabel("Click a PNG to preview it"), BorderLayout.CENTER);
    message.setVisible(false);
    add(icon, BorderLayout.CENTER);
  }
  
  public void setBehavior(byte behavior)
  {
    icon.setBehavior(behavior);
  }

  public static boolean alwaysRespondsTo(File f)
  {
    StringPP name = new StringPP(f.getName());
    return f.isFile() && name.endsWithIgnoreCase(".png");
  }

  @Override public boolean respondsTo(File f)
  {
    return alwaysRespondsTo(f);
  }

  @Override public void alertOfDisplay(File... f)
  {
    try
    {
      icon.setImage(new ImageIcon(f[0].toURI().toURL()));
      message.setVisible(false);
      icon.setVisible(true);
    }
    catch (MalformedURLException ex)
    {
      message.setText("Could not preview due to:\n" + ex);
      icon.setVisible(false);
      message.setVisible(true);
      Logger.getLogger(ImagePreviewPane.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
