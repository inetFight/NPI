package npi.test.address;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {
	

	public static void main (String[] args) throws URISyntaxException, ClientProtocolException, IOException, ParseException, InterruptedException {
		final List<AddressModel> all = ReadFile.readFileToListByLine();
		final List<AddressModel> ext1Thead = new ArrayList<AddressModel>();
		final List<AddressModel> ext2Thead = new ArrayList<AddressModel>();
		final List<AddressModel> ext3Thead = new ArrayList<AddressModel>();
		final List<AddressModel> ext4Thead = new ArrayList<AddressModel>();
		final List<AddressModel> ext5Thead = new ArrayList<AddressModel>();
		final PrintWriter writerResult = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream("d://NPI//testResultAdressTEST.csv", true), "UTF-8"));
		final PrintWriter writerResultError = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream("d://NPI//data_is_invalid.csv", true), "UTF-8"));
		final PrintWriter writerResultErrorData = new PrintWriter(
					new OutputStreamWriter(new FileOutputStream("d://NPI//errors.csv", true), "UTF-8"));
		
		for (int i = 0; i < all.size()/5; i++) {
			ext2Thead.add(all.get(i));			
		}
		
		for (int i = all.size()/5; i < all.size()/5*2; i++) {
			ext3Thead.add(all.get(i));
			
		}
		for (int i = all.size()/5*2; i < all.size()/5*3; i++) {
			ext4Thead.add(all.get(i));
			
		}
		for (int i = all.size()/5*3; i < all.size()/5*4; i++) {
			ext5Thead.add(all.get(i));
			
		}
		for (int i = all.size()/5*4; i < all.size(); i++) {
			ext1Thead.add(all.get(i));
			
		}
		
		
		Thread t1 = new Thread(){
		    public void run(){
		    	try {
//		    		writerResult.write("PostCode;Locality;AddressString;Распознало;settlementRef or Error;SettlementDescription;SettlementStreetRef;SettlementStreetDescription;HouseNumber;StreetsAvailability");
//		    		writerResult.write(System.lineSeparator());
//		    		writerResult.flush();
		    			System.out.println(Thread.currentThread().getName() + " --->" + ext1Thead.size());
					PostTest.Post(ext1Thead, writerResult, writerResultError, writerResultErrorData);
					
				} catch (org.apache.http.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //do stuff    
		    }};
		    
		    Thread t2 = new Thread(){
		        public void run(){
		        	try {
		        		System.out.println(Thread.currentThread().getName() + " --->" + ext2Thead.size());
						PostTest.Post(ext2Thead, writerResult, writerResultError, writerResultErrorData);
					
					} catch (org.apache.http.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            //do stuff    
		        }};
		        Thread t3 = new Thread(){
			        public void run(){
			        	try {
			        		System.out.println(Thread.currentThread().getName() + " --->" + ext3Thead.size());
							PostTest.Post(ext3Thead, writerResult, writerResultError, writerResultErrorData);
						
						} catch (org.apache.http.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            //do stuff    
			        }};
			        Thread t4 = new Thread(){
					    public void run(){
					    	try {
					    		System.out.println(Thread.currentThread().getName() + " --->" + ext4Thead.size());
								PostTest.Post(ext4Thead, writerResult, writerResultError, writerResultErrorData);
								
							} catch (org.apache.http.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					        //do stuff    
					    }};
					    Thread t5 = new Thread(){
						    public void run(){
						    	try {
						    		System.out.println(Thread.currentThread().getName() + " --->" + ext5Thead.size());
									PostTest.Post(ext5Thead, writerResult, writerResultError, writerResultErrorData);
									
								} catch (org.apache.http.ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (URISyntaxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						        //do stuff    
						    }};
		        t1.start();
		        t2.start();
		        t3.start();
		        t4.start();
		        t5.start();
	}


	
}
