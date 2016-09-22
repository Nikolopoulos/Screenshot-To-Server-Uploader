/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenshottomyserver;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Zolotas
 */
public class ScreenshotToMyServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        new TakeSS();
    }

}
