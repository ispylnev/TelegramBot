package Utils;

import java.util.Properties;

public class Tets extends Aes256 {

    public Tets(){
        super();
    }
    private String s;


    public static void main(String[] args) {
        Tets tets = new Tets();
        String s = "CxN9+vPDLE2NvdWWl013qjBOjJkcY0fiYUUhCe5Wnwo2KT2YI+91iP+oQYg/40l6";
        Properties properties = new Properties();
//        System.out.println(tets.encrypt(s));
        System.out.println(tets.decrypt( FileUtils.getToken(properties)));




    }
}
