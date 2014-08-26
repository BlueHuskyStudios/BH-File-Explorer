package org.bh.app.fe.events;

/**
 * DisplayChangeListener, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012.
 * @author Supuhstar
 * @since Jan 9, 2012
 * @version 1.0.0
 */
public interface DisplayChangeListener
{
  public void displayDidChange(DisplayEvent de);
  public void displayWillChange(DisplayEvent de);
}