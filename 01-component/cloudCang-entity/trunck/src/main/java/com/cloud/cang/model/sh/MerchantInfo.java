package com.cloud.cang.model.sh;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商户基础信息表(SH_MERCHANT_INFO) **/
public class MerchantInfo extends GenericEntity  {

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
	
	/* 合约到期日 */
	private Date dexpireDate;
	
	public Date getDexpireDate(){
		return dexpireDate;
	}
	
	public void setDexpireDate(Date dexpireDate){
		this.dexpireDate= dexpireDate;
	}
	/* 签约日期 */
	private Date dsignDate;
	
	public Date getDsignDate(){
		return dsignDate;
	}
	
	public void setDsignDate(Date dsignDate){
		this.dsignDate= dsignDate;
	}
	/* 公司类型
            10:国企
            20:民营 */
	private Integer icompanyType;
	
	public Integer getIcompanyType(){
		return icompanyType;
	}
	
	public void setIcompanyType(Integer icompanyType){
		this.icompanyType= icompanyType;
	}
	/* 合作模式
            10=采购
            20=租用
            数据字典配置 */
	private Integer icooperationMode;
	
	public Integer getIcooperationMode(){
		return icooperationMode;
	}
	
	public void setIcooperationMode(Integer icooperationMode){
		this.icooperationMode= icooperationMode;
	}
	/* 10=公司
            20=个人 */
	private Integer idbWap;
	
	public Integer getIdbWap(){
		return idbWap;
	}
	
	public void setIdbWap(Integer idbWap){
		this.idbWap= idbWap;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 二级分销开关 0否1是 */
	private Integer idistributionSwitch;
	
	public Integer getIdistributionSwitch(){
		return idistributionSwitch;
	}
	
	public void setIdistributionSwitch(Integer idistributionSwitch){
		this.idistributionSwitch= idistributionSwitch;
	}
	/* 是否父级商户 0否1是 */
	private Integer iisParent;
	
	public Integer getIisParent(){
		return iisParent;
	}
	
	public void setIisParent(Integer iisParent){
		this.iisParent= iisParent;
	}
	/* 返佣比例、方式=固定时代表金额 */
	private BigDecimal irebateRate;
	
	public BigDecimal getIrebateRate(){
		return irebateRate;
	}
	
	public void setIrebateRate(BigDecimal irebateRate){
		this.irebateRate= irebateRate;
	}
	/* 10=月结
            20=季度
            30=半年
            40=一年 */
	private Integer irebateRule;
	
	public Integer getIrebateRule(){
		return irebateRule;
	}
	
	public void setIrebateRule(Integer irebateRule){
		this.irebateRule= irebateRule;
	}
	/* 10=固定金额
            20=按毛利
            30=按纯利 */
	private Integer irebateWay;
	
	public Integer getIrebateWay(){
		return irebateWay;
	}
	
	public void setIrebateWay(Integer irebateWay){
		this.irebateWay= irebateWay;
	}
	/* 状态：
            10=洽谈中
            20=已签约
            30=已解约
            40=合约到期
             */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 商户类型
            10:个人
            20:企业 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/*  */
	private Integer ivmSkuType;
	
	public Integer getIvmSkuType(){
		return ivmSkuType;
	}
	
	public void setIvmSkuType(Integer ivmSkuType){
		this.ivmSkuType= ivmSkuType;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 商户编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 联系人地址 */
	private String scontactAddress;
	
	public String getScontactAddress(){
		return scontactAddress;
	}
	
	public void setScontactAddress(String scontactAddress){
		this.scontactAddress= scontactAddress;
	}
	/* 联系人邮箱 */
	private String scontactEmail;
	
	public String getScontactEmail(){
		return scontactEmail;
	}
	
	public void setScontactEmail(String scontactEmail){
		this.scontactEmail= scontactEmail;
	}
	/* 联系人电话 */
	private String scontactMobile;
	
	public String getScontactMobile(){
		return scontactMobile;
	}
	
	public void setScontactMobile(String scontactMobile){
		this.scontactMobile= scontactMobile;
	}
	/* 联系人 */
	private String scontactName;
	
	public String getScontactName(){
		return scontactName;
	}
	
	public void setScontactName(String scontactName){
		this.scontactName= scontactName;
	}
	/* 对接BD联系方式 */
	private String sdbContact;
	
	public String getSdbContact(){
		return sdbContact;
	}
	
	public void setSdbContact(String sdbContact){
		this.sdbContact= sdbContact;
	}
	/* 对接BD邮箱 */
	private String sdbEmail;
	
	public String getSdbEmail(){
		return sdbEmail;
	}
	
	public void setSdbEmail(String sdbEmail){
		this.sdbEmail= sdbEmail;
	}
	/* 对接BD用户ID */
	private String sdbId;
	
	public String getSdbId(){
		return sdbId;
	}
	
	public void setSdbId(String sdbId){
		this.sdbId= sdbId;
	}
	/* 对接BD姓名 */
	private String sdbName;
	
	public String getSdbName(){
		return sdbName;
	}
	
	public void setSdbName(String sdbName){
		this.sdbName= sdbName;
	}
	/* 商户名称(公司名称、个人) */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 父级商户编号 */
	private String sparentCode;
	
	public String getSparentCode(){
		return sparentCode;
	}
	
	public void setSparentCode(String sparentCode){
		this.sparentCode= sparentCode;
	}
	/* 父级商户ID */
	private String sparentId;
	
	public String getSparentId(){
		return sparentId;
	}
	
	public void setSparentId(String sparentId){
		this.sparentId= sparentId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 根商户编号 */
	private String srootCode;
	
	public String getSrootCode(){
		return srootCode;
	}
	
	public void setSrootCode(String srootCode){
		this.srootCode= srootCode;
	}
	/* 根商户ID */
	private String srootId;
	
	public String getSrootId(){
		return srootId;
	}
	
	public void setSrootId(String srootId){
		this.srootId= srootId;
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