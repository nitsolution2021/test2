//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.jpa.repository;

import com.decisionbox.jpa.entity.VendorMasterEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface VendorMasterRepo extends CrudRepository<VendorMasterEntity, Integer> {
    List<VendorMasterEntity> findByVendorNameIgnoreCase(String var1);
}
