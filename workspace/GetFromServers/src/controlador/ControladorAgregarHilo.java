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

import modelo.Hilo;

import org.xml.sax.SAXException;

import vista.AgregarHilo;

/**
 * @author adalberto
 * 
 */
public class ControladorAgregarHilo implements ActionListener {

	private AgregarHilo agregarHilo;

	/**
	 * 
	 */
	public ControladorAgregarHilo() {
		// TODO Auto-generated constructor stub
	}

	public ControladorAgregarHilo(AgregarHilo agregarHilo) {
		this.agregarHilo = agregarHilo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(agregarHilo.getBtnAgregarArchivo())) {
			agregarHilo.agregarArchivo(null);
		} else if (e.getSource().equals(agregarHilo.getBtnQuitarArchivo())) {
			agregarHilo.quitarArchivo();
		} else if (e.getSource().equals(agregarHilo.getBtnAgregar())) {

			if (agregarHilo.getTextIdHilo().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarHilo.getVentana(),
						"El Campo Id Hilo es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (agregarHilo.getTextNombre().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarHilo.getVentana(),
						"El Campo Nombre es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {

				boolean error = false;
				List<String> archivos = new ArrayList<String>();
				for (int i = 0; i < agregarHilo.getCmbArchivos().size(); i++) {
					if (agregarHilo.getCmbArchivos().get(i).getSelectedIndex() == 0) {
						error = true;

						JOptionPane.showMessageDialog(agregarHilo.getVentana(),
								"El Campo Archivo " + (i + 1) + " requerido",
								"Error", JOptionPane.ERROR_MESSAGE);

					} else if (archivos.contains(agregarHilo.getCmbArchivos()
							.get(i).getSelectedItem().toString())) {
						error = true;

						JOptionPane
								.showMessageDialog(
										agregarHilo.getVentana(),
										"No puede elegir más de una vez un archivo para un mismo hilo",
										"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						archivos.add(agregarHilo.getCmbArchivos().get(i)
								.getSelectedItem().toString());
					}
				}

				if (!error) {
					try {
						Hilo hilo = new Hilo();
						if (e.getActionCommand().equalsIgnoreCase("Agregar")) {
							if (hilo.agregar(agregarHilo.getTextIdHilo(),
									agregarHilo.getTextNombre(),
									agregarHilo.getAutoreprodir(), archivos)) {
								JOptionPane.showMessageDialog(
										agregarHilo.getVentana(),
										"Hilo Agregado con Éxito", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);
								agregarHilo.limpiar();
							} else {
								JOptionPane.showMessageDialog(
										agregarHilo.getVentana(),
										hilo.getMensaje(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						} else if (e.getActionCommand().equalsIgnoreCase("Editar")) {
							if (hilo.editar(agregarHilo.getTextIdHilo(),
									agregarHilo.getTextNombre(),
									agregarHilo.getAutoreprodir(), archivos)) {
								JOptionPane.showMessageDialog(
										agregarHilo.getVentana(),
										"Hilo Actualizado con Éxito", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);
								//agregarHilo.limpiar();
							} else {
								JOptionPane.showMessageDialog(
										agregarHilo.getVentana(),
										hilo.getMensaje(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(agregarHilo.getVentana(),
								e1.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		}
	}

}
