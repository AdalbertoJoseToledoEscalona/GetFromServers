package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import controlador.ControladorEditarValorAgenda;

public class EditarValorAgenda extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JSpinner spinnerMinutoSalto;
	private JSpinner spinnerDesde;
	private JSpinner spinnerHasta;
	private JSpinner spinnerMinutoExacto;
	private JRadioButton rdbtnCadaMinuto;
	private JRadioButton rdbtnEnUnRango;
	private JRadioButton rdbtnEnUnMinuto;
	private JRadioButton rdbtnEnUnSalto;
	private JButton okButton;
	private JButton cancelButton;
	private AgregarAgenda agregarAgenda;
	private String campo;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EditarValorAgenda dialog = new EditarValorAgenda();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 * @param campo 
	 * @param j 
	 * @param i 
	 * @param agregarAgenda 
	 */
	public EditarValorAgenda(AgregarAgenda agregarAgenda, int i, int j, String campo, String value) {
		this.agregarAgenda = agregarAgenda;
		this.campo = campo;
		setTitle("Editar " + campo);
		setModal(true);
		setBounds(100, 100, 450, 362);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			ButtonGroup buttonGroup = new ButtonGroup();
			
			int min = i;
		    int max = j;
		    int step = 1;
		    int initValue = i;
			
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{177, 0};
			gbl_panel.rowHeights = new int[]{38, 53, 93, 61, 0};
			gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel.setLayout(gbl_panel);
			{
				JPanel panelCada = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panelCada.getLayout();
				flowLayout.setAlignment(FlowLayout.LEADING);
				GridBagConstraints gbc_panelCada = new GridBagConstraints();
				gbc_panelCada.fill = GridBagConstraints.BOTH;
				gbc_panelCada.insets = new Insets(0, 0, 5, 0);
				gbc_panelCada.gridx = 0;
				gbc_panelCada.gridy = 0;
				panel.add(panelCada, gbc_panelCada);
				{
					rdbtnCadaMinuto = new JRadioButton("Cada " + campo);
					rdbtnCadaMinuto.setSelected(true);
					panelCada.add(rdbtnCadaMinuto);
					buttonGroup.add(rdbtnCadaMinuto);
				}
			}
			{
				JPanel panelSalto = new JPanel();
				GridBagConstraints gbc_panelSalto = new GridBagConstraints();
				gbc_panelSalto.fill = GridBagConstraints.BOTH;
				gbc_panelSalto.insets = new Insets(0, 0, 5, 0);
				gbc_panelSalto.gridx = 0;
				gbc_panelSalto.gridy = 1;
				panel.add(panelSalto, gbc_panelSalto);
				panelSalto.setLayout(new BorderLayout(0, 0));
				{
					JPanel panelRdbtnSalto = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panelRdbtnSalto.getLayout();
					flowLayout.setAlignment(FlowLayout.LEADING);
					panelSalto.add(panelRdbtnSalto, BorderLayout.NORTH);
					{
						rdbtnEnUnSalto = new JRadioButton("En un salto de:");
						panelRdbtnSalto.add(rdbtnEnUnSalto);
						buttonGroup.add(rdbtnEnUnSalto);
					}
				}
				{
					JPanel paneltxtSalto = new JPanel();
					panelSalto.add(paneltxtSalto);
					paneltxtSalto.setLayout(new GridLayout(1, 2, 0, 0));
					{
						JPanel panelLblMinuto = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelLblMinuto.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						paneltxtSalto.add(panelLblMinuto);
						{
							JLabel lblMinutos = new JLabel(campo + ": ");
							panelLblMinuto.add(lblMinutos);
						}
					}
					{
						JPanel panelTxtMinuto = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelTxtMinuto.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						paneltxtSalto.add(panelTxtMinuto);
						{
							SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
							spinnerMinutoSalto = new JSpinner(model);
							JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerMinutoSalto, "####");
							spinnerMinutoSalto.setEditor(editor); 
							panelTxtMinuto.add(spinnerMinutoSalto);
						}
					}
				}
			}
			{
				JPanel panelRango = new JPanel();
				GridBagConstraints gbc_panelRango = new GridBagConstraints();
				gbc_panelRango.fill = GridBagConstraints.BOTH;
				gbc_panelRango.insets = new Insets(0, 0, 5, 0);
				gbc_panelRango.gridx = 0;
				gbc_panelRango.gridy = 2;
				panel.add(panelRango, gbc_panelRango);
				panelRango.setLayout(new BorderLayout(0, 0));
				{
					JPanel panelRdbtnRango = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panelRdbtnRango.getLayout();
					flowLayout.setAlignment(FlowLayout.LEADING);
					panelRango.add(panelRdbtnRango, BorderLayout.NORTH);
					{
						rdbtnEnUnRango = new JRadioButton("En un Rango");
						panelRdbtnRango.add(rdbtnEnUnRango);
						buttonGroup.add(rdbtnEnUnRango);
					}
				}
				{
					JPanel panelRangos = new JPanel();
					panelRango.add(panelRangos, BorderLayout.CENTER);
					panelRangos.setLayout(new GridLayout(2, 2, 0, 0));
					{
						JPanel panelLblDesde = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelLblDesde.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelRangos.add(panelLblDesde);
						{
							JLabel lblDesde = new JLabel("Desde: ");
							panelLblDesde.add(lblDesde);
						}
					}
					{
						JPanel panelTxtDesde = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelTxtDesde.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelRangos.add(panelTxtDesde);
						{
							SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
							spinnerDesde = new JSpinner(model);
							JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerDesde, "####");
							spinnerDesde.setEditor(editor); 
							panelTxtDesde.add(spinnerDesde);
						}
					}
					{
						JPanel panelLblHasta = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelLblHasta.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelRangos.add(panelLblHasta);
						{
							JLabel lblHasta = new JLabel("Hasta: ");
							panelLblHasta.add(lblHasta);
						}
					}
					{
						JPanel panelTxtHasta = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelTxtHasta.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelRangos.add(panelTxtHasta);
						{
							SpinnerModel model = new SpinnerNumberModel(j, min, max, step);
							spinnerHasta = new JSpinner(model);
							JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerHasta, "####");
							spinnerHasta.setEditor(editor); 
							panelTxtHasta.add(spinnerHasta);
						}
					}
				}
			}
			{
				JPanel panelExacto = new JPanel();
				GridBagConstraints gbc_panelExacto = new GridBagConstraints();
				gbc_panelExacto.fill = GridBagConstraints.BOTH;
				gbc_panelExacto.gridx = 0;
				gbc_panelExacto.gridy = 3;
				panel.add(panelExacto, gbc_panelExacto);
				panelExacto.setLayout(new BorderLayout(0, 0));
				{
					JPanel panelRdbtnExacto = new JPanel();
					FlowLayout flowLayout = (FlowLayout) panelRdbtnExacto.getLayout();
					flowLayout.setAlignment(FlowLayout.LEADING);
					panelExacto.add(panelRdbtnExacto, BorderLayout.NORTH);
					{
						rdbtnEnUnMinuto = new JRadioButton("En un " + campo + " Exacto");
						panelRdbtnExacto.add(rdbtnEnUnMinuto);
						buttonGroup.add(rdbtnEnUnMinuto);
					}
				}
				{
					JPanel panelValorExacto = new JPanel();
					panelExacto.add(panelValorExacto, BorderLayout.CENTER);
					panelValorExacto.setLayout(new GridLayout(1, 2, 0, 0));
					{
						JPanel panelLblMinutoExacto = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelLblMinutoExacto.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelValorExacto.add(panelLblMinutoExacto);
						{
							JLabel lblMinuto = new JLabel(campo + ": ");
							panelLblMinutoExacto.add(lblMinuto);
						}
					}
					{
						JPanel panelTxtMinutoExacto = new JPanel();
						FlowLayout flowLayout = (FlowLayout) panelTxtMinutoExacto.getLayout();
						flowLayout.setAlignment(FlowLayout.TRAILING);
						panelValorExacto.add(panelTxtMinutoExacto);
						{
							SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
							spinnerMinutoExacto = new JSpinner(model);
							JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerMinutoExacto, "####");
							spinnerMinutoExacto.setEditor(editor); 
							panelTxtMinutoExacto.add(spinnerMinutoExacto);
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancelar");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setValue(value);
		
		ControladorEditarValorAgenda controlador = new ControladorEditarValorAgenda(this);
		okButton.addActionListener(controlador);
		cancelButton.addActionListener(controlador);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		
		
	}
	
	public String getValue(){
		if(rdbtnCadaMinuto.isSelected()) {
			return "*";
		} else if(rdbtnEnUnSalto.isSelected()) {
			return "*/" + spinnerMinutoSalto.getValue().toString();
		} else if(rdbtnEnUnRango.isSelected()) {
			return spinnerDesde.getValue().toString() + "-" + spinnerHasta.getValue().toString();
		}else if(rdbtnEnUnMinuto.isSelected()) {
			return spinnerMinutoExacto.getValue().toString();
		}else {
			return null;
		}
	}
	
	public void setValue(String value){
		if(value.equals("*")) {
			rdbtnCadaMinuto.setSelected(true);
		} else if(value.startsWith("*/")) {
			rdbtnEnUnSalto.setSelected(true);
			spinnerMinutoSalto.setValue(Integer.parseInt(value.split("\\*\\/")[1]));
		} else if(value.matches("\\d+-\\d+")) {
			rdbtnEnUnRango.setSelected(true);
			spinnerDesde.setValue(Integer.parseInt(value.split("-")[0]));
			spinnerHasta.setValue(Integer.parseInt(value.split("-")[1]));
		}else if(value.matches("\\d+")) {
			rdbtnEnUnMinuto.setSelected(true);
			spinnerMinutoExacto.setValue(Integer.parseInt(value));
		}
	}

	/**
	 * @return the okButton
	 */
	public JButton getOkButton() {
		return okButton;
	}

	/**
	 * @return the cancelButton
	 */
	public JButton getCancelButton() {
		return cancelButton;
	}

	/**
	 * @return the agregarAgenda
	 */
	public AgregarAgenda getAgregarAgenda() {
		return agregarAgenda;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}
	
	

}
