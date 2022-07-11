package com.cang.os.security.vo;

import java.io.Serializable;

/**
 * 保存用户的数据，存储到Session中
 * @author jili
 *
 */
public class AuthorizationVO implements Serializable {

	private String id;
	private String userName;
	private String code;
	private boolean freeze;
	private String password;
	
	private String realName;
	private String mobile;
	private String email;
	private String lastLoginTime;
	private boolean isAuth=false;
	private Integer memberType=1;
	private Integer sex;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -197569891751143211L;

	public AuthorizationVO() {

	}

	public AuthorizationVO(String operatorID, String userName,
			String code) {
		super();
		this.id = operatorID;
		this.userName = userName;
		this.code = code;
	}
	public String getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }
	
}
