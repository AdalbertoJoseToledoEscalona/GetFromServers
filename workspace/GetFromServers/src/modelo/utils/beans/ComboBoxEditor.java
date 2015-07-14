/**
 * 
 */
package modelo.utils.beans;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellEditor;

/**
 * @author adalberto
 * 
 */
public class ComboBoxEditor extends AbstractCellEditor implements
		TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JComboBox<String> comboBox;

	public ComboBoxEditor() {
		comboBox = new JComboBox<String>();
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		comboBox.setModel((ComboBoxModel<String>) value);

		return comboBox;
	}

	public Object getCellEditorValue() {
		ComboBoxModel<String> sm = comboBox.getModel();
		return sm;
	}

}
