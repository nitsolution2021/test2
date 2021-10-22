package org.flowable.customtasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DuplicateCheck implements JavaDelegate {
	final static Logger logger = Logger.getLogger(DuplicateCheck.class);
	public void execute(final DelegateExecution execution) {
		logger.info("Inside DuplicateCheck.............ProcessInstanceId:"+execution.getProcessInstanceId());
		String payload = null;
		String requestUrl = null;
		try {
			requestUrl = DBTaskProperty.intanceofPropertyFile().getProperty("genericExternalTaskURL");
			logger.info("Inside DuplicateCheck.............requestUrl:"+requestUrl);
			
		} catch (Exception e1) {
			logger.info("DuplicateCheck Exception :"+e1.getMessage());
			e1.printStackTrace();
		}
		requestUrl="http://65.2.162.230:8080/jsflab/rest/DBDuplicateCheck/serviceTaskGenericWebService";
		
		final ExternalSystemCallRequest request = new ExternalSystemCallRequest();
		request.setActivityId(execution.getCurrentActivityId());
		request.setActivityName(execution.getEventName());
		request.setParentId(execution.getParentId());
		request.setProcessDefinitionId(execution.getProcessDefinitionId());
		request.setProcessinstanceId(execution.getProcessInstanceId());
		final Map<String, Object> vairables = (Map<String, Object>) execution.getVariables();
		request.setVairables((Map) vairables);
		logger.info("Inside DuplicateCheck.............1:");
		final ObjectMapper Obj = new ObjectMapper();
		try {
			logger.info("Inside DuplicateCheck.............2:");
			final String jsonStr = Obj.writeValueAsString((Object) request); 
			logger.info(jsonStr);
			logger.info("Testing custom task ");
			payload = jsonStr;
		} catch (IOException e2) {
			logger.info("IOException DuplicateCheck............."+e2.getMessage());
			e2.printStackTrace();
		}
		final String respOnseJason = sendPostRequest(requestUrl, payload);
		logger.info("respOnseJason::"+respOnseJason);
		try {
			final ExternalSystemCallResponse emp = (ExternalSystemCallResponse) Obj.readValue(respOnseJason,
					(Class) ExternalSystemCallResponse.class);
			emp.getSettingVariablesNextFlow().putAll(vairables);
		   
			//Hardcoded 
			//emp.getSettingVariablesNextFlow().put("isDuplicate", false);
			logger.info("#########Emp::::##################"+emp.getSettingVariablesNextFlow());
			
			execution.setVariables(emp.getSettingVariablesNextFlow());
			logger.info(emp);
		} catch (JsonGenerationException e3) {
			logger.info("IOException DuplicateCheck............."+e3.getMessage());
			e3.printStackTrace();
		} catch (JsonMappingException e4) {
			logger.info("IOException DuplicateCheck............."+e4.getMessage());
			e4.printStackTrace();
		} catch (IOException e5) {
			logger.info("IOException DuplicateCheck............."+e5.getMessage());
			e5.printStackTrace();
		}catch(Exception ee) {
		  logger.info("Exit DuplicateCheck............."+ee.getMessage());
		  ee.printStackTrace();
		}
		
	}
	/*
	 * 
	 */
	public static DelegateExecution duplicateInvoiceDataPopulate(DelegateExecution execution) {
	try {
		logger.info("Inside DuplicateCheck[duplicateInvoiceDataPopulate]");
		Map<String, Object> map = (Map<String, Object>) execution.getVariables();
		map.put("vendorname","DCBOXTEST");
		execution.setVariables(map);
		
		logger.debug("Inside DuplicateCheck[duplicateInvoiceDataPopulate]");
		
		//execution.setVariable("duplicateWorkItemId", "12345");
		//execution.setVariable("pdfFileName", "12345");
	} catch(Exception ee) {
			logger.fatal("Exception::"+ee.getMessage());
	        
	}
		return execution;
		
	}
	

	public static String sendPostRequest(final String requestUrl, final String payload) {
		try {
			logger.info("Inside sendPostRequest.....");;
			final URL url = new URL(requestUrl);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			final StringBuffer jsonString = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
			return jsonString.toString();
		} catch (Exception e) {
			logger.info("Exception sendPostRequest....."+e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
		
		
	
}