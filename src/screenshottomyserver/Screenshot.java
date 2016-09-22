/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenshottomyserver;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Zolotas
 */
public class Screenshot {

    public static File takeShot(boolean jpg) throws
            AWTException, IOException {
        // capture the whole screen
        BufferedImage screencapture = new Robot().createScreenCapture(
                new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

        // Save as JPEG
        String filename;
        File file;
        if (jpg) {
            filename = screencapture.hashCode() + ".jpg";
            file = new File(filename);
            ImageIO.write(screencapture, "jpg", file);
        } else {
            filename = screencapture.hashCode() + ".png";
            file = new File(filename);
            ImageIO.write(screencapture, "png", file);//png
        }

        return file;
    }

    private static String md5sum(String inputFile) throws NoSuchAlgorithmException, IOException {
       

        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(inputFile);
        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());
        return sb.toString();
    }
}
