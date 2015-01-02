package org.bh.app.filexplorer.util;

import bht.tools.util.dynamics.ObjectFactory;
import org.bh.app.filexplorer.comps.def.DefaultFileDisplay;
import org.bh.app.filexplorer.comps.struct.FileDisplay;

/**
 * DefaultFileDisplayFactory, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2015 GPLv3 <hr/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2015-01-02 (1.0.0) - Kyli created DefaultFileDisplayFactory
 * @since 2015-01-02
 */
public class DefaultFileDisplayFactory implements ObjectFactory<FileDisplay>
{
	@Override
	public FileDisplay create()
	{
		return new DefaultFileDisplay(new HomeFolder());
	}
}
