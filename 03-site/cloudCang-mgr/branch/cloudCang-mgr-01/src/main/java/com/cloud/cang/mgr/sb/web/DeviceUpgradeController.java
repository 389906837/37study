package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDetailsDomain;
import com.cloud.cang.mgr.sb.domain.DeviceUpgradeDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.mgr.sb.service.DeviceUpgradeService;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeDetailsVo;
import com.cloud.cang.mgr.sb.vo.DeviceUpgradeVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
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
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceUpgradeController
 * @Description 设备升级记录controller
 * @Author zengzexiong
 * @Date 2018年6月22日15:10:08
 */
@Controller
@RequestMapping("/device")
public class DeviceUpgradeController extends GenericController {
    private static final Logger logger = LoggerFactory.getLogger(DeviceUpgradeController.class);

    @Autowired
    private DeviceUpgradeService deviceUpgradeService;

    @Autowired
    private DeviceUpgradeDetailsService deviceUpgradeDetailsService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    /**
     * 设备商品信息列表
     *
     * @return
     */
    @RequestMapping("/upgrade/list")
    public String commodityList() {
        return "sb/upgrade/deviceUpgrade-list";
    }

    /**
     * 设备升级列表数据
     * @param deviceUpgradeVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/upgrade/queryData")
    @ResponseBody
    public PageListVo<List<DeviceUpgradeDomain>> queryData(DeviceUpgradeVo deviceUpgradeVo, ParamPage paramPage) {
        PageListVo<List<DeviceUpgradeDomain>> pageListVo = new PageListVo<List<DeviceUpgradeDomain>>();//返回的页面对象
        Page<DeviceUpgradeDomain> page = new Page<DeviceUpgradeDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == deviceUpgradeVo) {
            deviceUpgradeVo = new DeviceUpgradeVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,50);
        deviceUpgradeVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceUpgradeVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = deviceUpgradeService.selectPageByDomainWhere(page, deviceUpgradeVo);

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
    @RequestMapping("/upgrade/toView")
    public String upgradeToView(String sid,ModelMap map) {
        try {
            //数据库查询该设备商品信息
            if (StringUtils.isNotBlank(sid)) {
                map.put("smainId", sid);
                //操作日志
                String logText = MessageFormat.format("查询设备升级记录明细记录{0}", "");
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                return "sb/upgrade/deviceUpgradeDetails-list";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    @RequestMapping("/upgradeDetails/queryData")
    @ResponseBody
    public PageListVo<List<DeviceUpgradeDetailsDomain>> upgradeDetailsQueryData(DeviceUpgradeDetailsVo deviceUpgradeDetailsVo, ParamPage paramPage) {
        PageListVo<List<DeviceUpgradeDetailsDomain>> pageListVo = new PageListVo<List<DeviceUpgradeDetailsDomain>>();//返回的页面对象
        Page<DeviceUpgradeDetailsDomain> page = new Page<DeviceUpgradeDetailsDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == deviceUpgradeDetailsVo) {
            deviceUpgradeDetailsVo = new DeviceUpgradeDetailsVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);    // 10=商户查询 20 活动列表 30 系统用户 40 系统角色 50 设备列表 60 商品列表 70 会员列表 110 资讯中心
        deviceUpgradeDetailsVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceUpgradeDetailsVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = deviceUpgradeDetailsService.selectPageByDomainWhere(page, deviceUpgradeDetailsVo);

        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    @RequestMapping("/upgradeDetails/toView")
    public String upgradeDetailsToView(String sid,ModelMap map) {
        try {
            //数据库查询该设备商品信息
            if (StringUtils.isNotBlank(sid)) {
                DeviceUpgradeDetailsDomain deviceUpgradeDetailsDomain = deviceUpgradeDetailsService.selectDomainById(sid);
                if (null != deviceUpgradeDetailsDomain) {
                    map.put("deviceUpgradeDetailsDomain", deviceUpgradeDetailsDomain);
                }
                //操作日志
                String logText = MessageFormat.format("查看设备编号：{0},升级记录详细信息明细", deviceUpgradeDetailsDomain.getSdeviceCode());
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                return "sb/upgrade/deviceUpgradeDetails-view";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }



}
