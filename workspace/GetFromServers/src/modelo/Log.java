/**
 * 
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import modelo.utils.XML;

/**
 * @author adalberto
 * 
 */
public class Log {

	private Agenda agenda;
	private Servidor servidor;
	private ArchivoBuscar archivoBuscar;
	private Hilo hilo;
	/**
	 * La fecha hora en que ocurrió un evento
	 */
	private Date fechaHora;
	/**
	 * El evento ocurrido
	 */
	private String evento;
	/**
	 * El status del Evento
	 */
	private String status;
	/**
	 * La descripción del Evento
	 */
	private String descripcion;

	/**
	 * 
	 */
	public Log(Hilo hilo2, ArchivoBuscar archivoBuscar2, Servidor servidor2,
			Agenda agenda2) {
		hilo = hilo2;
		// TODO Auto-generated constructor stub
		archivoBuscar = archivoBuscar2;
		servidor = servidor2;
		agenda = agenda2;
	}

	public static String[][] lista(String[] camposObtener) throws SAXException,
			IOException {

		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "logs.xml";

		String[] campos = { "fecha_hora", "evento", "status", "descripcion",
				"id_hilo", "id_archivo", "id_servidor", "id_agenda" };

		if (camposObtener == null) {
			camposObtener = campos;
		}

		Object[][] condiciones = null;

		Integer desdeFila = null;
		Integer numFilas = null;

		// Object ordenarPor = "orden";
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, camposObtener, condiciones,
				desdeFila, numFilas, null, asc, useDOM);

		if (resultado.length > 0) {
			return Arrays.copyOfRange(resultado, 1, resultado.length);
		} else {
			return resultado;
		}
	}

	public static boolean agregar(String evento, String status,
			String descripcion, String idHilo, String idArchivo,
			String idServidor, String idAgenda) throws SAXException, IOException {
		boolean error = false;
		String mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "logs.xml";

		String[] camposInsert = { "fecha_hora", "evento", "status",
				"descripcion", "id_hilo", "id_archivo", "id_servidor",
				"id_agenda" };

		String valores[][] = { { new SimpleDateFormat(Agenda.FORMATO_FECHA).format(new Date()), evento,
				status, descripcion,
				idHilo, idArchivo, idServidor, idAgenda } };

		if (xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
			error = true;
			mensaje = "No se agregó el Log";
		}

		return !error;
	}

}
