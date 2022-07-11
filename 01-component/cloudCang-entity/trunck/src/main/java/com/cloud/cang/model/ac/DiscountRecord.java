package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 活动优惠记录表(AC_DISCOUNT_RECORD) **/
public class DiscountRecord extends GenericEntity  {

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
	
	/* 活动分类
            10:场景活动
            20:促销活动 */
	private Integer iacType;
	
	public Integer getIacType(){
		return iacType;
	}
	
	public void setIacType(Integer iacType){
		this.iacType= iacType;
	}
	/* 优惠类型
            10:首单
            20:打折满X元
            30:打折满X件
            40:打折满X元且满X件
            50:满减满X元
            60:满减每满X元
            70:满减满X件
            80:满减满X元且满Y件
            90:返券
            100:返现
             */
	private Integer idiscountType;
	
	public Integer getIdiscountType(){
		return idiscountType;
	}
	
	public void setIdiscountType(Integer idiscountType){
		this.idiscountType= idiscountType;
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
	/* 来源订单是否拆单 */
	private Integer iisDismantling;
	
	public Integer getIisDismantling(){
		return iisDismantling;
	}
	
	public void setIisDismantling(Integer iisDismantling){
		this.iisDismantling= iisDismantling;
	}
	/* 来源单据类型
            10:平台赠送
            20:会员注册
            30:首次开通支付宝免密
            40:首次开通微信免密
            50:推荐注册
            60:首次下单
            70:下单
            80:促销活动
             */
	private Integer isourceType;
	
	public Integer getIsourceType(){
		return isourceType;
	}
	
	public void setIsourceType(Integer isourceType){
		this.isourceType= isourceType;
	}
	/* 状态
            10  未使用
            20  已使用
            30  已过期 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 活动编号 */
	private String sacCode;
	
	public String getSacCode(){
		return sacCode;
	}
	
	public void setSacCode(String sacCode){
		this.sacCode= sacCode;
	}
	/* 活动ID */
	private String sacId;
	
	public String getSacId(){
		return sacId;
	}
	
	public void setSacId(String sacId){
		this.sacId= sacId;
	}
	/* 活动名称 */
	private String sacName;
	
	public String getSacName(){
		return sacName;
	}
	
	public void setSacName(String sacName){
		this.sacName= sacName;
	}
	/* 优惠记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 用户持有劵编号 */
	private String scouponCode;
	
	public String getScouponCode(){
		return scouponCode;
	}
	
	public void setScouponCode(String scouponCode){
		this.scouponCode= scouponCode;
	}
	/* 用户持有券ID */
	private String scouponId;
	
	public String getScouponId(){
		return scouponId;
	}
	
	public void setScouponId(String scouponId){
		this.scouponId= scouponId;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
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
	/* 来源单据编号 */
	private String ssourceCode;
	
	public String getSsourceCode(){
		return ssourceCode;
	}
	
	public void setSsourceCode(String ssourceCode){
		this.ssourceCode= ssourceCode;
	}
	/* 来源单据设备地址 */
	private String ssourceDeviceAddress;
	
	public String getSsourceDeviceAddress(){
		return ssourceDeviceAddress;
	}
	
	public void setSsourceDeviceAddress(String ssourceDeviceAddress){
		this.ssourceDeviceAddress= ssourceDeviceAddress;
	}
	/* 来源单据设备编号 */
	private String ssourceDeviceCode;
	
	public String getSsourceDeviceCode(){
		return ssourceDeviceCode;
	}
	
	public void setSsourceDeviceCode(String ssourceDeviceCode){
		this.ssourceDeviceCode= ssourceDeviceCode;
	}
	/* 来源单据设备ID */
	private String ssourceDeviceId;
	
	public String getSsourceDeviceId(){
		return ssourceDeviceId;
	}
	
	public void setSsourceDeviceId(String ssourceDeviceId){
		this.ssourceDeviceId= ssourceDeviceId;
	}
	/* 来源单据ID */
	private String ssourceId;
	
	public String getSsourceId(){
		return ssourceId;
	}
	
	public void setSsourceId(String ssourceId){
		this.ssourceId= ssourceId;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 参与优惠时间 */
	private Date tdiscountTime;
	
	public Date getTdiscountTime(){
		return tdiscountTime;
	}
	
	public void setTdiscountTime(Date tdiscountTime){
		this.tdiscountTime= tdiscountTime;
	}
	/* 优惠使用时间 */
	private Date tdiscountUsedTime;
	
	public Date getTdiscountUsedTime(){
		return tdiscountUsedTime;
	}
	
	public void setTdiscountUsedTime(Date tdiscountUsedTime){
		this.tdiscountUsedTime= tdiscountUsedTime;
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