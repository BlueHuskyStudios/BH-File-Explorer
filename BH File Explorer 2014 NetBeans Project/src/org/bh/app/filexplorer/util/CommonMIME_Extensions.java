package org.bh.app.filexplorer.util;

public interface CommonMIME_Extensions
{
	/**
	 * Provides a MIME Type and file extension for basic ZIP files
	 * 
	 * @see CommonMIMETypes#APP_ZIP
	 * @see ZipFileFile#ZIP_EXTENSION
	 */
	public static MIME_Extension ZIP_BASIC = new MIME_Extension(CommonMIMETypes.APP_ZIP, ZipFileFile.ZIP_EXTENSION);
	
	/**
	 * Provides a MIME Type and file extensions for many common ZIP files
	 * 
	 * @see CommonMIMETypes#APP_ZIP
	 * @see ZipFileFile#ZIP_EXTENSIONS
	 */
	public static MIME_Extension ZIP_MANY = new MIME_Extension(CommonMIMETypes.APP_ZIP, ZipFileFile.ZIP_EXTENSIONS);
}
