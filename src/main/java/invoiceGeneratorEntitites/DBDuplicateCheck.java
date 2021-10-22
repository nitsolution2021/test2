
package invoiceGeneratorEntitites;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "DB_DUPLICATE_CHECK")
public class DBDuplicateCheck
{
    private long id;
    private String vendorname;
    private String invoiceAmount;
    private String poNumber;
    private String invoiceNumber;
    private String processInstanceId;
    private String message;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }
    
    @Column(name = "vendor_name")
    public String getVendorname() {
        return this.vendorname;
    }
    
    public void setVendorname(final String vendorname) {
        this.vendorname = vendorname;
    }
    
    @Column(name = "po_no")
    public String getPoNumber() {
        return this.poNumber;
    }
    
    public void setPoNumber(final String poNumber) {
        this.poNumber = poNumber;
    }
    
    @Column(name = "invoice_amount")
    public String getInvoiceAmount() {
        return this.invoiceAmount;
    }
    
    public void setInvoiceAmount(final String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
    
    @Column(name = "invoice_number")
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    
    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    @Column(name = "process_instance_id")
    public String getProcessInstanceId() {
        return this.processInstanceId;
    }
    
    public void setProcessInstanceId(final String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
}