/**
 * 
 */
package modelo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Exception;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.ibm.as400.access.IFSFileOutputStream;
import com.ibm.as400.access.IFSFileReader;
import com.ibm.as400.access.IFSFileWriter;
import com.ibm.as400.access.ObjectAlreadyExistsException;
import com.ibm.as400.jtopenstubs.javabeans.PropertyVetoException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/**
 * @author adalberto
 *
 */
public class AS400Connection {
	
	private String nombreArchivoOriginal = null;
	private String nombreArchivoFinal = null;

	/**
	 * 
	 */
	public AS400Connection() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean downloadFile(Servidor servidor, ArchivoBuscar archivo) throws JSchException, SftpException, IOException, AS400Exception, AS400SecurityException, InterruptedException, PropertyVetoException {
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
		
		
		

        /*SequentialFile file = 
           new SequentialFile(as400,                  
                         "/QSYS.LIB/SYSIBM.LIB/SYSDUMMY1.FILE");
        file.setRecordFormat(); 
        file.open(AS400File.READ_ONLY, 1, 
                  AS400File.COMMIT_LOCK_LEVEL_NONE );
        Record record = file.read(1);
        Object field = record.getField(0);
        System.out.println("Field is "+field); 
        file.close();*/
		

		/*Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;*/

		//try {
			AS400 as400 = new AS400(SFTPHOST,SFTPUSER,SFTPPASS);
			/*JSch jsch = new JSch();
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
			byte[] buffer = new byte[1024];*/
			// BufferedInputStream bis = new
			// BufferedInputStream(channelSftp.get("Test.java"));
			
			nombreArchivoOriginal = archivo.getNombreOriginal();
			
			while(nombreArchivoOriginal.indexOf("{fecha(") >= 0){
				String campo = nombreArchivoOriginal.substring(nombreArchivoOriginal.indexOf("{fecha("), nombreArchivoOriginal.indexOf(")}") + 2);
				String formato = campo.substring(campo.indexOf('(') + 1, campo.indexOf(')'));
				String fecha = new SimpleDateFormat(formato).format(new Date());
				nombreArchivoOriginal = nombreArchivoOriginal.replace(campo, fecha);
			}
			
			
			/*BufferedInputStream bis = new BufferedInputStream(
					//channelSftp.get("file1.txt"));
					channelSftp.get(nombreArchivoOriginal));
			// File newFile = new File("C:/Test.java");*/
			
			String dataFileOriginal = readFile2(as400, SFTPWORKINGDIR + nombreArchivoOriginal);
			
			
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
			
			byte[] b = dataFileOriginal.getBytes();
			//byte[] b = dataFileOriginal.getBytes(Charset.forName("UTF-8"));
			bos.write(b);
			/*while ((readCount = bis.read(buffer)) > 0) {
				System.out.println("Writing: ");
				bos.write(buffer, 0, readCount);
			}*/
			//bis.close();
			bos.close();

			return true;
		/*} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}*/
	}
	
	
	public boolean uploadFile(Servidor servidor, ArchivoBuscar archivo) throws Exception {
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
        /*Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;*/
        File f =null;
        FileInputStream fis=null;
        try {
        	AS400 as400 = new AS400(SFTPHOST,SFTPUSER,SFTPPASS);
            /*JSch jsch = new JSch();
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
			}*/
            /*java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);*/
            f = new File(FILETOTRANSFER);
            fis = new FileInputStream(f);
            //channelSftp.put(fis, f.getName());
            
            // read till the end of the file
            int i;
            String data = "";
            char c;
            while((i=fis.read())!=-1)
            {
               // converts integer to character
               c=(char)i;
               
               // prints character
               //System.out.print(c);
               data += c;
            }
            
            writeFile2(as400, SFTPWORKINGDIR + f.getName(), data);
            
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
	   Reads an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @return The characters read.
	   @throws IOException
	   @throws AS400SecurityException
	   **/
	   public byte[] readFile(AS400 system, String path) throws AS400SecurityException,
	 IOException{
	      IFSFile file = new IFSFile(system, path);
	      /** creates a file input stream */
	      IFSFileInputStream fis = new IFSFileInputStream (file,
	      IFSFileInputStream.SHARE_READERS);
	      byte[] data = new byte[fis.available()];
	      fis.read(data);
	      fis.close();
	      return data;
	 }
	   
	   
	   /**
	   Reads an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @return The characters read.
	   @throws IOException
	   @throws AS400SecurityException
	 **/
	   public String readFile2(AS400 system, String path) throws AS400SecurityException,
	 IOException{
	      IFSFile file = new IFSFile(system, path);
	      /** creates a file reader */
	      IFSFileReader fr = new IFSFileReader (file);
	      BufferedReader br = new BufferedReader(fr);
	      StringBuffer sb = new StringBuffer();
	      /** Reads a line of text */
	      String line = br.readLine();
	      while (line != null){
	           line = br.readLine();
	           sb.append(line);
	      }
	      fr.close();
	      return sb.toString();
	 }
	   
	   
	   /**
	   Writes an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @param bytes The bytes to be written.
	   @throws IOException
	   @throws AS400SecurityException
	 **/
	   public void writeFile(AS400 system, String path, byte[] bytes) throws
	 AS400SecurityException, IOException {
	      IFSFile file = new IFSFile(system, path);
	      /** creates a file output stream */
	      IFSFileOutputStream fos = new IFSFileOutputStream (file);
	      /** Writes bytes to file */
	      fos.write(bytes);
	      fos.close();
	 }
	   
	   
	   /**
	   Writes an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @param str The characters to be written.
	   @throws IOException
	   @throws AS400SecurityException
	 **/
	   public void writeFile2(AS400 system, String path, String str) throws IOException,
	 AS400SecurityException {
	      IFSFile file = new IFSFile(system, path);
	      /** creates a file writer */
	      IFSFileWriter fw = new IFSFileWriter (file);
	      /** Writes a string to file */
	      fw.write(str);
	      fw.close();
	 }
	   
	   
	   
	   /**
	   Creates a new directory
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @throws IOException
	 **/
	   public void createDir(AS400 system, String path) throws IOException{
	      IFSFile dir = new IFSFile (system, path);
	      if (!dir.exists()){
	         dir.mkdir();
	   }
	 }
	   
	   
	   
	   /**
	   Copy integrated file system file or directory
	   @param system The system that contains the file.
	   @param srcPath The source path.
	   @param destpath The destination path.
	   @throws ObjectAlreadyExistsException.
	   @throws AS400SecurityException
	   @throws AS400SecurityException
	 **/
	   public void copy(AS400 system, String srcPath, String destPath
	   ) throws IOException, AS400SecurityException, ObjectAlreadyExistsException{
	      IFSFile file = new IFSFile(system, srcPath);
	      file.copyTo(destPath);
	   }
	   
	   
	   /**
	   Writes an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @param chars The characters to be written.
	   @param CCSID The CCSID to convert the data to when writing to the file.
	   @throws IOException
	   @throws AS400SecurityException
	 **/
	   public void writeFile3(AS400 system, String path, char[] chars, int CCSID ) throws
	 AS400SecurityException, IOException  {
	      IFSFile file = new IFSFile(system, path);
	      /** creates a file writer */
	      IFSFileWriter fw = new IFSFileWriter (file, CCSID);
	      /** Writes a string to file */
	      fw.write(chars);
	      fw.close();
	 }
	   
	   
	   /**
	   Reads an integrated file system file.
	   @param system The system that contains the file.
	   @param path The absolute path name of the file.
	   @param The CCSID that the file data is currently in.
	   @throws IOException
	   @throws AS400SecurityException
	   @return The characters read.
	 **/
	   public String readFile3(AS400 system, String path, int CCSID) throws
	 AS400SecurityException, IOException {
	   IFSFile file = new IFSFile(system, path);
	   /** creates a file reader */
	   IFSFileReader fr = new IFSFileReader (file, CCSID);
	   BufferedReader br = new BufferedReader(fr);
	   StringBuffer sb = new StringBuffer();
	   /** Reads a line of text */
	   String line = br.readLine();
	   while (line != null){
	             line = br.readLine();
	             sb.append(line);
	   }
	   fr.close();
	   return sb.toString();
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
