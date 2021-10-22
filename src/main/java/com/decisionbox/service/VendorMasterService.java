//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.service;

import com.decisionbox.beans.VendorMaster;
import com.decisionbox.dao.VendorMasterDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("vendorMasterService")
public class VendorMasterService {
    @Autowired
    VendorMasterDao vendorMasterDao;
    @Value("${FILE_VENDOR_MASTER_FILENAME_WITH_PATH}")
    String FILE_VENDOR_MASTER_FILENAME_WITH_PATH;

    public VendorMasterService() {
    }

    public String updateVendorMasterFromCsv(Boolean isCleanUpdate) {
        return null;
    }

    public List<VendorMaster> getVendorByName(String vendorName) {
        return this.vendorMasterDao.getVendorByName(vendorName);
    }
}
