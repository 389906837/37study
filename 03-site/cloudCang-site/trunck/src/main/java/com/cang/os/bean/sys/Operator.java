package com.cang.os.bean.sys;

import java.util.Date;

import com.cang.os.bean.BaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

/** 管理员**/
@Document
public class Operator extends BaseBean {

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
	

	/* 是否删除 */
	private Integer bisDelete;
	
	/* 是否禁用:0：禁用
1：启用 */
	private Integer bisFreeze;
	

	public Integer getBisAdmin() {
		return bisAdmin;
	}

	public void setBisAdmin(Integer bisAdmin) {
		this.bisAdmin = bisAdmin;
	}

	public Integer getBisDelete() {
		return bisDelete;
	}

	public void setBisDelete(Integer bisDelete) {
		this.bisDelete = bisDelete;
	}

	public Integer getBisFreeze() {
		return bisFreeze;
	}

	public void setBisFreeze(Integer bisFreeze) {
		this.bisFreeze = bisFreeze;
	}

	/* 添加日期 */
	private Date daddDate;
	
	public Date getDaddDate(){
		return daddDate;
	}
	
	public void setDaddDate(Date daddDate){
		this.daddDate= daddDate;
	}
	/* 最后登陆时间 */
	private Date dendLoginTime;
	
	public Date getDendLoginTime(){
		return dendLoginTime;
	}
	
	public void setDendLoginTime(Date dendLoginTime){
		this.dendLoginTime= dendLoginTime;
	}
	/* 上次登陆时间 */
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
	/* 性别 */
	private Integer isex;
	
	public Integer getIsex(){
		return isex;
	}
	
	public void setIsex(Integer isex){
		this.isex= isex;
	}
	/* 添加人 */
	private String saddOperator;
	
	public String getSaddOperator(){
		return saddOperator;
	}
	
	public void setSaddOperator(String saddOperator){
		this.saddOperator= saddOperator;
	}
	/* 部门ID */
	private String sdepartmentId;
	
	public String getSdepartmentId(){
		return sdepartmentId;
	}
	
	public void setSdepartmentId(String sdepartmentId){
		this.sdepartmentId= sdepartmentId;
	}
	/* 简拼名 */
	private String sjpName;
	
	public String getSjpName(){
		return sjpName;
	}
	
	public void setSjpName(String sjpName){
		this.sjpName= sjpName;
	}
	/* 邮箱 */
	private String smail;
	
	public String getSmail(){
		return smail;
	}
	
	public void setSmail(String smail){
		this.smail= smail;
	}
	/* 手机 */
	private String smobile;
	
	public String getSmobile(){
		return smobile;
	}
	
	public void setSmobile(String smobile){
		this.smobile= smobile;
	}
	/* 修改人 */
	private String smodifyOperator;
	
	public String getSmodifyOperator(){
		return smodifyOperator;
	}
	
	public void setSmodifyOperator(String smodifyOperator){
		this.smodifyOperator= smodifyOperator;
	}
	/* 管理员编号 */
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
	/* 联系电话 */
	private String sphone;
	
	public String getSphone(){
		return sphone;
	}
	
	public void setSphone(String sphone){
		this.sphone= sphone;
	}
	/* 全拼名 */
	private String spyName;
	
	public String getSpyName(){
		return spyName;
	}
	
	public void setSpyName(String spyName){
		this.spyName= spyName;
	}
	/* 真实姓名 */
	private String srealName;
	
	public String getSrealName(){
		return srealName;
	}
	
	public void setSrealName(String srealName){
		this.srealName= srealName;
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