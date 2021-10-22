//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package hibernate;

import hibernateEntryProgram.HibernateMainProgrm;
import invoiceGeneratorEntitites.DBDuplicateCheckServiceTask;
import invoiceGeneratorEntitites.DBFunctionalTemplate;
import invoiceGeneratorEntitites.DBMapperTemplate;
import invoiceGeneratorEntitites.FunctionalRouterDB;
import java.io.ObjectStreamException;
import java.io.Serializable;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil implements Serializable {
    static final Logger logger = Logger.getLogger(HibernateMainProgrm.class);
    private static final long serialVersionUID = 1L;
    private static volatile SessionFactory sessionFactory;

    private HibernateUtil() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = (new Configuration()).configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(FunctionalRouterDB.class);
                configuration.addAnnotatedClass(DBFunctionalTemplate.class);
                configuration.addAnnotatedClass(DBDuplicateCheckServiceTask.class);
                configuration.addAnnotatedClass(DBMapperTemplate.class);
                configuration.configure();
                (new SchemaExport(configuration)).drop(false, false);
                ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
                registry.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception var4) {
                logger.error("Error Occured while Saving : " + var4.getMessage());
            }

        } else {
            throw new RuntimeException("You can not create new  instance of Sessoin Factory");
        }
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private Object readResolve() throws ObjectStreamException {
        if (logger.isDebugEnabled()) {
            logger.debug("Overriding Default Serialization Method");
        }

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Class var0 = HibernateUtil.class;
            synchronized(HibernateUtil.class) {
                if (sessionFactory == null) {
                    new HibernateUtil();
                }
            }
        }

        return sessionFactory;
    }
}
