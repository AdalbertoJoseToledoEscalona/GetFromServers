/**
 * 
 */
package vista;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.ArchivoBuscar;
import modelo.Servidor;
import modelo.utils.beans.ComboBoxEditor;
import modelo.utils.beans.ComboBoxRenderer;

import org.xml.sax.SAXException;

import controlador.ControladorListaArchivos;

/**
 * @author adalberto
 *
 */
public class ListaArchivos {

	private JFrame ventana;
	private JPanel panelCentral;
	private JScrollPane scrollPaneTabla;
	private JTable table;
	private JButton btnGuardar;
	private JButton btnEliminar;

	/**
	 * 
	 */
	public ListaArchivos(JPanel panelListaArchivos, JFrame ventana) {
		this.ventana = ventana;
		
		JPanel panelSuperior = new JPanel();
		panelListaArchivos.add(panelSuperior, BorderLayout.NORTH);
		
		JLabel lblListaDeArchivos = new JLabel("Lista de Archivos");
		panelSuperior.add(lblListaDeArchivos);
		
		panelCentral = new JPanel();
		panelListaArchivos.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new BorderLayout(0, 0));
		
		
		
		JPanel panelInferior = new JPanel();
		panelListaArchivos.add(panelInferior, BorderLayout.SOUTH);
		
		btnGuardar = new JButton("Guardar");
		panelInferior.add(btnGuardar);
		
		btnEliminar = new JButton("Eliminar");
		panelInferior.add(btnEliminar);
		
		dibujarTabla();
		
		ControladorListaArchivos controlador = new ControladorListaArchivos(this);
		btnGuardar.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
	}
	
	
	
	public void dibujarTabla(){
		 panelCentral.removeAll();
		 
		 Object cuerpo[][] = new Object[0][0];
			try {
				String resultado[][] = ArchivoBuscar.lista(new String[] {"id_archivo", "nombre_original",
						"ruta_original", "id_servidor_original", "nombre_final", "ruta_final",
						"id_servidor_final"});
				String resultado2[][] = Servidor.lista(new String[] {"id_servidor"});
				if(resultado.length > 0){
					//cuerpo = new Object[resultado.length][resultado[0].length + 1];
					cuerpo = new Object[resultado.length][resultado[0].length];
					for (int i = 0; i < resultado.length; i++) {
						for (int j = 0; j < resultado[i].length; j++) {
							cuerpo[i][j] = resultado[i][j];
						}
						//cuerpo[i][resultado[i].length] = new JButton("Eliminar");
						
						//SERVIDOR ORIGINAL
						String temp = cuerpo[i][3].toString();
						
					    //ComboBoxModel<String> model = new SpinnerNumberModel(initValue, min, max, step);
					    JComboBox<String> comboBoxServidorOriginal = new JComboBox<String>();
					    comboBoxServidorOriginal.setModel(new DefaultComboBoxModel<String>(
								new String[] { "" }));
					    
					    int indexSelect = 0;
					    for (int j = 0; j < resultado2.length; j++) {
					    	if(temp.equals(resultado2[j][0])){
					    		indexSelect = j+1;
					    	}
							comboBoxServidorOriginal.addItem(resultado2[j][0]);
							
						}
					    
					    comboBoxServidorOriginal.setSelectedIndex(indexSelect);
					    
					    
						cuerpo[i][3] = comboBoxServidorOriginal.getModel();
						
						//SERVIDOR FINAL
						temp = cuerpo[i][6].toString();
						
						//ComboBoxModel<String> model = new SpinnerNumberModel(initValue, min, max, step);
					    JComboBox<String> comboBoxServidorFinal = new JComboBox<String>();
					    comboBoxServidorFinal.setModel(new DefaultComboBoxModel<String>(
								new String[] { "" }));
					    
					    indexSelect = 0;
					    for (int j = 0; j < resultado2.length; j++) {
					    	if(temp.equals(resultado2[j][0])){
					    		indexSelect = j+1;
					    	}
							comboBoxServidorFinal.addItem(resultado2[j][0]);
							
						}
					    
					    comboBoxServidorFinal.setSelectedIndex(indexSelect);
					    
					    
						cuerpo[i][6] = comboBoxServidorFinal.getModel();
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
					"Id", "Nombre Original", "Ruta Original", "Servidor Original", "Nombre Final", "Ruta Final", "Servidor Final"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, JComboBox.class, String.class, String.class, JComboBox.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, true, true, true, true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			table.getColumn("Servidor Original").setCellRenderer(new ComboBoxRenderer());
		    table.getColumn("Servidor Original").setCellEditor(new ComboBoxEditor());
		    
		    table.getColumn("Servidor Final").setCellRenderer(new ComboBoxRenderer());
		    table.getColumn("Servidor Final").setCellEditor(new ComboBoxEditor());
			
		    table.setAutoscrolls(true);
			table.setDragEnabled(true);
			table.setRowSelectionAllowed(true);
			table.setFillsViewportHeight(true);
		    
			scrollPaneTabla = new JScrollPane(table);
			panelCentral.add(scrollPaneTabla, BorderLayout.CENTER);
		 
		 ventana.repaint();
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



	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}

	
	
}
