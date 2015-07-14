/**
 *
 */
package vista;
 
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
 
/**
 * @author kodehelp
 *
 */
public class SFTPinJavaUpload {
 
    /**
*
*/
    public SFTPinJavaUpload() {
        // TODO Auto-generated constructor stub
    }
 
    /**
     * @param args
     */
    public static void main(String[] args) {
        //String SFTPHOST = "10.20.30.40";
    	String SFTPHOST = "10.0.25.102";
        //int SFTPPORT = 22;
    	int SFTPPORT = 2222;
        //String SFTPUSER = "username";
    	String SFTPUSER = "adalberto";
        //String SFTPPASS = "password";
    	String SFTPPASS = "linux.2816";
        //String SFTPWORKINGDIR = "/export/home/kodehelp/";
    	String SFTPWORKINGDIR = "/home/adalberto/Documents/folder2/";
        //String FILETOTRANSFER = "/home/kodehelp/temp/";
    	String FILETOTRANSFER = "files/fileDownloaded.txt";
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        File f =null;
        FileInputStream fis=null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
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
        }finally{
            if(fis!=null){
                try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        System.exit(0);
    }
 
}