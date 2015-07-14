/**
 * 
 */
package modelo.utils.beans;

import java.awt.Component;

import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author adalberto
 *
 */
public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**
	 * 
	 */
	public CheckBoxRenderer() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setModel((ButtonModel) value);

		return this;
	}

}
