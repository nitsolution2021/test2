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
    name = "DB_MAPPER_TEMPLATE"
)
public class DBMapperTemplate {
    private long id;
    private String activityID;
    private String processDefinitionID;
    private String fetchVariable;
    private String criteriaMappingVariable;
    private String decisionVarName;
    private String mappedTableName;

    public DBMapperTemplate() {
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
        name = "FETCH_VARIABLE_"
    )
    public String getFetchVariable() {
        return this.fetchVariable;
    }

    public void setFetchVariable(String fetchVariable) {
        this.fetchVariable = fetchVariable;
    }

    @Column(
        name = "CRITERIA_MAPPING_VARIABLE_"
    )
    public String getCriteriaMappingVariable() {
        return this.criteriaMappingVariable;
    }

    public void setCriteriaMappingVariable(String criteriaMappingVariable) {
        this.criteriaMappingVariable = criteriaMappingVariable;
    }

    @Column(
        name = "DECISION_VARIABLE_NAME_"
    )
    public String getDecisionVarName() {
        return this.decisionVarName;
    }

    public void setDecisionVarName(String decisionVarName) {
        this.decisionVarName = decisionVarName;
    }

    @Column(
        name = "EXTERNAL_TABLE_NAME_"
    )
    public String getMappedTableName() {
        return this.mappedTableName;
    }

    public void setMappedTableName(String mappedTableName) {
        this.mappedTableName = mappedTableName;
    }
}
