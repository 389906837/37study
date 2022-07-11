package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 智能垃圾箱授权记录表(HY_TRASH_AUTHORISE_RECORD) **/
public class TrashAuthoriseRecord extends GenericEntity  {

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
	
	/* 操作动作 10 签约 20 解约 */
	private Integer iaction;
	
	public Integer getIaction(){
		return iaction;
	}
	
	public void setIaction(Integer iaction){
		this.iaction= iaction;
	}
	/* 用户编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 用户ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 用户标识 */
	private String sopenIn;
	
	public String getSopenIn(){
		return sopenIn;
	}
	
	public void setSopenIn(String sopenIn){
		this.sopenIn= sopenIn;
	}
	/* 智能垃圾箱品牌 */
	private String strashBrand;
	
	public String getStrashBrand(){
		return strashBrand;
	}
	
	public void setStrashBrand(String strashBrand){
		this.strashBrand= strashBrand;
	}
	/* 智能垃圾箱编号 */
	private String strashCode;
	
	public String getStrashCode(){
		return strashCode;
	}
	
	public void setStrashCode(String strashCode){
		this.strashCode= strashCode;
	}
	/* 解约方式
            0-未解约 
            1-有效期过自动解约 
            2-用户主动解约 
            3-商户API解约 
            4-商户平台解约 
            5-注销 */
	private Integer sunsignWay;
	
	public Integer getSunsignWay(){
		return sunsignWay;
	}
	
	public void setSunsignWay(Integer sunsignWay){
		this.sunsignWay= sunsignWay;
	}
	/* 操作时间 */
	private Date toperTime;
	
	public Date getToperTime(){
		return toperTime;
	}
	
	public void setToperTime(Date toperTime){
		this.toperTime= toperTime;
	}

		
}