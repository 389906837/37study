package com.cloud.cang.act.ac.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.ac.DiscountRecord;

/**
 * 活动优惠记录表(AC_DISCOUNT_RECORD)
 **/
public interface DiscountRecordDao extends GenericDao<DiscountRecord, String> {
    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param map (会员ID,来源单据Id,券编号)
     * @return DiscountRecord
     */
    DiscountRecord selectByMmberIdAndCouponId(Map<String, String> map);

    /**
     * 根据条件查询活动优惠记录表(会员ID,来源单据Id,券编号)
     *
     * @param map 查询条件
     * @return Integer
     */
    Integer selectCount(Map map);
}