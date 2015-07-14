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
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.Agenda;
import modelo.Hilo;

import org.xml.sax.SAXException;

import vista.AgregarAgenda;
import vista.AgregarHilo;
import vista.ListaAgendas;
import vista.Principal;

/**
 * @author adalberto
 *
 */
public class ControladorListaAgendas extends MouseAdapter implements ActionListener {

	private ListaAgendas listaAgendas;

	/**
	 * @param listaAgendas 
	 * 
	 */
	public ControladorListaAgendas(ListaAgendas listaAgendas) {
		this.listaAgendas = listaAgendas;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(listaAgendas.getBtnEliminar())) {

			int columnIndex = 0;
			for (int i = 0; i < listaAgendas.getTable().getColumnCount(); i++) {
				if (listaAgendas.getTable().getColumnName(i).equals("Id")) {
					columnIndex = i;
					break;
				}
			}

			if (listaAgendas.getTable().getSelectedRowCount() > 0) {

				List<Object> idAgendas = new ArrayList<Object>();
				String strHilos = "";
				for (int i = 0; i < listaAgendas.getTable()
						.getSelectedRowCount(); i++) {
					idAgendas.add(listaAgendas.getTable().getValueAt(
							listaAgendas.getTable().getSelectedRows()[i],
							columnIndex));

					strHilos += listaAgendas.getTable().getValueAt(
							listaAgendas.getTable().getSelectedRows()[i],
							columnIndex);

					if (i < listaAgendas.getTable().getSelectedRowCount() - 1) {
						strHilos += ", ";
					}
				}

				int respond = JOptionPane.showConfirmDialog(
						listaAgendas.getVentana(),
						"¿Está seguro de querer Eliminar las Agendas cuyos IDs son \""
								+ strHilos + "\"", "Confirmación",
						JOptionPane.YES_NO_OPTION);

				if (respond == JOptionPane.YES_OPTION) {

					try {
						int eliminadas = new Agenda().eliminar(idAgendas);
						if (eliminadas > 0) {
							if (eliminadas == listaAgendas.getTable()
									.getSelectedRowCount()) {

								JOptionPane.showMessageDialog(
										listaAgendas.getVentana(),
										"Agendas Eliminadas con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaAgendas
										.getVentana(),
										"Solo se pudieron eliminar "
												+ eliminadas
												+ "de "
												+ listaAgendas.getTable()
														.getSelectedRowCount()
												+ " cantidad de Agendas",
										"Error", JOptionPane.ERROR_MESSAGE);
							}

							listaAgendas.dibujarTabla();
						} else {
							JOptionPane.showMessageDialog(
									listaAgendas.getVentana(),
									"No se pudo eliminar ninguna Agenda",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								listaAgendas.getVentana(),
								e1.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaAgendas.getVentana(),
						"Debe seleccionar al menos una fila", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		int fila = listaAgendas.getTable().rowAtPoint(e.getPoint());
		int columna = listaAgendas.getTable().columnAtPoint(e.getPoint());

		/**
		 * Preguntamos si hicimos clic sobre la celda que contiene el botón, si
		 * tuviéramos más de un botón por fila tendríamos que además preguntar
		 * por el contenido del botón o el nombre de la columna
		 */
		if (listaAgendas.getTable().getModel().getColumnClass(columna)
				.equals(JButton.class)) {
			/**
			 * Aquí pueden poner lo que quieran, para efectos de este ejemplo,
			 * voy a mostrar en un cuadro de dialogo todos los campos de la fila
			 * que no sean un botón.
			 */

			ArrayList<String> columnNames = new ArrayList<String>();
			List<Object> columnValues = new ArrayList<Object>();
			//StringBuilder sb = new StringBuilder();
			for (int i = 0; i < listaAgendas.getTable().getModel()
					.getColumnCount(); i++) {
				if (!listaAgendas.getTable().getModel().getColumnClass(i)
						.equals(JButton.class)) {
					/*sb.append("\n")
							.append(listaAgendas.getTable().getModel()
									.getColumnName(i))
							.append(": ")
							.append(listaAgendas.getTable().getModel()
									.getValueAt(fila, i));*/

					columnNames.add(listaAgendas.getTable().getModel()
							.getColumnName(i));
					if (listaAgendas.getTable().getModel().getValueAt(fila, i) instanceof ButtonModel) {
						ButtonModel buttonModel = (ButtonModel) listaAgendas
								.getTable().getModel().getValueAt(fila, i);

						columnValues.add(buttonModel.isSelected());

					} else {
						columnValues.add(listaAgendas.getTable().getModel()
								.getValueAt(fila, i));
					}
				}
			}
			/*JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila
					+ sb.toString());*/
			
			// Agrego los Campos que le corresponde
			new AgregarAgenda(listaAgendas.getVentana().getPanelAgregarHilo(),
					listaAgendas.getVentana(), columnValues.get(
							columnNames.indexOf("Id")).toString(), columnValues
							.get(columnNames.indexOf("Nombre")).toString(),
							columnValues
							.get(columnNames.indexOf("Fecha Hora Inicial")).toString(),
					Boolean.parseBoolean(columnValues.get(
							columnNames.indexOf("Solo Una Vez")).toString()),
							columnValues
							.get(columnNames.indexOf("Minuto")).toString(),
							columnValues
							.get(columnNames.indexOf("Hora")).toString(),
							columnValues
							.get(columnNames.indexOf("Dia")).toString(),
							columnValues
							.get(columnNames.indexOf("Mes")).toString(),
							columnValues
							.get(columnNames.indexOf("Dia de Semana")).toString()
					);

			listaAgendas
					.getVentana()
					.getTabbedPane()
					.addTab("Editar Agenda "
							+ columnValues.get(columnNames.indexOf("Id")),
							new ImageIcon(
									Principal.class
											.getResource("/vista/img/icon/process-add-icon.png")),
							listaAgendas.getVentana().getPanelAgregarHilo(),
							"Editar un Hilo");
			listaAgendas
					.getVentana()
					.getTabbedPane()
					.setSelectedIndex(
							listaAgendas.getVentana().getTabbedPane()
									.getTabCount() - 1);

			final String tabName = "Editar Agenda "
					+ columnValues.get(columnNames.indexOf("Id"));
			// tabbedPane.addTab(title, panelInicio);
			int index = listaAgendas.getVentana().getTabbedPane()
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

			listaAgendas.getVentana().getTabbedPane()
					.setTabComponentAt(index, pnlTab);

			// btnClose.addActionListener(this);
			btnClose.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = listaAgendas.getVentana().getTabbedPane()
							.indexOfTab(tabName);
					if (index >= 0) {

						listaAgendas.getVentana().getTabbedPane()
								.removeTabAt(index);
						// It would probably be worthwhile getting the source
						// casting it back to a JButton and removing
						// the action handler reference ;)
						listaAgendas.getVentana().getPanelAgregarHilo()
								.removeAll();
					}
				}
			});
		}

		// listaAgendas.getVentana().getPanelAgregarHilo();

		/*
		 * try { int respond = JOptionPane.showConfirmDialog(
		 * listaAgendas.getVentana(),
		 * "¿Está seguro de querer Eliminar el Servidor ID \"" +
		 * listaAgendas.getTable().getModel() .getValueAt(fila, 0) + "\"",
		 * "Confirmación", JOptionPane.YES_NO_OPTION);
		 * 
		 * if (respond == JOptionPane.YES_OPTION) { List<Object> idServidores =
		 * new ArrayList<Object>();
		 * idServidores.add(listaAgendas.getTable().getModel() .getValueAt(fila,
		 * 0)); new Servidor().eliminar(idServidores); if (new
		 * Servidor().eliminar(idServidores) > 0) {
		 * JOptionPane.showMessageDialog( listaAgendas.getVentana(),
		 * "Servidor Eliminado con éxito", "Éxito",
		 * JOptionPane.INFORMATION_MESSAGE); } else {
		 * JOptionPane.showMessageDialog( listaAgendas.getVentana(),
		 * "No se pudo eliminar el servidor", "Error",
		 * JOptionPane.ERROR_MESSAGE); }
		 * 
		 * listaAgendas.dibujarTabla(); } } catch (SAXException | IOException e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace();
		 * JOptionPane.showMessageDialog(listaAgendas.getVentana(),
		 * e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
		 */

	}
	

}
