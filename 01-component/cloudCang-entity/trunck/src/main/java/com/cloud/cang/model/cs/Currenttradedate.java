package com.cloud.cang.model.cs;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 节日管理(CS_CURRENTTRADEDATE) **/
public class Currenttradedate extends GenericEntity  {

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
	
	/* 是否工作日 */
	private Integer bisworkdate;
	
	public Integer getBisworkdate(){
		return bisworkdate;
	}
	
	public void setBisworkdate(Integer bisworkdate){
		this.bisworkdate= bisworkdate;
	}
	/* 添加日期 */
	private Date dadddate;
	
	public Date getDadddate(){
		return dadddate;
	}
	
	public void setDadddate(Date dadddate){
		this.dadddate= dadddate;
	}
	/* 交易日期 */
	private Date dtradedate;
	
	public Date getDtradedate(){
		return dtradedate;
	}
	
	public void setDtradedate(Date dtradedate){
		this.dtradedate= dtradedate;
	}

		
}