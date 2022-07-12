package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sys.service.IndexService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.mgr.sys.vo.CommoditySaleRankingVo;
import com.cloud.cang.mgr.sys.vo.DeviceSaleRankingVo;
import com.cloud.cang.mgr.sys.vo.EchartsVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 后台首页
 *
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/mgr")
public class IndexController {


    @Autowired
    private IndexService indexService;

    @RequestMapping("/index")
    public String login(ModelMap modelMap, HttpServletRequest request) {

        //获取登录用户信息
        AuthorizationVO authorizationVO = SessionUserUtils.getSessionAttributeForUserDtl();
        modelMap.put("operator", authorizationVO);

        Map<String, Object> mapData = indexService.getMainData(request);
        modelMap.put("index", mapData);
        return "/sys/index";
    }

    /****
     * 商品销售排行Top10
     * @param condition 搜索条件
     * @param
     * @return
     */
    @RequestMapping("/commoditySaleRanking")
    @ResponseBody
    public ResponseVo<List<CommoditySaleRankingVo>> commoditySaleRanking(String condition, String start, String end, Boolean isSearch) {
        return indexService.selectCommoditySaleRanking(condition, start, end,isSearch);
    }

    /****
     * 设备商品销售排行Top10
     * @param condition 搜索条件
     * @param
     * @return
     */
    @RequestMapping("/deviceSaleRanking")
    @ResponseBody
    public ResponseVo<List<DeviceSaleRankingVo>> deviceSaleRanking(String condition, String start, String end, Boolean isSearch) {
        return indexService.selectDeviceSaleRanking(condition, start, end, isSearch);
    }


    /****
     * 订单统计
     * @param condition 搜索条件
     * @param
     * @return
     */
    @RequestMapping("/orderStatistics")
    @ResponseBody
    public List<EchartsVo> selectOrderStatistics(String condition, String start, String end, Boolean isSearch) {
        return indexService.selectOrderStatistics(condition, start, end, isSearch);
    }

    /****
     * 销售统计
     * @param condition 搜索条件
     * @param
     * @return
     */
    @RequestMapping("/salesAmountStatistics")
    @ResponseBody
    public List<EchartsVo> selectSalesAmountStatistics(String condition, String start, String end, Boolean isSearch) {
        return indexService.selectSalesAmountStatistics(condition, start, end, isSearch);
    }

    /****
     * 会员统计
     * @param condition 搜索条件
     * @param
     * @return
     */
    @RequestMapping("/memberStatistics")
    @ResponseBody
    public List<EchartsVo> selectMemberStatistics(String condition, String start, String end, Boolean isSearch) {
        return indexService.selectMemberStatistics(condition, start, end, isSearch);
    }


}
