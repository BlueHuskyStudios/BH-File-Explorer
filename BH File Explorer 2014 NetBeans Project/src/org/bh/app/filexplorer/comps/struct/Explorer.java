package org.bh.app.filexplorer.comps.struct;

import javax.swing.JApplet;

/**
 * Explorer, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * The main application, which contains a BrowsingPane, manages the menu bar, and does anything else needed to control the
 * entire application, aside from OS-particular window controls like close, maximize, etc.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-23
 */
public abstract class Explorer extends JApplet
{
	public abstract BrowsingPane getBrowsingPane();
}
