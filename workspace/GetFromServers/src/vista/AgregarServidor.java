/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import controlador.ControladorAgregarServidor;

/**
 * @author adalberto
 *
 */
public class AgregarServidor {

	/**
	 * 
	 */
	private JTextField textFieldIdServidor;
	private JTextField textFieldNombre;
	private JSpinner spinnerPuerto;
	private JTextField textFieldProtocolo;
	private JTextField textFieldUsuario;
	private JPasswordField textFieldClave;
	private JButton btnAgregar;
	private int initValue;
	private JFrame ventana;
	
	public AgregarServidor(JPanel panelAgregarServidor, JFrame ventana) {
		this.ventana = ventana;
		JPanel panelSuperior = new JPanel();
		panelAgregarServidor.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblMensaje = new JLabel("Por favor llene los campos y presione Agregar");
		panelSuperior.add(lblMensaje);
		
		JPanel panelCentral = new JPanel();
		panelAgregarServidor.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(6, 2, 0, 0));
		
		JPanel panelLblIdServidor = new JPanel();
		FlowLayout fl_panelLblIdServidor = (FlowLayout) panelLblIdServidor.getLayout();
		fl_panelLblIdServidor.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblIdServidor);
		
		JLabel lblIdServidor = new JLabel("Id Servidor: ");
		lblIdServidor.setToolTipText("Identificador del Servidor");
		panelLblIdServidor.add(lblIdServidor);
		
		JPanel panelTxtIdServidor = new JPanel();
		FlowLayout fl_panelTxtIdServidor = (FlowLayout) panelTxtIdServidor.getLayout();
		fl_panelTxtIdServidor.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtIdServidor);
		
		textFieldIdServidor = new JTextField();
		textFieldIdServidor.setToolTipText("Identificador del Servidor");
		panelTxtIdServidor.add(textFieldIdServidor);
		textFieldIdServidor.setColumns(10);
		
		JPanel panelLblNombre = new JPanel();
		FlowLayout fl_panelLblNombre = (FlowLayout) panelLblNombre.getLayout();
		fl_panelLblNombre.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblNombre);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setToolTipText("Nombre del Servidor");
		panelLblNombre.add(lblNombre);
		
		JPanel panelTxtNombre = new JPanel();
		FlowLayout fl_panelTxtNombre = (FlowLayout) panelTxtNombre.getLayout();
		fl_panelTxtNombre.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtNombre);
		
		textFieldNombre = new JTextField();
		panelTxtNombre.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		textFieldNombre.setToolTipText("Nombre del Servidor");
		
		JPanel panelLblPuerto = new JPanel();
		FlowLayout fl_panelLblPuerto = (FlowLayout) panelLblPuerto.getLayout();
		fl_panelLblPuerto.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblPuerto);
		
		JLabel lblPuerto = new JLabel("Puerto: ");
		lblPuerto.setToolTipText("Puerto de Conexión con el Servidor");
		panelLblPuerto.add(lblPuerto);
		
		JPanel panelTxtPuerto = new JPanel();
		FlowLayout fl_panelTxtPuerto = (FlowLayout) panelTxtPuerto.getLayout();
		fl_panelTxtPuerto.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtPuerto);
		
		int min = 0;
	    int max = 65535;
	    int step = 1;
	    initValue = 0;
	    SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
		spinnerPuerto = new JSpinner(model);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPuerto, "####");
		spinnerPuerto.setEditor(editor); 
		spinnerPuerto.setToolTipText("Puerto de Conexión con el Servidor");
		panelTxtPuerto.add(spinnerPuerto);
		
		JPanel panelLblProtocolo = new JPanel();
		FlowLayout fl_panelLblProtocolo = (FlowLayout) panelLblProtocolo.getLayout();
		fl_panelLblProtocolo.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblProtocolo);
		
		JLabel lblProtocolo = new JLabel("Protocolo: ");
		panelLblProtocolo.add(lblProtocolo);
		lblProtocolo.setToolTipText("Protocolo de Conexión con el Servidor");
		
		JPanel panelTxtProtocolo = new JPanel();
		FlowLayout fl_panelTxtProtocolo = (FlowLayout) panelTxtProtocolo.getLayout();
		fl_panelTxtProtocolo.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtProtocolo);
		
		textFieldProtocolo = new JTextField();
		panelTxtProtocolo.add(textFieldProtocolo);
		textFieldProtocolo.setColumns(10);
		textFieldProtocolo.setToolTipText("Protocolo de Conexión con el Servidor");
		
		
		
		JPanel panelLblUsuario = new JPanel();
		FlowLayout fl_panelLblUsuario = (FlowLayout) panelLblUsuario.getLayout();
		fl_panelLblUsuario.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblUsuario);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		panelLblUsuario.add(lblUsuario);
		lblUsuario.setToolTipText("El Usuario de Conexión");
		
		JPanel panelTxtUsuario = new JPanel();
		FlowLayout fl_panelTxtUsuario = (FlowLayout) panelTxtUsuario.getLayout();
		fl_panelTxtUsuario.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtUsuario);
		
		textFieldUsuario = new JTextField();
		panelTxtUsuario.add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		textFieldUsuario.setToolTipText("El Usuario de Conexión");
		
		
		
		
		JPanel panelLblClave = new JPanel();
		FlowLayout fl_panelLblClave = (FlowLayout) panelLblClave.getLayout();
		fl_panelLblClave.setAlignment(FlowLayout.TRAILING);
		panelCentral.add(panelLblClave);
		
		JLabel lblClave = new JLabel("Clave: ");
		panelLblClave.add(lblClave);
		lblClave.setToolTipText("La Clave de Conexión");
		
		JPanel panelTxtClave = new JPanel();
		FlowLayout fl_panelTxtClave = (FlowLayout) panelTxtClave.getLayout();
		fl_panelTxtClave.setAlignment(FlowLayout.LEADING);
		panelCentral.add(panelTxtClave);
		
		textFieldClave = new JPasswordField();
		panelTxtClave.add(textFieldClave);
		textFieldClave.setColumns(10);
		textFieldClave.setToolTipText("La Clave de Conexión");
		
		
		
		JPanel panelInferior = new JPanel();
		panelAgregarServidor.add(panelInferior, BorderLayout.SOUTH);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setToolTipText("Agregar Servidor");
		panelInferior.add(btnAgregar);
		JFormattedTextField txt = ((JSpinner.NumberEditor) spinnerPuerto.getEditor()).getTextField();
		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
		
		ControladorAgregarServidor controlador = new ControladorAgregarServidor(this);
		btnAgregar.addActionListener(controlador);
	}
	
	public void limpiar(){
		textFieldIdServidor.setText("");
		textFieldNombre.setText("");
		spinnerPuerto.setValue(initValue);
		textFieldProtocolo.setText("");
		textFieldUsuario.setText("");
		textFieldClave.setText("");
	}

	/**
	 * @return the textIdServidor
	 */
	public String getTextIdServidor() {
		return textFieldIdServidor.getText();
	}

	/**
	 * @return the textNombre
	 */
	public String getTextNombre() {
		return textFieldNombre.getText();
	}

	/**
	 * @return the valuePuerto
	 */
	public int getValuePuerto() {
		return Integer.parseInt(spinnerPuerto.getValue().toString());
	}

	/**
	 * @return the textProtocolo
	 */
	public String getTextProtocolo() {
		return textFieldProtocolo.getText();
	}

	/**
	 * @return the btnAgregar
	 */
	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	/**
	 * @return the ventana
	 */
	public JFrame getVentana() {
		return ventana;
	}

	/**
	 * @return the textFieldUsuario
	 */
	public String getTextFieldUsuario() {
		return textFieldUsuario.getText();
	}

	/**
	 * @param textFieldUsuario the textFieldUsuario to set
	 */
	public void setTextFieldUsuario(String textFieldUsuario) {
		this.textFieldUsuario.setText(textFieldUsuario);
	}

	/**
	 * @return the textFieldClave
	 */
	public String getTextFieldClave() {
		char[] passwdChars = textFieldClave.getPassword();
		String passw = "";
		for (int i = 0; i < passwdChars.length; i++) {
			passw += passwdChars[i];
		}
		
		return passw;
	}

	/**
	 * @param textFieldClave the textFieldClave to set
	 */
	public void setTextFieldClave(String textFieldClave) {
		this.textFieldClave.setText(textFieldClave);
	}
	
	

}
