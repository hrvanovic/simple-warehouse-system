package com.blixmark.utilites;

import com.blixmark.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SystemLog {
    /**
     * Metod za dodavanje novog loga tipa Exception.
     *
     * @param exception Exception
     */
    public static void newLog(Exception exception) {
        exception.printStackTrace();
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            Writer writer = null;
            try {
                File file = new File(Config.getDirectoryPath() + "logs.txt");
                if ((!file.exists()) && (!file.isFile())) {
                    if (file.createNewFile()) {
                        fos = new FileOutputStream(file);
                        osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                        writer = new BufferedWriter(osw);
                        writer.write("Greska!" + exception.getMessage() + "\n");

                        fos.close();
                        osw.close();
                        writer.close();
                    }
                } else {
                    fos = new FileOutputStream(file);
                    osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                    writer = new BufferedWriter(osw);

                    writer.write("Greska!" + exception.getMessage() + "\n");
                }
            } catch (Exception exception1) {
                  exception1.printStackTrace();
            } finally {
               try {
                   fos.close();
                   osw.close();
                   writer.close();
               } catch (Exception exception1) {

               }
            }
    }

    /**
     * Metod za kreiranje novog Loga.
     *
     * @param message Tekst koji ce se zapisati.
     */
    public static void newLog(String message) {
        System.out.println(message);
    }
}
