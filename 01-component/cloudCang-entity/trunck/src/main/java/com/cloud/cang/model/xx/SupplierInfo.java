package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 消息供应商信息表(XX_SUPPLIER_INFO) **/
public class SupplierInfo extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键ID*/
	private String id;
	/*主键ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 0：正常 1：删除 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 渠道作用
            1：营销
            2：非营销 */
	private Integer iintention;
	
	public Integer getIintention(){
		return iintention;
	}
	
	public void setIintention(Integer iintention){
		this.iintention= iintention;
	}
	/* 是否禁用 */
	private Integer iisDisable;
	
	public Integer getIisDisable(){
		return iisDisable;
	}
	
	public void setIisDisable(Integer iisDisable){
		this.iisDisable= iisDisable;
	}
	/* 供应商类型
            1=短信
            2=邮箱 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 供应商账户名 */
	private String saccName;
	
	public String getSaccName(){
		return saccName;
	}
	
	public void setSaccName(String saccName){
		this.saccName= saccName;
	}
	/* 供应商密码 */
	private String saccPassword;
	
	public String getSaccPassword(){
		return saccPassword;
	}
	
	public void setSaccPassword(String saccPassword){
		this.saccPassword= saccPassword;
	}
	/* 编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 供应商描述 */
	private String sdesc;
	
	public String getSdesc(){
		return sdesc;
	}
	
	public void setSdesc(String sdesc){
		this.sdesc= sdesc;
	}
	/* 供应商扩展信息 */
	private String sextInfo;
	
	public String getSextInfo(){
		return sextInfo;
	}
	
	public void setSextInfo(String sextInfo){
		this.sextInfo= sextInfo;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户信息ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 增加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 修改时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}