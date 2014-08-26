package org.bh.app.fe;

import org.bh.app.fe.events.LanguageChangeEvent;
import org.bh.app.fe.events.LanguageChangeListener;
import bht.tools.util.ArrayPP;

/**
 * Translator, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 23, 2012
 * @version 1.0.0
 */
public class Translator //NOTE: Must be compiled in UTF-8
{
  ArrayPP<LanguageChangeListener> changeListeners;
  ArrayPP<Language> languages;
  int current = 0;

  public Translator(Language... initLanguages)
  {
    languages = new ArrayPP<>(initLanguages);
    changeListeners = new ArrayPP<>();
  }
  
  public CharSequence translate(CharSequence key)
  {
    return languages.get(current).getWord(key);
  }
  
  public Translator addLanguage(Language newLanguage)
  {
    languages.add(newLanguage);
    return this;
  }
  
  /**
   * Sets the given language to be the currently selected one. If it is not already in the array, it is added and selected.
   * Language change listeners are fired in this method.
   * @param lang the new language
   * @return 
   */
  public Translator setCurrentLanguage(Language lang)
  {
    LanguageChangeEvent evt = new LanguageChangeEvent(this, languages.get(current), lang);
    for (LanguageChangeListener changeListener : changeListeners)
      changeListener.willChange(evt);
    
    for(int i=0, l=languages.length(); i < l; i++)
      if (languages.get(i).equals(lang))
      {
        current = i;
        for (LanguageChangeListener changeListener : changeListeners)
          changeListener.didChange(evt);
        return this;
      }
    
    languages.add(lang);
    current = languages.length() - 1;
    
    
    for (LanguageChangeListener changeListener : changeListeners)
      changeListener.didChange(evt);
    return this;
  }

  public Language[] getAllLanguages()
  {
    return languages.t;
  }
  
  public Translator addLanguageChangeListener(LanguageChangeListener cl)
  {
    changeListeners.add(cl);
    return this;
  }
  
  /**
   * Removes the first encountered instance of the given {@link LanguageChangeListener} from the array that fires upon changing
   * the current language.
   * @param cl the {@link LanguageChangeListener} to remove
   * @return 
   */
  public Translator removeLanguageChangeListener(LanguageChangeListener cl)
  {
    changeListeners.remove(cl, false);
    return this;
  }
}
