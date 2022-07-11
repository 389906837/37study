package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/** 运营区域表(REGION) **/
public class Region extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String sregionName;   //名称
	private Integer icount;   //数量
	private Integer sposition;   //所属页面位置
	private Integer iregionLength;   //长
	private Integer iregionWidth;   //宽
	private String sremark;   //说明
	private Integer idelFlag;   //是否可修改:是否可供用户修改
	private Date taddTime;   //添加日期
	private String saddUser;   //添加人
	private Date tupdateTime;   //修改日期
	private String supateUser;   //修改人
	private String id;   //主键ID
	private String scode;   //编码
	
	//-----get set-----
	 /**名称*/
	 public String getSregionName(){
		 return sregionName;
	 }
	 /**名称*/
	 public void setSregionName(String sregionName){
		 this.sregionName=sregionName;
	 }
	 /**数量*/
	 public Integer getIcount(){
		 return icount;
	 }
	 /**数量*/
	 public void setIcount(Integer icount){
		 this.icount=icount;
	 }
	
	 /**长*/
	 public Integer getIregionLength(){
		 return iregionLength;
	 }
	 public Integer getSposition() {
		return sposition;
	}
	public void setSposition(Integer sposition) {
		this.sposition = sposition;
	}
	/**长*/
	 public void setIregionLength(Integer iregionLength){
		 this.iregionLength=iregionLength;
	 }
	 /**宽*/
	 public Integer getIregionWidth(){
		 return iregionWidth;
	 }
	 /**宽*/
	 public void setIregionWidth(Integer iregionWidth){
		 this.iregionWidth=iregionWidth;
	 }
	 /**说明*/
	 public String getSremark(){
		 return sremark;
	 }
	 /**说明*/
	 public void setSremark(String sremark){
		 this.sremark=sremark;
	 }
	 /**是否可修改:是否可供用户修改*/
	 public Integer getIdelFlag(){
		 return idelFlag;
	 }
	 /**是否可修改:是否可供用户修改*/
	 public void setIdelFlag(Integer idelFlag){
		 this.idelFlag=idelFlag;
	 }
	 /**添加日期*/
	 public Date getTaddTime(){
		 return taddTime;
	 }
	 /**添加日期*/
	 public void setTaddTime(Date taddTime){
		 this.taddTime=taddTime;
	 }
	 /**添加人*/
	 public String getSaddUser(){
		 return saddUser;
	 }
	 /**添加人*/
	 public void setSaddUser(String saddUser){
		 this.saddUser=saddUser;
	 }
	 /**修改日期*/
	 public Date getTupdateTime(){
		 return tupdateTime;
	 }
	 /**修改日期*/
	 public void setTupdateTime(Date tupdateTime){
		 this.tupdateTime=tupdateTime;
	 }
	 /**修改人*/
	 public String getSupateUser(){
		 return supateUser;
	 }
	 /**修改人*/
	 public void setSupateUser(String supateUser){
		 this.supateUser=supateUser;
	 }
	 /**主键ID*/
	 public String getId(){
		 return id;
	 }
	 /**主键ID*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**编码*/
	 public String getScode(){
		 return scode;
	 }
	 /**编码*/
	 public void setScode(String scode){
		 this.scode=scode;
	 }
	

		
} 