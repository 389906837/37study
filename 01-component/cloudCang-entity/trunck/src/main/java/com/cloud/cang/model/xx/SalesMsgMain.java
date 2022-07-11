package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 营销短信主表(XX_SALES_MSG_MAIN) **/
public class SalesMsgMain extends GenericEntity  {

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
	
	/* 发送总数 */
	private Integer icount;
	
	public Integer getIcount(){
		return icount;
	}
	
	public void setIcount(Integer icount){
		this.icount= icount;
	}
	/* 10=正在发送 20=已经完成 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 备注 */
	private String remark;
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark= remark;
	}
	/* 添加人 */
	private String sadduser;
	
	public String getSadduser(){
		return sadduser;
	}
	
	public void setSadduser(String sadduser){
		this.sadduser= sadduser;
	}
	/* 记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 消息内容 */
	private String scontent;
	
	public String getScontent(){
		return scontent;
	}
	
	public void setScontent(String scontent){
		this.scontent= scontent;
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
	/* 条件记录 */
	private String sselect;
	
	public String getSselect(){
		return sselect;
	}
	
	public void setSselect(String sselect){
		this.sselect= sselect;
	}
	/* 供应商ID */
	private String sspartnerId;
	
	public String getSspartnerId(){
		return sspartnerId;
	}
	
	public void setSspartnerId(String sspartnerId){
		this.sspartnerId= sspartnerId;
	}
	/* 供应商编号 */
	private String ssupplierCode;
	
	public String getSsupplierCode(){
		return ssupplierCode;
	}
	
	public void setSsupplierCode(String ssupplierCode){
		this.ssupplierCode= ssupplierCode;
	}
	/* 增加时间 */
	private Date taddtime;
	
	public Date getTaddtime(){
		return taddtime;
	}
	
	public void setTaddtime(Date taddtime){
		this.taddtime= taddtime;
	}

		
}