//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.jpa.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
    name = "ACT_FO_LINE_ITEM"
)
public class TableData {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY,
        generator = "native"
    )
    @GenericGenerator(
        name = "native",
        strategy = "native"
    )
    @Column(
        name = "id"
    )
    private Integer id;
    @Column(
        name = "proc_inst_id"
    )
    private String processInstanceId;
    @Column(
        name = "created_datetime"
    )
    private Date createDateTime;
    @Column(
        name = "work_unit_id"
    )
    private String workunitid;
    @Column(
        name = "table_data_json"
    )
    private String tableDataJson;

    public TableData() {
        this.processInstanceId = null;
        this.createDateTime = null;
        this.workunitid = null;
        this.tableDataJson = null;
    }

    public TableData(String processInstanceId, Date createDateTime, String workunitid, String tableDataJson) {
        this.processInstanceId = processInstanceId;
        this.createDateTime = createDateTime;
        this.workunitid = workunitid;
        this.tableDataJson = tableDataJson;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public TableData(Integer id, String processInstanceId, Date createDateTime, String workunitid, String tableDataJson) {
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.createDateTime = createDateTime;
        this.workunitid = workunitid;
        this.tableDataJson = tableDataJson;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getWorkunitid() {
        return this.workunitid;
    }

    public void setWorkunitid(String workunitid) {
        this.workunitid = workunitid;
    }

    public String getTableDataJson() {
        return this.tableDataJson;
    }

    public void setTableDataJson(String tableDataJson) {
        this.tableDataJson = tableDataJson;
    }
}
