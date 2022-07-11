package com.cloud.cang.model.sm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备实时库存明细(SM_STOCK_DETAIL) **/
public class StockDetail extends GenericEntity  {

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
	/* 商品重量 */
	private BigDecimal fcommodityWeight;
	
	public BigDecimal getFcommodityWeight(){
		return fcommodityWeight;
	}
	
	public void setFcommodityWeight(BigDecimal fcommodityWeight){
		this.fcommodityWeight= fcommodityWeight;
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
	/* 商品售出状态
            10=正常售出
            20=重复售出
            30=过期售出
            40=重复过期售出
            50=异常售出
            60=无效售出
            70=正常下架
            80=异常下架 */
	private Integer isaleStatus;
	
	public Integer getIsaleStatus(){
		return isaleStatus;
	}
	
	public void setIsaleStatus(Integer isaleStatus){
		this.isaleStatus= isaleStatus;
	}
	/* 商品状态
            10=正常
            20=已售出
            30=已过期
            40=已过期且售出
            50=异常商品
            60=无效 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
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
	/* 唯一标识 */
	private String sidentifies;
	
	public String getSidentifies(){
		return sidentifies;
	}
	
	public void setSidentifies(String sidentifies){
		this.sidentifies= sidentifies;
	}
	/* 保质期 */
	private String sshelfLife;
	
	public String getSshelfLife(){
		return sshelfLife;
	}
	
	public void setSshelfLife(String sshelfLife){
		this.sshelfLife= sshelfLife;
	}
	/* 商户编号 */
	private String sstockCode;
	
	public String getSstockCode(){
		return sstockCode;
	}
	
	public void setSstockCode(String sstockCode){
		this.sstockCode= sstockCode;
	}
	/* 商户ID */
	private String sstockId;
	
	public String getSstockId(){
		return sstockId;
	}
	
	public void setSstockId(String sstockId){
		this.sstockId= sstockId;
	}
	/* 上架时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}