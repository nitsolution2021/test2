/**
 * AMIT modified on 11th May 2021
 *  * Decompiled
 */

package invoice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;

import dao.DBDuplicateCheckDao;
import daoimpl.DBDuplicateCheckDaoImpl;
import invoiceGeneratorEntitites.DBDuplicateCheck;
import invoiceGeneratorEntitites.DBDuplicateCheckServiceTask;
import invoiceGeneratorEntitites.DBFunctionalTemplate;
import invoiceGeneratorEntitites.DBMapperTemplate;
import invoiceGeneratorEntitites.FunctionalRouterDB;
import invoicegeneratorwebservice.ExternalSystemCallRequest;
import notification.NotificationObject;

public class DBDuplicateChecService
{
	final static Logger logger = Logger.getLogger(DBDuplicateChecService.class);

    private DBDuplicateCheckDao daoImpl;
    
    public DBDuplicateChecService() {
        this.daoImpl = (DBDuplicateCheckDao)new DBDuplicateCheckDaoImpl();
    }
    
    public NotificationObject saveFormDetails(final DBDuplicateCheck dbObject) {
        NotificationObject notification = new NotificationObject();
        notification = this.daoImpl.save(notification, (Object)dbObject);
        return notification;
    }
    
    public DBDuplicateCheck checkValueForProcessInstanceId(final DBDuplicateCheck dbObject) throws Exception {
        dbObject.setProcessInstanceId(this.daoImpl.getProcessinstanceID(dbObject.getProcessInstanceId()));
        return this.daoImpl.getRecordForProcessInstance(dbObject);
    }
    
    public DBDuplicateCheck checkDuplicateValues(final DBDuplicateCheck dbObject) {
        dbObject.setProcessInstanceId(this.daoImpl.getProcessinstanceID(dbObject.getProcessInstanceId()));
        return this.daoImpl.checkDuplicateValues(dbObject);
    }
    
    public DBDuplicateCheck doubleCheckDuplicateValues(final DBDuplicateCheck dbObject, final String taskID) {
        final String currentProcessinstanceId = this.daoImpl.getProcessinstanceID(taskID);
        final DBDuplicateCheck duplcateObjec = this.daoImpl.checkDuplicateValues(dbObject);
        if (null != duplcateObjec && !currentProcessinstanceId.equalsIgnoreCase(currentProcessinstanceId)) {
            return null;
        }
        return new DBDuplicateCheck();
    }
    
    public String deriveFunctionalIndicator(final ExternalSystemCallRequest object) {
        final FunctionalRouterDB functionalRouterDB = new FunctionalRouterDB();
        functionalRouterDB.setActivityID(object.getActivityId());
        return this.daoImpl.deriveFunctionalIndicator(functionalRouterDB);
    }
    
    public DBFunctionalTemplate getTempalteForDuplicateCheck(final ExternalSystemCallRequest object) {
        final DBFunctionalTemplate dBDuplicateTemplate = new DBFunctionalTemplate();
        dBDuplicateTemplate.setActivityID(object.getActivityId());
        dBDuplicateTemplate.setProcessDefinitionID(object.getProcessDefinitionId());
        return this.daoImpl.getDuplicateTemplate(dBDuplicateTemplate);
    }
    
    public List<DBDuplicateCheckServiceTask> checkIfDuplicateForServiceTask(final Map<String, String> duplicateVariableMap, final ExternalSystemCallRequest object, final List<String> templateVariableSequence) {
        final DBDuplicateCheckServiceTask dBDuplicateCheckServiceTask = new DBDuplicateCheckServiceTask();
        dBDuplicateCheckServiceTask.setActivityID(object.getActivityId());
        dBDuplicateCheckServiceTask.setProcessDefinitionID(object.getProcessDefinitionId());
        dBDuplicateCheckServiceTask.setValueStored(this.formValueForDB(templateVariableSequence, duplicateVariableMap));
        return this.daoImpl.checkIfDuplicateST(dBDuplicateCheckServiceTask);
    }
    
    private String formValueForDB(final List<String> templateVariableSequence, final Map<String, String> duplicateVariableMap) {
        final StringBuilder sb = new StringBuilder();
        for (final String variableName : templateVariableSequence) {
            final int currentindex = templateVariableSequence.indexOf(templateVariableSequence);
            if (currentindex != templateVariableSequence.size() - 1) {
                sb.append(variableName).append("-").append(duplicateVariableMap.get(variableName)).append(",");
            }
            else {
                sb.append(variableName).append("-").append(duplicateVariableMap.get(variableName));
            }
        }
        return sb.toString();
    }
    //AMIT
    public void saveInDuplicateCheckForST(final Map<String, String> duplicateVariableMap, final ExternalSystemCallRequest object, final List<String> templateVariableSequence) {
    try { 
    	NotificationObject notification = new NotificationObject();
        final DBDuplicateCheckServiceTask dBDuplicateCheckServiceTask = new DBDuplicateCheckServiceTask();
        dBDuplicateCheckServiceTask.setActivityID(object.getActivityId());
        dBDuplicateCheckServiceTask.setProcessDefinitionID(object.getProcessDefinitionId());
        dBDuplicateCheckServiceTask.setValueStored(this.formValueForDB(templateVariableSequence, duplicateVariableMap));
        //AMIT updated..
        final Map<String, Object> map= (Map<String, Object>)object.getVairables();
        dBDuplicateCheckServiceTask.setWorkUnitId((String)map.get("workunitid"));
        //....end
        notification = this.daoImpl.save(notification, (Object)dBDuplicateCheckServiceTask);
        
    }catch(Exception e) {
        	logger.fatal("Exception DBDuplicateChecService[saveInDuplicateCheckForST] e:"+e.getMessage());
        	e.printStackTrace();
        }
       
    }
    //AMIT
    public void updateInDuplicateCheckForST(final Map<String, String> duplicateVariableMap, final ExternalSystemCallRequest object, 
    		final List<String> templateVariableSequence,
    		final List<DBDuplicateCheckServiceTask>duplicateRecordList) {
    try { 
    	//String duplicateWorkUnitId=null;
    	NotificationObject notification = new NotificationObject();
        final DBDuplicateCheckServiceTask dbDuplicateUpdateServiceTask = new DBDuplicateCheckServiceTask();
         //dbDuplicateUpdateServiceTask.setValueStored(this.formValueForDB(templateVariableSequence, duplicateVariableMap));
       // if(null!=duplicateRecordList.get(0).getWorkUnitId()) {
       //     duplicateWorkUnitId = duplicateRecordList.get(0).getWorkUnitId();
       //  }
        final Map<String, Object> map= (Map<String, Object>)object.getVairables();
        logger.fatal("AMIT DBDuplicateChecService[saveInDuplicateCheckForST] map:"+map);
        dbDuplicateUpdateServiceTask.setWorkUnitId((String)map.get("workunitid"));
        dbDuplicateUpdateServiceTask.setDuplicateFlag("Y");
        //....end
        
        notification = this.daoImpl.update(notification, (Object)dbDuplicateUpdateServiceTask, duplicateRecordList.get(0));
        
    }catch(Exception e) {
        	logger.fatal("Exception DBDuplicateChecService[saveInDuplicateCheckForST] e:"+e.getMessage());
        	e.printStackTrace();
        }
       
    }
    
    public Map<String, Object> checkIfMatchedFromERP(final List<String> critariaVariableForMacher, final Map<String, String> criteriaVariableMap, final List<String> selectVariableList, final Map<String, String> selectVariableMap, final ExternalSystemCallRequest object, final DBMapperTemplate returnValueFromTask) {
        final List<String> variableSequencer = new ArrayList<String>();
        final Map<String, Object> settingVariablesNextFlow = (Map<String, Object>)new HashedMap();
        final String sql = this.formSqlQuery(selectVariableMap, object, variableSequencer, critariaVariableForMacher, criteriaVariableMap, returnValueFromTask);
        final List<Object[]> returnFromMatcher = (List<Object[]>)this.daoImpl.checkIfMatchedInERPSystem(sql, (Map)selectVariableMap);
        if (null != returnFromMatcher && !returnFromMatcher.isEmpty()) {
            settingVariablesNextFlow.put(returnValueFromTask.getDecisionVarName().trim(), true);
            int i = 0;
            try {
                for (final Object[] o : returnFromMatcher) {
                    for (final String val : variableSequencer) {
                        settingVariablesNextFlow.put(val, o[i].toString());
                        ++i;
                    }
                    i = 0;
                }
            }
            catch (Exception e) {
                for (final Object o2 : returnFromMatcher) {
                    for (final String val2 : variableSequencer) {
                        settingVariablesNextFlow.put(val2, o2.toString());
                        ++i;
                    }
                    i = 0;
                }
            }
        }
        else {
            settingVariablesNextFlow.put(returnValueFromTask.getDecisionVarName().trim(), false);
        }
        return settingVariablesNextFlow;
    }
    
    private String formSqlQuery(final Map<String, String> selectValueMap, final ExternalSystemCallRequest object, final List<String> variableSequencer, final List<String> critariaVariableForMacher, final Map<String, String> criteriaVariableMap, final DBMapperTemplate returnValueFromTask) {
        final StringBuilder sb = new StringBuilder("Select ");
        for (final String key : selectValueMap.keySet()) {
            sb.append(selectValueMap.get(key));
            sb.append(", ");
            variableSequencer.add(key);
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        sb.append(" from ");
        sb.append(returnValueFromTask.getMappedTableName());
        sb.append(" where ");
        for (final String key : criteriaVariableMap.keySet()) {
            sb.append(criteriaVariableMap.get(key));
            sb.append(" = ");
            sb.append("'" + object.getVairables().get(key) + "'");
            sb.append(" and ");
        }
        sb.replace(sb.length() - 4, sb.length(), "");
        return sb.toString();
    }
    
    public DBMapperTemplate getTempalteForMapper(final ExternalSystemCallRequest object) {
        final DBMapperTemplate dBDuplicateTemplate = new DBMapperTemplate();
        dBDuplicateTemplate.setActivityID(object.getActivityId());
        dBDuplicateTemplate.setProcessDefinitionID(object.getProcessDefinitionId());
        return this.daoImpl.getMapperTemplate(dBDuplicateTemplate);
    }
}