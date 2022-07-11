package com.cloud.cang.model.sp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品信息表(SP_COMMODITY_INFO) **/
public class CommodityInfo extends GenericEntity  {

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
	
	/* 平均成本价 */
	private BigDecimal favgCostPrice;
	
	public BigDecimal getFavgCostPrice(){
		return favgCostPrice;
	}
	
	public void setFavgCostPrice(BigDecimal favgCostPrice){
		this.favgCostPrice= favgCostPrice;
	}
	/* 平均销售价 */
	private BigDecimal favgSalePrice;
	
	public BigDecimal getFavgSalePrice(){
		return favgSalePrice;
	}
	
	public void setFavgSalePrice(BigDecimal favgSalePrice){
		this.favgSalePrice= favgSalePrice;
	}
	/* 成本价 */
	private BigDecimal fcostPrice;
	
	public BigDecimal getFcostPrice(){
		return fcostPrice;
	}
	
	public void setFcostPrice(BigDecimal fcostPrice){
		this.fcostPrice= fcostPrice;
	}
	/* 市场价 */
	private BigDecimal fmarketPrice;
	
	public BigDecimal getFmarketPrice(){
		return fmarketPrice;
	}
	
	public void setFmarketPrice(BigDecimal fmarketPrice){
		this.fmarketPrice= fmarketPrice;
	}
	/* 销售价 */
	private BigDecimal fsalePrice;
	
	public BigDecimal getFsalePrice(){
		return fsalePrice;
	}
	
	public void setFsalePrice(BigDecimal fsalePrice){
		this.fsalePrice= fsalePrice;
	}
	/* 商品重量允许浮动值 正负值 */
	private BigDecimal icommodityFloat;
	
	public BigDecimal getIcommodityFloat(){
		return icommodityFloat;
	}
	
	public void setIcommodityFloat(BigDecimal icommodityFloat){
		this.icommodityFloat= icommodityFloat;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 电子秤允许浮动值 正负值 */
	private BigDecimal ielectronicFloat;
	
	public BigDecimal getIelectronicFloat(){
		return ielectronicFloat;
	}
	
	public void setIelectronicFloat(BigDecimal ielectronicFloat){
		this.ielectronicFloat= ielectronicFloat;
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
	/* 是否可以积分抵扣0否1是 */
	private Integer iisUseIntegral;
	
	public Integer getIisUseIntegral(){
		return iisUseIntegral;
	}
	
	public void setIisUseIntegral(Integer iisUseIntegral){
		this.iisUseIntegral= iisUseIntegral;
	}
	/* 参与活动次数 */
	private Integer ijoinActiveNum;
	
	public Integer getIjoinActiveNum(){
		return ijoinActiveNum;
	}
	
	public void setIjoinActiveNum(Integer ijoinActiveNum){
		this.ijoinActiveNum= ijoinActiveNum;
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
	/* 最高抵扣积分（0不限制） */
	private Integer imaxIntegral;
	
	public Integer getImaxIntegral(){
		return imaxIntegral;
	}
	
	public void setImaxIntegral(Integer imaxIntegral){
		this.imaxIntegral= imaxIntegral;
	}
	/* 总销售数量 */
	private Integer isaleNum;
	
	public Integer getIsaleNum(){
		return isaleNum;
	}
	
	public void setIsaleNum(Integer isaleNum){
		this.isaleNum= isaleNum;
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
	/* 商品状态
            10=正常
            20=下架 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 商品适用设备类型
            10=视觉
            20=rfid设备
            30=其他
            数据字典控制 */
	private Integer istoreDevice;
	
	public Integer getIstoreDevice(){
		return istoreDevice;
	}
	
	public void setIstoreDevice(Integer istoreDevice){
		this.istoreDevice= istoreDevice;
	}
	/* 商品重量（G） */
	private BigDecimal iweigth;
	
	public BigDecimal getIweigth(){
		return iweigth;
	}
	
	public void setIweigth(BigDecimal iweigth){
		this.iweigth= iweigth;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
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
	/* 分类编号  如零食等（大类）
            数据字典配置 */
	private String scategoryCode;
	
	public String getScategoryCode(){
		return scategoryCode;
	}
	
	public void setScategoryCode(String scategoryCode){
		this.scategoryCode= scategoryCode;
	}
	/* 分类名称（大类）
            同编号 */
	private String scategoryName;
	
	public String getScategoryName(){
		return scategoryName;
	}
	
	public void setScategoryName(String scategoryName){
		this.scategoryName= scategoryName;
	}
	/* 商品编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 展示图片 */
	private String scommodityImg;
	
	public String getScommodityImg(){
		return scommodityImg;
	}
	
	public void setScommodityImg(String scommodityImg){
		this.scommodityImg= scommodityImg;
	}
	/* 商品标签ID */
	private String slabelId;
	
	public String getSlabelId(){
		return slabelId;
	}
	
	public void setSlabelId(String slabelId){
		this.slabelId= slabelId;
	}
	/* 商品标签名称 */
	private String slabelName;
	
	public String getSlabelName(){
		return slabelName;
	}
	
	public void setSlabelName(String slabelName){
		this.slabelName= slabelName;
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
	/* 商品名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
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
	/* 商品简称 */
	private String sshortName;
	
	public String getSshortName(){
		return sshortName;
	}
	
	public void setSshortName(String sshortName){
		this.sshortName= sshortName;
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
	/* 视觉识别编号 */
	private String svrCode;
	
	public String getSvrCode(){
		return svrCode;
	}
	
	public void setSvrCode(String svrCode){
		this.svrCode= svrCode;
	}
	/* 视觉商品SKU编号 */
	private String svrCommodityCode;
	
	public String getSvrCommodityCode(){
		return svrCommodityCode;
	}
	
	public void setSvrCommodityCode(String svrCommodityCode){
		this.svrCommodityCode= svrCommodityCode;
	}
	/* 视觉商品SKU ID */
	private String svrCommodityId;
	
	public String getSvrCommodityId(){
		return svrCommodityId;
	}
	
	public void setSvrCommodityId(String svrCommodityId){
		this.svrCommodityId= svrCommodityId;
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