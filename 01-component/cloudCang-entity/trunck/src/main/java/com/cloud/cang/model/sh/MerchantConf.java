package com.cloud.cang.model.sh;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户支付、公众号配置信息(SH_MERCHANT_CONF) **/
public class MerchantConf extends GenericEntity  {

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
	
	/* 单日支付限额 */
	private BigDecimal fpayLimitDay;
	
	public BigDecimal getFpayLimitDay(){
		return fpayLimitDay;
	}
	
	public void setFpayLimitDay(BigDecimal fpayLimitDay){
		this.fpayLimitDay= fpayLimitDay;
	}
	/* 单笔多大金额短信确认 */
	private BigDecimal fpayLimitMoney;
	
	public BigDecimal getFpayLimitMoney(){
		return fpayLimitMoney;
	}
	
	public void setFpayLimitMoney(BigDecimal fpayLimitMoney){
		this.fpayLimitMoney= fpayLimitMoney;
	}
	/* 单月支付限额 */
	private BigDecimal fpayLimitMonth;
	
	public BigDecimal getFpayLimitMonth(){
		return fpayLimitMonth;
	}
	
	public void setFpayLimitMonth(BigDecimal fpayLimitMonth){
		this.fpayLimitMonth= fpayLimitMonth;
	}
	/* 单笔支付限额 */
	private BigDecimal fpayLimitSingle;
	
	public BigDecimal getFpayLimitSingle(){
		return fpayLimitSingle;
	}
	
	public void setFpayLimitSingle(BigDecimal fpayLimitSingle){
		this.fpayLimitSingle= fpayLimitSingle;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 10=微信支付
            20=支付宝
             */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 微信代扣方式 10:签约免密  20:微信支付分 */
	private Integer iwechatWithholdType;
	
	public Integer getIwechatWithholdType(){
		return iwechatWithholdType;
	}
	
	public void setIwechatWithholdType(Integer iwechatWithholdType){
		this.iwechatWithholdType= iwechatWithholdType;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 支付宝账号 */
	private String salipayAccount;
	
	public String getSalipayAccount(){
		return salipayAccount;
	}
	
	public void setSalipayAccount(String salipayAccount){
		this.salipayAccount= salipayAccount;
	}
	/* APPID */
	private String sappId;
	
	public String getSappId(){
		return sappId;
	}
	
	public void setSappId(String sappId){
		this.sappId= sappId;
	}
	/* 支付宝/微信应用公钥 */
	private String sappPublicKey;
	
	public String getSappPublicKey(){
		return sappPublicKey;
	}
	
	public void setSappPublicKey(String sappPublicKey){
		this.sappPublicKey= sappPublicKey;
	}
	/* MD5秘钥 */
	private String sappSecret;
	
	public String getSappSecret(){
		return sappSecret;
	}
	
	public void setSappSecret(String sappSecret){
		this.sappSecret= sappSecret;
	}
	/* 支付宝第三方授权TOKEN */
	private String sauthToken;
	
	public String getSauthToken(){
		return sauthToken;
	}
	
	public void setSauthToken(String sauthToken){
		this.sauthToken= sauthToken;
	}
	/* 授权回调URL */
	private String scallBackUrl;
	
	public String getScallBackUrl(){
		return scallBackUrl;
	}
	
	public void setScallBackUrl(String scallBackUrl){
		this.scallBackUrl= scallBackUrl;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户信息ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 支付回调地址 */
	private String spayCallBackUrl;
	
	public String getSpayCallBackUrl(){
		return spayCallBackUrl;
	}
	
	public void setSpayCallBackUrl(String spayCallBackUrl){
		this.spayCallBackUrl= spayCallBackUrl;
	}
	/* 微信签名KEY-pay.weixin.key */
	private String spayWechatKey;
	
	public String getSpayWechatKey(){
		return spayWechatKey;
	}
	
	public void setSpayWechatKey(String spayWechatKey){
		this.spayWechatKey= spayWechatKey;
	}
	/* 支付宝合作伙伴ID */
	private String spid;
	
	public String getSpid(){
		return spid;
	}
	
	public void setSpid(String spid){
		this.spid= spid;
	}
	/* 微信代扣模板ID */
	private String splanId;
	
	public String getSplanId(){
		return splanId;
	}
	
	public void setSplanId(String splanId){
		this.splanId= splanId;
	}
	/* 支付宝/微信应用私钥 */
	private String sprivateKey;
	
	public String getSprivateKey(){
		return sprivateKey;
	}
	
	public void setSprivateKey(String sprivateKey){
		this.sprivateKey= sprivateKey;
	}
	/* 支付宝/微信公钥 */
	private String spublicKey;
	
	public String getSpublicKey(){
		return spublicKey;
	}
	
	public void setSpublicKey(String spublicKey){
		this.spublicKey= spublicKey;
	}
	/* 微信支付分商户服务ID */
	private String sserviceId;
	
	public String getSserviceId(){
		return sserviceId;
	}
	
	public void setSserviceId(String sserviceId){
		this.sserviceId= sserviceId;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 微信账号 */
	private String swechatAccount;
	
	public String getSwechatAccount(){
		return swechatAccount;
	}
	
	public void setSwechatAccount(String swechatAccount){
		this.swechatAccount= swechatAccount;
	}
	/* 微信APIV3证书序列号 */
	private String swechatApiv3CertNumber;
	
	public String getSwechatApiv3CertNumber(){
		return swechatApiv3CertNumber;
	}
	
	public void setSwechatApiv3CertNumber(String swechatApiv3CertNumber){
		this.swechatApiv3CertNumber= swechatApiv3CertNumber;
	}
	/* 微信APIV3密钥 */
	private String swechatApiv3Key;
	
	public String getSwechatApiv3Key(){
		return swechatApiv3Key;
	}
	
	public void setSwechatApiv3Key(String swechatApiv3Key){
		this.swechatApiv3Key= swechatApiv3Key;
	}
	/* 证书密码 */
	private String swechatCertPwd;
	
	public String getSwechatCertPwd(){
		return swechatCertPwd;
	}
	
	public void setSwechatCertPwd(String swechatCertPwd){
		this.swechatCertPwd= swechatCertPwd;
	}
	/* 微信证书地址 */
	private String swechatCertUrl;
	
	public String getSwechatCertUrl(){
		return swechatCertUrl;
	}
	
	public void setSwechatCertUrl(String swechatCertUrl){
		this.swechatCertUrl= swechatCertUrl;
	}
	/* 微信支付分APPID */
	private String swechatPointAppid;
	
	public String getSwechatPointAppid(){
		return swechatPointAppid;
	}
	
	public void setSwechatPointAppid(String swechatPointAppid){
		this.swechatPointAppid= swechatPointAppid;
	}
	/* 微信支付分MD5秘钥 */
	private String swechatPointAppSecret;
	
	public String getSwechatPointAppSecret(){
		return swechatPointAppSecret;
	}
	
	public void setSwechatPointAppSecret(String swechatPointAppSecret){
		this.swechatPointAppSecret= swechatPointAppSecret;
	}
	/* 微信APIV3私钥地址 */
	private String swechatPrivateKeyUrl;
	
	public String getSwechatPrivateKeyUrl(){
		return swechatPrivateKeyUrl;
	}
	
	public void setSwechatPrivateKeyUrl(String swechatPrivateKeyUrl){
		this.swechatPrivateKeyUrl= swechatPrivateKeyUrl;
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