package org.flowable.customtasks;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

public class PropertiesUtil{ 
	public static Map<String,String> configMap;
	
	
	final static Logger logger = Logger.getLogger(PropertiesUtil.class);

	public static String getPropertyValue(String fileName, String key) {
		String value = null;
		Properties properties = new Properties();
		try (FileInputStream stream = new FileInputStream((fileName))) {
             properties.load(stream);
			 value= properties.getProperty(key);
			 logger.info(""+key+"="+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return value;
	}
	public static String getPropertyValue(String key) {
		String value = null;
		String fileName="";
		fileName = System.getProperty("user.dir").concat("//").concat(Constants.DEFAULT_CONFIG_FILENAME);
		Properties properties = new Properties();
		try (FileInputStream stream = new FileInputStream((fileName))) {
             properties.load(stream);
			 value= properties.getProperty(key);
			 logger.info(""+key+"="+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return value;
	}
	
	public static Map<String,String> readConfigPropertiesFile(String fileName) {
		Properties properties = new Properties();
		logger.info("FILE READER*******  fileName:"+fileName);;
		try (FileInputStream stream = new FileInputStream((fileName))) {
            properties.load(stream);
			Enumeration en= properties.keys();
			configMap=new HashMap<>();
			while(en.hasMoreElements()) {
				String lkey=(String) en.nextElement();
				configMap.put(lkey, properties.getProperty(lkey));
				lkey=null;
			}
		 } catch (Exception e) {
        	  logger.error("Exception [getMapPropertyValue]"+e.getMessage());
              e.printStackTrace();
        }
		return configMap;
	}
	
	public static boolean writeINFile(String fileName, String msg) throws IOException {
		boolean status = false;
		
		Path path = Paths.get(fileName);
		if(path.isAbsolute()) {
			logger.info("Is absolute path?  Yes");
		}else {
			logger.info("I s absolute path?  No");}
			logger.info("Writer File Path: "+path);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) 
		{
		    writer.write(msg);
		    status=true;
		}catch(Exception e) {
			logger.error("Exception[writeINFile] :"+e.getMessage());
			e.printStackTrace();
			
		}
		return status;
	}
    
    
    public static void main(String[] args)  {
    try {  
    	PropertiesUtil obj= new PropertiesUtil();
    	Map<String,String> map = readConfigPropertiesFile("F://STSWorkspace//DecisionBoxAPIs//src//main//resources//Config.properties");
    	System.out.println("MAP:"+map);
    	configMap=new HashMap<>();
		configMap.put(Constants.PAYMENT_HEADER, "PAYMENT_ID,DATE");
		configMap.put(Constants.PAYMENT_WRITER_FILE_PATH, "/home/ubuntu/Attachment/Feeds/Output/");
		configMap.put(Constants.PAYMENT_WRITER_FILE_NAME, "HCPaymentFile");
		configMap.put(Constants.PAYMENT_WRITER_FILE_EXT, ".txt");
		
    	String writeFileName = configMap.get(Constants.PAYMENT_WRITER_FILE_PATH).trim()
				.concat(configMap.get(Constants.PAYMENT_WRITER_FILE_NAME).trim())
				.concat(DateTime.now().toString("yyyyMMddHHmmss").trim())
				.concat(configMap.get(Constants.PAYMENT_WRITER_FILE_EXT).trim()).trim();
	
    	System.out.println("writeFileName:"+writeFileName);
    		
    	}catch(Exception e) {
    		System.out.println("Exception ::"+e.getMessage());
    		e.printStackTrace();
    		
    	}
   
	}
}