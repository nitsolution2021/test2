/**
 * AMIT modified on 11th May 2021
 *  * Decompiled
 */

package invoiceGeneratorEntitites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DB_DUPLICATE_CHECK_S_TASK")
public class DBDuplicateCheckServiceTask
{
    private long id;
    private String activityID;
    private String processDefinitionID;
    private String valueStored;
    
    //Added by AMIT
    private String workUnitId;
    private String duplicateFlag;   //Y for duplicate
    private String duplicateWorkUnitId; 
    
   
	@Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }
    
    public void setId(final long id) {
        this.id = id;
    }
    
    @Column(name = "ACTIVITY_ID_")
    public String getActivityID() {
        return this.activityID;
    }
    
    public void setActivityID(final String activityID) {
        this.activityID = activityID;
    }
    
    @Column(name = "PRCESS_DEFINITION_ID_")
    public String getProcessDefinitionID() {
        return this.processDefinitionID;
    }
    
    public void setProcessDefinitionID(final String processDefinitionID) {
        this.processDefinitionID = processDefinitionID;
    }
    
    @Column(name = "VALUE_STORED_")
    public String getValueStored() {
        return this.valueStored;
    }
    
    public void setValueStored(final String valueStored) {
        this.valueStored = valueStored;
    }
    
    //AMIT
    @Column(name = "WORK_UNIT_ID_")
    public String getWorkUnitId() {
		return workUnitId;
	}

	public void setWorkUnitId(String workUnitId) {
		this.workUnitId = workUnitId;
	}

	@Column(name = "DUPLICATE_FLAG_")
	public String getDuplicateFlag() {
		return duplicateFlag;
	}

	public void setDuplicateFlag(String duplicateFlag) {
		this.duplicateFlag = duplicateFlag;
	}

	@Column(name = "DU_WORK_UNIT_ID_")
	public String getDuplicateWorkUnitId() {
		return duplicateWorkUnitId;
	}

	public void setDuplicateWorkUnitId(String duplicateWorkUnitId) {
		this.duplicateWorkUnitId = duplicateWorkUnitId;
	}

    
}