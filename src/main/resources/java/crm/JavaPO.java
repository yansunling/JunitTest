package com.junit.po;

public class JavaPO {
	private String apiName;
	private String apiUrl;
	private String paramBean;
	private String responseBean;
	
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getParamBean() {
		return paramBean;
	}
	public void setParamBean(String paramBean) {
		this.paramBean = paramBean;
	}
	public String getResponseBean() {
		return responseBean;
	}
	public void setResponseBean(String responseBean) {
		this.responseBean = responseBean;
	}
	@Override
	public String toString() {
		return "ApiBean [apiName=" + apiName + ", apiUrl=" + apiUrl + ", paramBean=" + paramBean + ", responseBean="
				+ responseBean + "]";
	}
	
	
	
}
