//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.beans;

import java.util.Map;

public class SaveFormRepresentation {
    String formDefinitionId;
    String processDefinitionId;
    String taskId;
    String processInstanceId;
    Map<String, Object> variables;

    public String getFormDefinitionId() {
        return this.formDefinitionId;
    }

    public void setFormDefinitionId(String formDefinitionId) {
        this.formDefinitionId = formDefinitionId;
    }

    public String getProcessDefinitionId() {
        return this.processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public SaveFormRepresentation(String formDefinitionId, String processDefinitionId, String taskId, String processInstanceId, Map<String, Object> variables) {
        this.formDefinitionId = formDefinitionId;
        this.processDefinitionId = processDefinitionId;
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.variables = variables;
    }

    public SaveFormRepresentation() {
        this.formDefinitionId = null;
        this.processDefinitionId = null;
        this.taskId = null;
        this.processInstanceId = null;
        this.variables = null;
    }
}
