/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.bh.app.fe;

/**
 * defines a way of handling error without ever catching them
 * @author Supuhstar of Blue Husky Programming
 * @since Jan 07, 2012
 * @version 1.0.0
 */
public interface ExceptionHandler
{
  /**
   * Defines a method that looks at and handles the given {@link Throwable} {@code t}
   * @param t the {@link Throwable} to be handled
   */
  public void exceptionThrown(Throwable t);
}
