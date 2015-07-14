/**
 * 
 */
package vista;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import modelo.utils.XML;

import org.xml.sax.SAXException;

/**
 * @author adalberto
 *
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			URL url = new URL("http://www.vistatd.com/consola_web/entrada_apps_web_service/vista/php/index.php");
			URLConnection connection = url.openConnection();
			System.out.println("connection -->"+connection);
			System.out.println("connection 11111-->"+connection.getURL());
			connection.setDoInput(true);
			InputStream inStream = connection.getInputStream();
			System.out.println("inStream -->"+inStream.toString());
			BufferedReader input = new BufferedReader(new InputStreamReader(
			        inStream));
			String line = "";
			HashMap<String, String> monitors = new HashMap<String, String>();
			String[] str = null;
			while ((line = input.readLine()) != null){
			    System.out.println("Files in the folder are -->"+line);
			    /*str = line.split(":");
			    monitors.put(str[0], str[1]);
			    System.out.println(str[0]+" --- "+str[1]);*/
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*String copyFrom[] = { "d", "e", "c", "a", "f", "f", "e",
			    "i", "n", "a", "t", "e", "d" };
        String copyTo[] = new String[3];

        System.arraycopy(copyFrom, 0, copyTo, 0, copyFrom.length <= copyTo.length ? copyFrom.length : copyTo.length);
        //System.out.println(new String(copyTo));
        for (int i = 0; i < copyTo.length; i++) {
			System.out.println(copyTo[i]);
		}
        System.out.println("OTRA VEZ");
        copyTo = new String[3];
        for (int i = 0; i < copyTo.length; i++) {
			System.out.println(copyTo[i]);
		}*/
		
		/*XML xml = new XML();
		
		
		
		
		try {
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		XML xml = new XML();
		try {
			
			String campos[] = {
					"DATE",
					"col1",
					"CARTINFO",
					"idCARTINFO",
					
					"col2",
					"USUARIO",
					"DATETIME"
				};
				
				String valores[][] = {{
					"",
					"HELEN",
					"CART...INFO",
					"15",
					"1456",
					"2015-02-01",
					"2015-02-01 05:04:32"
				},
				{
					"101",
					"HECTOR",
					"CART...INFO",
					"489",
					"1456",
					"2015-02-01",
					"2015-02-01 05:04:32"
				}};
			
				//System.out.println(DateFormat.getInstance().format(new Date(2015 - 1900, 1, 25, 3, 21, 5)));
				//System.out.println(DateFormat.getDateTimeInstance().format(new Date(2015 - 1900, 1, 25, 3, 21, 5)));
				//System.out.println(DateFormat.getDateInstance().format(new Date(2015 - 1900, 1, 25, 3, 21, 5)));
				//System.out.println(DateFormat.getTimeInstance().format(new Date(2015 - 1900, 1, 25, 3, 21, 5)));
				//System.out.println(new SimpleDateFormat().format(new Date(2015 - 1900, 1, 25, 3, 21, 5)));
			
				
			Object condiciones[][] = 
			{
	 		{
				"USUARIO",
				"=", // Igualdad (=, !=, >, <, >=, <=)
				"vsdvs",
				true // if(thisValue == true){AND}else{OR}
			},
			};
			
			ResourceBundle rb = ResourceBundle.getBundle("modelo.properties.xmlbd");
			System.out.println(rb.getString("direccion_archivos"));
			
			Object[][] camposValores = {{"USUARIO", "ADALBERTO TOLEDO"}, {"DATE", new Date()}};
			
			System.out.println("Filas insertadas: " + xml.insertFilas(rb.getString("direccion_archivos") + File.separator + "cartinfo1.xml", campos, valores));
			//System.out.println("filas actualizadas: " + xml.updateFilas(rb.getString("direccion_archivos") + File.separator + "cartinfo1.xml", camposValores, condiciones));
			Date dInicial = new Date();
			String resultado[][] = xml.getFilas(rb.getString("direccion_archivos") + File.separator + "cartinfo1.xml", campos, null, -1, null, null, false, false);
			Date dFinal = new Date();
			//System.out.println("duró: " + (dFinal.getTime() - dInicial.getTime()) + " milisegundos");
			//System.out.println("Imprimo Resultado:");
			/*if(resultado.length > 0){
				for (int i = 0; i < resultado.length; i++) {
					for (int j = 0; j < resultado[i].length; j++) {
						if(resultado[i][j] == null){
							System.out.print("NULL \t");
						}else if(resultado[i][j].length() == 0){
							System.out.print("EMPTY \t");
						}else{
							System.out.print(resultado[i][j] + " \t");
						}
						
					}
					System.out.println();
				}
			}else{
				System.out.println("No trajo ningun resultado");
			}*/
			//System.out.println("Fin Imprimo Resultado:");
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
