package com.cloud.cang.model.hy;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 智能垃圾箱授权表(HY_TRASH_AUTHORISE) **/
public class TrashAuthorise extends GenericEntity  {

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
	
	/* 10：开启 20：关闭  */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 用户编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 用户ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 用户名 */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 第三方用户唯一标识 */
	private String sopenId;
	
	public String getSopenId(){
		return sopenId;
	}
	
	public void setSopenId(String sopenId){
		this.sopenId= sopenId;
	}
	/* 智能垃圾箱品牌 */
	private String strashBrand;
	
	public String getStrashBrand(){
		return strashBrand;
	}
	
	public void setStrashBrand(String strashBrand){
		this.strashBrand= strashBrand;
	}
	/* 智能垃圾箱编号 */
	private String strashCode;
	
	public String getStrashCode(){
		return strashCode;
	}
	
	public void setStrashCode(String strashCode){
		this.strashCode= strashCode;
	}
	/* 授权时间 */
	private Date tauthoriseTime;
	
	public Date getTauthoriseTime(){
		return tauthoriseTime;
	}
	
	public void setTauthoriseTime(Date tauthoriseTime){
		this.tauthoriseTime= tauthoriseTime;
	}
	/* 最近一次取消授权时间 */
	private Date tlastCloseTauthoriseTime;
	
	public Date getTlastCloseTauthoriseTime(){
		return tlastCloseTauthoriseTime;
	}
	
	public void setTlastCloseTauthoriseTime(Date tlastCloseTauthoriseTime){
		this.tlastCloseTauthoriseTime= tlastCloseTauthoriseTime;
	}

		
}