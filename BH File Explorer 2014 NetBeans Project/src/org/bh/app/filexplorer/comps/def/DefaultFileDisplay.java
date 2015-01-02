package org.bh.app.filexplorer.comps.def;

import bht.tools.util.ArrayPP;
import static bht.tools.util.Do.s;
import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.Enumeration;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.bh.app.filexplorer.comps.struct.FileDisplay;
import org.bh.app.filexplorer.lang.Language;
import org.bh.app.filexplorer.util.BHFile;
import org.bh.app.filexplorer.util.HomeFolder;
import org.bh.app.filexplorer.util.NewNameExistsException;

/**
 * DefaultFileDisplay, made for BH File Explorer 2014 NetBeans Project, is copyright Blue Husky Programming ©2014 CC 3.0 BY-SA<HR/>
 * 
 * @author Kyli of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-08-24
 */
public class DefaultFileDisplay extends FileDisplay
{
	private static final long serialVersionUID = 0x1_000_000L;
	
	private BHFile dir;

	public DefaultFileDisplay(File dir)
	{
		this.dir = BHFile.valueOf(dir);
		initGUI();
	}
	
	private JTable display;
	private void initGUI()
	{
		setLayout(new BorderLayout());
		
		{
			FileDisplayTableColumns cm = new FileDisplayTableColumns();
			display = new JTable(new FileDisplayTableModel(dir), cm);
			display.setAutoCreateColumnsFromModel(true);
			display.setAutoCreateRowSorter(true);
			display.setTableHeader(new JTableHeader(cm));
			add(display, BorderLayout.CENTER);
		}
	}
	
	@Override
	public File[] getDisplayedFiles()
	{
		return dir.listFiles();
	}

	@Override
	public void displayDirectory(File newDirectory)
	{
		dir = BHFile.valueOf(newDirectory);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Expected directory, got " + newDirectory);
		validate();
	}

	@Override
	public File getCurrentDirectory()
	{
		return dir;
	}

	
	
	private static class FileDisplayTableModel implements TableModel
	{
		private ArrayPP<TableModelListener> tmls = new ArrayPP<>();
		private File dir, kids[];
		public FileDisplayTableModel(File initDirectoryToDisplay)
		{
			dir = initDirectoryToDisplay;
			kids = dir.listFiles();
		}
		
		private ColumnData columnData(int index)
		{
			return columnDatatype(index).DATA;
		}
		
		private FileDisplayTableColumns.ColumnDatatype columnDatatype(int index)
		{
			return FileDisplayTableColumns.datatypeForName(getColumnName(index));
		}

		@Override
		public int getRowCount()
		{
			return kids.length;
		}

		@Override
		public int getColumnCount()
		{
			return FileDisplayTableColumns.NUM_COLS;
		}

		@Override
		public String getColumnName(int columnIndex)
		{
			return FileDisplayTableColumns.ColumnDatatype.values()[columnIndex].DATA.getName();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			return columnData(columnIndex).getDataClass();
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex)
		{
			return false;/*
				!(dir instanceof HomeFolder) &&
				kids[rowIndex].canWrite() &&
				columnData(columnIndex).isDefaultEditable();*/
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			File row = kids[rowIndex];
			Object ret = FileDisplayTableColumns.extractData(row, columnDatatype(columnIndex));
			return ret;
		}

		@Override
		public void setValueAt(Object newValue, int rowIndex, int columnIndex)
		{
			File row = kids[rowIndex];
			FileDisplayTableColumns.changeData(
				row,
				columnDatatype(columnIndex),
				newValue,
				null
			);
		}

		@Override
		public void addTableModelListener(TableModelListener l)
		{
			tmls.add(l);
		}

		@Override
		public void removeTableModelListener(TableModelListener l)
		{
			tmls.remove(l, false);
		}
	}

	public static class ColumnData
	{
		private String name;
		private Class dataClass;
		private int defaultPosition;
		private boolean defaultEditable;

		public ColumnData(String name, Class dataClass, int defaultPosition, boolean defaultEditable)
		{
			this.name = name;
			this.dataClass = dataClass;
			this.defaultPosition = defaultPosition;
			this.defaultEditable = defaultEditable;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public Class getDataClass()
		{
			return dataClass;
		}

		public void setDataClass(Class dataClass)
		{
			this.dataClass = dataClass;
		}

		public int getDefaultPosition()
		{
			return defaultPosition;
		}

		public void setDefaultPosition(int defaultPosition)
		{
			this.defaultPosition = defaultPosition;
		}

		public boolean isDefaultEditable()
		{
			return defaultEditable;
		}

		public void setDefaultEditable(boolean defaultEditable)
		{
			this.defaultEditable = defaultEditable;
		}
	}
	
	private static class FileDisplayTableColumns implements TableColumnModel
	{
		public static final ColumnData COL_NAME = new ColumnData("Name", String.class, 0, true);
		
		public static enum ColumnDatatype
		{
			NAME(COL_NAME)
			;
			
			public final ColumnData DATA;
			private ColumnDatatype(ColumnData initData)
			{
				DATA = initData;
			}
		}
		public static final int NUM_COLS = ColumnDatatype.values().length;
		
		public static ColumnData dataForName(String name)
		{
			ColumnDatatype datatype = datatypeForName(name);
			return datatype == null ? null : datatype.DATA;
		}
		
		public static ColumnDatatype datatypeForName(String name)
		{
			for (ColumnDatatype datatype : ColumnDatatype.values())
				if (datatype.DATA.getName().equalsIgnoreCase(name))
					return datatype;
			return null;
		}
		
		public static Object extractData(File file, ColumnDatatype data)
		{
			switch (data)
			{
				case NAME:
					String name = file.getName();
					if (name == null || name.isEmpty())
					{
						File parent = file.getParentFile();
						if (parent == null)
							return file.getPath();
						return parent.getPath();
					}
					return name;
			}
			return null;
		}
		
		private static void changeData(File file, ColumnDatatype datatype, Object newValue, Component parent)
		{
			BHFile bhfile = BHFile.valueOf(file);
			switch (datatype)
			{
				case NAME:
					try
					{
						bhfile.rename(s(newValue));
					}
					catch (NewNameExistsException ex)
					{
						if (
							JOptionPane.showConfirmDialog(
								parent,
								Language.replaceFileConfirmationText,
								Language.replaceFileConfirmationTitleText.toString(),
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE
							)
							== JOptionPane.YES_OPTION)
							bhfile.rename(s(newValue), true);
					}
					break;
			}
		}
		
		
		
		private final ArrayPP<TableColumn> tableColumns = new ArrayPP<>();
		private final ArrayPP<TableColumnModelListener> tcmls = new ArrayPP<>();
		private int margin = 0;
		private boolean columnsCanBeSelected = true;
		private int[] selectedColumns;
		private ListSelectionModel listSelectionModel;
		private final ListSelectionListener listSelectionListener;

		public FileDisplayTableColumns()
		{
			for (ColumnDatatype columnDatatype : FileDisplayTableColumns.ColumnDatatype.values())
			{
				tableColumns.add(new TableColumn());
			}
			listSelectionListener = (ListSelectionEvent e) ->
			{
				for (TableColumnModelListener tcml : tcmls)
					tcml.columnSelectionChanged(e);
			};
			listSelectionModel = new DefaultListSelectionModel();
		}
		
		
		
		@Override
		public void addColumn(TableColumn aColumn)
		{
			tableColumns.add(aColumn);
			int i = tableColumns.length() - 1;
			TableColumnModelEvent evt = new TableColumnModelEvent(this, i, i);
			for (TableColumnModelListener tcml : tcmls)
				tcml.columnAdded(evt);
		}

		@Override
		public void removeColumn(TableColumn column)
		{
			int i = tableColumns.getIndexOf(column);
			if (i < 0)
				return;
			tableColumns.remove(i);
			TableColumnModelEvent evt = new TableColumnModelEvent(this, i, i);
			for (TableColumnModelListener tcml : tcmls)
				tcml.columnRemoved(evt);
		}

		@Override
		public void moveColumn(int columnIndex, int newIndex)
		{
			tableColumns.move(columnIndex, newIndex);
			TableColumnModelEvent evt = new TableColumnModelEvent(this, columnIndex, newIndex);
			for (TableColumnModelListener tcml : tcmls)
				tcml.columnMoved(evt);
		}

		@Override
		public void setColumnMargin(int newMargin)
		{
			margin = newMargin;
			ChangeEvent evt = new ChangeEvent(newMargin);
			for (TableColumnModelListener tcml : tcmls)
				tcml.columnMarginChanged(evt);
		}

		@Override
		public int getColumnCount()
		{
			return tableColumns.length();
		}

		@Override
		public Enumeration<TableColumn> getColumns()
		{
			return tableColumns.toEnumeration();
		}

		/**
		 * @inhetitDoc
		 * @param columnIdentifier {@inheritDoc}
		 * @return the index of the first table column whose identifier is equal to {@code columnIdentifier}, or {@code -1} if
		 * none is found
		 * 
		 * @author Kyli Rouge
		 * @since 2014-12-29 (1.0.0)
		 * @version 1.0.0
		 */
		@Override
		public int getColumnIndex(Object columnIdentifier)
		{
			for (int i = 0, l = tableColumns.length(); i < l; i++)
				if (tableColumns.get(i).equals(columnIdentifier))
					return i;
			return -1;
		}

		@Override
		public TableColumn getColumn(int columnIndex)
		{
			return tableColumns.get(columnIndex);
		}

		@Override
		public int getColumnMargin()
		{
			return margin;
		}

		@Override
		public int getColumnIndexAtX(int xPosition)
		{
			for (int i = 0, l = tableColumns.length(); i < l; i++)
				if ((xPosition -= tableColumns.get(i).getWidth()) <= 0)
					return i;
			return -1;
		}

		@Override
		public int getTotalColumnWidth()
		{
			int ret = 0;
			for (TableColumn tableColumn : tableColumns)
				ret += tableColumn.getWidth();
			return ret;
		}

		@Override
		public void setColumnSelectionAllowed(boolean flag)
		{
			columnsCanBeSelected = flag;
		}

		@Override
		public boolean getColumnSelectionAllowed()
		{
			return columnsCanBeSelected;
		}

		@Override
		public int[] getSelectedColumns()
		{
			return selectedColumns;
		}

		@Override
		public int getSelectedColumnCount()
		{
			return selectedColumns == null ? 0 : selectedColumns.length;
		}

		@Override
		public void setSelectionModel(ListSelectionModel newModel)
		{
			listSelectionModel = newModel;
			listSelectionModel.addListSelectionListener(listSelectionListener);
		}

		@Override
		public ListSelectionModel getSelectionModel()
		{
			return listSelectionModel;
		}

		@Override
		public void addColumnModelListener(TableColumnModelListener x)
		{
			tcmls.add(x);
		}

		@Override
		public void removeColumnModelListener(TableColumnModelListener x)
		{
			tcmls.remove(x, false);
		}
	}
}
