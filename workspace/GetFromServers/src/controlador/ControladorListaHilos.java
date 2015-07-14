/**
 * 
 */
package controlador;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.Hilo;

import org.xml.sax.SAXException;

import vista.AgregarHilo;
import vista.ListaHilos;
import vista.Principal;

/**
 * @author adalberto
 * 
 */
public class ControladorListaHilos extends MouseAdapter implements
		ActionListener {

	private ListaHilos listaHilos;

	/**
	 * @param listaHilos
	 * 
	 */
	public ControladorListaHilos(ListaHilos listaHilos) {
		this.listaHilos = listaHilos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(listaHilos.getBtnEliminar())) {

			int columnIndex = 0;
			for (int i = 0; i < listaHilos.getTable().getColumnCount(); i++) {
				if (listaHilos.getTable().getColumnName(i).equals("Id")) {
					columnIndex = i;
					break;
				}
			}

			if (listaHilos.getTable().getSelectedRowCount() > 0) {

				List<Object> idHilos = new ArrayList<Object>();
				String strHilos = "";
				for (int i = 0; i < listaHilos.getTable()
						.getSelectedRowCount(); i++) {
					idHilos.add(listaHilos.getTable().getValueAt(
							listaHilos.getTable().getSelectedRows()[i],
							columnIndex));

					strHilos += listaHilos.getTable().getValueAt(
							listaHilos.getTable().getSelectedRows()[i],
							columnIndex);

					if (i < listaHilos.getTable().getSelectedRowCount() - 1) {
						strHilos += ", ";
					}
				}

				int respond = JOptionPane.showConfirmDialog(
						listaHilos.getVentana(),
						"¿Está seguro de querer Eliminar los Hilos cuyos IDs son \""
								+ strHilos + "\"", "Confirmación",
						JOptionPane.YES_NO_OPTION);

				if (respond == JOptionPane.YES_OPTION) {

					try {
						int eliminadas = new Hilo().eliminar(idHilos);
						if (eliminadas > 0) {
							if (eliminadas == listaHilos.getTable()
									.getSelectedRowCount()) {

								JOptionPane.showMessageDialog(
										listaHilos.getVentana(),
										"Hilos Eliminados con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaHilos
										.getVentana(),
										"Solo se pudieron eliminar "
												+ eliminadas
												+ "de "
												+ listaHilos.getTable()
														.getSelectedRowCount()
												+ " cantidad de hilos",
										"Error", JOptionPane.ERROR_MESSAGE);
							}

							listaHilos.dibujarTabla();
						} else {
							JOptionPane.showMessageDialog(
									listaHilos.getVentana(),
									"No se pudo eliminar ningun Hilo",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								listaHilos.getVentana(),
								e1.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaHilos.getVentana(),
						"Debe seleccionar al menos una fila", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		int fila = listaHilos.getTable().rowAtPoint(e.getPoint());
		int columna = listaHilos.getTable().columnAtPoint(e.getPoint());

		/**
		 * Preguntamos si hicimos clic sobre la celda que contiene el botón, si
		 * tuviéramos más de un botón por fila tendríamos que además preguntar
		 * por el contenido del botón o el nombre de la columna
		 */
		if (listaHilos.getTable().getModel().getColumnClass(columna)
				.equals(JButton.class) && listaHilos.getTable().getModel().getColumnName(columna)
				.equals("Editar")) {
			/**
			 * Aquí pueden poner lo que quieran, para efectos de este ejemplo,
			 * voy a mostrar en un cuadro de dialogo todos los campos de la fila
			 * que no sean un botón.
			 */

			ArrayList<String> columnNames = new ArrayList<String>();
			List<Object> columnValues = new ArrayList<Object>();
			//StringBuilder sb = new StringBuilder();
			for (int i = 0; i < listaHilos.getTable().getModel()
					.getColumnCount(); i++) {
				if (!listaHilos.getTable().getModel().getColumnClass(i)
						.equals(JButton.class)) {
					/*sb.append("\n")
							.append(listaHilos.getTable().getModel()
									.getColumnName(i))
							.append(": ")
							.append(listaHilos.getTable().getModel()
									.getValueAt(fila, i));*/

					columnNames.add(listaHilos.getTable().getModel()
							.getColumnName(i));
					if (listaHilos.getTable().getModel().getValueAt(fila, i) instanceof ButtonModel) {
						ButtonModel buttonModel = (ButtonModel) listaHilos
								.getTable().getModel().getValueAt(fila, i);

						columnValues.add(buttonModel.isSelected());

					} else {
						columnValues.add(listaHilos.getTable().getModel()
								.getValueAt(fila, i));
					}
				}
			}
			/*JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila
					+ sb.toString());*/

			// Agrego los Campos que le corresponde
			new AgregarHilo(listaHilos.getVentana().getPanelAgregarHilo(),
					listaHilos.getVentana(), columnValues.get(
							columnNames.indexOf("Id")).toString(), columnValues
							.get(columnNames.indexOf("Nombre")).toString(),
					Boolean.parseBoolean(columnValues.get(
							columnNames.indexOf("Auto Reproducir")).toString()));

			listaHilos
					.getVentana()
					.getTabbedPane()
					.addTab("Editar Hilo "
							+ columnValues.get(columnNames.indexOf("Id")),
							new ImageIcon(
									Principal.class
											.getResource("/vista/img/icon/process-add-icon.png")),
							listaHilos.getVentana().getPanelAgregarHilo(),
							"Editar un Hilo");
			listaHilos
					.getVentana()
					.getTabbedPane()
					.setSelectedIndex(
							listaHilos.getVentana().getTabbedPane()
									.getTabCount() - 1);

			final String tabName = "Editar Hilo "
					+ columnValues.get(columnNames.indexOf("Id"));
			// tabbedPane.addTab(title, panelInicio);
			int index = listaHilos.getVentana().getTabbedPane()
					.indexOfTab(tabName);
			JPanel pnlTab = new JPanel(new GridBagLayout());
			pnlTab.setOpaque(false);
			JLabel lblTitle = new JLabel(tabName);
			JButton btnClose = new JButton("x");

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			pnlTab.add(lblTitle, gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			pnlTab.add(btnClose, gbc);

			listaHilos.getVentana().getTabbedPane()
					.setTabComponentAt(index, pnlTab);

			// btnClose.addActionListener(this);
			btnClose.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = listaHilos.getVentana().getTabbedPane()
							.indexOfTab(tabName);
					if (index >= 0) {

						listaHilos.getVentana().getTabbedPane()
								.removeTabAt(index);
						// It would probably be worthwhile getting the source
						// casting it back to a JButton and removing
						// the action handler reference ;)
						listaHilos.getVentana().getPanelAgregarHilo()
								.removeAll();
					}
				}
			});
		} else if (listaHilos.getTable().getModel().getColumnClass(columna)
				.equals(JButton.class) && listaHilos.getTable().getModel().getColumnName(columna)
				.equals("Ejecutar/Detener")) {
			/**
			 * Aquí pueden poner lo que quieran, para efectos de este ejemplo,
			 * voy a mostrar en un cuadro de dialogo todos los campos de la fila
			 * que no sean un botón.
			 */

			ArrayList<String> columnNames = new ArrayList<String>();
			List<Object> columnValues = new ArrayList<Object>();
			//StringBuilder sb = new StringBuilder();
			for (int i = 0; i < listaHilos.getTable().getModel()
					.getColumnCount(); i++) {
				/*if (!listaHilos.getTable().getModel().getColumnClass(i)
						.equals(JButton.class)) {*/
					/*sb.append("\n")
							.append(listaHilos.getTable().getModel()
									.getColumnName(i))
							.append(": ")
							.append(listaHilos.getTable().getModel()
									.getValueAt(fila, i));*/

					columnNames.add(listaHilos.getTable().getModel()
							.getColumnName(i));
					if (listaHilos.getTable().getModel().getValueAt(fila, i) instanceof ButtonModel) {
						ButtonModel buttonModel = (ButtonModel) listaHilos
								.getTable().getModel().getValueAt(fila, i);

						columnValues.add(buttonModel.isSelected());

					} else {
						columnValues.add(listaHilos.getTable().getModel()
								.getValueAt(fila, i));
					}
				//}
			}
			
			JButton b = (JButton) columnValues.get(
					columnNames.indexOf("Ejecutar/Detener"));
			
			if (b.getText().equalsIgnoreCase("ejecutar")) {
				Hilo hilo = new Hilo(columnValues.get(
						columnNames.indexOf("Id")).toString(), columnValues.get(
								columnNames.indexOf("Id")).toString(), columnValues
								.get(columnNames.indexOf("Nombre")).toString(), Boolean.parseBoolean(columnValues.get(
										columnNames.indexOf("Auto Reproducir")).toString()));
				
				hilo.ejecutarHilo();
			} else {
				Hilo hilo = new Hilo(columnValues.get(
						columnNames.indexOf("Id")).toString(), columnValues.get(
								columnNames.indexOf("Id")).toString(), columnValues
								.get(columnNames.indexOf("Nombre")).toString(), Boolean.parseBoolean(columnValues.get(
										columnNames.indexOf("Auto Reproducir")).toString()));
				
				
				hilo.detenerHilo();
			}

			listaHilos.dibujarTabla();
		} 

		// listaHilos.getVentana().getPanelAgregarHilo();

		/*
		 * try { int respond = JOptionPane.showConfirmDialog(
		 * listaHilos.getVentana(),
		 * "¿Está seguro de querer Eliminar el Servidor ID \"" +
		 * listaHilos.getTable().getModel() .getValueAt(fila, 0) + "\"",
		 * "Confirmación", JOptionPane.YES_NO_OPTION);
		 * 
		 * if (respond == JOptionPane.YES_OPTION) { List<Object> idServidores =
		 * new ArrayList<Object>();
		 * idServidores.add(listaHilos.getTable().getModel() .getValueAt(fila,
		 * 0)); new Servidor().eliminar(idServidores); if (new
		 * Servidor().eliminar(idServidores) > 0) {
		 * JOptionPane.showMessageDialog( listaHilos.getVentana(),
		 * "Servidor Eliminado con éxito", "Éxito",
		 * JOptionPane.INFORMATION_MESSAGE); } else {
		 * JOptionPane.showMessageDialog( listaHilos.getVentana(),
		 * "No se pudo eliminar el servidor", "Error",
		 * JOptionPane.ERROR_MESSAGE); }
		 * 
		 * listaHilos.dibujarTabla(); } } catch (SAXException | IOException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace();
		 * JOptionPane.showMessageDialog(listaHilos.getVentana(),
		 * e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
		 */

	}

}
