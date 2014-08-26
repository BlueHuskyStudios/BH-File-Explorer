package org.bh.app.fe.events;

import org.bh.app.fe.Language;
import java.util.EventObject;

/**
 * LanguageChangeEvent, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 26, 2012
 * @version 1.0.0
 */
public class LanguageChangeEvent extends EventObject //NOTE: Must be compiled in UTF-8
{
  private Language oldLang, newLang;
  public LanguageChangeEvent(Object source, Language oldLanguage, Language newLanguage)
  {
    super(source);
    
    oldLang = oldLanguage;
    newLang = newLanguage;
  }

  public Language getNewLang()
  {
    return newLang;
  }

  public void setNewLang(Language newLang)
  {
    this.newLang = newLang;
  }

  public Language getOldLang()
  {
    return oldLang;
  }

  public void setOldLang(Language oldLang)
  {
    this.oldLang = oldLang;
  }
}
