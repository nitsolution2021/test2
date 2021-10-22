package org.flowable.customtasks;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class DBTaskProperty
{   final static Logger logger = Logger.getLogger(DBTaskProperty.class);
    private static Properties properties;
    
    public DBTaskProperty() throws FileNotFoundException, IOException, Exception {
        DBTaskProperty.properties = new Properties();
        final FileInputStream inStream = new FileInputStream("/opt/DecisionBox/decision-box.properties");
        final Throwable t = null;
        try {
            DBTaskProperty.properties.load(inStream);
        }
        catch (Throwable t2) {
            throw t2;
        }
        if (inStream != null) {
            if (t != null) {
                try {
                    inStream.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            else {
                inStream.close();
            }
        }
		
    }

    
    public static Properties intanceofPropertyFile() throws FileNotFoundException, IOException {
        if (null != DBTaskProperty.properties) {
            DBTaskProperty.properties = new Properties();
        }
        final FileInputStream inStream = new FileInputStream("/opt/DecisionBox/decision-box.properties");
           System.out.println("inStream::"+inStream);
        final Throwable t = null;
        try {
            DBTaskProperty.properties.load(inStream);
        }
        catch (Throwable t2) {
            throw t2;
        }
        if (inStream != null) {
            if (t != null) {
                try {
                    inStream.close();
                }
                catch (Throwable exception) {
                    t.addSuppressed(exception);
                }
            }
            else {
                inStream.close();
            }
        }
        if (inStream != null) {
            if (t != null) {
                try {
                    inStream.close();
                }
                catch (Throwable exception2) {
                    t.addSuppressed(exception2);
                }
            }
            else {
                inStream.close();
            }
        }
        System.out.println("Return:"+DBTaskProperty.properties);
        return DBTaskProperty.properties;
    }
    
	public static String getPropertyValue(String fileName, String key) {
		String value = null;
		Properties properties = new Properties();
		try (FileInputStream stream = new FileInputStream((fileName))) {
             properties.load(stream);
			 value= properties.getProperty(key);
             System.out.println(""+key+"="+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return value;
	}
	public static Map<String,String> getMapPropertyValue(String fileName) {
		Map<String,String> map=new HashMap<>();
		Properties properties = new Properties();
		try (FileInputStream stream = new FileInputStream((fileName))) {
             properties.load(stream);
			Enumeration en= properties.keys();
			while(en.hasMoreElements()) {
				String lkey=(String) en.nextElement();
				map.put(lkey, properties.getProperty(lkey));
				lkey=null;
			}
			System.out.println(map);
          } catch (IOException e) {
            e.printStackTrace();
        }
		return map;
	}
	public static boolean writeINFile(String fileName) throws IOException {
		boolean status = false;
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) 
		{   String s="HEADER".concat("\n").concat("VALUES");
		    writer.write(s);
		    status=true;
		}
		return status;
	}
    
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
    	DBTaskProperty obj= new DBTaskProperty();
    	System.out.println(System.getProperty("user.dir"));
    	System.out.println(DateTime.now().toString("yyyyMMddHHmmss"));
    	//obj.getPropertyValue("F:\\STSWorkspace\\DecisionBoxAPIs\\src\\main\\resources\\Config.properties","PAYMENT_HEADER");
    	//System.out.println("VALUE::"+value);
    	
    //	boolean b=obj.writeINFile("F:\\STSWorkspace\\DecisionBoxAPIs\\src\\main\\resources\\PaymentFile.properties");
      //  System.out.println("Status:"+b);
	}
}