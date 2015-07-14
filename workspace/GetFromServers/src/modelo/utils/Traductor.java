/**
 * 
 */
package modelo.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/*import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;*/

/**
 * @author adalberto
 * 
 */
public class Traductor {

	private ResourceBundle rb;
	private String archivoPropiedades;
	private Locale localidad;
	private String idioma, pais;
	private static final String PAQUETE_IDIOMA = "modelo.properties.idiomas";
	private static final String URL_IDIOMA = "modelo/properties/idiomas";
	private static final String ARCHIVO_DEFAULT = "Archivo";

	/**
	 * 
	 */
	public Traductor() {
		archivoPropiedades = PAQUETE_IDIOMA + "." + ARCHIVO_DEFAULT;
		localidad = Locale.getDefault();
		rb = ResourceBundle.getBundle(archivoPropiedades);
		this.idioma = localidad.getLanguage();
		this.pais = localidad.getCountry();
	}

	public Traductor(String idioma, String pais) {
		archivoPropiedades = PAQUETE_IDIOMA + "." + ARCHIVO_DEFAULT;
		if (idioma == null || pais == null) {
			localidad = Locale.getDefault();
			idioma = localidad.getLanguage();
			pais = localidad.getCountry();
		} else {
			localidad = new Locale(idioma, pais);
		}
		rb = ResourceBundle.getBundle(archivoPropiedades, localidad);
		this.idioma = localidad.getLanguage();
		this.pais = localidad.getCountry();
	}

	public Traductor(String idioma, String pais, String archivoPropiedades) {
		if (idioma == null || pais == null) {
			localidad = Locale.getDefault();
			idioma = localidad.getLanguage();
			pais = localidad.getCountry();
		} else {
			localidad = new Locale(idioma, pais);
		}
		if (archivoPropiedades == null) {
			archivoPropiedades = PAQUETE_IDIOMA + "." + ARCHIVO_DEFAULT;
		}
		rb = ResourceBundle.getBundle(archivoPropiedades, localidad);
		this.idioma = localidad.getLanguage();
		this.pais = localidad.getCountry();
		this.archivoPropiedades = archivoPropiedades;
	}

	public String traducir(String texto) {
		try{
			if (texto != null) {
				texto = rb.getString(texto);
			}else{
				texto = "";
			}
		}catch(Exception e){
			texto = texto.trim().replace('_', ' ');
		}

		return texto;
	}

	public String[] getIdiomasPaisesDisponibles() {
		final String archivo = archivoPropiedades.substring(archivoPropiedades
				.lastIndexOf(".") + 1);

		String f[] = new File(Thread.currentThread().getContextClassLoader()
				.getResource(URL_IDIOMA).getFile()).list(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String arg1) {
				if (arg1.startsWith(archivo) && arg1.endsWith(".properties")) {
					return true;
				} else {
					return false;
				}
			}
		});

		for (int i = 0; i < f.length; i++) {
			f[i] = f[i].replace(archivo, "").replaceAll(".properties", "")
					.replaceFirst("_", "");
		}

		Arrays.sort(f);

		return f;
	}

	public String getIdioma() {
		return idioma;
	}

	public String getPais() {
		return pais;
	}

	public String getNombreArchivoPropiedades() {
		return archivoPropiedades
				.substring(archivoPropiedades.lastIndexOf(".") + 1);
	}

	public void setIdiomaPais(String idioma, String pais) {
		if (idioma != null && pais != null) {
			localidad = new Locale(idioma, pais);

			rb = ResourceBundle.getBundle(archivoPropiedades, localidad);
			this.idioma = localidad.getLanguage();
			this.pais = localidad.getCountry();
		}
	}

	/*public static Traductor getTraductorSesion(HttpServletRequest request,
			HttpServletResponse response) {
		Traductor traductor = (Traductor) request.getSession().getAttribute(
				"traductor");
		String[] idioma_pais = new String[2];
		// String strIdiomaPais = "";
		if (traductor == null) {
			String cookieName = "cookie_idioma_pais";
			Cookie cookies[] = request.getCookies();
			Cookie myCookie = null;
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(cookieName)) {
						myCookie = cookies[i];
						break;
					}
				}
			}

			if (myCookie == null) {
				traductor = new Traductor();
				idioma_pais[0] = traductor.getIdioma();
				idioma_pais[1] = traductor.getPais();
				myCookie = new Cookie(cookieName, idioma_pais[0] + "_"
						+ idioma_pais[1]);
				myCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(myCookie);
			} else {
				idioma_pais = myCookie.getValue().split("_");
				traductor = new Traductor(idioma_pais[0], idioma_pais[1]);
			}

		} else {
			idioma_pais[0] = traductor.getIdioma();
			idioma_pais[1] = traductor.getPais();
		}

		// strIdiomaPais = idioma_pais[0] + "_" + idioma_pais[1];

		// String[] idiomasDisponibles = traductor
		// .getIdiomasPaisesDisponibles();
		return traductor;
	}*/

}
