package com.cloud.cang.rec.ws.entity;

public class RequestEntity {
	private String buyerID;
	  private String appName;
	  private String appVersion;
	  private String requestContent;
	  private String requestType;
	  private String licenseType;
	  private String requestAmount;
	  private String requestDays;
	  private String allocalTable;
	  
	  public String getBuyerID()
	  {
	    return this.buyerID;
	  }
	  
	  public void setBuyerID(String buyerID)
	  {
	    this.buyerID = buyerID;
	  }
	  
	  public String getAppName()
	  {
	    return this.appName;
	  }
	  
	  public void setAppName(String appName)
	  {
	    this.appName = appName;
	  }
	  
	  public String getAppVersion()
	  {
	    return this.appVersion;
	  }
	  
	  public void setAppVersion(String appVersion)
	  {
	    this.appVersion = appVersion;
	  }
	  
	  public String getRequestContent()
	  {
	    return this.requestContent;
	  }
	  
	  public void setRequestContent(String requestContent)
	  {
	    this.requestContent = requestContent;
	  }
	  
	  public String getRequestType()
	  {
	    return this.requestType;
	  }
	  
	  public void setRequestType(String requestType)
	  {
	    this.requestType = requestType;
	  }
	  
	  public String getLicenseType()
	  {
	    return this.licenseType;
	  }
	  
	  public void setLicenseType(String licenseType)
	  {
	    this.licenseType = licenseType;
	  }
	  
	  public String getRequestAmount()
	  {
	    return this.requestAmount;
	  }
	  
	  public void setRequestAmount(String requestAmount)
	  {
	    this.requestAmount = requestAmount;
	  }
	  
	  public String getRequestDays()
	  {
	    return this.requestDays;
	  }
	  
	  public void setRequestDays(String requestDays)
	  {
	    this.requestDays = requestDays;
	  }
	  
	  public String getAllocalTable()
	  {
	    return this.allocalTable;
	  }
	  
	  public void setAllocalTable(String allocalTable)
	  {
	    this.allocalTable = allocalTable;
	  }
}
