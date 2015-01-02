package org.bh.app.filexplorer.comps.def;

import java.awt.GridLayout;
import java.io.File;
import javax.swing.JLabel;
import org.bh.app.filexplorer.comps.struct.FilePreviewPane;

/**
 * DefaultFilePreviewPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 GPLv3 <hr/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2014-12-27 (1.0.0) - Kyli created DefaultFilePreviewPane
 * @since 2014-12-27
 */
public class DefaultFilePreviewPane extends FilePreviewPane
{
	public DefaultFilePreviewPane()
	{
		this(null);
	}
	
	public DefaultFilePreviewPane(File initPreviewed)
	{
		currentFile = initPreviewed;
		initGUI();
	}
	
	private JLabel fileNameLabel;
	private void initGUI()
	{
		setLayout(new GridLayout(0, 1));
		{
			fileNameLabel = new JLabel();
			add(fileNameLabel);
		}
	}
	
	@Override
	public void setCurrentFile(File fileToPreview)
	{
		currentFile = fileToPreview;
		refreshPreview();
	}

	@Override
	public void refreshPreview()
	{
		fileNameLabel.setText(currentFile == null ? "..." : currentFile.getName());
	}

}
