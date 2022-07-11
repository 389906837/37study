package com.cloud.cang.tec.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.concurrent.TaskAction;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.sb.DeviceServicesDefine;
import com.cloud.cang.sb.InventoryDto;
import com.cloud.cang.sb.OfflineDeviceDto;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.sh.service.MerchantInfoService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备定时任务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service(value = "deviceTimerService")
public class DeviceTimerService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceTimerService.class);

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 设备在线扫描定时任务
     */
    public void deviceOnlineScanJob() {
        logger.info("设备在线扫描定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(10);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("设备在线扫描定时任务失败", e);
                    return "设备在线扫描定时任务失败";
                }
            }

        }, TecConstants.DEVICE_ONLINE_SCAN_TASK, true);
    }

    /**
     * 设备盘货定时任务
     */
    public void deviceInventoryJob() {
        logger.info("设备盘货定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    return pageOperByMerchant(20);
                } catch (ServiceException se) {
                    return se.getMessage();
                } catch (Exception e) {
                    logger.error("设备盘货定时任务失败", e);
                    return "设备盘货定时任务失败";
                }
            }

        }, TecConstants.DEVICE_INVENTORY_TASK, true);
    }

    /**
     * 商户分页操作
     *
     * @param type 定时任务类型 10 设备在线扫描  20 设备盘货
     * @return
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
                msg = "设备在线扫描定时任务成功";
            } else if (20 == type.intValue()) {
                msg = "设备盘货定时任务成功";
            }
            return msg;
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
                            if (10 == type.intValue()) {
                                deviceOnlineScanByMerchant(merchantInfo);
                            } else if (20 == type.intValue()) {
                                deviceInventoryByMerchant(merchantInfo);
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
            msg = "设备在线扫描定时任务成功";
        } else if (20 == type.intValue()) {
            msg = "设备盘货定时任务成功";
        }
        return msg;
    }

    /**
     * 设备在线扫描 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void deviceOnlineScanByMerchant(MerchantInfo merchantInfo) {
        logger.info("设备在线扫描开始:商户Code=={}", merchantInfo.getScode());
        logger.debug("准备调用" + DeviceServicesDefine.GET_OFFLINE_DEVICE + "后台接口");
        OfflineDeviceDto offlineDeviceDto = new OfflineDeviceDto();
        offlineDeviceDto.setMerchantId(merchantInfo.getId());
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.GET_OFFLINE_DEVICE);
            invoke.setRequestObj(offlineDeviceDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo>() {
            });
            logger.debug("开始调用" + DeviceServicesDefine.GET_OFFLINE_DEVICE + "后台接口，参数：{}", offlineDeviceDto);
            ResponseVo responseVo = (ResponseVo) invoke.invoke();
        } catch (Exception e) {
            logger.error("设备在线扫描异常：{}", e);
            /*throw new ServiceException("设备在线扫描异常!");*/
        }
    }

    /**
     * 设备盘货 根据商户
     *
     * @param merchantInfo 商户信息
     */
    private void deviceInventoryByMerchant(MerchantInfo merchantInfo) {
        logger.info("设备盘货开始:商户Code=={}", merchantInfo.getScode());
        logger.debug("准备调用" + DeviceServicesDefine.GET_INVENTORY_RESULT + "后台接口");
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setMerchantId(merchantInfo.getId());
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.GET_INVENTORY_RESULT);
            invoke.setRequestObj(inventoryDto); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            logger.debug("开始调用" + DeviceServicesDefine.GET_INVENTORY_RESULT + "接口，参数：{}", inventoryDto);
            ResponseVo responseVo = (ResponseVo<String>) invoke.invoke();
        } catch (Exception e) {
            logger.error("设备盘货异常：{}", e);
          /*  throw new ServiceException("设备盘货异常!");*/
        }
    }
}
