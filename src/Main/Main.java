/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
 
/** 
 *
 * @author HeriSeven
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        if (new File("SteamApps").exists()) {
            File[] f = new File("SteamApps").listFiles();

            for (File ff : f) {
                if (ff.getName().endsWith(".acf") && ff.getName().startsWith("appmanifest")) {
                    String everything;
                    System.out.println("reading File: " + ff.getName());
                    BufferedReader br = new BufferedReader(new FileReader(ff.getPath()));
                    try {
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        while (line != null) {
                            if (line.contains("StateFlags")) {
                                sb.append("	\"StateFlags\"		\"2\"");
                            } else if (line.contains("AllowOtherDownloadsWhileRunning")) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                sb.append("	\"FullValidateOnNextUpdate\"		\"1\"");
                            } else {
                                sb.append(line);
                            }
                            sb.append(System.lineSeparator());
                            line = br.readLine();
                        }
                        everything = sb.toString();
                    } finally {
                        br.close();
                    }

                    //System.out.println(everything);
                    if (ff.delete()) {
                        System.out.println("Writing File: " + ff.getName());
                    } else {
                        System.out.println("File couldn't be overwritten!");
                    }
                    if (!ff.exists()) {
                        ff.createNewFile();
                    }

                    FileWriter fw = new FileWriter(ff.getAbsolutePath());
                    try (BufferedWriter bw = new BufferedWriter(fw)) {
                        bw.write(everything);
                    }
                }

            }
        } else {
            System.out.println("Please put me in the Steam Root Folder!");
        }
    }
}
