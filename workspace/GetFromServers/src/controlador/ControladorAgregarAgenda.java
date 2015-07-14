/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Agenda;
import modelo.Hilo;

import org.xml.sax.SAXException;

import vista.AgregarAgenda;
import vista.EditarValorAgenda;

/**
 * @author adalberto
 *
 */
public class ControladorAgregarAgenda implements ActionListener {

	private AgregarAgenda agregarAgenda;

	/**
	 * @param agregarAgenda 
	 * 
	 */
	public ControladorAgregarAgenda(AgregarAgenda agregarAgenda) {
		this.agregarAgenda = agregarAgenda;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(agregarAgenda.getBtnAgregarHilo())) {
			agregarAgenda.agregarHilo(null);
		} else if(e.getSource().equals(agregarAgenda.getBtnQuitarHilo())) {
			agregarAgenda.quitarHilo();
		} else if(e.getSource().equals(agregarAgenda.getBtnEditarMinuto())) {
			new EditarValorAgenda(agregarAgenda, 0, 59, "Minuto", agregarAgenda.getTxtMinuto());
		} else if(e.getSource().equals(agregarAgenda.getBtnEditarHora())) {
			new EditarValorAgenda(agregarAgenda, 0, 59, "Hora", agregarAgenda.getTxtHora());
		} else if(e.getSource().equals(agregarAgenda.getBtnEditarDia())) {
			new EditarValorAgenda(agregarAgenda, 0, 23, "Dia", agregarAgenda.getTxtDia());
		} else if(e.getSource().equals(agregarAgenda.getBtnEditarMes())) {
			new EditarValorAgenda(agregarAgenda, 1, 12, "Mes", agregarAgenda.getTxtMes());
		} else if(e.getSource().equals(agregarAgenda.getBtnEditarDiaSemana())) {
			new EditarValorAgenda(agregarAgenda, 1, 7, "Dia de Semana", agregarAgenda.getTxtDiaSemana());
		} else if (e.getSource().equals(agregarAgenda.getBtnAgregar())) {
			
			if (agregarAgenda.getTxtIdAgenda().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarAgenda.getVentana(),
						"El Campo Id Agenda es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (agregarAgenda.getTxtNombre().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarAgenda.getVentana(),
						"El Campo Nombre es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else if (agregarAgenda.getSpinnerFechaHoraInicial().trim().length() == 0) {
				JOptionPane.showMessageDialog(agregarAgenda.getVentana(),
						"El Campo Fecha Inicial es requerido", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {

				boolean error = false;
				List<String> hilos = new ArrayList<String>();
				for (int i = 0; i < agregarAgenda.getCmbHilos().size(); i++) {
					if (agregarAgenda.getCmbHilos().get(i).getSelectedIndex() == 0) {
						error = true;

						JOptionPane.showMessageDialog(agregarAgenda.getVentana(),
								"El Campo Hilo " + (i + 1) + " requerido",
								"Error", JOptionPane.ERROR_MESSAGE);

					} else if (hilos.contains(agregarAgenda.getCmbHilos()
							.get(i).getSelectedItem().toString())) {
						error = true;

						JOptionPane
								.showMessageDialog(
										agregarAgenda.getVentana(),
										"No puede elegir más de una vez un hilo para una misma agenda",
										"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						hilos.add(agregarAgenda.getCmbHilos().get(i)
								.getSelectedItem().toString());
					}
				}

				if (!error) {
					try {
						Agenda agenda = new Agenda();
						if (e.getActionCommand().equalsIgnoreCase("Agregar")) {
							if (agenda.agregar(agregarAgenda.getTxtIdAgenda(),
									agregarAgenda.getTxtNombre(), agregarAgenda.getIsSoloUnaVez(),
									agregarAgenda.getSpinnerFechaHoraInicial(),
									agregarAgenda.getTxtMinuto(), agregarAgenda.getTxtHora(), agregarAgenda.getTxtDia(),
									agregarAgenda.getTxtMes(), agregarAgenda.getTxtDiaSemana(), hilos)) {
								JOptionPane.showMessageDialog(
										agregarAgenda.getVentana(),
										"Agenda Agregada con Éxito", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);
								agregarAgenda.limpiar();
							} else {
								JOptionPane.showMessageDialog(
										agregarAgenda.getVentana(),
										agenda.getMensaje(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						} else if (e.getActionCommand().equalsIgnoreCase("Editar")) {
							if (agenda.editar(agregarAgenda.getTxtIdAgenda(),
									agregarAgenda.getTxtNombre(), agregarAgenda.getIsSoloUnaVez(),
									agregarAgenda.getSpinnerFechaHoraInicial(),
									agregarAgenda.getTxtMinuto(), agregarAgenda.getTxtHora(), agregarAgenda.getTxtDia(),
									agregarAgenda.getTxtMes(), agregarAgenda.getTxtDiaSemana(), hilos)) {
								JOptionPane.showMessageDialog(
										agregarAgenda.getVentana(),
										"Agenda Actualizada con Éxito", "Éxito",
										JOptionPane.INFORMATION_MESSAGE);
								//agregarAgenda.limpiar();
							} else {
								JOptionPane.showMessageDialog(
										agregarAgenda.getVentana(),
										agenda.getMensaje(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (SAXException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(agregarAgenda.getVentana(),
								e1.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		}
	}

}
