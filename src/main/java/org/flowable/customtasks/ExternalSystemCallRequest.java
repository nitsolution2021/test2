
package org.flowable.customtasks;

import java.util.Map;

public class ExternalSystemCallRequest
{
    private String activityId;
    private String activityName;
    private String parentId;
    private String processDefinitionId;
    private String processinstanceId;
    private String rootProcessInstanceId;
    private Map<String, Object> vairables;
    
    public String getActivityId() {
        return this.activityId;
    }
    
    public void setActivityId(final String activityId) {
        this.activityId = activityId;
    }
    
    public String getActivityName() {
        return this.activityName;
    }
    
    public void setActivityName(final String activityName) {
        this.activityName = activityName;
    }
    
    public String getParentId() {
        return this.parentId;
    }
    
    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }
    
    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }
    
    public void setProcessDefinitionId(final String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }
    
    public String getProcessinstanceId() {
        return this.processinstanceId;
    }
    
    public void setProcessinstanceId(final String processinstanceId) {
        this.processinstanceId = processinstanceId;
    }
    
    public String getRootProcessInstanceId() {
        return this.rootProcessInstanceId;
    }
    
    public void setRootProcessInstanceId(final String rootProcessInstanceId) {
        this.rootProcessInstanceId = rootProcessInstanceId;
    }
    
    public Map<String, Object> getVairables() {
        return this.vairables;
    }
    
    public void setVairables(final Map<String, Object> vairables) {
        this.vairables = vairables;
    }
}