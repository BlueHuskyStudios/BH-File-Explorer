package org.bh.app.filexplorer.comps.def;

import bht.tools.comps.BHLinkLabel;
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
public class NoFilePreviewPane extends FilePreviewPane
{
	private String noFileText, unsupportedFileText, openFileLinkText;

	public NoFilePreviewPane()
	{
		this(
			"Select a file to preview",
			"I don't know how to preview this file.",
			"Click here to try opening it.");
	}
	
	public NoFilePreviewPane(String initNoFileText, String initUnsupportedFileText, String initOpenFileLinkText)
	{
		noFileText = initNoFileText;
		unsupportedFileText = initUnsupportedFileText;
		openFileLinkText = initOpenFileLinkText;
		initGUI();
	}
	
	private JLabel noFileLabel;
	private BHLinkLabel openFileLabel;
	private void initGUI()
	{
		setLayout(new GridLayout(2, 1));
		{
			noFileLabel = new JLabel(noFileText);
			add(noFileLabel);
		}
		{
			openFileLabel = new BHLinkLabel(openFileLinkText, currentFile.toURI(), BHLinkLabel.DEF_BEHAVIOR);
			openFileLabel.setText(openFileLinkText);
			add(openFileLabel);
		}
	}
	
	public void setNoFileText(String newNoFileText)
	{
		noFileText = newNoFileText;
		refreshPreview();
	}
	
	public void setUnsupportedFileText(String newUnsupportedFileText)
	{
		unsupportedFileText = newUnsupportedFileText;
		refreshPreview();
	}
	
	public void setOpenFileLinkText(String newOpenFileLinkText)
	{
		openFileLinkText = newOpenFileLinkText;
		openFileLabel.setText(openFileLinkText);
		refreshPreview();
	}
	
	@Override
	public void setCurrentFile(File fileToPreview)
	{
		currentFile = fileToPreview;
		openFileLabel.setURI(currentFile == null ? null : currentFile.toURI());
		refreshPreview();
	}

	@Override
	public void refreshPreview()
	{
		noFileLabel.setText(currentFile == null ? noFileText : unsupportedFileText);
		openFileLabel.setVisible(currentFile != null);
	}
}
