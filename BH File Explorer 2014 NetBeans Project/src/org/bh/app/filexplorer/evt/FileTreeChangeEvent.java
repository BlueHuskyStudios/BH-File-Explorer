package org.bh.app.filexplorer.evt;

import bht.tools.comps.event.NavigationEvent;
import bht.tools.comps.event.NavigationEvent.NavigationState;

/**
 * FileTreeChangeEvent, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-09-04
 */
public class FileTreeChangeEvent
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	private FileTreeChangeType type;
	private File oldFile, newFile;

	public FileTreeChangeEvent(Object source,
							   FileTreeChangeType type,
							   File oldFile,
							   File newFile)
	{
		super(source);
		this.type = type;
		this.oldFile = oldFile;
		this.newFile = newFile;
	}
	
	public static enum FileTreeChangeType
	{
		FILE_CREATED,
		FILE_RENAMED,
		FILE_DELETED,
		DRIVE_ADDED,
		DRIVE_REMOVED
	}
}
