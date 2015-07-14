/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import modelo.Servidor;
import modelo.utils.beans.SpinnerEditor;
import modelo.utils.beans.SpinnerRenderer;

import org.xml.sax.SAXException;

import controlador.ControladorListaServidores;

/**
 * @author adalberto
 *
 */
public class ListaServidores {

	/**
	 * 
	 */
	private JTable table;
	private JButton btnGuardar;
	private JFrame ventana;
	private JPanel panelCentral;
	//private ControladorListaServidores controlador;
	private JButton btnEliminar;
	
	public ListaServidores(JPanel panelListaServidores, JFrame ventana) {
		this.ventana = ventana;
		
		JPanel panelSuperior = new JPanel();
		panelListaServidores.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblTitulo = new JLabel("Lista de Servidores");
		panelSuperior.add(lblTitulo);
		
		
		
		panelCentral = new JPanel(new BorderLayout());
		panelListaServidores.add(panelCentral, BorderLayout.CENTER);
		
		
		
		
		JPanel panelInferior = new JPanel();
		panelListaServidores.add(panelInferior, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar);
		
		btnEliminar = new JButton("Eliminar Filas Seleccionadas");
		panelInferior.add(btnEliminar);
		
		ControladorListaServidores controlador = new ControladorListaServidores(this);
		btnGuardar.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
		
		
		dibujarTabla();
		
		
	}

	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * @return the btnGuardar
	 */
	public JButton getBtnGuardar() {
		return btnGuardar;
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
	public JFrame getVentana() {
		return ventana;
	}


	 public void dibujarTabla(){
		 panelCentral.removeAll();
		 
		 
		 Object cuerpo[][] = new Object[0][0];
			try {
				String resultado[][] = Servidor.lista(null);
				if(resultado.length > 0){
					//cuerpo = new Object[resultado.length][resultado[0].length + 1];
					cuerpo = new Object[resultado.length][resultado[0].length];
					for (int i = 0; i < resultado.length; i++) {
						for (int j = 0; j < resultado[i].length; j++) {
							cuerpo[i][j] = resultado[i][j];
						}
						//cuerpo[i][resultado[i].length] = new JButton("Eliminar");
						
						//SPINNER PUERTO
						String temp = cuerpo[i][2].toString();
						
						int min = 0;
					    int max = 65535;
					    int step = 1;
					    int initValue = Integer.parseInt(temp);
					    SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
					    /*JSpinner spinner = new JSpinner(model);
						JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "####");
						spinner.setEditor(editor); 
						spinner.setToolTipText("Puerto de Conexión con el Servidor");*/
						
						/*JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
						((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);*/
						
						//cuerpo[i][2] = spinner;
						cuerpo[i][2] = model;
						
						//SPINNER ORDEN
						temp = cuerpo[i][6].toString();
						
						min = 0;
					    max = Integer.MAX_VALUE;
					    step = 1;
					    initValue = Integer.parseInt(temp);
					    model = new SpinnerNumberModel(initValue, min, max, step);
					    /*spinner = new JSpinner(model);
						editor = new JSpinner.NumberEditor(spinner, "####");
						spinner.setEditor(editor); 
						spinner.setToolTipText("Orden");
						
						txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
						((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);*/
						
						//cuerpo[i][4] = spinner;
					    cuerpo[i][6] = model;
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
			
			//String[][] cuerpoTabla = new String[resultado.length][]
			
			table = new JTable();
			table.setModel(new DefaultTableModel(
				cuerpo,
				new String[] {
					/*"Seleccion",*/ "Id", "Nombre", "Puerto", "Protocolo", "Usuario", "Clave", "Orden"/*, "Eliminar"*/
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					/*Boolean.class,*/ String.class, String.class, JSpinner.class, String.class, String.class, String.class, JSpinner.class/*, JButton.class*/
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					/*true,*/ false, true, true, true, true, true, true/*, false*/
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		
			table.getColumn("Puerto").setCellRenderer(new SpinnerRenderer());
		    table.getColumn("Puerto").setCellEditor(new SpinnerEditor());
		    
		    table.getColumn("Orden").setCellRenderer(new SpinnerRenderer());
		    table.getColumn("Orden").setCellEditor(new SpinnerEditor());
			
			// El objetivo de la siguiente línea es indicar el CellRenderer que será utilizado para dibujar el botón
//			table.setDefaultRenderer(JComponent.class, new TableCellRenderer() {
//				
//	        	 @Override
//	             public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
//	                 /**
//	                  * Observen que todo lo que hacemos en éste método es retornar el objeto que se va a dibujar en la 
//	                  * celda. Esto significa que se dibujará en la celda el objeto que devuelva el TableModel. También 
//	                  * significa que este renderer nos permitiría dibujar cualquier objeto gráfico en la grilla, pues 
//	                  * retorna el objeto tal y como lo recibe.
//	                  */
//	                 return (Component) objeto;
//	             }
//	         });

	        /**
	         * Por último, agregamos un listener que nos permita saber cuando fue pulsada la celda que contiene el botón.
	         * Noten que estamos capturando el clic sobre JTable, no el clic sobre el JButton. Esto también implica que en 
	         * lugar de poner un botón en la celda, simplemente pudimos definir un CellRenderer que hiciera parecer que la 
	         * celda contiene un botón. Es posible capturar el clic del botón, pero a mi parecer el efecto es el mismo y 
	         * hacerlo de esta forma es más "simple"
	         */
//			table.addMouseListener(new MouseAdapter() {
//	            @Override
//	            public void mouseClicked(MouseEvent e) {
//	                int fila = table.rowAtPoint(e.getPoint());
//	                int columna = table.columnAtPoint(e.getPoint());
	//
//	                /**
//	                 * Preguntamos si hicimos clic sobre la celda que contiene el botón, si tuviéramos más de un botón 
//	                 * por fila tendríamos que además preguntar por el contenido del botón o el nombre de la columna
//	                 */
//	                if (table.getModel().getColumnClass(columna).equals(JButton.class)) {
//	                    /**
//	                     * Aquí pueden poner lo que quieran, para efectos de este ejemplo, voy a mostrar
//	                     * en un cuadro de dialogo todos los campos de la fila que no sean un botón.
//	                     */
//	                    StringBuilder sb = new StringBuilder();
//	                    for (int i = 0; i < table.getModel().getColumnCount(); i++) {
//	                        if (!table.getModel().getColumnClass(i).equals(JButton.class)) {
//	                            sb.append("\n").append(table.getModel().getColumnName(i)).append(": ").append(table.getModel().getValueAt(fila, i));
//	                        }
//	                    }
//	                    JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila + sb.toString());
//	                }
//	            }
//	        });
		    //table.getTableHeader().setReorderingAllowed(false) ;
			table.setAutoscrolls(true);
			table.setDragEnabled(true);
			table.setRowSelectionAllowed(true);
			table.setFillsViewportHeight(true);
			
			JScrollPane scrollPaneTabla = new JScrollPane(table);
			panelCentral.add(scrollPaneTabla, BorderLayout.CENTER);
			
			/**
	         * Por último, agregamos un listener que nos permita saber cuando fue pulsada la celda que contiene el botón.
	         * Noten que estamos capturando el clic sobre JTable, no el clic sobre el JButton. Esto también implica que en 
	         * lugar de poner un botón en la celda, simplemente pudimos definir un CellRenderer que hiciera parecer que la 
	         * celda contiene un botón. Es posible capturar el clic del botón, pero a mi parecer el efecto es el mismo y 
	         * hacerlo de esta forma es más "simple"
	         */
			//table.addMouseListener(controlador);
			
			ventana.repaint();
	 }
}
