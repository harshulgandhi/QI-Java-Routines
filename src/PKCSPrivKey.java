import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;


public class PKCSPrivKey {
public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
	RandomAccessFile raf = new RandomAccessFile("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT_key.pem", "r");
	byte[] buf = new byte[(int)raf.length()];
	raf.readFully(buf);
	raf.close();
	PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(buf);
	KeyFactory kf = KeyFactory.getInstance("RSA");
	PrivateKey privKey = kf.generatePrivate(kspec);
}
}
