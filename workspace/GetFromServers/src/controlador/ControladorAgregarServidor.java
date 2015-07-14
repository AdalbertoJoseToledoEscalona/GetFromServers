/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.xml.sax.SAXException;

import modelo.Servidor;
import vista.AgregarServidor;

/**
 * @author adalberto
 * 
 */
public class ControladorAgregarServidor implements ActionListener {

	private AgregarServidor agregarServidor;

	/**
	 * @param agregarServidor
	 * 
	 */
	public ControladorAgregarServidor(AgregarServidor agregarServidor) {
		this.agregarServidor = agregarServidor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(agregarServidor.getBtnAgregar())) {
			// Valido el formulario
			if (agregarServidor.getTextIdServidor().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarServidor.getVentana(),
						"El Campo Id Servidor es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (agregarServidor.getTextNombre().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarServidor.getVentana(),
						"El Campo Nombre es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (agregarServidor.getValuePuerto() == 0
					&& agregarServidor.getTextProtocolo().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarServidor.getVentana(),
						"Debe Introducir el Puerto o el Protocolo de Conexión",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				Servidor servidor = new Servidor();
				try {
					if (servidor.agregar(agregarServidor.getTextIdServidor()
							.trim(), agregarServidor.getTextNombre().trim(),
							agregarServidor.getValuePuerto(), agregarServidor
									.getTextProtocolo().trim(), agregarServidor.getTextFieldUsuario(), agregarServidor.getTextFieldClave())) {
						JOptionPane.showMessageDialog(
								agregarServidor.getVentana(),
								"Servidor Agregado con éxito", "Éxito",
								JOptionPane.INFORMATION_MESSAGE);
						agregarServidor.limpiar();
					} else {
						JOptionPane.showMessageDialog(
								agregarServidor.getVentana(),
								servidor.getMensaje(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (SAXException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(agregarServidor.getVentana(),
							e1.getMessage(), e1.getClass().getName(),
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

}
