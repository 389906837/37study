package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 平台接口信息管理表(OP_INTERFACE_INFO) **/
public class InterfaceInfo extends GenericEntity  {

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
	
	/* 添加日期 */
	private Date addTime;
	
	public Date getAddTime(){
		return addTime;
	}
	
	public void setAddTime(Date addTime){
		this.addTime= addTime;
	}
	/* 添加人 */
	private String addUser;
	
	public String getAddUser(){
		return addUser;
	}
	
	public void setAddUser(String addUser){
		this.addUser= addUser;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 回调最大次数 */
	private Integer imaxCallbackNum;
	
	public Integer getImaxCallbackNum(){
		return imaxCallbackNum;
	}
	
	public void setImaxCallbackNum(Integer imaxCallbackNum){
		this.imaxCallbackNum= imaxCallbackNum;
	}
	/* 接口收费类型
            10=按次计费
            20=按单位次数计费 （识别图片张数）
            30=时间计费
            40=通用账户
             */
	private Integer ipayType;
	
	public Integer getIpayType(){
		return ipayType;
	}
	
	public void setIpayType(Integer ipayType){
		this.ipayType= ipayType;
	}
	/* 接口收费方式
            10=免费无需开通
            20=免费需开通
            30=收费接口
             */
	private Integer ipayWay;
	
	public Integer getIpayWay(){
		return ipayWay;
	}
	
	public void setIpayWay(Integer ipayWay){
		this.ipayWay= ipayWay;
	}
	/* 接口状态
            10=待上线
            20=已上线
            30=已废弃 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 接口类型
            10=同步接口
            20=异步接口 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 接口动作 */
	private String saction;
	
	public String getSaction(){
		return saction;
	}
	
	public void setSaction(String saction){
		this.saction= saction;
	}
	/* 接口地址 */
	private String saddress;
	
	public String getSaddress(){
		return saddress;
	}
	
	public void setSaddress(String saddress){
		this.saddress= saddress;
	}
	/* 应用编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 接口描述 */
	private String sdesc;
	
	public String getSdesc(){
		return sdesc;
	}
	
	public void setSdesc(String sdesc){
		this.sdesc= sdesc;
	}
	/* 接口名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 触发通知参数 */
	private String snoticeParam;
	
	public String getSnoticeParam(){
		return snoticeParam;
	}
	
	public void setSnoticeParam(String snoticeParam){
		this.snoticeParam= snoticeParam;
	}
	/* 收费标准描述 */
	private String spayDesc;
	
	public String getSpayDesc(){
		return spayDesc;
	}
	
	public void setSpayDesc(String spayDesc){
		this.spayDesc= spayDesc;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 请求示例 */
	private String srequestExample;
	
	public String getSrequestExample(){
		return srequestExample;
	}
	
	public void setSrequestExample(String srequestExample){
		this.srequestExample= srequestExample;
	}
	/* 请求格式 */
	private String srequestFormat;
	
	public String getSrequestFormat(){
		return srequestFormat;
	}
	
	public void setSrequestFormat(String srequestFormat){
		this.srequestFormat= srequestFormat;
	}
	/* 请求限制 */
	private String srequestLimit;
	
	public String getSrequestLimit(){
		return srequestLimit;
	}
	
	public void setSrequestLimit(String srequestLimit){
		this.srequestLimit= srequestLimit;
	}
	/* 请求参数 */
	private String srequestParam;
	
	public String getSrequestParam(){
		return srequestParam;
	}
	
	public void setSrequestParam(String srequestParam){
		this.srequestParam= srequestParam;
	}
	/* 返回说明 */
	private String sresponseDesc;
	
	public String getSresponseDesc(){
		return sresponseDesc;
	}
	
	public void setSresponseDesc(String sresponseDesc){
		this.sresponseDesc= sresponseDesc;
	}
	/* 返回事例 */
	private String sresponseExample;
	
	public String getSresponseExample(){
		return sresponseExample;
	}
	
	public void setSresponseExample(String sresponseExample){
		this.sresponseExample= sresponseExample;
	}
	/* 返回格式 */
	private String sresponseFormat;
	
	public String getSresponseFormat(){
		return sresponseFormat;
	}
	
	public void setSresponseFormat(String sresponseFormat){
		this.sresponseFormat= sresponseFormat;
	}
	/* 返回参数 */
	private String sresponseParam;
	
	public String getSresponseParam(){
		return sresponseParam;
	}
	
	public void setSresponseParam(String sresponseParam){
		this.sresponseParam= sresponseParam;
	}
	/* 应用分属系统 10=云识别 20=人脸识别 */
	private String ssystemType;
	
	public String getSsystemType(){
		return ssystemType;
	}
	
	public void setSsystemType(String ssystemType){
		this.ssystemType= ssystemType;
	}
	/* 调用方式 */
	private String stransferWay;
	
	public String getStransferWay(){
		return stransferWay;
	}
	
	public void setStransferWay(String stransferWay){
		this.stransferWay= stransferWay;
	}
	/* 上线时间 */
	private Date tonlineTime;
	
	public Date getTonlineTime(){
		return tonlineTime;
	}
	
	public void setTonlineTime(Date tonlineTime){
		this.tonlineTime= tonlineTime;
	}
	/* 修改人 */
	private String upateUser;
	
	public String getUpateUser(){
		return upateUser;
	}
	
	public void setUpateUser(String upateUser){
		this.upateUser= upateUser;
	}
	/* 修改日期 */
	private Date updateTime;
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime= updateTime;
	}

		
}