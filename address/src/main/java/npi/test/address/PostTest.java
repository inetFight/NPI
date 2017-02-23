package npi.test.address;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PostTest {
	
	public PostTest(){}

	public static void Post(List<AddressModel> ext, PrintWriter writerResult, PrintWriter writerResultError,PrintWriter writerResultErrorData) throws URISyntaxException, ParseException, IOException{
	 
	 
		
			for (int j = 0; j < ext.size(); j++) {
			CloseableHttpClient httpclient = HttpClients.createDefault();

			URIBuilder builder = new URIBuilder("https://api.novaposhta.ua/v2.0/json/");
//			URIBuilder builder = new URIBuilder("http://webclient.sb.np.ua/data/get/container/JSON/");
			URI uri = builder.build();
			HttpPost request = new HttpPost(uri);
			String postCode = ext.get(j).getPostCode().replace("\"", "");
			String city = ext.get(j).getCity().replace("\"", "");
			String street = ext.get(j).getStreet().replace("\"", "");
			
			postCode = postCode.replace("\\", "");
			city = city.replace("\\", "");
			street = street.replace("\\", "");
			
//			postCode = postCode.replace(";", "");
//			city = city.replace(";", "");
//			street = street.replace(";", "");
			System.out.println(Thread.currentThread().getName() + " --> " + j + " из " + ext.size());
//			StringEntity reqEntity = new StringEntity("{"
//    + "\"modelName\": \"AddressGeneral\","
//    + "\"calledMethod\": \"getAddressByTextString\","
//    + "\"methodProperties\": {"
//    + "\"PostCode\": \"19701\","
//    + "\"Locality\": \"Золотоноша\","
//    + "\"AddressString\": \"Баха 8\""
//    + "},\"apiKey\": \"809961161fcb3b8b5d9552109096a715\"}"
//    		 ,"UTF-8");
			StringEntity reqEntity = new StringEntity("{\"apiKey\": \"9ed76991e45e507322577e92984e511b\","
					+ "\"modelName\": \"AddressGeneral\"," + "\"calledMethod\": \"getAddressByTextString\","
					+ "\"methodProperties\": {" 
					+ "\"PostCode\":\"" + postCode + "\","
					+ "\"Locality\":\"" + city + "\"," 
					+ "\"AddressString\":\"" + street
					+ "\"" + "}}", "UTF-8");
			
			request.setEntity(reqEntity);
//			System.out.println(EntityUtils.toString(reqEntity, "UTF-8"));
			HttpResponse response = null;
			try {
				response = httpclient.execute(request);
			} catch (Exception e) {
				e.printStackTrace();
				response.getStatusLine();
				continue;
			}
			
			
			String otvet = EntityUtils.toString(response.getEntity(), "UTF-8");
//			System.out.println(otvet);
			JSONParser jsonParser = new JSONParser();
			try {
				
			
			if(jsonParser.parse(otvet) instanceof JSONObject){
			JSONObject jsonObject = (JSONObject) jsonParser.parse(otvet);
			
			boolean success = (Boolean) jsonObject.get("success");
			if (success == true) {
				JSONArray data = (JSONArray) jsonObject.get("data");
				Iterator i = data.iterator();
				while (i.hasNext()) {

					JSONObject innerObj = (JSONObject) i.next();
					String toWrite= ext.get(j).getPostCode().replaceAll(";", "") + ";"
							 + ext.get(j).getCity().replaceAll(";", "") + ";"
							 + ext.get(j).getStreet().replaceAll(";", "") + ";"
							 + "1;"
							 + innerObj.get("SettlementRef") + ";"
							 + innerObj.get("SettlementDescription") + ";"
							 + innerObj.get("SettlementStreetRef") + ";"
							 + innerObj.get("SettlementStreetDescription") + ";"
							 + innerObj.get("HouseNumber") + ";"
							 + innerObj.get("StreetsAvailability");
					PostTest t = new PostTest();
					t.write(writerResult, toWrite);
//					writerResult.write(ext.get(j).getPostCode() + ";"
//									 + ext.get(j).getCity() + ";"
//									 + ext.get(j).getStreet() + ";"
//									 + "1;"
//									 + innerObj.get("SettlementRef") + ";"
//									 + innerObj.get("SettlementDescription") + ";"
//									 + innerObj.get("SettlementStreetRef") + ";"
//									 + innerObj.get("SettlementStreetDescription") + ";"
//									 + innerObj.get("HouseNumber") + ";"
//									 + innerObj.get("StreetsAvailability"));
//					writerResult.write(System.lineSeparator());
//					writerResult.flush();

				}
			} else if(success == false) {
				JSONArray data = (JSONArray) jsonObject.get("errors");
				if(data.get(0).equals("Data is invalid")){
					
					 writerResultError.write(EntityUtils.toString(reqEntity));
					 writerResultError.write(System.lineSeparator());
					 writerResultError.flush();
				}
				else {
					String toWriteE = ext.get(j).getPostCode().replaceAll(";", "") + ";"
							 + ext.get(j).getCity().replaceAll(";", "") + ";"
							 + ext.get(j).getStreet().replaceAll(";", "") + ";"
							 + "0;"
							 + data.get(0);
					PostTest t = new PostTest();
					t.write(writerResult, toWriteE);
//				writerResult.write(ext.get(j).getPostCode() + ";"
//						 + ext.get(j).getCity() + ";"
//						 + ext.get(j).getStreet() + ";"
//						 + "0;"
//						 + data.get(0));
				
			
				}
			}
		}
			} catch (Exception e) {
				 
				 writerResultErrorData.write(EntityUtils.toString(reqEntity));
				 writerResultErrorData.write(System.lineSeparator());
				 writerResultErrorData.flush();
				 
				System.out.println(EntityUtils.toString(reqEntity));
				e.printStackTrace();
				continue;
			}
			httpclient.close();
	}
	}
	// Для синхронизирвоанной записи в файл
	public synchronized void write(PrintWriter printWriten, String string){
		printWriten.write(string + "\n");
		printWriten.write(System.lineSeparator());
		printWriten.flush();
	}
}
