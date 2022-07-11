package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 后台用户表(SYS_OPERATOR) **/
public class Operator extends GenericEntity  {

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
	
	/* 是否管理员:0 no
            1 yes */
	private Integer bisAdmin;
	
	public Integer getBisAdmin(){
		return bisAdmin;
	}
	
	public void setBisAdmin(Integer bisAdmin){
		this.bisAdmin= bisAdmin;
	}
	/* 是否删除 */
	private Integer bisDelete;
	
	public Integer getBisDelete(){
		return bisDelete;
	}
	
	public void setBisDelete(Integer bisDelete){
		this.bisDelete= bisDelete;
	}
	/* 是否禁用:0：禁用
            1：启用 */
	private Integer bisFreeze;
	
	public Integer getBisFreeze(){
		return bisFreeze;
	}
	
	public void setBisFreeze(Integer bisFreeze){
		this.bisFreeze= bisFreeze;
	}
	/* 添加日期 */
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 首次登陆时间 */
	private Date dfirstLoginTime;
	
	public Date getDfirstLoginTime(){
		return dfirstLoginTime;
	}
	
	public void setDfirstLoginTime(Date dfirstLoginTime){
		this.dfirstLoginTime= dfirstLoginTime;
	}
	/* 最后登陆时间 */
	private Date dlastLoginTime;
	
	public Date getDlastLoginTime(){
		return dlastLoginTime;
	}
	
	public void setDlastLoginTime(Date dlastLoginTime){
		this.dlastLoginTime= dlastLoginTime;
	}
	/* 修改日期 */
	private Date dmodifyDate;
	
	public Date getDmodifyDate(){
		return dmodifyDate;
	}
	
	public void setDmodifyDate(Date dmodifyDate){
		this.dmodifyDate= dmodifyDate;
	}
	/* 0=所有
            1=指定分组
            2=指定设备 */
	private Integer idevType;
	
	public Integer getIdevType(){
		return idevType;
	}
	
	public void setIdevType(Integer idevType){
		this.idevType= idevType;
	}
	/* 是否BD 0否1是 */
	private Integer iisBd;
	
	public Integer getIisBd(){
		return iisBd;
	}
	
	public void setIisBd(Integer iisBd){
		this.iisBd= iisBd;
	}
	/* 0=所有
            1=对应bd管理商户
            2=自己商户
            3=指定商户 */
	private Integer imerType;
	
	public Integer getImerType(){
		return imerType;
	}
	
	public void setImerType(Integer imerType){
		this.imerType= imerType;
	}
	/* 添加人(用户名) */
	private String saddOperator;
	
	public String getSaddOperator(){
		return saddOperator;
	}
	
	public void setSaddOperator(String saddOperator){
		this.saddOperator= saddOperator;
	}
	/* 部门编号 */
	private String sdepartmentCode;
	
	public String getSdepartmentCode(){
		return sdepartmentCode;
	}
	
	public void setSdepartmentCode(String sdepartmentCode){
		this.sdepartmentCode= sdepartmentCode;
	}
	/* 部门名称 */
	private String sdepartmentName;
	
	public String getSdepartmentName(){
		return sdepartmentName;
	}
	
	public void setSdepartmentName(String sdepartmentName){
		this.sdepartmentName= sdepartmentName;
	}
	/* 分组/设备列表 */
	private String sgroupDecList;
	
	public String getSgroupDecList(){
		return sgroupDecList;
	}
	
	public void setSgroupDecList(String sgroupDecList){
		this.sgroupDecList= sgroupDecList;
	}
	/* 邮箱 */
	private String smail;
	
	public String getSmail(){
		return smail;
	}
	
	public void setSmail(String smail){
		this.smail= smail;
	}
	/* 商户名称 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 商户列表 */
	private String smerList;
	
	public String getSmerList(){
		return smerList;
	}
	
	public void setSmerList(String smerList){
		this.smerList= smerList;
	}
	/* 手机 */
	private String smobile;
	
	public String getSmobile(){
		return smobile;
	}
	
	public void setSmobile(String smobile){
		this.smobile= smobile;
	}
	/* 修改人(用户名) */
	private String smodifyOperator;
	
	public String getSmodifyOperator(){
		return smodifyOperator;
	}
	
	public void setSmodifyOperator(String smodifyOperator){
		this.smodifyOperator= smodifyOperator;
	}
	/* 编号 */
	private String soperatorNo;
	
	public String getSoperatorNo(){
		return soperatorNo;
	}
	
	public void setSoperatorNo(String soperatorNo){
		this.soperatorNo= soperatorNo;
	}
	/* 密码 */
	private String spassword;
	
	public String getSpassword(){
		return spassword;
	}
	
	public void setSpassword(String spassword){
		this.spassword= spassword;
	}
	/* 真实姓名 */
	private String srealName;
	
	public String getSrealName(){
		return srealName;
	}
	
	public void setSrealName(String srealName){
		this.srealName= srealName;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 用户名 */
	private String suserName;
	
	public String getSuserName(){
		return suserName;
	}
	
	public void setSuserName(String suserName){
		this.suserName= suserName;
	}

		
}