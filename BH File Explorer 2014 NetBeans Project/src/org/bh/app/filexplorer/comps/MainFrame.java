package org.bh.app.filexplorer.comps;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import javax.swing.JFrame;

/**
 * MainFrame, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-21
 */
public class MainFrame extends JFrame implements WindowListener
{
	private static final long serialVersionUID = 0x1_000_000L;

	public MainFrame(String title)
	{
		super(title);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
	}

	@Override public void windowOpened(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}
	@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowActivated(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}

	@Override
	public void windowClosing(WindowEvent e)
	{
		setVisible(false);
		WindowEvent closeEvent =
			new WindowEvent(
				e.getWindow(),
				e.getID(),
				e.getOppositeWindow(),
				e.getNewState(),
				WindowEvent.WINDOW_CLOSED);
		windowClosed(closeEvent);
	}
	
	@Override
	public void windowClosed(WindowEvent e)
	{
		System.exit(0);
	}
}
