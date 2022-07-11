package com.cloud.cang.model.tb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 第三方订单/补货记录(TB_OPERATE_DEVICE_RECORD) **/
public class OperateDeviceRecord extends GenericEntity  {

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
	
	/* 订单增加总额 */
	private BigDecimal faddTotalAmount;
	
	public BigDecimal getFaddTotalAmount(){
		return faddTotalAmount;
	}
	
	public void setFaddTotalAmount(BigDecimal faddTotalAmount){
		this.faddTotalAmount= faddTotalAmount;
	}
	/* 订单增加总数 */
	private Integer faddTotalNum;
	
	public Integer getFaddTotalNum(){
		return faddTotalNum;
	}
	
	public void setFaddTotalNum(Integer faddTotalNum){
		this.faddTotalNum= faddTotalNum;
	}
	/* 订单减少总额 */
	private BigDecimal fsubTotalAmount;
	
	public BigDecimal getFsubTotalAmount(){
		return fsubTotalAmount;
	}
	
	public void setFsubTotalAmount(BigDecimal fsubTotalAmount){
		this.fsubTotalAmount= fsubTotalAmount;
	}
	/* 订单减少总数 */
	private Integer fsubTotalNum;
	
	public Integer getFsubTotalNum(){
		return fsubTotalNum;
	}
	
	public void setFsubTotalNum(Integer fsubTotalNum){
		this.fsubTotalNum= fsubTotalNum;
	}
	/* 操作类型 10 购物订单，20 盘点订单 */
	private Integer iorderType;
	
	public Integer getIorderType(){
		return iorderType;
	}
	
	public void setIorderType(Integer iorderType){
		this.iorderType= iorderType;
	}
	/* 第三方用户类型 10普通用户，20管理员 */
	private Integer iuserType;
	
	public Integer getIuserType(){
		return iuserType;
	}
	
	public void setIuserType(Integer iuserType){
		this.iuserType= iuserType;
	}
	/* 订单编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 第三方设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 第三方设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 第三方用户ID */
	private String suserId;
	
	public String getSuserId(){
		return suserId;
	}
	
	public void setSuserId(String suserId){
		this.suserId= suserId;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 操作时间 */
	private Date torderTime;
	
	public Date getTorderTime(){
		return torderTime;
	}
	
	public void setTorderTime(Date torderTime){
		this.torderTime= torderTime;
	}

		
}