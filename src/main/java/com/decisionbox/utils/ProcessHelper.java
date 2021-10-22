//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.utils;

import com.decisionbox.beans.VendorMaster;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class ProcessHelper {
    public ProcessHelper() {
    }

    public static Map<String, Object> fillFormFields(Map<String, Object> poMap, VendorMaster vendor, String workUnitId, File fileToAttach, String contentId) throws ParseException {
        Map<String, Object> poAndVendorMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        DateFormat taskDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        poAndVendorMap.put("accountpayable-processing", "accountpayable-processing");
        poAndVendorMap.put("taskdate", taskDateFormat.format(date));

        try {
            if (poMap.get("PONumber") != null) {
                poAndVendorMap.put("ponumber", ((TreeMap)poMap.get("PONumber")).get("Value"));
                poAndVendorMap.put("invoicetype", "PO");
                poAndVendorMap.put("podate", "N.A.");
            } else {
                poAndVendorMap.put("ponumber", "N.A.");
                poAndVendorMap.put("invoicetype", "Non PO");
                poAndVendorMap.put("podate", "N.A.");
            }
        } catch (Exception var12) {
            System.out.println("No PO number found");
            poAndVendorMap.put("ponumber", "N.A.");
            poAndVendorMap.put("invoicetype", "Non PO");
            poAndVendorMap.put("podate", "N.A.");
        }

        try {
            poAndVendorMap.put("invoiceamount", ((TreeMap)poMap.get("InvoiceAmount")).get("Value"));
        } catch (Exception var11) {
            poAndVendorMap.put("invoiceamount", "");
        }

        try {
            poAndVendorMap.put("invoicenumber", ((TreeMap)poMap.get("InvoiceNumber")).get("Value"));
        } catch (Exception var10) {
            poAndVendorMap.put("invoicenumber", "");
        }

        try {
            poAndVendorMap.put("invoicedate", ((TreeMap)poMap.get("InvoiceDate")).get("Value"));
        } catch (Exception var9) {
            poAndVendorMap.put("invoicedate", "");
        }

        poAndVendorMap.put("workunitid", workUnitId.toString());
        if (vendor != null) {
            poAndVendorMap.put("vendorid", vendor.getVendorID());
            poAndVendorMap.put("vendorname", vendor.getVendorName());
            poAndVendorMap.put("vendoremail", vendor.getWebaddress());
            poAndVendorMap.put("vendoraddress", vendor.getAddress());
            poAndVendorMap.put("city", vendor.getCity());
            poAndVendorMap.put("state", vendor.getState());
            poAndVendorMap.put("pincode", vendor.getZip());
            poAndVendorMap.put("country", vendor.getState());
        }

        poAndVendorMap.put("viewimage", contentId);
        return poAndVendorMap;
    }
}
