package com.cloud.cang.rec.cr.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.openapi.ModelUpdateDto;
import com.cloud.cang.openapi.OpenApiServicesDefine;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.dao.RecognitionServerDao;
import com.cloud.cang.rec.cr.dao.ServerModelDao;
import com.cloud.cang.rec.cr.domain.RecognitionServerDomain;
import com.cloud.cang.rec.cr.service.RecognitionServerService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

@Service
public class RecognitionServerServiceImpl extends GenericServiceImpl<RecognitionServer, String> implements
        RecognitionServerService {
    private static final Logger log = LoggerFactory.getLogger(RecognitionServerServiceImpl.class);

    @Autowired
    RecognitionServerDao recognitionServerDao;

    @Autowired
    ServerModelDao serverModelDao;

    @Override
    public GenericDao<RecognitionServer, String> getDao() {
        return recognitionServerDao;
    }

    /**
     * 查询列表
     *
     * @param page
     * @param recognitionServerDomain
     * @return
     */
    @Override
    public Page<RecognitionServer> selectPageByDomainWhere(Page<RecognitionServer> page, RecognitionServerDomain recognitionServerDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return recognitionServerDao.selectPageByDomainWhere(recognitionServerDomain);
    }

    /**
     * 新增
     *
     * @param recognitionServer
     * @return
     */
    @Override
    public void saveRecognitionServer(RecognitionServer recognitionServer) {
        recognitionServer.setScode(CoreUtils.newCode(EntityTables.CR_RECOGNITION_SERVER));
        recognitionServer.setIauditStatus(10);
        recognitionServer.setIdelFlag(0);
        recognitionServer.setTaddTime(DateUtils.getCurrentDateTime());
        recognitionServer.setTupdateTime(DateUtils.getCurrentDateTime());
        recognitionServer.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        recognitionServer.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        recognitionServerDao.insert(recognitionServer);
        //操作日志
        String logText = MessageFormat.format("新增开放平台识别服务器，识别服务器编号{0}", recognitionServer.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }

    /**
     * 修改
     *
     * @param recognitionServer
     * @return
     */
    @Override
    public void upRecognitionServer(RecognitionServer recognitionServer) {
        recognitionServer.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        recognitionServer.setTupdateTime(DateUtils.getCurrentDateTime());
        recognitionServerDao.updateByIdSelective(recognitionServer);
        //操作日志
        String logText = MessageFormat.format("修改开放平台识别服务器，识别服务器编号{0}", recognitionServer.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }

    /**
     * 审核
     *
     * @param recognitionServer
     * @return
     */
    @Override
    public void recognitionServerAudit(RecognitionServer recognitionServer) {
        recognitionServer.setTauditTime(DateUtils.getCurrentDateTime());
        recognitionServer.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        recognitionServerDao.updateByIdSelective(recognitionServer);
    }


    /**
     * 删除
     *
     * @param checkboxId
     */
    @Override
    public void deleteRecognitionServer(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                RecognitionServer recognitionServer = new RecognitionServer();
                recognitionServer.setId(id);
                recognitionServer.setIdelFlag(1);
                recognitionServerDao.updateByIdSelective(recognitionServer);
            }
        }
    }

    /**
     * 更新模型
     *
     * @param recognitionServer
     * @param
     * @return
     */
    @Override
    public ResponseVo saveUpModel(RecognitionServer recognitionServer, /*Integer iisRestartImmediately,*/BigDecimal fvisThresh)   {
        try {
            RecognitionServer oldTemp = this.selectByPrimaryKey(recognitionServer.getId());
            RecognitionServer temp = new RecognitionServer();
            temp.setId(recognitionServer.getId());
            temp.setSmodelCode(recognitionServer.getSmodelCode());
            temp.setSmodelId(recognitionServer.getSmodelId());
            temp.setTupdateTime(DateUtils.getCurrentDateTime());
            temp.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            recognitionServerDao.updateByIdSelective(temp);
            ServerModel serverModel = new ServerModel();
            serverModel.setFvisThresh(fvisThresh);
            serverModel.setId(temp.getSmodelId());
            serverModelDao.updateByIdSelective(serverModel);
      /*  if (iisRestartImmediately == 1) {*/
            //立刻重启服务器
            // 创建Rest服务客户端
            ModelUpdateDto modelUpdateDto = new ModelUpdateDto();
            modelUpdateDto.setHost(oldTemp.getSip());
            modelUpdateDto.setPort(oldTemp.getSport());
            modelUpdateDto.setOldModelCode(oldTemp.getSmodelCode());
            modelUpdateDto.setNewModelCode(temp.getSmodelCode());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OpenApiServicesDefine.MODEL_UPDATE);
            invoke.setRequestObj(modelUpdateDto);
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null == responseVo || !responseVo.isSuccess()) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新模型重启失败！");
            }
    /*    }*/
            //操作日志
            String logText = MessageFormat.format("识别服务器更新模型，服务器编号{0}", recognitionServer.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("开放平台识别服务器更新模型异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新模型失败！");
        }
    }

    /**
     * 重启识别服务器
     *
     * @param irangeServer
     * @param recognitionServerIds
     * @param recognitionServerCodes
     * @return
     */
    @Override
    public ResponseVo reboot(Integer irangeServer, String recognitionServerIds, String recognitionServerCodes) throws Exception {
        if (10 == irangeServer) {//全部重启
            RecognitionServer recognitionServer = new RecognitionServer();
            recognitionServer.setIauditStatus(10);
            recognitionServer.setIdelFlag(0);
            List<RecognitionServer> recognitionServerList = this.selectByEntityWhere(recognitionServer);
            if (null != recognitionServerList && !recognitionServerList.isEmpty()) {
                for (RecognitionServer temp : recognitionServerList) {
                    //立刻重启服务器
                    // 创建Rest服务客户端
                    ModelUpdateDto modelUpdateDto = new ModelUpdateDto();
                    modelUpdateDto.setHost(temp.getSip());
                    modelUpdateDto.setPort(temp.getSport());
                    modelUpdateDto.setNewModelCode(temp.getSmodelCode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OpenApiServicesDefine.MODEL_UPDATE);
                    invoke.setRequestObj(modelUpdateDto);
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                    if (null != responseVo && responseVo.isSuccess()) {
                        log.info("识别服务器重启成功：{}", temp.getScode());
                    }
                }
            }
        } else if (20 == irangeServer) {
            String[] strings = recognitionServerIds.split(",");
            if (null != strings && strings.length > 0) {
                for (int i = 0; i < strings.length; i++) {
                    RecognitionServer recognitionServer = this.selectByPrimaryKey(strings[i]);
                    //立刻重启服务器
                    // 创建Rest服务客户端
                    ModelUpdateDto modelUpdateDto = new ModelUpdateDto();
                    modelUpdateDto.setHost(recognitionServer.getSip());
                    modelUpdateDto.setPort(recognitionServer.getSport());
                    modelUpdateDto.setNewModelCode(recognitionServer.getSmodelCode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(OpenApiServicesDefine.MODEL_UPDATE);
                    invoke.setRequestObj(modelUpdateDto);
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                    if (null != responseVo && responseVo.isSuccess()) {
                        log.info("识别服务器重启成功：{}", recognitionServer.getScode());
                    }
                }
            }
        }
        return ResponseVo.getSuccessResponse();
    }

}