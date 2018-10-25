package Utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import static Controls.Constants.*;

public  class FileUtils extends Aes256 {

    private static FileInputStream fileInputStream = null;

    public FileUtils() {
        super();
    }



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
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  (String)properties.get(PROXY);
    }

    public static String  getProxyPassword(Properties properties)  {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return (String) properties.get(TOKEN);
    }

    public static String geProxyPort(Properties properties) {
        openFile(PATHFILE);
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return (String) properties.get(PORT);


    }

}
