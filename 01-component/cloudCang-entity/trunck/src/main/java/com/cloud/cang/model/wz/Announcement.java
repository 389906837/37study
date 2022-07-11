package com.cloud.cang.model.wz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 系统公告(WZ_ANNOUNCEMENT) **/
public class Announcement extends GenericEntity  {

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
	
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 排序号 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 状态
            10 = 待发布
            20 = 已发布 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 发布作者 */
	private String sauthor;
	
	public String getSauthor(){
		return sauthor;
	}
	
	public void setSauthor(String sauthor){
		this.sauthor= sauthor;
	}
	/* 内容地址 */
	private String scontentUrl;
	
	public String getScontentUrl(){
		return scontentUrl;
	}
	
	public void setScontentUrl(String scontentUrl){
		this.scontentUrl= scontentUrl;
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
	/* 广告区域Id */
	private String sregionId;
	
	public String getSregionId(){
		return sregionId;
	}
	
	public void setSregionId(String sregionId){
		this.sregionId= sregionId;
	}
	/* 说明 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 标题 */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
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
	/* 发布时间 */
	private Date tpublishDate;
	
	public Date getTpublishDate(){
		return tpublishDate;
	}
	
	public void setTpublishDate(Date tpublishDate){
		this.tpublishDate= tpublishDate;
	}
	/* 修改日期 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}
	/* 有效期 */
	private Date tvalidDate;
	
	public Date getTvalidDate(){
		return tvalidDate;
	}
	
	public void setTvalidDate(Date tvalidDate){
		this.tvalidDate= tvalidDate;
	}

		
}