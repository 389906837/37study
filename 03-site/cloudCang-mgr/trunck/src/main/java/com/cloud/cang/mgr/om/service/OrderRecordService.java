package com.cloud.cang.mgr.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface OrderRecordService extends GenericService<OrderRecord, String> {

    Page<OrderRecordVo> selectPageByDomainWhere(Page<OrderRecordVo> page, OrderRecordDomain orderRecordDomain);

    ResponseVo saveOrder(OrderRecord orderRecord, HttpServletRequest request, String commodityIds) throws UnsupportedEncodingException;

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


    /**
     * 撤销订单
     *
     * @param orderId
     * @return
     */
    ResponseVo cancelOrder(String orderId) throws Exception;
}