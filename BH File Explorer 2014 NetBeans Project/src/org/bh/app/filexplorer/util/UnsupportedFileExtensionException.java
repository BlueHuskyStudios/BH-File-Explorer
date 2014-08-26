package org.bh.app.filexplorer.util;

/**
 * UnsupportedFileExtensionException, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2013 GPLv3<HR/>
 * Signifies that a given file's extension is not supported.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-19
 */
public class UnsupportedFileExtensionException extends Exception
{
	FileExtension[] supportedExtensions, unsupportedExtensions;
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension expected)
	{
		this(new FileExtension[]{expected}, new FileExtension[]{});
	}
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension[] expected)
	{
		this(expected, new FileExtension[]{});
	}
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension expected, FileExtension actual)
	{
		this(new FileExtension[]{expected}, new FileExtension[]{actual});
	}
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension[] expected, FileExtension actual)
	{
		this(expected, new FileExtension[]{actual});
	}
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension expected, FileExtension[] actual)
	{
		this(new FileExtension[]{expected}, actual);
	}
	
	/**
	 * Creates a new instance of <code>UnsupportedFileExtensionException</code> with the given expected and actual file types
	 */
	public UnsupportedFileExtensionException(FileExtension[] expected, FileExtension[] actual)
	{
		super(makeDetailMessage(expected, actual));
		supportedExtensions = expected;
		unsupportedExtensions = actual;
	}
	
	private static String makeDetailMessage(FileExtension[] expected, FileExtension[] actual)
	{
		return
				makeDetailMessagePart(
						expected,
						"I don't know what I expected, but it wasn't ",
						"Expected ", ", but got ",
						"Expected these:", ", but got ") +
				makeDetailMessagePart(
						actual,
						"absolutely nothing",
						"", ".",
						"", ".")
		;
	}
	
	private static String makeDetailMessagePart(
			FileExtension[] extensions,
			String ifNone,
			String beforeOne, String afterOne,
			String beforeMore, String afterMore
	){
		StringBuilder ret = new StringBuilder();
		if (
			extensions == null
			|| extensions.length == 0
			|| (
				extensions.length == 1
				&& extensions[0] == null
			)
		)
			return ifNone;
		else
		{
			if (extensions.length == 1)
				ret
					.append(beforeOne)
					.append(extensions[0])
					.append(afterOne);
			else
			{
				ret.append(beforeMore);
				for(int i = 0, l = extensions.length - 1; i < l; i++)
					ret
						.append(' ')
						.append(extensions[i])
						.append(',');
				ret
					.append(" or ")
					.append(extensions[extensions.length - 1])
					.append(afterMore);
			}
		}
		return ret.toString();
	}
	
	public static void main(String[] args)
	{
		System.out.println(new UnsupportedFileExtensionException(CommonMIME_Extensions.ZIP_BASIC.getExts()));
		System.out.println(new UnsupportedFileExtensionException(CommonMIME_Extensions.ZIP_MANY.getExts()));
		System.out.println(new UnsupportedFileExtensionException(CommonMIME_Extensions.ZIP_BASIC.getExts(), CommonMIME_Extensions.ZIP_MANY.getExts()));
		System.out.println(new UnsupportedFileExtensionException(CommonMIME_Extensions.ZIP_MANY.getExts(), CommonMIME_Extensions.ZIP_BASIC.getExts()));
		System.out.println(new UnsupportedFileExtensionException(CommonMIME_Extensions.ZIP_MANY.getExts(), CommonMIME_Extensions.ZIP_MANY.getExts()));
		System.out.println(new UnsupportedFileExtensionException((FileExtension)null, CommonMIME_Extensions.ZIP_BASIC.getExts()));
		System.out.println(new UnsupportedFileExtensionException(new FileExtension[]{}, CommonMIME_Extensions.ZIP_BASIC.getExts()));
	}
}