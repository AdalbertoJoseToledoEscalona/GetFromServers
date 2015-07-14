/**
 * 
 */
package modelo.utils.beans;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * @author adalberto
 *
 */
public class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JCheckBox checkBox;

	/**
	 * 
	 */
	public CheckBoxEditor() {
		checkBox = new JCheckBox();
	}

	/* (non-Javadoc)
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		ButtonModel sm = checkBox.getModel();
		return sm;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		checkBox.setModel((ButtonModel) value);

		return checkBox;
	}

}
