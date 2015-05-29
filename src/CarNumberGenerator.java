import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import com.csvreader.*;

public class CarNumberGenerator {
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		CarNumberGenerator obj = new CarNumberGenerator();
		obj.getRandom();
		obj.getRandomAlpha();
		obj.readWriteCsv("C:/Masters/workspace/QI_JavaCodes/src/FullSet.csv","C:/Masters/workspace/QI_JavaCodes/src/FullSet_out.csv");
	}
	
	public void readWriteCsv(String fileName, String output) throws IOException, NoSuchAlgorithmException{
		CsvReader records = new CsvReader(fileName);
		records.readHeaders();
		CsvWriter csvOutput = new CsvWriter(new FileWriter(output, true), ',');
		csvOutput.write("car_no");
		csvOutput.write("latitude");
		csvOutput.write("longitude");
		csvOutput.endRecord();
		while(records.readRecord()){
			csvOutput.write("SH"+getRandomAlpha()+getRandom()+getRandomAlpha());
			csvOutput.write(records.get("latitude"));
			csvOutput.write(records.get("longitude"));
			csvOutput.endRecord();
		}
		csvOutput.close();
	}
	
	
	public int getRandom() throws NoSuchAlgorithmException{
		SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");         
	    return randomGenerator.nextInt(9999);
	}
	
	
	public char getRandomAlpha() throws NoSuchAlgorithmException{
		SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");         
	    return (char)((int)'A'+randomGenerator.nextInt(25));
	}
	
	
}
