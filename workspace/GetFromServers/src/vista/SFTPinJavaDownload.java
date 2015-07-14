/**
 *
 */
package vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * @author kodehelp
 * 
 */
public class SFTPinJavaDownload {

	/**
*
*/
	public SFTPinJavaDownload() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// String SFTPHOST = "10.20.30.40";
		String SFTPHOST = "10.0.25.102";
		// int SFTPPORT = 22;
		int SFTPPORT = 2222;
		// String SFTPUSER = "username";
		String SFTPUSER = "adalberto";
		// String SFTPPASS = "password";
		String SFTPPASS = "linux.2816";
		// String SFTPWORKINGDIR = "/export/home/kodehelp/";
		String SFTPWORKINGDIR = "/home/adalberto/Documents/folder1/";

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			// session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
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
			byte[] buffer = new byte[1024];
			// BufferedInputStream bis = new
			// BufferedInputStream(channelSftp.get("Test.java"));
			BufferedInputStream bis = new BufferedInputStream(
					channelSftp.get("file1.txt"));
			// File newFile = new File("C:/Test.java");
			File newFile = new File("files/fileDownloaded.txt");
			OutputStream os = new FileOutputStream(newFile);
			BufferedOutputStream bos = new BufferedOutputStream(os);
			int readCount;
			// System.out.println("Getting: " + theLine);
			System.out.println("before while");
			while ((readCount = bis.read(buffer)) > 0) {
				System.out.println("Writing: ");
				bos.write(buffer, 0, readCount);
			}
			System.out.println("after while");
			bis.close();
			System.out.println("bis is closed");
			bos.close();
			System.out.println("bos is closed");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("end main");
		System.exit(0);
	}

}