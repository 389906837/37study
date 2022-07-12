package com.cloud.cang.mgr.tb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.tb.domain.ThirdDeviceSkuDomain;
import com.cloud.cang.mgr.tb.service.ThirdDeviceSkuService;
import com.cloud.cang.mgr.tb.vo.ThirdDeviceSkuVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceController
 * @Description 设备管理controller
 * @Author zengzexiong
 * @Date 2018年1月24日15:10:20
 */
@Controller
@RequestMapping("/tbThirdDeviceSku")
public class ThirdDeviceSkuController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(ThirdDeviceSkuController.class);

    @Autowired
    ThirdDeviceSkuService thirdDeviceSkuService;    // 第三方商户设备SKU库

    @Autowired
    OperatorService operatorService;    // 查询条件


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
        return "tb/thirdDeviceSku/thirdDeviceSku-list";
    }

    /**
     * 第三方商户设备SKU库设备查询
     *
     * @param thirdDeviceSkuVo 初始化页面对象
     * @param paramPage        初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<ThirdDeviceSkuDomain>> queryData(ThirdDeviceSkuVo thirdDeviceSkuVo, ParamPage paramPage) {
        logger.debug("开始分页查询第三方商户设备SKU库");
        PageListVo<List<ThirdDeviceSkuDomain>> pageListVo;
        pageListVo = new PageListVo<List<ThirdDeviceSkuDomain>>();
        Page<ThirdDeviceSkuDomain> page = new Page<>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == thirdDeviceSkuVo) {
            thirdDeviceSkuVo = new ThirdDeviceSkuVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        thirdDeviceSkuVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            if (paramPage.getSidx().equals("merchantName")) {
                thirdDeviceSkuVo.setOrderStr(" D.sname " + paramPage.getSord() + ",");
            } else {
                thirdDeviceSkuVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
            }
        }

        //分页查询
        page = thirdDeviceSkuService.selectPageByDomainWhere(page, thirdDeviceSkuVo);
        logger.debug("第三方商户设备SKU库分页查询完成");


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
     * 第三方订单数据列表
     *
     * @return
     */
    @RequestMapping("/view")
    public String viewList(String sdeviceId, ModelMap map) {
        if (StringUtil.isNotBlank(sdeviceId)) {
            map.put("sdeviceId", sdeviceId);
            logger.info("设备ID:{}", sdeviceId);
        }
        return "tb/thirdDeviceSku/thirdDeviceSku-viewList";
    }

    /**
     * 第三方商户设备SKU库设备查询
     *
     * @param thirdDeviceSkuVo 初始化页面对象
     * @param paramPage        初始化分页对象
     * @return
     */
    @RequestMapping("/queryViewData")
    @ResponseBody
    public PageListVo<List<ThirdDeviceSku>> queryViewData(ThirdDeviceSku thirdDeviceSkuVo,  ParamPage paramPage) {
        logger.debug("开始分页查询第三方商户设备SKU库");
        PageListVo<List<ThirdDeviceSku>> pageListVo;
        pageListVo = new PageListVo<List<ThirdDeviceSku>>();
        Page<ThirdDeviceSku> page = new Page<>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == thirdDeviceSkuVo) {
            thirdDeviceSkuVo = new ThirdDeviceSkuVo();
        }
        logger.info("设备ID：{}", thirdDeviceSkuVo.getSdeviceId());
        //分页查询
        page = thirdDeviceSkuService.selectViewPageByDomainWhere(page, thirdDeviceSkuVo);
        logger.debug("第三方商户设备SKU库分页查询完成");

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



}
