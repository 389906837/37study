package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 用户资金账户表(HY_FUND_ACCOUNT) **/
public class FundAccount extends GenericEntity  {

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
	
	/* 提现冻结金额 */
	private BigDecimal famountFrozen;
	
	public BigDecimal getFamountFrozen(){
		return famountFrozen;
	}
	
	public void setFamountFrozen(BigDecimal famountFrozen){
		this.famountFrozen= famountFrozen;
	}
	/* 其他冻结金额 */
	private BigDecimal famountFrozenOther;
	
	public BigDecimal getFamountFrozenOther(){
		return famountFrozenOther;
	}
	
	public void setFamountFrozenOther(BigDecimal famountFrozenOther){
		this.famountFrozenOther= famountFrozenOther;
	}
	/* 账户总余额 */
	private BigDecimal ftotalBalance;
	
	public BigDecimal getFtotalBalance(){
		return ftotalBalance;
	}
	
	public void setFtotalBalance(BigDecimal ftotalBalance){
		this.ftotalBalance= ftotalBalance;
	}
	/* 帐户可用余额 */
	private BigDecimal fusableBalance;
	
	public BigDecimal getFusableBalance(){
		return fusableBalance;
	}
	
	public void setFusableBalance(BigDecimal fusableBalance){
		this.fusableBalance= fusableBalance;
	}
	/* 10=平台账户 
            20=会员账户  */
	private Integer iaccountType;
	
	public Integer getIaccountType(){
		return iaccountType;
	}
	
	public void setIaccountType(Integer iaccountType){
		this.iaccountType= iaccountType;
	}
	/* 10=活动费户
            20=会员户 
             */
	private Integer iaccountUse;
	
	public Integer getIaccountUse(){
		return iaccountUse;
	}
	
	public void setIaccountUse(Integer iaccountUse){
		this.iaccountUse= iaccountUse;
	}
	/* 10：正常 20：冻结  30 ：销户 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
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
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
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
	/* 用户名 */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 创建时间 */
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