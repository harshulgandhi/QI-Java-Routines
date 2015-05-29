import java.io.*;
import org.apache.commons.codec.binary.*;
import java.security.*;
import java.security.spec.*;

/*
 * openssl pkcs8 -topk8 -inform PEM -outform DER -passin pass:invent2006 -in "C:\Masters\Internship\QI\carpark_static_import\Input\QICERT.pem" -out "C:\Masters\Internship\QI\carpark_static_import\Input\QICERT.der"-nocrypt
 */

class MainClassSignature{
	public static void main(String args[]){
		try {
			MessageDigest cript = MessageDigest.getInstance("SHA-1");
			cript.reset();
            cript.update(args[0].getBytes("UTF-8"));
            byte[] b_digest = cript.digest();

			Signature sign = Signature.getInstance("SHA1withRSA");
			PrivateKey pk = get("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.der");
			sign.initSign(pk);
			sign.update(b_digest);
			byte[] b1 = sign.sign();

			String signedString = new String(b1);
			String s2 = new String(Base64.encodeBase64(b1),"UTF-8");

			System.out.println("_______________PrivateKey_________________________");
			System.out.println(pk.toString() +"|");
			System.out.println("_______________Digest_________________________");
			String temp = new String(b_digest);
			System.out.println(temp + "|");
			System.out.println("_______________Signature_________________________");
			System.out.println(signedString +"|");
			System.out.println("_______________Encoded_________________________");
			System.out.println(s2 +"|");

			}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			}
		}
	//"QIHDB20150525162305"
	public static PrivateKey get(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int)f.length()];
		dis.readFully(keyBytes);
		dis.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
		}
	}