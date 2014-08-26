package org.bh.app.filexplorer.comps.def;

import java.io.File;
import org.bh.app.filexplorer.comps.struct.FileDisplay;
import org.bh.app.filexplorer.util.BHFile;

/**
 * DefaultFileDisplay, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-24
 */
public class DefaultFileDisplay extends FileDisplay
{
	private BHFile dir;

	public DefaultFileDisplay(File dir)
	{
		this.dir = BHFile.valueOf(dir);
	}
	
	@Override
	public File[] getDisplayedFiles()
	{
		return dir.listFiles();
	}

	@Override
	public void displayDirectory(File newDirectory)
	{
		dir = BHFile.valueOf(newDirectory);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Expected directory, got " + newDirectory);
		validate();
	}

	@Override
	public File getCurrentDirectory()
	{
		return dir;
	}
}
