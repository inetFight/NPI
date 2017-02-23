package npi.test.address;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class ReadFile {
	
	public static List<AddressModel> readFileToListByLine() throws InterruptedException{
		String csvFile = "D:\\NPI\\тест2.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ";";
		List <AddressModel> tmp = new ArrayList<AddressModel>();
	    int cnt = 0;
	    System.out.println("Начинаю читать файл");
		try {
			br = new BufferedReader(
			           new InputStreamReader(new FileInputStream(csvFile), "UTF-8"));
//			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
				
			        // use comma as separator
				String[] ext = line.split(cvsSplitBy);
				AddressModel adress = null;
				if(ext.length > 2){
				 adress = new AddressModel(ext[0], ext[1], ext[2]);
				}
				else {
				 adress = new AddressModel(ext[0], ext[1], "");
				}
				tmp.add(adress);
				cnt += 1;
				
				
			}
	   
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		  System.out.println(" В файле найдено " + cnt + " строк");
		return tmp;
		}

}
