package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/** 文章表(ARTICLE) **/
public class Article extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键ID
	private String stitle;   //文章标题
	private String ssubjoinTitle;   //副标题
	private String stitleImage;   //文章标题缩略图
	private String stitleImageIndex;   //文章标题缩略图 ---首页展示
	private String snewsAbstract;   //摘要
	private String sresourceName;   //来源名称
	private String sresourceUrl;   //来源地址
	private Date tpublishDate;   //发布时间
	private String sauthor;   //发布作者
	private Integer isort;   //排序号从小到大，小的排前面
	private Integer iallCount;   //总访问数
	private String stopicUrl;   //专题url
	private String skeys;   //关键字
	private Date tvalidDate;   //有效期
	private String snavigationId;   //栏目id
	private String snavigationName;//栏目名称
	private Integer iistopic;   //是否专题(预留)
	private Integer iisRecommend;   //是否推荐
	private Integer istatus;   //状态：10：待发布20：已发布
	private Integer idelFlag;   //是否可修改:是否可供用户修改
	private Date taddTime;   //添加日期
	private String saddUser;   //添加人
	private Date tupdateTime;   //修改日期
	private String supateUser;   //修改人
	private Integer iisUrgentNotice;
	
	//-----get set-----
	
	 /**主键ID*/
	 public String getId(){
		 return id;
	 }
	 public Integer getIisUrgentNotice() {
		return iisUrgentNotice;
	}
	public void setIisUrgentNotice(Integer iisUrgentNotice) {
		this.iisUrgentNotice = iisUrgentNotice;
	}
	/**主键ID*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**文章标题*/
	 public String getStitle(){
		 return stitle;
	 }
	 /**文章标题*/
	 public void setStitle(String stitle){
		 this.stitle=stitle;
	 }
	 /**副标题*/
	 public String getSsubjoinTitle(){
		 return ssubjoinTitle;
	 }
	 /**副标题*/
	 public void setSsubjoinTitle(String ssubjoinTitle){
		 this.ssubjoinTitle=ssubjoinTitle;
	 }
	 /**文章标题缩略图*/
	 public String getStitleImage(){
		 return stitleImage;
	 }
	 /**文章标题缩略图*/
	 public void setStitleImage(String stitleImage){
		 this.stitleImage=stitleImage;
	 }

	public String getStitleImageIndex() {
		return stitleImageIndex;
	}

	public void setStitleImageIndex(String stitleImageIndex) {
		this.stitleImageIndex = stitleImageIndex;
	}

	/**摘要*/
	 public String getSnewsAbstract(){
		 return snewsAbstract;
	 }
	 /**摘要*/
	 public void setSnewsAbstract(String snewsAbstract){
		 this.snewsAbstract=snewsAbstract;
	 }
	 /**来源名称*/
	 public String getSresourceName(){
		 return sresourceName;
	 }
	 /**来源名称*/
	 public void setSresourceName(String sresourceName){
		 this.sresourceName=sresourceName;
	 }
	 /**来源地址*/
	 public String getSresourceUrl(){
		 return sresourceUrl;
	 }
	 /**来源地址*/
	 public void setSresourceUrl(String sresourceUrl){
		 this.sresourceUrl=sresourceUrl;
	 }
	 /**发布时间*/
	 public Date getTpublishDate(){
		 return tpublishDate;
	 }
	 /**发布时间*/
	 public void setTpublishDate(Date tpublishDate){
		 this.tpublishDate=tpublishDate;
	 }
	 /**发布作者*/
	 public String getSauthor(){
		 return sauthor;
	 }
	 /**发布作者*/
	 public void setSauthor(String sauthor){
		 this.sauthor=sauthor;
	 }
	 /**排序号从小到大，小的排前面*/
	 public Integer getIsort(){
		 return isort;
	 }
	 /**排序号从小到大，小的排前面*/
	 public void setIsort(Integer isort){
		 this.isort=isort;
	 }
	 /**总访问数*/
	 public Integer getIallCount(){
		 return iallCount;
	 }
	 /**总访问数*/
	 public void setIallCount(Integer iallCount){
		 this.iallCount=iallCount;
	 }
	 /**专题url*/
	 public String getStopicUrl(){
		 return stopicUrl;
	 }
	 /**专题url*/
	 public void setStopicUrl(String stopicUrl){
		 this.stopicUrl=stopicUrl;
	 }
	 /**关键字*/
	 public String getSkeys(){
		 return skeys;
	 }
	 /**关键字*/
	 public void setSkeys(String skeys){
		 this.skeys=skeys;
	 }
	 /**有效期*/
	 public Date getTvalidDate(){
		 return tvalidDate;
	 }
	 /**有效期*/
	 public void setTvalidDate(Date tvalidDate){
		 this.tvalidDate=tvalidDate;
	 }
	 /**栏目id*/
	 public String getSnavigationId(){
		 return snavigationId;
	 }
	 /**栏目id*/
	 public void setSnavigationId(String snavigationId){
		 this.snavigationId=snavigationId;
	 }
	 /**是否专题(预留)*/
	 public Integer getIistopic(){
		 return iistopic;
	 }
	 /**是否专题(预留)*/
	 public void setIistopic(Integer iistopic){
		 this.iistopic=iistopic;
	 }
	 /**是否推荐*/
	 public Integer getIisRecommend(){
		 return iisRecommend;
	 }
	 /**是否推荐*/
	 public void setIisRecommend(Integer iisRecommend){
		 this.iisRecommend=iisRecommend;
	 }
	 /**状态：10：待发布20：已发布*/
	 public Integer getIstatus(){
		 return istatus;
	 }
	 /**状态：10：待发布20：已发布*/
	 public void setIstatus(Integer istatus){
		 this.istatus=istatus;
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
	public String getSnavigationName() {
		return snavigationName;
	}
	public void setSnavigationName(String snavigationName) {
		this.snavigationName = snavigationName;
	}
	 
	

		
} 