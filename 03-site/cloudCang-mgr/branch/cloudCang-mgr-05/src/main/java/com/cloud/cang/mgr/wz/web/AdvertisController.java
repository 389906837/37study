package com.cloud.cang.mgr.wz.web;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.common.utils.ReadFileUtil;
import com.cloud.cang.mgr.sb.vo.DeviceSelectVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description ??????????????????
 * @time 2018-03-29 17:16:15
 * @fileName AdvertisController.java
 */
@Controller
@RequestMapping("/advertis")
public class AdvertisController {

    // ????????????
    private static final Logger logger = LoggerFactory.getLogger(AdvertisController.class);

    // ????????????
    @Autowired
    private AdvertisService advertisService;

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
        return "wz/advertis-list-new";
    }

    /**
     * ??????????????????????????????
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
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            advertisVo.setOrderStr("" + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 110);
        advertisVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            advertisVo.setOrderStr("" + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        advertisVo.setIdelFlag(0);
        page = advertisService.selectPageByDomainWhere(page, advertisVo);
        //page = advertisService.selectAdvertis(page, advertisVo);
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
        List<Region> regions = regionService.selectGetRerionList(regionVo);
        return ResponseVo.getSuccessResponse(regions);
    }


    /**
     * ????????????????????????????????????
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
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ????????????ID????????????????????????
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
     * ???????????????????????????
     *
     * @param modelMap
     * @param aid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap modelMap, String aid) {
        try {
            Advertis advertis = advertisService.selectByPrimaryKey(aid);
            if (advertis == null) {
                advertis = new Advertis();
            }
            modelMap.put("advertis", advertis);
            //????????????????????????
            Region paramRegion = new Region();
            paramRegion.setIdelFlag(0);
            List<Region> regions = regionService.selectByEntityWhere(paramRegion);
            modelMap.put("regions", regions);
            return "wz/advertis-edit";
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ??????
     *
     * @param photoFile
     * @param advertis
     * @param response
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo save(@RequestParam(value = "photoFile", required = false) MultipartFile photoFile,
                           Advertis advertis, HttpServletResponse response, String tempFile) {
        try {

            if (advertis.getTstartDate() != null && advertis.getTendDate() != null) {
                if (DateUtils.compareDate(advertis.getTstartDate(), advertis.getTendDate()) == 1) {
              /*      StreamRenderUtil.render(
                            ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????????????????"),
                            response, null);*/

                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("wzcom.effective.date.error"));
                }
            }
            if (StringUtil.isBlank(advertis.getSregionCode())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("region.area.code.empty"));

            }
            advertisService.save(photoFile, advertis, tempFile);
            advertis = advertisService.selectByPrimaryKey(advertis.getId());
            AdvertisUpdate(advertis.getSmerchantCode());
            //StreamRenderUtil.render(ResponseVo.getSuccessResponse(), response, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException se) {
            logger.error("???????????????????????????{}", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            logger.error("???????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
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
                        paramNav.setItype(10);
                        List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                        logger.debug("??????????????????==========" + regionList);
                        if (regionList != null && regionList.size() > 0) {
                            logger.debug("----??????????????????????????????Redis????????????");
                            iCached.remove(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode);
                            for (Region region : regionList) {
                                advertisService.updateByCached(region, smerchantCode);
                            }
                            logger.debug("----????????????????????????Redis??????????????????????????????KEY:" + RedisConst.WZ_REGIO + smerchantCode);
                        }
                    }
                } catch (Exception e) {
                    logger.error("----????????????????????????Redis?????????????????????????????????????????????{}", e);
                    throw new ServiceException(e.getMessage());
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
            advertisService.delete(checkboxId);
            String smerchantCode = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode();
            AdvertisUpdate(smerchantCode);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("wzcon.delete.ads.error", null));
    }

    /**
     * ?????????????????????FTP?????????
     *
     * @param guid
     */
    @RequestMapping("/upVideoToFtp")
    @ResponseBody
    public ResponseVo upVideoToFtp(HttpServletRequest request, String guid, Integer chunk, MultipartFile file) {
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart && null != file && !file.isEmpty()) {
                //???????????????????????????file?????????????????????Tomcat???webapps??????????????????????????????fileDir
                //????????????
                String fileRealName = file.getOriginalFilename();     //?????????????????????;
                String[] fileNameSplit = fileRealName.split("\\.");//???????????????
                String exp = fileNameSplit[fileNameSplit.length - 1];  //??????????????????
                //??????????????????
                if (!"mp4".equals(exp)) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("video.only.support.mp4"));
                }
                // ?????????????????????????????????????????? ??
                String saveFile = System.getProperty("catalina.home") + File.separator + "videoTempFile" + File.separator;
                String tempFileDir = saveFile + guid;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                // ??????????????????????????????????????????????????????????????????????????????????????????????????? ??
                if (null == chunk) {
                    chunk = 0;
                }
                File tempPartFile = new File(parentFileDir, guid + "_" + chunk + "." + exp);
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                return ResponseVo.getSuccessResponse();
            }
            logger.info("?????????????????????????????????{}", isMultipart);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("upload.file.to.server.fail"));
        } catch (Exception e) {
            logger.error("??????apk?????????FTP??????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("upload.file.to.server.fail"));
        }
    }

    /**
     * ???? ?? * ????????????
     * ???? ?? * @param guid
     * ???? ?? * @param fileName
     * ???? ?? * @throws Exception
     * ???? ??
     */
    @RequestMapping("/mergeFile")
    @ResponseBody
    public ResponseVo<String> mergeFile(String guid, String fileName) {
        // ?????? destTempFile ????????????????????? ??
        //  JsonResult jsonReulst = new JsonResult();
        ResponseVo<String> responseVo = null;
        try {
            String[] fileNameSplit = fileName.split("\\.");//???????????????
            String exp = fileNameSplit[fileNameSplit.length - 1];  //??????????????????
            String file = System.getProperty("catalina.home") + File.separator + "videoTempFile";
            File parentFileDir = new File(file + File.separator + guid);
            String name = DateUtils.getCurrentSTimeNumber() + "." + exp;
            String proFile = file + File.separator + name;
            if (parentFileDir.isDirectory()) {
                File destTempFile = new File(proFile);
                for (int i = 0; i < parentFileDir.listFiles().length; i++) {
                    File partFile = new File(parentFileDir, guid + "_" + i + "." + exp);
                    FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                    //??????"??????????????????"???"????????????"??? ??
                    FileUtils.copyFile(partFile, destTempfos);
                    destTempfos.close();
                }
                // ???????????????????????????????????? ??
                FileUtils.deleteDirectory(parentFileDir);

            }
            return new ResponseVo<>(proFile);
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.uf"));
        }
    }

    /**
     * ??????????????????
     *
     * @param tempFile
     */
    @RequestMapping("/deleTempFile")
    @ResponseBody
    public ResponseVo deleTempFile(String tempFile) {
        try {
            String location = System.getProperty("catalina.home") + File.separator + "videoTempFile" + File.separator + tempFile;
            if (tempFile.contains("mp4")) {
                File file = new File(location);
                // ??????????????????????????????????????????
                if (file.isFile() && file.exists()) {
                    file.delete();
                }
            } else {
                ReadFileUtil.delFolder(location);
            }
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("??????????????????????????????:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("main.delete.error"));
        }
    }

    /***
     * ??????????????????????????????
     * @param modelMap
     * @param checkboxId ????????????
     * @return
     */
    @RequestMapping("/toBinding")
    public String toBinding(String[] checkboxId, ModelMap modelMap) {
        try {
            if (null != checkboxId && checkboxId.length > 0) {
                List<AdvertisDomain> advertisList = new ArrayList<AdvertisDomain>();
                AdvertisDomain advertis = null;
                StringBuffer sb = new StringBuffer();
                for (String advId : checkboxId) {
                    advertis = advertisService.selectDomainByAdvId(advId);
                    if (null != advertis) {
                        advertisList.add(advertis);
                        sb.append(advId);
                        sb.append(",");
                    }
                }
                if (advertisList.size() <= 0) {
                    return ExceptionUtil.to404(MessageSourceUtils.getMessageByKey("wzcon.delete.toBinding.error"), modelMap);
                }
                modelMap.put("advIds", sb.toString().substring(0, (sb.toString().length() - 1)));
                modelMap.put("advertisList", advertisList);
                return "wz/advertis-binding";
            }
            return ExceptionUtil.to404();
        } catch (Exception e) {
            logger.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ??????????????????????????????
     *
     * @param selectVo ??????????????????
     * @throws Exception
     */
    @RequestMapping("/saveBinding")
    public @ResponseBody
    ResponseVo<String> saveBinding(DeviceSelectVo selectVo) {
        try {
            if (null == selectVo) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.ac.tdiapro"));
            }
            if (null == selectVo.getIrangeDevice()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("bind.device.range.empty"));
            }
            if (StringUtil.isBlank(selectVo.getAdvIds())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("bind.news.data.empty"));
            }
            if (selectVo.getIrangeDevice().intValue() == 20 && StringUtil.isBlank(selectVo.getDeviceIds())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("bind.device.empty"));
            }
            return advertisService.saveBinding(selectVo);
        } catch (ServiceException e) {
            logger.error("???????????????????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("???????????????????????????????????????{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.tdiapro"));
    }
}