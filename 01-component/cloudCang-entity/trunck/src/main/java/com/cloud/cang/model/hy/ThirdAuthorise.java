package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 第三方授权信息表(HY_THIRD_AUTHORISE) **/
public class ThirdAuthorise extends GenericEntity  {

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
	
	/* 10：关联
            20：解除关联 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 10:微信
            20:支付宝 */
	private Integer ithirdType;
	
	public Integer getIthirdType(){
		return ithirdType;
	}
	
	public void setIthirdType(Integer ithirdType){
		this.ithirdType= ithirdType;
	}
	/* 访问TOKEN */
	private String saccessToken;
	
	public String getSaccessToken(){
		return saccessToken;
	}
	
	public void setSaccessToken(String saccessToken){
		this.saccessToken= saccessToken;
	}
	/* 城市 */
	private String scity;
	
	public String getScity(){
		return scity;
	}
	
	public void setScity(String scity){
		this.scity= scity;
	}
	/* T是通过 F是没有实名认证  */
	private String sisCertified;
	
	public String getSisCertified(){
		return sisCertified;
	}
	
	public void setSisCertified(String sisCertified){
		this.sisCertified= sisCertified;
	}
	/* T是学生 F不是学生  */
	private String sisStudentCertified;
	
	public String getSisStudentCertified(){
		return sisStudentCertified;
	}
	
	public void setSisStudentCertified(String sisStudentCertified){
		this.sisStudentCertified= sisStudentCertified;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 第三方用户唯一标识 */
	private String sopenId;
	
	public String getSopenId(){
		return sopenId;
	}
	
	public void setSopenId(String sopenId){
		this.sopenId= sopenId;
	}
	/* 用户头像 */
	private String sotherImg;
	
	public String getSotherImg(){
		return sotherImg;
	}
	
	public void setSotherImg(String sotherImg){
		this.sotherImg= sotherImg;
	}
	/* 用户第三方昵称 */
	private String sotherNickname;
	
	public String getSotherNickname(){
		return sotherNickname;
	}
	
	public void setSotherNickname(String sotherNickname){
		this.sotherNickname= sotherNickname;
	}
	/* 省份 */
	private String sprovince;
	
	public String getSprovince(){
		return sprovince;
	}
	
	public void setSprovince(String sprovince){
		this.sprovince= sprovince;
	}
	/* 刷新TOKEN */
	private String srefuseToken;
	
	public String getSrefuseToken(){
		return srefuseToken;
	}
	
	public void setSrefuseToken(String srefuseToken){
		this.srefuseToken= srefuseToken;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 第三方用户唯一标识 */
	private String sthirdUserid;
	
	public String getSthirdUserid(){
		return sthirdUserid;
	}
	
	public void setSthirdUserid(String sthirdUserid){
		this.sthirdUserid= sthirdUserid;
	}
	/* 用户状态Q代表快速注册用户 T代表已认证用户 B代表被冻结账户 W代表已注册，未激活的账户  */
	private String suserStatus;
	
	public String getSuserStatus(){
		return suserStatus;
	}
	
	public void setSuserStatus(String suserStatus){
		this.suserStatus= suserStatus;
	}
	/* 用户类型1代表公司账户2代表个人账户  */
	private String suserType;
	
	public String getSuserType(){
		return suserType;
	}
	
	public void setSuserType(String suserType){
		this.suserType= suserType;
	}
	/* 授权时间 */
	private Date tauthoriseTime;
	
	public Date getTauthoriseTime(){
		return tauthoriseTime;
	}
	
	public void setTauthoriseTime(Date tauthoriseTime){
		this.tauthoriseTime= tauthoriseTime;
	}

		
}