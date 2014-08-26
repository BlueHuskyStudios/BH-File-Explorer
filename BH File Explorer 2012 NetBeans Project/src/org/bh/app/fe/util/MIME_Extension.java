package org.bh.app.fe.util;

import static bht.tools.util.Do.s;

/**
 * MIME_Extension, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * A bridge between {@link MIMEType} and {@link FileExtension}
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-20
 */
public class MIME_Extension
{
	private MIMEType mime;
	private FileExtension[] exts;

	public MIME_Extension(MIMEType initMimeType, FileExtension... initExtensions)
	{
		mime = initMimeType;
		exts = initExtensions;
	}

	public MIMEType getMime()
	{
		return mime;
	}

	public void setMime(MIMEType mime)
	{
		this.mime = mime;
	}

	public FileExtension[] getExts()
	{
		return exts;
	}

	public void setExts(FileExtension[] exts)
	{
		this.exts = exts;
	}

	/**
	 * Creates a string of the following format: The string value of the mime type, followed by a space, then a space-separated
	 * list of file extensions. For example: {@code text/html htm html}
	 * @return 
	 */
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder(s(mime));
		for (FileExtension fileExtension : exts)
			ret.append(' ').append(fileExtension.withoutFirstDot());
		return ret.toString();
	}
}
