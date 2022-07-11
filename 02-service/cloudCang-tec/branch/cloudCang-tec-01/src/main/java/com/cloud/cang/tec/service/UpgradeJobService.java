package com.cloud.cang.tec.service;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.model.sb.TimingTask;
import com.cloud.cang.recongition.ModelUpdateDto;
import com.cloud.cang.recongition.VisionOperServicesDefine;
import com.cloud.cang.sb.DeviceDto;
import com.cloud.cang.sb.DeviceServicesDefine;
import com.cloud.cang.server.GpuServerUpgrade;
import com.cloud.cang.tec.common.TecConstants;
import com.cloud.cang.tec.cr.service.ServerModelService;
import com.cloud.cang.tec.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.tec.sb.service.DeviceUpgradeService;
import com.cloud.cang.tec.sb.service.TimingTaskService;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 严凌峰
 * @version 1.0
 */
@Service(value = "upgradeJobService")
public class UpgradeJobService {

    @Autowired
    private JobTemplate jobTemplate;
    @Autowired
    private TimingTaskService timingTaskService;
    @Autowired
    private DeviceUpgradeService deviceUpgradeService;
    @Autowired
    private DeviceUpgradeDetailsService deviceUpgradeDetailsService;
    @Autowired
    private ServerModelService serverModelService;


    private static final Logger logger = LoggerFactory.getLogger(UpgradeJobService.class);


    public void upModelJob(final String orderCode) {
        logger.info("模型升级定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    String msg = saveUpModelJob(orderCode);
                    return msg;
                } catch (Exception e) {
                    logger.error("模型升级定时任务失败", e);
                    return "模型升级定时任务失败";
                }
            }
        }, TecConstants.GPU_SERVER_UPGRADE, true);
    }

    public String saveUpModelJob(String code) {
        GpuServerUpgrade gpuServerUpgrade = null;
        try {
            TimingTask timingTask = timingTaskService.selectByPrimaryKey(code);
            gpuServerUpgrade = JSON.parseObject(timingTask.getStaskContent(), GpuServerUpgrade.class);
            String smodelCode = JSON.parseObject(timingTask.getStaskObject(), String.class);
            ServerModel serverModel = serverModelService.selectByCode(gpuServerUpgrade.getSmodelCode());
            if (null == serverModel) {
                return ("识别服务模型不存在");
            }
            DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
            deviceUpgrade.setItype(70);
            deviceUpgrade.setIdeviceType(Integer.valueOf(gpuServerUpgrade.getIrangeDevice()));
            deviceUpgrade.setSresourcesUrl(gpuServerUpgrade.getUrl());
            deviceUpgrade.setIversion(Integer.valueOf(gpuServerUpgrade.getVersion()));
            deviceUpgrade.setIactionType(Integer.valueOf(gpuServerUpgrade.getTimeType()));
            deviceUpgrade.setIdelFlag(0);
            deviceUpgrade.setTaddTime(DateUtils.getCurrentDateTime());
            deviceUpgrade.setTupdateTime(DateUtils.getCurrentDateTime());
        /*deviceUpgrade.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        deviceUpgrade.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());*/
            deviceUpgradeService.insert(deviceUpgrade);
            DeviceUpgradeDetails deviceUpgradeDetails = new DeviceUpgradeDetails();
            deviceUpgradeDetails.setSmainId(deviceUpgrade.getId());
            deviceUpgradeDetails.setSdeviceId(serverModel.getId());
            deviceUpgradeDetails.setSdeviceAddress(serverModel.getSmodelAddress());
            deviceUpgradeDetails.setSdeviceCode(serverModel.getScode());
            deviceUpgradeDetails.setIstatus(10);
            deviceUpgradeDetailsService.insert(deviceUpgradeDetails);
            ModelUpdateDto modelUpdateDto = new ModelUpdateDto();
            modelUpdateDto.setAddressType(10);
            List<String> result = null;
            if (StringUtils.isNotBlank(gpuServerUpgrade.getDeviceCodes())) {
                result = Arrays.asList(gpuServerUpgrade.getDeviceCodes().split(","));
            }
            modelUpdateDto.setGpuCodes(result);

            modelUpdateDto.setModelAddress(serverModel.getSmodelAddress());
            modelUpdateDto.setModelCode(serverModel.getScode());
            //modelUpdateDto.setOperMan(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            modelUpdateDto.setUpdateRecodeCode(deviceUpgrade.getId());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(VisionOperServicesDefine.MODEL_UPDATE);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            invoke.setRequestObj(modelUpdateDto); // post 参数
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            logger.info("模型定时升级完成,更新模型编号：{}", gpuServerUpgrade.getSmodelCode());

            return "成功";
        } catch (Exception e) {
            logger.error("模型定时升级异常,更新模型编号：{}", gpuServerUpgrade.getSmodelCode());
            return "模型定时升级失败！";
        }
    }

    /**
     * mgr后台给售货机设备发送升级消息
     * @param taskCode
     */
    public void mgrDeviceUpgrade (final String taskCode) {
        logger.info("mgr发送定时升级消息，定时任务开始执行...");
        jobTemplate.excuteJob(new JobCallBack() {
            @Override
            public String doInJob() throws Exception {
                try {
                    String msg = mgrSendDeviceTimingUpgradeMsg(taskCode);
                    return msg;
                } catch (Exception e) {
                    logger.error("mgr发送定时升级消息，定时任务失败", e);
                    return "mgr发送定时升级消息，定时任务失败";
                }
            }
        }, TecConstants.MGR_SEND_UPGRADE_MSG, true);
    }

    private String mgrSendDeviceTimingUpgradeMsg(String orderCode) {
        DeviceUpgrade deviceUpgrade = new DeviceUpgrade();
        try {
            TimingTask timingTask = timingTaskService.selectByPrimaryKey(orderCode);
            deviceUpgrade = JSON.parseObject(timingTask.getStaskContent(), DeviceUpgrade.class);
            String[] deviceIds = JSON.parseObject(timingTask.getStaskObject(), String[].class);

            //调用长连接服务前，对DTO对象进行赋值
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setId(StringUtils.join(deviceIds, ","));//设备ID数组使用“,”拼接成字符串
            if (deviceUpgrade.getItype() == 10) {   // 10=视觉服务升级 20=视觉库升级 30=客户端APK升级
                deviceDto.setFunction(NettyConst.VR_SERVER_UPGRADE);
            } else if (deviceUpgrade.getItype() == 20) {
                deviceDto.setFunction(NettyConst.UPDATE_FEATURE_LIBRARY);
            } else if (deviceUpgrade.getItype() == 30) {
                deviceDto.setFunction(NettyConst.SYSTEMUPGRADE);
            }
            deviceDto.setUserId(deviceUpgrade.getSupdateUser());
            deviceDto.setOperateParam(deviceUpgrade.getSresourcesUrl() + "-//-" + deviceUpgrade.getIversion() + "-//-updateNow-//-" + deviceUpgrade.getId());
            ResponseVo<String> responseVo = null;
            //开始调用长连接服务接口，调用netty提供的接口，发送消息给对应的设备
            logger.debug("开始调用长连接服务接口" + DeviceServicesDefine.OPERATE_DEVICE);
            try {
                // 创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(DeviceServicesDefine.OPERATE_DEVICE);
                invoke.setRequestObj(deviceDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                responseVo = (ResponseVo<String>) invoke.invoke();
                if (responseVo.isSuccess()) {
                    logger.debug("调用长连接服务接口成功，执行方法名：{} ，方法参数：{}", deviceDto.getFunction(), deviceDto.getOperateParam());
                    return responseVo.getData();
                } else {
                    throw new ServiceException("调用向设备推送消息服务失败");
                }
            } catch (Exception e) {
                logger.error("mgr后台给售货机设备发送升级消息异常：{}",e);
            }
            return "failed";
        } catch (Exception e) {
            logger.error("mgr后台发送定时升级消息出现异常：{}", e);
            return "mgr后台发送定时升级消息失败！";
        }
    }



}
