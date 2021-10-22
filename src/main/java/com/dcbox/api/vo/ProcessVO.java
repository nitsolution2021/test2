package com.dcbox.api.vo;

public class ProcessVO {

	private String processId;
	private boolean isDuplicate;
	private String processName;
	private String tenantCode;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	@Override
	public String toString() {
		return "ProcessVO [processId=" + processId + ", isDuplicate=" + isDuplicate + ", processName=" + processName
				+ ", tenantCode=" + tenantCode + "]";
	}

}
