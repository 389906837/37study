package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备型号详细信息表(SB_DEVICE_MODEL) **/
public class DeviceModel extends GenericEntity  {

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
	
	/* 摄像头个数 */
	private Integer icameraNum;
	
	public Integer getIcameraNum(){
		return icameraNum;
	}
	
	public void setIcameraNum(Integer icameraNum){
		this.icameraNum= icameraNum;
	}
	/* 货道数 */
	private Integer icargoRoadNum;
	
	public Integer getIcargoRoadNum(){
		return icargoRoadNum;
	}
	
	public void setIcargoRoadNum(Integer icargoRoadNum){
		this.icargoRoadNum= icargoRoadNum;
	}
	/* 压缩机个数 */
	private Integer icompressorNum;
	
	public Integer getIcompressorNum(){
		return icompressorNum;
	}
	
	public void setIcompressorNum(Integer icompressorNum){
		this.icompressorNum= icompressorNum;
	}
	/* 门引脚序号 */
	private Integer idoorPinSn;
	
	public Integer getIdoorPinSn(){
		return idoorPinSn;
	}
	
	public void setIdoorPinSn(Integer idoorPinSn){
		this.idoorPinSn= idoorPinSn;
	}
	/* 10=0类
            20=I类
            30=II类
            40=III类 */
	private Integer ielectricShock;
	
	public Integer getIelectricShock(){
		return ielectricShock;
	}
	
	public void setIelectricShock(Integer ielectricShock){
		this.ielectricShock= ielectricShock;
	}
	/* 霍尔引脚序号 */
	private Integer ihallPinSn;
	
	public Integer getIhallPinSn(){
		return ihallPinSn;
	}
	
	public void setIhallPinSn(Integer ihallPinSn){
		this.ihallPinSn= ihallPinSn;
	}
	/* 是否检测霍尔值 0否，1是 */
	private Integer iisDetectHall;
	
	public Integer getIisDetectHall(){
		return iisDetectHall;
	}
	
	public void setIisDetectHall(Integer iisDetectHall){
		this.iisDetectHall= iisDetectHall;
	}
	/* 开锁方式 */
	private Integer iisUseExpandGpio;
	
	public Integer getIisUseExpandGpio(){
		return iisUseExpandGpio;
	}
	
	public void setIisUseExpandGpio(Integer iisUseExpandGpio){
		this.iisUseExpandGpio= iisUseExpandGpio;
	}
	/* 设备层数 */
	private Integer ilayerNum;
	
	public Integer getIlayerNum(){
		return ilayerNum;
	}
	
	public void setIlayerNum(Integer ilayerNum){
		this.ilayerNum= ilayerNum;
	}
	/* 锁芯引脚序号 */
	private Integer ilockCylinder;
	
	public Integer getIlockCylinder(){
		return ilockCylinder;
	}
	
	public void setIlockCylinder(Integer ilockCylinder){
		this.ilockCylinder= ilockCylinder;
	}
	/* 主板块数 */
	private Integer imotherboardNum;
	
	public Integer getImotherboardNum(){
		return imotherboardNum;
	}
	
	public void setImotherboardNum(Integer imotherboardNum){
		this.imotherboardNum= imotherboardNum;
	}
	/* 开门引脚序号 */
	private Integer iopendoorPinSn;
	
	public Integer getIopendoorPinSn(){
		return iopendoorPinSn;
	}
	
	public void setIopendoorPinSn(Integer iopendoorPinSn){
		this.iopendoorPinSn= iopendoorPinSn;
	}
	/* 是否支持分层盘货 */
	private Integer isupportSingleLayer;
	
	public Integer getIsupportSingleLayer(){
		return isupportSingleLayer;
	}
	
	public void setIsupportSingleLayer(Integer isupportSingleLayer){
		this.isupportSingleLayer= isupportSingleLayer;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 广告机配置描叙 */
	private String sadConf;
	
	public String getSadConf(){
		return sadConf;
	}
	
	public void setSadConf(String sadConf){
		this.sadConf= sadConf;
	}
	/* 广告机屏幕尺寸说明 */
	private String sadDimensions;
	
	public String getSadDimensions(){
		return sadDimensions;
	}
	
	public void setSadDimensions(String sadDimensions){
		this.sadDimensions= sadDimensions;
	}
	/* 摄像头品牌 */
	private String scameraBrand;
	
	public String getScameraBrand(){
		return scameraBrand;
	}
	
	public void setScameraBrand(String scameraBrand){
		this.scameraBrand= scameraBrand;
	}
	/* 摄像头方法 */
	private String scameraMethod;
	
	public String getScameraMethod(){
		return scameraMethod;
	}
	
	public void setScameraMethod(String scameraMethod){
		this.scameraMethod= scameraMethod;
	}
	/* 摄像头型号 */
	private String scameraModel;
	
	public String getScameraModel(){
		return scameraModel;
	}
	
	public void setScameraModel(String scameraModel){
		this.scameraModel= scameraModel;
	}
	/* 摄像头类型：USB摄像头=UsbCamera，网络摄像头=NetCamera */
	private String scameraType;
	
	public String getScameraType(){
		return scameraType;
	}
	
	public void setScameraType(String scameraType){
		this.scameraType= scameraType;
	}
	/* 压缩机描叙 */
	private String scompressorDesc;
	
	public String getScompressorDesc(){
		return scompressorDesc;
	}
	
	public void setScompressorDesc(String scompressorDesc){
		this.scompressorDesc= scompressorDesc;
	}
	/* 压缩机位置 */
	private String scompressorPosition;
	
	public String getScompressorPosition(){
		return scompressorPosition;
	}
	
	public void setScompressorPosition(String scompressorPosition){
		this.scompressorPosition= scompressorPosition;
	}
	/* 盘货对比方式 
rawstock 原始库存 
openDoor 开门前后 */
	private String scontrastMode;
	
	public String getScontrastMode(){
		return scontrastMode;
	}
	
	public void setScontrastMode(String scontrastMode){
		this.scontrastMode= scontrastMode;
	}
	/* 核心配置描叙 */
	private String scoreDesc;
	
	public String getScoreDesc(){
		return scoreDesc;
	}
	
	public void setScoreDesc(String scoreDesc){
		this.scoreDesc= scoreDesc;
	}
	/* 日耗电量 */
	private String sdailyPower;
	
	public String getSdailyPower(){
		return sdailyPower;
	}
	
	public void setSdailyPower(String sdailyPower){
		this.sdailyPower= sdailyPower;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 设备核心型号（机械结构、RFID、视觉识别） */
	private String sdeviceModel;
	
	public String getSdeviceModel(){
		return sdeviceModel;
	}
	
	public void setSdeviceModel(String sdeviceModel){
		this.sdeviceModel= sdeviceModel;
	}
	/* 整体外形尺寸 */
	private String sdimensions;
	
	public String getSdimensions(){
		return sdimensions;
	}
	
	public void setSdimensions(String sdimensions){
		this.sdimensions= sdimensions;
	}
	/* 电子锁生产厂商 */
	private String slocksManufacturer;
	
	public String getSlocksManufacturer(){
		return slocksManufacturer;
	}
	
	public void setSlocksManufacturer(String slocksManufacturer){
		this.slocksManufacturer= slocksManufacturer;
	}
	/* 电子锁型号 */
	private String slocksModel;
	
	public String getSlocksModel(){
		return slocksModel;
	}
	
	public void setSlocksModel(String slocksModel){
		this.slocksModel= slocksModel;
	}
	/* 核心生产厂商 */
	private String smanufacturer;
	
	public String getSmanufacturer(){
		return smanufacturer;
	}
	
	public void setSmanufacturer(String smanufacturer){
		this.smanufacturer= smanufacturer;
	}
	/* 支持支付方式微信支付 支付宝 现金 银联卡  */
	private String spayWap;
	
	public String getSpayWap(){
		return spayWap;
	}
	
	public void setSpayWap(String spayWap){
		this.spayWap= spayWap;
	}
	/* PCB板型号 */
	private String spcbModel;
	
	public String getSpcbModel(){
		return spcbModel;
	}
	
	public void setSpcbModel(String spcbModel){
		this.spcbModel= spcbModel;
	}
	/* 商品容量 */
	private String sproductCapacity;
	
	public String getSproductCapacity(){
		return sproductCapacity;
	}
	
	public void setSproductCapacity(String sproductCapacity){
		this.sproductCapacity= sproductCapacity;
	}
	/* 商品类型 */
	private String sproductTypes;
	
	public String getSproductTypes(){
		return sproductTypes;
	}
	
	public void setSproductTypes(String sproductTypes){
		this.sproductTypes= sproductTypes;
	}
	/* 额定功率 */
	private String sratedPower;
	
	public String getSratedPower(){
		return sratedPower;
	}
	
	public void setSratedPower(String sratedPower){
		this.sratedPower= sratedPower;
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
	/* 视觉服务提供商:七奇=qiqi，比特大陆=bitmain */
	private String svisualServiceProvider;
	
	public String getSvisualServiceProvider(){
		return svisualServiceProvider;
	}
	
	public void setSvisualServiceProvider(String svisualServiceProvider){
		this.svisualServiceProvider= svisualServiceProvider;
	}
	/* 重量 */
	private String sweight;
	
	public String getSweight(){
		return sweight;
	}
	
	public void setSweight(String sweight){
		this.sweight= sweight;
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