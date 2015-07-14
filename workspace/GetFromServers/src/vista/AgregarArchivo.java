/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.Servidor;

import org.xml.sax.SAXException;

import controlador.ControladorAgregarArchivo;

/**
 * @author adalberto
 * 
 */
public class AgregarArchivo {

	/**
	 * 
	 */
	private JTextField textFieldIdArchivo;
	private JTextField textFieldNombreOriginal;
	private JTextField textFieldRutaOriginal;
	private JComboBox<String> comboBoxServidorOriginal;
	private JTextField textFieldRutaFinal;
	private JComboBox<String> comboBoxServidorFinal;
	private JButton btnAgregar;
	private JTextField textFieldNombreFinal;
	private JFrame ventana;

	public AgregarArchivo(JPanel panelAgregarArchivo, JFrame ventana) {
		this.ventana = ventana;
		try {
			JPanel panelSuperior = new JPanel();
			panelAgregarArchivo.add(panelSuperior, BorderLayout.NORTH);
	
			JLabel lblMensaje = new JLabel(
					"Por favor, llene los campos y presione Agregar");
			panelSuperior.add(lblMensaje);
	
			JPanel panelCentral = new JPanel();
			panelAgregarArchivo.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setLayout(new GridLayout(7, 2, 0, 0));
	
			JPanel panelLblIdArchivo = new JPanel();
			FlowLayout fl_panelLblIdArchivo = (FlowLayout) panelLblIdArchivo
					.getLayout();
			fl_panelLblIdArchivo.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblIdArchivo);
	
			JLabel lblIdArchivo = new JLabel("Id Archivo: ");
			lblIdArchivo.setToolTipText("Identificador del Archivo");
			panelLblIdArchivo.add(lblIdArchivo);
	
			JPanel panelTxtIdArchivo = new JPanel();
			FlowLayout fl_panelTxtIdArchivo = (FlowLayout) panelTxtIdArchivo
					.getLayout();
			fl_panelTxtIdArchivo.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelTxtIdArchivo);
	
			textFieldIdArchivo = new JTextField();
			textFieldIdArchivo.setToolTipText("Identificador del Archivo");
			panelTxtIdArchivo.add(textFieldIdArchivo);
			textFieldIdArchivo.setColumns(10);
	
			JPanel panelLblNombreOriginal = new JPanel();
			FlowLayout fl_panelLblNombreOriginal = (FlowLayout) panelLblNombreOriginal
					.getLayout();
			fl_panelLblNombreOriginal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblNombreOriginal);
	
			JLabel lblNombreOriginal = new JLabel("Nombre Original: ");
			lblNombreOriginal.setToolTipText("Nombre en el servidor original");
			panelLblNombreOriginal.add(lblNombreOriginal);
	
			JPanel panelTxtNombreOriginal = new JPanel();
			FlowLayout fl_panelTxtNombreOriginal = (FlowLayout) panelTxtNombreOriginal
					.getLayout();
			fl_panelTxtNombreOriginal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelTxtNombreOriginal);
	
			textFieldNombreOriginal = new JTextField();
			textFieldNombreOriginal
					.setToolTipText("Nombre en el servidor Original");
			panelTxtNombreOriginal.add(textFieldNombreOriginal);
			textFieldNombreOriginal.setColumns(10);
	
			JPanel panelLblRutaOriginal = new JPanel();
			FlowLayout fl_panelLblRutaOriginal = (FlowLayout) panelLblRutaOriginal
					.getLayout();
			fl_panelLblRutaOriginal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblRutaOriginal);
	
			JLabel lblRutaOriginal = new JLabel("Ruta Original: ");
			lblRutaOriginal.setToolTipText("Ruta en el servidor Original");
			panelLblRutaOriginal.add(lblRutaOriginal);
	
			JPanel panelTxtRutaOriginal = new JPanel();
			FlowLayout fl_panelTxtRutaOriginal = (FlowLayout) panelTxtRutaOriginal
					.getLayout();
			fl_panelTxtRutaOriginal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelTxtRutaOriginal);
	
			textFieldRutaOriginal = new JTextField();
			textFieldRutaOriginal
					.setToolTipText("Ruta del archivo en el servidor Original");
			panelTxtRutaOriginal.add(textFieldRutaOriginal);
			textFieldRutaOriginal.setColumns(10);
	
			JPanel panelLblServidorOriginal = new JPanel();
			FlowLayout fl_panelLblServidorOriginal = (FlowLayout) panelLblServidorOriginal
					.getLayout();
			fl_panelLblServidorOriginal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblServidorOriginal);
	
			JLabel lblServidorOriginal = new JLabel("Servidor Original: ");
			panelLblServidorOriginal.add(lblServidorOriginal);
	
			JPanel panelCmbServidorOriginal = new JPanel();
			FlowLayout fl_panelCmbServidorOriginal = (FlowLayout) panelCmbServidorOriginal
					.getLayout();
			fl_panelCmbServidorOriginal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelCmbServidorOriginal);
	
			String resultado[][] = Servidor.lista(new String[] {"id_servidor"});
			
			comboBoxServidorOriginal = new JComboBox<String>();
			comboBoxServidorOriginal.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Seleccione un Servidor" }));
			
			for (String[] strings : resultado) {
				comboBoxServidorOriginal.addItem(strings[0]);
			}
			
			comboBoxServidorOriginal
					.setToolTipText("Servidor donde se encuentra el archivo originalmente");
			panelCmbServidorOriginal.add(comboBoxServidorOriginal);
	
			JPanel panelLblNombreFinal = new JPanel();
			FlowLayout fl_panelLblNombreFinal = (FlowLayout) panelLblNombreFinal
					.getLayout();
			fl_panelLblNombreFinal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblNombreFinal);
	
			JLabel labelNombreFinal = new JLabel("Nombre Final: ");
			labelNombreFinal.setToolTipText("Nombre en el servidor original");
			panelLblNombreFinal.add(labelNombreFinal);
	
			JPanel panelTxtNombreFinal = new JPanel();
			FlowLayout fl_panelTxtNombreFinal = (FlowLayout) panelTxtNombreFinal
					.getLayout();
			fl_panelTxtNombreFinal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelTxtNombreFinal);
	
			textFieldNombreFinal = new JTextField();
			textFieldNombreFinal.setToolTipText("Nombre en el servidor Final");
			textFieldNombreFinal.setColumns(10);
			panelTxtNombreFinal.add(textFieldNombreFinal);
	
			JPanel panelLblRutaFinal = new JPanel();
			FlowLayout fl_panelLblRutaFinal = (FlowLayout) panelLblRutaFinal
					.getLayout();
			fl_panelLblRutaFinal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblRutaFinal);
	
			JLabel labelRutaFinal = new JLabel("Ruta Final: ");
			labelRutaFinal.setToolTipText("Ruta en el servidor Final");
			panelLblRutaFinal.add(labelRutaFinal);
	
			JPanel panelTxtRutaFinal = new JPanel();
			FlowLayout fl_panelTxtRutaFinal = (FlowLayout) panelTxtRutaFinal
					.getLayout();
			fl_panelTxtRutaFinal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelTxtRutaFinal);
	
			textFieldRutaFinal = new JTextField();
			textFieldRutaFinal
					.setToolTipText("Ruta del archivo en el servidor Destino");
			textFieldRutaFinal.setColumns(10);
			panelTxtRutaFinal.add(textFieldRutaFinal);
	
			JPanel panelLblServidorFinal = new JPanel();
			FlowLayout fl_panelLblServidorFinal = (FlowLayout) panelLblServidorFinal
					.getLayout();
			fl_panelLblServidorFinal.setAlignment(FlowLayout.TRAILING);
			panelCentral.add(panelLblServidorFinal);
	
			JLabel labelServidorFinal = new JLabel("Servidor Final: ");
			labelServidorFinal.setToolTipText("Servidor destino o Final");
			panelLblServidorFinal.add(labelServidorFinal);
	
			JPanel panelCmbServidorFinal = new JPanel();
			panelCmbServidorFinal.setToolTipText("");
			FlowLayout fl_panelCmbServidorFinal = (FlowLayout) panelCmbServidorFinal
					.getLayout();
			fl_panelCmbServidorFinal.setAlignment(FlowLayout.LEADING);
			panelCentral.add(panelCmbServidorFinal);
		
			comboBoxServidorFinal = new JComboBox<String>();
			comboBoxServidorFinal.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Seleccione un Servidor" }));
			for (String[] strings : resultado) {
				comboBoxServidorFinal.addItem(strings[0]);
			}
			comboBoxServidorFinal.setToolTipText("Servidor destino o final");
			panelCmbServidorFinal.add(comboBoxServidorFinal);
	
			JPanel panelInferior = new JPanel();
			panelAgregarArchivo.add(panelInferior, BorderLayout.SOUTH);
	
			btnAgregar = new JButton("Agregar");
			btnAgregar.setToolTipText("Agregar un Archivo");
			panelInferior.add(btnAgregar);
	
			ControladorAgregarArchivo controlador = new ControladorAgregarArchivo(
					this);
			btnAgregar.addActionListener(controlador);

		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void limpiar() {
		textFieldIdArchivo.setText("");
		textFieldNombreOriginal.setText("");
		textFieldRutaOriginal.setText("");
		comboBoxServidorOriginal.setSelectedIndex(0);
		textFieldRutaFinal.setText("");
		comboBoxServidorFinal.setSelectedIndex(0);
		textFieldNombreFinal.setText("");
	}

	/**
	 * @return the textFieldIdArchivo
	 */
	public String getTextIdArchivo() {
		return textFieldIdArchivo.getText();
	}

	/**
	 * @return the textFieldNombreOriginal
	 */
	public String getTextNombreOriginal() {
		return textFieldNombreOriginal.getText();
	}

	/**
	 * @return the textFieldRutaOriginal
	 */
	public String getTextRutaOriginal() {
		return textFieldRutaOriginal.getText();
	}

	/**
	 * @return the comboBoxServidorOriginal
	 */
	public int getSelectedIndexComboBoxServidorOriginal() {
		return comboBoxServidorOriginal.getSelectedIndex();
	}

	public String getSelectedItemComboBoxServidorOriginal() {
		return comboBoxServidorOriginal.getSelectedItem().toString();
	}

	/**
	 * @return the textFieldRutaFinal
	 */
	public String getTextRutaFinal() {
		return textFieldRutaFinal.getText();
	}

	/**
	 * @return the comboBoxServidorFinal
	 */
	public int getSelectedIndexComboBoxServidorFinal() {
		return comboBoxServidorFinal.getSelectedIndex();
	}

	public String getSelectedItemComboBoxServidorFinal() {
		return comboBoxServidorFinal.getSelectedItem().toString();
	}

	/**
	 * @return the btnAgregar
	 */
	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	/**
	 * @return the textFieldNombreFinal
	 */
	public String getTextNombreFinal() {
		return textFieldNombreFinal.getText();
	}

	/**
	 * @return the ventana
	 */
	public JFrame getVentana() {
		return ventana;
	}

}
