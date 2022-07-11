package com.cloud.cang.model.vr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品申报(VR_COMMODITY_DECLARE) **/
public class CommodityDeclare extends GenericEntity  {

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
	/* 标识类型
            数据字典配置 
            10=特产
            20=进口 */
	private Integer iidentificationType;
	
	public Integer getIidentificationType(){
		return iidentificationType;
	}
	
	public void setIidentificationType(Integer iidentificationType){
		this.iidentificationType= iidentificationType;
	}
	/* 保质期类型
            10=天
            20=月 */
	private Integer ilifeType;
	
	public Integer getIlifeType(){
		return ilifeType;
	}
	
	public void setIlifeType(Integer ilifeType){
		this.ilifeType= ilifeType;
	}
	/* 保质期 */
	private Integer ishelfLife;
	
	public Integer getIshelfLife(){
		return ishelfLife;
	}
	
	public void setIshelfLife(Integer ishelfLife){
		this.ishelfLife= ishelfLife;
	}
	/* 规格/重量 */
	private String ispecWeight;
	
	public String getIspecWeight(){
		return ispecWeight;
	}
	
	public void setIspecWeight(String ispecWeight){
		this.ispecWeight= ispecWeight;
	}
	/* 10=申请中 20=审核通过 30= 审核拒绝 40=已上架 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 审核人ID */
	private String sauditOperId;
	
	public String getSauditOperId(){
		return sauditOperId;
	}
	
	public void setSauditOperId(String sauditOperId){
		this.sauditOperId= sauditOperId;
	}
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核意见 */
	private String sauditOpinion;
	
	public String getSauditOpinion(){
		return sauditOpinion;
	}
	
	public void setSauditOpinion(String sauditOpinion){
		this.sauditOpinion= sauditOpinion;
	}
	/* 大类ID */
	private String sbigCategoryId;
	
	public String getSbigCategoryId(){
		return sbigCategoryId;
	}
	
	public void setSbigCategoryId(String sbigCategoryId){
		this.sbigCategoryId= sbigCategoryId;
	}
	/* 大类名称 */
	private String sbigCategoryName;
	
	public String getSbigCategoryName(){
		return sbigCategoryName;
	}
	
	public void setSbigCategoryName(String sbigCategoryName){
		this.sbigCategoryName= sbigCategoryName;
	}
	/* 品牌ID */
	private String sbrandId;
	
	public String getSbrandId(){
		return sbrandId;
	}
	
	public void setSbrandId(String sbrandId){
		this.sbrandId= sbrandId;
	}
	/* 品牌名称 */
	private String sbrandName;
	
	public String getSbrandName(){
		return sbrandName;
	}
	
	public void setSbrandName(String sbrandName){
		this.sbrandName= sbrandName;
	}
	/* 分类编号  如零食等
            数据字典配置 */
	private String scategoryCode;
	
	public String getScategoryCode(){
		return scategoryCode;
	}
	
	public void setScategoryCode(String scategoryCode){
		this.scategoryCode= scategoryCode;
	}
	/* 分类名称
            同编号 */
	private String scategoryName;
	
	public String getScategoryName(){
		return scategoryName;
	}
	
	public void setScategoryName(String scategoryName){
		this.scategoryName= scategoryName;
	}
	/* 展示图片 */
	private String scommodityImg;
	
	public String getScommodityImg(){
		return scommodityImg;
	}
	
	public void setScommodityImg(String scommodityImg){
		this.scommodityImg= scommodityImg;
	}
	/* 商品名 */
	private String scommodityName;
	
	public String getScommodityName(){
		return scommodityName;
	}
	
	public void setScommodityName(String scommodityName){
		this.scommodityName= scommodityName;
	}
	/* 生产厂家 */
	private String smanufacturer;
	
	public String getSmanufacturer(){
		return smanufacturer;
	}
	
	public void setSmanufacturer(String smanufacturer){
		this.smanufacturer= smanufacturer;
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
	/* 产地 */
	private String sorigin;
	
	public String getSorigin(){
		return sorigin;
	}
	
	public void setSorigin(String sorigin){
		this.sorigin= sorigin;
	}
	/* 最小销售包装单位 */
	private String spackageUnit;
	
	public String getSpackageUnit(){
		return spackageUnit;
	}
	
	public void setSpackageUnit(String spackageUnit){
		this.spackageUnit= spackageUnit;
	}
	/* 包装材质 */
	private String spackagingMaterial;
	
	public String getSpackagingMaterial(){
		return spackagingMaterial;
	}
	
	public void setSpackagingMaterial(String spackagingMaterial){
		this.spackagingMaterial= spackagingMaterial;
	}
	/* 商品条形码 */
	private String sproductBarcode;
	
	public String getSproductBarcode(){
		return sproductBarcode;
	}
	
	public void setSproductBarcode(String sproductBarcode){
		this.sproductBarcode= sproductBarcode;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 小类ID */
	private String ssmallCategoryId;
	
	public String getSsmallCategoryId(){
		return ssmallCategoryId;
	}
	
	public void setSsmallCategoryId(String ssmallCategoryId){
		this.ssmallCategoryId= ssmallCategoryId;
	}
	/* 小类名称 */
	private String ssmallCategoryName;
	
	public String getSsmallCategoryName(){
		return ssmallCategoryName;
	}
	
	public void setSsmallCategoryName(String ssmallCategoryName){
		this.ssmallCategoryName= ssmallCategoryName;
	}
	/* 规格单位 */
	private String sspecUnit;
	
	public String getSspecUnit(){
		return sspecUnit;
	}
	
	public void setSspecUnit(String sspecUnit){
		this.sspecUnit= sspecUnit;
	}
	/* 口味 */
	private String staste;
	
	public String getStaste(){
		return staste;
	}
	
	public void setStaste(String staste){
		this.staste= staste;
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
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
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