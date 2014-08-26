package org.bh.app.filexplorer.comps.def;

import java.io.File;
import org.bh.app.filexplorer.comps.struct.AddressBar;

/**
 * DefaultAddressBar, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-24
 */
public class DefaultAddressBar extends AddressBar
{

	public DefaultAddressBar()
	{
		this(null);
	}
	
	public DefaultAddressBar(File initCurrentFile)
	{
		super(initCurrentFile);
	}
}
