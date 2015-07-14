/**
 * 
 */
package controlador;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.AgregarAgenda;
import vista.AgregarArchivo;
import vista.AgregarHilo;
import vista.AgregarServidor;
import vista.ListaAgendas;
import vista.ListaArchivos;
import vista.ListaHilos;
import vista.ListaServidores;
import vista.Principal;

/**
 * @author adalberto
 * 
 */
public class ControladorPrincipal implements ActionListener {

	private Principal principal;

	/**
	 * @param principal
	 * 
	 */
	public ControladorPrincipal(Principal principal) {
		this.principal = principal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("x")){
			Component selected = principal.getTabbedPane().getSelectedComponent();
	        if (selected != null) {

	        	principal.getTabbedPane().remove(selected);
	            // It would probably be worthwhile getting the source
	            // casting it back to a JButton and removing
	            // the action handler reference ;)

	        }
			
			
			/*int index = principal.getTabbedPane().indexOfTab(principal.getTabbedPane().getName());
	        if (index >= 0) {

	        	principal.getTabbedPane().removeTabAt(index);
	            // It would probably be worthwhile getting the source
	            // casting it back to a JButton and removing
	            // the action handler reference ;)

	        }*/
			
		} else if (e.getSource().equals(principal.getMntmAgregarServidor())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelAgregarServidor())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelAgregarServidor().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				//Agrego los Campos que le corresponde
				new AgregarServidor(principal.getPanelAgregarServidor(), principal);
				
				//Agrego el Panel que le Corresponde
				principal.getTabbedPane().addTab(
						"Agregar Servidor",
						new ImageIcon(Principal.class
								.getResource("/vista/img/icon/addserver.png")),
						principal.getPanelAgregarServidor(),
						"Agregar un Servidor");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				//Agrego la x de Cerrar la Pestaña
				final String tabName = "Agregar Servidor";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelAgregarServidor().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmListarServidores())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelListaServidores())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelListaServidores().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				new ListaServidores(principal.getPanelListaServidores(), principal);
				
				
				principal
						.getTabbedPane()
						.addTab("Lista de Servidores",
								new ImageIcon(
										Principal.class
												.getResource("/vista/img/icon/lista_servidores.png")),
								principal.getPanelListaServidores(),
								"Listar los Servidores a conectar");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				final String tabName = "Lista de Servidores";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelListaServidores().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmAgregarArchivo())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelAgregarArchivo())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelAgregarArchivo().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				
				//Agrego los Campos que le corresponde
				new AgregarArchivo(principal.getPanelAgregarArchivo(), principal);
				
				principal.getTabbedPane().addTab(
						"Agregar Archivo",
						new ImageIcon(Principal.class
								.getResource("/vista/img/icon/add_file.png")),
						principal.getPanelAgregarArchivo(),
						"Agregar un Archivo");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				final String tabName = "Agregar Archivo";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelAgregarArchivo().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmListarArchivos())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelListaArchivos())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelListaArchivos().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				
				//Agrego los Campos que le corresponde
				new ListaArchivos(principal.getPanelListaArchivos(), principal);
				
				principal.getTabbedPane().addTab(
						"Lista de Archivos",
						new ImageIcon(Principal.class
								.getResource("/vista/img/icon/file.png")),
						principal.getPanelListaArchivos(),
						"Lista de Archivos a Enviar");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				final String tabName = "Lista de Archivos";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelListaArchivos().removeAll();
				        }
					}
				});
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmAgregarHilo())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelAgregarHilo())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelAgregarHilo().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				//Agrego los Campos que le corresponde
				new AgregarHilo(principal.getPanelAgregarHilo(), principal, null, null, null);
				
				principal
						.getTabbedPane()
						.addTab("Agregar Hilo",
								new ImageIcon(
										Principal.class
												.getResource("/vista/img/icon/process-add-icon.png")),
								principal.getPanelAgregarHilo(),
								"Agregar un Hilo");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				final String tabName = "Agregar Hilo";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelAgregarHilo().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmListarHilos())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelListaHilos())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelListaHilos().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				//Agrego los Campos que le corresponde
				new ListaHilos(principal.getPanelListaHilos(), principal);
				
				principal
						.getTabbedPane()
						.addTab("Lista de Hilos",
								new ImageIcon(
										Principal.class
												.getResource("/vista/img/icon/listahilos.png")),
								principal.getPanelListaHilos(),
								"Lista de los Hilos a ejecutar");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				final String tabName = "Lista de Hilos";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelListaHilos().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmAgregarAgenda())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelAgregarAgenda())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelAgregarAgenda().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				
				//Agrego los Campos que le corresponde
				new AgregarAgenda(principal.getPanelAgregarAgenda(), principal, null, null, null, null, null, null, null, null, null);
				
				principal
						.getTabbedPane()
						.addTab("Agregar una Agenda",
								new ImageIcon(
										Principal.class
												.getResource("/vista/img/icon/calendar_add.png")),
								principal.getPanelAgregarAgenda(),
								"Agregar una Agenda para subir y bajar archivos");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				
				final String tabName = "Agregar una Agenda";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelAgregarAgenda().removeAll();
				        }
					}
				});
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getMntmListarAgendas())) {
			boolean consiguio = false;
			for (int i = 0; i < principal.getTabbedPane().getTabCount(); i++) {
				if (principal.getTabbedPane().getComponentAt(i)
						.equals(principal.getPanelListaAgendas())) {
					principal.getTabbedPane().removeTabAt(i);
					principal.getPanelListaAgendas().removeAll();
					consiguio = true;
					break;
				}
			}
			if (!consiguio) {
				
				//Agrego los Campos que le corresponde
				new ListaAgendas(principal.getPanelListaAgendas(), principal);
				
				principal
						.getTabbedPane()
						.addTab("Lista de Agendas",
								new ImageIcon(
										Principal.class
												.getResource("/vista/img/icon/To-Do-List.icon.gif")),
								principal.getPanelListaAgendas(),
								"Lista de las Agendas programadas");
				principal.getTabbedPane().setSelectedIndex(
						principal.getTabbedPane().getTabCount() - 1);
				
				
				final String tabName = "Lista de Agendas";
				//tabbedPane.addTab(title, panelInicio);
				int index = principal.getTabbedPane().indexOfTab(tabName);
				JPanel pnlTab = new JPanel(new GridBagLayout());
				pnlTab.setOpaque(false);
				JLabel lblTitle = new JLabel(tabName);
				JButton btnClose = new JButton("x");

				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.weightx = 1;

				pnlTab.add(lblTitle, gbc);

				gbc.gridx++;
				gbc.weightx = 0;
				pnlTab.add(btnClose, gbc);

				principal.getTabbedPane().setTabComponentAt(index, pnlTab);
				
				//btnClose.addActionListener(this);
				
				btnClose.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = principal.getTabbedPane().indexOfTab(tabName);
				        if (index >= 0) {

				        	principal.getTabbedPane().removeTabAt(index);
				            // It would probably be worthwhile getting the source
				            // casting it back to a JButton and removing
				            // the action handler reference ;)
				        	principal.getPanelListaAgendas().removeAll();
				        }
					}
				});
				
			}
			// principal.pack();
		} else if (e.getSource().equals(principal.getBtnRefrescar())) {
			principal.dibujarTabla();
		}
	}

}
