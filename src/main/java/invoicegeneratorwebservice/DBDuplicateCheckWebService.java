/**
 * AMIT modified on 11th May 2021
 * Decompiled
 */


package invoicegeneratorwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.flowable.customtasks.ExternalSystemCallResponse;

import invoice.services.DBDuplicateChecService;
import invoiceGeneratorEntitites.DBDuplicateCheck;
import invoiceGeneratorEntitites.DBDuplicateCheckServiceTask;
import invoiceGeneratorEntitites.DBFunctionalTemplate;
import invoiceGeneratorEntitites.DBMapperTemplate;

@Path("/DBDuplicateCheck")
public class DBDuplicateCheckWebService
{ 
	final static Logger logger = Logger.getLogger(DBDuplicateCheckWebService.class);
    DBDuplicateChecService service;
    
    public DBDuplicateCheckWebService() {
        this.service = new DBDuplicateChecService();
    }
    
    @POST
    @Path("/serviceTaskGenericWebService")
    @Produces({ "application/json" })
    public ExternalSystemCallResponse duplicateCheckServiceTask(final ExternalSystemCallRequest object) {
        final Map<String, Object> responseVariable = new HashMap<String, Object>();
        final ExternalSystemCallResponse response = new ExternalSystemCallResponse();
        try {
            final String functionalFlowIndicator = this.service.deriveFunctionalIndicator(object);
            final Map<String, String> duplicateVariableMap = new HashMap<String, String>();
            if (functionalFlowIndicator.equalsIgnoreCase("DUPLICATE")) {
                final DBFunctionalTemplate returnValueFromTask = this.service.getTempalteForDuplicateCheck(object);
                final String variableTemplateForDuplicate = returnValueFromTask.getTemplateMetaData();
                final List<String> templateVariableSequence = new ArrayList<String>();
                if (null != variableTemplateForDuplicate) {
                    final StringTokenizer st = new StringTokenizer(variableTemplateForDuplicate, ",");
                    while (st.hasMoreTokens()) {
                        String token = st.nextToken();
                        token = token.trim();
                        duplicateVariableMap.put(token, object.getVairables().get(token).toString());
                        templateVariableSequence.add(token);
                    }
                    final List<DBDuplicateCheckServiceTask> duplicateRecordList= this.service.checkIfDuplicateForServiceTask((Map)duplicateVariableMap, object, (List)templateVariableSequence);
                    final List<String> returnVariableList = new ArrayList<String>();
                    final StringTokenizer rt = new StringTokenizer(returnValueFromTask.getReturnVariableName(), ",");
                    while (rt.hasMoreTokens()) {
                        final String token2 = rt.nextToken();
                        if (null != token2) {
                            returnVariableList.add(token2.trim());
                        }
                    }
                    //AMIT If it duplicate then update duplicate work unit id and duplicate flag.
                    if (null!= duplicateRecordList && duplicateRecordList.size() > 0) { 
                        responseVariable.put(returnVariableList.get(0), true);
                        this.service.updateInDuplicateCheckForST((Map)duplicateVariableMap, object, (List)templateVariableSequence, duplicateRecordList);
                     }
                    else {//AMIT else insert record in cutome table.
                        responseVariable.put(returnVariableList.get(0), false);
                        this.service.saveInDuplicateCheckForST((Map)duplicateVariableMap, object, (List)templateVariableSequence);
                    }
                }
            }
            if (functionalFlowIndicator.equalsIgnoreCase("MATCHER")) {
                final DBMapperTemplate returnValueFromTask2 = this.service.getTempalteForMapper(object);
                final String selectVariable = returnValueFromTask2.getFetchVariable();
                final String criteriaVariable = returnValueFromTask2.getCriteriaMappingVariable();
                final List<String> selectVariableList = new ArrayList<String>();
                final Map<String, String> selectVariableMap = new HashMap<String, String>();
                final List<String> criteriaVariableList = new ArrayList<String>();
                final Map<String, String> criteriaVariableMap = new HashMap<String, String>();
                if (null != selectVariable && null != criteriaVariable) {
                    StringTokenizer mt = new StringTokenizer(selectVariable, ",");
                    while (mt.hasMoreTokens()) {
                        final String token3 = mt.nextToken().trim();
                        final StringTokenizer vt = new StringTokenizer(token3, "-");
                        String key = null;
                        String value = null;
                        int i = 0;
                        while (vt.hasMoreTokens()) {
                            final String keyValue = vt.nextToken().trim();
                            if (i == 0) {
                                key = keyValue;
                            }
                            else if (i == 1) {
                                value = keyValue;
                            }
                            ++i;
                        }
                        selectVariableMap.put(key, value);
                        selectVariableList.add(token3);
                    }
                    mt = new StringTokenizer(criteriaVariable, ",");
                    while (mt.hasMoreTokens()) {
                        final String token3 = mt.nextToken().trim();
                        final StringTokenizer vt = new StringTokenizer(token3, "-");
                        String key = null;
                        String value = null;
                        int i = 0;
                        while (vt.hasMoreTokens()) {
                            final String keyValue = vt.nextToken().trim();
                            if (i == 0) {
                                key = keyValue;
                            }
                            else if (i == 1) {
                                value = keyValue;
                            }
                            ++i;
                        }
                        criteriaVariableMap.put(key, value);
                        criteriaVariableList.add(token3);
                    }
                    final Map<String, Object> mapperReturn = (Map<String, Object>)this.service.checkIfMatchedFromERP((List)criteriaVariableList, (Map)criteriaVariableMap, (List)selectVariableList, (Map)selectVariableMap, object, returnValueFromTask2);
                    responseVariable.putAll(mapperReturn);
                }
            }
            response.setActivityId(object.getActivityId());
            response.setActivityName(object.getActivityName());
            response.setParentId(object.getParentId());
            response.setProcessDefinitionId(object.getProcessDefinitionId());
            response.setProcessinstanceId(object.getProcessinstanceId());
            response.setRootProcessInstanceId(object.getRootProcessInstanceId());
            response.setVairables(object.getVairables());
            response.setSettingVariablesNextFlow((Map)responseVariable);
        }
        catch (Exception e) {
            e.getMessage();
        }
        return response;
    }
    
    @POST
    @Path("/save")
    @Produces({ "application/json" })
    public Response gerERPPODetails(final ExternalSystemCallRequest object) {
        final DBDuplicateCheck dbObject = null;
        final HashMap<String, String> responseVariable = new HashMap<String, String>();
        return Response.status(200).entity((Object)dbObject).build();
    }
    
    @POST
    @Path("/checkForDuplicate")
    @Produces({ "application/json" })
    public Response checkDuplicate(final Object object) {
        DBDuplicateCheck dbObject = null;
        try {
            Map<String, List<String>> test1 = new LinkedHashMap<String, List<String>>();
            Map<String, String> fromLinkedHashmap = new LinkedHashMap<String, String>();
            test1 = (Map<String, List<String>>)object;
            for (final Map.Entry<String, List<String>> entry : test1.entrySet()) {
                try {
                    fromLinkedHashmap = (Map<String, String>)(Map)entry.getValue();
                    for (final Map.Entry<String, String> entry2 : fromLinkedHashmap.entrySet()) {
                        final String key = entry2.getKey();
                        if ((key.equalsIgnoreCase("po_no") || key.equalsIgnoreCase("po_no.") || key.equalsIgnoreCase("ponumber")) && null != entry2 && null != entry2.getValue()) {
                            if (null == dbObject) {
                                dbObject = new DBDuplicateCheck();
                            }
                            dbObject.setPoNumber("" + entry2.getValue().toString());
                        }
                        if ((key.equalsIgnoreCase("vendorname") || key.equalsIgnoreCase("vendorname.") || key.equalsIgnoreCase("vendorname")) && null != entry2 && null != entry2.getValue()) {
                            if (null == dbObject) {
                                dbObject = new DBDuplicateCheck();
                            }
                            dbObject.setVendorname("" + entry2.getValue().toString());
                        }
                        if ((key.equalsIgnoreCase("invoiceamount") || key.equalsIgnoreCase("invoiceamount.") || key.equalsIgnoreCase("invoiceamount")) && null != entry2 && null != entry2.getValue()) {
                            if (null == dbObject) {
                                dbObject = new DBDuplicateCheck();
                            }
                            dbObject.setInvoiceAmount("" + entry2.getValue().toString());
                        }
                        if ((key.equalsIgnoreCase("invoicenumber") || key.equalsIgnoreCase("invoicenumber.") || key.equalsIgnoreCase("invoicenumber")) && null != entry2 && null != entry2.getValue()) {
                            if (null == dbObject) {
                                dbObject = new DBDuplicateCheck();
                            }
                            dbObject.setInvoiceNumber("" + entry2.getValue().toString());
                        }
                        if ((key.equalsIgnoreCase("processinstanceid") || key.equalsIgnoreCase("processinstanceid.") || key.equalsIgnoreCase("process_id")) && null != entry2 && null != entry2.getValue()) {
                            if (null == dbObject) {
                                dbObject = new DBDuplicateCheck();
                            }
                            dbObject.setProcessInstanceId("" + entry2.getValue().toString());
                        }
                    }
                }
                catch (Exception e2) {
                    System.out.print("Not a form value");
                }
            }
            final String currentProcessInsatnceId = dbObject.getProcessInstanceId();
            final DBDuplicateCheck checkInDB = this.service.checkDuplicateValues(dbObject);
            if (null != checkInDB) {
                if (!currentProcessInsatnceId.equalsIgnoreCase(checkInDB.getProcessInstanceId())) {
                    dbObject.setMessage("FAILED");
                }
                else {
                    dbObject.setMessage("SUCCESS");
                }
            }
            else {
                dbObject = new DBDuplicateCheck();
                dbObject.setMessage("EXCEPTION");
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        return Response.status(200).entity((Object)dbObject).build();
    }
    
    /*
     * Get rest exposed services to get duplicate workitem details..
     */
    @GET
    @Path("/getDuplicateItemDetails/{workunitid}")
    @Produces({ "application/json" })
    public Response getDuplicateDetails(@PathParam("workunitid") String workunitid) {
    	//  DBDuplicateCheck dbObject = null;
    	  logger.info("AMIT WorkUnitId::"+workunitid);
    	//Response.status(200).entity((Object)dbObject).build()
    	
     return Response.status(200).entity("workunitid:>>>>"+workunitid).build();
    }
    
}