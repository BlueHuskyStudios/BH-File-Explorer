package org.bh.app.filexplorer.comps.def;

import bht.tools.util.ArrayPP;
import bht.tools.util.dynamics.ObjectFactory;
import java.awt.Component;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.bh.app.filexplorer.comps.struct.FileDisplay;
import static org.bh.app.filexplorer.lang.Language.inventTabTitleFor;

/**
 * BHFETabbedPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2015 GPLv3 <hr/>
 * 
 * A custom JTabbedPane for BH File Explorer
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2015-01-01 (1.0.0) - Kyli created BHFETabbedPane
 * @since 2015-01-01
 */
public class BHFETabbedPane extends JTabbedPane implements ChangeListener
{
	private ObjectFactory<FileDisplay> displayFactory;

	/**
	 * Creates a new {@link BHFETabbedPane} with the given default new tab.
	 * 
	 * @param initDisplayFactory The factory whose {@link ObjectFactory#create()} method will be called to add new tabs
	 */
	public BHFETabbedPane(ObjectFactory<FileDisplay> initDisplayFactory)
	{
		super.addChangeListener(this);
		displayFactory = initDisplayFactory;
	}

	
	boolean stateChangeIgnoreFlag = false;
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (stateChangeIgnoreFlag)
			return;
		stateChangeIgnoreFlag = true;
		
		Component comp;
		BHFENewTab newTab = null;
		
		// Guarantee last tab is New Tab
		if (!(getComponentAt(getTabCount() - 1) instanceof BHFENewTab)) // if the last item is not a new tab
		{
			for (int i = 0, l = getTabCount() - 1; i < l; i++) // remove all new tabs and remember the last encountered
			{
				comp = getComponentAt(i);
				if (comp instanceof BHFENewTab)
				{
					newTab = (BHFENewTab)comp;
					removeTabAt(i);
					break;
				}
			}
			addTab("+", // put a new tab at the end
				newTab == null // if we didn't fiund a pre-existing one before
					? new BHFENewTab() // make a new one
					: newTab // else, use the one we found
			);
		}
		
		
		
		// Guarantee New Tab is never selected
		comp = getSelectedComponent();
		if (comp != null && comp instanceof BHFENewTab) // If the new tab tab is selected
		{
			newTab = (BHFENewTab)comp;
			int index = getSelectedIndex();
			removeTabAt(index);
			comp = displayFactory.create();
			addTab(inventTabTitleFor((FileDisplay)comp), comp);
			addTab("+", newTab);
			setSelectedIndex(index);
		}
		stateChangeIgnoreFlag = false;
	}

	private static class BHFENewTab extends Component
	{
		public BHFENewTab()
		{
		}
	}
}
