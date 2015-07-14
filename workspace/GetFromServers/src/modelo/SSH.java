/**
 * 
 */
package modelo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author adalberto
 * 
 */
public class SSH {

	private String nombreArchivoOriginal = null;
	private String nombreArchivoFinal = null;

	/**
	 * 
	 */
	public SSH() {
		// TODO Auto-generated constructor stub
	}

	public boolean downloadFile(Servidor servidor, ArchivoBuscar archivo) throws JSchException, SftpException, IOException {
		// String SFTPHOST = "10.20.30.40";
		String SFTPHOST = servidor.getNombre();
		// int SFTPPORT = 22;
		int SFTPPORT = servidor.getPuerto();
		// String SFTPUSER = "username";
		String SFTPUSER = servidor.getUsuario();
		// String SFTPPASS = "password";
		String SFTPPASS = servidor.getClave();
		// String SFTPWORKINGDIR = "/export/home/kodehelp/";
		String SFTPWORKINGDIR = archivo.getRutaOriginal();

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		//try {
			JSch jsch = new JSch();
			// session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
			//session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			session = null;
			if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPPORT > 0 && SFTPUSER != null && SFTPUSER.trim() != "" && SFTPPASS != null && SFTPPASS.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
				session.setPassword(SFTPPASS);
			}else if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPPORT > 0 && SFTPUSER != null && SFTPUSER.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			}else if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPUSER.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST);
			}else if(SFTPHOST != null && SFTPHOST.trim() != ""){
				session = jsch.getSession(SFTPHOST);
			}
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(SFTPWORKINGDIR);
			byte[] buffer = new byte[1024];
			// BufferedInputStream bis = new
			// BufferedInputStream(channelSftp.get("Test.java"));
			
			nombreArchivoOriginal = archivo.getNombreOriginal();
			
			while(nombreArchivoOriginal.indexOf("{fecha(") >= 0){
				String campo = nombreArchivoOriginal.substring(nombreArchivoOriginal.indexOf("{fecha("), nombreArchivoOriginal.indexOf(")}") + 2);
				String formato = campo.substring(campo.indexOf('(') + 1, campo.indexOf(')'));
				String fecha = new SimpleDateFormat(formato).format(new Date());
				nombreArchivoOriginal = nombreArchivoOriginal.replace(campo, fecha);
			}
			
			
			BufferedInputStream bis = new BufferedInputStream(
					//channelSftp.get("file1.txt"));
					channelSftp.get(nombreArchivoOriginal));
			// File newFile = new File("C:/Test.java");
			
			
			nombreArchivoFinal = (archivo.getNombreFinal() == null || archivo.getNombreFinal().trim() == "" ? archivo.getNombreOriginal() : archivo.getNombreFinal());
			
			while(nombreArchivoFinal.indexOf("{fecha(") >= 0){
				String campo = nombreArchivoFinal.substring(nombreArchivoFinal.indexOf("{fecha("), nombreArchivoFinal.indexOf(")}") + 2);
				String formato = campo.substring(campo.indexOf('(') + 1, campo.indexOf(')'));
				String fecha = new SimpleDateFormat(formato).format(new Date());
				nombreArchivoFinal = nombreArchivoFinal.replace(campo, fecha);
			}
			
			
			File newFile = new File("files" + File.separator + nombreArchivoFinal);
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			// System.out.println("Getting: " + theLine);
			while ((readCount = bis.read(buffer)) > 0) {
				System.out.println("Writing: ");
				bos.write(buffer, 0, readCount);
			}
			bis.close();
			bos.close();

			return true;
		/*} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}*/
	}
	
	public boolean uploadFile(Servidor servidor, ArchivoBuscar archivo) throws JSchException, SftpException, IOException {
		boolean exito = true;
		// String SFTPHOST = "10.20.30.40";
		String SFTPHOST = servidor.getNombre();
		// int SFTPPORT = 22;
		int SFTPPORT = servidor.getPuerto();
		// String SFTPUSER = "username";
		String SFTPUSER = servidor.getUsuario();
		// String SFTPPASS = "password";
		String SFTPPASS = servidor.getClave();
		// String SFTPWORKINGDIR = "/export/home/kodehelp/";
		String SFTPWORKINGDIR = archivo.getRutaFinal() == null || archivo.getRutaFinal().trim() == "" ? archivo.getRutaOriginal() : archivo.getRutaFinal();
		//String FILETOTRANSFER = "/home/kodehelp/temp/";
    	String FILETOTRANSFER = "files" + File.separator + nombreArchivoFinal;
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        File f =null;
        FileInputStream fis=null;
        try {
            JSch jsch = new JSch();
            //session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            //session.setPassword(SFTPPASS);
            session = null;
			if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPPORT > 0 && SFTPUSER != null && SFTPUSER.trim() != "" && SFTPPASS != null && SFTPPASS.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
				session.setPassword(SFTPPASS);
			}else if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPPORT > 0 && SFTPUSER != null && SFTPUSER.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
			}else if(SFTPHOST != null && SFTPHOST.trim() != "" && SFTPUSER.trim() != ""){
				session = jsch.getSession(SFTPUSER, SFTPHOST);
			}else if(SFTPHOST != null && SFTPHOST.trim() != ""){
				session = jsch.getSession(SFTPHOST);
			}
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);
            f = new File(FILETOTRANSFER);
            fis = new FileInputStream(f);
            channelSftp.put(fis, f.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
            exito = false;
            throw ex;
        }finally{
            if(fis!=null){
                //try {
					fis.close();
				/*} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw e;
				}*/
            }
        }
        //System.exit(0);
		return exito;
	}

	/**
	 * @return the nombreArchivoOriginal
	 */
	public String getNombreArchivoOriginal() {
		return nombreArchivoOriginal;
	}

	/**
	 * @param nombreArchivoOriginal the nombreArchivoOriginal to set
	 */
	public void setNombreArchivoOriginal(String nombreArchivoOriginal) {
		this.nombreArchivoOriginal = nombreArchivoOriginal;
	}

	/**
	 * @return the nombreArchivoFinal
	 */
	public String getNombreArchivoFinal() {
		return nombreArchivoFinal;
	}

	/**
	 * @param nombreArchivoFinal the nombreArchivoFinal to set
	 */
	public void setNombreArchivoFinal(String nombreArchivoFinal) {
		this.nombreArchivoFinal = nombreArchivoFinal;
	}
	
	

}
