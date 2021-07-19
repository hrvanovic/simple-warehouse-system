package com.blixmark.utilites;

import com.blixmark.Config;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ErrorScanner {
    /**
     * Konstruktor ErrorScanner.
     */
    public ErrorScanner() throws Exception {
        this.checkDir();
        this.checkDerbyConn();
        // this.checkDatabase();
    }

    /**
     * Metod za provjeru foldera.
     * Ukoliko ne postoji folderi, program ce ih kreirati.
     */
    private void checkDir() {
        try {
            File file = new File(Config.getDirectoryPath());
            if (!(file.exists() && file.isDirectory())) {
                if (!file.mkdir()) {
                    onError();
                }
            }
        } catch (Exception e) {
            new SystemLog().newLog("There was error with the directory!");
            onError();
        }
    }

    /**
     * Metod za provjeru database fajla.
     */
    private void checkDatabase() {
        File file = new File(Config.getDirectoryPath());
        if ((file.exists() && file.isFile())) {
            if (!file.canWrite()) {
                new SystemLog().newLog("You don't have a permission for directory!");
                onError();
            }
        } else
            this.updateDatabaseFile();
    }

    /**
     * Metod za kreiranje .db fajla.
     */
    private void updateDatabaseFile() {
        try {
            Files.copy(new File("com/blixmark/resources/db.example").toPath(), new File(Config.getDirectoryPath()).toPath());
        } catch (Exception exception) {
            new SystemLog().newLog(exception);
            onError();
        }
    }

    /**
     * Metod koji se poziva ukoliko dode do greske.
     * Ovaj metod prekida program.
     */
    private void onError() {
        JOptionPane.showMessageDialog(null, "Doslo je do greske!", "Greska", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    private void checkDerbyConn() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        Connection connection = DriverManager.getConnection(Config.get("sqlhost"), Config.get("sqluser"), Config.get("sqlpass"));
    }
}
