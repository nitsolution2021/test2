//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.decisionbox.beans;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;

@JsonAutoDetect(
    fieldVisibility = Visibility.ANY
)
public class ContentResponse {
    String id;
    String name;
    @JsonIgnore
    String mimeType;
    @JsonIgnore
    String taskId;
    @JsonIgnore
    String processInstanceId;
    @JsonIgnore
    String contentStoreId;
    @JsonIgnore
    String contentStoreName;
    @JsonIgnore
    String contentAvailable;
    @JsonIgnore
    String tenantId;
    @JsonIgnore
    String created;
    @JsonIgnore
    String createdBy;
    @JsonIgnore
    String lastModified;
    @JsonIgnore
    String lastModifiedBy;
    @JsonIgnore
    String url;
    @JsonIgnore
    String simpleType;

    public ContentResponse(String id, String name, String mimeType, String taskId, String processInstanceId, String contentStoreId, String contentStoreName, String contentAvailable, String tenantId, String created, String createdBy, String lastModified, String lastModifiedBy, String url) {
        this.id = id;
        this.name = name;
        this.mimeType = mimeType;
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.contentStoreId = contentStoreId;
        this.contentStoreName = contentStoreName;
        this.contentAvailable = contentAvailable;
        this.tenantId = tenantId;
        this.created = created;
        this.createdBy = createdBy;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.url = url;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentResponse() {
    }
}
