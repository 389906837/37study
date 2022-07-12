package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.MonitorDataConfDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.MonitorDataConfService;
import com.cloud.cang.mgr.sb.vo.MonitorDataConfVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.MonitorDataConf;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
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
 * @ClassName MonitorDataConfController
 * @Description 设备实时监控controller
 * @Author zengzexiong
 * @Date 2018年6月4日09:32:35
 */
@Controller
@RequestMapping("/device")
public class MonitorDataConfController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(MonitorDataConfController.class);

    @Autowired
    OperatorService operatorService;

    @Autowired
    MonitorDataConfService monitorDataConfService;//5.设备监控配置信息

    @Autowired
    DeviceInfoService deviceInfoService;

    /* ----------5.设备监控配置开始 ----------*/

    /**
     * 设备配置信息列表
     *
     * @return
     */
    @RequestMapping("monitorDataConf/list")
    public String monitorDataConfList() {
        return "sb/monitorDataConf/monitorDataConf-list";
    }

    /**
     * 设备配置信息列表数据
     * @param monitorDataConfVo 初始化页面对象
     * @param paramPage 初始化分页对象
     * @return
     */
    @RequestMapping("/monitorDataConf/queryData")
    @ResponseBody
    public PageListVo<List<MonitorDataConfDomain>> queryData(MonitorDataConfVo monitorDataConfVo, ParamPage paramPage) {
        PageListVo<List<MonitorDataConfDomain>> pageListVo = new PageListVo<List<MonitorDataConfDomain>>();//返回的页面对象
        Page<MonitorDataConfDomain> page = new Page<MonitorDataConfDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        if (null == monitorDataConfVo) {
            monitorDataConfVo = new MonitorDataConfVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,50);
        monitorDataConfVo.setQueryCondition(queryCondition);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            monitorDataConfVo.setOrderStr("A." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }

        //分页查询
        page = monitorDataConfService.selectPageByDomainWhere(page, monitorDataConfVo);


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
     * 根据配置表ID查询设备监控配置信息
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/monitorDataConf/toView")
    public String monitorDataConftoView(String sid,ModelMap map) {
        try {
            //数据库查询该设备监控配置信息
            if (StringUtils.isNotBlank(sid)) {
                MonitorDataConfDomain monitorDataConfDomain = monitorDataConfService.selectViewBySid(sid);
                if (monitorDataConfDomain != null) {
                    map.put("monitorDataConfDomain", monitorDataConfDomain);
                    //操作日志
                    String logText = MessageFormat.format("查询设备实时监控信息，设备编号{0}", monitorDataConfDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/monitorDataConf/monitorDataConf-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 设备监控编辑按钮跳转页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/monitorDataConf/toEdit")
    public String monitorDataConftoEdit(String sid, ModelMap modelMap) {
        try {
            //数据库查询该设备信息
            if (StringUtils.isNotBlank(sid)) {
                MonitorDataConfDomain monitorDataConfDomain = monitorDataConfService.selectViewBySid(sid);
                if (monitorDataConfDomain != null) {
                    modelMap.put("monitorDataConfDomain", monitorDataConfDomain);
                    //操作日志
                    String logText = MessageFormat.format("设备监控配置信息编辑按钮，设备编号{0}", monitorDataConfDomain.getScode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText, null);
                    return "sb/monitorDataConf/monitorDataConf-toEdit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑设备分组信息
     * @param monitorDataConf
     * @return
     */
    @RequestMapping("/monitorDataConf/edit")
    public @ResponseBody
    ResponseVo<MonitorDataConf> monitorDataConfEdit(MonitorDataConf monitorDataConf) {
        try {
            String id = monitorDataConf.getId();//ID
            String sbId = monitorDataConf.getSdeviceId();//设备ID
            if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(sbId)) {

                monitorDataConf.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
                monitorDataConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());/* 修改人 */
                /*  修改数据入库 SB_MONITOR_DATA_CONF   */
                monitorDataConfService.updateBySelective(monitorDataConf);
                //操作日志
                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(sbId);
                String logText = MessageFormat.format("编辑设备监控配置信息，设备编号{0}", deviceInfo.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse(monitorDataConf);
            }
        } catch (ServiceException e) {
            logger.error("编辑设备监控配置信息出现ServiceException异常：{}",e);
        } catch (Exception e) {
            logger.error("编辑设备监控配置信息出现Exception异常：{}",e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("编辑设备监控配置service异常");
    }


    /* ----------5.设备监控配置结束 ----------*/
}
