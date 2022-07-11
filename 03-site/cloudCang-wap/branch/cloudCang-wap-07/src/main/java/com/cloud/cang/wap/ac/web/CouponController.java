package com.cloud.cang.wap.ac.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.wap.ac.service.CouponUserService;
import com.cloud.cang.wap.ac.vo.CommodityVo;
import com.cloud.cang.wap.ac.vo.CouponDomain;
import com.cloud.cang.wap.ac.vo.DeviceVo;
import com.cloud.cang.wap.common.WapConstants;
import com.cloud.cang.wap.common.utils.ParamPage;
import com.cloud.cang.wap.sb.service.DeviceInfoService;
import com.cloud.cang.wap.sp.service.CommodityInfoService;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @version 1.0
 * @Description: 我的券包
 * @Author: yanlingfeng
 * @Date: 2018/5/22 19:28
 */
@Controller
@RequestMapping("/personal")
public class CouponController {
    @Autowired
    private CouponUserService couponUserService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private DeviceInfoService deviceInfoService;

    /***
     * 我的券包列表
     * @param itype 订单类型 10 所有卡券  20 有效卡券  30 失效卡券
     * @return
     */
    @RequestMapping("/couponList")
    public String orderList(Integer itype, ModelMap modelMap) {
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        if (null == itype) {
            itype = 10;
        }
        modelMap.put("pageSize", WapConstants.PAGE_SIZE);
        modelMap.put("itype", itype);
        //查询订单数量
        Map<String, Object> mapValue = couponUserService.statisticalCouponNumByMemberId(authVo.getId());
        modelMap.put("allCount", (Long) mapValue.get("ALLCOUNT"));//全部卡券
        modelMap.put("payCount", (Long) mapValue.get("EFFECTIVECOUNT"));//有效卡券
        modelMap.put("failureCount", (Long) mapValue.get("INVALIDCOUNT"));//INVALIDCOUNT 失效卡券

        return "coupon/coupon_list_new";
    }

    /**
     * 我的卡券列表
     *
     * @param itype 订单类型 10 全部 20 有效 30 失效
     * @return
     * @author yanlingfewng
     */
    @RequestMapping("/myCouponList")
    @ResponseBody
    ResponseVo<List<CouponDomain>> myCouponList(ParamPage paramPage, Integer itype) {
        if (itype == null) {
            itype = 10;
        }
        if (itype.intValue() == 10 || itype.intValue() == 20 || itype.intValue() == 30) {
        } else {
            ResponseVo<List<CouponDomain>> responseVo = new ResponseVo<List<CouponDomain>>();
            responseVo.setSuccess(false);
            return responseVo;
        }
        Page<CouponDomain> page = new Page<CouponDomain>();
        page.setPageNum(paramPage.pageNo() > 0 ? paramPage.pageNo() : 1);
        page.setPageSize(paramPage.getLimit() > 0 ? paramPage.getLimit() : WapConstants.PAGE_SIZE);
        Map<String, Object> map = new HashMap<String, Object>();
        AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
        map.put("memberId", authVo.getId());

        map.put("itype", itype);
        page = couponUserService.selectCouponListByPage(page, map);
        return ResponseVo.getSuccessResponse(page.getTotal(), page.getResult());
    }

    /**
     * 可使用商品列表
     *
     * @param couponId 优惠券Id
     * @return
     * @author yanlingfewng
     */
    @RequestMapping("/useGoods")
    @ResponseBody
    ResponseVo<List<CommodityVo>> useGoods(String couponId) {

        CouponUser couponUser = couponUserService.selectByPrimaryKey(couponId);
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotBlank(couponUser.getSuseLimitCategory())) {
            List<String> list = Arrays.asList(couponUser.getSuseLimitCategory().split(","));
            String categoryCondition = "AND SSMALL_CATEGORY_ID IN(";
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    categoryCondition += "\'" + list.get(i) + "\',";
                } else {
                    categoryCondition += "\'" + list.get(i) + "\')";
                }
            }
            map.put("categoryCondition", categoryCondition);
        }

        if (StringUtils.isNotBlank(couponUser.getSuseLimitBrand())) {
            List<String> list = Arrays.asList(couponUser.getSuseLimitBrand().split(","));
            String barandCondition = "AND SBRAND_ID IN(";
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    barandCondition += "\'" + list.get(i) + "\',";
                } else {
                    barandCondition += "\'" + list.get(i) + "\')";
                }
            }
            map.put("barandCondition", barandCondition);
        }
        if (StringUtils.isNotBlank(couponUser.getSuseLimitCommodity())) {
            List<String> list = Arrays.asList(couponUser.getSuseLimitCommodity().split(","));
            String commodityCondition = "AND SCODE IN(";
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    commodityCondition += "\'" + list.get(i) + "\',";
                } else {
                    commodityCondition += "\'" + list.get(i) + "\')";
                }
            }
            map.put("commodityCondition", commodityCondition);
        }
        List<CommodityVo> commodityVoList = commodityInfoService.selectByCondition(map);
        return ResponseVo.getSuccessResponse(commodityVoList);
    }

    /**
     * 可使用设备列表
     *
     * @param couponId 优惠券Id
     * @return
     * @author yanlingfewng
     */
    @RequestMapping("/useDevice")
    @ResponseBody
    ResponseVo<List<DeviceVo>> useDevice(String couponId) {
        CouponUser couponUser = couponUserService.selectByPrimaryKey(couponId);
        List<DeviceVo> deviceVoList = new ArrayList<>();
        if (StringUtils.isNotBlank(couponUser.getSuseLimitDevice())) {
            List<String> deviceCodes = Arrays.asList(couponUser.getSuseLimitDevice().split(","));
            String queryData = "AND SCODE IN(";
            for (int i = 0; i < deviceCodes.size(); i++) {
                if (i != deviceCodes.size() - 1) {
                    queryData += "\'" + deviceCodes.get(i) + "\',";
                } else {
                    queryData += "\'" + deviceCodes.get(i) + "\')";
                }
            }
            deviceVoList = deviceInfoService.selectUseDevice(queryData);
        }
        return ResponseVo.getSuccessResponse(deviceVoList);
    }
}
