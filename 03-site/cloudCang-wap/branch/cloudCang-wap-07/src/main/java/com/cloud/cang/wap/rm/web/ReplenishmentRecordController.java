package com.cloud.cang.wap.rm.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.BizParaUtil;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.wap.ac.vo.CouponDomain;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.ParamPage;
import com.cloud.cang.wap.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.wap.rm.service.ReplenishmentRecordService;
import com.cloud.cang.wap.rm.vo.HistoryReplenishmentVo;
import com.cloud.cang.wap.rm.vo.ReplenishmentCommodityVo;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/personal")
public class ReplenishmentRecordController {
    @Autowired
    private ReplenishmentRecordService replenishmentRecordService;
    @Autowired
    private ReplenishmentCommodityService replenishmentCommodityService;

    /***
     * 我的历史补货单
     * @param
     * @return
     */
    @RequestMapping("/replenishmentList")
    public String orderList(ModelMap modelMap) {
        modelMap.put("pageSize", WapConstants.PAGE_SIZE);
        return "replenishment/replenishment_list_new";
    }

    /**
     * 补货单列表
     * @param
     * @return
     * @author yanlingfewng
     */
    @RequestMapping("/myReplenishmentList")
    @ResponseBody
    ResponseVo<List<HistoryReplenishmentVo>> myReplenishmentList(ParamPage paramPage) {

        Page<HistoryReplenishmentVo> page = new Page<HistoryReplenishmentVo>();
        page.setPageNum(paramPage.pageNo() > 0 ? paramPage.pageNo() : 1);
        page.setPageSize(paramPage.getLimit() > 0 ? paramPage.getLimit() : WapConstants.PAGE_SIZE);
        Map<String, Object> map = new HashMap<String, Object>();
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        String a = BizParaUtil.get("message_balance_warn");
        if (StringUtils.isBlank(a)) {
            a = "0";
        }
        map.put("memberName", authVo.getUserName());
        map.put("merchantCode", authVo.getSmerchantCode());
        map.put("num", Integer.valueOf(a));
        page = replenishmentRecordService.selectHistoryReplenishment(page, map);
        return ResponseVo.getSuccessResponse(page.getTotal(), page.getResult());
    }


    /**
     * 补货单上/下架明细
     * @param
     * @return
     * @author yanlingfewng
     */
    @RequestMapping("/queryDetail")
    @ResponseBody
    ResponseVo<List<ReplenishmentCommodityVo>> queryDetail(ModelMap modelMap, String id, String deviceId, Integer type) {
        //上架商品
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sreplenishmentId", id);
        params.put("itype", type);
        params.put("deviceId", deviceId);
        List<ReplenishmentCommodityVo> addList = replenishmentCommodityService.selectByMap(params);
        if (null != addList) {
            modelMap.put("addList", addList);
        }
        return ResponseVo.getSuccessResponse(addList);
    }

}
