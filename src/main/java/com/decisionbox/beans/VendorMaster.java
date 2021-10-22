//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.beans;

public class VendorMaster extends CsvBean {
    private Integer id;
    private String vendorName;
    private String VendorID;
    private String Address;
    private String State;
    private String Zip;
    private String City;
    private String webaddress;
    private String phone;

    public VendorMaster() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorID() {
        return this.VendorID;
    }

    public void setVendorID(String vendorID) {
        this.VendorID = vendorID;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getState() {
        return this.State;
    }

    public void setState(String state) {
        this.State = state;
    }

    public String getZip() {
        return this.Zip;
    }

    public void setZip(String zip) {
        this.Zip = zip;
    }

    public String getCity() {
        return this.City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getWebaddress() {
        return this.webaddress;
    }

    public void setWebaddress(String webaddress) {
        this.webaddress = webaddress;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "VendorMaster [vendorName=" + this.vendorName + ", VendorID=" + this.VendorID + "]";
    }
}
