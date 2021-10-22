//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.dao;

import com.decisionbox.jpa.entity.WorkUnitEntity;
import com.decisionbox.jpa.repository.WorkUnitRepository;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountsPayableDao {
    @Autowired
    WorkUnitRepository workUnitRepository;
    @Autowired
    DataSource dataSource;
    DateFormat targetDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public AccountsPayableDao() {
    }

    public WorkUnitEntity createWorkUnit(String piId, int i) {
        WorkUnitEntity wui = new WorkUnitEntity();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        wui.setProcessInstanceId(piId);
        wui.setCreateDateTime(timestamp);
        wui.setWorkunitId("wu-" + this.targetDateFormat.format(date) + "-" + i);
        wui = (WorkUnitEntity)this.workUnitRepository.save(wui);
        return wui;
    }
}
