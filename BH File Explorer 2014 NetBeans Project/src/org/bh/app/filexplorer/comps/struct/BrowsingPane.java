package org.bh.app.filexplorer.comps.struct;

import javax.swing.JComponent;

/**
 * BrowsingPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * The pane that will hold one or more {@link FileDisplay}s, an {@link AddressBar} for all (or each), and a way to switch
 * between them, such as tabs
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-23
 */
public abstract class BrowsingPane extends JComponent
{
	public abstract FileDisplay[] getFileDisplays();
}
