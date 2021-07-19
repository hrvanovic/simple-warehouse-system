package com.blixmark;

import com.blixmark.utilites.Localization;
import com.blixmark.utilites.UserDialog;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    public final static String separator = System.getProperty("file.separator");
    private final static String directoryPath = System.getProperty("user.home") + separator + "BlixmarkMagacin" + separator;
    public final static String appName = Localization.get("app-name").toString();

    public static String getDirectoryPath() {
        if(System.getProperty("os.name").equals("Linux"))
            return directoryPath;
        else
            return "C:\\" + "BlixmarkMagacin" + separator;
    }

    public static String getAppName() {
        return appName;
    }

    public static String get(String obj) {
        Properties properties;
        FileInputStream fileInputStream;
        try {
            properties = new Properties();
            fileInputStream = new FileInputStream(getDirectoryPath() + "default.properties");
            properties.load(fileInputStream);

            return new String(properties.getProperty(obj));
        } catch (Exception exception) {
            UserDialog.showError("Doslo je do greske! Developer mail: hrvanovic.dev@gmail.com");
            System.exit(0);
        }

        return null;
    }
}
