/**
 * Modified by AMIT on 15th May 2021
 *  * Decompiled
 */

package daoimpl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import dao.DBDuplicateCheckDao;
import hibernate.HibernateUtil;
import invoiceGeneratorEntitites.DBDuplicateCheck;
import invoiceGeneratorEntitites.DBDuplicateCheckServiceTask;
import invoiceGeneratorEntitites.DBFunctionalTemplate;
import invoiceGeneratorEntitites.DBMapperTemplate;
import invoiceGeneratorEntitites.FunctionalRouterDB;
import notification.NotificationObject;

public class DBDuplicateCheckDaoImpl implements  DBDuplicateCheckDao
{
    static final Logger logger;
     
    public DBDuplicateCheck getRecordForProcessInstance(final DBDuplicateCheck dBDuplicateCheckRequest) throws Exception {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside DBDuplicateCheckDaoImpl()");
        Session session = null;
        List<DBDuplicateCheck> dBDuplicateCheck = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)DBDuplicateCheck.class);
            final Criterion c1 = (Criterion)Restrictions.eq("processInstanceId", (Object)dBDuplicateCheckRequest.getProcessInstanceId());
            criteria.add(c1);
            dBDuplicateCheck = (List<DBDuplicateCheck>)criteria.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        if (dBDuplicateCheck.isEmpty()) {
            return null;
        }
        return dBDuplicateCheck.get(0);
    }
    
    public <T> NotificationObject save(final NotificationObject notification, final Object o) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside Save()");
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            notification.setStatus("SUCCESS");
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            notification.setStatus("Failure");
            notification.setMessage(e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        return notification;
    }
    /*Updating table DB_DUPLICATE_CHECK_S_TASK for duplicate work unit id. (AMIT)
     * (non-Javadoc)
     * @see dao.DBDuplicateCheckDao#update(notification.NotificationObject, java.lang.Object, invoiceGeneratorEntitites.DBDuplicateCheckServiceTask)
     */
    public <T> NotificationObject update(
    		final NotificationObject notification,
    		final Object o,
    		DBDuplicateCheckServiceTask duplicateRecord) {
    	
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside Update()");
        Session session = null;
        try {
        	session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            DBDuplicateCheckServiceTask obj= (DBDuplicateCheckServiceTask)o;
            logger.debug("workUnitId:"+obj.getWorkUnitId()+", duplicateWorkUnitId:"+duplicateRecord.getWorkUnitId() +"ID:"+duplicateRecord.getId());
            DBDuplicateCheckServiceTask updateObj = 
            		(DBDuplicateCheckServiceTask)session.get(DBDuplicateCheckServiceTask.class, duplicateRecord.getId() );
            updateObj.setDuplicateWorkUnitId(obj.getWorkUnitId());
            updateObj.setDuplicateFlag("Y");
            session.merge(updateObj);
            session.getTransaction().commit();
            notification.setStatus("SUCCESS");
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.fatal("Exception: "+(Object)e.getMessage());
            notification.setStatus("Failure");
            notification.setMessage(e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.fatal((Object)"Session was already closed");
            }
            e.printStackTrace();
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.fatal((Object)" Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)" Exit Save()");
        return notification;
    }
    
    
    public DBDuplicateCheck checkDuplicateValues(final DBDuplicateCheck dbObject) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getExistingOwnerDetails()");
        Session session = null;
        List<DBDuplicateCheck> dBDuplicateCheck = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)DBDuplicateCheck.class);
            final Criterion c1 = (Criterion)Restrictions.eq("vendorname", (Object)dbObject.getVendorname());
            final Criterion c2 = (Criterion)Restrictions.eq("invoiceAmount", (Object)dbObject.getInvoiceAmount());
            final Criterion c3 = (Criterion)Restrictions.eq("poNumber", (Object)dbObject.getPoNumber());
            final Criterion c4 = (Criterion)Restrictions.eq("invoiceNumber", (Object)dbObject.getInvoiceNumber());
            criteria.add((Criterion)Restrictions.and(new Criterion[] { c1, c2, c3, c4 }));
            dBDuplicateCheck = (List<DBDuplicateCheck>)criteria.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        if (dBDuplicateCheck.isEmpty()) {
            return null;
        }
        return dBDuplicateCheck.get(0);
    }
    
    public String getProcessinstanceID(final String taskId) {
        String sql = "select PROC_INST_ID_ as instanceid from ACT_HI_TASKINST where id_ = ";
        sql = sql + "'" + taskId + "' ";
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getExistingOwnerDetails()");
        Session session = null;
        String processInstanceId = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final SQLQuery query = session.createSQLQuery(sql).addScalar("instanceid", (Type)new StringType());
            final List<String> rows = (List<String>)query.list();
            final Iterator<String> iterator = rows.iterator();
            while (iterator.hasNext()) {
                final String row = processInstanceId = iterator.next();
            }
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        return processInstanceId;
    }
    
    public String deriveFunctionalIndicator(final FunctionalRouterDB functionalRouterDB) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getExistingOwnerDetails()");
        Session session = null;
        List<FunctionalRouterDB> listFunctionalRouterDB = null;
        String functionalityName = "";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)FunctionalRouterDB.class);
            final Criterion c1 = (Criterion)Restrictions.eq("activityID", (Object)functionalRouterDB.getActivityID());
            criteria.add(c1);
            listFunctionalRouterDB = (List<FunctionalRouterDB>)criteria.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        if (!listFunctionalRouterDB.isEmpty()) {
            functionalityName = listFunctionalRouterDB.get(0).getFlowName();
        }
        return functionalityName;
    }
    
    public DBFunctionalTemplate getDuplicateTemplate(final DBFunctionalTemplate dBDuplicateTemplate) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getDuplicateTemplate()");
        Session session = null;
        List<DBFunctionalTemplate> listFunctionalRouterDB = null;
        final String functionalityName = "";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)DBFunctionalTemplate.class);
            final Criterion c1 = (Criterion)Restrictions.eq("activityID", (Object)dBDuplicateTemplate.getActivityID());
            final Criterion c2 = (Criterion)Restrictions.eq("processDefinitionID", (Object)dBDuplicateTemplate.getProcessDefinitionID());
            criteria.add((Criterion)Restrictions.and(c1, c2));
            listFunctionalRouterDB = (List<DBFunctionalTemplate>)criteria.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        return listFunctionalRouterDB.get(0);
    }
    
    public List<DBDuplicateCheckServiceTask> checkIfDuplicateST(final DBDuplicateCheckServiceTask dBDuplicateCheckServiceTask) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getDuplicateTemplate()");
        Session session = null;
        List<DBDuplicateCheckServiceTask> listFunctionalRouterDB = null;
        boolean isDuplicate = true;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)DBDuplicateCheckServiceTask.class);
            final Criterion c1 = (Criterion)Restrictions.eq("activityID", (Object)dBDuplicateCheckServiceTask.getActivityID());
            final Criterion c2 = (Criterion)Restrictions.eq("processDefinitionID", (Object)dBDuplicateCheckServiceTask.getProcessDefinitionID());
            final Criterion c3 = (Criterion)Restrictions.eq("valueStored", (Object)dBDuplicateCheckServiceTask.getValueStored());
            criteria.add((Criterion)Restrictions.and(new Criterion[] { c1, c2, c3 }));
            listFunctionalRouterDB = (List<DBDuplicateCheckServiceTask>)criteria.list();
            logger.info("Amit Duplicate List:  "+listFunctionalRouterDB);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
       if (listFunctionalRouterDB.isEmpty()) {
            isDuplicate = false;
        }
        
        return listFunctionalRouterDB;
    }
    
    public List<Object[]> checkIfMatchedInERPSystem(final String sql, final Map<String, String> mapForMatherKeyValue) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getExistingOwnerDetails()");
        Session session = null;
        final String processInstanceId = null;
        List<Object[]> rows = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final SQLQuery query = session.createSQLQuery(sql);
            rows = (List<Object[]>)query.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Exit Save()");
        return rows;
    }
    
    public DBMapperTemplate getMapperTemplate(final DBMapperTemplate dBDuplicateTemplate) {
        DBDuplicateCheckDaoImpl.logger.debug((Object)"Inside getDuplicateTemplate()");
        Session session = null;
        List<DBMapperTemplate> listFunctionalRouterDB = null;
        final String functionalityName = "";
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            final Criteria criteria = session.createCriteria((Class)DBMapperTemplate.class);
            final Criterion c1 = (Criterion)Restrictions.eq("activityID", (Object)dBDuplicateTemplate.getActivityID());
            final Criterion c2 = (Criterion)Restrictions.eq("processDefinitionID", (Object)dBDuplicateTemplate.getProcessDefinitionID());
            criteria.add((Criterion)Restrictions.and(c1, c2));
            listFunctionalRouterDB = (List<DBMapperTemplate>)criteria.list();
            session.getTransaction().commit();
        }
        catch (Exception e) {
            DBDuplicateCheckDaoImpl.logger.debug((Object)e.getMessage());
            try {
                session.close();
            }
            catch (HibernateException e2) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        finally {
            try {
                session.close();
            }
            catch (HibernateException e3) {
                DBDuplicateCheckDaoImpl.logger.debug((Object)"Session was already closed");
            }
        }
        return listFunctionalRouterDB.get(0);
    }
    
    public static void main(String[] args) {
		
	}
    
    static {
        logger = Logger.getLogger((Class)DBDuplicateCheckDaoImpl.class);
    }
}