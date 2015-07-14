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
public class ArchivoBuscar {

	private Servidor servidorFinal;
	private Servidor servidorOriginal;
	/**
	 * Identificador del Archivo
	 */
	private String idArchivo;
	/**
	 * Nombre del Archivo en el Servidor Origen
	 */
	private String nombreOriginal;
	/**
	 * La dirección donde se ubica el Archivo en el Servidor Original
	 */
	private String rutaOriginal;
	/**
	 * Nombre como va a quedar el archivo después que sea copiado en el Servidor
	 * Destino. Si está NULL o vacío, el nombreFinal = nombreOriginal Se puede
	 * colocar palabras clave como: [nombreOriginal] El nombre (sin extension)
	 * del archivo Original [fecha] La fecha del sistema en formato SQL [hora]
	 * La hora del sistema SQL [fecha_hora] la fecha y hora del sistema en
	 * formato SQL
	 */
	private String nombreFinal;
	/**
	 * Ruta del Archivo donde se va a copiar en el Servidor destino
	 */
	private String rutaFinal;
	private String mensaje;

	/**
	 * 
	 */
	public ArchivoBuscar(Servidor servidorOriginal, Servidor servidorFinal) {
		super();
		this.servidorOriginal = servidorOriginal;
		this.servidorFinal = servidorFinal;
	}

	public ArchivoBuscar() {
	}

	public boolean agregar(String textIdArchivo, String textNombreOriginal,
			String textRutaOriginal, String textNombreFinal,
			String textRutaFinal, String selectedItemComboBoxServidorOriginal,
			String selectedItemComboBoxServidorFinal) throws SAXException,
			IOException {

		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_buscar.xml";

		String[] campos = { "id_archivo" };

		Object[][] condiciones = { { "id_archivo", "=", // Igualdad (=, !=, >,
														// <, >=, <=)
				textIdArchivo, true // if(thisValue == true){AND}else{OR}
		}, };

		int desdeFila = 0;
		int numFilas = 1;
		// Object ordenarPor = "orden";
		boolean asc = false;

		boolean useDOM = false;
		if (xml.getFilas(rutaArchivoXML, campos, condiciones, desdeFila,
				numFilas, null, asc, useDOM).length == 0) {
			//campos[0] = "orden";

			// String resultado[][] = xml.getFilas(rutaArchivoXML, campos, null,
			// desdeFila, numFilas, ordenarPor, asc, useDOM);
			// //int orden = 1;
			// if (resultado.length > 0) {
			// orden = Integer.parseInt(resultado[1][0]) + 1;
			// }

			String[] camposInsert = { "id_archivo", "nombre_original",
					"ruta_original", "nombre_final", "ruta_final",
					"id_servidor_original", "id_servidor_final" };

			String valores[][] = { { textIdArchivo, textNombreOriginal,
					textRutaOriginal, textNombreFinal, textRutaFinal,
					selectedItemComboBoxServidorOriginal,
					selectedItemComboBoxServidorFinal } };

			if (xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
				error = true;
				mensaje = "No se agregó el servidor";
			}
		} else {
			error = true;
			mensaje = "Ya existe un Archivo con ese Id";
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
				+ File.separator + "archivos_buscar.xml";

		String[] campos = { "id_archivo", "nombre_original",
				"ruta_original", "nombre_final", "ruta_final",
				"id_servidor_original", "id_servidor_final" };
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = null;

		Integer desdeFila = null;
		Integer numFilas = null;

		Object ordenarPor = null;
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

	public int actualizar(List<String> columnNames, List<Object[]> columnValues) throws SAXException, IOException {
		int filasActualizadas = 0;
		XML xml = new XML();

		String[] campos = { "id_archivo", "nombre_original",
				"ruta_original", "id_servidor_original", "nombre_final", "ruta_final",
				 "id_servidor_final" };;

		Object[][] camposVista = { { "Id", 0 }, { "Nombre Original", 1 },
				{ "Ruta Original", 2 }, { "Servidor Original", 3 }, { "Nombre Final", 4 },
				{ "Ruta Final", 5 }, { "Servidor Final", 6 }};

		for (int i = 0; i < columnNames.size(); i++) {
			for (int j = 0; j < campos.length; j++) {
				if (columnNames.get(i).equals(camposVista[j][0])) {
					camposVista[j][1] = i;
				}
			}
		}

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_buscar.xml";
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

	public int eliminar(List<Object> idArchivos) throws SAXException, IOException {
		
		XML xml = new XML();

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_buscar.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		Object[][] condiciones = new Object[idArchivos.size()][4];
		for (int i = 0; i < idArchivos.size(); i++) {
			condiciones[i][0] = "id_archivo";
			condiciones[i][1] = "="; // Igualdad (=, !=, >, <, >=, <=)
			condiciones[i][2] = idArchivos.get(i);
			condiciones[i][3] = false; // = AND, false = OR, null = Don't Care
										// //
		}

		return xml.deleteFilas(rutaArchivoXML, condiciones);
		
	}

	public static ArchivoBuscar getById(String idArchivo) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_buscar.xml";

		String[] campos = { "id_archivo", "nombre_original",
				"ruta_original", "nombre_final", "ruta_final",
				"id_servidor_original", "id_servidor_final" };
		
		
		

		Object[][] condiciones = { { "id_archivo", "=", // Igualdad (=, !=, >, <,
			// >=, <=)
			idArchivo, true // = AND, false = OR, null = Don't Care // },
		} };

		Integer desdeFila = null;
		Integer numFilas = null;

		Object ordenarPor = null;
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, campos, condiciones,
				desdeFila, numFilas, ordenarPor, asc, useDOM);
		
		

		if (resultado.length > 0) {
			ArchivoBuscar archivoBuscar = new ArchivoBuscar();
			for (int i = 1; i < resultado.length; i++) {
				archivoBuscar.setIdArchivo(resultado[i][0]);
				archivoBuscar.setNombreOriginal(resultado[i][1]);
				archivoBuscar.setRutaOriginal(resultado[i][2]);
				archivoBuscar.setNombreFinal(resultado[i][3]);
				archivoBuscar.setRutaFinal(resultado[i][4]);
				
				Servidor servidorOriginal = Servidor.getById(resultado[i][5]);
				Servidor servidorFinal = Servidor.getById(resultado[i][6]);
				
				archivoBuscar.setServidorOriginal(servidorOriginal);
				archivoBuscar.setServidorFinal(servidorFinal);
				
			}
			return archivoBuscar;
		} else {
			return null;
		}
	}

	/**
	 * @return the servidorFinal
	 */
	public Servidor getServidorFinal() {
		return servidorFinal;
	}

	/**
	 * @param servidorFinal the servidorFinal to set
	 */
	public void setServidorFinal(Servidor servidorFinal) {
		this.servidorFinal = servidorFinal;
	}

	/**
	 * @return the servidorOriginal
	 */
	public Servidor getServidorOriginal() {
		return servidorOriginal;
	}

	/**
	 * @param servidorOriginal the servidorOriginal to set
	 */
	public void setServidorOriginal(Servidor servidorOriginal) {
		this.servidorOriginal = servidorOriginal;
	}

	/**
	 * @return the idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo the idArchivo to set
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return the nombreOriginal
	 */
	public String getNombreOriginal() {
		return nombreOriginal;
	}

	/**
	 * @param nombreOriginal the nombreOriginal to set
	 */
	public void setNombreOriginal(String nombreOriginal) {
		this.nombreOriginal = nombreOriginal;
	}

	/**
	 * @return the rutaOriginal
	 */
	public String getRutaOriginal() {
		return rutaOriginal;
	}

	/**
	 * @param rutaOriginal the rutaOriginal to set
	 */
	public void setRutaOriginal(String rutaOriginal) {
		this.rutaOriginal = rutaOriginal;
	}

	/**
	 * @return the nombreFinal
	 */
	public String getNombreFinal() {
		return nombreFinal;
	}

	/**
	 * @param nombreFinal the nombreFinal to set
	 */
	public void setNombreFinal(String nombreFinal) {
		this.nombreFinal = nombreFinal;
	}

	/**
	 * @return the rutaFinal
	 */
	public String getRutaFinal() {
		return rutaFinal;
	}

	/**
	 * @param rutaFinal the rutaFinal to set
	 */
	public void setRutaFinal(String rutaFinal) {
		this.rutaFinal = rutaFinal;
	}
	
	

}
