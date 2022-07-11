package com.cloud.cang.model.fr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 人脸识别会员注册表(FR_FACE_REGISTER_INFO) **/
public class FaceRegisterInfo extends GenericEntity  {

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
	/* 10：注册
            20：绑定
            30：解绑
            40：删除 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 注册设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 人脸编号 */
	private String sfaceCode;
	
	public String getSfaceCode(){
		return sfaceCode;
	}
	
	public void setSfaceCode(String sfaceCode){
		this.sfaceCode= sfaceCode;
	}
	/* 人脸图片唯一标志 */
	private String sfaceToken;
	
	public String getSfaceToken(){
		return sfaceToken;
	}
	
	public void setSfaceToken(String sfaceToken){
		this.sfaceToken= sfaceToken;
	}
	/* 用户组ID */
	private String sgroupId;
	
	public String getSgroupId(){
		return sgroupId;
	}
	
	public void setSgroupId(String sgroupId){
		this.sgroupId= sgroupId;
	}
	/* 人脸区域的高度 */
	private String sheight;
	
	public String getSheight(){
		return sheight;
	}
	
	public void setSheight(String sheight){
		this.sheight= sheight;
	}
	/* 人脸区域离左边界的距离 */
	private String sleft;
	
	public String getSleft(){
		return sleft;
	}
	
	public void setSleft(String sleft){
		this.sleft= sleft;
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
	/* 人脸框相对于竖直方向的顺时针旋转角，[-180,180] */
	private String sratation;
	
	public String getSratation(){
		return sratation;
	}
	
	public void setSratation(String sratation){
		this.sratation= sratation;
	}
	/* 注册时上传的人脸图片地址 */
	private String sregisterFaceAddress;
	
	public String getSregisterFaceAddress(){
		return sregisterFaceAddress;
	}
	
	public void setSregisterFaceAddress(String sregisterFaceAddress){
		this.sregisterFaceAddress= sregisterFaceAddress;
	}
	/* 注册AI设备编号 */
	private String sregAiCode;
	
	public String getSregAiCode(){
		return sregAiCode;
	}
	
	public void setSregAiCode(String sregAiCode){
		this.sregAiCode= sregAiCode;
	}
	/* 注册IP */
	private String sregIp;
	
	public String getSregIp(){
		return sregIp;
	}
	
	public void setSregIp(String sregIp){
		this.sregIp= sregIp;
	}
	/* 人脸区域离上边界的距离 */
	private String stop;
	
	public String getStop(){
		return stop;
	}
	
	public void setStop(String stop){
		this.stop= stop;
	}
	/* 注册人脸库对应的用户资料 */
	private String suserInfo;
	
	public String getSuserInfo(){
		return suserInfo;
	}
	
	public void setSuserInfo(String suserInfo){
		this.suserInfo= suserInfo;
	}
	/*  */
	private String sversion;
	
	public String getSversion(){
		return sversion;
	}
	
	public void setSversion(String sversion){
		this.sversion= sversion;
	}
	/* 人脸区域的宽度 */
	private String swidth;
	
	public String getSwidth(){
		return swidth;
	}
	
	public void setSwidth(String swidth){
		this.swidth= swidth;
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
	/* 删除时间 */
	private Date tdeleteTime;
	
	public Date getTdeleteTime(){
		return tdeleteTime;
	}
	
	public void setTdeleteTime(Date tdeleteTime){
		this.tdeleteTime= tdeleteTime;
	}
	/* 注册时间 */
	private Date tregisterTime;
	
	public Date getTregisterTime(){
		return tregisterTime;
	}
	
	public void setTregisterTime(Date tregisterTime){
		this.tregisterTime= tregisterTime;
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