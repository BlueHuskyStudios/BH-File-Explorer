package org.bh.app.fe.events;

import java.util.EventListener;

public interface ViewChangeListener extends EventListener
{
	public void willNavigate(ViewChangeEvent evt);
	public void didNavigate(ViewChangeEvent evt);
}
