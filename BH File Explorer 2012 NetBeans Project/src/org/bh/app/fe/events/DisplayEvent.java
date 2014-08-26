package org.bh.app.fe.events;

import java.awt.Component;
import java.io.File;

/**
 * DisplayEvent, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Jan 9, 2012
 * @version 1.0.0
 */
public class DisplayEvent extends java.util.EventObject
{
  private File f;
  public DisplayEvent(Component source, File newLocation)
  {
    super(source);
    f = newLocation;
  }

  @Override
  public Component getSource()
  {
    return (Component)super.getSource();
  }

  public File getNewLocation()
  {
    return f;
  }
  
  public void setNewLocation(File newNewLoc)
  {
    f = newNewLoc;
  }
}
