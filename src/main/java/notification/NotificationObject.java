//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package notification;

import org.apache.log4j.Logger;

public class NotificationObject {
    static final Logger logger = Logger.getLogger(InvoiceNotification.class);
    private String status;
    private String message;

    public NotificationObject() {
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
