package com.cloud.cang.model.op;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 接口购买记录表(OP_BUY_RECORD) **/
public class BuyRecord extends GenericEntity  {

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
	/* 支付金额 */
	private BigDecimal fpayAmount;
	
	public BigDecimal getFpayAmount(){
		return fpayAmount;
	}
	
	public void setFpayAmount(BigDecimal fpayAmount){
		this.fpayAmount= fpayAmount;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 是否后台添加 */
	private Integer iisAdminAdd;
	
	public Integer getIisAdminAdd(){
		return iisAdminAdd;
	}
	
	public void setIisAdminAdd(Integer iisAdminAdd){
		this.iisAdminAdd= iisAdminAdd;
	}
	/* 支付方式
            10=微信支付
            20=支付宝 */
	private Integer ipayWay;
	
	public Integer getIpayWay(){
		return ipayWay;
	}
	
	public void setIpayWay(Integer ipayWay){
		this.ipayWay= ipayWay;
	}
	/* 订单状态 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 购买内容 */
	private String sbuyContent;
	
	public String getSbuyContent(){
		return sbuyContent;
	}
	
	public void setSbuyContent(String sbuyContent){
		this.sbuyContent= sbuyContent;
	}
	/* 购买方式
            10=按次购买
            20=资源包购买
            30=按时间购买 */
	private String sbuyWay;
	
	public String getSbuyWay(){
		return sbuyWay;
	}
	
	public void setSbuyWay(String sbuyWay){
		this.sbuyWay= sbuyWay;
	}
	/* 购买记录编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 接口编号 */
	private String sinterfaceCode;
	
	public String getSinterfaceCode(){
		return sinterfaceCode;
	}
	
	public void setSinterfaceCode(String sinterfaceCode){
		this.sinterfaceCode= sinterfaceCode;
	}
	/* 支付流水号 */
	private String spayNumber;
	
	public String getSpayNumber(){
		return spayNumber;
	}
	
	public void setSpayNumber(String spayNumber){
		this.spayNumber= spayNumber;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 用户编号 */
	private String suserCode;
	
	public String getSuserCode(){
		return suserCode;
	}
	
	public void setSuserCode(String suserCode){
		this.suserCode= suserCode;
	}
	/* 用户ID */
	private String suserId;
	
	public String getSuserId(){
		return suserId;
	}
	
	public void setSuserId(String suserId){
		this.suserId= suserId;
	}
	/* 购买时间 */
	private Date tbuyTime;
	
	public Date getTbuyTime(){
		return tbuyTime;
	}
	
	public void setTbuyTime(Date tbuyTime){
		this.tbuyTime= tbuyTime;
	}
	/* 支付成功时间 */
	private Date tpayFinishTime;
	
	public Date getTpayFinishTime(){
		return tpayFinishTime;
	}
	
	public void setTpayFinishTime(Date tpayFinishTime){
		this.tpayFinishTime= tpayFinishTime;
	}
	/* 修改日期 */
	private Date updateTime;
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime= updateTime;
	}

		
}