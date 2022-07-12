package com.cloud.cang.mgr.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sys.dao.IndexDao;
import com.cloud.cang.mgr.sys.service.IndexService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.mgr.sys.vo.*;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.tj.StatisticsVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private ICached iCached;
    @Autowired
    private IndexDao indexDao;

    @Override
    public Map<String, Object> getMainData(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //用户数据
        IndexCacheDataVo indexCacheDataVo = new IndexCacheDataVo();
        String key = RedisConst.CURRENT_PLATFORM_DATA_INFO + SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
        try {
            String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
            //缓存数据
            String str = (String) iCached.get(key);
            if (StringUtils.isBlank(str)) {
                indexCacheDataVo.setTdOrderTotal(0);
                indexCacheDataVo.setTdSaleTotal(BigDecimal.ZERO.setScale(2));
            } else {
                StatisticsVo statisticsVo = JSON.parseObject(str, StatisticsVo.class);
                if (null == statisticsVo.getOrderTodayNum()) {
                    indexCacheDataVo.setTdOrderTotal(0);
                } else {
                    indexCacheDataVo.setTdOrderTotal(statisticsVo.getOrderTodayNum());
                }
                if (null == statisticsVo.getOrderTodayAmount()) {
                    indexCacheDataVo.setTdSaleTotal(BigDecimal.ZERO.setScale(2));
                } else {
                    indexCacheDataVo.setTdSaleTotal(statisticsVo.getOrderTodayAmount());
                }
            }
            key = RedisConst.YESTERDAY_TODAY_PLATFORM_DATA_INFO + SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
            str = (String) iCached.get(key);
            if (StringUtils.isBlank(str)) {
                indexCacheDataVo.setYdOrderTotal(0);
                indexCacheDataVo.setYdSaleTotal(BigDecimal.ZERO.setScale(2));
            } else {
                SummarizationDataPlf yesterdayPSlatformData = JSON.parseObject(str, SummarizationDataPlf.class);
                if (null == yesterdayPSlatformData.getIorderNum()) {
                    indexCacheDataVo.setYdOrderTotal(0);
                } else {
                    indexCacheDataVo.setYdOrderTotal(yesterdayPSlatformData.getIorderNum());
                }
                if (null == yesterdayPSlatformData.getForderAmount()) {
                    indexCacheDataVo.setYdSaleTotal(BigDecimal.ZERO.setScale(2));
                } else {
                    indexCacheDataVo.setYdSaleTotal(yesterdayPSlatformData.getForderAmount());
                }
            }
            indexCacheDataVo.setSevenDSaleTotal(indexDao.selectSevenDSaleTotal(merchantId));
            map.put("indexCacheDataVo", indexCacheDataVo);
            //用户总览
            IndexMemberVo indexMemberVo = new IndexMemberVo();
            indexMemberVo.setTdAdd(indexDao.selectTdAdd(merchantId));
            indexMemberVo.setYdAdd(indexDao.selectYdAdd(merchantId));
            indexMemberVo.setmAdd(indexDao.selectMAdd(merchantId));
            indexMemberVo.setMemberTotal(indexDao.selectMemberTotal(merchantId));
            map.put("indexMemberVo", indexMemberVo);
            //商品总览
            IndexCommodityVo indexCommodityVo = new IndexCommodityVo();
            indexCommodityVo.setUpShelfCommodity(indexDao.selectUpShelfCommodity(merchantId));
            indexCommodityVo.setDownShelfCommodity(indexDao.selectDownShelfCommodity(merchantId));
            indexCommodityVo.setCommodityExpireWarn(indexDao.selectCommodityExpireWarn(merchantId, BizParaUtil.get("commodity_expire_warn")));
            indexCommodityVo.setCommodityStockWarn(indexDao.selectCommodityStockWarn(merchantId));
            map.put("indexCommodityVo", indexCommodityVo);
            //待处理事务
            IndexWaitProcess indexWaitProcess = new IndexWaitProcess();
            indexWaitProcess.setpPaymentOrderNum(indexDao.selectPPaymentOrderNum(merchantId));
            indexWaitProcess.setpRefundNum(indexDao.selectPRefundNum(merchantId));
            indexWaitProcess.setpReplenishmentOrderNum(indexDao.selectPReplenishmentOrderNum(merchantId));
            indexWaitProcess.setDeviceFaultNum(indexDao.selectDeviceFaultNum(merchantId));
            indexWaitProcess.setCommodityErrorNum(indexDao.selectCommodityErrorNum(merchantId));
            indexWaitProcess.setStocktakingError(indexDao.selectStocktakingError(merchantId));
            map.put("indexWaitProcess", indexWaitProcess);
            //统计参数
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            //1.本周  上周
            String start = format.format(DateUtil.getBeginDayOfWeek());
            String end = format.format(DateUtil.getEndDayOfWeek());
            Map<String, String> map1 = new HashMap<>();
            map1.put("merchantId", merchantId);
            map1.put("start", start);
            map1.put("end", end);
            String lastStart = format.format(DateUtil.getBeginDayOfLastWeek());
            String lastEnd = format.format(DateUtil.getEndDayOfLastWeek());
            Map<String, String> map2 = new HashMap<>();
            map2.put("merchantId", merchantId);
            map2.put("lastStart", lastStart);
            map2.put("lastEnd", lastEnd);
            //2.本月 上月
            String thisMonthStart = format.format(DateUtil.getBeginDayOfMonth());
            String thisMonthEnd = format.format(DateUtils.getCurrentDateTime());
            Map<String, String> thisMonthMap = new HashMap<>();
            thisMonthMap.put("merchantId", merchantId);
            thisMonthMap.put("thisMonthStart", thisMonthStart);
            thisMonthMap.put("thisMonthEnd", thisMonthEnd);
            String lastMonthStart = format.format(DateUtils.addMonths(DateUtil.getBeginDayOfMonth(), -1));
            String lastMonthEnd = format.format(DateUtils.addMonths(DateUtils.getCurrentDateTime(), -1));
            Map<String, String> lastMonthMap = new HashMap<>();
            thisMonthMap.put("merchantId", merchantId);
            thisMonthMap.put("lastMonthStart", lastMonthStart);
            thisMonthMap.put("lastMonthEnd", lastMonthEnd);
            //订单统计
            OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
            BigDecimal thisMonthOrderNum = indexDao.selectThisMonthOrderNum(thisMonthMap);
            BigDecimal LastMonthOrderNum = indexDao.selectLastMonthOrderNum(lastMonthMap);
            orderStatisticsVo.setThisMonthOrderNum(thisMonthOrderNum);
            orderStatisticsVo.setThisMonthRateOfChange(getRateOfChangeResult2(thisMonthOrderNum, LastMonthOrderNum));
            BigDecimal thisWeekOrderNum = indexDao.selectThisWeekOrderNum(map1);
            BigDecimal LastWeekOrderNum = indexDao.selectLastWeekOrderNum(map2);
            orderStatisticsVo.setThisWeekOrderNum(thisWeekOrderNum);
            orderStatisticsVo.setThisWeekRateOfChange(getRateOfChangeResult2(thisWeekOrderNum, LastWeekOrderNum));
            map.put("orderStatisticsVo", orderStatisticsVo);
            //销售统计
            SalesAmountStatisticsVo salesAmountStatisticsVo = new SalesAmountStatisticsVo();
            BigDecimal thisMonthSalesAmount = indexDao.selectThisMonthSaleAmount(thisMonthMap);
            salesAmountStatisticsVo.setThisMonthSalesAmount(thisMonthSalesAmount);
            BigDecimal lastMonthSalesAmount = indexDao.selectLastMonthSaleAmount(lastMonthMap);
            salesAmountStatisticsVo.setThisMonthRateOfChange(getRateOfChangeResult2(thisMonthSalesAmount, lastMonthSalesAmount));
            BigDecimal thisWeekSaleAmount = indexDao.selectThisWeekSaleAmount(map1);
            salesAmountStatisticsVo.setThisWeekMemberSalesAmount(thisWeekSaleAmount);
            BigDecimal lastWeekSaleAmount = indexDao.selectLastWeekSaleAmount(map2);
            salesAmountStatisticsVo.setThisWeekRateOfChange(getRateOfChangeResult2(thisWeekSaleAmount, lastWeekSaleAmount));
            map.put("salesAmountStatisticsVo", salesAmountStatisticsVo);
            //会员统计
            MemberStatisticsVo memberStatisticsVo = new MemberStatisticsVo();
            BigDecimal thisMonthMemberNum = indexDao.selectThisMonthMemberNum(thisMonthMap);
            BigDecimal LastMonthMemberNum = indexDao.selectLastMonthMemberNum(lastMonthMap);
            memberStatisticsVo.setThisMonthMemberNum(thisMonthMemberNum);
            memberStatisticsVo.setThisMonthRateOfChange(getRateOfChangeResult2(thisMonthMemberNum, LastMonthMemberNum));
            BigDecimal thisWeekMemberNum = indexDao.selectThisWeekMemberNum(map1);
            BigDecimal LastWeekMemberNum = indexDao.selectLastWeekMemberNum(map2);
            memberStatisticsVo.setThisWeekMemberNum(thisWeekMemberNum);
            memberStatisticsVo.setThisWeekRateOfChange(getRateOfChangeResult2(thisWeekMemberNum, LastWeekMemberNum));
            map.put("memberStatisticsVo", memberStatisticsVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /****
     * 计算 同比改变率  （本期数－同期数）÷同期数×100%
     * @param thisData  本期数
     * @param LastData  同期数
     * @return
     */
    private Map getRateOfChangeResult2(BigDecimal thisData, BigDecimal LastData) {
        Map map = new HashMap();
        String result = "";
        int plus = 0;
        BigDecimal subResult = thisData.subtract(LastData);
        if (0 == BigDecimal.ZERO.compareTo(subResult)) {
            result = "0.00%";
        } else if (0 == BigDecimal.ZERO.compareTo(thisData)) {
            result = LastData.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%";
            plus = -1;
        } else if (0 == BigDecimal.ZERO.compareTo(LastData)) {
            result = thisData.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString() + "%";
            plus = 1;
        } else {
            BigDecimal b = subResult.divide(LastData, 4, BigDecimal.ROUND_HALF_UP);
            plus = b.compareTo(BigDecimal.ZERO);
            b = b.abs();
            //下面将结果转化成百分比
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(2
            );
            result = percent.format(Double.parseDouble(b.toString()));
        }
        map.put("result", result);
        map.put("plus", plus);
        return map;
    }

    /**
     * 商品销售排行TOP
     *
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    @Override
    public ResponseVo<List<CommoditySaleRankingVo>> selectCommoditySaleRanking(String condition, String start, String end, Boolean isSearch) {
     /*   if (SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {*/
        if (isSearch) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            start = format.format(new Date(Long.valueOf(start)));
            end = format.format(new Date(Long.valueOf(end)));
            condition = " DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";
        } else {
            Map<String, String> map = new HashMap<>();
            if ("lastSevenDay".equals(condition)) {
               /* map = DateUtil.getFrontDay(-6);
                start = map.get("resTime");
                end = map.get("nowTime");
                condition = " DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";*/
                condition = "date_sub(now(), INTERVAL 7 DAY) <=  date(OOR.TORDER_TIME)";
            } else if ("nearlyThirtyDays".equals(condition)) {
                /*map = DateUtil.getFrontDay(-29);
                start = map.get("resTime");
                end = map.get("nowTime");
                condition = " DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";*/
                condition = "date_sub(now(), INTERVAL 30 DAY) <=  date(OOR.TORDER_TIME)";
            } else if ("today".equals(condition)) {
                condition = "DATEDIFF(OOR.TORDER_TIME,NOW())=0";
            } else if ("yesterday".equals(condition)) {
                condition = "DATEDIFF(OOR.TORDER_TIME,NOW())=-1";
            }
        }
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap<>();
        map.put("condition", condition);
        map.put("merchantId", merchantId);
        ResponseVo<List<CommoditySaleRankingVo>> responseVo = new ResponseVo<List<CommoditySaleRankingVo>>();
        List<CommoditySaleRankingVo> commoditySaleRankingVos = indexDao.selectCommoditySaleRanking(map);
        Integer topNum = 10;
        String topNumStr = SysParaUtil.getValue("mgr_index_top_num");//后台首页TOP个数
        if (StringUtil.isNotBlank(topNumStr)) {
            try {
                topNum = Integer.parseInt(topNumStr);
            } catch (Exception e) {
                topNum = 10;
            }
        }

        if (null == commoditySaleRankingVos || commoditySaleRankingVos.size() < topNum) {
            if (null == commoditySaleRankingVos) {
                commoditySaleRankingVos = new ArrayList<CommoditySaleRankingVo>();
            }
            CommoditySaleRankingVo tempVo = null;
            for (int i = (commoditySaleRankingVos.size() + 1); i <= topNum; i++) {
                tempVo = new CommoditySaleRankingVo();
                tempVo.setCommodityAmount(null);
                tempVo.setCommodityCount("--");
                tempVo.setCommodityName("--");
                tempVo.setCommodityId("--");
                commoditySaleRankingVos.add(tempVo);
            }
        }
        return responseVo.getSuccessResponse(commoditySaleRankingVos);
       /* }
        return null;*/
    }

    /**
     * 设备销售排行TOP
     *
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    @Override
    public ResponseVo<List<DeviceSaleRankingVo>> selectDeviceSaleRanking(String condition, String start, String end, Boolean isSearch) {
  /*      if (SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {*/

        String deviceOperCondition = "";
        if (isSearch) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            start = format.format(new Date(Long.valueOf(start)));
            end = format.format(new Date(Long.valueOf(end)));
            condition = "  DATE_FORMAT(oor.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(oor.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";
            deviceOperCondition = "  DATE_FORMAT(TADD_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(TADD_TIME, '%Y-%m-%d') <='" + end + "'";
        } else {
            Map<String, String> map = new HashMap<>();
            if ("lastSevenDay".equals(condition)) {
            /*    map = DateUtil.getFrontDay(-6);
                start = map.get("resTime");
                end = map.get("nowTime");*/
                /*condition = " DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";
                deviceOperCondition = " DATE_FORMAT(TADD_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(TADD_TIME, '%Y-%m-%d') <='" + end + "'";*/
                condition = "date_sub(now(), INTERVAL 7 DAY) <=  date(OOR.TORDER_TIME)";
                deviceOperCondition = "date_sub(now(), INTERVAL 7 DAY) <=  date(TADD_TIME)";
            } else if ("nearlyThirtyDays".equals(condition)) {
               /* map = DateUtil.getFrontDay(-29);
                start = map.get("resTime");
                end = map.get("nowTime");*/
                /*condition = " DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(OOR.TORDER_TIME, '%Y-%m-%d') <='" + end + "'";
                deviceOperCondition = " DATE_FORMAT(TADD_TIME, '%Y-%m-%d')  >='" + start + "' AND  DATE_FORMAT(TADD_TIME, '%Y-%m-%d') <='" + end + "'";*/
                condition = "date_sub(now(), INTERVAL 30 DAY) <=  date(OOR.TORDER_TIME)";
                deviceOperCondition = "date_sub(now(), INTERVAL 30 DAY) <=  date(TADD_TIME)";
            } else if ("today".equals(condition)) {
                condition = "DATEDIFF(OOR.TORDER_TIME,NOW())=0";
                deviceOperCondition = "DATEDIFF(TADD_TIME,NOW())=0";
            } else if ("yesterday".equals(condition)) {
                condition = "DATEDIFF(OOR.TORDER_TIME,NOW())=-1";
                deviceOperCondition = "DATEDIFF(TADD_TIME,NOW())=-1";
            }
        }
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap<>();
        map.put("condition", condition);
        map.put("merchantId", merchantId);
        map.put("deviceOperCondition", deviceOperCondition);
        ResponseVo<List<DeviceSaleRankingVo>> responseVo = new ResponseVo<List<DeviceSaleRankingVo>>();
        List<DeviceSaleRankingVo> deviceSaleRankingVoList = indexDao.selectDeviceSaleRanking(map);

        Integer topNum = 10;
        String topNumStr = SysParaUtil.getValue("mgr_index_top_num");//后台首页TOP个数
        if (StringUtil.isNotBlank(topNumStr)) {
            try {
                topNum = Integer.parseInt(topNumStr);
            } catch (Exception e) {
                topNum = 10;
            }
        }

        if (null == deviceSaleRankingVoList || deviceSaleRankingVoList.size() < topNum) {
            if (null == deviceSaleRankingVoList) {
                deviceSaleRankingVoList = new ArrayList<DeviceSaleRankingVo>();
            }
            DeviceSaleRankingVo tempVo = null;
            for (int i = (deviceSaleRankingVoList.size() + 1); i <= topNum; i++) {
                tempVo = new DeviceSaleRankingVo();
                tempVo.setDeviceSaleAmount(null);
                tempVo.setDeviceId("--");
                tempVo.setDeviceSaleNum("--");
                tempVo.setDeviceName("--");
                tempVo.setVisitorNum("--");
                tempVo.setId("--");
                deviceSaleRankingVoList.add(tempVo);
            }
        }
        return responseVo.getSuccessResponse(deviceSaleRankingVoList);
       /* }
        return null;*/
    }

    /**
     * 订单统计
     *
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    @Override
    public List<EchartsVo> selectOrderStatistics(String condition, String start, String end, Boolean isSearch) {
    /*    if (SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {*/
        String memberStatisticsStart = "";
        String memberStatisticsEnd = "";
        Map<String, String> map = new HashMap<>();
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if (isSearch) {
            memberStatisticsStart = format.format(new Date(Long.valueOf(start)));
            memberStatisticsEnd = format.format(new Date(Long.valueOf(end)));
            condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            if (memberStatisticsStart.equals(memberStatisticsEnd)) {
                map.put("merchantId", merchantId);
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayOrderStatistics(map);
                return echartsVos;
            }
        } else {
            if ("lastSevenDay".equals(condition)) {
                map = DateUtil.getFrontDay(-6);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                 /* condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";*/
                condition = "date_sub(now(), INTERVAL 7 DAY) <=  date(TORDER_TIME)";
            } else if ("nearlyThirtyDays".equals(condition)) {
                map = DateUtil.getFrontDay(-29);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                /* condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";*/
                condition = "date_sub(now(), INTERVAL 30 DAY) <=  date(TORDER_TIME)";
            } else if ("thisWeek".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfWeek());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfWeek());
                condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            } else if ("thisMonth".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfMonth());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfMonth());
                condition = " date_format(TORDER_TIME, '%Y%m') = date_format(curdate() , '%Y%m')";
            } else if ("today".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TORDER_TIME,NOW())=0";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayOrderStatistics(map);
                return echartsVos;
            } else if ("yesterday".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TORDER_TIME,NOW())=-1";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayOrderStatistics(map);
                return echartsVos;
            }

        }
        map.put("condition", condition);
        map.put("merchantId", merchantId);
        map.put("start", memberStatisticsStart);
        map.put("end", memberStatisticsEnd);
        List<EchartsVo> echartsVos = indexDao.selectOrderStatistics(map);
        for (EchartsVo echartsVo : echartsVos) {
            String week = DateUtil.getCategories(echartsVo.getCategories());
            echartsVo.setName(week);
            echartsVo.setGroup(MessageSourceUtils.getMessageByKey("index.order.statistics",null));
        }
        return echartsVos;
        /*}
        return null;*/
    }

    /**
     * 销售统计
     *
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    @Override
    public List<EchartsVo> selectSalesAmountStatistics(String condition, String start, String end, Boolean isSearch) {
       /* if (SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {*/

        String memberStatisticsStart = "";
        String memberStatisticsEnd = "";
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        if (isSearch) {
            memberStatisticsStart = format.format(new Date(Long.valueOf(start)));
            memberStatisticsEnd = format.format(new Date(Long.valueOf(end)));
            condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            if (memberStatisticsStart.equals(memberStatisticsEnd)) {
                map.put("merchantId", merchantId);
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdaySalesStatistics(map);
                return echartsVos;
            }
        } else {
            if ("lastSevenDay".equals(condition)) {
                map = DateUtil.getFrontDay(-6);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                condition = "date_sub(now(), INTERVAL 7 DAY) <=  date(TORDER_TIME)";

               /* condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";*/
            } else if ("nearlyThirtyDays".equals(condition)) {
                map = DateUtil.getFrontDay(-29);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                condition = "date_sub(now(), INTERVAL 30 DAY) <=  date(TORDER_TIME)";
               /* condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";*/
            } else if ("thisWeek".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfWeek());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfWeek());
                condition = " DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TORDER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            } else if ("thisMonth".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfMonth());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfMonth());
                condition = " date_format(TORDER_TIME, '%Y%m') = date_format(curdate() , '%Y%m')";
            } else if ("today".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TORDER_TIME,NOW())=0 ";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdaySalesStatistics(map);
                return echartsVos;
            } else if ("yesterday".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TORDER_TIME,NOW())=-1";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdaySalesStatistics(map);
                return echartsVos;
            }
        }

        map.put("condition", condition);
        map.put("merchantId", merchantId);
        map.put("start", memberStatisticsStart);
        map.put("end", memberStatisticsEnd);
        List<EchartsVo> echartsVos = indexDao.selectSalesAmountStatistics(map);
        for (EchartsVo echartsVo : echartsVos) {
            String week = DateUtil.getCategories(echartsVo.getCategories());
            echartsVo.setName(week);
            echartsVo.setGroup(MessageSourceUtils.getMessageByKey("index.sales.statistics",null));
        }
        return echartsVos;
      /*  }
        return null;*/
    }


    /**
     * 会员统计
     *
     * @param condition 搜索条件
     * @param start     搜索开始时间
     * @param end       搜索结束时间
     */
    @Override
    public List<EchartsVo> selectMemberStatistics(String condition, String start, String end, Boolean isSearch) {
       /* if (SecurityUtils.getSubject().isPermitted("ORDER_MEMBER_NAME_DESENSITIZE")) {
*/
        String merchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Map<String, String> map = new HashMap<>();
        String memberStatisticsStart = "";
        String memberStatisticsEnd = "";
        if (isSearch) {
            memberStatisticsStart = format.format(new Date(Long.valueOf(start)));
            memberStatisticsEnd = format.format(new Date(Long.valueOf(end)));
            condition = " DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            if (memberStatisticsStart.equals(memberStatisticsEnd)) {
                map.put("merchantId", merchantId);
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayMemberStatistics(map);
                return echartsVos;
            }
        } else {
            if ("lastSevenDay".equals(condition)) {
                map = DateUtil.getFrontDay(-6);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                condition = "date_sub(now(), INTERVAL 7 DAY) <=  date(TREGISTER_TIME)";
/*
                condition = " DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
*/
            } else if ("nearlyThirtyDays".equals(condition)) {
                map = DateUtil.getFrontDay(-29);
                memberStatisticsStart = map.get("resTime");
                memberStatisticsEnd = map.get("nowTime");
                condition = "date_sub(now(), INTERVAL 30 DAY) <=  date(TREGISTER_TIME)";

/*
                condition = " DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
*/
            } else if ("thisWeek".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfWeek());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfWeek());
                condition = " DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  >='" + memberStatisticsStart + "' AND DATE_FORMAT(TREGISTER_TIME, '%Y-%m-%d')  <='" + memberStatisticsEnd + "'";
            } else if ("thisMonth".equals(condition)) {
                memberStatisticsStart = format.format(DateUtil.getBeginDayOfMonth());
                memberStatisticsEnd = format.format(DateUtil.getEndDayOfMonth());
                condition = " date_format(TREGISTER_TIME, '%Y%m') = date_format(curdate() , '%Y%m')";
            } else if ("today".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TREGISTER_TIME,NOW())=0 ";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayMemberStatistics(map);
                return echartsVos;
            } else if ("yesterday".equals(condition)) {
                map.put("merchantId", merchantId);
                condition = "DATEDIFF(TREGISTER_TIME,NOW())=-1";
                map.put("condition", condition);
                List<EchartsVo> echartsVos = indexDao.selectSpecialdayMemberStatistics(map);
                return echartsVos;
            }
        }
        map.put("condition", condition);
        map.put("merchantId", merchantId);
        map.put("start", memberStatisticsStart);
        map.put("end", memberStatisticsEnd);
        List<EchartsVo> echartsVos = indexDao.selectMemberStatistics(map);
        for (EchartsVo echartsVo : echartsVos) {
            String week = DateUtil.getCategories(echartsVo.getCategories());
            echartsVo.setName(week);
            echartsVo.setGroup(MessageSourceUtils.getMessageByKey("index.member.statistics",null));
        }
        return echartsVos;
       /* }
        return null;*/
    }
}