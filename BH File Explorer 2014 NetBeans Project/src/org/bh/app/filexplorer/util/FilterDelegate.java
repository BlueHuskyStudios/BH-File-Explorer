package org.bh.app.filexplorer.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * FilterDelegate, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-20
 */
public class FilterDelegate
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	Object filter;
	
	public FilterDelegate(FileFilter fileFilter)
	{
		filter = fileFilter;
	}
	public FilterDelegate(FilenameFilter filenameFilter)
	{
		filter = filenameFilter;
	}
	
	public boolean accept(Object... o)
	{
		if (filter instanceof FileFilter)
		{
			try
			{
				return ((FileFilter) filter).accept((File) o[0]);
			}
			catch (ClassCastException e)
			{
				throw new IllegalArgumentException("filter is FileFilter; expected File, got " + getClasses(o), e);
			}
		}
		else if (filter instanceof FilenameFilter)
		{
			try
			{
				return ((FilenameFilter) filter).accept((File) o[0], (String) o[1]);
			}
			catch (ClassCastException e)
			{
				throw new IllegalArgumentException("filter is FilenameFilter; expected File and String, got " + getClasses(o), e);
			}
		}
		else
			throw new IllegalStateException("unknown filter specified. Expected FileFilter or FilenameFilter, got " + getClasses(filter));
	}

	private static String getClasses(Object... o)
	{
		if (o == null)
			return "null";
		switch (o.length)
		{
			case 0:
				return "empty " + o.getClass().toGenericString();
			case 1:
				return o[0].getClass().toGenericString();
			case 2:
				return o[0].getClass().toGenericString() + " and " + o[1].getClass().toGenericString();
			default:
				StringBuilder ret = new StringBuilder();
				for (int i = 0, l = o.length - 1; i < l; i++)
					ret.append(o[i].getClass().toGenericString()).append(", ");
				ret.append("and ").append(o[o.length - 1]);
				return ret.toString();
		}
	}
}
