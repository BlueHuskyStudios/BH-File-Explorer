package org.bh.app.filexplorer.comps.struct;

import java.io.File;
import javax.swing.JComponent;

/**
 * BrowsingPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Acts as a delegate between {@link FileSelectorView} and {@link BrowsingPane}. This should contain and control a
 * {@link FileSelectorView} and any accessories, such as a preview pane or toolbar.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-23
 */
public abstract class FileDisplay extends JComponent
{
	public abstract File[] getDisplayedFiles();
	public abstract File getCurrentDirectory();
	public abstract void displayDirectory(File newDirectory);
}
