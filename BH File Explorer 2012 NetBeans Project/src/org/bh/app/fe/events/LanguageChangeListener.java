package org.bh.app.fe.events;

import java.util.EventListener;

/**
 *
 * @author Supuhstar
 */
public interface LanguageChangeListener extends EventListener
{
  public void willChange(LanguageChangeEvent evt);
  public void didChange(LanguageChangeEvent evt);
}
