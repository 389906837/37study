package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 模板主表表(XX_MSG_TEMPLATE_MAIN) **/
public class MsgTemplateMain extends GenericEntity  {

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
	
	/* 0:启用 1：删除 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 用途: 1：验证码  2：普通 */
	private Integer iusage;
	
	public Integer getIusage(){
		return iusage;
	}
	
	public void setIusage(Integer iusage){
		this.iusage= iusage;
	}
	/* 编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 功能编号数据字典配置 */
	private String sfunctionCode;
	
	public String getSfunctionCode(){
		return sfunctionCode;
	}
	
	public void setSfunctionCode(String sfunctionCode){
		this.sfunctionCode= sfunctionCode;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户信息ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 名称 */
	private String smsgName;
	
	public String getSmsgName(){
		return smsgName;
	}
	
	public void setSmsgName(String smsgName){
		this.smsgName= smsgName;
	}
	/* 描述 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 所属系统 */
	private String stemplateSourceSystem;
	
	public String getStemplateSourceSystem(){
		return stemplateSourceSystem;
	}
	
	public void setStemplateSourceSystem(String stemplateSourceSystem){
		this.stemplateSourceSystem= stemplateSourceSystem;
	}
	/* 添加时间 */
	private Date taddtime;
	
	public Date getTaddtime(){
		return taddtime;
	}
	
	public void setTaddtime(Date taddtime){
		this.taddtime= taddtime;
	}
	/* 修改时间 */
	private Date tupdatetime;
	
	public Date getTupdatetime(){
		return tupdatetime;
	}
	
	public void setTupdatetime(Date tupdatetime){
		this.tupdatetime= tupdatetime;
	}

		
}