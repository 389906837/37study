package com.cloud.cang.mgr.sm.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sm.domain.DeviceStockDomain;
import com.cloud.cang.mgr.sm.service.DeviceStockService;
import com.cloud.cang.mgr.sm.service.StockDetailService;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * 单机库存列表
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-24 20:00:00
 */
@Controller
@RequestMapping("/devicestock")
public class DeviceStockController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(DeviceStockController.class);

    // 注入serv
    @Autowired
    private DeviceStockService deviceStockService;

    @Autowired
    private OperatorService operatorService; // 操作员信息表

    @Autowired
    MerchantInfoService merchantInfoService; // 商户信息表

    @Autowired
    DeviceInfoService deviceInfoService; // 设备基础信息表

    @Autowired
    StockDetailService stockDetailService; // 设备实时库存明细表

    @Autowired
    CommodityInfoService commodityInfoService; // 商品信息表

    /**
     * 总库存列表查询
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sm/devicestock-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<DeviceStockDomain>> queryDeviceStock(ParamPage paramPage, DeviceStockVo deviceStockVo) {
        PageListVo<List<DeviceStockDomain>> pageListVo = new PageListVo<List<DeviceStockDomain>>();
        Page<DeviceStockDomain> page = new Page<DeviceStockDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceStockVo) {
            deviceStockVo = new DeviceStockVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 140);
        deviceStockVo.setQueryCondition(queryCondition);

        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceStockVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = deviceStockService.selectDeviceStock(page, deviceStockVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 总库存列表明细
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryDevicestockInfo")
    public String queryDevicestockInfo(ModelMap modelMap, String sid) {
        try {
            String provinceName = "";
            String cityName = "";
            String areaName = "";
            String adress = "";
            String pcaAdress = "";
            if (StringUtils.isNotBlank(sid)) {
                // 查询设备库存记录表
                DeviceStock deviceStock = deviceStockService.selectByPrimaryKey(sid);
                List<StockDetail> allList = null;
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceStock.getSdeviceId());
                provinceName = deviceInfo.getSprovinceName();
                cityName = deviceInfo.getScityName();
                areaName = deviceInfo.getSareaName();
                adress = deviceInfo.getSputAddress();
                pcaAdress = provinceName + cityName + areaName + adress;
                deviceInfo.setSputAddress(pcaAdress);
                allList = stockDetailService.selectInfoId(sid);
                modelMap.put("allList", allList);
                return "sm/devicestock-infoList";
            }
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 单机库存列表查询
     *
     * @return
     */
    @RequestMapping("/singlist")
    public String singList() {
        return "sm/devicestock-singlist";
    }

    @RequestMapping("/queryOneData")
    public @ResponseBody
    PageListVo<List<DeviceStockDomain>> queryOneDeviceStock(ParamPage paramPage, DeviceStockVo deviceStockVo) {
        PageListVo<List<DeviceStockDomain>> pageListVo = new PageListVo<List<DeviceStockDomain>>();
        Page<DeviceStockDomain> page = new Page<DeviceStockDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceStockVo) {
            deviceStockVo = new DeviceStockVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 140);
        deviceStockVo.setQueryCondition(queryCondition);

        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceStockVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = deviceStockService.selectOneDeviceStock(page, deviceStockVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 单机库存列表明细
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryOneDevicestockInfo")
    public String queryOneDevicestockInfo(ModelMap modelMap, String sid) {
        try {
            String provinceName = "";
            String cityName = "";
            String areaName = "";
            String adress = "";
            String pcaAdress = "";
            // 查询单机设备库存记录表
            DeviceStock deviceStock = deviceStockService.selectByPrimaryKey(sid);
            if (deviceStock != null && StringUtil.isNotBlank(deviceStock.getId())) {
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceStock.getSdeviceId());
                provinceName = deviceInfo.getSprovinceName();
                cityName = deviceInfo.getScityName();
                areaName = deviceInfo.getSareaName();
                adress = deviceInfo.getSputAddress();
                pcaAdress = provinceName + cityName + areaName + adress;
                deviceInfo.setSputAddress(pcaAdress);
                List<StockDetail> allList = null;
                allList = stockDetailService.selectInfoId(deviceStock.getId());
                modelMap.put("allList", allList);
                modelMap.put("deviceInfo", deviceInfo);
                return "sm/stockdetail-infoList";
            }
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 跳转修改商品单价页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/comFsalePricelist")
    public String list(ModelMap modelMap, String sid) {
        try {
            // 查询库存表
            DeviceStock deviceStock = deviceStockService.selectByPrimaryKey(sid);
            if (null != deviceStock) {
                // 查询库存明细表
                StockDetail stockDetail = stockDetailService.selectByPrimaryKey(deviceStock.getId());
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceStock.getSdeviceId());
                CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey(deviceStock.getScommodityId());
                String scommodityFullName = "";
                if (null != commodityInfo) {
                    scommodityFullName = commodityInfo.getSbrandName() + commodityInfo.getSname() + commodityInfo.getStaste() + commodityInfo.getIspecWeight() + commodityInfo.getSspecUnit() + "/" + commodityInfo.getSpackageUnit();
                }
                modelMap.put("deviceStock", deviceStock);
                modelMap.put("stockDetail", stockDetail);
                modelMap.put("deviceInfo", deviceInfo);
                modelMap.put("scommodityFullName", scommodityFullName);
            }
            return "sm/deviceStock-comFsalePriceList";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 手动修改库存的商品单价
     *
     * @param deviceStock
     * @return
     */
    @RequestMapping("/updateComFsalePrice")
    public @ResponseBody
    ResponseVo<DeviceStock> updateComFsalePrice(DeviceStock deviceStock) {
        try {
            if (StringUtil.isBlank(deviceStock.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品不存在，请重新操作！");
            }
            if (null == deviceStock.getFsalePrice()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品单价不能为空！");
            }
            if (null == deviceStock.getIstock()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品库存数量不能为空！");
            }
            // 20181129 修改库存逻辑--zzx
            return deviceStockService.updatePriceAndStock(deviceStock);
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(se.getMessage());
        } catch (Exception ex) {
            log.error("手动修改库存的商品单价异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("修改商品单价与库存数量失败");
        }
    }

    /**
     * 同步商品单价到库存表
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/syncComFsalePrice")
    public @ResponseBody
    ResponseVo<String> syncComFsalePrice(String[] checkboxId) {
        try {
            commodityInfoService.syncComFsalePrice(checkboxId);
            ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
            responseVo.setMsg("同步成功");
            // 操作日志
            String logText = MessageFormat.format("商品单价，商品ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return responseVo;
        } catch (Exception e) {
            log.error("同步商品单价到库存表异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("同步商品单价失败！");
        }
    }


    /**
     * 手动下架商品库存
     *
     * @param sid
     * @return
     */
    @RequestMapping("/undercarriage")
    public String undercarriage(String sid, ModelMap map) {
        try {
            DeviceStock deviceStock = deviceStockService.selectByPrimaryKey(sid);
            map.put("deviceStock", deviceStock);
            return "sm/deviceStock-undercarriage";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 更新下架商品库存
     *
     * @param deviceStock
     * @param unType      商品库存下架类型
     * @param unNum       商品库存下架数量
     * @return
     */
    @RequestMapping("/saveUndercarriage")
    @ResponseBody
    public ResponseVo saveUndercarriage(DeviceStock deviceStock, Integer unType, Integer unNum) {
        try {
            if (null == unType) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择商品下架类型！");
            }
            //判断是否为整数
           /* Pattern pattern = Pattern.compile("^//d+$");
            if(!pattern.matcher(unNum.toString()).matches()){
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请输入正确商品下架数量！");
            }*/
            if ((20 == unType && null == unNum) || (20 == unType && unNum < 0)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请输入正确商品下架数量！");
            }
            return deviceStockService.saveUndercarriage(deviceStock, unType, unNum);
        } catch (Exception e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商品手动下架失败！");
        }
    }
}
