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
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.domain.AnnouncementDomain;
import com.cloud.cang.mgr.wz.service.AnnouncementService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.mgr.wz.vo.AnnouncementVo;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 系统广告区域管理
 * @time 2018-04-16 16:48:15
 * @fileName AnnouncementController.java
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    // 系统公告区域
    @Autowired
    private AnnouncementService announcementService;

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
        return "wz/announcement-list-left";
    }

    /**
     * 查询广告区域管理列表
     *
     * @param paramPage
     * @param announcementVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<AnnouncementDomain>> queryAdvertis(ParamPage paramPage, AnnouncementVo announcementVo) {
        PageListVo<List<AnnouncementDomain>> pageListVo = new PageListVo<List<AnnouncementDomain>>();
        Page<AnnouncementDomain> page = new Page<AnnouncementDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        announcementVo.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            announcementVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = announcementService.selectAnnounceMent(page, announcementVo);
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
        List<Region> regions = regionService.selectGetRerionList1(regionVo);
        return ResponseVo.getSuccessResponse(regions);
    }

    /**
     * 跳转到系统公告区域管理父页面
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
            return "wz/announcement-list";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 根据菜单ID获取系统公告区域数据
     *
     * @param aid
     * @return
     */
    @RequestMapping("/getAnnounceMentByAid")
    public @ResponseBody
    PageListVo<List<AnnouncementDomain>> getAnnounceMentByAid(ParamPage paramPage, String aid) {
        PageListVo<List<AnnouncementDomain>> pageListVo = new PageListVo<List<AnnouncementDomain>>();
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
        Page<AnnouncementDomain> page = new Page<AnnouncementDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());

        page = announcementService.selectSregionId(page, map);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 跳转到编辑保存页面
     *
     * @param modelMap
     * @param aid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, Announcement announcement, String aid, String sregionId) {
        try {
            if (StringUtil.isNotBlank(aid)) {
                announcement = announcementService.selectByPrimaryKey(aid);
                announcement.setSregionId(sregionId);
                modelMap.put("announcement", announcement);
                MerchantInfo merchantInfo = merchantInfoService.selectOne(announcement.getSmerchantId());//商户基础信息表
                modelMap.put("merchantInfo", merchantInfo);
            }
            announcement.setSregionId(sregionId);
            return "wz/announcement-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存
     *
     * @param announcement
     * @param response
     */
    @RequestMapping("/save")
    public void save(Announcement announcement, AnnouncementVo announcementVo, HttpServletResponse response) {
        try {
            announcementService.save(announcement);
            StreamRenderUtil.render(ResponseVo.getSuccessResponse(), response, null);
        } catch (Exception e) {
            logger.error("保存系统公告异常：{}", e);
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
                    paramNav.setItype(20);
                    List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                    if (regionList != null && regionList.size() > 0) {
                        logger.debug("----运营位修改后更新系统公告Redis缓存开始");
                        iCached.remove(RedisConst.WZ_REGIO_ANNOUNCEMENT + smerchantCode);
                        for (Region region : regionList) {
                            announcementService.updateByCached(region, smerchantCode);
                        }
                        logger.debug("----运营位修改后更新Redis结束，缓存成功！，主KEY:" + RedisConst.WZ_REGIO + smerchantCode);
                    }

                } catch (Exception e) {
                    logger.error("----运营位修改后更新Redis结束，缓存失败！！，错误信息：{}", e);
                    throw new ServiceException("系统公告缓存更新失败");
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
            announcementService.delete(checkboxId);
            AdvertisUpdate();
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            logger.error("删除失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常！删除失败：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常！删除失败");
    }

    /**
     * 系统公告发布状态
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/publish")
    public @ResponseBody
    ResponseVo<String> pubLish(String checkboxId, Integer type) {
        try {
            Announcement announcementOld = announcementService.selectByPrimaryKey(checkboxId);
            Announcement announcement = new Announcement();
            announcement.setId(checkboxId);
            announcement.setIstatus(type);
            announcement.setTpublishDate(DateUtils.getCurrentDateTime());
            announcement.setSauthor(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            announcement.setTupdateTime(DateUtils.getCurrentDateTime());
            announcement.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            announcement.setSremark(announcementOld.getSremark());
            Announcement announcements = announcementService.selectByPrimaryKey(checkboxId);
            if (announcement.getTpublishDate() != null && announcements.getTvalidDate() != null) {
                if (announcements.getTvalidDate().compareTo(announcement.getTpublishDate()) < 0) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("发布日期大于截止日期,公告已失效,请重新选择截止时间！");
                }
            }
            announcementService.updateBySelective(announcement);
            AdvertisUpdate();//更新缓存
            // 操作日志
            String logText = MessageFormat.format("发布系统公告,", announcement.getStitle());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("系统公告发布状态异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统公告为已发布状态且还未到期不能删除");
        }
    }

    /**
     * 手动修改过期状态
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/expire")
    public @ResponseBody
    ResponseVo<String> expire(String checkboxId, Integer type) {
        try {
            Announcement announcement = new Announcement();
            announcement.setId(checkboxId);
            announcement.setIstatus(type);
            announcement.setTpublishDate(DateUtils.getCurrentDateTime());
            announcement.setSauthor(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            announcement.setTupdateTime(DateUtils.getCurrentDateTime());
            announcement.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            Announcement announcements = announcementService.selectByPrimaryKey(checkboxId);
            if (announcement.getTpublishDate() != null && announcements.getTvalidDate() != null) {
                if (announcements.getTvalidDate().compareTo(announcement.getTpublishDate()) < 0) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("公告已过期失效,请删除！");
                }
            }
            announcementService.updateBySelective(announcement);
            AdvertisUpdate();//更新缓存
            // 操作日志
            String logText = MessageFormat.format("过期系统公告，状态{0}", announcement.getIstatus());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("手动修改过期状态异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统公告为待发布状态且过期可以删除");
        }
    }
}
