package com.cloud.cang.mgr.wz.web;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.StreamRenderUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.domain.AnnouncementDomain;
import com.cloud.cang.mgr.wz.domain.RegionDomain;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.nntp.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 广告区域管理
 * @time 2018-03-29 17:16:15
 * @fileName AdvertisController.java
 */
@Controller
@RequestMapping("/advertis")
public class AdvertisController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(AdvertisController.class);

    // 广告区域
    @Autowired
    private AdvertisService advertisService;

    // 运营区域
    @Autowired
    RegionService regionService;

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ICached iCached;

    @RequestMapping("/list")
    public String list() {
        return "wz/advertis-list-left";
    }

    /**
     * 查询广告区域管理列表
     *
     * @param paramPage
     * @param advertisVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<AdvertisDomain>> queryAdvertis(ParamPage paramPage, AdvertisVo advertisVo) {
        PageListVo<List<AdvertisDomain>> pageListVo = new PageListVo<List<AdvertisDomain>>();
        Page<AdvertisDomain> page = new Page<AdvertisDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        advertisVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            advertisVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = advertisService.selectAdvertis(page, advertisVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 获取运营数据
     *
     * @param
     * @return
     */
    @RequestMapping("/getRerionList")
    public @ResponseBody
    ResponseVo<List<Region>> queryDataProvince(RegionVo regionVo) {
        List<Region> regions = regionService.selectGetRerionList(regionVo);
        return ResponseVo.getSuccessResponse(regions);
    }


    /**
     * 跳转到广告区域管理父页面
     *
     * @param modelMap
     * @param aid
     * @return
     */
    @RequestMapping("/page")
    public String pageList(ModelMap modelMap, String aid) {
        try {
            if (StringUtil.isNotBlank(aid)) {
                modelMap.put("aid", aid);
            }
            return "wz/advertis-list";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 根据菜单ID获取广告区域数据
     *
     * @param aid
     * @return
     */
    @RequestMapping("/getAdvertisByAid")
    public @ResponseBody
    PageListVo<List<AdvertisDomain>> getAdvertisByAid(ParamPage paramPage, String aid) {
        PageListVo<List<AdvertisDomain>> pageListVo = new PageListVo<List<AdvertisDomain>>();
        /*if (StringUtil.isBlank(aid)) {
            return pageListVo;
        }*/
        Map<String, Object> map = new HashMap<String, Object>();
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 110);
        map.put("condition", sql);
        if (StringUtil.isNotBlank(aid)) {
            map.put("sregionId", aid);
        }
        map.put("idelFlag", 0);
        Page<AdvertisDomain> page = new Page<AdvertisDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        page = advertisService.selectSregionId(page, map);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
      /*  List<AdvertisDomain> advertis = advertisService.selectSregionId(map);
        if (advertis != null) {
            pageListVo.setRows(advertis);
        }
        return pageListVo;*/
    }


    /**
     * 跳转到编辑保存页面
     *
     * @param modelMap
     * @param aid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, Advertis advertis, String aid) {
        try {
            if (StringUtil.isNotBlank(aid)) {
                advertis = advertisService.selectByPrimaryKey(aid);
//                advertis.setSregionId(sregionId);
                modelMap.put("advertis", advertis);
                MerchantInfo merchantInfo = merchantInfoService.selectOne(advertis.getSmerchantId());//商户基础信息表
                modelMap.put("merchantInfo", merchantInfo);
            }
//            advertis.setSregionId(sregionId);
            return "wz/advertis-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存
     *
     * @param photoFile
     * @param advertis
     * @param response
     */
    @RequestMapping("/save")
    public void save(@RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                     Advertis advertis, HttpServletResponse response) {
        try {

            if (advertis.getTstartDate() != null && advertis.getTendDate() != null) {
                if (DateUtils.compareDate(advertis.getTstartDate(), advertis.getTendDate()) == 1) {
                    StreamRenderUtil.render(
                            ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("开始日期不能大于结束日期！"),
                            response, null);
                    return;
                }
            }
            advertisService.save(photoFile, advertis);

            AdvertisUpdate();

            StreamRenderUtil.render(ResponseVo.getSuccessResponse(), response, null);

        } catch (Exception e) {
            logger.error("编辑保存区域异常：{}", e);
            throw new ServiceException(e.getMessage());
        }
    }

    //更新缓存与RegionController下方法AdvertisUpdate保持一致
    private void AdvertisUpdate() {
        ExecutorManager.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
                    Region paramNav = new Region();
                    paramNav.setIdelFlag(0);
                    paramNav.setItype(10);
                    List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                    logger.debug("运营区域数据==========" + regionList);
                    if (regionList != null && regionList.size() > 0) {
                        logger.debug("----运营位修改后更新广告Redis缓存开始");
                        iCached.remove(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode);
                        for (Region region : regionList) {
                            advertisService.updateByCached(region, smerchantCode);
                        }
                        logger.debug("----运营位修改后更新Redis结束，缓存成功！，主KEY:" + RedisConst.WZ_REGIO + smerchantCode);
                    }
                } catch (Exception e) {
                    logger.error("----运营位修改后更新Redis结束，缓存失败！！，错误信息：{}", e);
                    throw new ServiceException(e.getMessage());
                }
            }
        });
    }

    /**
     * 根据ID数据删除广告表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            advertisService.delete(checkboxId);
            AdvertisUpdate();
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("广告为已发布状态不能删除");
    }
}