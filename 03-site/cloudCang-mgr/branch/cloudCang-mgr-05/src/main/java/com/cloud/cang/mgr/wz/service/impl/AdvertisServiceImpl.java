package com.cloud.cang.mgr.wz.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sb.dao.DeviceInfoDao;
import com.cloud.cang.mgr.sb.vo.DeviceSelectVo;
import com.cloud.cang.mgr.wz.dao.AdvertisDao;
import com.cloud.cang.mgr.wz.dao.RegionDao;
import com.cloud.cang.mgr.wz.domain.AdvertisDomain;
import com.cloud.cang.mgr.wz.service.AdvertisService;
import com.cloud.cang.mgr.wz.service.DeviceNewsService;
import com.cloud.cang.mgr.wz.service.RegionService;
import com.cloud.cang.mgr.wz.vo.AdvertisVo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.DeviceNews;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Service
public class AdvertisServiceImpl extends GenericServiceImpl<Advertis, String> implements
        AdvertisService {
    @Autowired
    AdvertisDao advertisDao;
    @Autowired
    RegionDao regionDao;
    @Autowired
    RegionService regionService;
    @Autowired
    private ICached iCached;
    @Autowired
    private DeviceNewsService deviceNewsService;
    @Autowired
    private DeviceInfoDao deviceInfoDao;
    private static final Logger logger = LoggerFactory.getLogger(com.cloud.cang.mgr.wz.service.impl.AdvertisServiceImpl.class);


    @Override
    public GenericDao<Advertis, String> getDao() {
        return advertisDao;
    }


    @Override
    public Page<AdvertisDomain> selectAdvertis(Page<AdvertisDomain> page, AdvertisVo advertisVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return advertisDao.selectAdvertis(advertisVo);
    }

    @Override
    public Page<AdvertisDomain> selectSregionId(Page<AdvertisDomain> page, Map<String, Object> map) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return advertisDao.selectSregionId(map);
    }

    @Override
    public List<Advertis> selectByRrgionId(String rid) {
        return advertisDao.selectByRrgionId(rid);
    }

    @Override
    public void save(MultipartFile photoFile, Advertis advertis, String tempFile) {
        Region region = regionDao.selectByCode(advertis.getSregionCode());
        if (null == region) {
            throw new ServiceException("运营区域数据异常，请重新操作");
        }
        if (StringUtils.isBlank(advertis.getId())) {
            if (advertis.getIadvType() == 10 && photoFile != null && photoFile.getSize() > 0) {
                // 1.文件上传
                String url = uploadHome(photoFile, advertis.getSregionCode());
                advertis.setScontentUrl(url);
            } else if (advertis.getIadvType() == 20 && StringUtils.isNotBlank(tempFile)) {
                tempFile = uploadHome(tempFile,advertis.getSregionCode());
                advertis.setScontentUrl(tempFile);
            }
        } else {
            if (advertis.getIadvType() == 10 && photoFile != null && photoFile.getSize() > 0) {
                String url = uploadHome(photoFile, advertis.getSregionCode());
                advertis.setScontentUrl(url);
            } else if (advertis.getIadvType() == 20 && StringUtils.isNotBlank(tempFile)) {
                tempFile = uploadHome(tempFile, advertis.getSregionCode());
                advertis.setScontentUrl(tempFile);
            }
        }
        // 如果id为空就就添加
        if (StringUtils.isBlank(advertis.getId())) {
            if (advertis.getIadvType() == 10 && photoFile == null) {
                throw new ServiceException(MessageSourceUtils.getMessageByKey("wzcon.select.image.upload",null));
            } else if (advertis.getIadvType() == 20 && StringUtils.isBlank(tempFile)) {
                throw new ServiceException("请选择视频上传");
            }
            advertis.setSregionId(region.getId());
            advertis.setSmerchantCode(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantCode());//商户编号
            advertis.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            advertis.setTaddTime(DateUtils.getCurrentDateTime());
            advertis.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            advertis.setTupdateTime(DateUtils.getCurrentDateTime());
            advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            advertis.setIdelFlag(0);
            advertis.setSregionId(region.getId());
            this.insert(advertis);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.add.ad.area",null)+"，"+ MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", advertis.getStitle());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        } else {
            /*if (advertis.getIadvType() == 20 && StringUtils.isBlank(tempFile)) {
                throw new ServiceException("请选择视频上传");
            }*/
            // 修改
            advertis.setSregionId(region.getId());
            advertis.setTupdateTime(DateUtils.getCurrentDateTime());
            advertis.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            advertis.setIdelFlag(0);
            this.updateBySelective(advertis);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.modify.ad.area",null)+"，"+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", advertis.getStitle());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        }
    }

    public String uploadHome(MultipartFile imgFile, String code) {
        if (imgFile == null) {
            throw new ServiceException("没有找到上传文件");
        }
        //文件原名
        String filName = imgFile.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";
        try {
            String serverPath = "article/" + DateUtils.dateParseShortString(new Date()) + "/" + code + "/";
            String fileFileName = imgFile.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            if (FtpUtils.uploadToFtpServer(imgFile.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return url;
    }

    /**
     * 上传视频
     *
     * @param videoFile
     * @param sregionCode
     * @return
     */
    private String uploadHome(String videoFile, String sregionCode) {

        File file = new File(videoFile);
        //文件原名
        String filName = file.getName();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("mp4")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";
        try {
            String code = sregionCode;
            String serverPath = "article/" + DateUtils.dateParseShortString(new Date()) + "/" + code + "/";
            FileInputStream fileInputStream = new FileInputStream(file);
            String fileFileName = filName.substring(filName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            if (FtpUtils.uploadToFtpServer(fileInputStream, serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return url;
    }

    @Override
    public List<Advertis> selectListByVo(AdvertisVo paramAdvertis) {
        return advertisDao.selectListByVo(paramAdvertis);
    }

    /**
     * 更新缓存
     *
     * @param region        运营位
     * @param smerchantCode 商户编号
     */
    @Override
    public void updateByCached(Region region, String smerchantCode) throws Exception {
        //key为前缀+栏目CODE
        String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region.getScode() + "_" + smerchantCode;
        logger.info("从KEY广告位运营==========" + catcheKey);
        //找出已发布和未删除的广告位
        AdvertisVo paramAdvertis = new AdvertisVo();
        paramAdvertis.setSregionId(region.getId());
        paramAdvertis.setSmerchantCode(smerchantCode);
        paramAdvertis.setIdelFlag(0);
        paramAdvertis.setIstatus(1);
        paramAdvertis.setTstartDate(DateUtils.add(DateUtils.getToday(), Calendar.DATE, -1));
        Page<AdvertisVo> page = new Page<AdvertisVo>();
        page.setPageNum(1);
        page.setPageSize(region.getIcount());//取出要缓存的数量
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Advertis> advertisList = this.selectListByVo(paramAdvertis);

        //更新到redis缓存
        iCached.hset(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode, catcheKey, JSON.toJSONString(advertisList));
        logger.debug("----更新广告Redis缓存主KEY:" + RedisConst.WZ_REGIO_ADVERTIS + smerchantCode + "---" + catcheKey + "---" + JSON.toJSONString(advertisList));
    }

    /**
     * 根据ID进行逻辑删除(删除页面数据同时删除表数据)
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        for (String id : checkboxId) {
            Advertis advertis = advertisDao.selectByPrimaryKey(id);
            if (advertis != null) {
                if (advertis.getIstatus().equals(1)){
                    throw new ServiceException(MessageSourceUtils.getMessageByKey("wzcon.delete.ad.area.error",null));
                }
                advertis.setIdelFlag(1);
                advertisDao.updateByPrimaryKey(advertis);
            }
            regionDao.deleteRegionById(id);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("wzcon.delete.ad.area",null)+","+MessageSourceUtils.getMessageByKey("main.title.msg",null)+"{0}", advertis.getStitle());
            LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);
        }
    }

    /**
     * 查询广告列表数据
     *
     * @param page
     * @param advertisVo
     * @return
     */
    @Override
    public Page<AdvertisDomain> selectPageByDomainWhere(Page<AdvertisDomain> page, AdvertisVo advertisVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return advertisDao.selectPageByDomainWhere(advertisVo);
    }

    /***
     * 查询广告信息
     * @param advId 资讯ID
     * @return
     */
    @Override
    public AdvertisDomain selectDomainByAdvId(String advId) {
        return advertisDao.selectDomainByAdvId(advId);
    }

    /**
     * 设备广告数据绑定保存
     *
     * @param selectVo 绑定数据模型
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public ResponseVo<String> saveBinding(DeviceSelectVo selectVo) throws ServiceException, Exception {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        String[] checkboxId = selectVo.getAdvIds().split(",");
        if (null == checkboxId || checkboxId.length <= 0) {
            throw new ServiceException("没有选择任何有效资讯信息，请重新操作");
        }
        List<AdvertisDomain> advertisList = new ArrayList<AdvertisDomain>();
        AdvertisDomain advertis = null;

        for (String advId : checkboxId) {
            advertis = this.selectDomainByAdvId(advId);
            if (null != advertis) {
                advertisList.add(advertis);
            }
        }
        if (advertisList.size() <= 0) {
            throw new ServiceException("没有选择任何有效资讯信息，请重新操作");
        }
        List<DeviceInfo> deviceInfos = null;
        if (selectVo.getIrangeDevice().intValue() == 10) {
            //查询商户下全部设备
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("itype", 200);
            paramMap.put("merchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
            deviceInfos = deviceInfoDao.selectAllDeviceBySmerchantId(paramMap);
            if (null == deviceInfos || deviceInfos.size() <= 0) {
                throw new ServiceException("没有可操作的设备数据，请重新操作");
            }

        } else if (selectVo.getIrangeDevice().intValue() == 20) {
            String[] deviceIds = selectVo.getDeviceIds().split(",");
            if (null == deviceIds || deviceIds.length <= 0) {
                throw new ServiceException("选择设备数据异常，请重新操作");
            }
            deviceInfos = new ArrayList<DeviceInfo>();
            DeviceInfo tempDeviceInfo = null;
            for (String deviceId : deviceIds) {
                tempDeviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
                if (null != tempDeviceInfo && tempDeviceInfo.getIoperateStatus().intValue() == BizTypeDefinitionEnum.DeviceOperateStatus.ENABLE.getCode()
                        && (tempDeviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.NORMAL.getCode() ||
                        tempDeviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode() ||
                        tempDeviceInfo.getIstatus().intValue() == BizTypeDefinitionEnum.DeviceStatus.MALFUNCTION.getCode())) {
                    deviceInfos.add(tempDeviceInfo);
                }
            }
            if (deviceInfos.size() <= 0) {
                throw new ServiceException("没有可操作的设备数据，请重新操作");
            }
        }
        DeviceNews deviceNews = null;
        StringBuffer sb = new StringBuffer();
        for (DeviceInfo deviceInfo : deviceInfos) {
            for (AdvertisDomain temp : advertisList) {
                //判断是添加还是修改
                deviceNews = deviceNewsService.selectByAdvIdAndDeviceId(deviceInfo.getId(), temp.getId());
                if (null == deviceNews) {
                    deviceNews = new DeviceNews();
                    deviceNews.setIdelFlag(0);
                    deviceNews.setItype(10);
                    deviceNews.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    deviceNews.setSmerchantId(deviceInfo.getSmerchantId());
                    deviceNews.setSmerchantCode(deviceInfo.getSmerchantCode());
                    deviceNews.setSdeviceCode(deviceInfo.getScode());
                    deviceNews.setSdeviceId(deviceInfo.getId());
                    deviceNews.setSnewsId(temp.getId());
                    deviceNews.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    deviceNews.setTaddTime(DateUtils.getCurrentDateTime());
                    deviceNews.setTupdateTime(DateUtils.getCurrentDateTime());
                    deviceNewsService.insert(deviceNews);
                } else {
                    deviceNews.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    deviceNews.setTupdateTime(DateUtils.getCurrentDateTime());
                    deviceNewsService.updateBySelective(deviceNews);
                }
            }
            sb.append(deviceInfo.getScode());
            sb.append(",");
        }
        if (sb.toString().length() > 0) {
            //操作日志
            String logText = MessageFormat.format("设备广告数据绑定，设备编号{0}", sb.toString().substring(0, sb.toString().length() - 1));
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            responseVo.setData(sb.toString().substring(0, sb.toString().length() - 1));
        }

        return responseVo;
    }
    /**
     * 分页查询广告资源数据
     * @param page 分页参数
     * @param advertisVo 查询条件
     * @return
     */
    @Override
    public Page<AdvertisDomain> selectPageByDeviceId(Page<AdvertisDomain> page, AdvertisVo advertisVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return advertisDao.selectPageByDeviceId(advertisVo);
    }
}