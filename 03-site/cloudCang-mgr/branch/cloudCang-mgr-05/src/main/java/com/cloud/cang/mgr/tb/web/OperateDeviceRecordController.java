package com.cloud.cang.mgr.tb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tb.domain.OperateDeviceRecordDomain;
import com.cloud.cang.mgr.tb.domain.ThirdOrderCommodityDomain;
import com.cloud.cang.mgr.tb.service.OperateDeviceDetailService;
import com.cloud.cang.mgr.tb.service.OperateDeviceRecordService;
import com.cloud.cang.mgr.tb.service.ThirdDeviceSkuService;
import com.cloud.cang.mgr.tb.vo.OperateDeviceRecordVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tb.OperateDeviceDetail;
import com.cloud.cang.model.tb.OperateDeviceRecord;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceController
 * @Description 设备管理controller
 * @Author zengzexiong
 * @Date 2018年1月24日15:10:20
 */
@Controller
@RequestMapping("/tbOperateDeviceRecord")
public class OperateDeviceRecordController extends GenericController {
    private static final Logger logger = LoggerFactory.getLogger(OperateDeviceRecordController.class);

    @Autowired
    OperatorService operatorService;    // 查询条件

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    DeviceInfoService deviceInfoService;//0.设备基础信息

    @Autowired
    OperateDeviceRecordService operateDeviceRecordService;  // 第三方订单补货单

    @Autowired
    OperateDeviceDetailService operateDeviceDetailService;  // 第三方订单补货单明细表

    @Autowired
    ThirdDeviceSkuService thirdDeviceSkuService;    // 第三方商户设备SKU库

    /**
     * 第三方订单数据列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "tb/record/thirdOperateDeviceRecord-list";
    }

    /**
     * 第三方订单数据
     *
     * @param operateDeviceRecordVo 初始化页面对象
     * @param paramPage             初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<OperateDeviceRecordDomain>> queryData(OperateDeviceRecordVo operateDeviceRecordVo, ParamPage paramPage) {
        logger.debug("开始分页查询第三方商户订单");
        PageListVo<List<OperateDeviceRecordDomain>> pageListVo;
        pageListVo = new PageListVo<List<OperateDeviceRecordDomain>>();
        Page<OperateDeviceRecordDomain> page = new Page<>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == operateDeviceRecordVo) {
            operateDeviceRecordVo = new OperateDeviceRecordVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        operateDeviceRecordVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                operateDeviceRecordVo.setOrderStr(" D.sname " + paramPage.getSord() + ",");
            } else {
                operateDeviceRecordVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = operateDeviceRecordService.selectPageByDomainWhere(page, operateDeviceRecordVo);
        logger.debug("第三方分页查询完成");


        //将分页查询结果转换成页面List集合
//        pageToPageList(pageListVo, page);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }

        return pageListVo;
    }


    /**
     * 查看详情页面
     *
     * @param sid
     * @param modelMap
     * @return
     */
    @RequestMapping("/view")
    public String infoView(String sid, ModelMap modelMap) {
        logger.debug("开始查看第三方商户订单详情，订单ID：{}", sid);
        try {
            if (StringUtils.isNotBlank(sid)) {
                String deviceCode = "";
                String deviceId = "";
                List<ThirdOrderCommodityDomain> thirdOrderCommodityDomainList = new ArrayList<>();
                OperateDeviceRecord operateDeviceRecord = operateDeviceRecordService.selectByPrimaryKey(sid);
                if (null != operateDeviceRecord) {
                    deviceId = operateDeviceRecord.getSdeviceId();
                    deviceCode = operateDeviceRecord.getSdeviceCode();
                    logger.debug("第三方订单中设备编号为：{}", deviceCode);
                    logger.debug("第三方订单编号为：{}", operateDeviceRecord.getScode());
                }
                OperateDeviceDetail operateDeviceDetailVo = new OperateDeviceDetail();
                operateDeviceDetailVo.setSoperateId(sid);
                List<OperateDeviceDetail> operateDeviceDetailList = operateDeviceDetailService.selectByEntityWhere(operateDeviceDetailVo);  // 查询订单中所有的商品
                // 查询第三方商户设备SKU库
                if (StringUtil.isNotBlank(deviceId) && StringUtil.isNotBlank(deviceCode) && CollectionUtils.isNotEmpty(operateDeviceDetailList)) {
                    ThirdDeviceSku thirdDeviceSkuVo = new ThirdDeviceSku();
                    thirdDeviceSkuVo.setSdeviceId(deviceId);
                    thirdDeviceSkuVo.setSdeviceCode(deviceCode);
                    List<ThirdDeviceSku> thirdDeviceSkuList = thirdDeviceSkuService.selectByEntityWhere(thirdDeviceSkuVo);
                    if (CollectionUtils.isNotEmpty(thirdDeviceSkuList)) {
                        for (ThirdDeviceSku t : thirdDeviceSkuList) {
                            for (OperateDeviceDetail d : operateDeviceDetailList) {
                                if (t.getSvrCode().equals(d.getSvrCode())) {
                                    ThirdOrderCommodityDomain thirdOrderCommodityDomain = new ThirdOrderCommodityDomain();
                                    thirdOrderCommodityDomain.setIweight(t.getIweight());
                                    thirdOrderCommodityDomain.setSoperateId(d.getSoperateId());
                                    thirdOrderCommodityDomain.setSoperateCode(d.getSoperateCode());
                                    thirdOrderCommodityDomain.setSthirdPartSkuCode(d.getSthirdPartSkuCode());
                                    thirdOrderCommodityDomain.setSvrId(d.getSvrId());
                                    thirdOrderCommodityDomain.setSvrCode(d.getSvrCode());
                                    thirdOrderCommodityDomain.setSvrIncrement(d.getSvrIncrement());
                                    thirdOrderCommodityDomain.setSvrDecrease(d.getSvrDecrease());
                                    thirdOrderCommodityDomain.setSvrTotalAmount(d.getSvrTotalAmount());
                                    thirdOrderCommodityDomain.setSvrName(d.getSvrName());
                                    thirdOrderCommodityDomain.setFprice(d.getFprice());
                                    thirdOrderCommodityDomain.setFaddTotalAmount(d.getFaddTotalAmount());
                                    thirdOrderCommodityDomain.setFaddTotalAmount(d.getFaddTotalAmount());
                                    thirdOrderCommodityDomainList.add(thirdOrderCommodityDomain);
                                }
                            }
                        }

                    }
                }
                modelMap.put("thirdOrderCommodityDomainList", thirdOrderCommodityDomainList);
                String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.view.third.order.info"), deviceCode);
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                return "tb/record/thirdOperateDeviceRecord-view";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

}
