/**
 * 
 */
package controlador;

import java.util.Hashtable;

import modelo.Hilo;


/**
 * @author adalberto
 *
 */
public class EjecutorHilo implements Runnable {

	public static Hashtable<String, EjecutorHilo> hashtable = new Hashtable<String, EjecutorHilo>();
	public Hilo hilo;
	public boolean corriendo;

	/**
	 * 
	 */
	public EjecutorHilo(Hilo hilo2) {
		hilo = hilo2;
		// TODO Auto-generated constructor stub
	}
	
	

	public EjecutorHilo() {
		super();
	}

	/**
	 * @return the hilo
	 */
	public Hilo getHilo() {
		return hilo;
	}



	/**
	 * @param hilo the hilo to set
	 */
	public void setHilo(Hilo hilo) {
		this.hilo = hilo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(hashtable.containsKey(hilo.getIdHilo()) && corriendo) { 
			System.out.println("Verificar lo que quedó pendiente del pasado (en los logs)");
			
			System.out.println("Ver los archivos que tengo");
			
			System.out.println("Por cada archivo conectarse al servidor original, copiar el archivo, desconectarse y conectarse"
					+ "en el servidor destino para pegar el archivo con el nombre especificado");
		
		}
		
		System.out.println("SALGO DEL RUN");
	}
	
	public static void ejecutarHilo(String idHilo, String nombre, boolean autoReproducir) {
		if(!hashtable.containsKey(idHilo)){

			EjecutorHilo eh = new EjecutorHilo();
			eh.corriendo = true;
			Hilo hilo = new Hilo(eh, idHilo, idHilo, nombre, autoReproducir);
			eh.setHilo(hilo);
			
			hashtable.put(hilo.getIdHilo(), eh);
			hilo.start();
		} else {
			EjecutorHilo eh = hashtable.get(idHilo);
			if(!eh.corriendo) {
				eh.corriendo = true;
				eh.hilo.start();
			}
		}
	}
	
	public static void detenerHilo(String idHilo) {
		if(hashtable.containsKey(idHilo)){
			EjecutorHilo eh = hashtable.get(idHilo);
			if(eh.corriendo) {
				eh.corriendo = false;
			}
			eh.hilo.notify();
			hashtable.remove(idHilo);
		}
	}
	
	public static boolean estaCorriendo(String idHilo) {
		if(hashtable.containsKey(idHilo)){
			EjecutorHilo eh = hashtable.get(idHilo);
			return eh.corriendo;
		}else{
			return false;
		}
	}

}
