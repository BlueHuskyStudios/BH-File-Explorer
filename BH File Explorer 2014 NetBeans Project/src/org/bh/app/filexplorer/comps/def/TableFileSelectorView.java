package org.bh.app.filexplorer.comps.def;

import java.io.File;
import org.bh.app.filexplorer.comps.struct.FileSelectorView;
import org.bh.app.filexplorer.util.BHFile;

/**
 * TableFileSelectorView, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-25
 */
public class TableFileSelectorView extends FileSelectorView
{
	BHFile currentDir;
	
	@Override
	public File[] getDisplayedFiles()
	{
		return currentDir.listFiles();
	}

	@Override
	public void displayDirectory(File newDirectory)
	{
		currentDir = BHFile.valueOf(newDirectory);
	}
	
}
