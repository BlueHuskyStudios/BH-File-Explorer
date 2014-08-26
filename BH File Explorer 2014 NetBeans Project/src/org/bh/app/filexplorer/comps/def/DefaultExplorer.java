package org.bh.app.filexplorer.comps.def;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import org.bh.app.filexplorer.comps.struct.AddressBar;
import org.bh.app.filexplorer.comps.struct.BrowsingPane;
import org.bh.app.filexplorer.comps.struct.Explorer;

/**
 * DefaultExplorer, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-23
 */
public class DefaultExplorer extends Explorer
{
	public DefaultExplorer()
	{
		initGUI();
	}
	
	//<editor-fold defaultstate="collapsed" desc="GUI Objects">
	private BrowsingPane browsingPane;
	//</editor-fold>
	private void initGUI()
	{
		//<editor-fold defaultstate="collapsed" desc="Explorer layout">
		{
			setLayout(new BorderLayout());
		}
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="Browsing Pane">
		{
			browsingPane = new DefaultBrowsingPane();
			add(browsingPane);
		}
		//</editor-fold>
		
	}

	@Override
	public BrowsingPane getBrowsingPane()
	{
		return browsingPane;
	}
}
