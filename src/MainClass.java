import java.io.*;
//import org.apache.commons.codec.binary.*;
import java.security.*;
import java.security.spec.*;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/*
 * openssl pkcs8 -topk8 -inform PEM -outform DER -passin pass:invent2006 -in "C:\Masters\Internship\QI\carpark_static_import\Input\QICERT.pem" -out "C:\Masters\Internship\QI\carpark_static_import\Input\QICERT.der"-nocrypt
 */

class MainClass{
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
			String s2 = new String(Base64.encode(b1));

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





/*import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.JCEECPrivateKey;
import org.bouncycastle.jce.provider.X509CertParser;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.x509.util.StreamParsingException;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

class MainClass{
		
		
	public static void main(String args[]){
		
		try{
		PrivateKey pk = readPrivateKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT"
				+ ".pem","Invent2006;");
		//PublicKey pubk = readPublicKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT_key.pem","invent2006");
		byte[] data = "QIHDB20150525162305".getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(pk);
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));

//        sig.initVerify(pubk);
//        sig.update(data);
//        System.out.println(sig.verify(signatureBytes));

		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("deprecation")
	protected static  PrivateKey readPrivateKey(String privateKeyPath, String keyPassword) throws IOException, OperatorCreationException, StreamParsingException {
		
		//File as input stream
		File file = new File(privateKeyPath);
		FileInputStream fis = new FileInputStream(file);
		
		FileReader fileReader = new FileReader(privateKeyPath);
		
	    X509CertParser xReader = new X509CertParser();
	    xReader.engineInit(fis);
	    System.out.println("xReader.engineRead()  : "+ xReader.engineRead());
	    
	    
	    System.out.println("xReader : "+xReader.toString());
	    //xReader.engineInit(arg0);
	    PEMParser keyReader = new PEMParser(fileReader);
	    System.out.println("keyReader.readPemObject()  : "+keyReader.toString());
	    
	    
	    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(keyPassword.toCharArray());
	  
	    
	    
	    Object keyPair = keyReader.readObject();
	    System.out.println("keyPair : "+keyPair.toString());
	    System.out.println("keyReader.getClass() : "+keyReader.getClass());
	    PrivateKeyInfo keyInfo;
	    PrivateKey kp = null;
	    //System.out.println("keyPair.getClass() : "+keyPair.getClass());
	    
	    
	    if (keyPair instanceof PEMEncryptedKeyPair ) {
	    	System.out.println("Correct instance found");
	    	//System.out.println(((PrivateKeyInfo) keyPair).getPrivateKey());
	    	
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPair).decryptKeyPair(decryptionProv);
	        keyInfo = decryptedKeyPair.getPrivateKeyInfo();
	        
	        
	    } else {
	    	//kp = converter.getKeyPair((PEMKeyPair) keyPair);
	    	//kp = converter.getPrivateKey((PrivateKeyInfo) keyPair);
	    	keyInfo = (PrivateKeyInfo) converter.getPrivateKey((PrivateKeyInfo) keyPair);
	        //keyInfo = ((PEMKeyPair) keyPair).getPrivateKeyInfo();
	    	System.out.println("KeyInfo : "+kp.toString());
	    	return kp;
	    }
	    keyReader.close();
	    
	    return converter.getPrivateKey((PrivateKeyInfo) keyInfo);
	    
	}
	
	public  PrivateKey getPemPrivateKey(String filename, String algorithm) throws Exception {
	      File f = new File(filename);
	      FileInputStream fis = new FileInputStream(f);
	      DataInputStream dis = new DataInputStream(fis);
	      byte[] keyBytes = new byte[(int) f.length()];
	      dis.readFully(keyBytes);
	      dis.close();

	      String temp = new String(keyBytes);
	      System.out.println("TEMP\n"+temp);
	      String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----", "");
	      privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
	      System.out.println("Private key\n"+privKeyPEM);

	      Base64 b64 = new Base64();
	      byte [] decoded = b64.decode(privKeyPEM);
	      System.out.println("decoded : "+decoded);
	      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
	      KeyFactory kf = KeyFactory.getInstance(algorithm);
	      return kf.generatePrivate(spec);
	      }
	
	
	private static PublicKey readPublicKey(String privateKeyPath, String keyPassword) throws IOException {

	    FileReader fileReader = new FileReader(privateKeyPath);
	    PEMParser keyReader = new PEMParser(fileReader);

	    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(keyPassword.toCharArray());

	    Object keyPair = keyReader.readObject();
	    SubjectPublicKeyInfo keyInfo;

	    if (keyPair instanceof PEMEncryptedKeyPair) {
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPair).decryptKeyPair(decryptionProv);
	        keyInfo = decryptedKeyPair.getPublicKeyInfo();
	    } else {
	        keyInfo = ((PEMKeyPair) keyPair).getPublicKeyInfo();
	    }
	    keyReader.close();
	    return converter.getPublicKey(keyInfo);
	}
}*/

