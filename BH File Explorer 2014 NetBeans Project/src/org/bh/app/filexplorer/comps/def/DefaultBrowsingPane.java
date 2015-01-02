package org.bh.app.filexplorer.comps.def;

import bht.tools.util.ArrayPP;
import java.awt.BorderLayout;
import java.io.File;
import javax.swing.JTabbedPane;
import org.bh.app.filexplorer.comps.struct.AddressBar;
import org.bh.app.filexplorer.comps.struct.BrowsingPane;
import org.bh.app.filexplorer.comps.struct.FileDisplay;
import static org.bh.app.filexplorer.lang.Language.inventTabTitleFor;
import org.bh.app.filexplorer.util.DefaultFileDisplayFactory;
import org.bh.app.filexplorer.util.HomeFolder;

/**
 * DefaultBrowsingPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * Contains tabs containing {@link FileDisplay}s. If created by passing a {@link File}, they're {@link DefaultFileDisplay}s.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-24
 */
public class DefaultBrowsingPane extends BrowsingPane
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	private final ArrayPP<FileDisplay> displays;
	
	public DefaultBrowsingPane()
	{
		this(new FileDisplay[]{new DefaultFileDisplay(new HomeFolder())});
	}
	
	public DefaultBrowsingPane(File... initialDirs)
	{
		displays = new ArrayPP<>(initialDirs.length);
		for (File dir : initialDirs)
			displays.add(new DefaultFileDisplay(dir));
		initGUI();
	}
	
	public DefaultBrowsingPane(FileDisplay... initialDisplays)
	{
		displays = new ArrayPP<>(initialDisplays);
		initGUI();
	}

	private BHFETabbedPane displaysTabbedPane;
	private AddressBar addressBar;
	private void initGUI()
	{
		setLayout(new BorderLayout());
		{
			displaysTabbedPane = new BHFETabbedPane(new DefaultFileDisplayFactory());
			for (FileDisplay display : displays)
				displaysTabbedPane.addTab(inventTabTitleFor(display), display);
			add(displaysTabbedPane, BorderLayout.CENTER);
		}
		{
			addressBar = new DefaultAddressBar();
			add(addressBar, BorderLayout.NORTH);
		}
	}
	
	public void addDisplay(FileDisplay newFileDisplay)
	{
		displays.add(newFileDisplay);
	}
	
	public void addDisplay(File newFileToDisplay)
	{
		displays.add(new DefaultFileDisplay(newFileToDisplay));
	}

	//<editor-fold defaultstate="collapsed" desc="Overrides">
	@Override
	public FileDisplay[] getFileDisplays()
	{
		return displays.toArray();
	}
	//</editor-fold>
}
