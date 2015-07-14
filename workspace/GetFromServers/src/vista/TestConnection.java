/**
 * 
 */
package vista;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.IFSFileInputStream;
import com.ibm.as400.access.IFSFileOutputStream;
import com.ibm.as400.access.IFSFileReader;
import com.ibm.as400.access.IFSFileWriter;
import com.ibm.as400.access.ObjectAlreadyExistsException;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.SequentialFile;

/**
 * @author adalberto
 *
 */
public class TestConnection {
	static Properties props;
	/**
	 * 
	 */
	public TestConnection() {
		// TODO Auto-generated constructor stub
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
	 * @param args
	 */
	public static void main(String[] args) {
		
		 
		try {
		 
		 props = new Properties();
		 props.load(new FileInputStream("properties/mydb2.properties"));
		 
		 AS400 as400 = new AS400(props.getProperty("local_system").trim(),props.getProperty("userId").trim(),props.getProperty("password").trim());
		 
		 TestConnection connection = new TestConnection();
		 connection.readFile2(as400, "hola/chao/");
		 
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
		 
		 /*System.out.println(props.getProperty("local_system").trim());
		 System.out.println(props.getProperty("userId").trim());
		 System.out.println(props.getProperty("password").trim());*/
		 
		 /*String DRIVER = "com.ibm.as400.access.AS400JDBCDriver"; 
		 String URL = "jdbc:as400://" + props.getProperty("local_system").trim() + ";naming=system;errors=full";
		 Connection conn = null;
		 
		 //Connect to iSeries 
		 Class.forName(DRIVER); 
		 conn = DriverManager.getConnection(URL, props.getProperty("userId").trim(), props.getProperty("password").trim()); 
		 
		 conn.close();*/
		 
		}
		catch (Exception e) {
		 System.out.println(e);
		}
	}

}
