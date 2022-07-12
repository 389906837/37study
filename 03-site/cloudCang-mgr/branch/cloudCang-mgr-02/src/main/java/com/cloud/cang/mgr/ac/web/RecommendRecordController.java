package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.ac.domain.RecommendRecordDomain;
import com.cloud.cang.mgr.ac.service.ActivityConfService;
import com.cloud.cang.mgr.ac.service.IntegralRuleService;
import com.cloud.cang.mgr.ac.service.RecommendRecordService;
import com.cloud.cang.mgr.ac.vo.RecommendRecordVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.IntegralRule;
import com.cloud.cang.model.ac.RecommendRecord;
import com.cloud.cang.model.sys.Operator;
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

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @description 好友推荐记录表
 * @author ChangTanchang
 * @time 2018-03-19 18:45:26
 * @fileName RecommendRecordController.java
 * @version 1.0
 */
@Controller
@RequestMapping("/recommendRecord")
public class RecommendRecordController {

    private static final Logger logger = LoggerFactory.getLogger(RecommendRecordController.class);

    @Autowired
    private RecommendRecordService recommendRecordService;

    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/list")
    public String list() {
        return "ac/recommendRecord-list";
    }


    /**
     * 好友推荐记录表列表数据
     * @param recommendRecordVo
     * @param paramPage
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<RecommendRecordDomain>> queryDataByCondition(ParamPage paramPage, RecommendRecordVo recommendRecordVo) {
        PageListVo<List<RecommendRecordDomain>> pageListVo = new PageListVo<List<RecommendRecordDomain>>();
        Page<RecommendRecordDomain> page = new Page<RecommendRecordDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 20);
        recommendRecordVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            recommendRecordVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = recommendRecordService.selectQueryData(page, recommendRecordVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }
}