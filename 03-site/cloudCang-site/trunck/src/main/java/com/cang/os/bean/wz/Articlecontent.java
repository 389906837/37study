package com.cang.os.bean.wz;

import com.cang.os.bean.BaseBean;

/** 文章内容表(ARTICLECONTENT) **/
public class Articlecontent extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键ID
	private String scontent;   //内容
	private String sarticleId;   //文章字段id
	
	//-----get set-----
	 /**主键ID*/
	 public String getId(){
		 return id;
	 }
	 /**主键ID*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**内容*/
	 public String getScontent(){
		 return scontent;
	 }
	 /**内容*/
	 public void setScontent(String scontent){
		 this.scontent=scontent;
	 }
	 /**文章字段id*/
	 public String getSarticleId(){
		 return sarticleId;
	 }
	 /**文章字段id*/
	 public void setSarticleId(String sarticleId){
		 this.sarticleId=sarticleId;
	 }
	

		
} 