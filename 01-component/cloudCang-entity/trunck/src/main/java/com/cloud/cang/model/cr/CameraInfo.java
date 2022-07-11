package com.cloud.cang.model.cr;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 相机基础信息(CR_CAMERA_INFO) **/
public class CameraInfo extends GenericEntity  {

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
	/* 相机是否商用 */
	private Integer iisCommercial;
	
	public Integer getIisCommercial(){
		return iisCommercial;
	}
	
	public void setIisCommercial(Integer iisCommercial){
		this.iisCommercial= iisCommercial;
	}
	/* 状态：
            10=在线
            20=离线 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 相机品牌 */
	private String sbrand;
	
	public String getSbrand(){
		return sbrand;
	}
	
	public void setSbrand(String sbrand){
		this.sbrand= sbrand;
	}
	/* 相机编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 相机型号 */
	private String smodel;
	
	public String getSmodel(){
		return smodel;
	}
	
	public void setSmodel(String smodel){
		this.smodel= smodel;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 到期时间 */
	private Date texpiresTime;
	
	public Date getTexpiresTime(){
		return texpiresTime;
	}
	
	public void setTexpiresTime(Date texpiresTime){
		this.texpiresTime= texpiresTime;
	}
	/* 注册时间 */
	private Date tregisterTime;
	
	public Date getTregisterTime(){
		return tregisterTime;
	}
	
	public void setTregisterTime(Date tregisterTime){
		this.tregisterTime= tregisterTime;
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