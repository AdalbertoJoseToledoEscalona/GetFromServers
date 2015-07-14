/**
 * 
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.xml.sax.SAXException;

import modelo.utils.XML;

/**
 * @author adalberto
 *
 */
public class Agenda {

	/**
	 * Identificador de Agenda
	 */
	private String idAgenda;
	/**
	 * El nombre descriptivo de la Agenda
	 */
	private String nombre;
	/**
	 * Indica en qué minuto se va a ejecutar esta Agenda
	 */
	private Integer minuto;
	/**
	 * Indica en qué hora se va a ejecutar esta Agenda
	 */
	private Integer hora;
	/**
	 * Indica en qué dia se va a ejecutar esta Agenda
	 */
	private Integer dia;
	/**
	 * Indica en qué mes se va a ejecutar esta Agenda
	 */
	private Integer mes;
	/**
	 * Indica en qué Día de Semana se va a ejecutar esta Agenda
	 */
	private Integer diaSemana;
	private String mensaje;
	
	public final static String FORMATO_FECHA = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 */
	public Agenda() {
		// TODO Auto-generated constructor stub
	}

	public Agenda(String idAgenda2) {
		this.idAgenda = idAgenda2;
	}

	public boolean agregar(String txtIdAgenda, String txtNombre,
			boolean isSoloUnaVez, String fechaInicial, String txtMinuto, String txtHora,
			String txtDia, String txtMes, String txtDiaSemana,
			List<String> hilos) throws SAXException, IOException {
		
		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "agendas.xml";

		String[] campos = { "id_agenda" };

		Object[][] condiciones = { { "id_agenda", "=", // Igualdad (=, !=, >,
														// <, >=, <=)
			txtIdAgenda, true // if(thisValue == true){AND}else{OR}
		}, };

		int desdeFila = 0;
		int numFilas = 1;
		Object ordenarPor = "orden";
		boolean asc = false;

		boolean useDOM = false;
		if (xml.getFilas(rutaArchivoXML, campos, condiciones, desdeFila,
				numFilas, null, asc, useDOM).length == 0) {
			// campos[0] = "orden";

			// String resultado[][] = xml.getFilas(rutaArchivoXML, campos, null,
			// desdeFila, numFilas, ordenarPor, asc, useDOM);
			// //int orden = 1;
			// if (resultado.length > 0) {
			// orden = Integer.parseInt(resultado[1][0]) + 1;
			// }
			
			String[] camposInsert = { "id_agenda", "nombre", "fecha_hora_inicial",
					"solo_una_vez", "minuto", "hora", "dia", "mes", "dia_semana" };

			String valores[][] = { { txtIdAgenda, txtNombre, fechaInicial,
				Boolean.toString(isSoloUnaVez), txtMinuto, txtHora, txtDia, txtMes, txtDiaSemana} };

			if (xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
				error = true;
				mensaje = "No se agregó la Agenda";
			} else {
				rutaArchivoXML = rb.getString("direccion_archivos")
						+ File.separator + "hilos_x_agendas.xml";
				
				/*campos[0] = "orden";

				String resultado[][] = xml.getFilas(rutaArchivoXML, campos,
						null, desdeFila, numFilas, ordenarPor, asc, useDOM);
				int orden = 1;
				if (resultado.length > 0) {
					orden = Integer.parseInt(resultado[1][0]) + 1;
				}*/
				
				String[] camposInsert2 = { "id_hilo_x_agenda", "id_hilo", "id_agenda", "orden", "fecha_hora_ult_ejecucion" };
				
				String valores2[][] = new String[hilos.size()][camposInsert2.length];
				
				for (int i = 0; i < hilos.size(); i++) {
					valores2[i][0] = hilos.get(i) + "_x_" + txtIdAgenda;
					valores2[i][1] = hilos.get(i);
					valores2[i][2] = txtIdAgenda;
					//valores2[i][3] = Integer.toString(orden);
					valores2[i][3] = Integer.toString((i + 1));
					valores2[i][4] = "";
					//orden++;
				}
				
				if (xml.insertFilas(rutaArchivoXML, camposInsert2, valores2) != hilos.size()) {
					error = true;
					mensaje = "No se puedieron agregar todos los hilos a la agenda";
				}
			}
		} else {
			error = true;
			mensaje = "Ya existe una Agenda con ese Id";
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
				+ File.separator + "agendas.xml";

		String[] campos = { "id_agenda", "nombre", "fecha_hora_inicial",
					"solo_una_vez", "minuto", "hora", "dia", "mes", "dia_semana"};
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = null;

		Integer desdeFila = null;
		Integer numFilas = null;

		//Object ordenarPor = "orden";
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

	public int eliminar(List<Object> idAgendas) throws SAXException, IOException {
		XML xml = new XML();

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "agendas.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		Object[][] condiciones = new Object[idAgendas.size()][4];
		for (int i = 0; i < idAgendas.size(); i++) {
			condiciones[i][0] = "id_agenda";
			condiciones[i][1] = "="; // Igualdad (=, !=, >, <, >=, <=)
			condiciones[i][2] = idAgendas.get(i);
			condiciones[i][3] = false; // = AND, false = OR, null = Don't Care
										// //
		}

		int filas = xml.deleteFilas(rutaArchivoXML, condiciones);
		
		rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos_x_agendas.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		xml.deleteFilas(rutaArchivoXML, condiciones);
		
		return filas;
	}

	public String[][] getHilos(String[] camposObtener) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos_x_agendas.xml";

		String[] campos = { "id_hilo", "orden"};
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = { { "id_agenda", "=", // Igualdad (=, !=, >,
			// <, >=, <=)
idAgenda, true // if(thisValue == true){AND}else{OR}
}, };;

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
	
	public static void editarFechaUltEjecucion(String textIdHilo, String txtIdAgenda) throws SAXException, IOException{
		//boolean error = false;

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos_x_agendas.xml";
		
		Object[][] camposValores = {{"fecha_hora_ult_ejecucion", new SimpleDateFormat(FORMATO_FECHA).format(new Date())}};
		
		
		Object[][] condiciones = { { "id_hilo", "=", // Igualdad (=, !=, >,
			// <, >=, <=)
			textIdHilo, true // if(thisValue == true){AND}else{OR}
		}, { "id_agenda", "=", // Igualdad (=, !=, >,
			// <, >=, <=)
			txtIdAgenda, true // if(thisValue == true){AND}else{OR}
			}
		};
		
		xml.updateFilas(rutaArchivoXML, camposValores, condiciones);
	}

	public boolean editar(String txtIdAgenda, String txtNombre,
			boolean isSoloUnaVez, String spinnerFechaHoraInicial,
			String txtMinuto, String txtHora, String txtDia, String txtMes,
			String txtDiaSemana, List<String> hilos) throws SAXException, IOException {
		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "agendas.xml";

		String[] campos = { "id_agenda" };

		Object[][] condiciones = { { "id_agenda", "=", // Igualdad (=, !=, >,
														// <, >=, <=)
			txtIdAgenda, true // if(thisValue == true){AND}else{OR}
		}, };

		int desdeFila = 0;
		int numFilas = 1;
		Object ordenarPor = "orden";
		boolean asc = false;
//{ "id_agenda", "nombre", "fecha_hora_inicial",
		//"solo_una_vez", "minuto", "hora", "dia", "mes", "dia_semana"};
		boolean useDOM = false;
		Object[][] camposValores = {{"nombre", txtNombre}, {"fecha_hora_inicial", spinnerFechaHoraInicial}
		, {"solo_una_vez", Boolean.toString(isSoloUnaVez)}, {"minuto", txtMinuto}, {"hora", txtHora}
		, {"dia", txtDia}, {"mes", txtMes}, {"dia_semana", txtDiaSemana}};

		if (xml.updateFilas(rutaArchivoXML, camposValores, condiciones) > 0) {
			rutaArchivoXML = rb.getString("direccion_archivos")
					+ File.separator + "hilos_x_agendas.xml";
			
			xml.deleteFilas(rutaArchivoXML, condiciones);
			
			String[] camposInsert2 = { "id_hilo_x_agenda", "id_hilo", "id_agenda", "orden", "fecha_hora_ult_ejecucion" };
			
			String valores2[][] = new String[hilos.size()][camposInsert2.length];
			
			for (int i = 0; i < hilos.size(); i++) {
				valores2[i][0] = hilos.get(i) + "_x_" + txtIdAgenda;
				valores2[i][1] = hilos.get(i);
				valores2[i][2] = txtIdAgenda;
				//valores2[i][3] = Integer.toString(orden);
				valores2[i][3] = Integer.toString((i + 1));
				valores2[i][4] = "";
				//orden++;
			}
			
			if (xml.insertFilas(rutaArchivoXML, camposInsert2, valores2) != hilos.size()) {
				error = true;
				mensaje = "No se puedieron actualizar todos los archivos al hilo";
			}
			
			/*List<String[]> valoresList = new ArrayList<String[]>();
			Object[][] condicionesDelete = new Object[archivos.size() + 1][4];
			
			for (int i = 0; i < archivos.size(); i++) {
				
				condicionesDelete[i][0] = "id_archivo";
				condicionesDelete[i][1] = "!=";
				condicionesDelete[i][2] = archivos.get(i);
				condicionesDelete[i][3] = false;
				
				
				
				
				Object[][] condiciones2 = { { "id_hilo", "=", // Igualdad (=, !=, >,
									// <, >=, <=)
				textIdHilo, true // if(thisValue == true){AND}else{OR}
				},
				{ "id_archivo", "=", // Igualdad (=, !=, >,
					// <, >=, <=)
					archivos.get(i), true // if(thisValue == true){AND}else{OR}
				}
				};
				
				Object[][] camposValores2 = {{"orden", Integer.toString((i+1))}};
				if (xml.updateFilas(rutaArchivoXML, camposValores2, condiciones2) == 0) {
					String v[] = {archivos.get(i) + "_x_" + textIdHilo, textIdHilo, archivos.get(i), Integer.toString((i + 1))};
					valoresList.add(v);
				}
			}

			String[] camposInsert = { "id_archivo_x_hilo", "id_hilo", "id_archivo", "orden"  };

			//String valores[][] = { { textIdHilo, textNombre,
			//		Boolean.toString(autoReproducir) } };
			
			String valores[][] = new String[valoresList.size()][camposInsert.length];
			valores = valoresList.toArray(valores);
			if (valores.length > 0 && xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
				error = true;
				mensaje = "No se pudo agregar algunos archivos";
			} else {
				
				condicionesDelete[archivos.size() -1][3] = true;
				
				condicionesDelete[archivos.size()][0] = "id_hilo";
				condicionesDelete[archivos.size()][1] = "=";
				condicionesDelete[archivos.size()][2] = textIdHilo;
				condicionesDelete[archivos.size()][3] = true;
				

				
				xml.deleteFilas(rutaArchivoXML, condicionesDelete);
				
				for (Object[] strings : condicionesDelete) {
					for (Object object : strings) {
						System.out.println(object + "\t");
					}
					System.out.println();
				}
			}*/
		} else {
			error = true;
			mensaje = "No se puedo actualizar el hilo";
		}

		return !error;
	}

	public static String[][] getAgendasByIds(String[][] agendasHilos) throws SAXException, IOException {

		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "agendas.xml";

		String[] campos = { "id_agenda", "nombre", "fecha_hora_inicial",
					"solo_una_vez", "minuto", "hora", "dia", "mes", "dia_semana"};
		

		Object[][] condiciones = new Object[agendasHilos.length][4];
		
		for (int i = 0; i < condiciones.length; i++) {
			condiciones[i][0] = "id_agenda";
			condiciones[i][1] = "=";
			condiciones[i][2] = agendasHilos[i][0];
			condiciones[i][3] = false;
		}

		Integer desdeFila = null;
		Integer numFilas = null;

		//Object ordenarPor = "orden";
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, campos, condiciones,
				desdeFila, numFilas, null, asc, useDOM);

		if (resultado.length > 0) {
			return Arrays.copyOfRange(resultado, 1, resultado.length);
		} else {
			return resultado;
		}
	}
	
	public static String[][] getAgendasByIds(String[] agendasHilos) throws SAXException, IOException {

		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "agendas.xml";

		String[] campos = { "id_agenda", "nombre", "fecha_hora_inicial",
					"solo_una_vez", "minuto", "hora", "dia", "mes", "dia_semana"};
		

		Object[][] condiciones = new Object[1][4];
		
		for (int i = 0; i < condiciones.length; i++) {
			condiciones[i][0] = "id_agenda";
			condiciones[i][1] = "=";
			condiciones[i][2] = agendasHilos[0];
			condiciones[i][3] = false;
		}

		Integer desdeFila = null;
		Integer numFilas = null;

		//Object ordenarPor = "orden";
		boolean asc = true;

		boolean useDOM = false;

		resultado = xml.getFilas(rutaArchivoXML, campos, condiciones,
				desdeFila, numFilas, null, asc, useDOM);

		if (resultado.length > 0) {
			return Arrays.copyOfRange(resultado, 1, resultado.length);
		} else {
			return resultado;
		}
	}
	
	
	public static void bucarAgendas() throws SAXException, IOException, ParseException {
		Date dActual = new Date();
		
		String[][] agendasHilos = lista(null);
		String[][] agendas = Agenda.getAgendasByIds(agendasHilos);
		Long[] milisegundosDeEspera = new Long[agendas.length];
		
		for (int i = 0; i < agendas.length; i++) {
			Date dateInicial = new SimpleDateFormat(Agenda.FORMATO_FECHA).parse(agendas[i][2]);
			if(dActual.compareTo(dateInicial) >= 0){
				//busco la fecha proxima despues de la fecha actual
				if(Boolean.parseBoolean(agendas[i][3])) {
					if(dActual.compareTo(dateInicial) == 0) {
						milisegundosDeEspera[i] = (long) 0;
					}else {
						milisegundosDeEspera[i] = null;
					}
				}else{
					Calendar cActual = Calendar.getInstance();
					Calendar c = Calendar.getInstance();
					c.setTime(dActual);
					cActual.setTime(dActual);
					String minuto = agendas[i][5];
					String hora = agendas[i][6];
					String dia = agendas[i][7];
					String mes = agendas[i][8];
					String diaSemana = agendas[i][9];
					
					
					
					
					//MINUTOS
					int minutoActualEsperar = 0;
					if(minuto.equals("*")) {
						minutoActualEsperar = 0;
					}else if(minuto.startsWith("*/")) {
						minutoActualEsperar = Integer.parseInt(minuto.substring(2));
					}else if(minuto.indexOf('-') > 0) {
						String[] rango = minuto.split("-");
						if(c.get(Calendar.MINUTE) < Integer.parseInt(rango[0])) {
							minutoActualEsperar = Integer.parseInt(rango[0]) - c.get(Calendar.MINUTE);
						}else if(c.get(Calendar.MINUTE) > Integer.parseInt(rango[1])) {
							 minutoActualEsperar = 60 - c.get(Calendar.MINUTE) + Integer.parseInt(rango[0]); 
						} else {
							if(c.get(Calendar.MINUTE) <= Integer.parseInt(minuto)) {
								minutoActualEsperar = Integer.parseInt(minuto) - c.get(Calendar.MINUTE);
							}else {
								minutoActualEsperar = 60 - c.get(Calendar.MINUTE) + Integer.parseInt(minuto); 
							}
						}
					}
					
					
					//HORAS
					int horaActualEsperar = 0;
					if(hora.equals("*")) {
						horaActualEsperar = 0;
					}else if(hora.startsWith("*/")) {
						horaActualEsperar = Integer.parseInt(hora.substring(2));
					}else if(hora.indexOf('-') > 0) {
						String[] rango = hora.split("-");
						if(c.get(Calendar.HOUR) < Integer.parseInt(rango[0])) {
							horaActualEsperar = Integer.parseInt(rango[0]) - c.get(Calendar.HOUR);
						}else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
							 horaActualEsperar = 24 - c.get(Calendar.HOUR) + Integer.parseInt(rango[0]); 
						} else {
							if(c.get(Calendar.HOUR) <= Integer.parseInt(hora)) {
								horaActualEsperar = Integer.parseInt(hora) - c.get(Calendar.HOUR);
							}else {
								horaActualEsperar = 24 - c.get(Calendar.HOUR) + Integer.parseInt(hora); 
							}
						}
					}
					
					//DIAS
					int diaActualEsperar = 0;
					int diasMeses[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
					if(dia.equals("*")) {
						diaActualEsperar = 0;
					}else if(dia.startsWith("*/")) {
						diaActualEsperar = Integer.parseInt(dia.substring(2));
					}else if(dia.indexOf('-') > 0) {
						String[] rango = dia.split("-");
						if(c.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(rango[0])) {
							diaActualEsperar = Integer.parseInt(rango[0]) - c.get(Calendar.DAY_OF_MONTH);
						}else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
							 diaActualEsperar = diasMeses[c.get(Calendar.MONTH)] - c.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(rango[0]); 
						} else {
							if(c.get(Calendar.DAY_OF_MONTH) <= Integer.parseInt(dia)) {
								diaActualEsperar = Integer.parseInt(dia) - c.get(Calendar.DAY_OF_MONTH);
							}else {
								diaActualEsperar = diasMeses[c.get(Calendar.MONTH)] - c.get(Calendar.DAY_OF_MONTH) + Integer.parseInt(dia); 
							}
						}
					}
					
					//MES
					int mesActualEsperar = 0;
					if(mes.equals("*")) {
						mesActualEsperar = 0;
					}else if(mes.startsWith("*/")) {
						mesActualEsperar = Integer.parseInt(mes.substring(2));
					}else if(mes.indexOf('-') > 0) {
						String[] rango = mes.split("-");
						if(c.get(Calendar.MONTH) < Integer.parseInt(rango[0])) {
							mesActualEsperar = Integer.parseInt(rango[0]) - c.get(Calendar.MONTH);
						}else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
							 mesActualEsperar = 12 - c.get(Calendar.MONTH) + Integer.parseInt(rango[0]); 
						} else {
							if(c.get(Calendar.MONTH) <= Integer.parseInt(mes)) {
								mesActualEsperar = Integer.parseInt(mes) - c.get(Calendar.MONTH);
							}else {
								mesActualEsperar = 12 - c.get(Calendar.MONTH) + Integer.parseInt(mes); 
							}
						}
					}
					
					//DIAS_SEMANA
					int diaSemanaActualEsperar = 0;
					if(diaSemana.equals("*")) {
						diaSemanaActualEsperar = 0;
					}else if(diaSemana.startsWith("*/")) {
						diaSemanaActualEsperar = Integer.parseInt(diaSemana.substring(2));
					}else if(diaSemana.indexOf('-') > 0) {
						String[] rango = diaSemana.split("-");
						if(c.get(Calendar.DAY_OF_WEEK) < Integer.parseInt(rango[0])) {
							diaSemanaActualEsperar = Integer.parseInt(rango[0]) - c.get(Calendar.DAY_OF_WEEK);
						}else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
							 diaSemanaActualEsperar = 12 - c.get(Calendar.DAY_OF_WEEK) + Integer.parseInt(rango[0]); 
						} else {
							if(c.get(Calendar.DAY_OF_WEEK) <= Integer.parseInt(diaSemana)) {
								diaSemanaActualEsperar = Integer.parseInt(diaSemana) - c.get(Calendar.DAY_OF_WEEK);
							}else {
								diaSemanaActualEsperar = 12 - c.get(Calendar.DAY_OF_WEEK) + Integer.parseInt(diaSemana); 
							}
						}
					}
					
				}
				
				
			}else{
				//busco la fecha proxima despues de la fecha Inicial
			}
			System.out.println(dateInicial);
		}
		
	}

}
