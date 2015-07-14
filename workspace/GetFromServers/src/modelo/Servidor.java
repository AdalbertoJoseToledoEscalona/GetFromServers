/**
 * 
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import modelo.utils.XML;

/**
 * @author adalberto
 * 
 */
public class Servidor {

	/**
	 * Identificador del Servidor
	 */
	private String idServidor;
	/**
	 * Nombre del DNS o IP del Servidor
	 */
	private String nombre;
	/**
	 * Puerto de Acceso al Servidor
	 */
	private int puerto;
	/**
	 * Nombre del Protocolo de Comunicación a usar
	 */
	private String protocolo;
	/**
	 * Puerto de Acceso al Servidor
	 */
	private String usuario;
	/**
	 * Nombre del Protocolo de Comunicación a usar
	 */
	private String clave;
	/**
	 * Orden de ejecución del Servidor
	 */
	private int orden;

	private String mensaje;

	/**
	 * 
	 */
	public Servidor() {
		// TODO Auto-generated constructor stub
	}

	public boolean agregar(String textIdServidor, String textNombre,
			int valuePuerto, String textProtocolo, String textUsuario, String textClave) throws SAXException,
			IOException {
		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "servidores.xml";

		String[] campos = { "id_servidor" };

		Object[][] condiciones = { { "id_servidor", "=", // Igualdad (=, !=, >,
															// <, >=, <=)
				textIdServidor, true // if(thisValue == true){AND}else{OR}
		}, };

		int desdeFila = 0;
		int numFilas = 1;
		Object ordenarPor = "orden";
		boolean asc = false;

		boolean useDOM = false;
		if (xml.getFilas(rutaArchivoXML, campos, condiciones, desdeFila,
				numFilas, null, asc, useDOM).length == 0) {
			campos[0] = "orden";

			String resultado[][] = xml.getFilas(rutaArchivoXML, campos, null,
					desdeFila, numFilas, ordenarPor, asc, useDOM);
			int orden = 1;
			if (resultado.length > 0) {
				orden = Integer.parseInt(resultado[1][0]) + 1;
			}

			String[] camposInsert = { "id_servidor", "nombre", "puerto",
					"protocolo", "usuario", "clave", "orden" };

			String valores[][] = { { textIdServidor, textNombre,
					Integer.toString(valuePuerto), textProtocolo,
					textUsuario, textClave, Integer.toString(orden) } };

			if (xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
				error = true;
				mensaje = "No se agregó el servidor";
			}
		} else {
			error = true;
			mensaje = "Ya existe un servidor con ese Id";
		}

		return !error;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	public static String[][] lista(String[] camposObtener) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "servidores.xml";

		String[] campos = { "id_servidor", "nombre", "puerto", "protocolo", "usuario", "clave",
		"orden" };
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = null;

		Integer desdeFila = null;
		Integer numFilas = null;

		Object ordenarPor = "orden";
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, camposObtener, condiciones,
				desdeFila, numFilas, ordenarPor, asc, useDOM);

		if (resultado.length > 0) {
			return Arrays.copyOfRange(resultado, 1, resultado.length);
		} else {
			return resultado;
		}
	}

	public int actualizar(List<String> columnNames, List<Object[]> columnValues)
			throws SAXException, IOException {
		int filasActualizadas = 0;
		XML xml = new XML();

		String[] campos = { "id_servidor", "nombre", "puerto", "protocolo", "usuario", "clave", 
				"orden" };

		Object[][] camposVista = { { "Id", 0 }, { "Nombre", 1 },
				{ "Puerto", 2 }, { "Protocolo", 3 }, {"Usuario", 4}, {"Clave", 5}, { "Orden", 6 } };

		for (int i = 0; i < columnNames.size(); i++) {
			for (int j = 0; j < campos.length; j++) {
				if (columnNames.get(i).equals(camposVista[j][0])) {
					camposVista[j][1] = i;
				}
			}
		}

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "servidores.xml";
		Object[][] camposValores = new Object[columnNames.size() - 1][2];
		Object[][] condiciones = { { campos[0], "=", // Igualdad (=, !=, >, <,
														// >=, <=)
				"valor", true // = AND, false = OR, null = Don't Care // },
		} };

		for (int i = 0; i < columnValues.size(); i++) {
			condiciones[0][2] = columnValues.get(i)[(int) camposVista[0][1]];
			for (int j = 1; j < columnNames.size(); j++) {
				if (i == 0) {
					camposValores[j - 1][0] = campos[j];
				}
				camposValores[j - 1][1] = columnValues.get(i)[(int) camposVista[j][1]];

			}

			filasActualizadas += xml.updateFilas(rutaArchivoXML, camposValores,
					condiciones);
		}
		return filasActualizadas;
	}

	public int eliminar(List<Object> idServidor) throws SAXException,
			IOException {

		XML xml = new XML();

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "servidores.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		Object[][] condiciones = new Object[idServidor.size()][4];
		for (int i = 0; i < idServidor.size(); i++) {
			condiciones[i][0] = "id_servidor";
			condiciones[i][1] = "="; // Igualdad (=, !=, >, <, >=, <=)
			condiciones[i][2] = idServidor.get(i);
			condiciones[i][3] = false; // = AND, false = OR, null = Don't Care
										// //
		}

		return xml.deleteFilas(rutaArchivoXML, condiciones);

	}

	public static Servidor getById(String idServidor) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "servidores.xml";

		String[] campos = { "id_servidor", "nombre", "puerto", "protocolo", "usuario", "clave", 
		"orden" };
		
		

		Object[][] condiciones = { { "id_servidor", "=", // Igualdad (=, !=, >, <,
			// >=, <=)
			idServidor, true // = AND, false = OR, null = Don't Care // },
		} };

		Integer desdeFila = null;
		Integer numFilas = null;

		Object ordenarPor = "orden";
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, campos, condiciones,
				desdeFila, numFilas, ordenarPor, asc, useDOM);

		if (resultado.length > 0) {
			//return Arrays.copyOfRange(resultado, 1, resultado.length);
			Servidor servidor = new Servidor();
			for (int i = 1; i < resultado.length; i++) {
				servidor.setIdServidor(resultado[i][0]);
				servidor.setNombre(resultado[i][1]);
				servidor.setPuerto(Integer.parseInt(resultado[i][2]));
				servidor.setProtocolo(resultado[i][3]);
				servidor.setUsuario(resultado[i][4]);
				servidor.setClave(resultado[i][5]);
				servidor.setOrden(Integer.parseInt(resultado[i][6]));
				
			}
			return servidor;
		} else {
			return null;
		}
	}

	/**
	 * @return the idServidor
	 */
	public String getIdServidor() {
		return idServidor;
	}

	/**
	 * @param idServidor the idServidor to set
	 */
	public void setIdServidor(String idServidor) {
		this.idServidor = idServidor;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the puerto
	 */
	public int getPuerto() {
		return puerto;
	}

	/**
	 * @param puerto the puerto to set
	 */
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	/**
	 * @return the protocolo
	 */
	public String getProtocolo() {
		return protocolo;
	}

	/**
	 * @param protocolo the protocolo to set
	 */
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
	
	
}
