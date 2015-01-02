package org.bh.app.filexplorer.comps.def;

import bht.tools.util.ArrayPP;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileFilter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
	private static final long serialVersionUID = 0x1_000_000L;
	

	public DefaultAddressBar()
	{
		this(null);
	}
	
	public DefaultAddressBar(File initCurrentFile)
	{
		super(initCurrentFile);
		initGUI();
	}

	private ArrayPP<AddressBarFolderView> path;
	private void initGUI()
	{
		setLayout(new GridLayout(1, 0, 8, 0));
		fillAddressBar();
		setMinimumSize(new Dimension(16, 16));
	}

	private void fillAddressBar()
	{
		if (path != null)
			for (AddressBarFolderView folder : path)
				remove(folder);
		
		path = new ArrayPP<>();
		if (currentFile != null)
		{
			path.prepend(new AddressBarFolderView(currentFile));
			File parent = currentFile;
			while ((parent = parent.getParentFile()) != null)
				path.prepend(new AddressBarFolderView(parent));
		}
		
		for (AddressBarFolderView folder : path)
			add(folder);
	}
	
	// TODO: clicking dropdown items must do something
	public static class AddressBarFolderView extends JComboBox<File>
	{
		private File here;
		public AddressBarFolderView(File initLocation)
		{
			here = initLocation;
			initGUI();
		}
		
		private void initGUI()
		{
			if (here == null)
				return;
			File[] kids = here.listFiles(new FileFilter()
			{
				@Override
				public boolean accept(File pathname)
				{
					return pathname.isDirectory();
				}
			});
			if (kids == null)
				kids = new File[1];
			else
				System.arraycopy(kids, 0, kids, 1, kids.length + 1);
			kids[0] = here;
			setModel(new DefaultComboBoxModel<>(kids));
		}
	}
}
