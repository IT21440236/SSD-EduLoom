package com.learningloom.learnerservice.util;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class EncryptionUtil {

    private static  final String ALGORITHM = "AES";
    private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
            'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

    public static  String encrypt(String valueToEnc) throws Exception {
        Key key = generateKey();
        Cipher c =  Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue =  Base64.getEncoder().encodeToString(encValue);
        return encryptedValue;
    }


    public static String decrypt(String encryptedValue) throws Exception {
        Key key =  generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedValue);
        byte[] decValue =  c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return  decryptedValue;
    }


    private static Key generateKey() throws Exception {
        Key key =  new SecretKeySpec(keyValue, ALGORITHM);
        return  key;
    }
}
