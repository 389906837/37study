package com.cloud.cang.mgr.report.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.report.domain.CommodityReportDomain;
import com.cloud.cang.mgr.report.domain.DeviceSalesDetailReportDomain;
import com.cloud.cang.mgr.report.service.ReportService;
import com.cloud.cang.mgr.report.vo.CommodityReportVo;
import com.cloud.cang.mgr.report.vo.DeviceSalesDetailReportVo;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备销售报表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/deviceReport")
public class DeviceSalesReportController {
    private static final Logger log = LoggerFactory.getLogger(DeviceSalesReportController.class);
    @Autowired
    private ReportService reportService;

    @RequestMapping("/list")
    public String list() {
        return "report/device/deviceReport-list";
    }

    /**
     * 设备销售报表
     *
     * @param deviceSalesDetailReportDomain
     * @return PageListVo<List<DeviceSalesDetailReportVo>>
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<DeviceSalesDetailReportVo>> queryData(ParamPage paramPage, DeviceSalesDetailReportDomain deviceSalesDetailReportDomain) {


        PageListVo<List<DeviceSalesDetailReportVo>> pageListVo = new PageListVo<List<DeviceSalesDetailReportVo>>();
        try {
            Page<DeviceSalesDetailReportVo> page = new Page<DeviceSalesDetailReportVo>();
            page.setPageNum(paramPage.getPage());
            page.setPageSize(paramPage.getLimit());
            if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
                deviceSalesDetailReportDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
            String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
            deviceSalesDetailReportDomain.setMerchantId(merchantId);
            if (StringUtils.isNotBlank(deviceSalesDetailReportDomain.getQueryTimeCondition())) {
                String start = deviceSalesDetailReportDomain.getQueryTimeCondition().split(" - ")[0];
                String end = deviceSalesDetailReportDomain.getQueryTimeCondition().split(" - ")[1];

                if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    //时间差
                    int subRes = DateUtil.getDiffDays(format.parse(end), format.parse((start)));
                    String condition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
                    deviceSalesDetailReportDomain.setCondition(condition);
                    Calendar calendar = Calendar.getInstance();// 得到一个Calendar的实例
                    calendar.setTime(format.parse(start));
                    calendar.add(Calendar.YEAR, -1);
                    Date date = calendar.getTime();
                    start = format.format(date);
                    calendar.setTime(format.parse(end));
                    calendar.add(Calendar.YEAR, -1);
                    date = calendar.getTime();
                    end = format.format(date);
                    String lastCondition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
                    deviceSalesDetailReportDomain.setLastCondition(lastCondition);
                    //end
                    calendar.setTime(format.parse(start));
                    calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.DATE, -1);
                    date = calendar.getTime();
                    end = format.format(date);
                    //start
                    calendar.setTime(format.parse(end));
                    calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.DATE, subRes);
                    date = calendar.getTime();
                    start = format.format(date);
                    String lastTimeCondition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
                    deviceSalesDetailReportDomain.setLastTimeCondition(lastTimeCondition);
                }
            }
            page = reportService.selectDeviceReportPageByDomain(page, deviceSalesDetailReportDomain);
            List<DeviceSalesDetailReportVo> deviceSalesDetailReportVoList = page.getResult();
            if (null != deviceSalesDetailReportVoList && deviceSalesDetailReportVoList.size() > 0) {
                for (DeviceSalesDetailReportVo deviceSalesDetailReportVo : deviceSalesDetailReportVoList) {
                    //销售金额同比
                    String res = getRateOfChangeResult(deviceSalesDetailReportVo.getDeviceSalesAmount(), deviceSalesDetailReportVo.getLastYearSaleAmount());
                    deviceSalesDetailReportVo.setSalesAmountYearOnYear(res);
                    //销售金额环比
                    res = getRateOfChangeResult(deviceSalesDetailReportVo.getDeviceSalesAmount(), deviceSalesDetailReportVo.getLastTimeSaleAmount());
                    deviceSalesDetailReportVo.setSalesAmountAnnulusRatio(res);
                }
            }
            if (page != null) {
                pageListVo.setPage(page.getPageNum());
                pageListVo.setRecords(page.getTotal());
                pageListVo.setTotal(page.getPages());
                pageListVo.setRows(deviceSalesDetailReportVoList);
            }
        } catch (Exception e) {
            log.error("设备销售报表异常：{}",e);
        }
        return pageListVo;
    }

    private String getRateOfChangeResult(BigDecimal thisData, BigDecimal LastData) {
        String result = "";
        BigDecimal subResult = thisData.subtract(LastData);
        if (0 == BigDecimal.ZERO.compareTo(subResult)) {
            result = "0%";
        } else if (0 == BigDecimal.ZERO.compareTo(thisData)) {
            result = LastData.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString() + "%";
        } else if (0 == BigDecimal.ZERO.compareTo(LastData)) {
            result = thisData.multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString() + "%";
        } else {
            BigDecimal b = subResult.divide(LastData, 2, BigDecimal.ROUND_HALF_UP);
            //下面将结果转化成百分比
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(2);
            result = percent.format(b.doubleValue());
        }
        return result;
    }

    /**
     * 商品报表列表页脚总统计
     *
     * @param deviceSalesDetailReportDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<BigDecimal> queryTotalData(DeviceSalesDetailReportDomain deviceSalesDetailReportDomain) {
        try {
            String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
            deviceSalesDetailReportDomain.setMerchantId(merchantId);
            if (StringUtils.isNotBlank(deviceSalesDetailReportDomain.getQueryTimeCondition())) {
                String start = deviceSalesDetailReportDomain.getQueryTimeCondition().split(" - ")[0];
                String end = deviceSalesDetailReportDomain.getQueryTimeCondition().split(" - ")[1];

                if (StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                    //时间差
                    int subRes = DateUtil.getDiffDays(format.parse(end), format.parse((start)));
                    String condition = "  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TPAY_COMPLETE_TIME, '%Y-%m-%d') <='" + end + "'";
                    deviceSalesDetailReportDomain.setCondition(condition);
                }
            }
            BigDecimal bigDecimal = reportService.queryDeviceReportTotalData(deviceSalesDetailReportDomain);
            return ResponseVo.getSuccessResponse(bigDecimal);
        } catch (Exception e) {
            log.error("查询设备报表页脚总合计异常：{}", e);
        }
        return null;
    }

}
