package Utils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;

/*
Класс для шифровки и дешифровки входящий и изходящих данных

 */
public class Aes256 {

   private byte[] seed;

   public Aes256(){
       this.seed = System.getenv("secretKey").getBytes();
   }

    public Aes256(String password) {
        this.seed = password.getBytes();
    }

    public Aes256(byte[] password) {
        this.seed = password;
    }

    public String encrypt(String cleartext) {
        try {
            byte[] rawKey = getRawKey(seed);
            byte[] result = new byte[0];
            result = encrypt(rawKey, cleartext.getBytes());
            return toHex(result);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    public String decrypt(String encrypted) {
        byte[] rawKey = new byte[0];
        try {
            rawKey = getRawKey(seed);
            byte[] enc = toByte(encrypted);
            byte[] result = new byte[0];
            result = decrypt(rawKey, enc);
            return new String(result);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return null;
    }

    /*
    Этот метод генерирует дополнительное шифрование секретного ключа
     */
    private static byte[] getRawKey(byte[] password) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed( password);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(byte[] buffer) {
        return DatatypeConverter.printBase64Binary(buffer);
    }

    public static byte[] toByte(String hex) {
        return DatatypeConverter.parseBase64Binary(hex);
    }
}


    // Для отладки

//        public static void main(String args[]) {
//            Crypt aes = new Crypt("Bar12345Bar12345");
//            String crypted = aes.encrypt("C9b3DbO");
//            System.out.println(crypted);
//            System.out.println(aes.decrypt(crypted));
//        }