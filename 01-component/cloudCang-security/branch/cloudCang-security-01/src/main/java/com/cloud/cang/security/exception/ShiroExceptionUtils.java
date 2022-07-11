package com.cloud.cang.security.exception;

/**
 * 类功能描述: Shiro异常信息转换成字符工具
 * @author zhouhong
 * @version V1.0, 2014-4-17
 */
public class ShiroExceptionUtils {
    
    public static String getExceptionCString(String exceptionClassName) {
        if(exceptionClassName==null){
            return "用户名或密码错误！";
        }
        //UsernamePasswordToken.class
        if (exceptionClassName.indexOf("IncorrectCredentialsException")!=-1) {
            return "用户名或密码错误！";
        }  else if (exceptionClassName.indexOf("LockedAccountException")!=-1) {
            return "账号被锁定！";
        }  else if (exceptionClassName.indexOf("LockedAccountException")!=-1) {
            return "用户名或密码错误！";
        } else if (exceptionClassName.indexOf("IncorrectCaptchaException")!=-1) {
            return "验证码错误！";
        } else if (exceptionClassName.indexOf("BindWeixinException")!=-1){
        	 return "已有微信号绑定该账号！";
        } else if (exceptionClassName.indexOf("EnterpriseUserException")!=-1){
       	    return "企业用户无法登陆WAP，请在电脑上访问www.37cang.com";
       }else if (exceptionClassName.indexOf("EnterpriseBindCardException")!=-1){
            return "您尚未绑定银行卡，无法登陆WAP";
        }else if (exceptionClassName.indexOf("EnterpriseCertificationException")!=-1){
            return "开通存管账户后才能登录";
        }else if (exceptionClassName.indexOf("EnterpriseInvestmentException")!=-1){
            return "您不具备移动端登录权限";
        } else {
            return "用户名或密码错误！";
        }
    }
}
