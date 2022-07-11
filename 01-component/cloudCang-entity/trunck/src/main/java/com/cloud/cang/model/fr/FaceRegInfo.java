package com.cloud.cang.model.fr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 人脸识别会员注册表(FR_FACE_REG_INFO) **/
public class FaceRegInfo extends GenericEntity  {

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
	
	/* 审核状态
            10=待审核
            20=审核通过
            30=审核拒绝 */
	private Integer iauditStatus;
	
	public Integer getIauditStatus(){
		return iauditStatus;
	}
	
	public void setIauditStatus(Integer iauditStatus){
		this.iauditStatus= iauditStatus;
	}
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 注册来源
            10=后台注册
            20=设备注册
            30=H5页面注册 */
	private Integer iregSource;
	
	public Integer getIregSource(){
		return iregSource;
	}
	
	public void setIregSource(Integer iregSource){
		this.iregSource= iregSource;
	}
	/* 30=已申请
            10=已注册
            20=已删除 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 审核意见 */
	private String sauditRemark;
	
	public String getSauditRemark(){
		return sauditRemark;
	}
	
	public void setSauditRemark(String sauditRemark){
		this.sauditRemark= sauditRemark;
	}
	/* 注册设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 人脸编号 */
	private String sfaceCode;
	
	public String getSfaceCode(){
		return sfaceCode;
	}
	
	public void setSfaceCode(String sfaceCode){
		this.sfaceCode= sfaceCode;
	}
	/* 人脸唯一标志 */
	private String sfaceToken;
	
	public String getSfaceToken(){
		return sfaceToken;
	}
	
	public void setSfaceToken(String sfaceToken){
		this.sfaceToken= sfaceToken;
	}
	/* 人脸分组编号 */
	private String sgroupId;
	
	public String getSgroupId(){
		return sgroupId;
	}
	
	public void setSgroupId(String sgroupId){
		this.sgroupId= sgroupId;
	}
	/* 人脸区域的高度 */
	private String sheight;
	
	public String getSheight(){
		return sheight;
	}
	
	public void setSheight(String sheight){
		this.sheight= sheight;
	}
	/* 人脸区域离左边界的距离 */
	private String sleft;
	
	public String getSleft(){
		return sleft;
	}
	
	public void setSleft(String sleft){
		this.sleft= sleft;
	}
	/* 操作人 */
	private String soperMan;
	
	public String getSoperMan(){
		return soperMan;
	}
	
	public void setSoperMan(String soperMan){
		this.soperMan= soperMan;
	}
	/* 人脸框相对于竖直方向的顺时针旋转角，[-180,180] */
	private String sratation;
	
	public String getSratation(){
		return sratation;
	}
	
	public void setSratation(String sratation){
		this.sratation= sratation;
	}
	/* 真实姓名 */
	private String srealName;
	
	public String getSrealName(){
		return srealName;
	}
	
	public void setSrealName(String srealName){
		this.srealName= srealName;
	}
	/* 注册时上传的人脸图片地址 */
	private String sregisterFaceAddress;
	
	public String getSregisterFaceAddress(){
		return sregisterFaceAddress;
	}
	
	public void setSregisterFaceAddress(String sregisterFaceAddress){
		this.sregisterFaceAddress= sregisterFaceAddress;
	}
	/* 注册设备编号 */
	private String sregAiCode;
	
	public String getSregAiCode(){
		return sregAiCode;
	}
	
	public void setSregAiCode(String sregAiCode){
		this.sregAiCode= sregAiCode;
	}
	/* 注册IP */
	private String sregIp;
	
	public String getSregIp(){
		return sregIp;
	}
	
	public void setSregIp(String sregIp){
		this.sregIp= sregIp;
	}
	/* 人脸区域离上边界的距离 */
	private String stop;
	
	public String getStop(){
		return stop;
	}
	
	public void setStop(String stop){
		this.stop= stop;
	}
	/* 注册人脸库对应的用户资料 */
	private String suserInfo;
	
	public String getSuserInfo(){
		return suserInfo;
	}
	
	public void setSuserInfo(String suserInfo){
		this.suserInfo= suserInfo;
	}
	/* 人脸区域的宽度 */
	private String swidth;
	
	public String getSwidth(){
		return swidth;
	}
	
	public void setSwidth(String swidth){
		this.swidth= swidth;
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
	/* 删除时间 */
	private Date tdeleteTime;
	
	public Date getTdeleteTime(){
		return tdeleteTime;
	}
	
	public void setTdeleteTime(Date tdeleteTime){
		this.tdeleteTime= tdeleteTime;
	}
	/* 注册时间 */
	private Date tregisterTime;
	
	public Date getTregisterTime(){
		return tregisterTime;
	}
	
	public void setTregisterTime(Date tregisterTime){
		this.tregisterTime= tregisterTime;
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