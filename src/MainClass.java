import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;


import sun.misc.BASE64Encoder;

class MainClass{
	public static void main(String args[]){
		try{
		PrivateKey pk = readPrivateKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem","invent2006");
		PublicKey pubk = readPublicKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem","invent2006");
		byte[] data = "QIHDB20150520163306".getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(pk);
        sig.update(data);
        byte[] signatureBytes = sig.sign();
        System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));

        sig.initVerify(pubk);
        sig.update(data);
        System.out.println(sig.verify(signatureBytes));

		}catch(Exception e){
			
			e.printStackTrace();
		}

	}
	
	
	private static PrivateKey readPrivateKey(String privateKeyPath, String keyPassword) throws IOException {
		
	    FileReader fileReader = new FileReader(privateKeyPath);
	    
	    PEMParser keyReader = new PEMParser(fileReader);
	   
	    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(keyPassword.toCharArray());
	    System.out.println(keyReader.getClass());
	    Object keyPair = keyReader.readObject();
	    PrivateKeyInfo keyInfo;
	    System.out.println(keyPair.getClass());
	    
	    
	    if (keyPair instanceof PrivateKeyInfo) {
	    	System.out.println("Correct instance found");
	    	
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPair).decryptKeyPair(decryptionProv);
	        keyInfo = decryptedKeyPair.getPrivateKeyInfo();
	    } else {
	        keyInfo = ((PEMKeyPair) keyPair).getPrivateKeyInfo();
	    }

	    keyReader.close();

	    return converter.getPrivateKey(keyInfo);
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
}