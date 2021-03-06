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
     * ????????????
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
     * ??????
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
        serverList.setIrunStatus(10);//????????????
        serverList.setTaddTime(DateUtils.getCurrentDateTime());
        serverList.setTupdateTime(DateUtils.getCurrentDateTime());
        serverList.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverList.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverListDao.insert(serverList);
        return serverList;
    }

    /**
     * ????????????
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
     * ??????????????????API?????????
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
     * ??????
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
     * ????????????
     *
     * @param gpuServerUpgrade
     * @return
     */
    @Override
    @Transactional
    public ResponseVo saveUpModel(GpuServerUpgrade gpuServerUpgrade) {
        //????????????
        try {
            if ("10".equals(gpuServerUpgrade.getTimeType())) {
                ServerModel serverModel = serverModelService.selectByCode(gpuServerUpgrade.getSmodelCode());
                if (null == serverModel) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
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
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
                }*/
                modelUpdateDto.setModelAddress(serverModel.getSmodelAddress());
                modelUpdateDto.setModelCode(serverModel.getScode());
                modelUpdateDto.setOperMan(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                modelUpdateDto.setUpdateRecodeCode(deviceUpgrade.getId());
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(VisionOperServicesDefine.MODEL_UPDATE);// ????????????
                // ??????????????????????????????????????????????????????????????????????????????
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                invoke.setRequestObj(modelUpdateDto); // post ??????
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                log.info("????????????????????????,?????????????????????{}", gpuServerUpgrade.getSmodelCode());
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
                timingTask.setSremark("gpu?????????????????????");
                timingTask.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                timingTask.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                timingTaskService.insert(timingTask);
                //????????????
                JobDto jobDto = new JobDto();
                jobDto.setJobName(timingTask.getScode());
                jobDto.setTime(DateUtils.convertToDateTime(gpuServerUpgrade.getDproduceDate()));
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(GpuServerServicesDefine.GPU_SERVER_MODEL_UPGRADE);// ????????????
                // ??????????????????????????????????????????????????????????????????????????????
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                invoke.setRequestObj(jobDto); // post ??????
                ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            }
        } catch (Exception e) {
            log.error("",e);
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * ????????????
     *
     * @param gpuServerUpgrade
     * @return
     */
    @Override
    public ResponseVo saveUpService(GpuServerUpgrade gpuServerUpgrade) {
        //????????????
        if ("0".equals(gpuServerUpgrade.getTimeType())) {

        } else {
            //????????????

        }
        return null;
    }

}