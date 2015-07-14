/**
 * 
 */
package modelo.utils.beans;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellRenderer;

/**
 * @author adalberto
 * 
 */
public class ComboBoxRenderer extends JComboBox<String> implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComboBoxRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setModel((ComboBoxModel<String>) value);

		return this;
	}

}
