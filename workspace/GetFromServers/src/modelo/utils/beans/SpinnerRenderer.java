/**
 * 
 */
package modelo.utils.beans;

import java.awt.Component;

import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.table.TableCellRenderer;

/**
 * @author adalberto
 * 
 */
public class SpinnerRenderer extends JSpinner implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpinnerRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setModel((SpinnerModel) value);

		return this;
	}

}
