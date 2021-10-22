package org.flowable.customtasks;


import org.apache.log4j.Logger;

public class Constants{ 
	final static Logger logger = Logger.getLogger(Constants.class);
	final static String DEFAULT_CONFIG_FILENAME="DCBOXConfig.properties";
	
	//Config file location, It should be envt specific
	final static String READ_FILE_NAME="/home/ubuntu/Attachment/Feeds/Config.properties";
	
	// Keys
	final static String PAYMENT_WRITER_FILE_PATH="PAYMENT_WRITER_FILE_PATH";
	final static String PAYMENT_WRITER_FILE_NAME="PAYMENT_WRITER_FILE_NAME";
	final static String PAYMENT_WRITER_FILE_EXT="PAYMENT_WRITER_FILE_EXT";
	
	final static String PAYMENT_HEADER="PAYMENT_HEADER";
	final static String WRITER_FILE_NAME="PAYMENT_HEADER";
}