//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package invoiceGeneratorEntitites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "invoice_bill_invoice"
)
public class BillInvoiceEntity {
    private long sequence;
    private Long billInvoiceNumber;
    private Long ownerShopId;
    private Long custShopId;
    private String itemName;
    private String type;
    private Integer quantity;
    private Double cost;
    private Double tax;
    private Integer finalCost;

    public BillInvoiceEntity() {
    }

    @Id
    @Column(
        name = "sequence"
    )
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    public long getSequence() {
        return this.sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
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

    @Column(
        name = "item"
    )
    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(
        name = "type"
    )
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(
        name = "quantity"
    )
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(
        name = "cost"
    )
    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Column(
        name = "tax"
    )
    public Double getTax() {
        return this.tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    @Column(
        name = "final_cost"
    )
    public Integer getFinalCost() {
        return this.finalCost;
    }

    public void setFinalCost(Integer finalCost) {
        this.finalCost = finalCost;
    }
}
