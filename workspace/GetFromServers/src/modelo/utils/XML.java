/**
 * 
 */
package modelo.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.utils.ordenamientos.QuickSortMatriz;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author adalberto
 * 
 */
public class XML {

	/**
	 * 
	 */
	public XML() {
		
	}

	/**
	 * 
	 * @param rutaArchivoXML
	 *            = ruta al Archivo XML a analizar
	 * @param campos
	 *            if(campos == null || campos.length == 0){extrae todos los
	 *            campos}else{extrae estos campos}
	 * @param condiciones
	 *            = // Object condiciones[][] = // {{ // "campo", // "=", //
	 *            Igualdad (=, !=, >, <, >=, <=) // "valor", // true // true =
	 *            AND, false = OR, null = Don't Care // }, // { // "campo2", //
	 *            "!=", // Igualdad (=, !=, >, <, >=, <=) // "valor2", // false
	 *            // if(thisValue == true){AND}else{OR} // }};
	 * @param useDOM
	 *            if(useDOM == true){usa la implementacion DOM}else{usa la
	 *            implementacion SAX}
	 * @return los valores en forma de tabla o retorna un List Empty
	 * @throws SAXException
	 * @throws IOException
	 */
	public String[][] getFilas(String rutaArchivoXML, String campos[],
			Object condiciones[][], Integer desdeFila, Integer numFilas, Object ordenarPor, boolean asc, boolean useDOM) throws SAXException,
			IOException {

		String resultado[][] = null;

		if (useDOM) {
			resultado = new DOMImplementacion().getFilas(rutaArchivoXML,
					campos, condiciones, desdeFila, numFilas, ordenarPor, asc);
		} else {
			resultado = new SAXImplementacion().getFilas(rutaArchivoXML,
					campos, condiciones, desdeFila, numFilas, ordenarPor, asc);
		}

		return resultado;
	}

	public int insertFilas(String rutaArchivoXML, String campos[],
			String valores[][]) throws SAXException, IOException {
		return new DOMImplementacion().insertFilas(rutaArchivoXML, campos,
				valores);
	}

	public int updateFilas(String rutaArchivoXML, Object camposValores[][],
			Object condiciones[][]) throws SAXException, IOException {
		return new DOMImplementacion().updateFilas(rutaArchivoXML,
				camposValores, condiciones);
	}

	public int deleteFilas(String rutaArchivoXML, Object condiciones[][])
			throws SAXException, IOException {
		return new DOMImplementacion().deleteFilas(rutaArchivoXML, condiciones);
	}

	private static class DOMImplementacion {

		/**
		 * 
		 * @param rutaArchivoXML
		 *            = ruta al Archivo XML a analizar
		 * @param campos
		 *            if(campos == null || campos.length == 0){extrae todos los
		 *            campos}else{extrae estos campos}
		 * @param condiciones
		 *            = // Object condiciones[][] = // {{ // "campo", // "=", //
		 *            Igualdad (=, !=, >, <, >=, <=) // "valor", // true // true
		 *            = AND, false = OR, null = Don't Care // }, // { //
		 *            "campo2", // "!=", // Igualdad (=, !=, >, <, >=, <=) //
		 *            "valor2", // false // if(thisValue == true){AND}else{OR}
		 *            // }};
		 * @return los valores en forma de tabla o retorna un List Empty
		 * @throws SAXException
		 * @throws IOException
		 */
		public String[][] getFilas(String rutaArchivoXML, String campos[],
				Object condiciones[][], Integer desdeFila, Integer numFilas, Object ordenarPor, boolean asc) throws SAXException, IOException {
			// System.out.println("DOM");
			List<String[]> resultado = new ArrayList<String[]>();
			List<String> encabezadoList = new ArrayList<String>();
			List<String[]> cuerpoList = new ArrayList<String[]>();
			List<String> cuerpoActualList = new ArrayList<String>();

			DOMParser analizador = new DOMParser();
			analizador.parse(rutaArchivoXML);
			Document doc = analizador.getDocument();

			Element raiz = doc.getDocumentElement(); // DATA
			// System.out.println("El elemento raíz es "+ raiz.getNodeName());
			NodeList nodeRows = raiz.getChildNodes();
			boolean primeraFila = true;
			for (int i = 0; i < nodeRows.getLength(); i++) { // RECORRO LOS ROWS
				if (nodeRows.item(i).getNodeType() == Node.ELEMENT_NODE) { // ROW
					NodeList nodeFields = nodeRows.item(i).getChildNodes();
					for (int j = 0; j < nodeFields.getLength(); j++) { // RECORRO
																		// LOS
																		// FIELDS
						if (nodeFields.item(j).getNodeType() == Node.ELEMENT_NODE) { // FIELD
							if (primeraFila) {
								encabezadoList.add(nodeFields.item(j)
										.getNodeName());
							}

							cuerpoActualList.add(nodeFields.item(j)
									.getTextContent());

							/*
							 * if (nodeFields.item(j).getFirstChild() == null) {
							 * cuerpoActualList.add(null); } else {
							 * cuerpoActualList.add(nodeFields.item(j)
							 * .getFirstChild().getNodeValue()); }
							 */

						} // FIN FIELD
					} // FIN RECORRO LOS FIELDS
					String cuerpoActualArray[] = new String[cuerpoActualList
							.size()];
					cuerpoActualArray = cuerpoActualList
							.toArray(cuerpoActualArray);
					if (cumpleCondicion(encabezadoList, cuerpoActualArray,
							condiciones)) {
						cuerpoList.add(cuerpoActualArray);
					}
					cuerpoActualList.clear();
					primeraFila = false;
				} // FIN ROW
			} // FIN RECORRO LOS ROWS

			int desde = 0;
			if(desdeFila != null && desdeFila >= 0){
				desde = desdeFila;
			}
			
			int hasta = cuerpoList.size();
			if(numFilas != null){
				if(desde + numFilas < cuerpoList.size()){
					hasta = desde + numFilas;
				}
			}
			
			if (cuerpoList.size() > 0) {
				if(ordenarPor != null) {
					int indexOrder = -1;
					if(ordenarPor instanceof Integer){
						indexOrder = Integer.parseInt(ordenarPor.toString());
					}else if(ordenarPor instanceof String){
						for (int i = 0; i < encabezadoList.size(); i++) {
							if(ordenarPor.toString().equals(encabezadoList.get(i))) {
								indexOrder = i;
								break;
							}
						}
					}
					
					if(indexOrder >= 0){
						cuerpoList = new QuickSortMatriz().quickSort(cuerpoList, cuerpoList.size(), indexOrder, asc);
					}
				}
				
				String encabezadoArray[] = new String[encabezadoList.size()];
				encabezadoArray = encabezadoList.toArray(encabezadoArray);
				if (campos == null || campos.length == 0) {
					resultado.add(encabezadoArray);
					
					if(desde > 0 || hasta < cuerpoList.size()){
						for (int i = desde; i < hasta; i++) {
							resultado.add(cuerpoList.get(i));
						}
					}else {
						resultado.addAll(cuerpoList);
					}
					
				} else {
					// resultado.add(campos);
					for (int i = desde; i < hasta; i++) {
						List<String> camposFinal = new ArrayList<String>();
						List<String> cuerpoAdd = new ArrayList<String>();
						for (int j = 0; j < campos.length; j++) {
							for (int k = 0; k < encabezadoArray.length; k++) {
								if (campos[j].equals(encabezadoArray[k])) {
									if (i == 0) {
										camposFinal.add(encabezadoArray[k]);
									}
									cuerpoAdd.add(cuerpoList.get(i)[k]);
								}
							}
						}
						if (i == 0) {
							String camposFinalArray[] = new String[camposFinal
									.size()];
							camposFinalArray = camposFinal
									.toArray(camposFinalArray);
							resultado.add(camposFinalArray);
						}

						String cuerpoAddArray[] = new String[cuerpoAdd.size()];
						cuerpoAddArray = cuerpoAdd.toArray(cuerpoAddArray);
						resultado.add(cuerpoAddArray);
					}
				}
			}

			String resultadoArray[][] = null;

			if (resultado.size() > 0) {//1 >= 1 - 1			
				resultadoArray = new String[resultado.size()][resultado.get(0).length];
				resultadoArray = resultado.toArray(resultadoArray);
			} else {
				resultadoArray = new String[0][0];
			}

			return resultadoArray;
		}

		public int insertFilas(String rutaArchivoXML, String campos[],
				String valores[][]) throws SAXException, IOException {
			List<String> encabezadoList = new ArrayList<String>();
			List<Object[]> valoresList = new ArrayList<Object[]>();
			// int cont = 0;

			DOMParser analizador = new DOMParser();
			analizador.parse(rutaArchivoXML);
			Document doc = analizador.getDocument();

			Element raiz = doc.getDocumentElement(); // DATA
			// System.out.println("El elemento raíz es "+ raiz.getNodeName());
			NodeList nodeRows = raiz.getChildNodes();
			boolean primeraFila = true;
			for (int i = 0; i < nodeRows.getLength(); i++) { // RECORRO LOS ROWS
				if (nodeRows.item(i).getNodeType() == Node.ELEMENT_NODE) { // ROW
					NodeList nodeFields = nodeRows.item(i).getChildNodes();
					for (int j = 0; j < nodeFields.getLength(); j++) { // RECORRO
																		// LOS
																		// FIELDS
						if (nodeFields.item(j).getNodeType() == Node.ELEMENT_NODE) { // FIELD
							if (primeraFila) {
								encabezadoList.add(nodeFields.item(j)
										.getNodeName());
							}
						} // FIN FIELD
					} // FIN RECORRO LOS FIELDS
					primeraFila = false;
					break;
				} // FIN ROW
			} // FIN RECORRO LOS ROWS

			if (campos == null || campos.length == 0) {
				// for (int i = 0; i < encabezadoList.size(); i++) {

				for (int j = 0; j < valores.length; j++) {
					Object valoresListActual[] = new Object[encabezadoList
							.size()];
					// valoresListActual = valores[j];
					System.arraycopy(
							valores[j],
							0,
							valoresListActual,
							0,
							valores[j].length <= valoresListActual.length ? valores[j].length
									: valoresListActual.length);
					valoresList.add(valoresListActual);
				}
				// valoresList.add(valoresListActual);
				// }
			} else {
				if (encabezadoList.size() == 0) {
					for (int i = 0; i < campos.length; i++) {
						encabezadoList.add(campos[i]);
					}
				}
				for (int k = 0; k < valores.length; k++) {
					List<Object> valoresListActual = new ArrayList<Object>();
					for (int i = 0; i < encabezadoList.size(); i++) {
						for (int j = 0; j < campos.length; j++) {
							if (encabezadoList.get(i).equals(campos[j])) {
								if (j <= valores[k].length) {
									valoresListActual.add(valores[k][j]);
								} else {
									valoresListActual.add(null);
								}
								break;
							}
						}
					}
					
					String valoresListActualArray[] = new String[valoresListActual
							.size()];
					valoresListActualArray = valoresListActual
							.toArray(valoresListActualArray);
					valoresList.add(valoresListActualArray);
				}
			}

			for (int i = 0; i < valoresList.size(); i++) {
				// cont++;
				Element row = doc.createElement("ROW");
				for (int j = 0; j < valoresList.get(i).length; j++) {
					Element elemento = doc.createElement(encabezadoList.get(j));
					if (valoresList.get(i)[j] != null) {
						Text texto = doc.createTextNode(valoresList.get(i)[j]
								.toString());
						elemento.appendChild(texto);
					}
					row.appendChild(elemento);
				}
				raiz.appendChild(row);
			}

			// *serializar*
			OutputFormat format = new OutputFormat(doc);
			// XMLSerializer serializer = new XMLSerializer(System.out, format);

			File file = new File(rutaArchivoXML);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			XMLSerializer serializer = new XMLSerializer(bos, format);
			serializer.serialize(doc);

			bos.flush();
			fos.flush();

			bos.close();
			fos.close();

			// return cont;
			return valoresList.size();
		}

		public int updateFilas(String rutaArchivoXML, Object camposValores[][],
				Object condiciones[][]) throws SAXException, IOException {
			List<String> encabezadoList = new ArrayList<String>();
			List<String> cuerpoActualList = new ArrayList<String>();
			int cont = 0;

			DOMParser analizador = new DOMParser();
			analizador.parse(rutaArchivoXML);
			Document doc = analizador.getDocument();

			Element raiz = doc.getDocumentElement(); // DATA
			// System.out.println("El elemento raíz es "+ raiz.getNodeName());
			NodeList nodeRows = raiz.getChildNodes();
			boolean primeraFila = true;
			for (int i = 0; i < nodeRows.getLength(); i++) { // RECORRO LOS ROWS
				if (nodeRows.item(i).getNodeType() == Node.ELEMENT_NODE) { // ROW
					NodeList nodeFields = nodeRows.item(i).getChildNodes();
					for (int j = 0; j < nodeFields.getLength(); j++) { // RECORRO
																		// LOS
																		// FIELDS
						if (nodeFields.item(j).getNodeType() == Node.ELEMENT_NODE) { // FIELD
							if (primeraFila) {
								encabezadoList.add(nodeFields.item(j)
										.getNodeName());
							}
							if (nodeFields.item(j).getFirstChild() == null) {
								cuerpoActualList.add(null);
							} else {
								cuerpoActualList.add(nodeFields.item(j)
										.getFirstChild().getNodeValue());
							}

						} // FIN FIELD
					} // FIN RECORRO LOS FIELDS
					String cuerpoActualArray[] = new String[cuerpoActualList
							.size()];
					cuerpoActualArray = cuerpoActualList
							.toArray(cuerpoActualArray);
					if (cumpleCondicion(encabezadoList, cuerpoActualArray,
							condiciones)) {
						// ACTUALIZO LA FILA QUE CUMPLE CON LA CONDICION
						cont++;
						for (int k = 0; k < camposValores.length; k++) {
							for (int j = 0; j < nodeFields.getLength(); j++) { // RECORRO
																				// LOS
																				// FIELDS
								if (nodeFields.item(j).getNodeType() == Node.ELEMENT_NODE) { // FIELD
									if (nodeFields.item(j).getNodeName()
											.equals(camposValores[k][0])) {
										if (camposValores[k][1] == null) {
											if (nodeFields.item(j)
													.getFirstChild() != null) {
												nodeFields
														.item(j)
														.removeChild(
																nodeFields
																		.item(j)
																		.getFirstChild());
											}
										} else {
											nodeFields.item(j).setTextContent(
													camposValores[k][1]
															.toString());
										}
									}
								} // FIN FIELD
							} // FIN RECORRO LOS FIELDS
						}
						// FIN ACTUALIZO LA FILA QUE CUMPLE CON LA CONDICION
					}
					cuerpoActualList.clear();
					primeraFila = false;
				} // FIN ROW
			} // FIN RECORRO LOS ROWS

			// *serializar*
			OutputFormat format = new OutputFormat(doc);
			// XMLSerializer serializer = new XMLSerializer(System.out, format);

			File file = new File(rutaArchivoXML);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			XMLSerializer serializer = new XMLSerializer(bos, format);
			serializer.serialize(doc);

			bos.flush();
			fos.flush();

			bos.close();
			fos.close();

			return cont;
		}

		public int deleteFilas(String rutaArchivoXML, Object[][] condiciones)
				throws SAXException, IOException {
			List<String> encabezadoList = new ArrayList<String>();
			List<String> cuerpoActualList = new ArrayList<String>();
			int cont = 0;

			DOMParser analizador = new DOMParser();
			analizador.parse(rutaArchivoXML);
			Document doc = analizador.getDocument();

			Element raiz = doc.getDocumentElement(); // DATA
			// System.out.println("El elemento raíz es "+ raiz.getNodeName());
			//NodeList nodeRows = raiz.getChildNodes();
			boolean primeraFila = true;
			for (int i = 0; i < raiz.getChildNodes().getLength(); i++) { // RECORRO LOS ROWS
				if (raiz.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) { // ROW
					NodeList nodeFields = raiz.getChildNodes().item(i).getChildNodes();
					for (int j = 0; j < nodeFields.getLength(); j++) { // RECORRO
																		// LOS
																		// FIELDS
						if (nodeFields.item(j).getNodeType() == Node.ELEMENT_NODE) { // FIELD
							if (primeraFila) {
								encabezadoList.add(nodeFields.item(j)
										.getNodeName());
							}
							if (nodeFields.item(j).getFirstChild() == null) {
								cuerpoActualList.add(null);
							} else {
								cuerpoActualList.add(nodeFields.item(j)
										.getFirstChild().getNodeValue());
							}

						} // FIN FIELD
					} // FIN RECORRO LOS FIELDS
					String cuerpoActualArray[] = new String[cuerpoActualList
							.size()];
					cuerpoActualArray = cuerpoActualList
							.toArray(cuerpoActualArray);
					if (cumpleCondicion(encabezadoList, cuerpoActualArray,
							condiciones)) {
						// ELIMINO LA FILA QUE CUMPLE CON LA CONDICION
						cont++;
						raiz.removeChild(raiz.getChildNodes().item(i));
						i--;
						// FIN ELIMINO LA FILA QUE CUMPLE CON LA CONDICION
					}
					cuerpoActualList.clear();
					primeraFila = false;
				} // FIN ROW
			} // FIN RECORRO LOS ROWS

			// *serializar*
			OutputFormat format = new OutputFormat(doc);
			// XMLSerializer serializer = new XMLSerializer(System.out, format);

			File file = new File(rutaArchivoXML);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			XMLSerializer serializer = new XMLSerializer(bos, format);
			serializer.serialize(doc);

			bos.flush();
			fos.flush();

			bos.close();
			fos.close();

			return cont;
		}

		public static boolean cumpleCondicion(List<String> encabezadoList,
				String cuerpoActualArray[], Object condiciones[][]) {
			boolean cumple = false;

			if (condiciones == null || condiciones.length == 0) {
				cumple = true;
			} else {

				for (int i = 0; i < condiciones.length; i++) {
					boolean comparacionActual = true;
					for (int j = 0; j < cuerpoActualArray.length; j++) {
						if (encabezadoList.get(j).equals(condiciones[i][0])) {
							comparacionActual = false;

							try {
								DateFormat dateFormat = DateFormat
										.getInstance();
								dateFormat.setLenient(false);
								Date valor = dateFormat
										.parse(cuerpoActualArray[j].trim());

								int compareToResult = valor
										.compareTo(dateFormat
												.parse((condiciones[i][2]
														.toString()).trim()));

								if (condiciones[i][1].equals("=")) {
									comparacionActual = (compareToResult == 0);
								} else if (condiciones[i][1].equals("!=")) {
									comparacionActual = (compareToResult != 0);
								} else if (condiciones[i][1].equals(">")) {
									comparacionActual = (compareToResult < 0);
								} else if (condiciones[i][1].equals("<")) {
									comparacionActual = (compareToResult > 0);
								} else if (condiciones[i][1].equals(">=")) {
									comparacionActual = (compareToResult >= 0);
								} else if (condiciones[i][1].equals("<=")) {
									comparacionActual = (compareToResult <= 0);
								}

								// System.out.println("DateFormat.getInstance()");

							} catch (ParseException | NullPointerException peDefaultInstance) {
								try {
									DateFormat dateFormat = DateFormat
											.getDateTimeInstance();
									dateFormat.setLenient(false);
									Date valor = dateFormat
											.parse(cuerpoActualArray[j].trim());

									int compareToResult = valor
											.compareTo(dateFormat.parse((condiciones[i][2]
													.toString()).trim()));

									if (condiciones[i][1].equals("=")) {
										comparacionActual = (compareToResult == 0);
									} else if (condiciones[i][1].equals("!=")) {
										comparacionActual = (compareToResult != 0);
									} else if (condiciones[i][1].equals(">")) {
										comparacionActual = (compareToResult < 0);
									} else if (condiciones[i][1].equals("<")) {
										comparacionActual = (compareToResult > 0);
									} else if (condiciones[i][1].equals(">=")) {
										comparacionActual = (compareToResult >= 0);
									} else if (condiciones[i][1].equals("<=")) {
										comparacionActual = (compareToResult <= 0);
									}

									// System.out.println("DateFormat.getDateTimeInstance()");

								} catch (ParseException | NullPointerException peDateTime) {
									try {
										DateFormat dateFormat = DateFormat
												.getDateInstance();
										dateFormat.setLenient(false);
										Date valor = dateFormat
												.parse(cuerpoActualArray[j]
														.trim());

										int compareToResult = valor
												.compareTo(dateFormat
														.parse((condiciones[i][2]
																.toString())
																.trim()));

										if (condiciones[i][1].equals("=")) {
											comparacionActual = (compareToResult == 0);
										} else if (condiciones[i][1]
												.equals("!=")) {
											comparacionActual = (compareToResult != 0);
										} else if (condiciones[i][1]
												.equals(">")) {
											comparacionActual = (compareToResult < 0);
										} else if (condiciones[i][1]
												.equals("<")) {
											comparacionActual = (compareToResult > 0);
										} else if (condiciones[i][1]
												.equals(">=")) {
											comparacionActual = (compareToResult >= 0);
										} else if (condiciones[i][1]
												.equals("<=")) {
											comparacionActual = (compareToResult <= 0);
										}

										// System.out.println("DateFormat.getDateInstance()");

									} catch (ParseException
											| NullPointerException peDate) {
										try {
											DateFormat dateFormat = DateFormat
													.getTimeInstance();
											dateFormat.setLenient(false);
											Date valor = dateFormat
													.parse(cuerpoActualArray[j]
															.trim());

											int compareToResult = valor
													.compareTo(dateFormat
															.parse((condiciones[i][2]
																	.toString())
																	.trim()));

											if (condiciones[i][1].equals("=")) {
												comparacionActual = (compareToResult == 0);
											} else if (condiciones[i][1]
													.equals("!=")) {
												comparacionActual = (compareToResult != 0);
											} else if (condiciones[i][1]
													.equals(">")) {
												comparacionActual = (compareToResult < 0);
											} else if (condiciones[i][1]
													.equals("<")) {
												comparacionActual = (compareToResult > 0);
											} else if (condiciones[i][1]
													.equals(">=")) {
												comparacionActual = (compareToResult >= 0);
											} else if (condiciones[i][1]
													.equals("<=")) {
												comparacionActual = (compareToResult <= 0);
											}

											// System.out.println("DateFormat.getTimeInstance()");

										} catch (ParseException
												| NullPointerException peTime) {
											try {
												SimpleDateFormat dateFormat = new SimpleDateFormat();
												dateFormat.setLenient(false);
												Date valor = dateFormat
														.parse(cuerpoActualArray[j]
																.trim());

												int compareToResult = valor
														.compareTo(dateFormat
																.parse((condiciones[i][2]
																		.toString())
																		.trim()));

												if (condiciones[i][1]
														.equals("=")) {
													comparacionActual = (compareToResult == 0);
												} else if (condiciones[i][1]
														.equals("!=")) {
													comparacionActual = (compareToResult != 0);
												} else if (condiciones[i][1]
														.equals(">")) {
													comparacionActual = (compareToResult < 0);
												} else if (condiciones[i][1]
														.equals("<")) {
													comparacionActual = (compareToResult > 0);
												} else if (condiciones[i][1]
														.equals(">=")) {
													comparacionActual = (compareToResult >= 0);
												} else if (condiciones[i][1]
														.equals("<=")) {
													comparacionActual = (compareToResult <= 0);
												}

												// System.out.println("SimpleDateFormat()");

											} catch (ParseException
													| NullPointerException pe) {
												try {

													SimpleDateFormat dateFormat = new SimpleDateFormat(
															"yyyy-MM-dd HH:mm:ss");
													dateFormat
															.setLenient(false);
													Date valor = dateFormat
															.parse(cuerpoActualArray[j]
																	.trim());

													int compareToResult = valor
															.compareTo(dateFormat
																	.parse((condiciones[i][2]
																			.toString())
																			.trim()));

													if (condiciones[i][1]
															.equals("=")) {
														comparacionActual = (compareToResult == 0);
													} else if (condiciones[i][1]
															.equals("!=")) {
														comparacionActual = (compareToResult != 0);
													} else if (condiciones[i][1]
															.equals(">")) {
														comparacionActual = (compareToResult < 0);
													} else if (condiciones[i][1]
															.equals("<")) {
														comparacionActual = (compareToResult > 0);
													} else if (condiciones[i][1]
															.equals(">=")) {
														comparacionActual = (compareToResult >= 0);
													} else if (condiciones[i][1]
															.equals("<=")) {
														comparacionActual = (compareToResult <= 0);
													}

													// System.out.println("SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\")");

												} catch (ParseException
														| NullPointerException peSQL) {
													try {
														SimpleDateFormat dateFormat = new SimpleDateFormat(
																"yyyy-MM-dd");
														dateFormat
																.setLenient(false);
														Date valor = dateFormat
																.parse(cuerpoActualArray[j]
																		.trim());

														int compareToResult = valor
																.compareTo(dateFormat
																		.parse((condiciones[i][2]
																				.toString())
																				.trim()));

														if (condiciones[i][1]
																.equals("=")) {
															comparacionActual = (compareToResult == 0);
														} else if (condiciones[i][1]
																.equals("!=")) {
															comparacionActual = (compareToResult != 0);
														} else if (condiciones[i][1]
																.equals(">")) {
															comparacionActual = (compareToResult < 0);
														} else if (condiciones[i][1]
																.equals("<")) {
															comparacionActual = (compareToResult > 0);
														} else if (condiciones[i][1]
																.equals(">=")) {
															comparacionActual = (compareToResult >= 0);
														} else if (condiciones[i][1]
																.equals("<=")) {
															comparacionActual = (compareToResult <= 0);
														}

														// System.out.println("SimpleDateFormat(\"yyyy-MM-dd\")");

													} catch (
															ParseException
															| NullPointerException peDateSQL) {
														try {
															int valor = Integer
																	.parseInt(cuerpoActualArray[j]
																			.trim());

															int compareToResult = Integer
																	.parseInt((condiciones[i][2]
																			.toString())
																			.trim());

															if (condiciones[i][1]
																	.equals("=")) {
																comparacionActual = (valor == compareToResult);
															} else if (condiciones[i][1]
																	.equals("!=")) {
																comparacionActual = (valor != compareToResult);
															} else if (condiciones[i][1]
																	.equals(">")) {
																comparacionActual = (valor > compareToResult);
															} else if (condiciones[i][1]
																	.equals("<")) {
																comparacionActual = (valor < compareToResult);
															} else if (condiciones[i][1]
																	.equals(">=")) {
																comparacionActual = (valor >= compareToResult);
															} else if (condiciones[i][1]
																	.equals("<=")) {
																comparacionActual = (valor <= compareToResult);
															}

															// System.out.println("Integer");

														} catch (
																NumberFormatException
																| NullPointerException nfe) {
															try {
																float valor = Float
																		.parseFloat(cuerpoActualArray[j]
																				.trim());

																float compareToResult = Float
																		.parseFloat((condiciones[i][2]
																				.toString())
																				.trim());

																if (condiciones[i][1]
																		.equals("=")) {
																	comparacionActual = (valor == compareToResult);
																} else if (condiciones[i][1]
																		.equals("!=")) {
																	comparacionActual = (valor != compareToResult);
																} else if (condiciones[i][1]
																		.equals(">")) {
																	comparacionActual = (valor > compareToResult);
																} else if (condiciones[i][1]
																		.equals("<")) {
																	comparacionActual = (valor < compareToResult);
																} else if (condiciones[i][1]
																		.equals(">=")) {
																	comparacionActual = (valor >= compareToResult);
																} else if (condiciones[i][1]
																		.equals("<=")) {
																	comparacionActual = (valor <= compareToResult);
																}

																// System.out.println("Float");

															} catch (
																	NumberFormatException
																	| NullPointerException nfeFloat) {
																try {
																	double valor = Double
																			.parseDouble(cuerpoActualArray[j]
																					.trim());

																	double compareToResult = Double
																			.parseDouble((condiciones[i][2]
																					.toString())
																					.trim());

																	if (condiciones[i][1]
																			.equals("=")) {
																		comparacionActual = (valor == compareToResult);
																	} else if (condiciones[i][1]
																			.equals("!=")) {
																		comparacionActual = (valor != compareToResult);
																	} else if (condiciones[i][1]
																			.equals(">")) {
																		comparacionActual = (valor > compareToResult);
																	} else if (condiciones[i][1]
																			.equals("<")) {
																		comparacionActual = (valor < compareToResult);
																	} else if (condiciones[i][1]
																			.equals(">=")) {
																		comparacionActual = (valor >= compareToResult);
																	} else if (condiciones[i][1]
																			.equals("<=")) {
																		comparacionActual = (valor <= compareToResult);
																	}

																	// System.out.println("Double");

																} catch (
																		NumberFormatException
																		| NullPointerException nfeDouble) {
																	/*
																	 * try {
																	 * boolean
																	 * valor =
																	 * Boolean
																	 * .parseBoolean
																	 * (
																	 * cuerpoActualArray
																	 * [
																	 * j].trim()
																	 * );
																	 * 
																	 * boolean
																	 * compareToResult
																	 * = Boolean
																	 * .
																	 * parseBoolean
																	 * (((String
																	 * )
																	 * condiciones
																	 * [
																	 * i][2]).trim
																	 * ());
																	 * 
																	 * if(
																	 * condiciones
																	 * [
																	 * i][1].equals
																	 * ("=")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) == 0);
																	 * }else if(
																	 * condiciones
																	 * [i][1]
																	 * .equals
																	 * ("!=")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) != 0);
																	 * }else if(
																	 * condiciones
																	 * [i][1]
																	 * .equals
																	 * (">")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) > 0);
																	 * }else if(
																	 * condiciones
																	 * [i][1]
																	 * .equals
																	 * ("<")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) < 0);
																	 * }else if(
																	 * condiciones
																	 * [i][1]
																	 * .equals
																	 * (">=")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) >= 0);
																	 * }else if(
																	 * condiciones
																	 * [i][1]
																	 * .equals
																	 * ("<=")){
																	 * comparacionActual
																	 * =
																	 * (Boolean
																	 * .compare
																	 * (valor,
																	 * compareToResult
																	 * ) <= 0);
																	 * }
																	 * 
																	 * //System.out
																	 * .println(
																	 * "Boolean"
																	 * );
																	 * 
																	 * } catch(
																	 * NumberFormatException
																	 * |
																	 * NullPointerException
																	 * nfeBoolean
																	 * ) {
																	 */
																	try {
																		String valor = cuerpoActualArray[j];
																		if (valor == null) {

																			if (condiciones[i][1]
																					.equals("=")) {
																				comparacionActual = (condiciones[i][2] == null);
																			} else if (condiciones[i][1]
																					.equals("!=")) {
																				comparacionActual = (condiciones[i][2] != null);
																			} else if (condiciones[i][1]
																					.equals(">")) {
																				comparacionActual = false;
																			} else if (condiciones[i][1]
																					.equals("<")) {
																				comparacionActual = false;
																			} else if (condiciones[i][1]
																					.equals(">=")) {
																				comparacionActual = (condiciones[i][2] == null);
																			} else if (condiciones[i][1]
																					.equals("<=")) {
																				comparacionActual = (condiciones[i][2] == null);
																			}

																			// System.out.println("NULL");
																		} else {
																			valor = valor
																					.trim();

																			String compareToResult = (condiciones[i][2]
																					.toString())
																					.trim();

																			if (condiciones[i][1]
																					.equals("=")) {
																				comparacionActual = (valor
																						.equals(compareToResult));
																			} else if (condiciones[i][1]
																					.equals("!=")) {
																				comparacionActual = (!valor
																						.equals(compareToResult));
																			} else if (condiciones[i][1]
																					.equals(">")) {
																				comparacionActual = false;
																			} else if (condiciones[i][1]
																					.equals("<")) {
																				comparacionActual = false;
																			} else if (condiciones[i][1]
																					.equals(">=")) {
																				comparacionActual = (valor
																						.equals(compareToResult));
																			} else if (condiciones[i][1]
																					.equals("<=")) {
																				comparacionActual = (valor
																						.equals(compareToResult));
																			}

																			// System.out.println("String");

																		}
																	} catch (NullPointerException npeString) {
																	}
																	// }
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}

						}
					}

					if (i == 0) {
						cumple = comparacionActual;
					} else {
						if (((Boolean) condiciones[i - 1][3]).booleanValue() == true) {
							cumple = cumple && comparacionActual;
						} else {
							cumple = cumple || comparacionActual;
						}
					}
				}
			}

			return cumple;
		}

	}

	private class SAXImplementacion extends DefaultHandler {
		private String rutaArchivoXML;
		private List<String[]> resultado;
		private List<String[]> cuerpoList;
		private List<String> encabezadoList;
		private List<String> cuerpoActualList;
		private Object condiciones[][];
		private String campos[];
		private boolean primeraFila;
		private String resultadoArray[][];

		private String campo;
		private String valor;
		private Integer desdeFila;
		private Integer numFilas;
		
		private Object ordenarPor;
		private boolean asc;

		public SAXImplementacion() {

		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			resultado = new ArrayList<String[]>();
			cuerpoList = new ArrayList<String[]>();
			encabezadoList = new ArrayList<String>();
			// cuerpoActualList = new ArrayList<String>();
			primeraFila = true;
			valor = "";
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("DATA")) {

			} else if (localName.equals("ROW")) {
				cuerpoActualList = new ArrayList<String>();
			} else {
				campo = localName;
				valor = "";
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			StringBuffer todisplay = new StringBuffer();
			// if (length > 1) {
			todisplay.append(ch, start, length);
			if (campo != null) {
				valor = todisplay.toString();
			}
			// }
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("DATA")) {

			} else if (localName.equals("ROW")) {
				if (primeraFila) {
					primeraFila = false;
				}

				if (cuerpoActualList != null) {
					String cuerpoActualArray[] = new String[cuerpoActualList
							.size()];
					cuerpoActualArray = cuerpoActualList
							.toArray(cuerpoActualArray);
					if (DOMImplementacion.cumpleCondicion(encabezadoList,
							cuerpoActualArray, condiciones)) {
						cuerpoList.add(cuerpoActualArray);
					}
					cuerpoActualList = null;
				}

			} else {

				if (cuerpoActualList == null) {
					cuerpoActualList = new ArrayList<String>();
				}

				if (primeraFila) {
					encabezadoList.add(campo);
				}

				cuerpoActualList.add(valor);

				campo = null;
				valor = "";
			}
		}

		@Override
		public void endDocument() throws SAXException {
			super.endDocument();

			int desde = 0;
			if(desdeFila != null && desdeFila >= 0){
				desde = desdeFila;
			}
			
			int hasta = cuerpoList.size();
			if(numFilas != null){
				if(desde + numFilas < cuerpoList.size()){
					hasta = desde + numFilas;
				}
			}
			
			if (cuerpoList.size() > 0) {
				
				if(ordenarPor != null) {
					int indexOrder = -1;
					if(ordenarPor instanceof Integer){
						indexOrder = Integer.parseInt(ordenarPor.toString());
					}else if(ordenarPor instanceof String){
						for (int i = 0; i < encabezadoList.size(); i++) {
							if(ordenarPor.toString().equals(encabezadoList.get(i))) {
								indexOrder = i;
								break;
							}
						}
					}
					
					if(indexOrder >= 0){
						cuerpoList = new QuickSortMatriz().quickSort(cuerpoList, cuerpoList.size(), indexOrder, asc);
					}
				}
				
				String encabezadoArray[] = new String[encabezadoList.size()];
				encabezadoArray = encabezadoList.toArray(encabezadoArray);
				if (campos == null || campos.length == 0) {
					resultado.add(encabezadoArray);
					
					if(desde > 0 || hasta < cuerpoList.size()){
						for (int i = desde; i < hasta; i++) {
							resultado.add(cuerpoList.get(i));
						}
					}else {
						resultado.addAll(cuerpoList);
					}
					
				} else {
					// resultado.add(campos);
					for (int i = desde; i < hasta; i++) {
						List<String> camposFinal = new ArrayList<String>();
						List<String> cuerpoAdd = new ArrayList<String>();
						for (int j = 0; j < campos.length; j++) {
							for (int k = 0; k < encabezadoArray.length; k++) {
								if (campos[j].equals(encabezadoArray[k])) {
									if (i == 0) {
										camposFinal.add(encabezadoArray[k]);
									}
									cuerpoAdd.add(cuerpoList.get(i)[k]);
								}
							}
						}
						if (i == 0) {
							String camposFinalArray[] = new String[camposFinal
									.size()];
							camposFinalArray = camposFinal
									.toArray(camposFinalArray);
							resultado.add(camposFinalArray);
						}

						String cuerpoAddArray[] = new String[cuerpoAdd.size()];
						cuerpoAddArray = cuerpoAdd.toArray(cuerpoAddArray);
						resultado.add(cuerpoAddArray);
					}
				}
			}

			resultadoArray = null;

			if (resultado.size() > 0) {
				resultadoArray = new String[resultado.size()][resultado.get(0).length];
				resultadoArray = resultado.toArray(resultadoArray);
			} else {
				resultadoArray = new String[0][0];
			}

		}

		/**
		 * 
		 * @param rutaArchivoXML
		 *            = ruta al Archivo XML a analizar
		 * @param campos
		 *            if(campos == null || campos.length == 0){extrae todos los
		 *            campos}else{extrae estos campos}
		 * @param condiciones
		 *            = // Object condiciones[][] = // {{ // "campo", // "=", //
		 *            Igualdad (=, !=, >, <, >=, <=) // "valor", // true // true
		 *            = AND, false = OR, null = Don't Care // }, // { //
		 *            "campo2", // "!=", // Igualdad (=, !=, >, <, >=, <=) //
		 *            "valor2", // false // if(thisValue == true){AND}else{OR}
		 *            // }};
		 * @return los valores en forma de tabla o retorna un List Empty
		 * @throws SAXException
		 * @throws IOException
		 */
		public String[][] getFilas(String rutaArchivoXML, String campos[],
				Object condiciones[][], Integer desdeFila, Integer numFilas, Object ordenarPor, boolean asc) throws SAXException, IOException {
			// System.out.println("SAX");
			this.rutaArchivoXML = rutaArchivoXML;
			this.condiciones = condiciones;
			this.campos = campos;
			this.desdeFila = desdeFila;
			this.numFilas = numFilas;
			
			this.ordenarPor = ordenarPor;
			this.asc = asc;

			SAXParser analizador = new SAXParser();
			analizador.setContentHandler(this);
			analizador.setErrorHandler(this);
			analizador.parse(rutaArchivoXML);

			return resultadoArray;
		}

		@Override
		public void warning(SAXParseException e) throws SAXException {
			super.warning(e);
			System.out.println(e.getMessage() + "\r\n");
			System.out.println(" Error o Warning en linea " + e.getLineNumber()
					+ ", columna " + e.getColumnNumber() + "\r\n");
			try {
				resultadoArray = new DOMImplementacion().getFilas(rutaArchivoXML,
						campos, condiciones, desdeFila, numFilas, ordenarPor, asc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		

		@Override
		public void error(SAXParseException e) throws SAXException {
			super.error(e);
			System.out.println(e.getMessage() + "\r\n");
			System.out.println(" en la linea " + e.getLineNumber()
					+ ", columna " + e.getColumnNumber() + "\r\n");
			try {
				resultadoArray = new DOMImplementacion().getFilas(rutaArchivoXML,
						campos, condiciones, desdeFila, numFilas, ordenarPor, asc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			super.fatalError(e);
			System.out.println(e.getMessage() + "\r\n");
			System.out.println(" en la linea " + e.getLineNumber()
					+ ", columna " + e.getColumnNumber() + "\r\n");
			try {
				resultadoArray = new DOMImplementacion().getFilas(rutaArchivoXML,
						campos, condiciones, desdeFila, numFilas, ordenarPor, asc);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
