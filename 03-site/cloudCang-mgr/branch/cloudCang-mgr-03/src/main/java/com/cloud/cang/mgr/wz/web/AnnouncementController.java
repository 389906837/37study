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
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
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
 * @description ????????????????????????
 * @time 2018-04-16 16:48:15
 * @fileName AnnouncementController.java
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    // ????????????
    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    // ??????????????????
    @Autowired
    private AnnouncementService announcementService;

    // ????????????
    @Autowired
    RegionService regionService;

    @Autowired
    MerchantInfoService merchantInfoService;//????????????

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ICached iCached;

    @RequestMapping("/list")
    public String list() {
        return "wz/announcement-list-left";
    }

    /**
     * ??????????????????????????????
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
     * ??????????????????
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
     * ??????????????????????????????????????????
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
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ????????????ID??????????????????????????????
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
     * ???????????????????????????
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
                MerchantInfo merchantInfo = merchantInfoService.selectOne(announcement.getSmerchantId());//?????????????????????
                modelMap.put("merchantInfo", merchantInfo);
            }
            announcement.setSregionId(sregionId);
            return "wz/announcement-edit";
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ??????
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
            logger.error("???????????????????????????{}", e);
            throw new ServiceException(e.getMessage());
        }
    }

    //???????????????RegionController?????????AdvertisUpdate????????????
    private void AdvertisUpdate(final String smerchantCode) {
        ExecutorManager.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    if (StringUtil.isNotBlank(smerchantCode)) {
                        //String smerchantCode = (String) SessionUserUtils.getSession().getAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_CODE);
                        //String smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
                        Region paramNav = new Region();
                        paramNav.setIdelFlag(0);
                        paramNav.setItype(20);
                        List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                        if (regionList != null && regionList.size() > 0) {
                            logger.debug("----????????????????????????????????????Redis????????????");
                            iCached.remove(RedisConst.WZ_REGIO_ANNOUNCEMENT + smerchantCode);
                            for (Region region : regionList) {
                                announcementService.updateByCached(region, smerchantCode);
                            }
                            logger.debug("----????????????????????????Redis??????????????????????????????KEY:" + RedisConst.WZ_REGIO + smerchantCode);
                        }
                    }
                } catch (Exception e) {
                    logger.error("----????????????????????????Redis?????????????????????????????????????????????{}", e);
                    throw new ServiceException("??????????????????????????????");
                }
            }
        });
    }

    /**
     * ??????ID?????????????????????
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
            String smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
            AdvertisUpdate(smerchantCode);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            logger.error("???????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("??????????????????????????????{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("main.system.delete.error",null));
    }

    /**
     * ????????????????????????
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
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("wz.publish.time.error"));
                }
            }
            announcementService.updateBySelective(announcement);
            announcement = announcementService.selectByPrimaryKey(announcement.getId());
            AdvertisUpdate(announcement.getSmerchantCode());//????????????
            // ????????????
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.release.system.announcement",null)+",", announcement.getStitle());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("wzcon.announcement.delete.error",null));
        }
    }

    /**
     * ????????????????????????
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
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("wzcon.announcement.expired",null));
                }
            }
            announcementService.updateBySelective(announcement);
            announcement = announcementService.selectByPrimaryKey(announcement.getId());
            AdvertisUpdate(announcement.getSmerchantCode());//????????????
            // ????????????
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.announcement.expired.status",null)+"{0}", announcement.getIstatus());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("?????????????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("wzcon.announcement.expired.error",null));
        }
    }
}
