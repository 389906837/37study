package com.cloud.cang.mgr.sys.vo;


import com.cloud.cang.model.sys.Operator;
/**
 * @description: 系统用户 Vo
 * @author:Yanlingfeng
 * @time:2018-02-22 14:07:05
 * @version 1.0
 */
public class OperatorVo extends Operator {

    private  String merchantName;//商户名
    private String orderStr;//排序字段
    private String spasswordl;//确认密码
    private String srole;//角色名
    private String isReplenishment; //是否为补货员 0否/1是
    private  String queryCondition;//查询条件

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getIsReplenishment() {
        return isReplenishment;
    }

    public void setIsReplenishment(String isReplenishment) {
        this.isReplenishment = isReplenishment;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getSpasswordl() {
        return spasswordl;
    }

    public void setSpasswordl(String spasswordl) {
        this.spasswordl = spasswordl;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSrole() {
        return srole;
    }

    public void setSrole(String srole) {
        this.srole = srole;
    }
    
    
    

    
    
    
    
   
    
   

}
