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
    name = "TRN_WORKUNIT"
)
public class WorkUnitEntity {
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
    private String ProcessInstanceId;
    @Column(
        name = "created_datetime"
    )
    private Date createDateTime;
    @Column(
        name = "workunitid"
    )
    private String workunitid;

    public WorkUnitEntity() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return this.ProcessInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.ProcessInstanceId = processInstanceId;
    }

    public Date getCreateDateTime() {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getWorkunitId() {
        return this.workunitid;
    }

    public void setWorkunitId(String workunitid) {
        this.workunitid = workunitid;
    }
}
