package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/** 广告表(ADVERTIS) **/
public class Advertis extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键ID
	private String sregionId;   //广告区域Id
	private String sregionName;//广告区域名称
	private String stitle;   //标题
	private String scontactName;   //联系人(预留)
	private String smobile;   //手机(预留)
	private Integer istatus;   //状态:0: INVALID:已过期1: USING:投放中2: WAIT:待投放3: PAUSE:暂停
	private Date tstartDate;   //开始日期
	private Date tendDate;   //结束日期
	private Integer ilinkType;   //链接类型1:普通超链接2:内链活动3:内链项目4:内链资讯
	private String ssourceTitle;   //来源标题
	private String shref;   //超链接
	private Integer isort;   //排序号
	private Integer iisDefault;   //是否默认0:0：否1：是
	private String scontentUrl;   //内容地址
	private String sremark;   //说明
	private Integer idelFlag;   //是否可修改:是否可供用户修改
	private Date taddTime;   //添加日期
	private String saddUser;   //添加人
	private Date tupdateTime;   //修改日期
	private String supateUser;   //修改人
	private String sicoUrl;//缩略图Url
	
	//-----get set-----
	 /**主键ID*/
	 public String getId(){
		 return id;
	 }
	 /**主键ID*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**广告区域Id*/
	 public String getSregionId(){
		 return sregionId;
	 }
	 /**广告区域Id*/
	 public void setSregionId(String sregionId){
		 this.sregionId=sregionId;
	 }
	 /**标题*/
	 public String getStitle(){
		 return stitle;
	 }
	 /**标题*/
	 public void setStitle(String stitle){
		 this.stitle=stitle;
	 }
	 /**联系人(预留)*/
	 public String getScontactName(){
		 return scontactName;
	 }
	 /**联系人(预留)*/
	 public void setScontactName(String scontactName){
		 this.scontactName=scontactName;
	 }
	 /**手机(预留)*/
	 public String getSmobile(){
		 return smobile;
	 }
	 /**手机(预留)*/
	 public void setSmobile(String smobile){
		 this.smobile=smobile;
	 }
	 /**状态:0: INVALID:已过期1: USING:投放中2: WAIT:待投放3: PAUSE:暂停*/
	 public Integer getIstatus(){
		 return istatus;
	 }
	 /**状态:0: INVALID:已过期1: USING:投放中2: WAIT:待投放3: PAUSE:暂停*/
	 public void setIstatus(Integer istatus){
		 this.istatus=istatus;
	 }
	 /**开始日期*/
	 public Date getTstartDate(){
		 return tstartDate;
	 }
	 /**开始日期*/
	 public void setTstartDate(Date tstartDate){
		 this.tstartDate=tstartDate;
	 }
	 /**结束日期*/
	 public Date getTendDate(){
		 return tendDate;
	 }
	 /**结束日期*/
	 public void setTendDate(Date tendDate){
		 this.tendDate=tendDate;
	 }
	 /**链接类型1:普通超链接2:内链活动3:内链项目4:内链资讯*/
	 public Integer getIlinkType(){
		 return ilinkType;
	 }
	 /**链接类型1:普通超链接2:内链活动3:内链项目4:内链资讯*/
	 public void setIlinkType(Integer ilinkType){
		 this.ilinkType=ilinkType;
	 }
	 /**来源标题*/
	 public String getSsourceTitle(){
		 return ssourceTitle;
	 }
	 /**来源标题*/
	 public void setSsourceTitle(String ssourceTitle){
		 this.ssourceTitle=ssourceTitle;
	 }
	 /**超链接*/
	 public String getShref(){
		 return shref;
	 }
	 /**超链接*/
	 public void setShref(String shref){
		 this.shref=shref;
	 }
	 /**排序号*/
	 public Integer getIsort(){
		 return isort;
	 }
	 /**排序号*/
	 public void setIsort(Integer isort){
		 this.isort=isort;
	 }
	 /**是否默认0:0：否1：是*/
	 public Integer getIisDefault(){
		 return iisDefault;
	 }
	 /**是否默认0:0：否1：是*/
	 public void setIisDefault(Integer iisDefault){
		 this.iisDefault=iisDefault;
	 }
	 /**内容地址*/
	 public String getScontentUrl(){
		 return scontentUrl;
	 }
	 /**内容地址*/
	 public void setScontentUrl(String scontentUrl){
		 this.scontentUrl=scontentUrl;
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
	public String getSregionName() {
		return sregionName;
	}
	public void setSregionName(String sregionName) {
		this.sregionName = sregionName;
	}
	public String getSicoUrl() {
		return sicoUrl;
	}
	public void setSicoUrl(String sicoUrl) {
		this.sicoUrl = sicoUrl;
	}
	
} 