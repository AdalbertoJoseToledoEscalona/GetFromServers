/**
 * 
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

import modelo.utils.XML;

import org.xml.sax.SAXException;

import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.jtopenstubs.javabeans.PropertyVetoException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

//import controlador.EjecutorHilo;

/**
 * @author adalberto
 * 
 */
public class Hilo extends Thread implements Runnable {

	public static Hashtable<String, Hilo> hashtable = new Hashtable<String, Hilo>();
	private List<Agenda> agenda;
	private List<Servidor> servidor;
	public boolean corriendo;
	private String actualIdAgenda;
	/**
	 * El identificador de este hilo
	 */
	private String idHilo;
	/**
	 * El nombre del Hilo
	 */
	private String nombre;
	/**
	 * Indica si el Hilo se va a auto Reproducir en lo que arranque el Sistema
	 */
	private Boolean autoReproducir;
	private String mensaje;

	/**
	 * 
	 */
	public Hilo(List<Servidor> servidor2, List<Agenda> agenda2) {
		servidor = servidor2;
		agenda = agenda2;
	}

	/**
	 * @param target
	 */
	public Hilo(Runnable target, List<Servidor> servidor2, List<Agenda> agenda2) {
		super(target);
		servidor = servidor2;
		agenda = agenda2;
	}

	/**
	 * @param name
	 */
	public Hilo(String name, List<Servidor> servidor2, List<Agenda> agenda2) {
		super(name);
		servidor = servidor2;
		agenda = agenda2;
	}

	/**
	 * @param group
	 * @param target
	 */
	public Hilo(ThreadGroup group, Runnable target, List<Servidor> servidor2,
			List<Agenda> agenda2) {
		super(group, target);
		servidor = servidor2;
		agenda = agenda2;
	}

	/**
	 * @param group
	 * @param name
	 */
	public Hilo(ThreadGroup group, String name, List<Servidor> servidor2,
			List<Agenda> agenda2) {
		super(group, name);
		servidor = servidor2;
		agenda = agenda2;
	}
	
	/**
	 * @param target
	 * @param name
	 */
	public Hilo(Runnable target, String name, List<Servidor> servidor2,
			List<Agenda> agenda2) {
		super(target, name);
		servidor = servidor2;
		agenda = agenda2;
	}
	
	/**
	 * @param target
	 * @param name
	 * @param idHilo
	 * @param nombre
	 * @param autoReproducir
	 */
	public Hilo(Runnable target, String name, String idHilo, String nombre, boolean autoReproducir) {
		super(target, name);
		this.idHilo = idHilo;
		this.nombre = nombre;
		this.autoReproducir = autoReproducir;
	}
	
	
	/**
	 * @param target
	 * @param name
	 * @param idHilo
	 * @param nombre
	 * @param autoReproducir
	 */
	public Hilo(String name, String idHilo, String nombre, boolean autoReproducir) {
		this.idHilo = idHilo;
		this.nombre = nombre;
		this.autoReproducir = autoReproducir;
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 */
	public Hilo(ThreadGroup group, Runnable target, String name,
			List<Servidor> servidor2, List<Agenda> agenda2) {
		super(group, target, name);
		servidor = servidor2;
		agenda = agenda2;
	}

	/**
	 * @param group
	 * @param target
	 * @param name
	 * @param stackSize
	 */
	public Hilo(ThreadGroup group, Runnable target, String name,
			long stackSize, List<Servidor> servidor2, List<Agenda> agenda2) {
		super(group, target, name, stackSize);
		servidor = servidor2;
		agenda = agenda2;
	}

	public Hilo() {
		// TODO Auto-generated constructor stub
	}

	public Hilo(String idHilo2) {
		this.idHilo = idHilo2;
	}
	
	public String[][] getArchivos(String[] camposObtener) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_x_hilos.xml";

		String[] campos = { "id_archivo", "orden"};
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = { { "id_hilo", "=", // Igualdad (=, !=, >,
			// <, >=, <=)
idHilo, true // if(thisValue == true){AND}else{OR}
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

	public boolean agregar(String textIdHilo, String textNombre,
			boolean autoReproducir, List<String> archivos) throws SAXException,
			IOException {
		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos.xml";

		String[] campos = { "id_hilo" };

		Object[][] condiciones = { { "id_hilo", "=", // Igualdad (=, !=, >,
														// <, >=, <=)
				textIdHilo, true // if(thisValue == true){AND}else{OR}
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

			String[] camposInsert = { "id_hilo", "nombre", "auto_reproducir" };

			String valores[][] = { { textIdHilo, textNombre,
					Boolean.toString(autoReproducir) } };

			if (xml.insertFilas(rutaArchivoXML, camposInsert, valores) < 1) {
				error = true;
				mensaje = "No se agregó el Hilo";
			} else {
				rutaArchivoXML = rb.getString("direccion_archivos")
						+ File.separator + "archivos_x_hilos.xml";
				
				/*campos[0] = "orden";

				String resultado[][] = xml.getFilas(rutaArchivoXML, campos,
						null, desdeFila, numFilas, ordenarPor, asc, useDOM);
				int orden = 1;
				if (resultado.length > 0) {
					orden = Integer.parseInt(resultado[1][0]) + 1;
				}*/
				
				String[] camposInsert2 = { "id_archivo_x_hilo", "id_hilo", "id_archivo", "orden" };
				
				String valores2[][] = new String[archivos.size()][camposInsert2.length];
				
				for (int i = 0; i < archivos.size(); i++) {
					valores2[i][0] = archivos.get(i) + "_x_" + textIdHilo;
					valores2[i][1] = textIdHilo;
					valores2[i][2] = archivos.get(i);
					//valores2[i][3] = Integer.toString(orden);
					valores2[i][3] = Integer.toString((i + 1));
					//orden++;
				}
				
				if (xml.insertFilas(rutaArchivoXML, camposInsert2, valores2) != archivos.size()) {
					error = true;
					mensaje = "No se puedieron agregar todos los archivos al hilo";
				}
			}
		} else {
			error = true;
			mensaje = "Ya existe un Hilo con ese Id";
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
				+ File.separator + "hilos.xml";

		String[] campos = { "id_hilo", "nombre", "auto_reproducir"};
		
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
	
	public static String[][] listaAutoReproducir(String[] camposObtener) throws SAXException, IOException {
		XML xml = new XML();

		String[][] resultado = null;

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos.xml";

		String[] campos = { "id_hilo", "nombre", "auto_reproducir"};
		
		if(camposObtener == null){
			camposObtener = campos;
		}
		

		Object[][] condiciones = { { "auto_reproducir", "=", // Igualdad (=, !=, >,
			// <, >=, <=)
			"true", true // if(thisValue == true){AND}else{OR}
			}, };

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
	

	public boolean editar(String textIdHilo, String textNombre,
			boolean autoreprodir, List<String> archivos) throws SAXException, IOException {
		boolean error = false;
		mensaje = "";

		XML xml = new XML();
		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos.xml";

		String[] campos = { "id_hilo" };

		Object[][] condiciones = { { "id_hilo", "=", // Igualdad (=, !=, >,
														// <, >=, <=)
				textIdHilo, true // if(thisValue == true){AND}else{OR}
		}, };

		int desdeFila = 0;
		int numFilas = 1;
		Object ordenarPor = "orden";
		boolean asc = false;

		boolean useDOM = false;
		Object[][] camposValores = {{"nombre", textNombre}, {"auto_reproducir", Boolean.toString(autoreprodir)}};

		if (xml.updateFilas(rutaArchivoXML, camposValores, condiciones) > 0) {
			rutaArchivoXML = rb.getString("direccion_archivos")
					+ File.separator + "archivos_x_hilos.xml";
			
			xml.deleteFilas(rutaArchivoXML, condiciones);
			
			String[] camposInsert2 = { "id_archivo_x_hilo", "id_hilo", "id_archivo", "orden" };
			
			String valores2[][] = new String[archivos.size()][camposInsert2.length];
			
			for (int i = 0; i < archivos.size(); i++) {
				valores2[i][0] = archivos.get(i) + "_x_" + textIdHilo;
				valores2[i][1] = textIdHilo;
				valores2[i][2] = archivos.get(i);
				//valores2[i][3] = Integer.toString(orden);
				valores2[i][3] = Integer.toString((i + 1));
				//orden++;
			}
			
			if (xml.insertFilas(rutaArchivoXML, camposInsert2, valores2) != archivos.size()) {
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

	public int eliminar(List<Object> idHilos) throws SAXException, IOException {
		
		XML xml = new XML();

		ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
		String rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "hilos.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		Object[][] condiciones = new Object[idHilos.size()][4];
		for (int i = 0; i < idHilos.size(); i++) {
			condiciones[i][0] = "id_hilo";
			condiciones[i][1] = "="; // Igualdad (=, !=, >, <, >=, <=)
			condiciones[i][2] = idHilos.get(i);
			condiciones[i][3] = false; // = AND, false = OR, null = Don't Care
										// //
		}

		int filas = xml.deleteFilas(rutaArchivoXML, condiciones);
		
		rutaArchivoXML = rb.getString("direccion_archivos")
				+ File.separator + "archivos_x_hilos.xml";
		/*
		 * Object[][] condiciones = {{ "id_servidor", "=", //Igualdad (=, !=, >,
		 * <, >=, <=) idServidor, false //= AND, false = OR, null = Don't Care
		 * // }, }};
		 */

		xml.deleteFilas(rutaArchivoXML, condiciones);
		
		return filas;
	}

	/**
	 * @return the idHilo
	 */
	public String getIdHilo() {
		return idHilo;
	}

	/**
	 * @param idHilo the idHilo to set
	 */
	public void setIdHilo(String idHilo) {
		this.idHilo = idHilo;
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
	 * @return the autoReproducir
	 */
	public Boolean getAutoReproducir() {
		return autoReproducir;
	}

	/**
	 * @param autoReproducir the autoReproducir to set
	 */
	public void setAutoReproducir(Boolean autoReproducir) {
		this.autoReproducir = autoReproducir;
	}

	public List<String[]> bucarAgendas() throws SAXException, IOException, ParseException {
		Date dActual = new Date();
		
		String[][] agendasHilos = getAgendas(null);
		List<String[]>  agendasHilosEjecutar = new ArrayList<String[]>();
		//String[][] agendas = Agenda.getAgendasByIds(agendasHilos);
		Boolean[] ejecutar = new Boolean[agendasHilos.length];
		
		for (int i = 0; i < agendasHilos.length; i++) {
			String[][] agenda = Agenda.getAgendasByIds(agendasHilos[i]);
			Date dateInicial = new SimpleDateFormat(Agenda.FORMATO_FECHA).parse(agenda[0][2]);
			if(dActual.compareTo(dateInicial) >= 0){
				if(Boolean.parseBoolean(agenda[0][3])) {
					if(dActual.compareTo(dateInicial) >= 0) {
						if(agendasHilos[i][2] == null || agendasHilos[i][2].trim().length() == 0) {
							ejecutar[i] = true;
						}else{
							ejecutar[i] = false;
						}
					}else {
						ejecutar[i] = false;
					}
				}else{
					Calendar cActual = Calendar.getInstance();
					Calendar c = Calendar.getInstance();
					c.setTime(dActual);
					cActual.setTime(dActual);
					String minuto = agenda[0][4];
					String hora = agenda[0][5];
					String dia = agenda[0][6];
					String mes = agenda[0][7];
					String diaSemana = agenda[0][8];
					
					Date dateUltEjecucion = null;
					Double minutosUltEjecucion = null; 
					Double diasSemanasUlEjecucion = null; 
					if(agendasHilos[i][2] != null && agendasHilos[i][2].trim().length() > 0) {
						dateUltEjecucion = new SimpleDateFormat(Agenda.FORMATO_FECHA).parse(agendasHilos[i][2]);
						minutosUltEjecucion = (c.getTime().getTime() - dateUltEjecucion.getTime())/60000.00; 
						diasSemanasUlEjecucion = (c.getTime().getTime() - dateUltEjecucion.getTime())/60000.00/24.0/7.0; 
					}
					
					
					//MINUTOS
					int minutosCada = 0;
					if(minuto.equals("*")) {
						ejecutar[i] = true;
					}else if(minuto.startsWith("*/")) {
						if(dateUltEjecucion == null) {
							ejecutar[i] = true;
						} else {
							minutosCada = Integer.parseInt(minuto.substring(2));
							if(minutosUltEjecucion >= minutosCada) {
								ejecutar[i] = true;
							} else {
								ejecutar[i] = false;
							}
						}
					}else if(minuto.indexOf('-') > 0) {
						String[] rango = minuto.split("-");
						if(c.get(Calendar.MINUTE) < Integer.parseInt(rango[0])) {
							ejecutar[i] = false;
						}else if(c.get(Calendar.MINUTE) > Integer.parseInt(rango[1])) {
							ejecutar[i] = false;
						} else {
							ejecutar[i] = true;
						}
					} else {
						if(c.get(Calendar.MINUTE) == Integer.parseInt(minuto)) {
							ejecutar[i] = true;
						}else {
							ejecutar[i] = false;
						}
					}
					
					//HORAS
					
					if(ejecutar[i] == true) {
						if(hora.equals("*")) {
							ejecutar[i] = true;
						}else if(hora.startsWith("*/")) {
							if(dateUltEjecucion == null) {
								ejecutar[i] = true;
							} else {
								minutosCada += Integer.parseInt(minuto.substring(2))*60;
								if(minutosUltEjecucion >= minutosCada) {
									ejecutar[i] = true;
								} else {
									ejecutar[i] = false;
								}
							}
						}else if(hora.indexOf('-') > 0) {
							String[] rango = hora.split("-");
							if(c.get(Calendar.HOUR) < Integer.parseInt(rango[0])) {
								ejecutar[i] = false;
							} else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
								ejecutar[i] = false;
							} else {
								ejecutar[i] = true;
							}
						}else {
							if(c.get(Calendar.HOUR) == Integer.parseInt(hora)) {
								ejecutar[i] = true;
							}else {
								ejecutar[i] = false;
							}
						}
						
						if(ejecutar[i] == true) {
							
							//DIAS
							
							
							if(dia.equals("*")) {
								ejecutar[i] = true;
							}else if(dia.startsWith("*/")) {
								if(dateUltEjecucion == null) {
									ejecutar[i] = true;
								} else {
									minutosCada += Integer.parseInt(minuto.substring(2))*24*60;
									if(minutosUltEjecucion >= minutosCada) {
										ejecutar[i] = true;
									} else {
										ejecutar[i] = false;
									}
								}
							}else if(dia.indexOf('-') > 0) {
								String[] rango = dia.split("-");
								if(c.get(Calendar.DAY_OF_MONTH) < Integer.parseInt(rango[0])) {
									ejecutar[i] = false;
								}else if(c.get(Calendar.HOUR) > Integer.parseInt(rango[1])) {
									ejecutar[i] = false;
								} else {
									ejecutar[i] = true;
								}
							
							}else {
								if(c.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dia)) {
									ejecutar[i] = true;
								}else {
									ejecutar[i] = false;
								}
							}
							
						
							if(ejecutar[i] == true) {
								//MES
								//int mesActualEsperar = 0;
								int diasMeses[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
								if(mes.equals("*")) {
									ejecutar[i] = true;
								}else if(mes.startsWith("*/")) {
									if(dateUltEjecucion == null) {
										ejecutar[i] = true;
									} else {
										minutosCada += Integer.parseInt(minuto.substring(2))*24*60*diasMeses[c.get(Calendar.MONTH)];
										if(minutosUltEjecucion >= minutosCada) {
											ejecutar[i] = true;
										} else {
											ejecutar[i] = false;
										}
									}
								}else if(mes.indexOf('-') > 0) {
									String[] rango = mes.split("-");
									if(c.get(Calendar.MONTH) + 1 < Integer.parseInt(rango[0])) {
										ejecutar[i] = false;
									}else if(c.get(Calendar.MONTH) + 1 > Integer.parseInt(rango[1])) {
										ejecutar[i] = false;
									}else{
										ejecutar[i] = true;
									}
								} else {
									if(c.get(Calendar.MONTH) + 1 == Integer.parseInt(mes)) {
										ejecutar[i] = true;
									}else {
										ejecutar[i] = false;
									}
								}
							
								
								if(ejecutar[i] == true) {
									//DIAS_SEMANA
									if(diaSemana.equals("*")) {
										ejecutar[i] = true;
									}else if(diaSemana.startsWith("*/")) {
										if(dateUltEjecucion == null) {
											ejecutar[i] = true;
										} else {
											int diasSemanasCada = Integer.parseInt(minuto.substring(2));
											if(diasSemanasUlEjecucion >= diasSemanasCada) {
												ejecutar[i] = true;
											} else {
												ejecutar[i] = false;
											}
										}
									}else if(diaSemana.indexOf('-') > 0) {
										String[] rango = diaSemana.split("-");
										if(c.get(Calendar.DAY_OF_WEEK) + 1 < Integer.parseInt(rango[0])) {
											ejecutar[i] = false;
										}else if(c.get(Calendar.DAY_OF_WEEK) + 1 > Integer.parseInt(rango[1])) {
											ejecutar[i] = false;
										}else{
											ejecutar[i] = true;
										}
									} else {
										if(c.get(Calendar.DAY_OF_WEEK) + 1 == Integer.parseInt(diaSemana)) {
											ejecutar[i] = true;
										}else {
											ejecutar[i] = false;
										}
									}
								
								
									
									if(ejecutar[i] == true) {
										agendasHilosEjecutar.add(agendasHilos[i]);
									}
								
								}
								
							}
							
						}
					}
					
				}
				
				
			}else{
				//busco la fecha proxima despues de la fecha Inicial
			}
			System.out.println(dateInicial);
		}
		
		return agendasHilosEjecutar;
		
	}
	
		public String[][] getAgendas(String[] camposObtener) throws SAXException, IOException {
			XML xml = new XML();

			String[][] resultado = null;

			ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
			String rutaArchivoXML = rb.getString("direccion_archivos")
					+ File.separator + "hilos_x_agendas.xml";

			String[] campos = { "id_agenda", "orden", "fecha_hora_ult_ejecucion"};
			
			if(camposObtener == null){
				camposObtener = campos;
			}
			

			Object[][] condiciones = { { "id_hilo", "=", // Igualdad (=, !=, >,
				// <, >=, <=)
	idHilo, true // if(thisValue == true){AND}else{OR}
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
	

		
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			if(hashtable.containsKey(getIdHilo()) && corriendo) {
				System.out.println("Verificar lo que quedó pendiente del pasado (en los logs)");
				
				
				try {
					Log.agregar("Inicio Agenda", "Comenzando Agenda", "Comienzo de ejecutar la agenda", getIdHilo(), null, null, actualIdAgenda);
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//ArchivoBuscar.lista(null);
				
				//Hilo.lista(camposObtener)
				System.out.println("Ver los archivos que tengo");
				
					String[][] archivos = null;
					try {
						archivos = getArchivos(null);
					} catch (SAXException | IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					for (int i = 0; i < archivos.length; i++) {
						try {
							Log.agregar("Inicio Archivo", "Comenzando Archivo", "Comienzo de buscar el Archivo", getIdHilo(), archivos[i][0], null, actualIdAgenda);
						} catch (SAXException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						ArchivoBuscar archivoBuscar = null;
						try {
							archivoBuscar = ArchivoBuscar.getById(archivos[i][0]);
						} catch (SAXException | IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						try {
							Log.agregar("Inicio Servidor Origen", "Comenzando Servidor", "Comienzo conectarse con el Servidor", getIdHilo(), archivos[i][0], null, archivoBuscar.getServidorOriginal().getIdServidor());
						} catch (SAXException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						SSH ssh = new SSH();			
						AS400Connection as400 = new AS400Connection();
						try {
							if(archivoBuscar.getServidorOriginal().getProtocolo().trim().equalsIgnoreCase("SSH")){
								ssh.downloadFile(archivoBuscar.getServidorOriginal(), archivoBuscar);
							}else if(archivoBuscar.getServidorOriginal().getProtocolo().trim().equalsIgnoreCase("AS400Connection")){
								as400.downloadFile(archivoBuscar.getServidorOriginal(), archivoBuscar);
							}
						} catch (JSchException | SftpException | IOException | AS400Exception | AS400SecurityException | InterruptedException | PropertyVetoException  e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							try {
								Log.agregar("Error", "Error", e.getMessage(), getIdHilo(), archivos[i][0], null, archivoBuscar.getServidorOriginal().getIdServidor());
							} catch (SAXException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						try {
							Log.agregar("Fin Servidor Origen", "Terminando Servidor", "Termino conectarse con el Servidor", getIdHilo(), archivos[i][0], null, archivoBuscar.getServidorOriginal().getIdServidor());
						} catch (SAXException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						try {
							Log.agregar("Inicio Servidor Final", "Comenzando Servidor", "Comienzo conectarse con el Servidor", getIdHilo(), archivos[i][0], null, archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal().getIdServidor() : archivoBuscar.getServidorFinal().getIdServidor());
						} catch (SAXException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							if(archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal().getProtocolo().trim().equalsIgnoreCase("SSH") : archivoBuscar.getServidorFinal().getProtocolo().trim().equalsIgnoreCase("SSH")){
								
								if (ssh.getNombreArchivoOriginal() == null) {
									ssh.setNombreArchivoOriginal(as400.getNombreArchivoOriginal());
								}
								
								if (ssh.getNombreArchivoFinal() == null) {
									ssh.setNombreArchivoFinal(as400.getNombreArchivoFinal());
								}
								
								ssh.uploadFile(archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal() : archivoBuscar.getServidorFinal(), archivoBuscar);
							}else if(archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal().getProtocolo().trim().equalsIgnoreCase("AS400Connection") : archivoBuscar.getServidorFinal().getProtocolo().trim().equalsIgnoreCase("AS400Connection")){
								
								if (as400.getNombreArchivoOriginal() == null) {
									as400.setNombreArchivoOriginal(ssh.getNombreArchivoOriginal());
								}
								
								if (as400.getNombreArchivoFinal() == null) {
									as400.setNombreArchivoFinal(ssh.getNombreArchivoFinal());
								}
								
								as400.uploadFile(archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal() : archivoBuscar.getServidorFinal(), archivoBuscar);
							}
						} catch (/*JSchException | SftpException | IOException |*/ Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							try {
								Log.agregar("Error", "Error", e.getMessage(), getIdHilo(), archivos[i][0], null,  archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal().getIdServidor() : archivoBuscar.getServidorFinal().getIdServidor());
							} catch (SAXException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						try {
							Log.agregar("Fin Servidor Final", "Terminando Servidor", "Termino conectarse con el Servidor", getIdHilo(), archivos[i][0], null,  archivoBuscar.getServidorFinal() == null ? archivoBuscar.getServidorOriginal().getIdServidor() : archivoBuscar.getServidorFinal().getIdServidor());
						} catch (SAXException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						try {
							Log.agregar("Fin Archivo", "Terminando Archivo", "Termino de buscar el Archivo", getIdHilo(), archivos[i][0], null, actualIdAgenda);
						} catch (SAXException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
				
				
				
				System.out.println("Por cada archivo conectarse al servidor original, copiar el archivo, desconectarse y conectarse"
						+ "en el servidor destino para pegar el archivo con el nombre especificado");
				
				try {
					Log.agregar("Fin Agenda", "Terminando Agenda", "Termino de ejecutar la agenda", getIdHilo(), null, null, actualIdAgenda);
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			
			System.out.println("SALGO DEL RUN");
		}
		
		/**
		 * @param actualIdAgenda the actualIdAgenda to set
		 */
		public void setActualIdAgenda(String actualIdAgenda) {
			this.actualIdAgenda = actualIdAgenda;
		}

		public void ejecutarHilo() {
			if(!hashtable.containsKey(idHilo)){

				//EjecutorHilo eh = new EjecutorHilo();
				corriendo = true;
				//Hilo hilo = new Hilo(eh, idHilo, idHilo, nombre, autoReproducir);
				//eh.setHilo(hilo);
				
				hashtable.put(getIdHilo(), this);
				//start();
			} else {
				Hilo eh = hashtable.get(idHilo);
				if(!eh.corriendo) {
					eh.corriendo = true;
					//start();
				}
			}
		}
		
		public void detenerHilo() {
			if(hashtable.containsKey(idHilo)){
				Hilo eh = hashtable.get(idHilo);
				if(eh.corriendo) {
					eh.corriendo = false;
				}
				//notify();
				hashtable.remove(eh.idHilo);
			}
		}
		
		public boolean estaCorriendo() {
			if(hashtable.containsKey(idHilo)){
				Hilo eh = hashtable.get(idHilo);
				return eh.corriendo;
			}else{
				return false;
			}
		}
		
}
