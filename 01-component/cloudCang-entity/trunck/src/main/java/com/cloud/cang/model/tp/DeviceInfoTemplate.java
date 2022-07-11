package com.cloud.cang.model.tp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备基础信息模板表(TP_DEVICE_INFO_TEMPLATE) **/
public class DeviceInfoTemplate extends GenericEntity  {

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
	
	/* 商品重量允许浮动值 正负值 */
	private BigDecimal icommodityFloat;
	
	public BigDecimal getIcommodityFloat(){
		return icommodityFloat;
	}
	
	public void setIcommodityFloat(BigDecimal icommodityFloat){
		this.icommodityFloat= icommodityFloat;
	}
	/* 压缩机类型 10=制冷 20=制热 30=制冷热 */
	private Integer icompressorType;
	
	public Integer getIcompressorType(){
		return icompressorType;
	}
	
	public void setIcompressorType(Integer icompressorType){
		this.icompressorType= icompressorType;
	}
	/* 货柜类型 10=单开门 20=双开门 */
	private Integer icontainerType;
	
	public Integer getIcontainerType(){
		return icontainerType;
	}
	
	public void setIcontainerType(Integer icontainerType){
		this.icontainerType= icontainerType;
	}
	/* 合作模式 10=采购 20=租用 30=自主 */
	private Integer icooperationMode;
	
	public Integer getIcooperationMode(){
		return icooperationMode;
	}
	
	public void setIcooperationMode(Integer icooperationMode){
		this.icooperationMode= icooperationMode;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 系统对接类型 10=自主研发 20=对接第三方 */
	private Integer idockingType;
	
	public Integer getIdockingType(){
		return idockingType;
	}
	
	public void setIdockingType(Integer idockingType){
		this.idockingType= idockingType;
	}
	/* 电子秤允许浮动值 正负值 */
	private BigDecimal ielectronicFloat;
	
	public BigDecimal getIelectronicFloat(){
		return ielectronicFloat;
	}
	
	public void setIelectronicFloat(BigDecimal ielectronicFloat){
		this.ielectronicFloat= ielectronicFloat;
	}
	/* 是否广告机0否1是 */
	private Integer iinstallAd;
	
	public Integer getIinstallAd(){
		return iinstallAd;
	}
	
	public void setIinstallAd(Integer iinstallAd){
		this.iinstallAd= iinstallAd;
	}
	/* 是否绑定垃圾箱
0 否
1 是 */
	private Integer iisBindTrash;
	
	public Integer getIisBindTrash(){
		return iisBindTrash;
	}
	
	public void setIisBindTrash(Integer iisBindTrash){
		this.iisBindTrash= iisBindTrash;
	}
	/* 是否开门购物中实时盘货  云端识别柜子类型必填 */
	private Integer iisOpeningInventory;
	
	public Integer getIisOpeningInventory(){
		return iisOpeningInventory;
	}
	
	public void setIisOpeningInventory(Integer iisOpeningInventory){
		this.iisOpeningInventory= iisOpeningInventory;
	}
	/* 运营状态 10=启用 20=停用 */
	private Integer ioperateStatus;
	
	public Integer getIoperateStatus(){
		return ioperateStatus;
	}
	
	public void setIoperateStatus(Integer ioperateStatus){
		this.ioperateStatus= ioperateStatus;
	}
	/* 屏幕显示类型(数据字典配置) */
	private Integer iscreenDisplayType;
	
	public Integer getIscreenDisplayType(){
		return iscreenDisplayType;
	}
	
	public void setIscreenDisplayType(Integer iscreenDisplayType){
		this.iscreenDisplayType= iscreenDisplayType;
	}
	/* 模板状态 10=启用 20=禁用 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 是否支持小屏幕AI设备 */
	private Integer isupportAi;
	
	public Integer getIsupportAi(){
		return isupportAi;
	}
	
	public void setIsupportAi(Integer isupportAi){
		this.isupportAi= isupportAi;
	}
	/* 是否支持称重 */
	private Integer isupportWeigh;
	
	public Integer getIsupportWeigh(){
		return isupportWeigh;
	}
	
	public void setIsupportWeigh(Integer isupportWeigh){
		this.isupportWeigh= isupportWeigh;
	}
	/* 设备类型 （数据字典配置）
            10=传统
            20=RFID射频
            30=视觉
            40=前端视觉+重力
            50=云端识别
            60=云端识别+重力 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 称重类型 */
	private Integer iweightType;
	
	public Integer getIweightType(){
		return iweightType;
	}
	
	public void setIweightType(Integer iweightType){
		this.iweightType= iweightType;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 区县ID */
	private String sareaId;
	
	public String getSareaId(){
		return sareaId;
	}
	
	public void setSareaId(String sareaId){
		this.sareaId= sareaId;
	}
	/* 投放区县 */
	private String sareaName;
	
	public String getSareaName(){
		return sareaName;
	}
	
	public void setSareaName(String sareaName){
		this.sareaName= sareaName;
	}
	/* 城市ID */
	private String scityId;
	
	public String getScityId(){
		return scityId;
	}
	
	public void setScityId(String scityId){
		this.scityId= scityId;
	}
	/* 投放城市 */
	private String scityName;
	
	public String getScityName(){
		return scityName;
	}
	
	public void setScityName(String scityName){
		this.scityName= scityName;
	}
	/* 模板编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 设备型号 */
	private String sdeviceModel;
	
	public String getSdeviceModel(){
		return sdeviceModel;
	}
	
	public void setSdeviceModel(String sdeviceModel){
		this.sdeviceModel= sdeviceModel;
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
	/* 模板名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 省份ID */
	private String sprovinceId;
	
	public String getSprovinceId(){
		return sprovinceId;
	}
	
	public void setSprovinceId(String sprovinceId){
		this.sprovinceId= sprovinceId;
	}
	/* 投放省份 */
	private String sprovinceName;
	
	public String getSprovinceName(){
		return sprovinceName;
	}
	
	public void setSprovinceName(String sprovinceName){
		this.sprovinceName= sprovinceName;
	}
	/* 设备投放场景 10=公园 20=学校 30=医院 */
	private String sputScenes;
	
	public String getSputScenes(){
		return sputScenes;
	}
	
	public void setSputScenes(String sputScenes){
		this.sputScenes= sputScenes;
	}
	/* 读写器序列号 */
	private String sreaderSerialNumber;
	
	public String getSreaderSerialNumber(){
		return sreaderSerialNumber;
	}
	
	public void setSreaderSerialNumber(String sreaderSerialNumber){
		this.sreaderSerialNumber= sreaderSerialNumber;
	}
	/* 识别模型编号 */
	private String srecognitionModelCode;
	
	public String getSrecognitionModelCode(){
		return srecognitionModelCode;
	}
	
	public void setSrecognitionModelCode(String srecognitionModelCode){
		this.srecognitionModelCode= srecognitionModelCode;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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