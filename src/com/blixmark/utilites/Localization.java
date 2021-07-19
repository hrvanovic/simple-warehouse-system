package com.blixmark.utilites;

import com.blixmark.Config;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class Localization {
    final private static JSONParser jsonParser = new JSONParser();
    private static String url;
    private static FileReader reader = null;
    private static Object obj;
    private static JSONObject jsonObject;

    public static void create() {
        try {
            url = Config.getDirectoryPath() + Config.separator + "resources" + Config.separator + "languages" + Config.separator + "ba.json";
            reader = new FileReader(url);
            obj = jsonParser.parse(reader);
            jsonObject = (JSONObject) obj;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static String get(Object obj, String obj1) {
        if(reader == null)
            create();
        return ((JSONObject) jsonObject.get(obj.toString())).get(obj1).toString();
    }

    public static Object get(String obj) {
        if(reader == null)
            create();
        return jsonObject.get(obj);
    }
}
