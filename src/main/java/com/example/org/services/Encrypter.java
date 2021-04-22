package com.example.org.services;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

public class Encrypter {
   private static final String secretKey = "encriptacionMamalona";

    public static String encode(String cadena) {
        String encriptacion = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] BitesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(BitesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            byte[] PlainTextBytes = cadena.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(PlainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            encriptacion = new String(base64Bytes);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encriptacion;
    }

    public static String deencode ( String cadenaencriptada) {
        String desencriptacion = "";
        try {
            byte[] mensaje = Base64.decodeBase64(cadenaencriptada.getBytes("utf-8"));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] BitesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(BitesKey, "DESede");
            Cipher decifrado = Cipher.getInstance("DESede");
            decifrado.init(Cipher.DECRYPT_MODE, key);
            byte[] textoPlano = decifrado.doFinal(mensaje);
            desencriptacion = new String(textoPlano, "utf-8");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return desencriptacion;
    }
}
