package org.bh.app.filexplorer.comps.struct;

import java.io.File;
import javax.swing.JComponent;

/**
 * FilePreviewPane, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 GPLv3 <hr/>
 * 
 * Displays a preview of the file. This may be a thumbnail of an image file, or a description about its contents, or any other
 * such relevant information.
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2014-12-27 (1.0.0) - Kyli created FilePreviewPane
 * @since 2014-12-27
 */
public abstract class FilePreviewPane extends JComponent
{
	protected File currentFile;
	public abstract void setCurrentFile(File fileToPreview);
	public abstract void refreshPreview();
}
