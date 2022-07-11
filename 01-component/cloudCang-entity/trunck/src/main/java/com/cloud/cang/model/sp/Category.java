package com.cloud.cang.model.sp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品分类表(SP_CATEGORY) **/
public class Category extends GenericEntity  {

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
	
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否对外显示（分类状态）0否1是 */
	private Integer iisDisplay;
	
	public Integer getIisDisplay(){
		return iisDisplay;
	}
	
	public void setIisDisplay(Integer iisDisplay){
		this.iisDisplay= iisDisplay;
	}
	/* 是否热门分类0否1是 */
	private Integer iisHot;
	
	public Integer getIisHot(){
		return iisHot;
	}
	
	public void setIisHot(Integer iisHot){
		this.iisHot= iisHot;
	}
	/* 是否父ID */
	private Integer iisParent;
	
	public Integer getIisParent(){
		return iisParent;
	}
	
	public void setIisParent(Integer iisParent){
		this.iisParent= iisParent;
	}
	/* 排序号 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 10=通用
            20=商户 */
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
	/* 分类编号  如零食等（大类）
            数据字典配置 */
	private String scategoryCode;
	
	public String getScategoryCode(){
		return scategoryCode;
	}
	
	public void setScategoryCode(String scategoryCode){
		this.scategoryCode= scategoryCode;
	}
	/* 分类名称（大类）
            同编号 */
	private String scategoryName;
	
	public String getScategoryName(){
		return scategoryName;
	}
	
	public void setScategoryName(String scategoryName){
		this.scategoryName= scategoryName;
	}
	/* 分类编号（小类特有） */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 小图标 */
	private String sicon;
	
	public String getSicon(){
		return sicon;
	}
	
	public void setSicon(String sicon){
		this.sicon= sicon;
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
	/* 分类名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 父ID */
	private String sparentId;
	
	public String getSparentId(){
		return sparentId;
	}
	
	public void setSparentId(String sparentId){
		this.sparentId= sparentId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
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