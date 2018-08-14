package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {

    private static FileInputStream fileInputStream = null;

    public static FileInputStream openFile(String file){
        try{
            fileInputStream = new FileInputStream(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        return fileInputStream;

    }

    public static String getUrlProxy(Properties properties) {
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String)properties.get("proxy");

    }

    public static String  getProxyPassword(Properties properties)  {
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return (String) properties.get("proxyPassword");

    }

    public static String getUserProxy(Properties properties) {
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  (String)properties.get("proxyUser");

    }

    public static String getBotName(Properties properties){
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  (String)properties.get("botName");
    }

    public static String getToken(Properties properties){
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return (String) properties.get("token");
    }

    public static int geProxyPort(Properties properties) {
        openFile("properties.dat");
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String port =  (String) properties.get("port");
        return Integer.valueOf(port);

    }

}
