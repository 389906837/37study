package com.cloud.cang.rec.sys.vo;

import com.cloud.cang.model.sys.Role;

/**
* @description: 用户角色 Vo
* @author:Yanlingfeng
* @time:2018-02-22 14:07:05
* @version 1.0
*/
public class RoleVo extends Role {
   //是否已有角色 0:没有 1:有
   private  String  isSelect;

   private  String merchantName;//商户名
   private  String  soperatorId;

   public String getMerchantName() {
       return merchantName;
   }

   public void setMerchantName(String merchantName) {
       this.merchantName = merchantName;
   }

   public String getSoperatorId() {
       return soperatorId;
   }

   public void setSoperatorId(String soperatorId) {
       this.soperatorId = soperatorId;
   }

   public String getIsSelect() {
       return isSelect;
   }

   public void setIsSelect(String isSelect) {
       this.isSelect = isSelect;
   }
}
