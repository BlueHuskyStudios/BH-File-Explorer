package org.bh.app.filexplorer.comps.def;

import bht.tools.util.ArrayPP;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JTabbedPane;
import org.bh.app.filexplorer.comps.struct.AddressBar;
import org.bh.app.filexplorer.comps.struct.BrowsingPane;
import org.bh.app.filexplorer.comps.struct.FileDisplay;

/**
 * DefaultBrowsingPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-24
 */
public class DefaultBrowsingPane extends BrowsingPane
{
	private final ArrayPP<FileDisplay> displays;
	
	public DefaultBrowsingPane(File... initialDirs)
	{
		displays = new ArrayPP<>(initialDirs.length);
		for (File dir : initialDirs)
			displays.add(new DefaultFileDisplay(dir));
		initGUI();
	}

	@Override
	public FileDisplay[] getFileDisplays()
	{
		return displays.toArray();
	}

	private JTabbedPane displaysTabbedPane;
	private AddressBar addressBar;
	private void initGUI()
	{
		setLayout(new BorderLayout());
		{
			displaysTabbedPane = new JTabbedPane();
			for (FileDisplay display : displays)
				displaysTabbedPane.addTab(inventTabTitleFor(display), display);
			add(displaysTabbedPane, BorderLayout.CENTER);
		}
		{
			addressBar = new DefaultAddressBar();
			add(addressBar, BorderLayout.NORTH);
		}
	}
	
	public static final String inventTabTitleFor(FileDisplay display)
	{
		return display.getCurrentDirectory().getName();
	}
}
