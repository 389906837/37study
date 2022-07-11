package com.cloud.cang.model.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品订单审核记录信息表(OM_ORDER_AUDIT) **/
public class OrderAudit extends GenericEntity  {

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
	
	/* 订单总额 */
	private BigDecimal ftotalAmount;
	
	public BigDecimal getFtotalAmount(){
		return ftotalAmount;
	}
	
	public void setFtotalAmount(BigDecimal ftotalAmount){
		this.ftotalAmount= ftotalAmount;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 10=微信
            20=支付宝
            30=APP */
	private Integer isourceClientType;
	
	public Integer getIsourceClientType(){
		return isourceClientType;
	}
	
	public void setIsourceClientType(Integer isourceClientType){
		this.isourceClientType= isourceClientType;
	}
	/* 订单来源方式
             */
	private Integer isourceWay;
	
	public Integer getIsourceWay(){
		return isourceWay;
	}
	
	public void setIsourceWay(Integer isourceWay){
		this.isourceWay= isourceWay;
	}
	/* 来源方式编号 */
	private Integer isourceWayCode;
	
	public Integer getIsourceWayCode(){
		return isourceWayCode;
	}
	
	public void setIsourceWayCode(Integer isourceWayCode){
		this.isourceWayCode= isourceWayCode;
	}
	/* 来源方式名称 */
	private String isourceWayName;
	
	public String getIsourceWayName(){
		return isourceWayName;
	}
	
	public void setIsourceWayName(String isourceWayName){
		this.isourceWayName= isourceWayName;
	}
	/* 10=待处理
            20=异常处理成功
            30=异常处理忽略
             */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 异常审核订单类型（数据字典配置）
            10=盘点异常
            20=已售补扣
            30=未知错误
            40=重力错误*/
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 商户ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 设备名称 */
	private String sdeviceName;
	
	public String getSdeviceName(){
		return sdeviceName;
	}
	
	public void setSdeviceName(String sdeviceName){
		this.sdeviceName= sdeviceName;
	}
	/* 管理人员备注 */
	private String shandlerRemark;
	
	public String getShandlerRemark(){
		return shandlerRemark;
	}
	
	public void setShandlerRemark(String shandlerRemark){
		this.shandlerRemark= shandlerRemark;
	}
	/* 处理人ID */
	private String shandlerUserid;
	
	public String getShandlerUserid(){
		return shandlerUserid;
	}
	
	public void setShandlerUserid(String shandlerUserid){
		this.shandlerUserid= shandlerUserid;
	}
	/* 处理人姓名 */
	private String shandlerUsername;
	
	public String getShandlerUsername(){
		return shandlerUsername;
	}
	
	public void setShandlerUsername(String shandlerUsername){
		this.shandlerUsername= shandlerUsername;
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
	/* 会员用户名（手机号） */
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
	/* 订单编号 */
	private String sorderCode;
	
	public String getSorderCode(){
		return sorderCode;
	}
	
	public void setSorderCode(String sorderCode){
		this.sorderCode= sorderCode;
	}
	/* 设备读写器序列号 */
	private String sreaderSerialNumber;
	
	public String getSreaderSerialNumber(){
		return sreaderSerialNumber;
	}
	
	public void setSreaderSerialNumber(String sreaderSerialNumber){
		this.sreaderSerialNumber= sreaderSerialNumber;
	}
	/* 修改时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 处理时间 */
	private Date thandlerTime;
	
	public Date getThandlerTime(){
		return thandlerTime;
	}
	
	public void setThandlerTime(Date thandlerTime){
		this.thandlerTime= thandlerTime;
	}
	/* 修改时间 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

	/* 扩展字段 */
	private String sext;

	public String getSext() {
		return sext;
	}

	public void setSext(String sext) {
		this.sext = sext;
	}
}