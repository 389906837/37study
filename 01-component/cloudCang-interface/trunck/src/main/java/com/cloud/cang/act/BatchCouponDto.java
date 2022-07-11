/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月9日
 * Description:BatchCouponDto.java 
 */
package com.cloud.cang.act;

import com.cloud.cang.common.SuperDto;

/**
 * @author zhouhong
 * @version 1.0
 */
public class BatchCouponDto extends SuperDto {


    private static final long serialVersionUID = 2467416290756123724L;

    private String saddUser;//添加人姓名
    private String batchId;//优惠券批量ID


    public String getSaddUser() {
        return saddUser;
    }

    public void setSaddUser(String saddUser) {
        this.saddUser = saddUser;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return "BatchCouponDto{" +
                "saddUser='" + saddUser + '\'' +
                ", batchId='" + batchId + '\'' +
                '}';
    }
}
