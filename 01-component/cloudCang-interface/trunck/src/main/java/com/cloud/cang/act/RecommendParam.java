package com.cloud.cang.act;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐相关参数
 * @author zhouhong
 * @version 1.0
 */
public class RecommendParam implements Serializable {

	private static final long serialVersionUID = -987452344602402383L;

	/**推荐人ID*/
	private String recUserId;
	
	/**推荐人编号*/
	private String recUserCode;
	
	/**推荐人姓名*/
	private String recUserRealName;
	
	/**
	 * 推荐人生日
	 */
	private Date recUserBirthDay;
	
	/**注册时间*/
	private Date registTime;

	public String getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(String recUserId) {
		this.recUserId = recUserId;
	}

	public String getRecUserCode() {
		return recUserCode;
	}

	public void setRecUserCode(String recUserCode) {
		this.recUserCode = recUserCode;
	}

	public String getRecUserRealName() {
		return recUserRealName;
	}

	public void setRecUserRealName(String recUserRealName) {
		this.recUserRealName = recUserRealName;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Date getRecUserBirthDay() {
		return recUserBirthDay;
	}
	public void setRecUserBirthDay(Date recUserBirthDay) {
		this.recUserBirthDay = recUserBirthDay;
	}

	@Override
	public String toString() {
		return " [recUserCode=" + recUserCode + ", recUserId=" + recUserId + ", recUserRealName="
				+ recUserRealName + ", registTime=" + registTime + ",recUserBirthDay="+recUserBirthDay+"]";
	}

}
