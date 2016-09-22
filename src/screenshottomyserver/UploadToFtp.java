/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenshottomyserver;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.StrictMath.log;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Zolotas
 */
public class UploadToFtp {

    public static void upload(File fileLocation) {
        long BUFFER_SIZE = fileLocation.length();
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String host = "YourHost:YourPort";
        String user = "YourUserName";
        String pass = "YourPassword";
        String filePath = fileLocation.getAbsolutePath();
        String uploadPath = "/srv/www/htdocs/thePathToBeSavedAt";

        ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
        System.out.println("Upload URL: " + ftpUrl);

        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(filePath);

            byte[] buffer = new byte[(int) BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("File uploaded");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String send(String fileName) {
        String SFTPHOST = "YourHost";
        int SFTPPORT = 22;
        String SFTPUSER = "YourUsername";
        String SFTPPASS = "YourPassword";
        String SFTPWORKINGDIR = "/srv/www/htdocs/thePathToBeSavedAt";
        String finalLocation = "http://nikolopoulosbasil.com/files/screenshots/";

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        System.out.println("preparing the host information for sftp.");
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("Host connected.");
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("sftp channel opened and connected.");
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(SFTPWORKINGDIR);
            File f = new File(fileName);
            channelSftp.put(new FileInputStream(f), f.getName());
            finalLocation = "http://servername/folders/toBe/SavedAt/" + f.getName();
            System.out.println("File transfered successfully to host.");
        } catch (Exception ex) {
            System.out.println("Exception found while tranfer the response.");
        } finally {

            channelSftp.exit();
            System.out.println("sftp Channel exited.");
            channel.disconnect();
            System.out.println("Channel disconnected.");
            session.disconnect();
            System.out.println("Host Session disconnected.");
        }
        System.out.println("Image at "+finalLocation);
        return finalLocation;
    }
}
