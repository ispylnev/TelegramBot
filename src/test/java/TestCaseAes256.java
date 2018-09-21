import Utils.Aes256;
import Utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class TestCaseAes256 {

    Aes256 aes256 = new Aes256("Ivan123");
    Properties properties  = new Properties();

    @Test
    public void  testEncrypt (){
//        String s  = aes256.decrypt(2);
        Integer proxyPort = Integer.valueOf(aes256.decrypt(FileUtils.geProxyPort(properties)));
       String encrypt = aes256.encrypt(proxyPort);
        System.out.println(proxyPort);
//        Assert.assertEquals( "Rt8wX3Mc3U/5bW61glRP7g==",encrypt);
    }

    @Test
    public void testDecrypt(){
       Assert.assertEquals("test",aes256.decrypt("B+vHJG+63vPVlA6c7ZukkA=="));
    }


}
