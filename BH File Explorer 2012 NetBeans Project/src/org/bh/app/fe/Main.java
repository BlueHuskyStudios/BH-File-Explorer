package org.bh.app.fe;

import org.bh.app.fe.events.LanguageChangeEvent;
import org.bh.app.fe.events.LanguageChangeListener;
import bht.tools.fx.CompAction;
import bht.tools.util.ArrayPP;
import bht.tools.util.math.Numbers;
import bht.tools.util.ProgLog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * This is a file explorer made by Blue Husky Studios
 * @author Supuhstar
 * @since 
 * @version 1.0.0 λ
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class Main
{private static long start = System.nanoTime();
  private static ArrayPP<File> initPaths;
  public static GUI gui;
  public static final ProgLog log;
  /** Literally {@value} */
  public static final CharSequence TITLE = "Blue Husky's File Browser", VERSION = "1.0.0 λ";
  public static final Translator TRANSLATOR = new Translator();
  public static final String RES_DIR = "/org/bh/app/fe/res/";
  
  
  static
  {
    log = new ProgLog(TITLE, VERSION);
    Properties en = new Properties(), es = new Properties(), de = new Properties();
    //<editor-fold defaultstate="collapsed" desc="en">
    try
    {
      en.load(Main.class.getResourceAsStream(RES_DIR + "words.properties"));
      TRANSLATOR.addLanguage(new Language(en.getProperty("language"), en));
    }
    catch (IOException ex)
    {
      log.logThrowable(ex, Main.class + "#static");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="de">
    try
    {
      de.load(Main.class.getResourceAsStream(RES_DIR + "words_de_DE.properties"));
      TRANSLATOR.addLanguage(new Language(de.getProperty("language"), de));
    }
    catch (IOException ex)
    {
      log.logThrowable(ex, Main.class + "#static");
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="es">
    try
    {
      es.load(Main.class.getResourceAsStream(RES_DIR + "words_es_ES_EURO.properties"));
      TRANSLATOR.addLanguage(new Language(es.getProperty("language"), es));
    }
    catch (IOException ex)
    {
      log.logThrowable(ex, Main.class + "#static");
    }
    //</editor-fold>
    
    TRANSLATOR.addLanguageChangeListener(new LanguageChangeListener() {

      @Override
      public void willChange(LanguageChangeEvent evt)
      {
      }

      @Override
      public void didChange(LanguageChangeEvent evt)
      {
        translateAllRegisteredLabels();
      }
    });
    
    initPaths = new ArrayPP<>();
  }
  
  /**
   * @param args the command line arguments
   * <ul>
   * <li>Any existent file path to create a new browser tab displaying the contents of the given folder</li>
   * <li>{@code home} to create a new home tab</li>
   * </ul>
   */
  public static void main(String[] args)
  {
    long last = System.nanoTime(), begin, end;
    try
    {
      String n = Main.class.getName();
      System.out.println(n + ": Uncontrolled initialization complete (took " + Numbers.groupDigits(last-start) + "ns). Now starting "  +TITLE + "...");

      System.out.println(n + ": Processing arguments...");
      begin = System.nanoTime();

      processArgs(args);

      last = System.nanoTime();
      System.out.println(n + ": Done processing arguments. (took " + Numbers.groupDigits(last-begin) + "ns). Now creating GUI...");
      begin = System.nanoTime();
      {
        File[] temp = initPaths.t;
        gui = new GUI(temp);
      }
      gui.addWindowListener(new WindowAdapter()
      {
        @Override public void windowClosing(WindowEvent e)
        {
          gui.exit(0);
        }
      });
      gui.testTransparency();

      last = System.nanoTime();
      System.out.println(n + ": GUI created. (took " + Numbers.groupDigits(last-begin) + "ns). Now showing GUI...");
      begin = System.nanoTime();

      CompAction.snapToCenter(gui);
      gui.openWindow(gui/*, new java.awt.event.ActionListener()
      {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e)
        {
          if (gui.shouldUseTransparency())
            CompAction.setTransparency(gui, 1);
        }
      }*/);

      last = System.nanoTime();
      System.out.println(n + ": GUI shown. (took " + Numbers.groupDigits(last-begin) + "ns).");
      begin = System.nanoTime();

      last = System.nanoTime();
      System.out.println(n + ": " + TITLE + " startup complete. (took " + Numbers.groupDigits(last-start) + "ns; " + ((double)(last-start) / 1000000000) + "s).");
    }
    catch(Throwable t)
    {
      log.logThrowable(t, "Main#main(String[] " + Arrays.toString(args) + ")");
      t.printStackTrace();
    }
  }

  private static void processArgs(String[] args)
  {
    File f;
    for (String s : args)
      try
      {
        if ((f = new File(s)).exists())
          if (initPaths == null)
            initPaths = new ArrayPP<File>(f);
          else
            initPaths.add(f);
        else if (s.equalsIgnoreCase("home"))
          initPaths.add((File)null);
      }
      catch (Throwable t)
      {
        System.err.println("Could not parse argument to a file path: " + s);
        t.printStackTrace();
      }
  }

  public static void translateAllRegisteredLabels()
  {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}
