package org.bh.app.filexplorer.lang;

import bht.tools.util.save.SaveableString;
import bht.tools.util.save.StateSaver;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bh.app.filexplorer.Main;
import org.bh.app.filexplorer.comps.struct.FileDisplay;

/**
 * Language, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 GPLv3 <hr/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 *		- 2014-12-29 (1.0.0) - Kyli created Language
 * @since 2014-12-29
 */
public class Language extends Properties
{
	/** Shown in dialogs when a file may be replaced by another */
	public static SaveableString replaceFileConfirmationText;
	/** Shown in the title of dialogs when a file may be replaced by another */
	public static SaveableString replaceFileConfirmationTitleText;

	private StateSaver stateSaver;
	
	public Language()
	{
		try
		{
			stateSaver = new StateSaver(Main.APP_NAME, "Language.properties", true);
			stateSaver.addSaveable(
				replaceFileConfirmationText = new SaveableString(
					"File of that name already exists. Do you want to overwrite it?",
					"dialog.confirm.rename.text"));
			stateSaver.addSaveable(
				replaceFileConfirmationTitleText = new SaveableString(
					"Overwrite file?",
					"dialog.confirm.rename.title.text"));
		}
		catch (IOException ex)
		{
			Logger.getLogger(Language.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	public static final String inventTabTitleFor(FileDisplay display)
	{
		return display.getCurrentDirectory().getName();
	}
}
