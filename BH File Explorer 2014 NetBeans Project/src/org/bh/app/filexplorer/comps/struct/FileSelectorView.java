package org.bh.app.filexplorer.comps.struct;

import java.io.File;
import javax.swing.JComponent;

/**
 * FileSelectorView, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Only displays and allows the user to interact with files. This could be a grid of icons, a table, etc.
 * This should not have a large, interactive preview pane.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-25
 */
public abstract class FileSelectorView extends JComponent
{
	public abstract File[] getDisplayedFiles();
	public abstract void displayDirectory(File newDirectory);
}
