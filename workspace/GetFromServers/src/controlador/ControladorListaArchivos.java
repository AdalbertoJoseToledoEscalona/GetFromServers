/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;

import modelo.ArchivoBuscar;

import org.xml.sax.SAXException;

import vista.ListaArchivos;

/**
 * @author adalberto
 * 
 */
public class ControladorListaArchivos implements ActionListener {

	private ListaArchivos listaArchivos;

	/**
	 * @param listaArchivos
	 * 
	 */
	public ControladorListaArchivos(ListaArchivos listaArchivos) {
		this.listaArchivos = listaArchivos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(listaArchivos.getBtnGuardar())) {
			if (listaArchivos.getTable().getRowCount() > 0) {

				boolean error = false;

				List<String> columnNames = new ArrayList<String>();
				List<Object[]> columnValues = new ArrayList<Object[]>();
				for (int i = 0; i < listaArchivos.getTable().getRowCount(); i++) {
					List<Object> values = new ArrayList<Object>();
					for (int j = 0; j < listaArchivos.getTable()
							.getColumnCount(); j++) {

						if (!listaArchivos.getTable().getColumnName(j)
								.equalsIgnoreCase("eliminar")) {
							if (i == 0) {
								columnNames.add(listaArchivos.getTable()
										.getColumnName(j));
							}

							Object value = listaArchivos.getTable().getValueAt(
									i, j);

							if (value instanceof ComboBoxModel<?>) {
								ComboBoxModel<String> spModel = (ComboBoxModel<String>) value;
								value = spModel.getSelectedItem();
							}

							if (listaArchivos.getTable().getColumnName(j)
									.equals("Id")
									&& value.toString().trim().length() == 0) {
								JOptionPane.showMessageDialog(
										listaArchivos.getVentana(),
										"El Campo Id Archivo es requerido",
										"Error", JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if (listaArchivos.getTable()
									.getColumnName(j).equals("Nombre Original")
									&& value.toString().trim().length() == 0) {
								JOptionPane
										.showMessageDialog(
												listaArchivos.getVentana(),
												"El Campo Nombre Original es requerido",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if (listaArchivos.getTable()
									.getColumnName(j).equals("Ruta Original")
									&& value.toString().trim().length() == 0) {
								JOptionPane.showMessageDialog(
										listaArchivos.getVentana(),
										"El Campo Ruta Original es requerido",
										"Error", JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if (listaArchivos.getTable()
									.getColumnName(j)
									.equals("Servidor Original")
									&& value.toString().trim().length() == 0) {
								JOptionPane
										.showMessageDialog(
												listaArchivos.getVentana(),
												"Debe Seleccionar un Servidor Original",
												"Error",
												JOptionPane.ERROR_MESSAGE);
								error = true;
							} else if (listaArchivos.getTable()
									.getColumnName(j).equals("Servidor Final")
									&& value.toString().trim().length() == 0) {
								JOptionPane.showMessageDialog(
										listaArchivos.getVentana(),
										"Debe Seleccionar un Servidor Final",
										"Error", JOptionPane.ERROR_MESSAGE);
								error = true;
							} else {
								values.add(value);
							}
						}
					}
					Object valuesArray[] = new Object[values.size()];
					valuesArray = values.toArray(valuesArray);
					columnValues.add(valuesArray);
				}

				if (!error) {
					try {
						int actualizadas = new ArchivoBuscar().actualizar(
								columnNames, columnValues);

						if (actualizadas > 0) {
							if (actualizadas == listaArchivos.getTable()
									.getRowCount()) {
								JOptionPane.showMessageDialog(
										listaArchivos.getVentana(),
										"Archivos Actualizados con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaArchivos
										.getVentana(),
										"Solo se pudieron actualizar "
												+ actualizadas
												+ "de "
												+ listaArchivos.getTable()
														.getRowCount()
												+ " cantidad de archivos",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(
									listaArchivos.getVentana(),
									"No se pudo actualizar ningun archivo",
									"Error", JOptionPane.ERROR_MESSAGE);
						}

						listaArchivos.dibujarTabla();

					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(
								listaArchivos.getVentana(), e1.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaArchivos.getVentana(),
						"No hay Archivos que actualizar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource().equals(listaArchivos.getBtnEliminar())) {
			
			int columnIndex = 0;
			for (int i = 0; i < listaArchivos.getTable().getColumnCount(); i++) {
				if (listaArchivos.getTable().getColumnName(i).equals("Id")) {
					columnIndex = i;
					break;
				}
			}

			if (listaArchivos.getTable().getSelectedRowCount() > 0) {

				List<Object> idArchivos = new ArrayList<Object>();
				String strArchivos = "";
				for (int i = 0; i < listaArchivos.getTable()
						.getSelectedRowCount(); i++) {
					idArchivos.add(listaArchivos.getTable().getValueAt(
							listaArchivos.getTable().getSelectedRows()[i],
							columnIndex));

					strArchivos += listaArchivos.getTable().getValueAt(
							listaArchivos.getTable().getSelectedRows()[i],
							columnIndex);

					if (i < listaArchivos.getTable().getSelectedRowCount() - 1) {
						strArchivos += ", ";
					}
				}

				int respond = JOptionPane.showConfirmDialog(
						listaArchivos.getVentana(),
						"¿Está seguro de querer Eliminar los Archivos cuyos IDs son \""
								+ strArchivos + "\"", "Confirmación",
						JOptionPane.YES_NO_OPTION);

				if (respond == JOptionPane.YES_OPTION) {

					try {
						int eliminadas = new ArchivoBuscar().eliminar(idArchivos);
						if (eliminadas > 0) {
							if (eliminadas == listaArchivos.getTable()
									.getSelectedRowCount()) {

								JOptionPane.showMessageDialog(
										listaArchivos.getVentana(),
										"Archivos Eliminados con éxito",
										"Éxito",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(listaArchivos
										.getVentana(),
										"Solo se pudieron eliminar "
												+ eliminadas
												+ "de "
												+ listaArchivos.getTable()
														.getSelectedRowCount()
												+ " cantidad de Archivos",
										"Error", JOptionPane.ERROR_MESSAGE);
							}

							listaArchivos.dibujarTabla();
						} else {
							JOptionPane.showMessageDialog(
									listaArchivos.getVentana(),
									"No se pudo eliminar ningun Archivo",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(listaArchivos.getVentana(),
						"Debe seleccionar al menos una fila", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

}
