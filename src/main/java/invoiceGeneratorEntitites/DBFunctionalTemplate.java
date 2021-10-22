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
    name = "DB_FUNCTIONAL_TEMPLATE"
)
public class DBFunctionalTemplate {
    private long id;
    private String activityID;
    private String processDefinitionID;
    private String templateMetaData;
    private String returnVariableName;

    public DBFunctionalTemplate() {
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
        name = "TEMPLATE_METADATA_"
    )
    public String getTemplateMetaData() {
        return this.templateMetaData;
    }

    public void setTemplateMetaData(String templateMetaData) {
        this.templateMetaData = templateMetaData;
    }

    @Column(
        name = "RETURN_VAR_NAME_"
    )
    public String getReturnVariableName() {
        return this.returnVariableName;
    }

    public void setReturnVariableName(String returnVariableName) {
        this.returnVariableName = returnVariableName;
    }
}
