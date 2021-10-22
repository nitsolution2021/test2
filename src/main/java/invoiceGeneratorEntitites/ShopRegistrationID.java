//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package invoiceGeneratorEntitites;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShopRegistrationID implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long ownerShopId;
    private Long customerShopId;

    public ShopRegistrationID() {
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
    public Long getCustomerShopId() {
        return this.customerShopId;
    }

    public void setCustomerShopId(Long customerShopId) {
        this.customerShopId = customerShopId;
    }
}
