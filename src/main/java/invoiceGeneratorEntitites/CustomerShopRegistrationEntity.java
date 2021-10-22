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
    name = "invoice_customer_registration"
)
public class CustomerShopRegistrationEntity {
    private ShopRegistrationID shopRegistrationID;
    private String name;
    private String ownerShopName;
    private String address;
    private String mobileNumber;
    private String customerEmailId;
    private String city;
    private String location;
    private String description;
    private String tags;

    public CustomerShopRegistrationEntity() {
    }

    @EmbeddedId
    public ShopRegistrationID getShopRegistrationID() {
        return this.shopRegistrationID;
    }

    public void setShopRegistrationID(ShopRegistrationID shopRegistrationID) {
        this.shopRegistrationID = shopRegistrationID;
    }

    @Column(
        name = "name"
    )
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(
        name = "shop_owner_name"
    )
    public String getOwnerShopName() {
        return this.ownerShopName;
    }

    public void setOwnerShopName(String ownerShopName) {
        this.ownerShopName = ownerShopName;
    }

    @Column(
        name = "address"
    )
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(
        name = "mobile"
    )
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(
        name = "email_id"
    )
    public String getCustomerEmailId() {
        return this.customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    @Column(
        name = "city"
    )
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(
        name = "location"
    )
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(
        name = "description"
    )
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(
        name = "tags"
    )
    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
