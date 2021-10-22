//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package invoiceGeneratorEntitites;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "invoice_bill_overview"
)
public class BillOverviewEntity {
    private BillInvoiceID billInvoiceID;
    private Integer totalQuantity;
    private Double totalCost;
    private Double totalTax;
    private Double surcharge;
    private Integer finalCost;
    private Double totalOverallCost;
    private Double amountDue;
    private String billType;

    public BillOverviewEntity() {
    }

    @EmbeddedId
    public BillInvoiceID getBillInvoiceID() {
        return this.billInvoiceID;
    }

    public void setBillInvoiceID(BillInvoiceID billInvoiceID) {
        this.billInvoiceID = billInvoiceID;
    }

    @Column(
        name = "total_Quantity"
    )
    public Integer getTotalQuantity() {
        return this.totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Column(
        name = "total_cost"
    )
    public Double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    @Column(
        name = "total_tax"
    )
    public Double getTotalTax() {
        return this.totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    @Column(
        name = "surcharge"
    )
    public Double getSurcharge() {
        return this.surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
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

    @Column(
        name = "amount_due"
    )
    public Double getAmountDue() {
        return this.amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    @Column(
        name = "bill_type"
    )
    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    @Column(
        name = "total_overall_cost"
    )
    public Double getTotalOverallCost() {
        return this.totalOverallCost;
    }

    public void setTotalOverallCost(Double totalOverallCost) {
        this.totalOverallCost = totalOverallCost;
    }
}
