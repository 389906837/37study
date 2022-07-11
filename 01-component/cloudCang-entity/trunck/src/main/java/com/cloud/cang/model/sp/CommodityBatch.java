package com.cloud.cang.model.sp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品批次管理信息表(SP_COMMODITY_BATCH) **/
public class CommodityBatch extends GenericEntity  {

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
	
	/* 过期日期 */
	private Date dexpiredDate;
	
	public Date getDexpiredDate(){
		return dexpiredDate;
	}
	
	public void setDexpiredDate(Date dexpiredDate){
		this.dexpiredDate= dexpiredDate;
	}
	/* 生产日期 */
	private Date dproduceDate;
	
	public Date getDproduceDate(){
		return dproduceDate;
	}
	
	public void setDproduceDate(Date dproduceDate){
		this.dproduceDate= dproduceDate;
	}
	/* 商品成本 */
	private BigDecimal fcostAmount;
	
	public BigDecimal getFcostAmount(){
		return fcostAmount;
	}
	
	public void setFcostAmount(BigDecimal fcostAmount){
		this.fcostAmount= fcostAmount;
	}
	/* 商品进价税点 */
	private BigDecimal ftaxPoint;
	
	public BigDecimal getFtaxPoint(){
		return ftaxPoint;
	}
	
	public void setFtaxPoint(BigDecimal ftaxPoint){
		this.ftaxPoint= ftaxPoint;
	}
	/* 批次商品数量 */
	private Integer icommodityNum;
	
	public Integer getIcommodityNum(){
		return icommodityNum;
	}
	
	public void setIcommodityNum(Integer icommodityNum){
		this.icommodityNum= icommodityNum;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 货损商品数量 */
	private Integer ilossGoodsNum;
	
	public Integer getIlossGoodsNum(){
		return ilossGoodsNum;
	}
	
	public void setIlossGoodsNum(Integer ilossGoodsNum){
		this.ilossGoodsNum= ilossGoodsNum;
	}
	/* 10=销售中
            20=售罄 */
	private Integer isaleStatus;
	
	public Integer getIsaleStatus(){
		return isaleStatus;
	}
	
	public void setIsaleStatus(Integer isaleStatus){
		this.isaleStatus= isaleStatus;
	}
	/* 已上架商品数据 */
	private Integer ishelfNum;
	
	public Integer getIshelfNum(){
		return ishelfNum;
	}
	
	public void setIshelfNum(Integer ishelfNum){
		this.ishelfNum= ishelfNum;
	}
	/* 10=启用
            20=禁用 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 10=待上架
            20=部分上架
            30=全部上架 */
	private Integer istockStatus;
	
	public Integer getIstockStatus(){
		return istockStatus;
	}
	
	public void setIstockStatus(Integer istockStatus){
		this.istockStatus= istockStatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 批次号 */
	private String sbatchNo;
	
	public String getSbatchNo(){
		return sbatchNo;
	}
	
	public void setSbatchNo(String sbatchNo){
		this.sbatchNo= sbatchNo;
	}
	/* 商品编号 */
	private String scommodityCode;
	
	public String getScommodityCode(){
		return scommodityCode;
	}
	
	public void setScommodityCode(String scommodityCode){
		this.scommodityCode= scommodityCode;
	}
	/* 商品ID */
	private String scommodityId;
	
	public String getScommodityId(){
		return scommodityId;
	}
	
	public void setScommodityId(String scommodityId){
		this.scommodityId= scommodityId;
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
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
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