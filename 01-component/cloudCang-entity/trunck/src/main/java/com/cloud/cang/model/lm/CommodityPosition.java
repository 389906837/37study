package com.cloud.cang.model.lm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品标注信息位置记录表(LM_COMMODITY_POSITION) **/
public class CommodityPosition extends GenericEntity  {

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
	
	/* 批次商品ID */
	private String commodityDetailId;
	
	public String getCommodityDetailId(){
		return commodityDetailId;
	}
	
	public void setCommodityDetailId(String commodityDetailId){
		this.commodityDetailId= commodityDetailId;
	}
	/* 标注困难 */
	private String smarkDifficult;
	
	public String getSmarkDifficult(){
		return smarkDifficult;
	}
	
	public void setSmarkDifficult(String smarkDifficult){
		this.smarkDifficult= smarkDifficult;
	}
	/* 标注姿势 */
	private String smarkPose;
	
	public String getSmarkPose(){
		return smarkPose;
	}
	
	public void setSmarkPose(String smarkPose){
		this.smarkPose= smarkPose;
	}
	/* 标注信息 */
	private String smarkRemark;
	
	public String getSmarkRemark(){
		return smarkRemark;
	}
	
	public void setSmarkRemark(String smarkRemark){
		this.smarkRemark= smarkRemark;
	}
	/* 标注截断 */
	private String smarkTruncated;
	
	public String getSmarkTruncated(){
		return smarkTruncated;
	}
	
	public void setSmarkTruncated(String smarkTruncated){
		this.smarkTruncated= smarkTruncated;
	}
	/* 最大X点 */
	private String smaxX;
	
	public String getSmaxX(){
		return smaxX;
	}
	
	public void setSmaxX(String smaxX){
		this.smaxX= smaxX;
	}
	/* 最大Y点 */
	private String smaxY;
	
	public String getSmaxY(){
		return smaxY;
	}
	
	public void setSmaxY(String smaxY){
		this.smaxY= smaxY;
	}
	/* 最小X点 */
	private String sminX;
	
	public String getSminX(){
		return sminX;
	}
	
	public void setSminX(String sminX){
		this.sminX= sminX;
	}
	/* 最小Y点 */
	private String sminY;
	
	public String getSminY(){
		return sminY;
	}
	
	public void setSminY(String sminY){
		this.sminY= sminY;
	}

		
}