package org.bh.app.filexplorer.util;

import bht.tools.util.ArrayPP;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ZipFileFile, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Designed as a way to treat a Zip file as File
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-19
 */
public class ZipFileFile extends BHFile
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	/**
	 * Calls {@link BHFile#BHFile(String)}
	 * @param pathname A pathname string
	 * @throws UnsupportedFileExtensionException if the given file is not a ZIP
	 * @see BHFile#BHFile(String)
	 */
	public ZipFileFile(String pathname) throws UnsupportedFileExtensionException
	{
		super(pathname);
		ensureZip();
	}

	/**
	 * Calls {@link File#File(String,String)}
	 * @throws UnsupportedFileExtensionException if the given file is not a ZIP
	 * @see File#File(String,String)
	 */
	public ZipFileFile(String parent, String child) throws UnsupportedFileExtensionException
	{
		super(parent, child);
		ensureZip();
	}

	/**
	 * Calls {@link File#File(File,String)}
	 * @throws UnsupportedFileExtensionException if the given file is not a ZIP
	 * @see File#File(File,String)
	 */
	public ZipFileFile(File parent, String child) throws UnsupportedFileExtensionException
	{
		super(parent, child);
		ensureZip();
	}

	/**
	 * Calls {@link File#File(URI)}
	 * @see File#File(URI)
	 */
	public ZipFileFile(URI uri) throws UnsupportedFileExtensionException
	{
		super(uri);
		ensureZip();
	}
	
	/** The default extension for a zipped file */
	public static final FileExtension ZIP_EXTENSION = new FileExtension("ZIP");
	/**
	 * A sample of common files that use zip technology:
	 * {@code JAR}
	 */
	public static final FileExtension[] ZIP_EXTENSIONS = {
		ZIP_EXTENSION,
		new FileExtension("JAR"),
		new FileExtension("DOCX"),
		new FileExtension("PPTX"),
		new FileExtension("XLSX")
	};

	private void ensureZip() throws UnsupportedFileExtensionException
	{
		if (!isZip(this))
			throw new UnsupportedFileExtensionException(ZIP_EXTENSIONS);
	}
	
	public ZipFile toZipFile() throws IOException
	{
		return new ZipFile(this);
	}

	/**
	 * Returns {@code true}
	 * @return {@code true}
	 */
	@Override
	public boolean isDirectory()
	{
		return true;
	}
	
	/**
	 * Returns {@code true}
	 * @return {@code true}
	 */
	@Override
	public boolean isFile()
	{
		return true;
	}

	@Override
	public String[] list()
	{
		return list(ACCEPT_ALL_FILTER);
	}

	@Override
	public String[] list(FilenameFilter filter)
	{
		File[] files = listFiles(filter);
		if (files == null)
			return null;
		String[] ret = new String[files.length];
		for (int i = 0; i < ret.length; i++)
			ret[i] = files[i].getName();
		return ret;
	}

	@Override
	public File[] listFiles()
	{
		return listFiles((FileFilter)ACCEPT_ALL_FILTER);
	}

	@Override
	public File[] listFiles(FileFilter filter)
	{
		return listFiles(new FilterDelegate(filter));
	}

	@Override
	public File[] listFiles(FilenameFilter filter)
	{
		return listFiles(new FilterDelegate(filter));
	}
	
	public File[] listFiles(FilterDelegate filter)
	{
		ArrayList<File> ret = new ArrayList<>();
		try
		{
			ZipFile zf = toZipFile();
			int numFiles = zf.size();
			Enumeration<? extends ZipEntry> zipEntries = zf.entries();
					
			for (int i = 0; i < numFiles; i++)
			{
				File f = new File(zipEntries.nextElement().getName());
				if (filter.accept(f, f.getName())) // the name is ignored if we're using a FileFilter
					ret.add(f);
			}
		}
		catch (IOException ex)
		{
			Logger.getLogger(ZipFileFile.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ret.toArray(new File[ret.size()]);
	}
	
	
	private static final AcceptAllFilter ACCEPT_ALL_FILTER = new AcceptAllFilter();

	public String getComment() throws IOException
	{
		return toZipFile().getComment();
	}

	/**
	 * @deprecated unimplemented
	 * @param string 
	 */
	public void setComment(String string)
	{
		// TODO: set comment
	}

	private static class AcceptAllFilter implements FileFilter, FilenameFilter
	{
		private AcceptAllFilter(){}

		@Override public boolean accept(File pathname) { return true; }
		@Override public boolean accept(File dir, String name) { return true; }
	}

	public static boolean isZip(File aFile)
	{
		return new ArrayPP<>(ZIP_EXTENSIONS).contains(BHFile.getFileExtension(aFile));
	}
}
