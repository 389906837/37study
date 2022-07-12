package com.cloud.cang.mgr.wz.web;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.AnnouncementService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.mgr.wz.vo.AnnouncementVo;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 运营区域管理
 * @time 2018-03-29 14:46:15
 * @fileName KeyWordsController.java
 */
@Controller
@RequestMapping("/region")
public class RegionController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);

    // 注入运营区域管理serv
    @Autowired
    private RegionService regionService;

    // 注入广告区域管理serv
    @Autowired
    private AdvertisService advertisService;

    @Autowired
    private ICached iCached;
    @Autowired
    private AnnouncementService announcementService;

    /**
     * 返回运营区域管理列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "wz/region-list";
    }


    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<Region>> queryRegion(ParamPage paramPage, RegionVo regionVo) {
        PageListVo<List<Region>> pageListVo = new PageListVo<List<Region>>();
        Page<Region> page = new Page<Region>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        regionVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            regionVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = regionService.selectRegionAll(page, regionVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 跳转编辑页面
     *
     * @param modelMap
     * @param rid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String rid) {
        try {
            Region region = regionService.selectByPrimaryKey(rid);
            if (region == null) {
                region = new Region();
            }
            modelMap.put("region", region);
            return "wz/region-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 运营区域管理保存/编辑
     *
     * @param region
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<Region> save(Region region) {
        // 如果ID为空就添加
        if (StringUtils.isBlank(region.getId())) {
            region.setScode(CoreUtils.newCode(EntityTables.WZ_REGION));
            region.setTaddTime(DateUtils.getCurrentDateTime());
            region.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            region.setTupdateTime(DateUtils.getCurrentDateTime());
            region.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            region.setIdelFlag(0);
            regionService.insert(region);
            //操作日志
            String logText = MessageFormat.format("新增运营区域,名称{0},编号{1}", region.getSregionName(), region.getScode());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            // 修改
            Region oldRegion = regionService.selectByPrimaryKey(region.getId());
            region.setScode(oldRegion.getScode());
            region.setTupdateTime(DateUtils.getCurrentDateTime());
            region.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            region.setIdelFlag(0);
            regionService.updateBySelective(region);
            //操作日志
            String logText = MessageFormat.format("修改运营区域表名称{0},编号{1}", region.getSregionName(), region.getScode());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
        RegionUpdate();
        return ResponseVo.getSuccessResponse(region);
    }

    //更新缓存
    private void RegionUpdate() {

        ExecutorManager.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    Region paramNav = new Region();
                    paramNav.setIdelFlag(0);
                    List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                    if (regionList != null) {
                        iCached.remove(RedisConst.WZ_REGIO_MAIN);
                        logger.debug("----运营区域修改后更新Redis缓存开始");
                        for (Region region : regionList) {
                            //key为栏目CODE
                            String catcheKey = region.getScode();
                            if (StringUtils.isNotBlank(catcheKey)) {
                                //更新到redis缓存
                                iCached.hset(RedisConst.WZ_REGIO_MAIN, catcheKey, JSON.toJSONString(region));
                                logger.debug("----更新Redis缓存主KEY:" + RedisConst.WZ_REGIO_MAIN + "---" + catcheKey + "---" + JSON.toJSONString(region));
                            }
                        }
                        logger.debug("----运营区域修改后更新Redis结束，缓存成功！，主KEY:" + RedisConst.WZ_REGIO_MAIN);
                    }
                    //更新下面的广告区
                    AdvertisUpdate();
                } catch (Exception e) {
                    logger.error("----运营区域修改后更新Redis结束，缓存失败！！，错误信息：{}", e);
                    throw new ServiceException(e.getMessage());
                }
            }
        });

    }


    //更新缓存 与 AdvertisController 下方法 AdvertisUpdate 保持一致
    private void AdvertisUpdate() {
        ExecutorManager.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
                    Region paramNav = new Region();
                    paramNav.setIdelFlag(0);
                    List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                    if (regionList != null && regionList.size() > 0) {
                        logger.debug("----运营位修改后更新Redis缓存开始");
                        iCached.remove(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode);
                        iCached.remove(RedisConst.WZ_REGIO_ANNOUNCEMENT + smerchantCode);
                        for (Region region : regionList) {
                            if (region.getItype().intValue() == 10) {//广告
                                advertisService.updateByCached(region, smerchantCode);
                            } else if (region.getItype().intValue() == 20) {//系统通知
                                announcementService.updateByCached(region, smerchantCode);
                            }
                        }
                        logger.debug("----运营位修改后更新Redis结束，缓存成功！，主KEY:" + RedisConst.WZ_REGIO + smerchantCode);
                    }

                } catch (Exception e) {
                    logger.error("----运营位修改后更新Redis结束，缓存失败！！，错误信息：{}", e);
                    throw new ServiceException("运营位缓存更新失败");
                }
            }
        });
    }

    /**
     * 根据ID数据删除运营区域表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            regionService.delete(checkboxId);
            RegionUpdate();
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常！删除失败");
    }
}