package org.bh.app.filexplorer.util;

import bht.tools.util.ArrayPP;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BHFile, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Simply adds convenience methods to File
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-19
 */
public class BHFile extends File
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	/**
	 * Calls {@link File#File(String)}
	 * @param pathname passed to {@link File#File(String)}
	 * @see File#File(String)
	 */
	public BHFile(String pathname)
	{
		super(pathname);
	}

	/**
	 * Calls {@link File#File(String,String)}
	 * @param parent passed to {@link File#File(String,String)}
	 * @param child passed to {@link File#File(String,String)}
	 * @see File#File(String,String)
	 */
	public BHFile(String parent, String child)
	{
		super(parent, child);
	}

	/**
	 * Calls {@link File#File(File,String)}
	 * @param parent passed to {@link File#File(File,String)}
	 * @param child passed to {@link File#File(File,String)}
	 * @see File#File(File,String)
	 */
	public BHFile(File parent, String child)
	{
		super(parent, child);
	}

	/**
	 * Calls {@link File#File(URI)}
	 * @param uri passed to {@link File#File(URI)}
	 * @see File#File(URI)
	 */
	public BHFile(URI uri)
	{
		super(uri);
	}

	public static FileExtension getFileExtension(File f)
	{
		Pattern extPattern = Pattern.compile("\\..+^");
		Matcher extMatcher = extPattern.matcher(f.getName());
		if (extMatcher.find())
			return new FileExtension(extMatcher.group());
		else
			return null;
	}
	
	public FileExtension getFileExtension()
	{
		return getFileExtension(this);
	}
	
	@SuppressWarnings({"BroadCatchBlock", "TooBroadCatch"})
	public static BHFile valueOf(File aFile)
	{
		if (aFile instanceof ZipFileFile)
			return (ZipFileFile)aFile;
		if (ZipFileFile.isZip(aFile))
		{
			try
			{
				return new ZipFileFile(aFile.toURI());
			}
			catch (Throwable t)
			{
				try
				{
					return new ZipFileFile(aFile.getPath());
				}
				catch (UnsupportedFileExtensionException ufee)
				{
					Logger.getGlobal().log(Level.INFO, "Guess I was wrong; it's not a ZIP file");
				}
			}
		}
		if (aFile instanceof BHFile)
			return (BHFile)aFile;
		try
		{
			return new BHFile(aFile.toURI());
		}
		catch (SecurityException e) // File#toURI() might throw this
		{
			return new BHFile(aFile.getPath());
		}
	}

	/**
	 * Attempts to rewrite
	 * @param newName
	 * @throws NewNameExistsException 
	 */
	public void rename(String newName) throws NewNameExistsException
	{
		if (!rename(newName, false))
			throw new NewNameExistsException();
	}

	public boolean rename(String newName, boolean replace)
	{
		try
		{
			File newFile = new File(getParent(), newName);
			
			if (!replace && newFile.exists())
				return false;
			
			Files.move(toPath(), newFile.toPath());
		}
		catch (IOException ex)
		{
			Logger.getLogger(BHFile.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
			return false;
		}
		return true;
	}
}
