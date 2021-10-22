//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.dao;

import com.decisionbox.beans.VendorMaster;
import com.decisionbox.jpa.entity.VendorMasterEntity;
import com.decisionbox.jpa.repository.VendorMasterRepo;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("vendorMasterDao")
public class VendorMasterDao {
    @Autowired
    DataSource dataSource;
    @Autowired
    VendorMasterRepo vendorMasterRepo;

    public VendorMasterDao() {
    }

    @Transactional
    public void saveVendors(List<VendorMasterEntity> vendorMasterList) {
        System.out.println("Saving VemdorMAster..");
        if (!vendorMasterList.isEmpty()) {
            this.vendorMasterRepo.saveAll(vendorMasterList);
        }

    }

    @Transactional
    public void deleteAllVendorMasters() {
        System.out.println("Deleting all vendors..");
        this.vendorMasterRepo.deleteAll();
    }

    @Transactional
    public void saveVendor(VendorMasterEntity vendor) {
        System.out.println("Deleting all..");
        this.vendorMasterRepo.save(vendor);
    }

    public List<VendorMaster> getVendorByName(String vendorName) {
        List<VendorMaster> vendorMasters = new ArrayList();
        System.out.println("fetching info for vendor:." + vendorName);
        List<VendorMasterEntity> vendorEntiryList = this.vendorMasterRepo.findByVendorNameIgnoreCase(vendorName);
        if (!vendorEntiryList.isEmpty()) {
            for(int i = 0; i < vendorEntiryList.size(); ++i) {
                VendorMaster target = new VendorMaster();

                try {
                    BeanUtils.copyProperties(target, vendorEntiryList.get(i));
                    vendorMasters.add(target);
                } catch (InvocationTargetException | IllegalAccessException var7) {
                    var7.printStackTrace();
                }
            }
        }

        return vendorMasters;
    }
}
