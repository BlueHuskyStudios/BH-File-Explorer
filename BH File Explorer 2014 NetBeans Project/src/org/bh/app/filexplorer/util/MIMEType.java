package org.bh.app.filexplorer.util;

/**
 * MIMEType, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-20
 */
public class MIMEType
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	public static final String
		STANDARDS_TYPE_APPLICATION = "application",
		STANDARDS_TYPE_AUDIO       = "audio",
		STANDARDS_TYPE_IMAGE       = "image",
		STANDARDS_TYPE_TEXT        = "text"
	;
	
	String topLevel, typeName, tree[], subtypeName, suffix, parameters[];

	public MIMEType(String topLevel, String typeName, String[] tree, String subtypeName, String suffix, String[] parameters)
	{
		this.topLevel = topLevel;
		this.typeName = typeName;
		this.tree = tree;
		this.subtypeName = subtypeName;
		this.suffix = suffix;
		this.parameters = parameters;
	}
	
	public MIMEType(String typeName, String subtypeName, String[] parameters)
	{
		this(null, typeName, null, subtypeName, null, parameters);
	}
	
	public MIMEType(String typeName, String subtypeName, String suffix)
	{
		this(null, typeName, null, subtypeName, suffix, null);
	}
	
	public MIMEType(String typeName, String subtypeName)
	{
		this(typeName, subtypeName, (String)null);
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		if (topLevel != null && !topLevel.isEmpty())
			ret.append(topLevel);
		ret.append(typeName).append('/');
		if (tree != null && tree.length != 0)
			for (String branch : tree)
				ret.append(branch).append('.');
		if (suffix != null && !suffix.isEmpty())
			ret.append('+').append(suffix);
		if (parameters != null && parameters.length != 0)
			for (String parameter : parameters)
				ret.append(';').append(parameter);
		return ret.toString();
	}
}
