package com.cloud.cang.vis;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.dispatcher.utils.DispatcherUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.vis.common.VisContant;
import com.cloud.cang.vis.cr.service.RecognitionServerService;
import com.cloud.cang.vis.cr.service.ServerModelService;
import com.cloud.cang.vis.detector.DetectorLibrary;
import com.cloud.cang.vis.model.InitServerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.util.List;

/**
 * @program: 37cang-云平台
 * @description: 初始化服务-加载模型
 * @author: qzg
 * @create: 2019-11-21 09:38
 **/
@Component
public class InitServer implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(InitServer.class);
    @Autowired
    RecognitionServerService recognitionServerService;
    @Autowired
    ServerModelService serverModelService;

    // 初始化视觉服务状态
    public  int status = -1;
    // 识别模型编号
    public  String modelCode = "";
    // ip
    public  String host = "";
    // port
    public  String port = "";

    @Override
    public void afterPropertiesSet() throws Exception {
        loadVisMoel(false);
    }

    public void loadVisMoel(boolean isReload){
        // 获取host
        try {
            host = StrUtil.isEmpty(host) ? DispatcherUtils.getIpByHostName(VisContant.VIS_IP) : host;
            if(StrUtil.isEmpty(host)){
                throw new ServiceException("获取本地ip地址失败!");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            throw new ServiceException("获取本地ip地址失败!");
        }

        // 获取port
        port = StrUtil.isEmpty(port)  ? CoreUtils.getPortByMBean() : port;
        if(StrUtil.isEmpty(port)){
            throw new ServiceException("获取本地port地址失败!");
        }

        logger.info("开始-初始化模型, ip:{}, port:{}",host,port);
        // 校验识别服务是否存在
        RecognitionServer entity = new RecognitionServer();
        entity.setIdelFlag(0);
        entity.setIauditStatus(20);
        entity.setSip(host);
        entity.setSport(port);
        List<RecognitionServer> servers = recognitionServerService.selectByEntityWhere(entity);
        String log1 = StrFormatter.format("初始化模型失败 , 表cr_recognition_server中不存在ip:{} , port:{}",host,port);
        if(CollUtil.isEmpty(servers)) throw new ServiceException(log1);

        // 校验服务模型是否存在
        RecognitionServer server = servers.get(0);
        ServerModel modeTemp = new ServerModel();
        modeTemp.setScode(StrUtil.cleanBlank(server.getSmodelCode()));
        modeTemp.setIstatus(20);
        modeTemp.setIdelFlag(0);
        List<ServerModel> models = serverModelService.selectByEntityWhere(modeTemp);
        String log2 = StrFormatter.format("初始化模型失败 , 表cr_server_model中不存在模型编号scode:{} ",server.getSmodelCode());
        if(CollUtil.isEmpty(models)) throw new ServiceException(log2);
        ServerModel model = models.get(0);
        modelCode = StrUtil.cleanBlank(model.getScode());

        // 加载模型
        String smodelAddress = StrUtil.cleanBlank(model.getSmodelAddress());
        if(!smodelAddress.startsWith(StrUtil.SLASH)){
            smodelAddress = StrUtil.SLASH + smodelAddress;
        }
        if(!smodelAddress.endsWith(StrUtil.SLASH)){
            smodelAddress = smodelAddress + StrUtil.SLASH;
        }
        DetectorLibrary instance = new DetectorLibrary();
        InitServerDto initServerDto = InitServerDto.builder()
                            .modelPath(smodelAddress + VisContant.MODE_FIEL)
                            .nameCfg(smodelAddress + VisContant.NAMES_FILE)
                            .classes(model.getIcategoryNum())
                            .istiny(model.getIstiny())
                            .thresh(model.getFvisThresh()!=null?model.getFvisThresh().floatValue():0.8f)
                            .build();
        logger.info("初始化模型: {}",initServerDto);
        try {
            if(isReload){
                status = instance.reloadRecogitionServer(JSONUtil.toJsonStr(initServerDto));
            }else {
                status = instance.loadRecogitionServer(JSONUtil.toJsonStr(initServerDto));
            }
        } catch (Exception e) {
            logger.info("初始化模型失败: {}",e);
        }
        if(status != 200){
            DingTalkUtils.sendText(StrFormatter.format("模型初始化失败,vis服务{}:{},模型编号:{}",host,port,modelCode));
        }
        logger.info("初始化模型: loadRecogitionServer返回状态, status:{}",status);
        logger.info("完成-初始化模型, ip:{}, port:{}",host,port);
    }
}
