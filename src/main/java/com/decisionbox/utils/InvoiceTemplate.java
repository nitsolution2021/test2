//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import java.awt.Rectangle;
import java.util.TreeMap;

public class InvoiceTemplate {
    TreeMap<String, Rectangle> fieldRectangleMap = new TreeMap();
    String vendorName = null;
    String templateName = null;

    public InvoiceTemplate(String templateName) {
        this.templateName = templateName;
        byte var3 = -1;
        switch(templateName.hashCode()) {
        case -1339302243:
            if (templateName.equals("Quality Mat Inc")) {
                var3 = 0;
            }
            break;
        case -1184801600:
            if (templateName.equals("Mac & Associates")) {
                var3 = 1;
            }
            break;
        case 1048139225:
            if (templateName.equals("Textile Max")) {
                var3 = 2;
            }
            break;
        case 1227851526:
            if (templateName.equals("National Label")) {
                var3 = 4;
            }
            break;
        case 2067395292:
            if (templateName.equals("National Tooling")) {
                var3 = 3;
            }
        }

        switch(var3) {
        case 0:
            this.setTemplateForQualityMat();
            break;
        case 1:
            this.setTemplateForMacAssociates();
            break;
        case 2:
            this.setTemplateForTextileMax();
            break;
        case 3:
            this.setTemplateForNationalTooling();
            break;
        case 4:
            this.setTemplateForNationalLabel();
            break;
        default:
            this.setDefaultTemplate();
        }

    }

    public void setTemplateForTextileMax() {
        this.templateName = "Textile Max";
        this.fieldRectangleMap.put("Invoice Amount", new Rectangle(2074, 2962, 366, 60));
        this.fieldRectangleMap.put("Invoice Date", new Rectangle(2178, 442, 240, 54));
        this.fieldRectangleMap.put("Invoice Number", new Rectangle(2166, 374, 258, 62));
        this.vendorName = "Textile-Max";
        this.fieldRectangleMap.put("PO Number", (Rectangle)null);
    }

    public void setTemplateForMacAssociates() {
        this.templateName = "Mac";
        this.fieldRectangleMap.put("Invoice Amount", new Rectangle(2108, 2784, 364, 88));
        this.fieldRectangleMap.put("Invoice Date", new Rectangle(2188, 216, 280, 88));
        this.fieldRectangleMap.put("Invoice Number", new Rectangle(2184, 108, 276, 120));
        this.vendorName = "Mac & Associates, Inc.";
        this.fieldRectangleMap.put("PO Number", (Rectangle)null);
    }

    public void setTemplateForQualityMat() {
        this.templateName = "Quality Mat Inc";
        this.fieldRectangleMap.put("Invoice Amount", new Rectangle(2114, 2038, 296, 51));
        this.fieldRectangleMap.put("Invoice Date", new Rectangle(1813, 188, 430, 53));
        this.fieldRectangleMap.put("Invoice Number", new Rectangle(1824, 247, 377, 44));
        this.vendorName = "Quality Mat Inc";
        this.fieldRectangleMap.put("PO Number", new Rectangle(1828, 344, 308, 51));
    }

    public void setTemplateForNationalLabel() {
        this.templateName = "NATIONA";
        this.fieldRectangleMap.put("Invoice Amount", new Rectangle(2148, 3092, 258, 54));
        this.fieldRectangleMap.put("Invoice Date", new Rectangle(2050, 240, 334, 60));
        this.fieldRectangleMap.put("Invoice Number", new Rectangle(2040, 170, 342, 78));
        this.vendorName = "National Label";
        this.fieldRectangleMap.put("PO Number", (Rectangle)null);
    }

    public void setTemplateForNationalTooling() {
        this.templateName = "National Tooling";
        this.fieldRectangleMap.put("Invoice Amount", new Rectangle(1996, 2940, 356, 70));
        this.fieldRectangleMap.put("Invoice Date", new Rectangle(1852, 418, 214, 64));
        this.fieldRectangleMap.put("Invoice Number", new Rectangle(2110, 422, 258, 70));
        this.vendorName = "National Tooling Exchange";
        this.fieldRectangleMap.put("PO Number", (Rectangle)null);
    }

    public TreeMap<String, Rectangle> getFieldRectangleMap() {
        return this.fieldRectangleMap;
    }

    public void setFieldRectangleMap(TreeMap<String, Rectangle> fieldRectangleMap) {
        this.fieldRectangleMap = fieldRectangleMap;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setDefaultTemplate() {
    }
}
