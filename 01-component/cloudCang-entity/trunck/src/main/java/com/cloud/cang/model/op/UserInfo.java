package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 开放平台用户信息表(OP_USER_INFO) **/
public class UserInfo extends GenericEntity  {

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
	
	/* 会员生日 */
	private Date dbirthdate;
	
	public Date getDbirthdate(){
		return dbirthdate;
	}
	
	public void setDbirthdate(Date dbirthdate){
		this.dbirthdate= dbirthdate;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否挂靠后台商户 0否 1是 */
	private Integer iisMerchaant;
	
	public Integer getIisMerchaant(){
		return iisMerchaant;
	}
	
	public void setIisMerchaant(Integer iisMerchaant){
		this.iisMerchaant= iisMerchaant;
	}
	/* 10：个人会员
            20：企业会员 */
	private Integer imemberType;
	
	public Integer getImemberType(){
		return imemberType;
	}
	
	public void setImemberType(Integer imemberType){
		this.imemberType= imemberType;
	}
	/* 会员性别 0 女 1 男 */
	private Integer isex;
	
	public Integer getIsex(){
		return isex;
	}
	
	public void setIsex(Integer isex){
		this.isex= isex;
	}
	/* 用户状态：1：正常 2：注销停用 3：系统黑名单 4：冻结 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 用户编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 邮箱 */
	private String semail;
	
	public String getSemail(){
		return semail;
	}
	
	public void setSemail(String semail){
		this.semail= semail;
	}
	/* 会员头像地址 */
	private String sheadImage;
	
	public String getSheadImage(){
		return sheadImage;
	}
	
	public void setSheadImage(String sheadImage){
		this.sheadImage= sheadImage;
	}
	/* 登录密码 */
	private String sloginPwd;
	
	public String getSloginPwd(){
		return sloginPwd;
	}
	
	public void setSloginPwd(String sloginPwd){
		this.sloginPwd= sloginPwd;
	}
	/* 挂靠后台商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 挂靠后台商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 手机号码 */
	private String smobile;
	
	public String getSmobile(){
		return smobile;
	}
	
	public void setSmobile(String smobile){
		this.smobile= smobile;
	}
	/* 会员昵称 */
	private String snickName;
	
	public String getSnickName(){
		return snickName;
	}
	
	public void setSnickName(String snickName){
		this.snickName= snickName;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 注册时间 */
	private Date tregisterTime;
	
	public Date getTregisterTime(){
		return tregisterTime;
	}
	
	public void setTregisterTime(Date tregisterTime){
		this.tregisterTime= tregisterTime;
	}
	/* 修改人 */
	private String tupateUser;
	
	public String getTupateUser(){
		return tupateUser;
	}
	
	public void setTupateUser(String tupateUser){
		this.tupateUser= tupateUser;
	}
	/* 修改日期 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}