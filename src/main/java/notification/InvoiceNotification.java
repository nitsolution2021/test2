//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package notification;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class InvoiceNotification {
    static final Logger logger = Logger.getLogger(InvoiceNotification.class);
    private String status;
    private String message;
    private static volatile List<InvoiceNotification> notificationPool;

    private InvoiceNotification() {
    }

    private static void createNewObject() {
        try {
            logger.debug("Inside InvoiceNotification");
            if (null == notificationPool) {
                notificationPool = new ArrayList();
            }

            InvoiceNotification invoiceNotification = new InvoiceNotification();
            notificationPool.add(invoiceNotification);
        } catch (Exception var1) {
            logger.debug(var1.getMessage());
        }

    }

    public static synchronized InvoiceNotification getInstance() {
        try {
            if (null == notificationPool || !notificationPool.isEmpty()) {
                createNewObject();
            }
        } catch (Exception var1) {
            logger.debug(var1.getMessage());
        }

        return (InvoiceNotification)notificationPool.get(0);
    }

    public static synchronized void release(InvoiceNotification invoiceNotification) {
        try {
            notificationPool.add(invoiceNotification);
        } catch (Exception var2) {
            logger.debug(var2.getMessage());
        }

    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
