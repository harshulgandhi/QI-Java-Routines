import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.operator.OperatorCreationException;

import sun.misc.BASE64Encoder;

class CreateSignature{
	public static void main(String[] args) throws Exception{
		System.out.println("Getting signature");
		CreateSignature cgObj = new CreateSignature();
		String wsdl_url = "https://services2s.nicehomes.com.sg/webapp/BN22WSCPKAvail/BN22JCarparkAvailabilityEnqService/WEB-INF/wsdl/BN22JCarparkAvailabilityEnqService.wsdl";
		String keyPath = "C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem";
		String baseFolder= "C:/Masters/Internship/QI/carpark_static_import/Output";
		cgObj.encodedSignature(wsdl_url,"BN22JCarparkAvailabilityEnqService","QI","HDB",keyPath,keyPath,baseFolder);
	}

	public void encodedSignature(String wsdl_url, String service_name, String  sender, String  receiver, String  key_file, String cert_list_file, String  base_folder) throws  Exception{

		System.out.println("Certificate used : "+key_file);
		//Stage 1: get raw signature
		String base_signature_string = sender+receiver+getDateTime("yyyyMMdd")+"162305";
				//getDateTime("HHmmss");
		System.out.println("Base String : "+base_signature_string);

		//Step 2: Create SHA-1 hash
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		byte[] hashedBytes = crypt.digest(base_signature_string.getBytes("UTF-8"));
		String _str_hashedBytes = DatatypeConverter.printHexBinary(hashedBytes);
		
		System.out.println("Hashed String : "+_str_hashedBytes);
		
		//Step 3: Decrypt and sign with a key
		MainClass dec = new MainClass();
		//PrivateKey pk = dec.readPrivateKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem","Invent2006;");
		
		PrivateKey pk = ((MainClass) dec).readPrivateKey("C:/Masters/Internship/QI/carpark_static_import/Input/QICERT.pem", "RSA");
			System.out.println("pk :: "+pk.toString());
		byte[] data = _str_hashedBytes.getBytes("UTF8");
       Signature sig = Signature.getInstance("SHA1WithRSA");
       sig.initSign(pk);
       sig.update(data);
       byte[] signatureBytes = sig.sign();
       System.out.println("Signatured String: "+signatureBytes);
       
		//Step 4: base 64 encode
       System.out.println("Singature:" + new BASE64Encoder().encode(signatureBytes));
	}	

	public String getDateTime(String format){
		DateFormat fmt = new SimpleDateFormat(format);
		Date date = new Date();
		return (fmt.format(date));
	}
}