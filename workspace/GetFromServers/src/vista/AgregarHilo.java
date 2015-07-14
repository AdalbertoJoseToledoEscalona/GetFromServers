/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.ArchivoBuscar;
import modelo.Hilo;

import org.xml.sax.SAXException;

import controlador.ControladorAgregarHilo;

/**
 * @author adalberto
 * 
 */
public class AgregarHilo {

	private JFrame ventana;
	private JTextField textFieldIdHilo;
	private JTextField textFieldNombre;
	private JCheckBox chckbxAutoreprodir;
	private JButton btnAgregarArchivo;
	private JButton btnQuitarArchivo;
	private JPanel panelArchivos;
	private JButton btnAgregar;

	private List<JComboBox<String>> cmbArchivos;


	/**
	 * @param autoReproducir 
	 * @param nombre 
	 * @param idHilo 
	 * 
	 */
	public AgregarHilo(JPanel panelAgregarHilo, Principal ventana, String idHilo, String nombre, Boolean autoReproducir) {
		this.ventana = ventana;
		
		cmbArchivos = new ArrayList<JComboBox<String>>();

		JPanel panelMensaje = new JPanel();
		panelAgregarHilo.add(panelMensaje, BorderLayout.NORTH);

		JLabel lblMensaje = new JLabel(
				"Por favor, llene los campos y presione Agregar");
		panelMensaje.add(lblMensaje);

		JPanel panelCentral = new JPanel();
		panelAgregarHilo.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));

		JPanel panelCamposEstaticos = new JPanel();
		panelCentral.add(panelCamposEstaticos);
		panelCamposEstaticos.setLayout(new GridLayout(3, 2, 0, 0));

		JPanel panelLblIdHilo = new JPanel();
		FlowLayout fl_panelLblIdHilo = (FlowLayout) panelLblIdHilo.getLayout();
		fl_panelLblIdHilo.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblIdHilo);

		JLabel lblIdHilo = new JLabel("Id Hilo: ");
		panelLblIdHilo.add(lblIdHilo);

		JPanel panelTxtIdHilo = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTxtIdHilo.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelTxtIdHilo);

		textFieldIdHilo = new JTextField();
		panelTxtIdHilo.add(textFieldIdHilo);
		textFieldIdHilo.setColumns(10);
		if(idHilo != null){
			textFieldIdHilo.setText(idHilo);
			textFieldIdHilo.setEditable(false);
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

		textFieldNombre = new JTextField();
		panelTxtNombre.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		if(nombre != null){
			textFieldNombre.setText(nombre);
		}

		JPanel panelLblAutoReproducir = new JPanel();
		FlowLayout fl_panelLblAutoReproducir = (FlowLayout) panelLblAutoReproducir
				.getLayout();
		fl_panelLblAutoReproducir.setAlignment(FlowLayout.TRAILING);
		panelCamposEstaticos.add(panelLblAutoReproducir);

		JLabel lblAutoReproducir = new JLabel("Auto Reproducir: ");
		panelLblAutoReproducir.add(lblAutoReproducir);

		JPanel panelChckbxAutoReprodir = new JPanel();
		FlowLayout fl_panelChckbxAutoReprodir = (FlowLayout) panelChckbxAutoReprodir
				.getLayout();
		fl_panelChckbxAutoReprodir.setAlignment(FlowLayout.LEADING);
		panelCamposEstaticos.add(panelChckbxAutoReprodir);

		chckbxAutoreprodir = new JCheckBox("");
		panelChckbxAutoReprodir.add(chckbxAutoreprodir);
		if(autoReproducir != null){
			chckbxAutoreprodir.setSelected(autoReproducir);
		}

		JPanel panelCamosDinamicos = new JPanel();
		panelCentral.add(panelCamosDinamicos, BorderLayout.SOUTH);
		panelCamosDinamicos.setLayout(new BorderLayout(0, 0));

		JPanel panelBtnsAgregarQuitar = new JPanel();
		panelCamosDinamicos.add(panelBtnsAgregarQuitar, BorderLayout.NORTH);

		btnAgregarArchivo = new JButton("Agregar Archivo");
		panelBtnsAgregarQuitar.add(btnAgregarArchivo);

		btnQuitarArchivo = new JButton("Quitar Archivo");
		panelBtnsAgregarQuitar.add(btnQuitarArchivo);

		panelArchivos = new JPanel();
		panelCamosDinamicos.add(panelArchivos, BorderLayout.SOUTH);
		panelArchivos.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panelBtnAgregar = new JPanel();
		panelAgregarHilo.add(panelBtnAgregar, BorderLayout.SOUTH);

		if(idHilo == null){
			btnAgregar = new JButton("Agregar");
		}else{
			btnAgregar = new JButton("Editar");
		}
		panelBtnAgregar.add(btnAgregar);

		ControladorAgregarHilo controlador = new ControladorAgregarHilo(this);
		btnAgregarArchivo.addActionListener(controlador);
		btnQuitarArchivo.addActionListener(controlador);
		btnAgregar.addActionListener(controlador);
		
		if(idHilo != null){
			Hilo hilo = new Hilo(idHilo);
			
			try {
				String[][] resultado = hilo.getArchivos(null);
				
				for (int i = 0; i < resultado.length; i++) {
					agregarArchivo(resultado[i][0]);
				}
				
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	public void agregarArchivo(String value) {
		try {
			
			GridLayout layout = (GridLayout) panelArchivos.getLayout();

			layout.setRows(layout.getRows() + 1);
			layout.setColumns(layout.getRows() * 2);

			JPanel panelLblArchivo1 = new JPanel();
			panelLblArchivo1.setName("panelLblArchivo_" + cmbArchivos.size());
			FlowLayout flowLayout_1 = (FlowLayout) panelLblArchivo1.getLayout();
			flowLayout_1.setAlignment(FlowLayout.TRAILING);
			panelArchivos.add(panelLblArchivo1);

			JLabel lblArchivo1 = new JLabel("Archivo "
					+ (cmbArchivos.size() + 1) + ": ");
			lblArchivo1.setName("lblArchivo_" + cmbArchivos.size());
			panelLblArchivo1.add(lblArchivo1);

			JPanel panelCmbArchivo1 = new JPanel();
			panelCmbArchivo1.setName("panelCmbArchivo_" + cmbArchivos.size());
			FlowLayout flowLayout_2 = (FlowLayout) panelCmbArchivo1.getLayout();
			flowLayout_2.setAlignment(FlowLayout.LEADING);
			panelArchivos.add(panelCmbArchivo1);

			JComboBox<String> comboBoxArchivo1 = new JComboBox<String>();
			comboBoxArchivo1.setName("comboBoxArchivo_" + cmbArchivos.size());
			comboBoxArchivo1.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Seleccione un Archivo" }));

			String[][] resultado = ArchivoBuscar
					.lista(new String[] { "id_archivo" });

			for (int i = 0; i < resultado.length; i++) {
				comboBoxArchivo1.addItem(resultado[i][0]);
				if(value != null && value.equals(resultado[i][0])){
					comboBoxArchivo1.setSelectedIndex((i+1));
				}
			}

			panelCmbArchivo1.add(comboBoxArchivo1);

			cmbArchivos.add(comboBoxArchivo1);

			ventana.repaint();

		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(ventana, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void quitarArchivo() {
		if (cmbArchivos.size() > 0) {
			// JComboBox<String> combo = cmbArchivos.get(cmbArchivos.size() -1);
			Utils.removeComponentRecursiveBySuffix(panelArchivos,
					"_" + (cmbArchivos.size() - 1));

			GridLayout layout = (GridLayout) panelArchivos.getLayout();

			layout.setRows(layout.getRows() - 1);
			layout.setColumns(layout.getRows() == 0 ? 1 : layout.getRows() * 2);

			cmbArchivos.remove(cmbArchivos.size() - 1);

			ventana.repaint();
		}

	}

	

	/**
	 * @return the ventana
	 */
	public JFrame getVentana() {
		return ventana;
	}

	/**
	 * @return the textFieldIdHilo
	 */
	public String getTextIdHilo() {
		return textFieldIdHilo.getText();
	}

	/**
	 * @return the textNombre
	 */
	public String getTextNombre() {
		return textFieldNombre.getText();
	}

	/**
	 * @return the chckbxAutoreprodir
	 */
	public boolean getAutoreprodir() {
		return chckbxAutoreprodir.isSelected();
	}

	/**
	 * @return the btnAgregarArchivo
	 */
	public JButton getBtnAgregarArchivo() {
		return btnAgregarArchivo;
	}

	/**
	 * @return the btnQuitarArchivo
	 */
	public JButton getBtnQuitarArchivo() {
		return btnQuitarArchivo;
	}

	/**
	 * @return the panelArchivos
	 */
	public JPanel getPanelArchivos() {
		return panelArchivos;
	}

	/**
	 * @return the btnAgregar
	 */
	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	/**
	 * @return the cmbArchivos
	 */
	public List<JComboBox<String>> getCmbArchivos() {
		return cmbArchivos;
	}

	public void limpiar() {
		textFieldIdHilo.setText("");
		textFieldNombre.setText("");
		chckbxAutoreprodir.setSelected(false);
		panelArchivos.removeAll();
		cmbArchivos.clear();
		ventana.repaint();
	}
	
	
	
}

