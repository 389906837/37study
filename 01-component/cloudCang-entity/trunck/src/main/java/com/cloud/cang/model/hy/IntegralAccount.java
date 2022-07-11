package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员积分账户(HY_INTEGRAL_ACCOUNT) **/
public class IntegralAccount extends GenericEntity  {

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
	
	/* 已过期积分 */
	private Integer iexpiredPoints;
	
	public Integer getIexpiredPoints(){
		return iexpiredPoints;
	}
	
	public void setIexpiredPoints(Integer iexpiredPoints){
		this.iexpiredPoints= iexpiredPoints;
	}
	/* 冻结积分 */
	private Integer ifrozenPoints;
	
	public Integer getIfrozenPoints(){
		return ifrozenPoints;
	}
	
	public void setIfrozenPoints(Integer ifrozenPoints){
		this.ifrozenPoints= ifrozenPoints;
	}
	/* 总积分 */
	private Integer itotalPoints;
	
	public Integer getItotalPoints(){
		return itotalPoints;
	}
	
	public void setItotalPoints(Integer itotalPoints){
		this.itotalPoints= itotalPoints;
	}
	/* 可用积分 */
	private Integer iusablePoints;
	
	public Integer getIusablePoints(){
		return iusablePoints;
	}
	
	public void setIusablePoints(Integer iusablePoints){
		this.iusablePoints= iusablePoints;
	}
	/* 已使用积分 */
	private Integer iusedPoints;
	
	public Integer getIusedPoints(){
		return iusedPoints;
	}
	
	public void setIusedPoints(Integer iusedPoints){
		this.iusedPoints= iusedPoints;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 账户编号 */
	private String saccountCode;
	
	public String getSaccountCode(){
		return saccountCode;
	}
	
	public void setSaccountCode(String saccountCode){
		this.saccountCode= saccountCode;
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