package org.flowable.customtasks;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class PaymentFileGeneration implements JavaDelegate {
	final static Logger logger = Logger.getLogger(PaymentFileGeneration.class);
	public static Map<String,String> configMap;	
	
	public void execute(final DelegateExecution execution) {
		executeWriteFile(execution);
	}
	
	public static void executeWriteFile(final DelegateExecution execution) {
		logger.info("************Inside PaymentFileGeneration.............*****");
		String header = null;
		String value="";
		String writeFileName="";
		try {
			try {
				configMap=PropertiesUtil.readConfigPropertiesFile(Constants.READ_FILE_NAME);
				logger.info("Util ConfigMap::----->"+configMap);
			}catch(Exception ee) {	
				logger.error("Exception while reading config file::"+ee.getMessage());
			    ee.printStackTrace();
			}
			// Hard coded
			if(null== configMap || configMap.size()==0) {
		   	    configMap=new HashMap<>();
				configMap.put(Constants.PAYMENT_HEADER, "PAYMENT_ID,DATE");
				configMap.put(Constants.PAYMENT_WRITER_FILE_PATH, "/home/ubuntu/Attachment/Feeds/Output/");
				configMap.put(Constants.PAYMENT_WRITER_FILE_NAME, "HCPaymentFile");
				configMap.put(Constants.PAYMENT_WRITER_FILE_EXT, ".txt");
				logger.info("If null configMap::----->"+configMap);
			}
			logger.info(" configMap::----->"+configMap);
			header=configMap.get(Constants.PAYMENT_HEADER);
			writeFileName = configMap.get(Constants.PAYMENT_WRITER_FILE_PATH)
							.concat(configMap.get(Constants.PAYMENT_WRITER_FILE_NAME))
							.concat(DateTime.now().toString("yyyyMMddHHmmss"))
							.concat(configMap.get(Constants.PAYMENT_WRITER_FILE_EXT));
		   
			final Map<String, Object> map = (Map<String, Object>) execution.getVariables();
			value=messageFormat(map);
			if(logger.isDebugEnabled()) {
			   logger.debug("*******MAP vairables::*********** "+map);
			   logger.info("FileName:::"+writeFileName+", value::"+value);	
			 }
			PropertiesUtil.writeINFile(writeFileName,value);
			logger.info("***********Exit PaymentFileGeneration***************");
		} catch (Exception ee) {
			logger.info("Exception PaymentFileGeneration............." + ee.getMessage());
			ee.printStackTrace();
		}
	}
	public static String messageFormat(Map<String, Object> map) {
		String msg="";
		try {     msg  =msg.concat("*****INVOICE DETAILS************* \n");
		          msg = msg.concat(" Vendor Name : ").concat(map.get("vendorname") != null ? map.get("vendorname").toString(): " ").concat("\n");
			      msg = msg.concat(" Invoice Number : ").concat(map.get("invoicenumber") != null ? map.get("invoicenumber").toString(): " ").concat("\n");
			      msg = msg.concat(" Invoice Amount : ").concat(map.get("invoiceamount") != null ? map.get("invoiceamount").toString(): " ").concat("\n");
			      msg = msg.concat(" Invoice Date : ").concat(map.get("invoicedate") != null ? map.get("invoicedate").toString(): " ").concat("\n");
			      msg = msg.concat(" Invoice Type : ").concat(map.get("invoicetype") != null ? map.get("invoicetype").toString(): " ").concat("\n");
			      msg = msg.concat(" PO Number : ").concat(map.get("ponumber") != null ? map.get("ponumber").toString(): " ").concat("\n");
			      msg  =msg.concat("*****I*************************** \n");
			        
		}catch(Exception e) {
			logger.info("Exception PaymentFileGeneration[messageFormat]............." + e.getMessage());
			e.printStackTrace();
		}
		
		
		return msg;
	}
	public static void main(String[] args) {
		Map<String, Object> map=new HashMap<>();
		map.put("vendorname", new Date());
		System.out.println(""+messageFormat(map));
		
	}
}