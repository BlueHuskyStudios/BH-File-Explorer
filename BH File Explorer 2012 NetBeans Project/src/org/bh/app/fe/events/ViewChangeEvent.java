package org.bh.app.fe.events;

import java.util.EventObject;

/**
 * ViewChangeEvent, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-20
 */
public class ViewChangeEvent extends EventObject
{
	ViewChangeType type;
	
	public ViewChangeEvent(Object source, ViewChangeType suggestion)
	{
		super(source);
		type = suggestion;
	}

	public ViewChangeType getSuggestion()
	{
		return type;
	}
}
