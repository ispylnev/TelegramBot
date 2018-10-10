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
       String encrypt = aes256.encrypt(2);
        Assert.assertEquals("GnWhAoLjGJ9La2gHQLLnHQ==",encrypt);
    }

    @Test
    public void testDecrypt(){
//        String decrypt = aes256.decrypt("00FDa3UofFn0QL/RBijdFg==");
       Assert.assertEquals("test",aes256.decrypt("00FDa3UofFn0QL/RBijdFg=="));
    }


}
