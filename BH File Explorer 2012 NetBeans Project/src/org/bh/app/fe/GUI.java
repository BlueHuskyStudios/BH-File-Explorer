package org.bh.app.fe;

import org.bh.app.fe.comps.ExtendedTabbedPane;
import org.bh.app.fe.comps.TableFileBrowser;
import org.bh.app.fe.comps.SettingsPanel;
import org.bh.app.fe.events.DisplayChangeListener;
import org.bh.app.fe.events.DisplayEvent;
import bht.tools.Constants;
import bht.tools.comps.BHFrame;
import bht.tools.fx.Colors;
import bht.tools.fx.CompAction;
import bht.tools.fx.LookAndFeelChanger;
import bht.tools.util.ArrayPP;
import bht.tools.util.math.Numbers;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * 
 * @author Supuhstar
 */
public class GUI extends BHFrame
{private long start=System.nanoTime();
  //<editor-fold defaultstate="collapsed" desc="Objects">
  //<editor-fold defaultstate="collapsed" desc="Components">
  private ArrayPP<TableFileBrowser> mainBrowsers;
  private ExtendedTabbedPane browserTabs;
  private JMenuItem newTabMenuItem;
  //</editor-fold>
  
  //</editor-fold>
  
  @SuppressWarnings("UseOfSystemOutOrSystemErr")
  public GUI(final File... initPaths) throws FileNotFoundException, AccessDeniedException
  {
    super(Main.TITLE, Main.VERSION, Colors.DEFAULT, LookAndFeelChanger.DEFAULT, false);
    long last = System.nanoTime(), begin;//, end;
    final String CLASS_NAME = getClass().getName();
    System.out.println(CLASS_NAME + ": Uncontrolled initialization complete (took " + Numbers.groupDigits(last-start) + "ns). Now creating a new GUI...");
    
    System.out.println(CLASS_NAME + ": Initializing browser array...");
    begin = System.nanoTime();
    
    mainBrowsers = new ArrayPP<>(initPaths.length);
    if (mainBrowsers.isFlat())
      mainBrowsers.add(new TableFileBrowser(null));
    
    for (int i=0; i < initPaths.length; i++)
    {
      mainBrowsers.set(i, new TableFileBrowser(initPaths[i]));
      final int I = i;
      mainBrowsers.get(i).addExceptionHandler(new ExceptionHandler()
      {
        @Override
        public void exceptionThrown(Throwable t)
        {
          CompAction.flash(getThis(), true);
          alertOf(t);
          Main.log.logThrowable(t, CLASS_NAME + "#GUI(" + Arrays.toString(initPaths) + "); //Initialiing browser for " + initPaths[I]);
        }
      });
      mainBrowsers.get(i).addDisplayChangeListener(new DisplayChangeListener()
      {
        @Override
        public void displayWillChange(DisplayEvent de)
        {
          alertOf("Navigating to " + mainBrowsers.get(I).getCurrentDirName() + "...", Constants.INFO_MES);
        }
        @Override
        public void displayDidChange(DisplayEvent de)
        {
          CharSequence dirName = mainBrowsers.get(I).getCurrentDirName();
          browserTabs.setTitleAt(I, dirName.toString());
          alertOf("Navigated to " + dirName, Constants.INFO_MES);
        }
      });
    }
    
    last = System.nanoTime();
    System.out.println(CLASS_NAME + ": Browser array initialized (took " + Numbers.groupDigits(last-begin) + "ns). Initializing and adding internal components...");
    begin = System.nanoTime();
    
    initComponents();
    
    last = System.nanoTime();
    System.out.println(CLASS_NAME + ": Internal components initialized and added (took " + Numbers.groupDigits(last-begin) + "ns). Loading tabs...");
    begin = System.nanoTime();
    
    reloadTabs();
    
    last = System.nanoTime();
    System.out.println(CLASS_NAME + ": Tabs loaded (took " + Numbers.groupDigits(last-begin) + "ns).");
//    begin = System.nanoTime();
    
    last = System.nanoTime();
    System.out.println(CLASS_NAME + ": Creation complete. (took " + Numbers.groupDigits(last-start) + "ns; " + ((double)(last-start) / 1000000000) + "s). Initializing browser array...");
  }
  
  @SuppressWarnings("FinalPrivateMethod")
  private final GUI getThis()
  {
    return this;
  }

  private void initComponents()
  {
    setLayout(new java.awt.GridBagLayout());
    java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
    
    browserTabs = new ExtendedTabbedPane(javax.swing.JTabbedPane.TOP, javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
    gbc.weightx=1;
    gbc.weighty=1;
    gbc.fill=java.awt.GridBagConstraints.BOTH;
    add(browserTabs, gbc);
    
    newTabMenuItem = new JMenuItem("New Tab", KeyEvent.VK_N);
    newTabMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
    getMenu(FILE_MENU).add(newTabMenuItem);
    
    addSettingsPanel(new SettingsPanel(), Main.TITLE);
    
    pack();
  }

  private void reloadTabs()
  {
    browserTabs.removeAll();
    for (TableFileBrowser f : mainBrowsers)
      browserTabs.addTab(f.getCurrentDirName(), f);
    
    pack();
  }
}