package com.cloud.cang.open.api.mq.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.interfaces.HandlerEnum;
import com.cloud.cang.core.rmq.interfaces.RmqMessageHandler;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.DingTalkUtils;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_ImgResult;
import com.cloud.cang.mq.message.Mq_Zlqf_ImgResult;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;
import com.cloud.cang.open.api.op.service.InterfaceAcceptService;
import com.cloud.cang.open.api.op.service.InterfaceAccountService;
import com.cloud.cang.open.api.op.service.TransferLogService;
import com.cloud.cang.open.sdk.exception.CloudCangException;
import com.cloud.cang.open.sdk.model.request.ImgRecognition;
import com.cloud.cang.open.sdk.model.response.GoodDetail;
import com.cloud.cang.open.sdk.model.response.ImgResultDetail;
import com.cloud.cang.open.sdk.util.BaseSignature;
import com.cloud.cang.open.sdk.utils.EncryptUtils;
import com.cloud.cang.open.sdk.utils.SortUtils;
import com.cloud.cang.openapi.CommonParam;
import com.cloud.cang.openapi.RecongitionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: 37cang-云平台
 * @description: 消息处理类：处理图像识别结果消息
 * @author: qzg
 * @create: 2019-11-15 11:23
 **/
@Component
public class ImgRecZlqfMessageHandler implements RmqMessageHandler<Mq_Zlqf_ImgResult> {
    private static final Logger logger = LoggerFactory.getLogger(ImgRecZlqfMessageHandler.class);
    @Autowired
    private InterfaceAccountService interfaceAccountService;
    @Autowired
    private InterfaceAcceptService interfaceAcceptService;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private ICached iCached;
    @Autowired
    RmqProducer rmqProducer;


    @Override
    public HandlerEnum handler(RmqMessage<Mq_Zlqf_ImgResult> rmqMessage) {
        Mq_Zlqf_ImgResult imgResult = rmqMessage.getMessage();
        try{
            // 业务处理
            this.processBiz(imgResult);
            return HandlerEnum.ACK;
        } catch (Exception e){
            e.printStackTrace();
            DingTalkUtils.sendText("第三方open-api处理识别结果失败, 消息ID:"+rmqMessage.getId());
        }
        return HandlerEnum.ACK_BIZ_FAIL;
    }


    @Override
    public boolean matchHandler(String queueName) {
        if(queueName.contains(QueueEnum.IMG_RESULT_DARKNET.getName())) {
            return true;
        }
        return false;
    }


    /**
     * @param imgResult
     */
    private void processBiz(Mq_Zlqf_ImgResult imgResult)throws Exception{
        try {
            String backBody = this.buildBackBody(imgResult);
            logger.info("===========第三方(中林清风)接口同步识别结果处理成功==============");
            iCached.put(com.cloud.cang.openapi.APIConstant.ZLQF_RECOGNITION_RESULT + imgResult.getImgCode(), backBody,60*60*2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  构建backBody
     * @return
     */
    private String buildBackBody(Mq_Zlqf_ImgResult imgResult) throws Exception {
        Map tempMap = MapUtil.builder()
                .put("status", "200")
                .put("msgCode", SubCodeEnum.SUCCESS.getCode())
                .put("msgContent", SubCodeEnum.SUCCESS.getCode())
                .put("imgCode", imgResult.getImgCode())
                .put("imgResultDetail", imgResult.getGoods())
                .map();
        if( imgResult.getStatus() != 200){
            tempMap.put("status", "-100");
            tempMap.put("msgCode", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getCode());
            tempMap.put("msgContent", SubCodeEnum.RECOGNITION_SERVER_RESULT_ERROR.getReturnMsg());
            tempMap.put("imgCode", imgResult.getImgCode());
            tempMap.put("imgResultDetail", null);
        }
        // Map排序
        tempMap = SortUtils.sortMapByKey(tempMap);
        String backBody = JSONObject.toJSONString(MapUtil.builder().put("body",tempMap).map());
        return backBody;
    }


}

