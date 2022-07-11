package com.cloud.cang.rec.cr.service.impl;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.model.sb.DeviceUpgradeDetails;
import com.cloud.cang.model.sb.TimingTask;
import com.cloud.cang.rec.cr.domain.ServerListDomain;
import com.cloud.cang.rec.cr.service.ServerModelService;
import com.cloud.cang.rec.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.rec.sb.service.DeviceUpgradeService;
import com.cloud.cang.rec.sb.service.TimingTaskService;
import com.cloud.cang.recongition.ModelUpdateDto;
import com.cloud.cang.recongition.VisionOperServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.server.GpuServerServicesDefine;
import com.cloud.cang.server.GpuServerUpgrade;
import com.cloud.cang.server.JobDto;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rec.cr.dao.ServerListDao;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.rec.cr.service.ServerListService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServerListServiceImpl extends GenericServiceImpl<ServerList, String> implements
        ServerListService {

    private static final Logger log = LoggerFactory.getLogger(ServerListServiceImpl.class);

    @Autowired
    ServerListDao serverListDao;
    @Autowired
    TimingTaskService timingTaskService;
    @Autowired
    ServerModelService serverModelService;
    @Autowired
    DeviceUpgradeService deviceUpgradeService;
    @Autowired
    DeviceUpgradeDetailsService deviceUpgradeDetailsService;


    @Override
    public GenericDao<ServerList, String> getDao() {
        return serverListDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param serverListDomain
     * @return
     */
    @Override
    public Page<ServerList> selectPageByDomainWhere(Page<ServerList> page, ServerListDomain serverListDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return serverListDao.selectPageByDomainWhere(serverListDomain);
    }

    /**
     * 新增
     *
     * @param serverList
     * @return
     */
    @Override
    public ServerList saveServerList(ServerList serverList) {
        serverList.setScode(CoreUtils.newCode(EntityTables.CR_SERVER_LIST));
        serverList.setIstatus(10);
        serverList.setIauditStatus(10);
        serverList.setIdelFlag(0);
        serverList.setIrunStatus(10);//默认离线
        serverList.setTaddTime(DateUtils.getCurrentDateTime());
        serverList.setTupdateTime(DateUtils.getCurrentDateTime());
        serverList.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverList.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverListDao.insert(serverList);
        return serverList;
    }

    /**
     * 执行修改
     *
     * @param serverList
     */
    @Override
    public void upServerList(ServerList serverList) {
        serverList.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverList.setTupdateTime(DateUtils.getCurrentDateTime());
        serverListDao.updateByIdSelective(serverList);
    }

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                ServerList serverList = new ServerList();
                serverList.setId(id);
                serverList.setIdelFlag(1);
                serverListDao.updateByIdSelective(serverList);
            }
        }
    }

    /**
     * 审核
     *
     * @param serverList
     * @return
     */
    @Override
    public void serverListAudit(ServerList serverList) {
        if(30 == serverList.getIauditStatus()){
            serverList.setIstatus(60);
        }else{
            serverList.setIstatus(50);
        }
        serverList.setTauditTime(DateUtils.getCurrentDateTime());
        serverList.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverListDao.updateByIdSelective(serverList);
    }

    /**
     * 模型升级
     *
     * @param gpuServerUpgrade
     * @return
     */
    @Override
    @Transactional
    public ResponseVo saveUpModel(GpuServerUpgrade gpuServerUpgrade) {
        //立即更新
        try {
            if ("10".equals(gpuServerUpgrade.getTimeType())) {
                ServerModel serverModel = serverModelService.selectByCode(gpuServerUpgrade.getSmodelCode());
                if (null == serverModel) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("识别服务模型不存在");
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
                deviceUpgrade.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                deviceUpgrade.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
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
             /*   ServerModel serverModel = serverModelService.selectByCode(gpuServerUpgrade.getSmodelCode());
                if (null == serverModel) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("识别服务模型不存在");
                }*/
                modelUpdateDto.setModelAddress(serverModel.getSmodelAddress());
                modelUpdateDto.setModelCode(serverModel.getScode());
                modelUpdateDto.setOperMan(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                modelUpdateDto.setUpdateRecodeCode(deviceUpgrade.getId());
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(VisionOperServicesDefine.MODEL_UPDATE);// 服务名称
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                invoke.setRequestObj(modelUpdateDto); // post 参数
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                log.info("模型立即升级完成,更新模型编号：{}", gpuServerUpgrade.getSmodelCode());
            } else {
                TimingTask timingTask = new TimingTask();
                timingTask.setScode(CoreUtils.newCode(EntityTables.SB_TIMING_TASK));
                timingTask.setIdelFlag(0);
                timingTask.setStaskObject(JSON.toJSONString(gpuServerUpgrade.getSmodelCode()));
                gpuServerUpgrade.setSmodelCode("");
                timingTask.setStaskContent(JSON.toJSONString(gpuServerUpgrade));
                timingTask.setItype(20);
                timingTask.setIstatus(10);
                timingTask.setTaddTime(DateUtils.getCurrentDateTime());
                timingTask.setTupdateTime(DateUtils.getCurrentDateTime());
                timingTask.setSremark("gpu服务器模型升级");
                timingTask.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                timingTask.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                timingTaskService.insert(timingTask);
                //定时更新
                JobDto jobDto = new JobDto();
                jobDto.setJobName(timingTask.getScode());
                jobDto.setTime(DateUtils.convertToDateTime(gpuServerUpgrade.getDproduceDate()));
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(GpuServerServicesDefine.GPU_SERVER_MODEL_UPGRADE);// 服务名称
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                invoke.setRequestObj(jobDto); // post 参数
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 服务升级
     *
     * @param gpuServerUpgrade
     * @return
     */
    @Override
    public ResponseVo saveUpService(GpuServerUpgrade gpuServerUpgrade) {
        //立即更新
        if ("0".equals(gpuServerUpgrade.getTimeType())) {

        } else {
            //定时更新

        }
        return null;
    }

}