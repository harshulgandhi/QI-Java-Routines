
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;

class CreateSignature{
	public static void main(String[] args) throws NoSuchAlgorithmException,UnsupportedEncodingException{
		System.out.println("Getting singature");
		CreateSignature cgObj = new CreateSignature();
		cgObj.encodedSignature("h","a","r","s","h","u","l");

	}

	public void encodedSignature(String wsdl_url, String service_name, String  sender, String  receiver, String  key_file, String cert_list_file, String  base_folder) throws  NoSuchAlgorithmException, UnsupportedEncodingException{

		//Stage 1: get raw signature
		String base_signature_string = sender+receiver+getDateTime("yyyyMMdd")+getDateTime("HHmmSS");
		System.out.println("Base String : "+base_signature_string);

		//Step 2: Create SHA-1 hash
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		byte[] hashedBytes = crypt.digest(base_signature_string.getBytes("UTF-8"));
		String _str_hashedBytes = DatatypeConverter.printHexBinary(hashedBytes);
		
		System.out.println("Hashed String : "+_str_hashedBytes);


	}	

	public String getDateTime(String format){
		DateFormat fmt = new SimpleDateFormat(format);
		Date date = new Date();
		return (fmt.format(date));
	}
}