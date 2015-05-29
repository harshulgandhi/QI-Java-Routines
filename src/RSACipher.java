import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.sun.org.apache.xml.internal.security.utils.Base64;


class RSACipher{
private static final String PRIVATE_PATH = "C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem";
private static final String PUBLIC_PATH = null;

public static void main(String[] args) {


    //Base64 base64 = new Base64();

    String TextStream = "this is the input text";
    byte[] Cipher;
    System.out.println("input:\n" + TextStream);
    Cipher = encrypt(TextStream);
   // System.out.println("cipher:\n" + base64.encodeAsString(Cipher));
    System.out.println("decrypt:\n" + decrypt(Cipher));
}

private static byte[] encrypt(String Buffer) {
    try {

        Cipher rsa;
        rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.ENCRYPT_MODE, getPrivateKey(PRIVATE_PATH));
        return rsa.doFinal(Buffer.getBytes());
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


private static String decrypt(byte[] buffer) {
    try {
        Cipher rsa;
        rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.DECRYPT_MODE, getPrivateKey(PUBLIC_PATH));
        byte[] utf8 = rsa.doFinal(buffer);
        return new String(utf8, "UTF8");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public static PrivateKey getPrivateKey(String filename) throws Exception {
    File f = new File(filename);
    FileInputStream fis = new FileInputStream(f);
    DataInputStream dis = new DataInputStream(fis);
    byte[] keyBytes = new byte[(int) f.length()];
    dis.readFully(keyBytes);
    dis.close();

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    System.out.println("kf.generatePrivate(spec) : "+kf.generatePrivate(spec));
    return kf.generatePrivate(spec);
}

public static PublicKey getPublicKey(String filename) throws Exception {
    File f = new File(filename);
    FileInputStream fis = new FileInputStream(f);
    DataInputStream dis = new DataInputStream(fis);
    byte[] keyBytes = new byte[(int) f.length()];
    dis.readFully(keyBytes);
    dis.close();

    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(spec);
}
}