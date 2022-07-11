package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * 商品对象
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年3月19日16:45:47
 */
public class CommodityDto extends SuperDto {

    private String commodityId;//商品ID
    private String commodityName;//商品名称
    private Integer num;//商品数量

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


}
