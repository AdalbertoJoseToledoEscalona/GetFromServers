/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

import modelo.Servidor;

import org.xml.sax.SAXException;

import vista.ListaServidores;

/**
 * @author adalberto
 * 
 */
public class ControladorListaServidores /* extends MouseAdapter */implements
		ActionListener {

	private ListaServidores listaServidores;

	/**
	 * @param listaServidores
	 * 
	 */
	public ControladorListaServidores(ListaServidores listaServidores) {
		this.listaServidores = listaServidores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(listaServidores.getBtnGuardar())) {

			boolean error = false;

			if (listaServidores.getTable().getRowCount() > 0) {
				List<String> columnNames = new ArrayList<String>();
				List<Object[]> columnValues = new ArrayList<Object[]>();
				for (int i = 0; i < listaServidores.getTable().getRowCount(); i++) {
					List<Object> values = new ArrayList<Object>();
					String protocolo = null;
					Integer puerto = null;
					for (int j = 0; j < listaServidores.getTable()
							.getColumnCount(); j++) {

						if (!listaServidores.getTable().getColumnName(j)
								.equalsIgnoreCase("eliminar")) {
							if (i == 0) {
								columnNames.add(listaServidores.getTable()
										.getColumnName(j));
							}

							Object value = listaServidores.getTable()
									.getValueAt(i, j);
							if (value instanceof SpinnerNumberModel) {
								SpinnerNumberModel spModel = (SpinnerNumberModel) value;
								value = spModel.getValue();
							}

							if (listaServidores.getTable().getColumnName(j)
									.equals("Id")
									&& value.toString().trim().length() == 0) {
								JOptionPane.showMessageDialog(
										listaServidores.getVentana(),
										"El Campo Id Servidor es requerido",
										"Error", JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if (listaServidores.getTable()
									.getColumnName(j).equals("Nombre")
									&& value.toString().trim().length() == 0) {
								JOptionPane.showMessageDialog(
										listaServidores.getVentana(),
										"El Campo Nombre es requerido",
										"Error", JOptionPane.ERROR_MESSAGE);
								error = true;

								/*
								 * else if (agregarServidor.getValuePuerto() ==
								 * 0 &&
								 * agregarServidor.getTextProtocolo().trim()
								 * .length() == 0) {
								 */
							} else if ((listaServidores.getTable()
									.getColumnName(j).equals("Puerto") && (value
									.toString().trim().length() == 0 || Integer
									.parseInt(value.toString().trim()) == 0))
									&& (protocolo != null && protocolo.length() == 0)) {
								JOptionPane
										.showMessageDialog(
												listaServidores.getVentana(),
												"Debe Introducir el Puerto o el Protocolo de Conexión",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if ((listaServidores.getTable()
									.getColumnName(j).equals("Protocolo") && value
									.toString().trim().length() == 0)
									&& (puerto != null && puerto == 0)) {
								JOptionPane
										.showMessageDialog(
												listaServidores.getVentana(),
												"Debe Introducir el Puerto o el Protocolo de Conexión",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								error = true;
							} else {
								values.add(value);
								if (listaServidores.getTable().getColumnName(j)
										.equals("Puerto")) {
									if (value.toString().trim().length() == 0) {
										puerto = 0;
									} else {
										puerto = Integer.parseInt(value
												.toString().trim());
									}
								} else if (listaServidores.getTable()
										.getColumnName(j).equals("Protocolo")) {
									protocolo = value.toString().trim();
								}
							}

						}
					}
					Object valuesArray[] = new Object[values.size()];
					valuesArray = values.toArray(valuesArray);
					columnValues.add(valuesArray);

				}

				if (!error) {
					try {
						int actualizadas = new Servidor().actualizar(
								columnNames, columnValues);

						if (actualizadas > 0) {
							if (actualizadas == listaServidores.getTable()
									.getRowCount()) {
								JOptionPane.showMessageDialog(
										listaServidores.getVentana(),
										"Servidores Actualizados con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaServidores
										.getVentana(),
										"Solo se pudieron actualizar "
												+ actualizadas
												+ "de "
												+ listaServidores.getTable()
														.getRowCount()
												+ " cantidad de servidores",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(
									listaServidores.getVentana(),
									"No se pudo actualizar ningun servidor",
									"Error", JOptionPane.ERROR_MESSAGE);
						}

						listaServidores.dibujarTabla();

					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								listaServidores.getVentana(), e1.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaServidores.getVentana(),
						"No hay servidores que actualizar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource().equals(listaServidores.getBtnEliminar())) {

			int columnIndex = 0;
			for (int i = 0; i < listaServidores.getTable().getColumnCount(); i++) {
				if (listaServidores.getTable().getColumnName(i).equals("Id")) {
					columnIndex = i;
					break;
				}
			}

			if (listaServidores.getTable().getSelectedRowCount() > 0) {

				List<Object> idServidores = new ArrayList<Object>();
				String strServidores = "";
				for (int i = 0; i < listaServidores.getTable()
						.getSelectedRowCount(); i++) {
					idServidores.add(listaServidores.getTable().getValueAt(
							listaServidores.getTable().getSelectedRows()[i],
							columnIndex));

					strServidores += listaServidores.getTable().getValueAt(
							listaServidores.getTable().getSelectedRows()[i],
							columnIndex);

					if (i < listaServidores.getTable().getSelectedRowCount() - 1) {
						strServidores += ", ";
					}
				}

				int respond = JOptionPane.showConfirmDialog(
						listaServidores.getVentana(),
						"¿Está seguro de querer Eliminar los Servidores cuyos IDs son \""
								+ strServidores + "\"", "Confirmación",
						JOptionPane.YES_NO_OPTION);

				if (respond == JOptionPane.YES_OPTION) {

					try {
						int eliminadas = new Servidor().eliminar(idServidores);
						if (eliminadas > 0) {
							if (eliminadas == listaServidores.getTable()
									.getSelectedRowCount()) {

								JOptionPane.showMessageDialog(
										listaServidores.getVentana(),
										"Servidores Eliminados con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaServidores
										.getVentana(),
										"Solo se pudieron eliminar "
												+ eliminadas
												+ "de "
												+ listaServidores.getTable()
														.getSelectedRowCount()
												+ " cantidad de servidores",
										"Error", JOptionPane.ERROR_MESSAGE);
							}

							listaServidores.dibujarTabla();
						} else {
							JOptionPane.showMessageDialog(
									listaServidores.getVentana(),
									"No se pudo eliminar ningun servidor",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaServidores.getVentana(),
						"Debe seleccionar al menos una fila", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	// @Override
	// public void mouseClicked(MouseEvent e) {
	// // TODO Auto-generated method stub
	// super.mouseClicked(e);
	// int fila = listaServidores.getTable().rowAtPoint(e.getPoint());
	// int columna = listaServidores.getTable().columnAtPoint(e.getPoint());
	//
	// /**
	// * Preguntamos si hicimos clic sobre la celda que contiene el botón, si
	// * tuviéramos más de un botón por fila tendríamos que además preguntar
	// * por el contenido del botón o el nombre de la columna
	// */
	// if (listaServidores.getTable().getModel().getColumnClass(columna)
	// .equals(JButton.class)) {
	// /**
	// * Aquí pueden poner lo que quieran, para efectos de este ejemplo,
	// * voy a mostrar en un cuadro de dialogo todos los campos de la fila
	// * que no sean un botón.
	// */
	// /*
	// * StringBuilder sb = new StringBuilder(); for (int i = 0; i <
	// * listaServidores.getTable().getModel() .getColumnCount(); i++) {
	// * if (!listaServidores.getTable().getModel().getColumnClass(i)
	// * .equals(JButton.class)) { sb.append("\n")
	// * .append(listaServidores.getTable().getModel() .getColumnName(i))
	// * .append(": ") .append(listaServidores.getTable().getModel()
	// * .getValueAt(fila, i)); } } JOptionPane.showMessageDialog(null,
	// * "Seleccionada la fila " + fila + sb.toString());
	// */
	//
	// try {
	// int respond = JOptionPane.showConfirmDialog(
	// listaServidores.getVentana(),
	// "¿Está seguro de querer Eliminar el Servidor ID \""
	// + listaServidores.getTable().getModel()
	// .getValueAt(fila, 0) + "\"",
	// "Confirmación", JOptionPane.YES_NO_OPTION);
	//
	// if (respond == JOptionPane.YES_OPTION) {
	// List<Object> idServidores = new ArrayList<Object>();
	// idServidores.add(listaServidores.getTable().getModel()
	// .getValueAt(fila, 0));
	// new Servidor().eliminar(idServidores);
	// if (new Servidor().eliminar(idServidores) > 0) {
	// JOptionPane.showMessageDialog(listaServidores.getVentana(),
	// "Servidor Eliminado con éxito", "Éxito",
	// JOptionPane.INFORMATION_MESSAGE);
	// }else{
	// JOptionPane.showMessageDialog(
	// listaServidores.getVentana(),
	// "No se pudo eliminar el servidor", "Error",
	// JOptionPane.ERROR_MESSAGE);
	// }
	//
	// listaServidores.dibujarTabla();
	// }
	// } catch (SAXException | IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// JOptionPane.showMessageDialog(listaServidores.getVentana(),
	// e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	// }
	// }
	// }

}
