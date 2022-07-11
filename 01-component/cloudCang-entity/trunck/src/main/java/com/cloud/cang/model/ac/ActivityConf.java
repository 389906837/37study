package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 活动配置表(AC_ACTIVITY_CONF) **/
public class ActivityConf extends GenericEntity  {

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
	
	/* 活动期间总参与次数(0不限制) */
	private Integer iallCount;
	
	public Integer getIallCount(){
		return iallCount;
	}
	
	public void setIallCount(Integer iallCount){
		this.iallCount= iallCount;
	}
	/* 次数限制类型
            10：活动期间用户
            20：活动期间设备 */
	private Integer icountType;
	
	public Integer getIcountType(){
		return icountType;
	}
	
	public void setIcountType(Integer icountType){
		this.icountType= icountType;
	}
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 优惠方式
            10：首单
            20：打折
            30：满减
            40：返券
            50：返现 */
	private Integer idiscountWay;
	
	public Integer getIdiscountWay(){
		return idiscountWay;
	}
	
	public void setIdiscountWay(Integer idiscountWay){
		this.idiscountWay= idiscountWay;
	}
	/* 是否启用  -1 关闭 0 草稿 1 启用 */
	private Integer iisAvailable;
	
	public Integer getIisAvailable(){
		return iisAvailable;
	}
	
	public void setIisAvailable(Integer iisAvailable){
		this.iisAvailable= iisAvailable;
	}
	/* 优惠是否叠加(首单) */
	private Integer iisSuperposition;
	
	public Integer getIisSuperposition(){
		return iisSuperposition;
	}
	
	public void setIisSuperposition(Integer iisSuperposition){
		this.iisSuperposition= iisSuperposition;
	}
	/* 已参与人数（活动优惠记录统计） */
	private Integer ijoinNum;
	
	public Integer getIjoinNum(){
		return ijoinNum;
	}
	
	public void setIjoinNum(Integer ijoinNum){
		this.ijoinNum= ijoinNum;
	}
	/* 应用范围类型
            10:全部
            20:部分设备
            30:部分商品
            40:部分设备的部分商品 */
	private Integer irangeType;
	
	public Integer getIrangeType(){
		return irangeType;
	}
	
	public void setIrangeType(Integer irangeType){
		this.irangeType= irangeType;
	}
	/* 场景活动类型
            下单
            登录
            注册
            ...数据字典配置 */
	private Integer iscenesType;
	
	public Integer getIscenesType(){
		return iscenesType;
	}
	
	public void setIscenesType(Integer iscenesType){
		this.iscenesType= iscenesType;
	}
	/* 活动分类
            10:场景活动
            20:促销活动 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 已使用人数（活动优惠记录统计） */
	private Integer iusedNum;
	
	public Integer getIusedNum(){
		return iusedNum;
	}
	
	public void setIusedNum(Integer iusedNum){
		this.iusedNum= iusedNum;
	}
	/* 活动期间（用户、设备）总参与次数（0不限制） 根据次数限制类型判断 */
	private Integer iuserAllCount;
	
	public Integer getIuserAllCount(){
		return iuserAllCount;
	}
	
	public void setIuserAllCount(Integer iuserAllCount){
		this.iuserAllCount= iuserAllCount;
	}
	/* 单日（用户、设备）总参与次数上限（0不限制） 根据次数限制类型判断 */
	private Integer iuserDayCount;
	
	public Integer getIuserDayCount(){
		return iuserDayCount;
	}
	
	public void setIuserDayCount(Integer iuserDayCount){
		this.iuserDayCount= iuserDayCount;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 活动配置编号 */
	private String sconfCode;
	
	public String getSconfCode(){
		return sconfCode;
	}
	
	public void setSconfCode(String sconfCode){
		this.sconfCode= sconfCode;
	}
	/* 活动配置名称 */
	private String sconfName;
	
	public String getSconfName(){
		return sconfName;
	}
	
	public void setSconfName(String sconfName){
		this.sconfName= sconfName;
	}
	/* 描述 */
	private String sdescription;
	
	public String getSdescription(){
		return sdescription;
	}
	
	public void setSdescription(String sdescription){
		this.sdescription= sdescription;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
	}
	/* 活动结束时间 */
	private Date tactiveEndTime;
	
	public Date getTactiveEndTime(){
		return tactiveEndTime;
	}
	
	public void setTactiveEndTime(Date tactiveEndTime){
		this.tactiveEndTime= tactiveEndTime;
	}
	/* 活动开始时间 */
	private Date tactiveStartTime;
	
	public Date getTactiveStartTime(){
		return tactiveStartTime;
	}
	
	public void setTactiveStartTime(Date tactiveStartTime){
		this.tactiveStartTime= tactiveStartTime;
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