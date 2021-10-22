package invoicegeneratorwebservice;

import java.util.Map;

public class ExternalSystemCallRequest
{

    private String activityId;
    private String activityName;
    private String parentId;
    private String processDefinitionId;
    private String processinstanceId;
    private String rootProcessInstanceId;
    private Map vairables;

    public ExternalSystemCallRequest()
    {
    }

    public String getActivityId()
    {
        return activityId;
    }

    public void setActivityId(String activityId)
    {
        this.activityId = activityId;
    }

    public String getActivityName()
    {
        return activityName;
    }

    public void setActivityName(String activityName)
    {
        this.activityName = activityName;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getProcessDefinitionId()
    {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId)
    {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessinstanceId()
    {
        return processinstanceId;
    }

    public void setProcessinstanceId(String processinstanceId)
    {
        this.processinstanceId = processinstanceId;
    }

    public String getRootProcessInstanceId()
    {
        return rootProcessInstanceId;
    }

    public void setRootProcessInstanceId(String rootProcessInstanceId)
    {
        this.rootProcessInstanceId = rootProcessInstanceId;
    }

    public Map getVairables()
    {
        return vairables;
    }

    public void setVairables(Map vairables)
    {
        this.vairables = vairables;
    }
}
