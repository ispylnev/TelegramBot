package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static Controls.Constants.*;

public class FileUtils {
//todo разобраться с закрытием файла
    private static FileInputStream fileInputStream = null;

    public static FileInputStream openFile(String file) {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;



    }

    public static String getUrlProxy(Properties properties) {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String)properties.get(PROXY);


    }

    public static String  getProxyPassword(Properties properties)  {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) properties.get(PROXYPASSWORD);

    }

    public static String getUserProxy(Properties properties) {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String)properties.get(PROXYPUSER);

    }

    public static String getBotName(Properties properties){
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  (String)properties.get(BOTNAME);
    }

    public static String getToken(Properties properties){
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return (String) properties.get(TOKEN);
    }

    public static int geProxyPort(Properties properties) {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String port =  (String) properties.get(PORT);
        return Integer.valueOf(port);

    }

}
