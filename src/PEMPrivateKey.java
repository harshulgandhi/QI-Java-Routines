import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class PEMPrivateKey {

	public  PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
	      File f = new File(filename);
	      FileInputStream fis = new FileInputStream(f);
	      DataInputStream dis = new DataInputStream(fis);
	      byte[] keyBytes = new byte[(int) f.length()];
	      dis.readFully(keyBytes);
	      dis.close();

	      String temp = new String(keyBytes);
	      String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
	      privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
	      //System.out.println("Private key\n"+privKeyPEM);

	      Base64 b64 = new Base64();
	      byte [] decoded = b64.decode(privKeyPEM);

	      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
	      KeyFactory kf = KeyFactory.getInstance(algorithm);
	      return kf.generatePrivate(spec);
	      }
	
	public static void main(String[] args) throws Exception {
		PEMPrivateKey obj = new PEMPrivateKey();
		obj.getPemPrivateKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem", "X509");
	}
}
