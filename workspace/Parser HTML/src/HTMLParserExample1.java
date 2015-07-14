import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 */

/**
 * @author adalberto
 *
 */
public class HTMLParserExample1 {

	/**
	 * 
	 */
	public HTMLParserExample1() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Document doc;
		try {
	 
			// need http protocol
			String url = "http://www.cne.gov.ve/web/registro_electoral/ce.php?nacionalidad=V&cedula=17692816";
			System.out.println("URL = " + url);
			doc = Jsoup.connect(url).get();
	 
			// get page title
			/*String title = doc.title();
			
			System.out.println("title : " + title);
	 
			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 
				// get the value from href attribute
				System.out.println("\nlink : " + link.attr("href"));
				System.out.println("text : " + link.text());
	 
			}*/
			
			boolean agarrarElOtro = false;
			Elements tds = doc.getElementsByTag("td");
			String text = null;
			String nombre = null;
			String cedula = null;
			String estado = null;
			String municipio = null;
			String parroquia = null;
			String centro = null;
			String direccion = null;
			for (int i = 0; i < tds.size(); i++) {
				
				if(agarrarElOtro){
					//System.out.println(tds.get(i).html());
					if(text.toLowerCase().indexOf("nombre") >= 0) {
						nombre = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("cédula") >= 0) {
						cedula = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("estado") >= 0) {
						estado = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("municipio") >= 0) {
						municipio = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("parroquia") >= 0) {
						parroquia = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("centro") >= 0) {
						centro = tds.get(i).text();
					} else if(text.toLowerCase().indexOf("dirección") >= 0) {
						direccion = tds.get(i).text();
					}
					agarrarElOtro = false;
				}
				
				if(tds.get(i).html().toLowerCase().indexOf("nombre") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("cédula") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("estado") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("municipio") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("parroquia") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("centro:") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				} else if(tds.get(i).html().toLowerCase().indexOf("dirección") >= 0) {
					//System.out.println(tds.get(i).html());
					text = tds.get(i).html();
					agarrarElOtro = true;
				}
				
				
			}
			
			System.out.println("Cédula = " + cedula);
			System.out.println("Nombre = " + nombre);
			System.out.println("Estado = " + estado);
			System.out.println("Municipio = " + municipio);
			System.out.println("Parroquia = " + parroquia);
			System.out.println("Centro = " + centro);
			System.out.println("Dirección = " + direccion);
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
