package org.bh.app.fe.comps;

import org.bh.app.fe.Language;
import org.bh.app.fe.Main;
import bht.test.tools.comps.BHLabel;
import bht.tools.util.StringPP;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * SettingsPanel, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012. License is default.<hr/>
 * @author Supuhstar of Blue Husky Programming
 * @since Apr 23, 2012
 * @version 1.0.0
 */
public class SettingsPanel extends JPanel //NOTE: Must be compiled in UTF-8
{
  private JComboBox<CharSequence> languageComboBox;
  private JLabel languageGreeting;
  
  public SettingsPanel()
  {
    initGUI();
  }

  private void initGUI()
  {
    languageGreeting = new BHLabel("[Greeting]");
    add(languageGreeting, BorderLayout.SOUTH);
    
    //<editor-fold defaultstate="collapsed" desc="languageComboBox">
    languageComboBox = new JComboBox<>();
    for (Language lang : Main.TRANSLATOR.getAllLanguages())
    {
      languageComboBox.addItem(lang.getName());
    }
    
    languageComboBox.addItemListener(new ItemListener()
    {
      @Override
      public void itemStateChanged(ItemEvent e)
      {
        StringPP sel = new StringPP(languageComboBox.getSelectedItem().toString());
        for (Language lang : Main.TRANSLATOR.getAllLanguages())
        {
          if (sel.equalsIgnoreCase(lang.getName()))
          {
            languageGreeting.setText(lang.getWord("greeting").toString());
            Main.TRANSLATOR.setCurrentLanguage(lang);
            return;
          }
        }
      }
    });
    
    add(languageComboBox, BorderLayout.CENTER);
    //</editor-fold>
  }
  
}
