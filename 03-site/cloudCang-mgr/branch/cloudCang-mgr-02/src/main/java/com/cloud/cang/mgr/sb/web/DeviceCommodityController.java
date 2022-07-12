package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceCommodityDomain;
import com.cloud.cang.mgr.sb.service.DeviceCommodityService;
import com.cloud.cang.mgr.sb.vo.DeviceCommodityVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceCommodityController
 * @Description 设备商品管理controller
 * @Author zengzexiong
 * @Date 2018年6月4日09:29:22
 */
@Controller
@RequestMapping("/device")
public class DeviceCommodityController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceCommodityController.class);


    @Autowired
    OperatorService operatorService;


    @Autowired
    DeviceCommodityService deviceCommodityService;//4.设备商品信息


/* ----------4.设备商品信息开始 ----------*/
    /**
     * 设备商品信息列表
     *
     * @return
     */
    @RequestMapping("/commodity/list")
    public String commodityList() {
        return "sb/commodity/commodity-list";
    }

    /**
     * 设备商品信息列表数据
     * @param deviceCommodityVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/commodity/queryData")
    @ResponseBody
    public PageListVo<List<DeviceCommodityDomain>> queryData(DeviceCommodityVo deviceCommodityVo, ParamPage paramPage) {
        PageListVo<List<DeviceCommodityDomain>> pageListVo = new PageListVo<List<DeviceCommodityDomain>>();//返回的页面对象
        Page<DeviceCommodityDomain> page = new Page<DeviceCommodityDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == deviceCommodityVo) {
            deviceCommodityVo = new DeviceCommodityVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,190);
        deviceCommodityVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceCommodityVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = deviceCommodityService.selectPageByDomainWhere(page, deviceCommodityVo);

        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 跟商品ID查询设备商品信息，设备信息，商户信息
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/commodity/toView")
    public String commoditytoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备商品信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceCommodityDomain deviceCommodityDomain = deviceCommodityService.selectDomainByID(sid);
                if (deviceCommodityDomain != null) {
                    map.put("deviceCommodityDomain", deviceCommodityDomain);
                    //操作日志
                    String logText = MessageFormat.format("查询设备商品信息，商品编号{0}", deviceCommodityDomain.getScommodityCode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/commodity/commodity-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 下架设备商品
     * @param checkboxId
     * @return
     */
    @RequestMapping("/commodity/dropoff")
    public @ResponseBody
    ResponseVo<String> dropOffDeviceCommodity(String[] checkboxId) {
        try {
            //数据库查询该设备商品信息
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String merchantId = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
                String user = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
                deviceCommodityService.dropOffCommodity(checkboxId, merchantId, user);
                //操作日志
                String logText = MessageFormat.format("下架设备商品","");
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                return ResponseVo.getSuccessResponse("下架成功");
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("下架设备商品失败");
    }
    /* ----------4.设备商品信息结束 ----------*/

}
