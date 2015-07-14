/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.xml.sax.SAXException;

import modelo.ArchivoBuscar;
import vista.AgregarArchivo;

/**
 * @author adalberto
 * 
 */
public class ControladorAgregarArchivo implements ActionListener {

	private AgregarArchivo agregarArchivo;

	/**
	 * @param agregarArchivo
	 * 
	 */
	public ControladorAgregarArchivo(AgregarArchivo agregarArchivo) {
		this.agregarArchivo = agregarArchivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (agregarArchivo.getTextIdArchivo().trim().length() == 0) {
			JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
					"El Campo Id Archivo es requerido", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (agregarArchivo.getTextNombreOriginal().trim().length() == 0) {
			JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
					"El Campo Nombre Original es requerido", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (agregarArchivo.getTextRutaOriginal().trim().length() == 0) {
			JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
					"El Campo Ruta Original es requerido", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (agregarArchivo.getSelectedIndexComboBoxServidorOriginal() == 0) {
			JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
					"Debe Seleccionar un Servidor Original", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (agregarArchivo.getSelectedIndexComboBoxServidorFinal() == 0) {
			JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
					"Debe Seleccionar un Servidor Final", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				ArchivoBuscar archivoBuscar = new ArchivoBuscar();
				if (archivoBuscar.agregar(agregarArchivo.getTextIdArchivo(),
						agregarArchivo.getTextNombreOriginal().trim(), agregarArchivo
								.getTextRutaOriginal().trim(), agregarArchivo
								.getTextNombreFinal().trim(), agregarArchivo
								.getTextRutaFinal().trim(), agregarArchivo
								.getSelectedItemComboBoxServidorOriginal().trim(),
						agregarArchivo.getSelectedItemComboBoxServidorFinal().trim())) {
					JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
							"Archivo Agregado con Éxito", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					agregarArchivo.limpiar();
				} else {
					JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
							archivoBuscar.getMensaje(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (SAXException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(agregarArchivo.getVentana(),
						e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
