package com.cloud.cang.mgr.ac.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.utils.BigDecimalUtils;
import com.cloud.cang.mgr.ac.domain.CouponUserDomain;
import com.cloud.cang.mgr.ac.service.CouponUserService;
import com.cloud.cang.mgr.ac.vo.CouponUserVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @ClassName: CouponUserController
 * @Description: 用户持有券
 * @Author: ChangTanchang
 * @Date: 2018/3/20 14:33
 */
@Controller
@RequestMapping("/couponUser")
public class CouponUserController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(CouponUserController.class);

    // 注入用户持有券serv
    @Autowired
    private CouponUserService couponUserService;

    // 商品分类
    @Autowired
    private CategoryService categoryService;

    // 商品品牌
    @Autowired
    private BrandInfoService brandInfoService;

    // 商品信息
    @Autowired
    private CommodityInfoService commodityInfoService;

    // 设备信息
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private PurviewService purviewService; // 权限码表

    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/list")
    public String list() {
        return "ac/couponUser-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<CouponUserDomain>> queryDataCouponUser(ParamPage paramPage, CouponUserVo couponUserVo) {
        PageListVo<List<CouponUserDomain>> pageListVo = new PageListVo<List<CouponUserDomain>>();
        Page<CouponUserDomain> page = new Page<CouponUserDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 20);
        couponUserVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            couponUserVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = couponUserService.queryDataCouponUser(page, couponUserVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 查询用户持有券详情
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/listInfo")
    public String queryCouponUserInfo(ModelMap modelMap, String sid) {
        try {
            CouponUser couponUser = couponUserService.selectByPrimaryKey(sid);
            // 获取限制数据
            if (StringUtil.isNotBlank(couponUser.getSuseLimitCategory())) {
                String tempArr[] = couponUser.getSuseLimitCategory().split(",");
                // 商品分类
                Category category = null;
                StringBuffer sb = new StringBuffer();
                for (String categoryId : tempArr) {
                    category = categoryService.selectByPrimaryKey(categoryId);
                    sb.append(category.getSname() + ",");
                }
                modelMap.put("useLimitCategory", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponUser.getSuseLimitBrand())) {
                String tempArr[] = couponUser.getSuseLimitBrand().split(",");
                // 商品品牌
                BrandInfo brandInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String brandId : tempArr) {
                    brandInfo = brandInfoService.selectByPrimaryKey(brandId);
                    sb.append(brandInfo.getSname() + ",");
                }
                modelMap.put("useLimitBrand", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponUser.getSuseLimitCommodity())) {
                String tempArr[] = couponUser.getSuseLimitCommodity().split(",");
                // 商品信息
                CommodityInfo commodityInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String commodityCode : tempArr) {
                    commodityInfo = commodityInfoService.selectByCode(commodityCode);
                    sb.append(commodityInfo.getId() + ",");
                }
                modelMap.put("useCommodityIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponUser.getSuseLimitDevice())) {
                String tempArr[] = couponUser.getSuseLimitDevice().split(",");
                // 设备信息
                DeviceInfo deviceInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String scode : tempArr) {
                    deviceInfo = deviceInfoService.selectByCode(scode);
                    sb.append(deviceInfo.getId() + ",");
                }
                modelMap.put("useDeviceIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            modelMap.put("couponUser", couponUser);
            return "ac/couponUser-infoList";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
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
     * 用户持有券列表页脚总统计
     *
     * @param couponUserVo
     * @return ResponseVo
     */
    @RequestMapping("/queryTotalData")
    @ResponseBody
    public ResponseVo<HashMap<String, BigDecimal>> queryTotalData(CouponUserVo couponUserVo) {
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 20);
        couponUserVo.setCondition(sql);
        HashMap<String, BigDecimal> map = couponUserService.queryTotalData(couponUserVo);
        return ResponseVo.getSuccessResponse(map);
    }
}

