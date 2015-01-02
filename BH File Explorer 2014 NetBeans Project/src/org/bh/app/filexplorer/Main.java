package org.bh.app.filexplorer;

import bht.tools.fx.LookAndFeelChanger;
import bht.tools.net.upd.Version;
import bht.tools.net.upd.Version.Channel;
import bht.tools.util.save.general.ObjectSaver;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bh.app.filexplorer.comps.MainFrame;
import org.bh.app.filexplorer.comps.def.DefaultExplorer;
import org.bh.app.filexplorer.comps.struct.Explorer;

/**
 * Main, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2013
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2014-08-21 (1.0.0) - Kyli created Main
 * @since 2014-08-21
 */
public class Main
{
	public static final String APP_NAME = "BH File Explorer";
	public static final Version APP_VERSION = new Version(1,0,0).setChannel(Channel.LAMBDA);
	public static final String APP_VERSION_STR = APP_VERSION.toString();
	private static final String MAIN_FRAME_NAME = "mainFrame";
	
	private static final Explorer EXPLORER;
	
	static
	{
		LookAndFeelChanger.setLookAndFeel(LookAndFeelChanger.SYSTEM);
		
		EXPLORER = fetchSavedExplorer();
	}
	
	/**
	 * The main launcher for BH File Explorer
	 * 
	 * @param args the command line arguments
	 */
	@SuppressWarnings({"UseSpecificCatch", "BroadCatchBlock", "TooBroadCatch"})
	public static void main(String[] args)
	{
		MainFrame mainFrame = null;
		try
		{
			if (ObjectSaver.isSaved(MAIN_FRAME_NAME, APP_NAME))
				mainFrame = ObjectSaver.load(MAIN_FRAME_NAME, APP_NAME);
		}
		catch (Throwable t)
		{
			Logger.getGlobal().log(Level.WARNING, "Could not load " + MAIN_FRAME_NAME, t);
		}
		if (mainFrame == null)
			mainFrame = new MainFrame(APP_NAME);
		mainFrame.addWindowListener(new SaveOnCloseWindowListener());
		mainFrame.setContentPane(EXPLORER);
		mainFrame.pack();
		mainFrame.setVisible(true);
		
	}

	private static Explorer fetchSavedExplorer() // TODO: Make it saveable
	{
		return new DefaultExplorer();
	}

	private static class SaveOnCloseWindowListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			try
			{
				ObjectSaver.save(e.getWindow(), MAIN_FRAME_NAME, APP_NAME);
			}
			catch (IOException ex)
			{
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Could not save " + MAIN_FRAME_NAME, ex);
			}
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			
		}
	}
}
