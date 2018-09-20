import Utils.Aes256;
import org.junit.Assert;
import org.junit.Test;

public class TestCaseAes256 {

    Aes256 aes256 = new Aes256("Ivan123");

    @Test
    public void  testEncrypt (){
       String encrypt = aes256.encrypt(2);
        Assert.assertEquals( "B+vHJG+63vPVlA6c7ZukkA==",encrypt);
    }

    @Test
    public void testDecrypt(){
       Assert.assertEquals("test",aes256.decrypt("B+vHJG+63vPVlA6c7ZukkA=="));
    }


}
