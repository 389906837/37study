package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 权限码管理(HY_MBR_PURVIEW) **/
public class MbrPurview extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	private String id;
	/*主键*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 添加日期 */
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 权限说明 */
	private String sdescription;
	
	public String getSdescription(){
		return sdescription;
	}
	
	public void setSdescription(String sdescription){
		this.sdescription= sdescription;
	}
	/* 简拼名 */
	private String sjpName;
	
	public String getSjpName(){
		return sjpName;
	}
	
	public void setSjpName(String sjpName){
		this.sjpName= sjpName;
	}
	/* 权限码:根据用户登录时判断页面的操作时否显示 */
	private String spurCode;
	
	public String getSpurCode(){
		return spurCode;
	}
	
	public void setSpurCode(String spurCode){
		this.spurCode= spurCode;
	}
	/* 权限名称 */
	private String spurName;
	
	public String getSpurName(){
		return spurName;
	}
	
	public void setSpurName(String spurName){
		this.spurName= spurName;
	}
	/* 权限编号 */
	private String spurNo;
	
	public String getSpurNo(){
		return spurNo;
	}
	
	public void setSpurNo(String spurNo){
		this.spurNo= spurNo;
	}
	/* 全拼名 */
	private String spyName;
	
	public String getSpyName(){
		return spyName;
	}
	
	public void setSpyName(String spyName){
		this.spyName= spyName;
	}
	/* 系统代码:来源数据字典 */
	private String ssysCode;
	
	public String getSsysCode(){
		return ssysCode;
	}
	
	public void setSsysCode(String ssysCode){
		this.ssysCode= ssysCode;
	}
	/* 系统名称 */
	private String ssysName;
	
	public String getSsysName(){
		return ssysName;
	}
	
	public void setSsysName(String ssysName){
		this.ssysName= ssysName;
	}
	/* 系统分类
            10：pc
            20：wap */
	private Integer ssysType;
	
	public Integer getSsysType(){
		return ssysType;
	}
	
	public void setSsysType(Integer ssysType){
		this.ssysType= ssysType;
	}
	/* 访问路径 */
	private String surlPath;
	
	public String getSurlPath(){
		return surlPath;
	}
	
	public void setSurlPath(String surlPath){
		this.surlPath= surlPath;
	}

		
}