//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package invoiceGeneratorEntitites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(
    name = "FUNCTIOANL_ROUTER"
)
public class FunctionalRouterDB {
    private long id;
    private String activityID;
    private String processDefinitionID;
    private String flowName;

    public FunctionalRouterDB() {
    }

    @Id
    @Column(
        name = "id_"
    )
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(
        name = "ACTIVITY_ID_"
    )
    public String getActivityID() {
        return this.activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    @Column(
        name = "PRCESS_DEFINITION_ID_"
    )
    public String getProcessDefinitionID() {
        return this.processDefinitionID;
    }

    public void setProcessDefinitionID(String processDefinitionID) {
        this.processDefinitionID = processDefinitionID;
    }

    @Column(
        name = "FLOW_NAME"
    )
    public String getFlowName() {
        return this.flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }
}
