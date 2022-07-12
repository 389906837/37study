package com.cloud.cang.mgr.sq.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.domain.RefundAuditDomain;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.om.vo.RefunAuditVo;
import com.cloud.cang.mgr.sq.domain.RefundApplyDomain;
import com.cloud.cang.mgr.sq.service.RefundApplyService;
import com.cloud.cang.mgr.sq.vo.RefundApplyVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yanlingfeng
 * @version 1.0
 * @description 订单退款记录
 * @time 2018-2-28 15:17:19
 * @fileName RefundApplyController.java
 */
@Controller
@RequestMapping("/refundApply")
public class RefundApplyController {
    @Autowired
    private RefundApplyService refundApplyService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/list")
    public String list() {
        return "sq/refundApply-list";
    }

    /**
     * 订单退款申请列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<RefundApplyVo>> queryDataByCondition(RefundApplyDomain refundApplyDomain,
                                                       ParamPage paramPage) {
        PageListVo<List<RefundApplyVo>> pageListVo = new PageListVo<List<RefundApplyVo>>();
        Page<RefundApplyVo> page = new Page<RefundApplyVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            refundApplyDomain.setOrderStr("SRA." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            refundApplyDomain.setQueryCondition(queryCondition);
        }
        page = refundApplyService.selectPageByDomainWhere(page, refundApplyDomain);
       /* List<RefundApply> refundApplyList = page.getResult();
        if (!hasPurCode("REFUNDAPPLY_MEMBER_NAME_DESENSITIZE")) {
            for (RefundApply refundApply : refundApplyList) {
                refundApply.setSmemberName(DesensitizeUtil.mobilePhone(refundApply.getSmemberName()));
            }
        }*/
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /****
     * 查看用户有没有该权限
     * @param purCode 权限码
     * @return
     */
    private boolean hasPurCode(String purCode) {
        List<String> list = purviewService.selectOperatorPurview(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        if (list.contains(purCode)) {
            return true;
        }
        return false;
    }

    /**
     * 退款资金列表页脚总统计
     *
     * @param refundApplyDomain
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo queryTotalData(RefundApplyDomain refundApplyDomain
    ) {
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 90);
        if (StringUtil.isNotBlank(queryCondition)) {
            refundApplyDomain.setQueryCondition(queryCondition);
        }
        BigDecimal factualRefundAmout = refundApplyService.queryTotalData(refundApplyDomain);
        return ResponseVo.getSuccessResponse(factualRefundAmout);
    }
}
