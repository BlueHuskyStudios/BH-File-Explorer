package org.bh.app.filexplorer.evt;

import bht.tools.comps.event.NavigationEvent;
import bht.tools.comps.event.NavigationEvent.NavigationState;
import java.io.File;
import java.io.Serializable;
import java.util.EventObject;

/**
 * FileTreeChangeEvent, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-09-04
 */
public class FileTreeChangeEvent extends EventObject implements Serializable
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	public final FileTreeChangeType TYPE;
	public final File OLD_FILE, NEW_FILE;

	public FileTreeChangeEvent(Object source,
							   FileTreeChangeType type,
							   File oldFile,
							   File newFile)
	{
		super(source);
		this.TYPE = type;
		this.OLD_FILE = oldFile;
		this.NEW_FILE = newFile;
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
