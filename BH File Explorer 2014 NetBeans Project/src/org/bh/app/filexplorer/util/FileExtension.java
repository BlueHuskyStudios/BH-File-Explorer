package org.bh.app.filexplorer.util;

import bht.tools.util.ArrayPP;

/**
 * FileExtension, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Represents the extension of a file
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-19
 */
public class FileExtension extends ArrayPP<CharSequence> implements CharSequence
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	/**
	 * Creates a new {@code FileExtension} using the given {@link CharSequence}s. More than one can be specified for extensions
	 * like {@code .tar.gz}
	 * @param extension 
	 */
	public FileExtension(CharSequence... extension)
	{
		super(extension);
	}
	
	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		for(CharSequence cs : t)
			ret.append(".").append(cs);
		return ret.toString();
	}

	@Override
	public int length()
	{
		return toString().length();
	}

	@Override
	public char charAt(int index)
	{
		return toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end)
	{
		return toString().subSequence(start, end);
	}

	public String withoutFirstDot()
	{
		return toString().substring(1);
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;
		if (!(obj instanceof FileExtension)) return false;
		return equals((FileExtension)obj);
	}

	/**
	 * Returns {@code true} iff both the given {@link ArrayPP} contains only the same extensions as this {@link FileExtension},
	 * ignoring case. Don't worry about ambiguity with {@link #equals(FileExtension)}; if the other object is detected to be a
	 * {@link FileExtension}, it's cast and passed automatically.
	 * @param a the Array++ to compare against
	 * @return {@code true} iff both the given {@link ArrayPP} contains only the same extensions as this {@link FileExtension},
	 * ignoring case.
	 */
	@Override
	public boolean equals(ArrayPP<CharSequence> a)
	{
		if (a.length() != length())
			return false;
		else if (a instanceof FileExtension)
			return equals((FileExtension)a);
		for (int i = 0, l = length(); i < l; i++)
			if (!get(i).toString().equalsIgnoreCase(a.get(i).toString()))
				return false;
		return true;
	}
	
	/**
	 * Symantically the same as {@code other.toString().equalsIgnoreCase(this.toString())}
	 * @param other another {@link FileExtension} to compare
	 * @return true iff both contain the same extensions, ignoring case
	 */
	public boolean equals(FileExtension other)
	{
		return other.toString().equalsIgnoreCase(toString());
	}
}
