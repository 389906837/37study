package com.cloud.cang.model.cr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 识别图片结果管理表(CR_REPORT_MAN) **/
public class ReportMan extends GenericEntity  {

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
	
	/* 是否删除 
            0 否
            1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 设备编号 */
	private String scameraCode;
	
	public String getScameraCode(){
		return scameraCode;
	}
	
	public void setScameraCode(String scameraCode){
		this.scameraCode= scameraCode;
	}
	/* 设备型号 */
	private String scameraModel;
	
	public String getScameraModel(){
		return scameraModel;
	}
	
	public void setScameraModel(String scameraModel){
		this.scameraModel= scameraModel;
	}
	/* 编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 识别结果 */
	private String sidenResult;
	
	public String getSidenResult(){
		return sidenResult;
	}
	
	public void setSidenResult(String sidenResult){
		this.sidenResult= sidenResult;
	}
	/* 原始图片地址 */
	private String soriginalPicUrl;
	
	public String getSoriginalPicUrl(){
		return soriginalPicUrl;
	}
	
	public void setSoriginalPicUrl(String soriginalPicUrl){
		this.soriginalPicUrl= soriginalPicUrl;
	}
	/* 已处理图片地址 */
	private String sprocessedPicUrl;
	
	public String getSprocessedPicUrl(){
		return sprocessedPicUrl;
	}
	
	public void setSprocessedPicUrl(String sprocessedPicUrl){
		this.sprocessedPicUrl= sprocessedPicUrl;
	}
	/* 添加时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}