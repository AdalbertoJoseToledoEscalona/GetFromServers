/**
 * 
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.EditarValorAgenda;

/**
 * @author adalberto
 *
 */
public class ControladorEditarValorAgenda implements ActionListener {

	private EditarValorAgenda editarValorAgenda;

	/**
	 * @param editarValorAgenda 
	 * 
	 */
	public ControladorEditarValorAgenda(EditarValorAgenda editarValorAgenda) {
		this.editarValorAgenda = editarValorAgenda;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(editarValorAgenda.getOkButton())) {
			if(editarValorAgenda.getCampo().equalsIgnoreCase("minuto")) {
				editarValorAgenda.getAgregarAgenda().setTxtMinuto(editarValorAgenda.getValue());
			} else if(editarValorAgenda.getCampo().equalsIgnoreCase("hora")) {
				editarValorAgenda.getAgregarAgenda().setTxtHora(editarValorAgenda.getValue());
			} else if(editarValorAgenda.getCampo().equalsIgnoreCase("dia")) {
				editarValorAgenda.getAgregarAgenda().setTxtDia(editarValorAgenda.getValue());
			} else if(editarValorAgenda.getCampo().equalsIgnoreCase("mes")) {
				editarValorAgenda.getAgregarAgenda().setTxtMes(editarValorAgenda.getValue());
			} else if(editarValorAgenda.getCampo().equalsIgnoreCase("dia de semana")) {
				editarValorAgenda.getAgregarAgenda().setTxtDiaSemana(editarValorAgenda.getValue());
			}
			
			editarValorAgenda.setVisible(false);
			editarValorAgenda.dispose();
			editarValorAgenda = null;
		} else if(e.getSource().equals(editarValorAgenda.getCancelButton())) {
			editarValorAgenda.setVisible(false);
			editarValorAgenda.dispose();
			editarValorAgenda = null;
		}
	}

}
