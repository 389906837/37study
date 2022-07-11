package com.cloud.cang.bzc.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.inventory.CommodityVo;
import com.cloud.cang.model.sp.CommodityRfid;
import org.apache.ibatis.annotations.Param;

/** 商品标签表(SP_COMMODITY_RFID) **/
public interface CommodityRfidDao extends GenericDao<CommodityRfid, String> {


	/** codegen **/

    /**
     * 根据rfids标签集合，查询标签所属哪个商品
     * @param lables
     * @return
     */
    List<CommodityVo> selectCommodityVoGruopByCommodityCode(@Param("lables") Set<String> lables);
}