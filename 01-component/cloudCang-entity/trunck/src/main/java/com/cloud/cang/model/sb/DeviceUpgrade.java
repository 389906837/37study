package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备升级记录(SB_DEVICE_UPGRADE) **/
public class DeviceUpgrade extends GenericEntity  {

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
	
	/* 升级执行类型
            10=立即
            20=定时 */
	private Integer iactionType;
	
	public Integer getIactionType(){
		return iactionType;
	}
	
	public void setIactionType(Integer iactionType){
		this.iactionType= iactionType;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 本次升级设备总数量 */
	private Integer ideviceNum;
	
	public Integer getIdeviceNum(){
		return ideviceNum;
	}
	
	public void setIdeviceNum(Integer ideviceNum){
		this.ideviceNum= ideviceNum;
	}
	/* 升级设备类型
            10=全部
            20=部分 */
	private Integer ideviceType;
	
	public Integer getIdeviceType(){
		return ideviceType;
	}
	
	public void setIdeviceType(Integer ideviceType){
		this.ideviceType= ideviceType;
	}
	/* 通知完成设备总数量 */
	private Integer inoticeNum;
	
	public Integer getInoticeNum(){
		return inoticeNum;
	}
	
	public void setInoticeNum(Integer inoticeNum){
		this.inoticeNum= inoticeNum;
	}
	/* 状态 
            10=草稿
            20=已通知
            30=已完成
            40=已取消
            50=部分完成 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 升级类型
            10=视觉服务升级
            20=视觉库升级
            30=客户端APK升级 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 升级失败设备总数量 */
	private Integer iupgradeFailNum;
	
	public Integer getIupgradeFailNum(){
		return iupgradeFailNum;
	}
	
	public void setIupgradeFailNum(Integer iupgradeFailNum){
		this.iupgradeFailNum= iupgradeFailNum;
	}
	/* 升级成功设备总数量 */
	private Integer iupgradeSuccessNum;
	
	public Integer getIupgradeSuccessNum(){
		return iupgradeSuccessNum;
	}
	
	public void setIupgradeSuccessNum(Integer iupgradeSuccessNum){
		this.iupgradeSuccessNum= iupgradeSuccessNum;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 处理人 */
	private String sdealwithMan;
	
	public String getSdealwithMan(){
		return sdealwithMan;
	}
	
	public void setSdealwithMan(String sdealwithMan){
		this.sdealwithMan= sdealwithMan;
	}
	/* 处理时间 */
	private Date sdealwithTime;
	
	public Date getSdealwithTime(){
		return sdealwithTime;
	}
	
	public void setSdealwithTime(Date sdealwithTime){
		this.sdealwithTime= sdealwithTime;
	}
	/* 定时执行时间 */
	private Date sexecutionTime;
	
	public Date getSexecutionTime(){
		return sexecutionTime;
	}
	
	public void setSexecutionTime(Date sexecutionTime){
		this.sexecutionTime= sexecutionTime;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 升级资源地址 */
	private String sresourcesUrl;
	
	public String getSresourcesUrl(){
		return sresourcesUrl;
	}
	
	public void setSresourcesUrl(String sresourcesUrl){
		this.sresourcesUrl= sresourcesUrl;
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