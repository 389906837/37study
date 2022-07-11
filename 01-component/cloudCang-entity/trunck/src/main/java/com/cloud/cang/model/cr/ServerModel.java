package com.cloud.cang.model.cr;

import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;
import java.util.Date;
/** 识别服务模型管理(CR_SERVER_MODEL) **/
public class ServerModel extends GenericEntity  {

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
	
	/* 识别置信度 */
	private BigDecimal fvisThresh;
	
	public BigDecimal getFvisThresh(){
		return fvisThresh;
	}
	
	public void setFvisThresh(BigDecimal fvisThresh){
		this.fvisThresh= fvisThresh;
	}
	/* 识别类别数量 */
	private Integer icategoryNum;
	
	public Integer getIcategoryNum(){
		return icategoryNum;
	}
	
	public void setIcategoryNum(Integer icategoryNum){
		this.icategoryNum= icategoryNum;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 适用范围类型
            10=通用
            20=专用
            30=多商户
             */
	private Integer irangeType;
	
	public Integer getIrangeType(){
		return irangeType;
	}
	
	public void setIrangeType(Integer irangeType){
		this.irangeType= irangeType;
	}
	/* 状态
            10=待审核
            20=已审核
            30=已废弃 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 是否tiny模型 0否1是 */
	private Integer istiny;
	
	public Integer getIstiny(){
		return istiny;
	}
	
	public void setIstiny(Integer istiny){
		this.istiny= istiny;
	}
	/* 审核人姓名 */
	private String sauditOperName;
	
	public String getSauditOperName(){
		return sauditOperName;
	}
	
	public void setSauditOperName(String sauditOperName){
		this.sauditOperName= sauditOperName;
	}
	/* 审核原因 */
	private String sauditReason;
	
	public String getSauditReason(){
		return sauditReason;
	}
	
	public void setSauditReason(String sauditReason){
		this.sauditReason= sauditReason;
	}
	/* 用户编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 文件大小 */
	private String sfileSize;
	
	public String getSfileSize(){
		return sfileSize;
	}
	
	public void setSfileSize(String sfileSize){
		this.sfileSize= sfileSize;
	}
	/* 文件大小单位 */
	private String sfileSizeUnit;
	
	public String getSfileSizeUnit(){
		return sfileSizeUnit;
	}
	
	public void setSfileSizeUnit(String sfileSizeUnit){
		this.sfileSizeUnit= sfileSizeUnit;
	}
	/* 文件类型 */
	private String sfileType;
	
	public String getSfileType(){
		return sfileType;
	}
	
	public void setSfileType(String sfileType){
		this.sfileType= sfileType;
	}

	/* 模型类型  10 = bmodel模型；20 = darknet模型 */
	private Integer imodelType;

	public Integer getImodelType() {
		return imodelType;
	}

	public void setImodelType(Integer imodelType) {
		this.imodelType = imodelType;
	}

	/* 模型地址 */
	private String smodelAddress;
	
	public String getSmodelAddress(){
		return smodelAddress;
	}
	public void setSmodelAddress(String smodelAddress){
		this.smodelAddress= smodelAddress;
	}


	/* bmodel临时模型地址 */
	private String smodelAddressTmp;

	public String getSmodelAddressTmp() {
		return smodelAddressTmp;
	}

	public void setSmodelAddressTmp(String smodelAddressTmp) {
		this.smodelAddressTmp = smodelAddressTmp;
	}


	/* darknet临时模型地址 */
	private String sdarknetAddressTmp;

	public String getSdarknetAddressTmp() {
		return sdarknetAddressTmp;
	}

	public void setSdarknetAddressTmp(String sdarknetAddressTmp) {
		this.sdarknetAddressTmp = sdarknetAddressTmp;
	}



	/* 模型名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 范围商户编号集合 */
	private String srangeList;
	
	public String getSrangeList(){
		return srangeList;
	}
	
	public void setSrangeList(String srangeList){
		this.srangeList= srangeList;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 添加人 */
	private String taddUser;
	
	public String getTaddUser(){
		return taddUser;
	}
	
	public void setTaddUser(String taddUser){
		this.taddUser= taddUser;
	}
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
	}
	/* 修改人 */
	private String tupateUser;
	
	public String getTupateUser(){
		return tupateUser;
	}
	
	public void setTupateUser(String tupateUser){
		this.tupateUser= tupateUser;
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