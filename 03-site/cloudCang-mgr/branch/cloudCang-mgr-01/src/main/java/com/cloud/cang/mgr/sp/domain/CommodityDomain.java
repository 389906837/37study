package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.model.sp.CommodityInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @version 1.0
 * @Description: 商品列表返回domain
 * @Author: zhouhong
 * @Date: 2018/2/10 11:14
 */
public class CommodityDomain extends CommodityInfo {

   private String merchantName;//商户名称
   private String commodityFullName;   //商品全名 = 品牌+商品名+口味+规格重量+规格单位+/最小单位

   public String getCommodityFullName() {
      StringBuffer sb = new StringBuffer();
      if (StringUtils.isNotBlank(this.getSbrandName())) {
         sb.append(this.getSbrandName());
      }
      if (StringUtils.isNotBlank(this.getSname())) {
         sb.append(this.getSname());
      }
      if (StringUtils.isNotBlank(this.getStaste())) {
         sb.append(this.getStaste());
      }
      if (null != this.getIspecWeight()) {
         sb.append(this.getIspecWeight());
      }
      if (null != this.getSspecUnit()) {
         sb.append(this.getSspecUnit());
      }
      if (StringUtils.isNotBlank(this.getSpackageUnit())) {
         sb.append("/"+this.getSpackageUnit());
      }
      return sb.toString();
   }

   public void setCommodityFullName(String commodityFullName) {
      this.commodityFullName = commodityFullName;
   }

   public String getMerchantName() {
      return merchantName;
   }

   public void setMerchantName(String merchantName) {
      this.merchantName = merchantName;
   }
}
