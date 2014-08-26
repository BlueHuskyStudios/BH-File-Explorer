package org.bh.app.fe;

import java.util.Dictionary;

/**
 * Language, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 24, 2012
 * @version 1.0.0
 */
public class Language //NOTE: Must be compiled in UTF-8
{
  private CharSequence name;
  private Dictionary<Object, Object> dictionary;

  public Language(CharSequence initName,
                  Dictionary<Object, Object> initDictionary)
  {
    name = initName;
    dictionary = initDictionary;
  }
  
  

  /**
   * Get the name of this language
   *
   * @return the value of name
   */
  public CharSequence getName()
  {
    return name;
  }

  /**
   * Set the name of this language
   *
   * @param name the new name
   */
  public void setName(CharSequence name)
  {
    this.name = name;
  }

  public CharSequence getWord(CharSequence key)
  {
    return String.valueOf(dictionary.get(key));
  }

  public void setWord(CharSequence key, CharSequence newVal)
  {
    dictionary.put(key, newVal);
  }
}
