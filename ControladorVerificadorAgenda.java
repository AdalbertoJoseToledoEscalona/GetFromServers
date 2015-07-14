/**
 * 
 */
package controlador;

import java.io.IOException;
import java.text.ParseException;

import org.xml.sax.SAXException;

import modelo.VerificadorAgenda;

/**
 * @author adalberto
 *
 */
public class ControladorVerificadorAgenda implements Runnable {

	/**
	 * 
	 */
	VerificadorAgenda verificadorAgenda;
	
	public ControladorVerificadorAgenda() {
		
	}
	
	

	/**
	 * @param verificadorAgenda the verificadorAgenda to set
	 */
	public void setVerificadorAgenda(VerificadorAgenda verificadorAgenda) {
		this.verificadorAgenda = verificadorAgenda;
	}



	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	synchronized public void run() {
		while(true) {
			System.out.println("comienzo");
			try {
				verificadorAgenda.revisarHilosCorriendo();
				System.out.println("wait");
				verificadorAgenda.wait(60000); //Espero un minuto
			} catch (InterruptedException | SAXException | IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
