import java.io.FileReader;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import sun.misc.BASE64Encoder;

class MainClassInline {
	
	public static void main(String[] args) 
		{
		try{
		FileReader fileReaderPk = new FileReader("C:/Masters/Internship/QI/carpark_static_import/Input/QI_HDB_4.pem");
	    PEMParser keyReaderPk = new PEMParser(fileReaderPk);

	    JcaPEMKeyConverter converterPk = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProvPk = new JcePEMDecryptorProviderBuilder().build("invent2006".toCharArray());

	    Object keyPairPk = keyReaderPk.readObject();
	    PrivateKeyInfo keyInfoPk;

	    if (keyPairPk instanceof PEMEncryptedKeyPair) {
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPairPk).decryptKeyPair(decryptionProvPk);
	        keyInfoPk = decryptedKeyPair.getPrivateKeyInfo();
	    } else {
	        keyInfoPk = ((PEMKeyPair) keyPairPk).getPrivateKeyInfo();
	    }

	    keyReaderPk.close();
		
		
		PrivateKey pk = converterPk.getPrivateKey(keyInfoPk);


		FileReader fileReaderPub = new FileReader("C:/Masters/Internship/QI/carpark_static_import/Input/QI_HDB_4.pem");
	    PEMParser keyReaderPub = new PEMParser(fileReaderPub);

	    JcaPEMKeyConverter converterPub = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProvPub = new JcePEMDecryptorProviderBuilder().build("invent2006".toCharArray());

	    Object keyPairPub = keyReaderPub.readObject();
	    SubjectPublicKeyInfo keyInfoPub;

	    if (keyPairPub instanceof PEMEncryptedKeyPair) {
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPairPub).decryptKeyPair(decryptionProvPub);
	        keyInfoPub = decryptedKeyPair.getPublicKeyInfo();
	    } else {
	        keyInfoPub = ((PEMKeyPair) keyPairPub).getPublicKeyInfo();
	    }

	    keyReaderPub.close();
		
		PublicKey pubk =  converterPub.getPublicKey(keyInfoPub);
		
		byte[] data = "test".getBytes("UTF8");

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

		

	/*
	
	private PublicKey readPublicKey(String privateKeyPath, String keyPassword) throws IOException {

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
	
	
	private PrivateKey readPrivateKey(String privateKeyPath, String keyPassword) throws IOException {
		
	    FileReader fileReader = new FileReader(privateKeyPath);
	    PEMParser keyReader = new PEMParser(fileReader);

	    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
	    PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(keyPassword.toCharArray());

	    Object keyPair = keyReader.readObject();
	    PrivateKeyInfo keyInfo;

	    if (keyPair instanceof PEMEncryptedKeyPair) {
	        PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyPair).decryptKeyPair(decryptionProv);
	        keyInfo = decryptedKeyPair.getPrivateKeyInfo();
	    } else {
	        keyInfo = ((PEMKeyPair) keyPair).getPrivateKeyInfo();
	    }

	    keyReader.close();

	    return converter.getPrivateKey(keyInfo);
	}*/

}}