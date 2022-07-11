package com.cloud.cang.model.tb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 第三方商户操作设备记录明细表(TB_OPERATE_DEVICE_DETAIL) **/
public class OperateDeviceDetail extends GenericEntity  {

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
	
	/*  */
	private BigDecimal faddTotalAmount;
	
	public BigDecimal getFaddTotalAmount(){
		return faddTotalAmount;
	}
	
	public void setFaddTotalAmount(BigDecimal faddTotalAmount){
		this.faddTotalAmount= faddTotalAmount;
	}
	/*  */
	private BigDecimal fprice;
	
	public BigDecimal getFprice(){
		return fprice;
	}
	
	public void setFprice(BigDecimal fprice){
		this.fprice= fprice;
	}
	/*  */
	private BigDecimal fsubTotalAmount;
	
	public BigDecimal getFsubTotalAmount(){
		return fsubTotalAmount;
	}
	
	public void setFsubTotalAmount(BigDecimal fsubTotalAmount){
		this.fsubTotalAmount= fsubTotalAmount;
	}
	/* 第三方订单补货记录编号 */
	private String soperateCode;
	
	public String getSoperateCode(){
		return soperateCode;
	}
	
	public void setSoperateCode(String soperateCode){
		this.soperateCode= soperateCode;
	}
	/* 第三方操作设备记录表ID */
	private String soperateId;
	
	public String getSoperateId(){
		return soperateId;
	}
	
	public void setSoperateId(String soperateId){
		this.soperateId= soperateId;
	}
	/* 第三方SKU编号 */
	private String sthirdPartSkuCode;
	
	public String getSthirdPartSkuCode(){
		return sthirdPartSkuCode;
	}
	
	public void setSthirdPartSkuCode(String sthirdPartSkuCode){
		this.sthirdPartSkuCode= sthirdPartSkuCode;
	}
	/* 本地库视觉识别编号 */
	private String svrCode;
	
	public String getSvrCode(){
		return svrCode;
	}
	
	public void setSvrCode(String svrCode){
		this.svrCode= svrCode;
	}
	/* 视觉商品减少数量 */
	private Integer svrDecrease;
	
	public Integer getSvrDecrease(){
		return svrDecrease;
	}
	
	public void setSvrDecrease(Integer svrDecrease){
		this.svrDecrease= svrDecrease;
	}
	/* 本地库视觉识别ID */
	private String svrId;
	
	public String getSvrId(){
		return svrId;
	}
	
	public void setSvrId(String svrId){
		this.svrId= svrId;
	}
	/* 视觉商品增加数量 */
	private Integer svrIncrement;
	
	public Integer getSvrIncrement(){
		return svrIncrement;
	}
	
	public void setSvrIncrement(Integer svrIncrement){
		this.svrIncrement= svrIncrement;
	}
	/*  */
	private String svrName;
	
	public String getSvrName(){
		return svrName;
	}
	
	public void setSvrName(String svrName){
		this.svrName= svrName;
	}
	/* 视觉商品总数量 */
	private Integer svrTotalAmount;
	
	public Integer getSvrTotalAmount(){
		return svrTotalAmount;
	}
	
	public void setSvrTotalAmount(Integer svrTotalAmount){
		this.svrTotalAmount= svrTotalAmount;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}