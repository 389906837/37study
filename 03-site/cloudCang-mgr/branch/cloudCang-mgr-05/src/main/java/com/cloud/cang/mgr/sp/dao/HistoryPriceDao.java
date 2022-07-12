package com.cloud.cang.mgr.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sp.HistoryPrice;

/**
 * 商品历史价格记录表(SP_HISTORY_PRICE)
 **/
public interface HistoryPriceDao extends GenericDao<HistoryPrice, String> {

    /**
     * 根据类型查询平均价
     * @param historyPrice
     * @return
     */
    BigDecimal selectAvgPriceById(HistoryPrice historyPrice);


    /** codegen **/
}