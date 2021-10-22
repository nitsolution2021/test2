/**
 * AMIT modified on 11th May 2021
 * Decompiled
 */

package dao;

import invoiceGeneratorEntitites.DBMapperTemplate;
import java.util.List;
import java.util.Map;
import invoiceGeneratorEntitites.DBDuplicateCheckServiceTask;
import invoiceGeneratorEntitites.DBFunctionalTemplate;
import invoiceGeneratorEntitites.FunctionalRouterDB;
import invoiceGeneratorEntitites.DBDuplicateCheck;
import notification.NotificationObject;

public interface DBDuplicateCheckDao 
{
    <T> NotificationObject save(final NotificationObject p0, final Object p1);
    
    //Added by Amit
    <T> NotificationObject update(final NotificationObject p0, final Object p1,DBDuplicateCheckServiceTask duplicateRecord);
    
    DBDuplicateCheck getRecordForProcessInstance(final DBDuplicateCheck p0) throws Exception;
    
    DBDuplicateCheck checkDuplicateValues(final DBDuplicateCheck p0);
    
    String getProcessinstanceID(final String p0);
    
    String deriveFunctionalIndicator(final FunctionalRouterDB p0);
    
    DBFunctionalTemplate getDuplicateTemplate(final DBFunctionalTemplate p0);
    
    List<DBDuplicateCheckServiceTask> checkIfDuplicateST(final DBDuplicateCheckServiceTask p0);
    
    List<Object[]> checkIfMatchedInERPSystem(final String p0, final Map<String, String> p1);
    
    DBMapperTemplate getMapperTemplate(final DBMapperTemplate p0);
}