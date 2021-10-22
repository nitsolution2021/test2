//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.dao;

import com.decisionbox.jpa.entity.TableData;
import com.decisionbox.jpa.repository.LineItemRepo;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("lineItemDAO")
public class LineItemDAO {
    @Autowired
    LineItemRepo lineItemRepo;

    public LineItemDAO() {
    }

    @Transactional
    public void saveLineItem(TableData tabledata) {
        this.lineItemRepo.save(tabledata);
    }

    public TableData fetchLineItemByProcId(String procid) {
        try {
            return this.lineItemRepo.findByProcessInstanceId(procid);
        } catch (Exception var3) {
            System.out.println("Table Data Not Found, Returning null from DAO");
            return null;
        }
    }

    public TableData updateLineItemByProcId(String procid, String newData) {
        try {
            TableData td = this.lineItemRepo.findByProcessInstanceId(procid);
            if (td != null) {
                td.setTableDataJson(newData);
                this.lineItemRepo.save(td);
                return td;
            } else {
                System.out.println("No table data");
                return null;
            }
        } catch (Exception var4) {
            System.out.println("Exception in updating line item");
            return null;
        }
    }

    public TableData saveNewLineItem(String proc_id, String workunitId, String text) {
        Date date = new Date();

        try {
            TableData td = new TableData(proc_id, date, workunitId, text);
            this.lineItemRepo.save(td);
            return td;
        } catch (Exception var6) {
            System.out.println("Failed to save new TableData");
            return null;
        }
    }
}
