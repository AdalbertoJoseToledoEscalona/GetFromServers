/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import modelo.Hilo;

import org.xml.sax.SAXException;

import controlador.ControladorListaHilos;
//import controlador.EjecutorHilo;

/**
 * @author adalberto
 *
 */
public class ListaHilos {

	private JPanel panelCentral;
	//private JButton btnGuardar;
	private JButton btnEjecutar;
	private JButton btnEliminar;
	private Principal ventana;
	private ControladorListaHilos controlador;
	private JTable table;

	/**
	 * 
	 */
	public ListaHilos(JPanel panelListaHilos, Principal ventana) {
		this.ventana = ventana;
		
		JPanel panelSuperior = new JPanel();
		panelListaHilos.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel label = new JLabel("New label");
		panelSuperior.add(label);
		
		JLabel lblMensaje = new JLabel("Lista de Hilos");
		panelSuperior.add(lblMensaje);
		
		panelCentral = new JPanel();
		panelListaHilos.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		
		
		JPanel panelInferior = new JPanel();
		panelListaHilos.add(panelInferior, BorderLayout.SOUTH);
		
		/*btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar);*/
		
		btnEjecutar = new JButton("Ejecutar");
		panelInferior.add(btnEjecutar);
		
		btnEliminar = new JButton("Eliminar");
		panelInferior.add(btnEliminar);
		
		controlador = new ControladorListaHilos(this);
		
		btnEliminar.addActionListener(controlador);
		
		dibujarTabla();
	}
	
	public void dibujarTabla(){
		panelCentral.removeAll();
		
		Object cuerpo[][] = new Object[0][0];
		try {
			String resultado[][] = Hilo.lista(null);
			if(resultado.length > 0){
				cuerpo = new Object[resultado.length][resultado[0].length + 3];
				//cuerpo = new Object[resultado.length][resultado[0].length];
				for (int i = 0; i < resultado.length; i++) {
					for (int j = 0; j < resultado[i].length; j++) {
						cuerpo[i][j] = resultado[i][j];
					}
					Hilo hilo = new Hilo();
					hilo.setIdHilo(cuerpo[i][0].toString());
					
					cuerpo[i][resultado[i].length] = hilo.estaCorriendo();
					cuerpo[i][resultado[i].length + 1] = new JButton("Editar");
					cuerpo[i][resultado[i].length + 2] = new JButton((boolean) cuerpo[i][resultado[i].length] ? "Detener" : "Ejecutar");
					
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
					ventana, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			cuerpo,
			new String[] {
				"Id", "Nombre", "Auto Reproducir", "Corriendo", "Editar", "Ejecutar/Detener"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Boolean.class, JButton.class, JButton.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		

		//table.getColumn("Auto Reproducir").setCellRenderer(new CheckBoxRenderer());
	    //table.getColumn("Auto Reproducir").setCellEditor(new CheckBoxEditor());
	    
	 // El objetivo de la siguiente línea es indicar el CellRenderer que será utilizado para dibujar el botón
		table.setDefaultRenderer(JComponent.class, new TableCellRenderer() {
			
        	 @Override
             public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                 /**
                  * Observen que todo lo que hacemos en éste método es retornar el objeto que se va a dibujar en la 
                  * celda. Esto significa que se dibujará en la celda el objeto que devuelva el TableModel. También 
                  * significa que este renderer nos permitiría dibujar cualquier objeto gráfico en la grilla, pues 
                  * retorna el objeto tal y como lo recibe.
                  */
                 return (Component) objeto;
             }
         });
		
		/**
         * Por último, agregamos un listener que nos permita saber cuando fue pulsada la celda que contiene el botón.
         * Noten que estamos capturando el clic sobre JTable, no el clic sobre el JButton. Esto también implica que en 
         * lugar de poner un botón en la celda, simplemente pudimos definir un CellRenderer que hiciera parecer que la 
         * celda contiene un botón. Es posible capturar el clic del botón, pero a mi parecer el efecto es el mismo y 
         * hacerlo de esta forma es más "simple"
         */
		table.addMouseListener(controlador);
		table.setAutoscrolls(true);
		table.setDragEnabled(true);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);

		
		JScrollPane scrollPaneTabla = new JScrollPane(table);
		panelCentral.add(scrollPaneTabla, BorderLayout.CENTER);
		
		ventana.repaint();
	}

	/**
	 * @return the btnGuardar
	 */
	/*public JButton getBtnGuardar() {
		return btnGuardar;
	}*/

	/**
	 * @return the btnEjecutar
	 */
	public JButton getBtnEjecutar() {
		return btnEjecutar;
	}

	/**
	 * @return the btnEliminar
	 */
	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	/**
	 * @return the ventana
	 */
	public Principal getVentana() {
		return ventana;
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}
}
