/**
 * 
 */
package modelo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

//import controlador.ControladorVerificadorAgenda;
//import controlador.EjecutorHilo;

/**
 * @author adalberto
 *
 */
public class VerificadorAgenda extends Thread implements Runnable {

	
	/**
	 * 
	 */
	public VerificadorAgenda() {
		super();
	}

	/**
	 * @param target
	 */
	public VerificadorAgenda(Runnable target) {
		super(target);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public VerificadorAgenda(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param target
	 */
	public VerificadorAgenda(ThreadGroup group, Runnable target) {
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param name
	 */
	public VerificadorAgenda(ThreadGroup group, String name) {
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param target
	 * @param name
	 */
	public VerificadorAgenda(Runnable target, String name) {
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 */
	public VerificadorAgenda(ThreadGroup group, Runnable target, String name) {
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public VerificadorAgenda(ThreadGroup group, Runnable target, String name,
			long stackSize) {
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}
	
	public void revisarHilosCorriendo() throws SAXException, IOException, ParseException {
		
		Iterator it = Hilo.hashtable.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Hilo> type = (Map.Entry<String, Hilo>) it.next();
			Hilo eh = type.getValue();
			if(eh.corriendo) {
				List<String[]> agendasHilos = eh.bucarAgendas();
				for (int i = 0; i < agendasHilos.size(); i++) {
					//TODO: guardo el log para esta agenda y este hilo
					Hilo hilo = new Hilo(eh.getIdHilo(), eh.getIdHilo(), eh.getNombre(), eh.getAutoReproducir());
					hilo.corriendo = eh.corriendo;
					hilo.setActualIdAgenda(agendasHilos.get(i)[0]);
					hilo.start();
					Agenda.editarFechaUltEjecucion(eh.getIdHilo(), agendasHilos.get(i)[0]);
					//eh.start();
					System.out.println("Corre el hilo " + eh.getIdHilo());
					System.out.println("con la agenda " + agendasHilos.get(i)[0]);
				}
			}
			
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	synchronized public void run() {
		while(true) {
			System.out.println("comienzo");
			try {
				revisarHilosCorriendo();
				System.out.println("wait");
				wait(60000); //Espero un minuto
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
