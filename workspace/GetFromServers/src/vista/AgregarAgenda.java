/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import modelo.Agenda;
import modelo.Hilo;

import org.xml.sax.SAXException;

import controlador.ControladorAgregarAgenda;

/**
 * @author adalberto
 *
 */
public class AgregarAgenda {

	/**
	 * 
	 */
	private JTextField txtIdAgenda;
	private JTextField txtNombre;
	private JTextField txtMinuto;
	private JTextField txtHora;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtDiaSemana;
	private JButton btnEditarDiaSemana;
	private JButton btnEditarMes;
	private JButton btnAgregarHilo;
	private JButton btnQuitarHilo;
	private JPanel panelHilos;
	private JButton btnAgregar;
	private JSpinner spinnerFechaHoraInicial;
	
	private List<JComboBox<String>> cmbHilos;
	private JFrame ventana;
	private JButton btnEditarMinuto;
	private JButton btnEditarHora;
	private JButton btnEditarDia;
	private JCheckBox chckbxSoloUnaVez;
	
	public AgregarAgenda(JPanel panelAgregarAgenda, JFrame ventana, String idAgenda, String nombre, String fechaHoraInicial, Boolean soloUnaVez, String minuto, String hora, String dia, String mes, String diaSemana) {
		
		this.ventana = ventana;
		
		cmbHilos = new ArrayList<JComboBox<String>>();
		
		JPanel panelSuperior = new JPanel();
		panelAgregarAgenda.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblMensaje = new JLabel("Llene los campos y presione Agregar");
		panelSuperior.add(lblMensaje);
		
		JPanel panelCentral = new JPanel();
		panelAgregarAgenda.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCamposEstaticos = new JPanel();
		panelCentral.add(panelCamposEstaticos, BorderLayout.CENTER);
		panelCamposEstaticos.setLayout(new GridLayout(11, 2, 0, 0));
		
		JPanel panelLblIdAgenda = new JPanel();
		panelCamposEstaticos.add(panelLblIdAgenda);
		FlowLayout fl_panelLblIdAgenda = (FlowLayout) panelLblIdAgenda.getLayout();
		fl_panelLblIdAgenda.setAlignment(FlowLayout.TRAILING);
		
		JLabel lblIdAgenda = new JLabel("Id Agenda: ");
		panelLblIdAgenda.add(lblIdAgenda);
		
		JPanel panelTxtIdAgenda = new JPanel();
		panelCamposEstaticos.add(panelTxtIdAgenda);
		FlowLayout fl_panelTxtIdAgenda = (FlowLayout) panelTxtIdAgenda.getLayout();
		fl_panelTxtIdAgenda.setAlignment(FlowLayout.LEADING);
		
		
		txtIdAgenda = new JTextField();
		panelTxtIdAgenda.add(txtIdAgenda);
		txtIdAgenda.setColumns(10);
		if(idAgenda != null) {
			txtIdAgenda.setText(idAgenda);
			txtIdAgenda.setEditable(false);
		}
		
		JPanel panelLblNombre = new JPanel();
		FlowLayout fl_panelLblNombre = (FlowLayout) panelLblNombre.getLayout();
		fl_panelLblNombre.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblNombre);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		panelLblNombre.add(lblNombre);
		
		JPanel panelTxtNombre = new JPanel();
		FlowLayout fl_panelTxtNombre = (FlowLayout) panelTxtNombre.getLayout();
		fl_panelTxtNombre.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtNombre);
		
		txtNombre = new JTextField();
		panelTxtNombre.add(txtNombre);
		txtNombre.setColumns(10);
		if(idAgenda != null) {
			txtNombre.setText(nombre);
		}
		
		JPanel panelLblFechaHoraInicial = new JPanel();
		FlowLayout fl_panelLblFechaHoraInicial = (FlowLayout) panelLblFechaHoraInicial.getLayout();
		fl_panelLblFechaHoraInicial.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblFechaHoraInicial);
		
		JLabel lblFechaHoraInicial = new JLabel("Fecha Hora Inicial: ");
		panelLblFechaHoraInicial.add(lblFechaHoraInicial);
		
		JPanel panelTxtFechaHoraInicial = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTxtFechaHoraInicial.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtFechaHoraInicial);
		
		SpinnerDateModel model = new SpinnerDateModel();
		spinnerFechaHoraInicial = new JSpinner(model);
		
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerFechaHoraInicial, Agenda.FORMATO_FECHA);
		spinnerFechaHoraInicial.setEditor(timeEditor);
		if(fechaHoraInicial != null){
			try {
				spinnerFechaHoraInicial.setValue(new SimpleDateFormat(Agenda.FORMATO_FECHA).parse(fechaHoraInicial));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}else {
			spinnerFechaHoraInicial.setValue(new Date()); // will only show the current time
		}
		panelTxtFechaHoraInicial.add(spinnerFechaHoraInicial);
		
		
		JPanel panelLbLSoloUnaVez = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelLbLSoloUnaVez.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLbLSoloUnaVez);
		
		JLabel lblSoloUnaVez = new JLabel("Solo Una Vez: ");
		panelLbLSoloUnaVez.add(lblSoloUnaVez);
		
		JPanel panelChxbSoloUnaVez = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelChxbSoloUnaVez.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelChxbSoloUnaVez);
		
		chckbxSoloUnaVez = new JCheckBox("");
		panelChxbSoloUnaVez.add(chckbxSoloUnaVez);
		if(soloUnaVez != null){
			chckbxSoloUnaVez.setSelected(soloUnaVez);
		}
		
		JPanel panelLblMinuto = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panelLblMinuto.getLayout();
		flowLayout_3.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblMinuto);
		
		JLabel lblMinuto = new JLabel("Minuto: ");
		panelLblMinuto.add(lblMinuto);
		
		JPanel panelTxtMinuto = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panelTxtMinuto.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtMinuto);
		
		txtMinuto = new JTextField("*");
		txtMinuto.setEditable(false);
		panelTxtMinuto.add(txtMinuto);
		txtMinuto.setColumns(10);
		if(minuto != null){
			txtMinuto.setText(minuto);
		}
		
		btnEditarMinuto = new JButton("Editar");
		panelTxtMinuto.add(btnEditarMinuto);
		
		JPanel panelLblHora = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panelLblHora.getLayout();
		flowLayout_5.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblHora);
		
		JLabel lblHora = new JLabel("Hora: ");
		panelLblHora.add(lblHora);
		
		JPanel panelTxtHora = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panelTxtHora.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtHora);
		
		txtHora = new JTextField("*");
		txtHora.setEditable(false);
		txtHora.setColumns(10);
		panelTxtHora.add(txtHora);
		if(hora != null){
			txtHora.setText(hora);
		}
		
		btnEditarHora = new JButton("Editar");
		panelTxtHora.add(btnEditarHora);
		
		JPanel panelLblDia = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panelLblDia.getLayout();
		flowLayout_7.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblDia);
		
		JLabel lblDia = new JLabel("Dia: ");
		panelLblDia.add(lblDia);
		
		JPanel panelTxtDia = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panelTxtDia.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtDia);
		
		txtDia = new JTextField("*");
		txtDia.setEditable(false);
		txtDia.setColumns(10);
		panelTxtDia.add(txtDia);
		if(dia != null){
			txtDia.setText(dia);
		}
		
		btnEditarDia = new JButton("Editar");
		panelTxtDia.add(btnEditarDia);
		
		JPanel panelLblMes = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panelLblMes.getLayout();
		flowLayout_9.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblMes);
		
		JLabel lblMes = new JLabel("Mes: ");
		panelLblMes.add(lblMes);
		
		JPanel panelTxtMes = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) panelTxtMes.getLayout();
		flowLayout_10.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtMes);
		
		txtMes = new JTextField("*");
		txtMes.setEditable(false);
		txtMes.setColumns(10);
		panelTxtMes.add(txtMes);
		if(mes != null){
			txtMes.setText(mes);
		}
		
		btnEditarMes = new JButton("Editar");
		panelTxtMes.add(btnEditarMes);
		
		JPanel panelLblDiaSemana = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panelLblDiaSemana.getLayout();
		flowLayout_11.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblDiaSemana);
		
		JLabel lbDiaSemana = new JLabel("Dia de la Semana: ");
		panelLblDiaSemana.add(lbDiaSemana);
		
		JPanel panelTxtDiaSemana = new JPanel();
		FlowLayout fl_panelTxtDiaSemana = (FlowLayout) panelTxtDiaSemana.getLayout();
		fl_panelTxtDiaSemana.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtDiaSemana);
		
		txtDiaSemana = new JTextField("*");
		txtDiaSemana.setEditable(false);
		txtDiaSemana.setColumns(10);
		panelTxtDiaSemana.add(txtDiaSemana);
		if(diaSemana != null){
			txtDiaSemana.setText(diaSemana);
		}
		
		btnEditarDiaSemana = new JButton("Editar");
		panelTxtDiaSemana.add(btnEditarDiaSemana);
		
		JPanel panelCamosDinamicos = new JPanel();
		panelCentral.add(panelCamosDinamicos, BorderLayout.SOUTH);
		panelCamosDinamicos.setLayout(new BorderLayout(0, 0));

		JPanel panelBtnsAgregarQuitar = new JPanel();
		panelCamosDinamicos.add(panelBtnsAgregarQuitar, BorderLayout.NORTH);

		btnAgregarHilo = new JButton("Agregar Hilo");
		panelBtnsAgregarQuitar.add(btnAgregarHilo);

		btnQuitarHilo = new JButton("Quitar Hilo");
		panelBtnsAgregarQuitar.add(btnQuitarHilo);

		panelHilos = new JPanel();
		panelCamosDinamicos.add(panelHilos, BorderLayout.SOUTH);
		panelHilos.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelBtnAgregar = new JPanel();
		panelAgregarAgenda.add(panelBtnAgregar, BorderLayout.SOUTH);
		
		

		if(idAgenda == null){
			btnAgregar = new JButton("Agregar");
		}else{
			btnAgregar = new JButton("Editar");
		}
		panelBtnAgregar.add(btnAgregar);
		
		ControladorAgregarAgenda controlador = new ControladorAgregarAgenda(this);
		btnEditarMinuto.addActionListener(controlador);
		btnEditarHora.addActionListener(controlador);
		btnEditarDia.addActionListener(controlador);
		btnEditarMes.addActionListener(controlador);
		btnEditarDiaSemana.addActionListener(controlador);
		btnAgregarHilo.addActionListener(controlador);
		btnQuitarHilo.addActionListener(controlador);
		btnAgregar.addActionListener(controlador);
		
		if(idAgenda != null){
			Agenda agenda = new Agenda(idAgenda);
			
			try {
				String[][] resultado = agenda.getHilos(null);
				
				for (int i = 0; i < resultado.length; i++) {
					agregarHilo(resultado[i][0]);
				}
				
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public void agregarHilo(String value) {
		try {
			
			GridLayout layout = (GridLayout) panelHilos.getLayout();

			layout.setRows(layout.getRows() + 1);
			layout.setColumns(layout.getRows() * 2);

			JPanel panelLblHilo1 = new JPanel();
			panelLblHilo1.setName("panelLblHilo_" + cmbHilos.size());
			FlowLayout flowLayout_1 = (FlowLayout) panelLblHilo1.getLayout();
			flowLayout_1.setAlignment(FlowLayout.TRAILING);
			panelHilos.add(panelLblHilo1);

			JLabel lblHilo1 = new JLabel("Hilo "
					+ (cmbHilos.size() + 1) + ": ");
			lblHilo1.setName("lblHilo_" + cmbHilos.size());
			panelLblHilo1.add(lblHilo1);

			JPanel panelCmbHilo1 = new JPanel();
			panelCmbHilo1.setName("panelCmbHilo_" + cmbHilos.size());
			FlowLayout flowLayout_2 = (FlowLayout) panelCmbHilo1.getLayout();
			flowLayout_2.setAlignment(FlowLayout.LEADING);
			panelHilos.add(panelCmbHilo1);

			JComboBox<String> comboBoxHilo1 = new JComboBox<String>();
			comboBoxHilo1.setName("comboBoxHilo_" + cmbHilos.size());
			comboBoxHilo1.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Seleccione un Hilo" }));

			String[][] resultado = Hilo
					.lista(new String[] { "id_hilo" });

			for (int i = 0; i < resultado.length; i++) {
				comboBoxHilo1.addItem(resultado[i][0]);
				if(value != null && value.equals(resultado[i][0])){
					comboBoxHilo1.setSelectedIndex((i+1));
				}
			}

			panelCmbHilo1.add(comboBoxHilo1);

			cmbHilos.add(comboBoxHilo1);

			ventana.repaint();

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void quitarHilo() {
		if (cmbHilos.size() > 0) {
			// JComboBox<String> combo = cmbHilos.get(cmbHilos.size() -1);
			Utils.removeComponentRecursiveBySuffix(panelHilos,
					"_" + (cmbHilos.size() - 1));

			GridLayout layout = (GridLayout) panelHilos.getLayout();

			layout.setRows(layout.getRows() - 1);
			layout.setColumns(layout.getRows() == 0 ? 1 : layout.getRows() * 2);

			cmbHilos.remove(cmbHilos.size() - 1);

			ventana.repaint();
		}

	}

	/**
	 * @return the txtIdAgenda
	 */
	public String getTxtIdAgenda() {
		return txtIdAgenda.getText();
	}

	/**
	 * @return the txtNombre
	 */
	public String getTxtNombre() {
		return txtNombre.getText();
	}

	/**
	 * @return the txtMinuto
	 */
	public String getTxtMinuto() {
		return txtMinuto.getText();
	}

	/**
	 * @param txtMinuto the txtMinuto to set
	 */
	public void setTxtMinuto(String txtMinuto) {
		this.txtMinuto.setText(txtMinuto);
	}

	/**
	 * @param txtHora the txtHora to set
	 */
	public void setTxtHora(String txtHora) {
		this.txtHora.setText(txtHora);
	}

	/**
	 * @param txtDia the txtDia to set
	 */
	public void setTxtDia(String txtDia) {
		this.txtDia.setText(txtDia);
	}

	/**
	 * @param txtMes the txtMes to set
	 */
	public void setTxtMes(String txtMes) {
		this.txtMes.setText(txtMes);
	}

	/**
	 * @return the txtHora
	 */
	public String getTxtHora() {
		return txtHora.getText();
	}

	/**
	 * @return the txtDia
	 */
	public String getTxtDia() {
		return txtDia.getText();
	}

	/**
	 * @return the txtMes
	 */
	public String getTxtMes() {
		return txtMes.getText();
	}

	/**
	 * @return the txtDiaSemana
	 */
	public String getTxtDiaSemana() {
		return txtDiaSemana.getText();
	}
	
	

	/**
	 * @param txtDiaSemana the txtDiaSemana to set
	 */
	public void setTxtDiaSemana(String txtDiaSemana) {
		this.txtDiaSemana.setText(txtDiaSemana);
	}

	/**
	 * @return the btnEditarDiaSemana
	 */
	public JButton getBtnEditarDiaSemana() {
		return btnEditarDiaSemana;
	}

	/**
	 * @return the btnEditarMes
	 */
	public JButton getBtnEditarMes() {
		return btnEditarMes;
	}

	/**
	 * @return the btnAgregarHilo
	 */
	public JButton getBtnAgregarHilo() {
		return btnAgregarHilo;
	}

	/**
	 * @return the btnQuitarHilo
	 */
	public JButton getBtnQuitarHilo() {
		return btnQuitarHilo;
	}

	/**
	 * @return the btnAgregar
	 */
	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	/**
	 * @return the spinnerFechaHoraInicial
	 */
	public String getSpinnerFechaHoraInicial() {
		return new SimpleDateFormat(Agenda.FORMATO_FECHA).format(spinnerFechaHoraInicial.getValue());
	}

	/**
	 * @return the cmbHilos
	 */
	public List<JComboBox<String>> getCmbHilos() {
		return cmbHilos;
	}

	/**
	 * @return the ventana
	 */
	public JFrame getVentana() {
		return ventana;
	}

	/**
	 * @return the btnEditarMinuto
	 */
	public JButton getBtnEditarMinuto() {
		return btnEditarMinuto;
	}

	/**
	 * @return the btnEditarHora
	 */
	public JButton getBtnEditarHora() {
		return btnEditarHora;
	}

	/**
	 * @return the btnEditarDia
	 */
	public JButton getBtnEditarDia() {
		return btnEditarDia;
	}

	/**
	 * @return the chckbxSoloUnaVez
	 */
	public boolean getIsSoloUnaVez() {
		return chckbxSoloUnaVez.isSelected();
	}

	public void limpiar() {
		txtIdAgenda.setText("");
		txtNombre.setText("");
		chckbxSoloUnaVez.setSelected(false);
		
		txtMinuto.setText("*");
		txtHora.setText("*");
		txtDia.setText("*");
		txtMes.setText("*");
		txtDiaSemana.setText("*");
		
		panelHilos.removeAll();
		cmbHilos.clear();
		ventana.repaint();
	}
	
	

}
