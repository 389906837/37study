package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.GroupManage;

import java.util.Date;

/**
 * Created by Alex on 2018/2/9.
 */
public class GroupManageVo extends GroupManage {


//    private String id;/*主键*/
//    private Integer isort;/* 排序号 */
//    private String saddUser;/* 添加人 */
//    private String sgroupName;/* 组名 */
//    private String sremark;/* 备注 */
//    private String supdateUser;/* 修改人 */
//    private Date taddTime;/* 添加日期 */
//    private Date tupdateTime;/* 修改日期 */

    private String orderStr;//排序字段
    private String merchantName;//商户名称
    private String queryCondition;//查询条件

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
