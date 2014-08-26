package org.bh.app.fe.comps;

import org.bh.app.fe.ExceptionHandler;
import org.bh.app.fe.Main;
import org.bh.app.fe.events.DisplayChangeListener;
import org.bh.app.fe.events.DisplayEvent;
import bht.tools.Constants;
import bht.tools.comps.BHFrame;
import bht.tools.comps.BHNavButtons;
import bht.tools.comps.BHTextField;
import bht.tools.comps.event.NavigationEvent;
import bht.tools.comps.event.NavigationEvent.NavigationState;
import bht.tools.comps.event.NavigationListener;
import bht.tools.misc.YesNoBox;
import bht.tools.util.ArrayPP;
import bht.tools.util.math.Numbers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import static org.bh.app.fe.Main.RES_DIR;
import org.bh.app.fe.events.ViewChangeEvent;
import org.bh.app.fe.events.ViewChangeListener;
import org.bh.app.fe.events.ViewChangeType;
import org.bh.app.fe.util.ZipFileFile;

/**
 * TableFileBrowser, made for BH File Explorer, is made by and copyrighted to Blue Husky Programming, ©2012.
 *
 * @author Supuhstar
 * @since Nov 20, 2011
 * @version 1.0.0
 */
public class TableFileBrowser extends JPanel
{
	private static final long serialVersionUID = 1L;
	//<editor-fold defaultstate="collapsed" desc="Objects">
	//<editor-fold defaultstate="collapsed" desc="Components">
	private BHNavButtons navButtons;
	private BHTextField urlField;
	private FileContextMenu filePopupMenu;
	private JButton goRefreshButton, upButton;//, backButton, forwardButton, histButton;
//  private ArrayPP<JMenuItem> histCompArray;
	private JPanel navPanel;
	private JPopupMenu histPopupMenu;
	private JScrollPane displayTableScrollPane;
	private JSplitPane mainSplitPane;
	private JTable displayTable;
	private PluginPane pluginPane;
	//</editor-fold>
	private boolean writing = false;
	public static final CharSequence[] COLUMN_NAMES = new CharSequence[]
	{
		"Icon", "Name", "Size", "Type"
	};
	public static final byte COL_ICON = 0, COL_NAME = 1, COL_SIZE = 2, COL_TYPE = 3;
	public static final Class[] COLUMN_CLASSES = new Class<?>[]
	{
		Icon.class, String.class, String.class, String.class
	};
	private ArrayPP<DisplayChangeListener> displayChangeListeners, displayRefreshListeners;
	private File currentFile;
	private ImageIcon refreshIcon, goIcon;
	public static final String HOME = "Home";
	//</editor-fold>
	private ArrayPP<ExceptionHandler> exHandles = new ArrayPP<>();
	private ArrayPP<PluginPane> previewers = new ArrayPP<>();

	@SuppressWarnings("OverridableMethodCallInConstructor")
	public TableFileBrowser(final File initSelect) throws FileNotFoundException, AccessDeniedException
	{
		currentFile = initSelect;
		displayChangeListeners = new ArrayPP<>();
		displayRefreshListeners = new ArrayPP<>();
		installPlugin(new ImagePreviewPane());
		initComponents();
		goTo(currentFile);
	}

	private void initComponents()
	{
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new BorderLayout());

		filePopupMenu = new FileContextMenu((File) null);
		filePopupMenu.setRenameActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writing = true;
				displayTable.editCellAt(displayTable.getSelectedRow(), COL_NAME);
				refresh();
				writing = false;
			}
		});
		filePopupMenu.setDeleteActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				writing = true;
				File[] sel = getSelectedFiles();
				if (sel == null || sel.length == 0)
					return;
				if (YesNoBox.bool("Are you sure you want to delete " + (sel.length == 1 ? sel[0].getName() : "the selected files") + "?", "Delete? - " + Main.TITLE, YesNoBox.ERROR_MES))
					for (File file
						 : sel)
						file.delete();
				refresh();
				writing = false;
			}
		});
		filePopupMenu.setOpenActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for (File f
					 : getSelectedFiles())
					try
					{
						Desktop.getDesktop().browse(f.toURI());
					}
					catch (IOException ex)
					{
						handleException(ex);
					}
			}
		});

    //<editor-fold defaultstate="collapsed" desc="Navigation">
		//<editor-fold defaultstate="collapsed" desc="navPanel">
		navPanel = new JPanel(new GridBagLayout());//new JToolBar();
//    navPanel.setOrientation(JToolBar.HORIZONTAL);

		//<editor-fold defaultstate="collapsed" desc="navButtons">
		navButtons = new BHNavButtons();
		navButtons.addNavigationListener(new NavigationListener()
		{
			@Override public void willNavigate(NavigationEvent evt)
			{
				try
				{
					prepareToDisplayFile((File) evt.getNewObject());
				}
				catch (AccessDeniedException | FileNotFoundException ex)
				{
					handleException(ex);
				}
			}

			@Override public void didNavigate(NavigationEvent evt)
			{
				displayPreparedFile();
			}
		});
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 0;
		navPanel.add(navButtons, gbc);
    //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="upButton">
		upButton = new JButton("▲");
		upButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					goTo(getCurrentDir().getParentFile());
				}
				catch (IOException ex)
				{
					handleException(ex);
				}
			}
		});
		upButton.setMargin(null);
		gbc.gridx++;
		gbc.insets = new Insets(4, 0, 4, 4);
		navPanel.add(upButton, gbc);
    //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="urlField">
		urlField = new BHTextField();
		fixURLText();
		urlField.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				fixNavButtons();
			}
		});
		urlField.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				fixNavButtons();
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				fixNavButtons();
			}
		});
		urlField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				goToTypedURI();
			}
		});
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx++;
		gbc.insets.left = 4;
		gbc.insets.right = 0;
		gbc.weightx = 1;
		navPanel.add(urlField, gbc);
    //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="goRefreshButton">
		goRefreshButton = new JButton("Refresh");
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				refreshIcon = new ImageIcon(getClass().getResource(RES_DIR + "/view-refresh 16.png"));
				goIcon = new ImageIcon(getClass().getResource(RES_DIR + "/go-jump 16.png"));
				fixNavButtons();
				try
				{
					refreshIcon = new ImageIcon(new URL("http://s.supuhstar.operaunite.com/s/content/icons/Nimbus/16x16/actions/view-refresh%2016.png"));
					goIcon = new ImageIcon(new URL("http://s.supuhstar.operaunite.com/s/content/icons/Nimbus/16x16/actions/go-jump.png"));
				}
				catch (MalformedURLException ex)
				{
					handleException(ex);
					Component c = getParent();
					final String message = "The programmer didn't know what (s)he was doing";
					if (c != null && c instanceof BHFrame)
						((BHFrame) c).alertOf(message, Constants.ERROR_MES);
					else
					{
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(c, message, ex.getClass().getSimpleName(), Constants.ERROR_MES);
					}
				}
				fixNavButtons();
			}
		}, "Fetch Refresh image...").start();
		goRefreshButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (String.valueOf(getCurrentDir()).equalsIgnoreCase(urlField.getText()))
					refresh();
				else
					goToTypedURI();
			}
		});
		gbc.gridx++;
		gbc.insets.left = 0;
		gbc.insets.right = 4;
		gbc.weightx = 0;
		navPanel.add(goRefreshButton, gbc);
    //</editor-fold>

		fixNavButtons();

		add(navPanel, BorderLayout.NORTH);
    //</editor-fold>
		//</editor-fold>
		//<editor-fold defaultstate="collapsed" desc="mainSplitPane">
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		mainSplitPane.setResizeWeight(1);
		mainSplitPane.setContinuousLayout(true);
		mainSplitPane.setBorder(null);
		mainSplitPane.setOneTouchExpandable(true);
		//<editor-fold defaultstate="collapsed" desc="displayTable">
		displayTable = new JTable();
		displayTable.setAutoCreateRowSorter(true);
		displayTable.setComponentPopupMenu(filePopupMenu);
		displayTable.setInheritsPopupMenu(true);
		displayTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				try
				{
					fixFilePopupMenu();
					File[] fs = getSelectedFiles();

					p:
					for (PluginPane pp
						 : previewers)
						for (int i = 0;
							 i < fs.length;
							 i++)
							if (pp.respondsTo(fs[i]))
							{
								pluginPane = pp;
								add(pluginPane, BorderLayout.EAST);
								pp.alertOfDisplay(fs);
								repaint();
								break p;
							}

					if (e.getClickCount() > 1)
						if (canGoTo(fs[0]))
							goTo(fs[0]);
						else
							open(fs[0]);
				}
				catch (IOException ex)
				{
					handleException(ex);
				}
			}
		});
		displayTable.setDefaultRenderer(Component.class, new TableCellRenderer()
								{

									@Override
									public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
									{
										if (value instanceof Component)
											return (Component) value;
										return new DefaultTableCellRenderer();
									}
		});
		displayTableScrollPane = new JScrollPane(displayTable);
		mainSplitPane.setLeftComponent(displayTableScrollPane);
    //</editor-fold>

		//<editor-fold defaultstate="collapsed" desc="pluginPane">
		pluginPane = new PluginPane();
		mainSplitPane.setRightComponent(pluginPane);
		//</editor-fold>
		add(mainSplitPane, BorderLayout.CENTER);
		//</editor-fold>
	}

	public void fixFilePopupMenu()
	{
		filePopupMenu.setTarget(getSelectedFiles());
	}

	public void fixNavButtons()
	{
//    navButtons.fixNavButtons();

		upButton.setEnabled(getCurrentDir() != null);

		if ((refreshIcon != null && refreshIcon.getIconHeight() + refreshIcon.getIconWidth() >= 2)
			&& (goIcon != null))
			goRefreshButton.setIcon(urlField.getText().equalsIgnoreCase(getCurrentDirName()) ? refreshIcon : goIcon);
		if (goRefreshButton.getIcon() != null)
			goRefreshButton.setText(null);
		else
			goRefreshButton.setText(urlField.getText().equalsIgnoreCase(getCurrentDirName()) ? "Refresh" : "Go");
	}

	public void fixURLText()
	{
		urlField.setText(currentFile == null ? HOME : currentFile.toString());
	}

	public File[] getSelectedFiles()
	{
		File[] f = new File[displayTable.getSelectedRowCount()];
		int[] rows = displayTable.getSelectedRows();
		String path;
		for (int i = 0;
			 i < f.length;
			 i++)
			f[i] = new File((currentFile == null ? "" : (path = currentFile.getPath()).endsWith(File.separator) ? path
														: path + File.separator) + displayTable.getValueAt(rows[i], COL_NAME));
		return f;
	}

	/**
	 * Displays the given file in the display table
	 *
	 * @param f the file to be displayed
	 * @throws FileNotFoundException if the file does not exist
	 * @throws AccessDeniedException if the method cannot gain access to the file
	 */
	public void display(final File f) throws FileNotFoundException, AccessDeniedException
	{
		prepareToDisplayFile(f);
		displayPreparedFile();
	}

	public void goToTypedURI()
	{
		try
		{
			open(urlField.isEmpty() || urlField.getText().equalsIgnoreCase(HOME) ? null : new File(urlField.getText()));
		}
		catch (FileNotFoundException ex)
		{
//      Main.log.logThrowable(ex, "TableFileBrowser#displayTypedURL()");
			handleException(new java.net.URISyntaxException(urlField.getText(), "File does not exist"));
		}
	}

	public void open(File f) throws FileNotFoundException
	{
		try
		{
			if (canGoTo(f))
			{
				goTo(f);
				return;
			}
			Desktop.getDesktop().browse(f.toURI());
		}
		catch (FileNotFoundException ex)
		{
			throw ex;
		}
		catch (IOException ex)
		{
			handleException(ex);
		}
	}

	public File getCurrentDir()
	{
		return currentFile;
	}

	public String getCurrentDirName()
	{
		return currentFile == null ? HOME : currentFile.getPath().matches(".\\:\\\\") ? currentFile.getPath().charAt(0) + " Drive" : currentFile.getName();
	}

	private void handleException(Throwable ex)
	{
		for (ExceptionHandler eh
			 : exHandles)
			eh.exceptionThrown(ex);
	}

	public void addExceptionHandler(ExceptionHandler eh)
	{
		exHandles.add(eh);
	}

	public void removeExceptionHandler(ExceptionHandler eh)
	{
		exHandles.remove(eh, true);
	}

	private void goTo(File f) throws FileNotFoundException, AccessDeniedException
	{
		navButtons.getHistory().add(f);
		navButtons.fixNavButtons();
		display(f);
	}

	//<editor-fold defaultstate="collapsed" desc="Directory Display">
	private void fireDirectoryWillDisplay(DisplayEvent de)
	{
		for (DisplayChangeListener dcl
			 : displayChangeListeners)
			dcl.displayWillChange(de);
	}

	private void fireDirectoryDisplayed(DisplayEvent de)
	{
		for (DisplayChangeListener dcl
			 : displayChangeListeners)
			dcl.displayDidChange(de);
	}

	public void addDisplayChangeListener(DisplayChangeListener dcl)
	{
		displayChangeListeners.add(dcl);
	}

	public void removeDisplayChangeListener(DisplayChangeListener dcl)
	{
		displayChangeListeners.remove(dcl, false);
	}
	//</editor-fold>

	private void displayPreparedFile()
	{
		final boolean IS_HOME = currentFile == null;

		fixURLText();
		fixNavButtons();

		ZipFile zf = null;
		if (!IS_HOME)
			try
			{
				zf = new ZipFile(currentFile);
			}
			catch (IOException e)
			{
			}

		final ArrayPP files = IS_HOME ? null : new ArrayPP<>(
				currentFile.isDirectory() ? currentFile.listFiles()
				: (zf == null ? currentFile.getParentFile().listFiles() : null));

		final boolean IS_ZIP = zf != null;

		if (IS_ZIP)
		{
			files.clear();
			Enumeration<? extends ZipEntry> e = zf.entries();
			while (e.hasMoreElements())
				files.add(e.nextElement());
		}

		final ArrayPP<File> roots = new ArrayPP<>(java.io.File.listRoots());

		displayTable.setModel(
			new FileDisplayTableModel(
				IS_HOME
					? roots
					: files,
				IS_HOME
					? DirectoryType.DIR_TYPE_HOME
					: IS_ZIP
						? DirectoryType.DIR_TYPE_ZIP
						: DirectoryType.DIR_TYPE_PLAIN
				)
			)
		;

		if (!IS_HOME && currentFile.isFile())
			displayTable.changeSelection(files.getIndexOf(currentFile), COL_NAME, false, false);

		fireDirectoryDisplayed(new DisplayEvent(this, currentFile));
	}

	private void prepareToDisplayFile(File f) throws AccessDeniedException, FileNotFoundException
	{
		fireDirectoryWillDisplay(new DisplayEvent(this, f));
		final boolean home = f == null;
		try
		{
			if (!home && !f.exists())
				throw new FileNotFoundException("No such file: " + f);

			if (!home && !f.canRead())
				throw new AccessDeniedException(f.toString(), null, "Access denied to " + f);
		}
		catch (SecurityException ex)
		{
			throw (AccessDeniedException) new AccessDeniedException(f.toString(), null, "Access denied to " + f).initCause(ex);
		}

		currentFile = f;
	}

	//<editor-fold defaultstate="collapsed" desc="Refreshments">
	public void refresh()
	{
		displayPreparedFile();

		fireDirectoryRefreshed(new DisplayEvent(this, currentFile));
	}

	private void fireDirectoryRefreshed(DisplayEvent de)
	{
		for (DisplayChangeListener dcl
			 : displayRefreshListeners)
			dcl.displayWillChange(de);
	}

	public void addDisplayRefreshListener(DisplayChangeListener dcl)
	{
		displayRefreshListeners.add(dcl);
	}

	public void removeDisplayRefreshListener(DisplayChangeListener dcl)
	{
		displayRefreshListeners.remove(dcl, false);
	}
  //</editor-fold>

	public void installPlugin(PluginPane pp)
	{
		previewers.addWithoutDuplicates(pp);
	}

	public boolean canGoTo(File f)
	{
		try
		{
			System.out.println(new java.util.zip.ZipFile(f));
			return true;
		}
		catch (Throwable t)
		{
		}

		return (f == null || f.isDirectory());
	}

	
	
	
	
	
	
	/**
	 * FileDisplayTableModel, made for BH File Explorer NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0
	 * BY-SA<HR/>
	 *
	 * @author Kyli of Blue Husky Programming
	 * @version 1.0.0
	 * @since 2014-08-19
	 */
	public static class FileDisplayTableModel implements TableModel
	{

		ArrayPP<TableModelListener> tableModelListeners = new ArrayPP<>();
		ArrayPP<ViewChangeListener> viewChangeListeners = new ArrayPP<>();
		ArrayPP<File> files = new ArrayPP<>();
		private Object[][] vals;
		private DirectoryType type;

		public FileDisplayTableModel(ArrayPP<File> initFiles, DirectoryType initType)
		{
			files = initFiles;
			vals = new Object[initFiles.length()][COLUMN_NAMES.length];
			type = initType;
		}
		
		public boolean isHome() { return type == DirectoryType.DIR_TYPE_HOME; }
		public boolean isZip()  { return type == DirectoryType.DIR_TYPE_ZIP; }

		@Override
		public int getRowCount()
		{
			return vals.length;
		}

		@Override
		public int getColumnCount()
		{
			return COLUMN_NAMES.length;
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			if (isHome() && columnIndex == COL_SIZE)
				return "Space";
			if (isZip() && columnIndex == COL_TYPE)
				return "Comment";
			return COLUMN_NAMES[columnIndex].toString();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			if (isHome() && columnIndex == COL_SIZE)
				return JProgressBar.class;
			return COLUMN_CLASSES[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return
				!isHome() // if we're in the home, we can never edit anything, as all values are roots.
//				&& writing // we must be currently editing a cell
				&& columnIndex == COL_NAME
				&& (isZip()
					? false
					: files.get(rowIndex).canWrite()
				);
		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex)
		{
			try
			{
				if (vals[rowIndex][columnIndex] == null)
				{
					final Object SEL =
						isZip()
							? files.get(rowIndex)
							: files.get(rowIndex)
					;
					switch (columnIndex)
					{
						//<editor-fold defaultstate="collapsed" desc="Icon">
						case COL_ICON:
							//if (isZip()) return vals[rowIndex][columnIndex] = null;
							return vals[rowIndex][columnIndex] = FileSystemView.getFileSystemView().getSystemIcon((File) SEL);
						//</editor-fold>
						//<editor-fold defaultstate="collapsed" desc="Name">
						case COL_NAME:
							if (isZip()) return vals[rowIndex][columnIndex] = files.get(rowIndex).getName();
							return vals[rowIndex][columnIndex] = isHome() ? SEL.toString() : ((File) SEL).getName();
						//</editor-fold>
						//<editor-fold defaultstate="collapsed" desc="Size">
						case COL_SIZE:
							if (isZip())
							{
								ZipFileFile zff = ((ZipFileFile) files.get(rowIndex));
								return vals[rowIndex][columnIndex] = zff.isDirectory() ? null : Numbers.toPrettyBytes(zff.length());
							}

							if (((File) SEL).isDirectory())
								if (isHome())
								{
									vals[rowIndex][columnIndex] = new JProgressBar(JProgressBar.HORIZONTAL, 0, (int) (((File) SEL).getTotalSpace() / 1000));
									((JProgressBar) vals[rowIndex][columnIndex]).setValue((int) (((File) SEL).getFreeSpace() / 1000));
									double p = ((JProgressBar) vals[rowIndex][columnIndex]).getPercentComplete();
									if (p > 0.75)
										((Component) vals[rowIndex][columnIndex]).setForeground(p > 0.90 ? Color.RED : Color.YELLOW);
									((Component) vals[rowIndex][columnIndex]).setBackground(null);
								}
								else
									vals[rowIndex][columnIndex] = '-';
							else
								vals[rowIndex][columnIndex] = Numbers.toPrettyBytes(((File) SEL).length());
							return vals[rowIndex][columnIndex];
						//</editor-fold>
						//<editor-fold defaultstate="collapsed" desc="Type">
						case COL_TYPE:
							if (isZip())
								return vals[rowIndex][columnIndex] = ((ZipFileFile) files.get(rowIndex)).getComment();

							return vals[rowIndex][columnIndex] = javax.swing.filechooser.FileSystemView.getFileSystemView().getSystemTypeDescription((File) SEL);
						//</editor-fold>
						//<editor-fold defaultstate="collapsed" desc="Undefined">
						default:
							System.err.println("[ERROR] Tried to get value for an undefined column (" + columnIndex + ")");
							return "[ERROR] Undefined column (" + columnIndex + ")";
						//</editor-fold>
					}
				}
				return vals[rowIndex][columnIndex];
			}
			catch (Throwable t)
			{
				return "[ERROR] Could not read";
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		{
			ViewChangeEvent evt = new ViewChangeEvent(this, ViewChangeType.REFRESH);
			for (ViewChangeListener navListener : viewChangeListeners)
				if (navListener != null)
					navListener.willNavigate(evt);
			
			if (isHome())
				return;

			if (isZip())
				switch (columnIndex)
				{
					case COL_TYPE:
						((ZipFileFile) files.get(rowIndex)).setComment(aValue == null ? null : String.valueOf(aValue));
						break;
					default:
						throw new AssertionError();
				}
			else
				switch (columnIndex)
				{
					case COL_NAME:
						System.out.println("Renaming \"" + files.get(rowIndex) + "\" to \"" + files.get(rowIndex).getParent() + File.separator + String.valueOf(aValue) + "\"");
						files.get(rowIndex).renameTo(new java.io.File(files.get(rowIndex).getParent() + File.separator + String.valueOf(aValue)));
						break;
					default:
						Logger.getGlobal().log(Level.WARNING, "[ERROR] No renaming method defined for column {0}", columnIndex);
				}
			for (ViewChangeListener viewChangeListener : viewChangeListeners)
				if (viewChangeListener != null)
					viewChangeListener.didNavigate(evt);
		}

		@Override public void addTableModelListener(TableModelListener newTCL){tableModelListeners.add(newTCL);}
		@Override public void removeTableModelListener(TableModelListener oldTCL){tableModelListeners.remove(oldTCL, false);}
		public ArrayPP<ViewChangeListener> add(ViewChangeListener newVCL){return viewChangeListeners.add(newVCL);}
		public ArrayPP<ViewChangeListener> remove(ViewChangeListener oldVCL){return viewChangeListeners.remove(oldVCL, false);}
	}
	
	public static enum DirectoryType
	{
		DIR_TYPE_PLAIN,
		DIR_TYPE_HOME,
		DIR_TYPE_ZIP
	}
}
