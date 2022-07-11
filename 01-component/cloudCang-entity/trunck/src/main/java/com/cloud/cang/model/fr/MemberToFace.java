package com.cloud.cang.model.fr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员人脸绑定信息表(FR_MEMBER_TO_FACE) **/
public class MemberToFace extends GenericEntity  {

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
	
	/*  10 ：微信支付 
            20 ：支付宝 
            30：其他
             */
	private Integer ibindPayType;
	
	public Integer getIbindPayType(){
		return ibindPayType;
	}
	
	public void setIbindPayType(Integer ibindPayType){
		this.ibindPayType= ibindPayType;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 10：已绑定
            20：已解绑
            30：已失效
             */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 绑定人脸编号 */
	private String sfaceCode;
	
	public String getSfaceCode(){
		return sfaceCode;
	}
	
	public void setSfaceCode(String sfaceCode){
		this.sfaceCode= sfaceCode;
	}
	/* 绑定人脸ID */
	private String sfaceId;
	
	public String getSfaceId(){
		return sfaceId;
	}
	
	public void setSfaceId(String sfaceId){
		this.sfaceId= sfaceId;
	}
	/* 绑定人脸图片唯一标志 */
	private String sfaceToken;
	
	public String getSfaceToken(){
		return sfaceToken;
	}
	
	public void setSfaceToken(String sfaceToken){
		this.sfaceToken= sfaceToken;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名 */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 商户编号 */
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
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 绑定时间 */
	private Date tbindTime;
	
	public Date getTbindTime(){
		return tbindTime;
	}
	
	public void setTbindTime(Date tbindTime){
		this.tbindTime= tbindTime;
	}
	/* 解绑时间 */
	private Date tunbindTime;
	
	public Date getTunbindTime(){
		return tunbindTime;
	}
	
	public void setTunbindTime(Date tunbindTime){
		this.tunbindTime= tunbindTime;
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