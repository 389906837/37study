package com.cloud.cang.tec.service;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.model.wz.Announcement;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.sb.DeviceServicesDefine;
import com.cloud.cang.sb.SendOperatingAdvertisingDto;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.sb.service.DeviceInfoService;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.cloud.cang.tec.wz.service.AdvertisService;
import com.cloud.cang.tec.wz.service.AnnouncementService;
import com.cloud.cang.tec.wz.service.RegionService;
import com.cloud.cang.tec.wz.vo.RegionVo;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 广告资讯过期定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "advertisingService")
public class AdvertisingService {
    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private AdvertisService advertisService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ICached iCached;
    @Autowired
    private DeviceInfoService deviceInfoService;
    private static final Logger logger = LoggerFactory.getLogger(AdvertisingService.class);

    /**
     * 广告过期定时任务
     */
    public void advertisExpireJob() {
        logger.info("广告过期定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (Exception e) {
                    logger.error("广告过期定时任务失败", e);
                    return "广告过期定时任务失败";
                }
            }
        }, TecConstants.ADVERTIS_EXPIRED_WARN_TASK, true);
    }

    /**
     * 资讯过期定时任务
     */

    public void announcementExpireJob() {
        logger.info("资讯过期定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (Exception e) {
                    logger.error("资讯过期定时任务失败", e);
                    return "资讯过期定时任务失败";
                }
            }
        }, TecConstants.ANNOUNCEMENT_EXPIRED_WARN_TASK, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型 10 广告过期 20 资讯过期
     * @return
     * @throws Exception
     */
    private String pageOperByMerchant(final Integer type) throws Exception {
        String msg = "定时任务执行成功";
        Page<MerchantInfo> p = new Page<MerchantInfo>();
        p.setPageSize(10);
        p.setPageNum(1);
        final MerchantInfo param = new MerchantInfo();
        param.setIstatus(BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode());
        param.setIdelFlag(0);
        p = (Page<MerchantInfo>) merchantInfoService.queryPage(p, param);
        if (null == p || 0 == p.getPages()) {
            if (type.intValue() == 10) {
                msg = "广告过期定时任务成功";
            } else if (type.intValue() == 20) {
                msg = "资讯过期定时任务成功";
            }
        }
        //总页数
        int totalPage = p.getPages();
        //每个线程组执行10页
        final int pageSize = 1;
        //循环次数
        int loopPage = totalPage % pageSize == 0 ? totalPage / pageSize : totalPage / pageSize + 1;
        for (int i = 1; i <= loopPage; i++) {
            int endPage = i * pageSize;
            if (endPage > totalPage) endPage = totalPage;
            int startPage = (i - 1) * pageSize;
            int count = endPage - startPage;
            TaskAction<List<MerchantInfo>>[] taskActions = new TaskAction[endPage - startPage];
            for (int page = (i - 1) * pageSize + 1; page <= endPage; page++) {
                //根据页数循环创建任务，一页一个任务
                final int _page = page;
                TaskAction<List<MerchantInfo>> taskAction = new TaskAction<List<MerchantInfo>>() {
                    @Override
                    public List<MerchantInfo> doInAction() {
                        Page<MerchantInfo> pl = new Page<MerchantInfo>();
                        pl.setPageSize(10);
                        pl.setPageNum(_page);
                        pl = (Page<MerchantInfo>) merchantInfoService.queryPage(pl, param);
                        List<MerchantInfo> merchantInfoList = pl.getResult();
                        for (MerchantInfo merchantInfo : merchantInfoList) {
                            //执行定时任务方法
                            if (type.intValue() == 10) {
                                advertisExpireByMerchant(merchantInfo);
                            } else if (type.intValue() == 20) {
                                announcementExpireByMerchant(merchantInfo);
                            }
                        }
                        return merchantInfoList;
                    }
                };
                //加到任务数组里面
                taskActions[count - 1] = taskAction;
                count--;
            }
            //执行线程
            ExecutorManager.getInstance().executeTask(taskActions);
        }
        if (type.intValue() == 10) {
            msg = "广告过期定时任务成功";
        } else if (type.intValue() == 20) {
            msg = "资讯过期定时任务成功";
        }
        return msg;

    }

    /**
     * 广告过期定时任务 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void advertisExpireByMerchant(MerchantInfo merchantInfo) {
        logger.info("广告过期定时任务开始：{}", merchantInfo);
        try {
            List<RegionVo> regionVos = advertisService.selectNotExpiredAdvertis(merchantInfo.getId());
            for (RegionVo regionVo : regionVos) {
                Advertis upAdvertis = new Advertis();
                upAdvertis.setId(regionVo.getId());
                upAdvertis.setIstatus(BizTypeDefinitionEnum.AdvertisStatus.INVALID.getCode());
                advertisService.updateBySelective(upAdvertis);
                AdvertisUpdate(merchantInfo.getScode());
                //向安卓端发送指令
                if(StringUtils.isNotBlank(regionVo.getScode())&&("WZRE0054".equals(regionVo.getScode())||"WZRE0055".equals(regionVo.getScode())||"WZRE0057".equals(regionVo.getScode()))){
                    // 封装参数
                    SendOperatingAdvertisingDto sendOperatingAdvertisingDto = new SendOperatingAdvertisingDto();
                    String deviceIds = deviceInfoService.selectDevice(merchantInfo.getId());
                    sendOperatingAdvertisingDto.setDeviceIds(deviceIds);
                    sendOperatingAdvertisingDto.setPosition(GrpParaUtil.getName("SP000148",regionVo.getScode()));
                    sendOperatingAdvertisingDto.setMerchantCode(merchantInfo.getScode());
                    logger.debug("开始调用发送广告服务");
                    // 创建Rest服务客户端
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.SEND_OPERATING_ADVERTISING);
                    invoke.setRequestObj(sendOperatingAdvertisingDto);
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    ResponseVo responseVo = (ResponseVo<String>) invoke.invoke();
                }
                logger.info("广告过期定时任务完成！商户编号：{}", merchantInfo.getScode());
            }
        } catch (Exception e) {
            logger.error("广告过期定时任务异常：{}", e);
        }
    }

    /**
     * 资讯过期定时任务 根据商户
     *
     * @param merchantInfo 商户信息
     */

    private void announcementExpireByMerchant(MerchantInfo merchantInfo) {
        logger.info("资讯过期定时任务开始：{}", merchantInfo);
        try {
            List<Announcement> announcementList = announcementService.selectNotExpiredAnnouncement(merchantInfo.getId());
            for (Announcement announcement : announcementList) {
                Announcement upAnnouncement = new Announcement();
                upAnnouncement.setId(announcement.getId());
                upAnnouncement.setIstatus(BizTypeDefinitionEnum.AnnouncementStatus.INVALID.getCode());
                announcementService.updateBySelective(upAnnouncement);
            }
            logger.info("资讯过期定时任务完成！商户编号：{}", merchantInfo.getScode());
        } catch (Exception e) {
            logger.error("资讯过期定时任务异常：{}", e);
        }

    }

    //更新缓存与RegionController下方法AdvertisUpdate保持一致
    private void AdvertisUpdate(final String merchantCode) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Region paramNav = new Region();
                    paramNav.setIdelFlag(0);
                    paramNav.setItype(10);
                    List<Region> regionList = regionService.selectByEntityWhere(paramNav);
                    logger.debug("运营区域数据==========" + regionList);
                    if (regionList != null && regionList.size() > 0) {
                        logger.debug("----运营位修改后更新广告Redis缓存开始");
                        iCached.remove(RedisConst.WZ_REGIO_ADVERTIS + merchantCode);
                        for (Region region : regionList) {
                            advertisService.updateByCached(region, merchantCode);
                        }
                        logger.debug("----运营位修改后更新Redis结束，缓存成功！，主KEY:" + RedisConst.WZ_REGIO + merchantCode);
                    }
                } catch (Exception e) {
                    logger.error("----运营位修改后更新Redis结束，缓存失败！！，错误信息：{}", e);
                    throw new ServiceException(e.getMessage());
                }
            }
        });
    }
}
