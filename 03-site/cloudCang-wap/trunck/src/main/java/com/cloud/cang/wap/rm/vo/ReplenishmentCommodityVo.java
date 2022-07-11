package com.cloud.cang.wap.rm.vo;

import com.cloud.cang.model.rm.ReplenishmentCommodity;

/**
 * @version 1.0
 * @Description: 补货单商品信息
 * @Author: zhouhong
 * @Date: 2018/5/24 16:37
 */
public class ReplenishmentCommodityVo extends ReplenishmentCommodity {

    private Integer istock;//当前库存

    public Integer getIstock() {
        return istock;
    }

    public void setIstock(Integer istock) {
        this.istock = istock;
    }

    @Override
    public String toString() {
        return "ReplenishmentCommodityVo{" +
                "istock=" + istock +
                '}';
    }
}
