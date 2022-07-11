package com.cloud.cang.model.tp;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 设备型号详细信息模板表(TP_DEVICE_MODEL_TEMPLATE) **/
public class DeviceModelTemplate extends GenericEntity  {

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
	/* 是否删除 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 门引脚序号 */
	private Integer idoorPinSn;
	
	public Integer getIdoorPinSn(){
		return idoorPinSn;
	}
	
	public void setIdoorPinSn(Integer idoorPinSn){
		this.idoorPinSn= idoorPinSn;
	}
	/* 防触电类型 10=0类 20=I类 30=II类 40=III类 */
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
	/* 是否使用扩展GPIO：0否，1是 */
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
	/* 模板状态 10=启用 20=禁用 */
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
	/* 模板编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
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

	@Override
	public String toString() {
		return "DeviceModelTemplate{" +
				"id='" + id + '\'' +
				", icameraNum=" + icameraNum +
				", icargoRoadNum=" + icargoRoadNum +
				", icompressorNum=" + icompressorNum +
				", idelFlag=" + idelFlag +
				", idoorPinSn=" + idoorPinSn +
				", ielectricShock=" + ielectricShock +
				", ihallPinSn=" + ihallPinSn +
				", iisDetectHall=" + iisDetectHall +
				", iisUseExpandGpio=" + iisUseExpandGpio +
				", ilayerNum=" + ilayerNum +
				", ilockCylinder=" + ilockCylinder +
				", imotherboardNum=" + imotherboardNum +
				", iopendoorPinSn=" + iopendoorPinSn +
				", istatus=" + istatus +
				", saddUser='" + saddUser + '\'' +
				", sadConf='" + sadConf + '\'' +
				", sadDimensions='" + sadDimensions + '\'' +
				", scameraBrand='" + scameraBrand + '\'' +
				", scameraMethod='" + scameraMethod + '\'' +
				", scameraModel='" + scameraModel + '\'' +
				", scameraType='" + scameraType + '\'' +
				", scode='" + scode + '\'' +
				", scompressorDesc='" + scompressorDesc + '\'' +
				", scompressorPosition='" + scompressorPosition + '\'' +
				", scontrastMode='" + scontrastMode + '\'' +
				", scoreDesc='" + scoreDesc + '\'' +
				", sdailyPower='" + sdailyPower + '\'' +
				", sdeviceModel='" + sdeviceModel + '\'' +
				", sdimensions='" + sdimensions + '\'' +
				", slocksManufacturer='" + slocksManufacturer + '\'' +
				", slocksModel='" + slocksModel + '\'' +
				", smanufacturer='" + smanufacturer + '\'' +
				", smerchantCode='" + smerchantCode + '\'' +
				", smerchantId='" + smerchantId + '\'' +
				", sname='" + sname + '\'' +
				", spayWap='" + spayWap + '\'' +
				", spcbModel='" + spcbModel + '\'' +
				", sproductCapacity='" + sproductCapacity + '\'' +
				", sproductTypes='" + sproductTypes + '\'' +
				", sratedPower='" + sratedPower + '\'' +
				", sremark='" + sremark + '\'' +
				", supdateUser='" + supdateUser + '\'' +
				", svisualServiceProvider='" + svisualServiceProvider + '\'' +
				", sweight='" + sweight + '\'' +
				", taddTime=" + taddTime +
				", tupdateTime=" + tupdateTime +
				'}';
	}
}