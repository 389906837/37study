package com.cloud.cang.model.wz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 运营区域表(WZ_REGION) **/
public class Region extends GenericEntity  {

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
	
	/* 数量 */
	private Integer icount;
	
	public Integer getIcount(){
		return icount;
	}
	
	public void setIcount(Integer icount){
		this.icount= icount;
	}
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 长 */
	private BigDecimal iregionLength;
	
	public BigDecimal getIregionLength(){
		return iregionLength;
	}
	
	public void setIregionLength(BigDecimal iregionLength){
		this.iregionLength= iregionLength;
	}
	/* 宽 */
	private BigDecimal iregionWidth;
	
	public BigDecimal getIregionWidth(){
		return iregionWidth;
	}
	
	public void setIregionWidth(BigDecimal iregionWidth){
		this.iregionWidth= iregionWidth;
	}
	/* 运营区域类型
            10=广告
            20=系统公告 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 编码 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 所属页面位置 */
	private String sposition;
	
	public String getSposition(){
		return sposition;
	}
	
	public void setSposition(String sposition){
		this.sposition= sposition;
	}
	/* 名称 */
	private String sregionName;
	
	public String getSregionName(){
		return sregionName;
	}
	
	public void setSregionName(String sregionName){
		this.sregionName= sregionName;
	}
	/* 说明 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
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