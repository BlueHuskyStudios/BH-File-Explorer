package org.bh.app.filexplorer.comps.struct;

import bht.tools.comps.event.NavigationEvent.NavigationState;
import bht.tools.util.ArrayPP;
import java.io.File;
import javax.swing.JComponent;
import org.bh.app.filexplorer.evt.AddressBarListener;
import org.bh.app.filexplorer.evt.AddressChangeEvent;

/**
 * AddressBar, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * An address bar is where the user can view and modify the URI corresponding to the current directory.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-21
 */
public abstract class AddressBar extends JComponent
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	private File currentFile;
	private ArrayPP<AddressBarListener> addressBarListeners = new ArrayPP<AddressBarListener>();

	public AddressBar(File initCurrentFile)
	{
		currentFile = initCurrentFile;
	}

	public File getCurrentFile()
	{
		return currentFile;
	}

	public void setCurrentFile(File newCurrentFile)
	{
		AddressChangeEvent evt =
			new AddressChangeEvent(
				this,
				0,
				1,
				currentFile,
				newCurrentFile,
				NavigationState.STATE_GOING_FORWARD);
		currentFile = newCurrentFile;
	}
}
