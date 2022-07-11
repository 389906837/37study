package com.cloud.cang.model.sl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 对账日志表(SL_CHECK_LOG) **/
public class CheckLog extends GenericEntity  {

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
	
	/* 对账日期 */
	private Date dcheckDate;
	
	public Date getDcheckDate(){
		return dcheckDate;
	}
	
	public void setDcheckDate(Date dcheckDate){
		this.dcheckDate= dcheckDate;
	}
	/* 分类  10 微信支付 20 支付宝  */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 对账结果描述 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 对账结果 1=成功
            2=失败
            3=异常 */
	private Integer sresult;
	
	public Integer getSresult(){
		return sresult;
	}
	
	public void setSresult(Integer sresult){
		this.sresult= sresult;
	}
	/* 标题 */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
	}
	/* 对账开始时间 */
	private Date tbeginDatetime;
	
	public Date getTbeginDatetime(){
		return tbeginDatetime;
	}
	
	public void setTbeginDatetime(Date tbeginDatetime){
		this.tbeginDatetime= tbeginDatetime;
	}
	/* 对账结束时间 */
	private Date tendDatetime;
	
	public Date getTendDatetime(){
		return tendDatetime;
	}
	
	public void setTendDatetime(Date tendDatetime){
		this.tendDatetime= tendDatetime;
	}

		
}