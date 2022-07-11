package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员基础信息表(HY_MEMBER_INFO) **/
public class MemberInfo extends GenericEntity  {

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
	/* 会员生日 */
	private Date dbirthdate;
	
	public Date getDbirthdate(){
		return dbirthdate;
	}
	
	public void setDbirthdate(Date dbirthdate){
		this.dbirthdate= dbirthdate;
	}
	/* 支付宝免密是否开通0否1是 */
	private Integer iaipayOpen;
	
	public Integer getIaipayOpen(){
		return iaipayOpen;
	}
	
	public void setIaipayOpen(Integer iaipayOpen){
		this.iaipayOpen= iaipayOpen;
	}
	/* 证件类型
            10=身份证
            20=营业执照 */
	private Integer icardType;
	
	public Integer getIcardType(){
		return icardType;
	}
	
	public void setIcardType(Integer icardType){
		this.icardType= icardType;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 首单平台 10 ：微信支付
            20 ：支付宝
            30 ：APP
            数据字典配置 */
	private Integer ifirstOrderPlatform;
	
	public Integer getIfirstOrderPlatform(){
		return ifirstOrderPlatform;
	}
	
	public void setIfirstOrderPlatform(Integer ifirstOrderPlatform){
		this.ifirstOrderPlatform= ifirstOrderPlatform;
	}
	/* 是否后台添加0 否1是 */
	private Integer iisBackstageAdd;
	
	public Integer getIisBackstageAdd(){
		return iisBackstageAdd;
	}
	
	public void setIisBackstageAdd(Integer iisBackstageAdd){
		this.iisBackstageAdd= iisBackstageAdd;
	}
	/* 推荐奖励是否启用 */
	private Integer iisEnableRecoaward;
	
	public Integer getIisEnableRecoaward(){
		return iisEnableRecoaward;
	}
	
	public void setIisEnableRecoaward(Integer iisEnableRecoaward){
		this.iisEnableRecoaward= iisEnableRecoaward;
	}
	/* 是否首次绑卡0否1是 */
	private Integer iisFirstTieCard;
	
	public Integer getIisFirstTieCard(){
		return iisFirstTieCard;
	}
	
	public void setIisFirstTieCard(Integer iisFirstTieCard){
		this.iisFirstTieCard= iisFirstTieCard;
	}
	/* 是否已首单 0否1是 */
	private Integer iisOrder;
	
	public Integer getIisOrder(){
		return iisOrder;
	}
	
	public void setIisOrder(Integer iisOrder){
		this.iisOrder= iisOrder;
	}
	/* 是否注册人脸识别功能
            0：否
            1：是 */
	private Integer iisRegFace;
	
	public Integer getIisRegFace(){
		return iisRegFace;
	}
	
	public void setIisRegFace(Integer iisRegFace){
		this.iisRegFace= iisRegFace;
	}
	/* 是否补货员0否1是(如果是新增补货员权限) */
	private Integer iisReplenishment;
	
	public Integer getIisReplenishment(){
		return iisReplenishment;
	}
	
	public void setIisReplenishment(Integer iisReplenishment){
		this.iisReplenishment= iisReplenishment;
	}
	/* 是否实名认证0否1是 */
	private Integer iisVerified;
	
	public Integer getIisVerified(){
		return iisVerified;
	}
	
	public void setIisVerified(Integer iisVerified){
		this.iisVerified= iisVerified;
	}
	/* 最近购物平台 10 ：微信支付
            20 ：支付宝
            30 ：APP
            数据字典配置 */
	private Integer ilastShopPlatform;
	
	public Integer getIlastShopPlatform(){
		return ilastShopPlatform;
	}
	
	public void setIlastShopPlatform(Integer ilastShopPlatform){
		this.ilastShopPlatform= ilastShopPlatform;
	}
	/* 数据字典配置
            10：大众会员
            20：黄金会员
            30：铂金会员
            40：钻石会员 */
	private Integer imemberLevel;
	
	public Integer getImemberLevel(){
		return imemberLevel;
	}
	
	public void setImemberLevel(Integer imemberLevel){
		this.imemberLevel= imemberLevel;
	}
	/* 10：购物会员
            20：补货会员 */
	private Integer imemberType;
	
	public Integer getImemberType(){
		return imemberType;
	}
	
	public void setImemberType(Integer imemberType){
		this.imemberType= imemberType;
	}
	/* 注册平台 10 ：微信支付
            20 ：支付宝
            30：微信公众号
            40：支付宝生活号
            50：APP android
            60：APP ios
            数据字典配置 */
	private Integer iregClientType;
	
	public Integer getIregClientType(){
		return iregClientType;
	}
	
	public void setIregClientType(Integer iregClientType){
		this.iregClientType= iregClientType;
	}
	/* 会员性别 0 女 1 男 */
	private Integer isex;
	
	public Integer getIsex(){
		return isex;
	}
	
	public void setIsex(Integer isex){
		this.isex= isex;
	}
	/* 用户状态：1：正常 2：注销停用 3：系统黑名单 4：冻结 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 微信支付免密是否开通0否1是 */
	private Integer iwechatOpen;
	
	public Integer getIwechatOpen(){
		return iwechatOpen;
	}
	
	public void setIwechatOpen(Integer iwechatOpen){
		this.iwechatOpen= iwechatOpen;
	}
	/* 微信支付分是否开通 
0否 1是 */
	private Integer iwechatPointOpen;
	
	public Integer getIwechatPointOpen(){
		return iwechatPointOpen;
	}
	
	public void setIwechatPointOpen(Integer iwechatPointOpen){
		this.iwechatPointOpen= iwechatPointOpen;
	}
	/* 会员编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 注册设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 邮箱 */
	private String semail;
	
	public String getSemail(){
		return semail;
	}
	
	public void setSemail(String semail){
		this.semail= semail;
	}
	/* 会员头像地址 */
	private String sheadImage;
	
	public String getSheadImage(){
		return sheadImage;
	}
	
	public void setSheadImage(String sheadImage){
		this.sheadImage= sheadImage;
	}
	/* 身份证号 */
	private String sidCard;
	
	public String getSidCard(){
		return sidCard;
	}
	
	public void setSidCard(String sidCard){
		this.sidCard= sidCard;
	}
	/* 登录密码 */
	private String sloginPwd;
	
	public String getSloginPwd(){
		return sloginPwd;
	}
	
	public void setSloginPwd(String sloginPwd){
		this.sloginPwd= sloginPwd;
	}
	/* 用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 会员注册IP */
	private String smemberRegip;
	
	public String getSmemberRegip(){
		return smemberRegip;
	}
	
	public void setSmemberRegip(String smemberRegip){
		this.smemberRegip= smemberRegip;
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
	/* 手机号码 */
	private String smobile;
	
	public String getSmobile(){
		return smobile;
	}
	
	public void setSmobile(String smobile){
		this.smobile= smobile;
	}
	/* 会员昵称 */
	private String snickName;
	
	public String getSnickName(){
		return snickName;
	}
	
	public void setSnickName(String snickName){
		this.snickName= snickName;
	}
	/* 真实姓名 */
	private String srealName;
	
	public String getSrealName(){
		return srealName;
	}
	
	public void setSrealName(String srealName){
		this.srealName= srealName;
	}
	/* 推荐编号（自己） */
	private String srecommonCode;
	
	public String getSrecommonCode(){
		return srecommonCode;
	}
	
	public void setSrecommonCode(String srecommonCode){
		this.srecommonCode= srecommonCode;
	}
	/* 推荐人编号 */
	private String srecommonMbrCode;
	
	public String getSrecommonMbrCode(){
		return srecommonMbrCode;
	}
	
	public void setSrecommonMbrCode(String srecommonMbrCode){
		this.srecommonMbrCode= srecommonMbrCode;
	}
	/* 注册设备编号 */
	private String sregDeviceCode;
	
	public String getSregDeviceCode(){
		return sregDeviceCode;
	}
	
	public void setSregDeviceCode(String sregDeviceCode){
		this.sregDeviceCode= sregDeviceCode;
	}
	/* 来源渠道编号 */
	private String ssourceCode;
	
	public String getSsourceCode(){
		return ssourceCode;
	}
	
	public void setSsourceCode(String ssourceCode){
		this.ssourceCode= ssourceCode;
	}
	/* 指定数据字典【来源类型】key */
	private String ssourceType;
	
	public String getSsourceType(){
		return ssourceType;
	}
	
	public void setSsourceType(String ssourceType){
		this.ssourceType= ssourceType;
	}
	/* 指定数据字典【来源类型】值 */
	private String ssourceTypeName;
	
	public String getSsourceTypeName(){
		return ssourceTypeName;
	}
	
	public void setSsourceTypeName(String ssourceTypeName){
		this.ssourceTypeName= ssourceTypeName;
	}
	/* 交易密码 */
	private String stradePwd;
	
	public String getStradePwd(){
		return stradePwd;
	}
	
	public void setStradePwd(String stradePwd){
		this.stradePwd= stradePwd;
	}
	/* 首单时间 */
	private Date tfirstOrderTime;
	
	public Date getTfirstOrderTime(){
		return tfirstOrderTime;
	}
	
	public void setTfirstOrderTime(Date tfirstOrderTime){
		this.tfirstOrderTime= tfirstOrderTime;
	}
	/* 最近购物时间 */
	private Date tlastShopTime;
	
	public Date getTlastShopTime(){
		return tlastShopTime;
	}
	
	public void setTlastShopTime(Date tlastShopTime){
		this.tlastShopTime= tlastShopTime;
	}
	/* 实名认证时间 */
	private Date trealNameTime;
	
	public Date getTrealNameTime(){
		return trealNameTime;
	}
	
	public void setTrealNameTime(Date trealNameTime){
		this.trealNameTime= trealNameTime;
	}
	/* 注册时间 */
	private Date tregisterTime;
	
	public Date getTregisterTime(){
		return tregisterTime;
	}
	
	public void setTregisterTime(Date tregisterTime){
		this.tregisterTime= tregisterTime;
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

	/* 第三方用户ID */
	private String sthirdUserId;

	public String getSthirdUserId() {
		return sthirdUserId;
	}

	public void setSthirdUserId(String sthirdUserId) {
		this.sthirdUserId = sthirdUserId;
	}
}