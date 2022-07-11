package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 编号生成配置表(SYS_CODE_GENERATOR) **/
public class CodeGenerator extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	private Long id;
	/*主键*/
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		 this.id= id;
	}
	
	/* 是否重新生成 */
	private Integer banewBegin;
	
	public Integer getBanewBegin(){
		return banewBegin;
	}
	
	public void setBanewBegin(Integer banewBegin){
		this.banewBegin= banewBegin;
	}
	/* 当前日期 */
	private Date dcurrentDate;
	
	public Date getDcurrentDate(){
		return dcurrentDate;
	}
	
	public void setDcurrentDate(Date dcurrentDate){
		this.dcurrentDate= dcurrentDate;
	}
	/* 长度 */
	private Integer icodeSize;
	
	public Integer getIcodeSize(){
		return icodeSize;
	}
	
	public void setIcodeSize(Integer icodeSize){
		this.icodeSize= icodeSize;
	}
	/* 编码起始值 */
	private Integer icodeStart;
	
	public Integer getIcodeStart(){
		return icodeStart;
	}
	
	public void setIcodeStart(Integer icodeStart){
		this.icodeStart= icodeStart;
	}
	/* 步长 */
	private Integer icodeStep;
	
	public Integer getIcodeStep(){
		return icodeStep;
	}
	
	public void setIcodeStep(Integer icodeStep){
		this.icodeStep= icodeStep;
	}
	/* 删除标记 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 日期格式化 */
	private String scodeDateformat;
	
	public String getScodeDateformat(){
		return scodeDateformat;
	}
	
	public void setScodeDateformat(String scodeDateformat){
		this.scodeDateformat= scodeDateformat;
	}
	/* 前缀 */
	private String scodePrefix;
	
	public String getScodePrefix(){
		return scodePrefix;
	}
	
	public void setScodePrefix(String scodePrefix){
		this.scodePrefix= scodePrefix;
	}
	/* 编码类型 */
	private String scodeType;
	
	public String getScodeType(){
		return scodeType;
	}
	
	public void setScodeType(String scodeType){
		this.scodeType= scodeType;
	}
	/* 最后一次编号值 */
	private String slastCodeValue;
	
	public String getSlastCodeValue(){
		return slastCodeValue;
	}
	
	public void setSlastCodeValue(String slastCodeValue){
		this.slastCodeValue= slastCodeValue;
	}

		
}