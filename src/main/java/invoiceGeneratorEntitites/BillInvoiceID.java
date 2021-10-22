//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package invoiceGeneratorEntitites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BillInvoiceID implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long billInvoiceNumber;
    private Long ownerShopId;
    private Long custShopId;

    public BillInvoiceID() {
    }

    @Column(
        name = "bill_invoice"
    )
    public Long getBillInvoiceNumber() {
        return this.billInvoiceNumber;
    }

    public void setBillInvoiceNumber(Long billInvoiceNumber) {
        this.billInvoiceNumber = billInvoiceNumber;
    }

    @Column(
        name = "owner_shop_id"
    )
    public Long getOwnerShopId() {
        return this.ownerShopId;
    }

    public void setOwnerShopId(Long ownerShopId) {
        this.ownerShopId = ownerShopId;
    }

    @Column(
        name = "customer_shop_id"
    )
    public Long getCustShopId() {
        return this.custShopId;
    }

    public void setCustShopId(Long custShopId) {
        this.custShopId = custShopId;
    }
}
