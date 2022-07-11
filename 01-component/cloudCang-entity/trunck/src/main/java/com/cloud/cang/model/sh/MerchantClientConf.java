package com.cloud.cang.model.sh;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户客户端配置 前端配置(SH_MERCHANT_CLIENT_CONF) **/
public class MerchantClientConf extends GenericEntity  {

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
	
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 支付宝是否配置0否1是 */
	private Integer iisConfAlipay;
	
	public Integer getIisConfAlipay(){
		return iisConfAlipay;
	}
	
	public void setIisConfAlipay(Integer iisConfAlipay){
		this.iisConfAlipay= iisConfAlipay;
	}
	/* 支付宝生活号是否配置0否1是 */
	private Integer iisConfAlipayShh;
	
	public Integer getIisConfAlipayShh(){
		return iisConfAlipayShh;
	}
	
	public void setIisConfAlipayShh(Integer iisConfAlipayShh){
		this.iisConfAlipayShh= iisConfAlipayShh;
	}
	/* 微信支付是否配置0否1是 */
	private Integer iisConfWechat;
	
	public Integer getIisConfWechat(){
		return iisConfWechat;
	}
	
	public void setIisConfWechat(Integer iisConfWechat){
		this.iisConfWechat= iisConfWechat;
	}
	/* 微信公众号是否配置0否1是 */
	private Integer iisConfWechatGzh;
	
	public Integer getIisConfWechatGzh(){
		return iisConfWechatGzh;
	}
	
	public void setIisConfWechatGzh(Integer iisConfWechatGzh){
		this.iisConfWechatGzh= iisConfWechatGzh;
	}
	/* 本地补货是否开启实时盘货 */
	private Integer iisLocalInventory;
	
	public Integer getIisLocalInventory(){
		return iisLocalInventory;
	}
	
	public void setIisLocalInventory(Integer iisLocalInventory){
		this.iisLocalInventory= iisLocalInventory;
	}
	/* 云端补货是否开启实时盘货 */
	private Integer iisOpeningInventory;
	
	public Integer getIisOpeningInventory(){
		return iisOpeningInventory;
	}
	
	public void setIisOpeningInventory(Integer iisOpeningInventory){
		this.iisOpeningInventory= iisOpeningInventory;
	}
	/* 退款是否审核 0否 1是 */
	private Integer iisRefundAudit;
	
	public Integer getIisRefundAudit(){
		return iisRefundAudit;
	}
	
	public void setIisRefundAudit(Integer iisRefundAudit){
		this.iisRefundAudit= iisRefundAudit;
	}
	/* 补货是否需要确认补货单
0 否
1 是 */
	private Integer iisReplenConfirm;
	
	public Integer getIisReplenConfirm(){
		return iisReplenConfirm;
	}
	
	public void setIisReplenConfirm(Integer iisReplenConfirm){
		this.iisReplenConfirm= iisReplenConfirm;
	}
	/* 分账功能是否开启0否1是 */
	private Integer iisSeparateAccounts;
	
	public Integer getIisSeparateAccounts(){
		return iisSeparateAccounts;
	}
	
	public void setIisSeparateAccounts(Integer iisSeparateAccounts){
		this.iisSeparateAccounts= iisSeparateAccounts;
	}
	/* 支持支付方式
            10=全部
            20=仅代扣
            30=仅手动
             */
	private Integer isupportPayWay;
	
	public Integer getIsupportPayWay(){
		return isupportPayWay;
	}
	
	public void setIsupportPayWay(Integer isupportPayWay){
		this.isupportPayWay= isupportPayWay;
	}
	/* 称重稳定盘货次数 */
	private Integer iweightStockNum;
	
	public Integer getIweightStockNum(){
		return iweightStockNum;
	}
	
	public void setIweightStockNum(Integer iweightStockNum){
		this.iweightStockNum= iweightStockNum;
	}
	/* 代扣方式
10=商户代扣
20=单次代扣 */
	private Integer iwithholdingWay;
	
	public Integer getIwithholdingWay(){
		return iwithholdingWay;
	}
	
	public void setIwithholdingWay(Integer iwithholdingWay){
		this.iwithholdingWay= iwithholdingWay;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 批量发券审核人员手机号 多个,隔开 */
	private String sauditSendCoupon;
	
	public String getSauditSendCoupon(){
		return sauditSendCoupon;
	}
	
	public void setSauditSendCoupon(String sauditSendCoupon){
		this.sauditSendCoupon= sauditSendCoupon;
	}
	/* 云端识别APPID */
	private String scloudAppId;
	
	public String getScloudAppId(){
		return scloudAppId;
	}
	
	public void setScloudAppId(String scloudAppId){
		this.scloudAppId= scloudAppId;
	}
	/* 云端识别访问地址 */
	private String scloudHost;
	
	public String getScloudHost(){
		return scloudHost;
	}
	
	public void setScloudHost(String scloudHost){
		this.scloudHost= scloudHost;
	}
	/* 商户编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 整体颜色值 */
	private String scolor;
	
	public String getScolor(){
		return scolor;
	}
	
	public void setScolor(String scolor){
		this.scolor= scolor;
	}
	/* 商户联系电话 */
	private String scontactMobile;
	
	public String getScontactMobile(){
		return scontactMobile;
	}
	
	public void setScontactMobile(String scontactMobile){
		this.scontactMobile= scontactMobile;
	}
	/* 商户客服服务时间 格式 HH:mm - HH:mm */
	private String scustomerServiceTime;
	
	public String getScustomerServiceTime(){
		return scustomerServiceTime;
	}
	
	public void setScustomerServiceTime(String scustomerServiceTime){
		this.scustomerServiceTime= scustomerServiceTime;
	}
	/* 分账比例0-100 */
	private Integer separateAccountsPro;
	
	public Integer getSeparateAccountsPro(){
		return separateAccountsPro;
	}
	
	public void setSeparateAccountsPro(Integer separateAccountsPro){
		this.separateAccountsPro= separateAccountsPro;
	}
	/* 首页背景图片URL */
	private String sindexBgurl;
	
	public String getSindexBgurl(){
		return sindexBgurl;
	}
	
	public void setSindexBgurl(String sindexBgurl){
		this.sindexBgurl= sindexBgurl;
	}
	/* 首页提示语 */
	private String sindexHint;
	
	public String getSindexHint(){
		return sindexHint;
	}
	
	public void setSindexHint(String sindexHint){
		this.sindexHint= sindexHint;
	}
	/* 登录LOGO */
	private String sloginLogo;
	
	public String getSloginLogo(){
		return sloginLogo;
	}
	
	public void setSloginLogo(String sloginLogo){
		this.sloginLogo= sloginLogo;
	}
	/* 商户LOGO */
	private String slogo;
	
	public String getSlogo(){
		return slogo;
	}
	
	public void setSlogo(String slogo){
		this.slogo= slogo;
	}
	/* 公司简称 */
	private String sshortName;
	
	public String getSshortName(){
		return sshortName;
	}
	
	public void setSshortName(String sshortName){
		this.sshortName= sshortName;
	}
	/* 开门背景图片URL */
	private String ssuccessBgurl;
	
	public String getSsuccessBgurl(){
		return ssuccessBgurl;
	}
	
	public void setSsuccessBgurl(String ssuccessBgurl){
		this.ssuccessBgurl= ssuccessBgurl;
	}
	/* 开门成功提示语 */
	private String ssuccessHint;
	
	public String getSsuccessHint(){
		return ssuccessHint;
	}
	
	public void setSsuccessHint(String ssuccessHint){
		this.ssuccessHint= ssuccessHint;
	}
	/* 客户端TITLE */
	private String stitle;
	
	public String getStitle(){
		return stitle;
	}
	
	public void setStitle(String stitle){
		this.stitle= stitle;
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