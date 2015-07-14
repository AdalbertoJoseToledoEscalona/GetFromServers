package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controlador.ControladorPrincipal;
//import controlador.ControladorVerificadorAgenda;



import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import modelo.Hilo;
import modelo.Log;
import modelo.VerificadorAgenda;

import org.xml.sax.SAXException;

public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel panelAgregarServidor;
	private JPanel panelListaServidores;
	private JMenuItem mntmAgregarServidor;
	private JMenuItem mntmListarServidores;
	private JMenuItem mntmAgregarArchivo;
	private JMenuItem mntmListarArchivos;
	private JMenuItem mntmAgregarHilo;
	private JMenuItem mntmListarHilos;
	private JMenuItem mntmAgregarAgenda;
	private JMenuItem mntmListarAgendas;
	private JPanel panelAgregarArchivo;
	private JPanel panelListaArchivos;
	private JPanel panelAgregarHilo;
	private JPanel panelListaHilos;
	private JPanel panelAgregarAgenda;
	private JPanel panelListaAgendas;
	private JButton btnRefrescar;
	private JPanel panelCentral;

	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
					
					
					String[][] resultado = Hilo.listaAutoReproducir(null);
					
					for (int i = 0; i < resultado.length; i++) {
						Hilo hilo = new Hilo(resultado[i][0], resultado[i][0], resultado[i][1], Boolean.parseBoolean(resultado[i][2]));
						hilo.ejecutarHilo();
					}
					
					VerificadorAgenda verificadorAgenda = new VerificadorAgenda();
					
					verificadorAgenda.start();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/vista/img/icon/ftp-px.png")));
		setTitle("Grupo Tara");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 513);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("Barra de Menús");
		setJMenuBar(menuBar);
		
		JMenu mnServidores = new JMenu("Servidores");
		mnServidores.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/server.png")));
		menuBar.add(mnServidores);
		
		mntmAgregarServidor = new JMenuItem("Agregar");
		mntmAgregarServidor.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/agregar.gif")));
		mnServidores.add(mntmAgregarServidor);
		
		mntmListarServidores = new JMenuItem("Listar");
		mntmListarServidores.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/listar.jpg")));
		mnServidores.add(mntmListarServidores);
		
		JMenu mnArchivos = new JMenu("Archivos");
		mnArchivos.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/afile.png")));
		menuBar.add(mnArchivos);
		
		mntmAgregarArchivo = new JMenuItem("Agregar");
		mntmAgregarArchivo.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/agregar.gif")));
		mnArchivos.add(mntmAgregarArchivo);
		
		mntmListarArchivos = new JMenuItem("Listar");
		mntmListarArchivos.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/listar.jpg")));
		mnArchivos.add(mntmListarArchivos);
		
		JMenu mnHilos = new JMenu("Hilos");
		mnHilos.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/process.gif")));
		menuBar.add(mnHilos);
		
		mntmAgregarHilo = new JMenuItem("Agregar");
		mntmAgregarHilo.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/agregar.gif")));
		mnHilos.add(mntmAgregarHilo);
		
		mntmListarHilos = new JMenuItem("Listar");
		mntmListarHilos.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/listar.jpg")));
		mnHilos.add(mntmListarHilos);
		
		JMenu mnAgendas = new JMenu("Agendas");
		mnAgendas.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/schedule.png")));
		menuBar.add(mnAgendas);
		
		mntmAgregarAgenda = new JMenuItem("Agregar");
		mntmAgregarAgenda.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/agregar.gif")));
		mnAgendas.add(mntmAgregarAgenda);
		
		mntmListarAgendas = new JMenuItem("Listar");
		mntmListarAgendas.setIcon(new ImageIcon(Principal.class.getResource("/vista/img/icon/listar.jpg")));
		mnAgendas.add(mntmListarAgendas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panelInicio = new JPanel();
		tabbedPane.addTab("Inicio", new ImageIcon(Principal.class.getResource("/vista/img/icon/ic_inicio.gif")), panelInicio, "Inicio");
		panelInicio.setLayout(new BorderLayout(0, 0));
		
		JPanel panelSuperior = new JPanel();
		panelInicio.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblLogs = new JLabel("Logs");
		panelSuperior.add(lblLogs);
		
		panelCentral = new JPanel();
		panelInicio.add(panelCentral, BorderLayout.CENTER);
		
		
		
		JPanel panelInferior = new JPanel();
		panelInicio.add(panelInferior, BorderLayout.SOUTH);
		
		btnRefrescar = new JButton("Refrescar");
		panelInferior.add(btnRefrescar);
		
		
		
		panelAgregarServidor = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Agregar Servidor", new ImageIcon(Principal.class.getResource("/vista/img/icon/addserver.png")), panelAgregarServidor, "Agregar un Servidor");
		
		panelListaServidores = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Lista de Servidores", new ImageIcon(Principal.class.getResource("/vista/img/icon/lista_servidores.png")), panelListaServidores, "Listar los Servidores a conectar");
		
		panelAgregarArchivo = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Agregar Archivo", new ImageIcon(Principal.class.getResource("/vista/img/icon/add_file.png")), panelAgregarArchivo, "Agregar un Archivo");
		
		panelListaArchivos = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Lista de Archivos", new ImageIcon(Principal.class.getResource("/vista/img/icon/file.png")), panelListaArchivos, "Lista de Archivos a Enviar");
		
		panelAgregarHilo = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Agregar Hilo", new ImageIcon(Principal.class.getResource("/vista/img/icon/process-add-icon.png")), panelAgregarHilo, "Agregar un Hilo");
		
		panelListaHilos = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Lista de Hilos", new ImageIcon(Principal.class.getResource("/vista/img/icon/listahilos.png")), panelListaHilos, "Lista de los Hilos a ejecutar");
		
		
		
		panelAgregarAgenda = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Agregar una Agenda", new ImageIcon(Principal.class.getResource("/vista/img/icon/calendar_add.png")), panelAgregarAgenda, "Agregar una Agenda para subir y bajar archivos");

		
		panelListaAgendas = new JPanel(new BorderLayout(0, 0));
		//tabbedPane.addTab("Lista de Agendas", new ImageIcon(Principal.class.getResource("/vista/img/icon/To-Do-List.icon.gif")), panelListaAgendas, "Lista de las Agendas programadas");
	
		//pack();
		
		ControladorPrincipal controlador = new ControladorPrincipal(this);
		
		mntmAgregarServidor.addActionListener(controlador);
		mntmListarServidores.addActionListener(controlador);
		
		mntmAgregarArchivo.addActionListener(controlador);
		mntmListarArchivos.addActionListener(controlador);
		
		mntmAgregarHilo.addActionListener(controlador);
		mntmListarHilos.addActionListener(controlador);
		
		mntmAgregarAgenda.addActionListener(controlador);
		mntmListarAgendas.addActionListener(controlador);
		
		btnRefrescar.addActionListener(controlador);
		
		dibujarTabla();

	}
	
	public void dibujarTabla() {
		panelCentral.removeAll();
		
		Object cuerpo[][] = new Object[0][0];
		try {
			String resultado[][] = Log.lista(null);
			if(resultado.length > 0){
				cuerpo = new Object[resultado.length][resultado[0].length + 1];
				//cuerpo = new Object[resultado.length][resultado[0].length];
				for (int i = 0; i < resultado.length; i++) {
					for (int j = resultado[i].length - 1; j >= 0; j--) {
						cuerpo[cuerpo.length - 1 - i][j] = resultado[i][j];
					}
					//cuerpo[i][resultado[i].length] = new JButton("Editar");
					//SERVIDOR ORIGINAL
					//String temp = cuerpo[i][2].toString();
					
				    //ComboBoxModel<String> model = new SpinnerNumberModel(initValue, min, max, step);
				    //JCheckBox chxbxAutoReproducir = new JCheckBox();
				    /*comboBoxServidorOriginal.setModel(new DefaultC<String>(
							new String[] { "" }));*/
				    
				    /*int indexSelect = 0;
				    for (int j = 0; j < resultado2.length; j++) {
				    	if(temp.equals(resultado2[j][0])){
				    		indexSelect = j+1;
				    	}
						comboBoxServidorOriginal.addItem(resultado2[j][0]);
						
					}*/
				    
				    //chxbxAutoReproducir.setSelected(Boolean.parseBoolean(temp));
				    
				    
					//cuerpo[i][2] = chxbxAutoReproducir.getModel();

				}
					
				
			}
			
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			cuerpo,
			new String[] {
				"Fecha Hora", "Evento", "Status", "Descripcion", "Id Hilo", "Id Archivo", "Id Servidor", "Id Agenda"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		//table.getTableHeader().setReorderingAllowed(false) ;
		table.setAutoscrolls(true);
		table.setDragEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPaneTabla = new JScrollPane(table);
		panelCentral.add(scrollPaneTabla);
		
		repaint();
	}
	
	

	/**
	 * @return the btnRefrescar
	 */
	public JButton getBtnRefrescar() {
		return btnRefrescar;
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * @return the panelAgregarServidor
	 */
	public JPanel getPanelAgregarServidor() {
		return panelAgregarServidor;
	}

	/**
	 * @return the panelListaServidores
	 */
	public JPanel getPanelListaServidores() {
		return panelListaServidores;
	}

	/**
	 * @return the mntmAgregarServidor
	 */
	public JMenuItem getMntmAgregarServidor() {
		return mntmAgregarServidor;
	}

	/**
	 * @return the mntmListarServidores
	 */
	public JMenuItem getMntmListarServidores() {
		return mntmListarServidores;
	}

	/**
	 * @return the mntmAgregarArchivo
	 */
	public JMenuItem getMntmAgregarArchivo() {
		return mntmAgregarArchivo;
	}

	/**
	 * @return the mntmListarArchivo
	 */
	public JMenuItem getMntmListarArchivos() {
		return mntmListarArchivos;
	}

	/**
	 * @return the mntmAgregarHilo
	 */
	public JMenuItem getMntmAgregarHilo() {
		return mntmAgregarHilo;
	}

	/**
	 * @return the mntmListarHilos
	 */
	public JMenuItem getMntmListarHilos() {
		return mntmListarHilos;
	}

	/**
	 * @return the mntmAgregarAgenda
	 */
	public JMenuItem getMntmAgregarAgenda() {
		return mntmAgregarAgenda;
	}

	/**
	 * @return the mntmListarAgendas
	 */
	public JMenuItem getMntmListarAgendas() {
		return mntmListarAgendas;
	}

	/**
	 * @return the panelAgregarArchivo
	 */
	public JPanel getPanelAgregarArchivo() {
		return panelAgregarArchivo;
	}

	/**
	 * @return the panelListaArchivo
	 */
	public JPanel getPanelListaArchivos() {
		return panelListaArchivos;
	}

	/**
	 * @return the panelAgregarHilo
	 */
	public JPanel getPanelAgregarHilo() {
		return panelAgregarHilo;
	}

	/**
	 * @return the panelListaHilos
	 */
	public JPanel getPanelListaHilos() {
		return panelListaHilos;
	}

	/**
	 * @return the panelAgregarAgenda
	 */
	public JPanel getPanelAgregarAgenda() {
		return panelAgregarAgenda;
	}

	/**
	 * @return the panelListaAgendas
	 */
	public JPanel getPanelListaAgendas() {
		return panelListaAgendas;
	}

	
	
}
