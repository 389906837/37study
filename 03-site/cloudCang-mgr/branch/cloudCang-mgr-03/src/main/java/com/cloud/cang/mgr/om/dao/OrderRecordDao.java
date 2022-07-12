package com.cloud.cang.mgr.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.mgr.sh.domain.DomainConfDomain;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.model.om.OrderRecord;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 商品订单记录信息表(OM_ORDER_RECORD)
 **/
public interface OrderRecordDao extends GenericDao<OrderRecord, String> {


    /**
     * codegen
     **/
    Page<OrderRecordVo> selectPageByDomainWhere(OrderRecordDomain orderRecordDomain);

    /**
     * 根据设备ID和商品ID查询该设备商品的总销量
     *
     * @param map
     * @return
     */
    Integer selectSalesNumByDeviceIdAndCommodityId(Map<String, String> map);

    /**
     * 查询导出的Excel数据
     *
     * @param orderRecordDomain
     * @return List<OrderRecordVo>
     */
    List<Map<String, Object>> selectDownloadExcelData(OrderRecordDomain orderRecordDomain);

    /**
     * 订单列表页脚总统计
     *
     * @param orderRecordDomain
     * @return OrderRecordMoneyStaVo
     */
    OrderRecordMoneyStaVo queryTotalData(OrderRecordDomain orderRecordDomain);
}