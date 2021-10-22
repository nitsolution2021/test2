//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.service;

import com.decisionbox.dao.AccountsPayableDao;
import com.decisionbox.jpa.entity.WorkUnitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountsPayableService")
public class AccountsPayableService {
    @Autowired
    AccountsPayableDao accountsPayableDao;

    public AccountsPayableService() {
    }

    public WorkUnitEntity createWorkUnit(String piId, int i) {
        WorkUnitEntity wue = this.accountsPayableDao.createWorkUnit(piId, i);
        return wue;
    }
}
