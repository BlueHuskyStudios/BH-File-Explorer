package org.bh.app.filexplorer.util;

import bht.tools.util.ArrayPP;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * HomeFolder, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 GPLv3 <hr/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2014-12-28 (1.0.0) - Kyli created HomeFolder
 * @since 2014-12-28
 */
public class HomeFolder extends BHFile
{
	private boolean isCacheDirty = true;
	private File[] cache;
	
	public HomeFolder()
	{
		super("/", "");
		isCacheDirty = true;
		cachedContent();
	}

	@Override
	public String[] list()
	{
		return list(null);
	}

	@Override
	public String[] list(FilenameFilter filter)
	{
		File[] list = listFiles(filter);
		String[] ret = new String[list.length];
		for (int i = 0; i < ret.length; i++)
			ret[i] = list[i].getAbsolutePath();
		return ret;
	}

	@Override
	public File[] listFiles()
	{
		return listFiles((FileFilter)null);
	}

	@Override
	public File[] listFiles(FileFilter filter)
	{
		ArrayPP<File> ret = new ArrayPP<>(cachedContent());
		if (filter != null)
			for (int i = 0; i < ret.length(); i++)
				if (!filter.accept(ret.get(i)))
					ret.remove(i);
		return ret.t;
	}

	@Override
	public File[] listFiles(FilenameFilter filter)
	{
		ArrayPP<File> ret = new ArrayPP<>(cachedContent());
		if (filter != null)
			for (int i = 0; i < ret.length(); i++)
			{
				File f = ret.get(i);
				String name = f.getName();
				if (!filter.accept(f, name))
					ret.remove(i);
			}
		return ret.t;
	}
	
	private File[] cachedContent()
	{
		if (!isCacheDirty)
			return cache;
		
		FileSystem fs = FileSystems.getDefault();
		ArrayPP<Path> paths = new ArrayPP<>(fs.getRootDirectories());
		cache = new File[paths.length()];
		for (int i = 0; i < cache.length; i++)
			cache[i] = paths.get(0).toFile();
		
		isCacheDirty = false;
		
		return cache;
	}
	
	@Override
	public long getFreeSpace()
	{
		long ret = 0;
		for (File root : cache)
			ret += root.getFreeSpace();
		return ret;
	}

	@Override
	public long getUsableSpace()
	{
		long ret = 0;
		for (File root : cache)
			ret += root.getUsableSpace();
		return ret;
	}

	@Override
	public long getTotalSpace()
	{
		long ret = 0;
		for (File root : cache)
			ret += root.getTotalSpace();
		return ret;
	}

	@Override public String toString() { return "Home"; }
	@Override public String getName() { return toString(); }
	@Override public String getAbsolutePath() { return toString(); }
	@Override public File getAbsoluteFile() { return this; }
	@Override public String getCanonicalPath() throws IOException { return toString(); }
	@Override public File getCanonicalFile() throws IOException { return this; }
	@Override public String getParent() { return null; }
	@Override public File getParentFile() { return null; }
	@Override public FileExtension getFileExtension() { return new FileExtension(); }
	@Override public String getPath() { return toString(); }
	@Override public boolean setReadOnly() { return false; }
	@Override public boolean setReadable(boolean readable, boolean ownerOnly) { return false; }
	@Override public boolean canRead() { return true; }
	@Override public boolean canWrite() { return false; }
	@Override public boolean canExecute() { return false; }
	@Override public boolean isDirectory() { return true; }
	@Override public boolean isFile() { return false; }
	@Override public boolean isHidden() { return false; }
}
