package com.cloud.cang.mgr.sb.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sb.domain.BackstageOperDomain;
import com.cloud.cang.mgr.sb.service.BackstageOperService;
import com.cloud.cang.mgr.sb.vo.BackstageOperVo;
import com.cloud.cang.model.sb.BackstageOper;
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
 * @ClassName BackstageOperController
 * @Description 设备后台操作记录
 * @Author zengzexiong
 * @Date 2018年12月6日11:03:00
 */
@Controller
@RequestMapping("/device")
public class BackstageOperController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(BackstageOperController.class);

    @Autowired
    BackstageOperService backstageOperService;


    /**
     * 设备后台操作记录列表
     *
     * @return
     */
    @RequestMapping("/backstageOper/list")
    public String list() {
        return "sb/backstageOper/backstageOper-list";
    }

    /**
     * 后台设备操作记录分页查询
     * @param paramPage
     * @param backstageOperVo
     * @return
     */
    @RequestMapping("/backstageOper/queryData")
    @ResponseBody
    public PageListVo<List<BackstageOperDomain>> queryMerchantSelectData(ParamPage paramPage, BackstageOperVo backstageOperVo) {
        logger.debug("<===分页查询后台设备操作记录开始===>");
        PageListVo<List<BackstageOperDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<BackstageOperDomain>>();
        Page<BackstageOperDomain> page = new Page<BackstageOperDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == backstageOperVo) {
            backstageOperVo = new BackstageOperVo();
        }
        backstageOperVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            backstageOperVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        //分页查询
        page = backstageOperService.selectPageByDomainWhere(page, backstageOperVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        logger.debug("<===分页查询后台设备操作记录结束===>",pageListVo);
        return pageListVo;
    }

    /**
     * 查看设备后台操作详情页面
     *
     * @param sid 操作ID
     * @param map
     * @return
     */
    @RequestMapping("/backstageOper/view")
    public String commodityInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该设备信息
                //
                BackstageOper backstageOper = backstageOperService.selectByPrimaryKey(sid);
                if (null == backstageOper) {
                    return ExceptionUtil.to500();
                }
                map.put("backstageOper", backstageOper);
                String logText4 = MessageFormat.format("查看设备后台操作详情信息", "");
                LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                logger.debug("<=== 查看设备后台操作详情页面结束===>", backstageOper.getId());
                return "sb/backstageOper/backstageOper-view";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }
}
