package com.cloud.cang.act.ac.service;

import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface DiscountRecordService extends GenericService<DiscountRecord, String> {

    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param memberId 会员ID
     * @param couponId 券ID
     * @param
     * @return DiscountRecord
     */
    DiscountRecord selectByMmberIdAndCouponId(String memberId, String couponId);

    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param map 查询条件
     * @param
     * @return Integer
     */
    Integer selectCount(Map map);
}