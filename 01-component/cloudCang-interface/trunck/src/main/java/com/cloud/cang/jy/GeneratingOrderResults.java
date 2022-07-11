package com.cloud.cang.jy;

import com.cloud.cang.common.SuperDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @Description: 生成订单返回对象
 * @Author: yanlingfeng
 */
public class GeneratingOrderResults extends SuperDto {
    private List<CreatOrderResult> creatOrderResultList;//生成订单结果
    private Integer isFirstOrder;//是否首单0:否 1:是


    public Integer getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(Integer isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }

    public List<CreatOrderResult> getCreatOrderResultList() {
        return creatOrderResultList;
    }

    public void setCreatOrderResultList(List<CreatOrderResult> creatOrderResultList) {
        this.creatOrderResultList = creatOrderResultList;
    }
}

