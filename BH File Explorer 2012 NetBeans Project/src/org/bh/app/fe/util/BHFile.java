package org.bh.app.fe.util;

import java.io.File;
import java.net.URI;

/**
 * BHFile, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-19
 */
public class BHFile extends File
{
	/**
	 * Calls {@link File#File(String)}
	 * @see File#File(String)
	 */
	public BHFile(String pathname)
	{
		super(pathname);
	}

	/**
	 * Calls {@link File#File(String,String)}
	 * @see File#File(String,String)
	 */
	public BHFile(String parent, String child)
	{
		super(parent, child);
	}

	/**
	 * Calls {@link File#File(File,String)}
	 * @see File#File(File,String)
	 */
	public BHFile(File parent, String child)
	{
		super(parent, child);
	}

	/**
	 * Calls {@link File#File(URI)}
	 * @see File#File(URI)
	 */
	public BHFile(URI uri)
	{
		super(uri);
	}
}
