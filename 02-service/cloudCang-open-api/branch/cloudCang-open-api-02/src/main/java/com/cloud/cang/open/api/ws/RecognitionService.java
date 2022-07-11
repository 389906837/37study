package com.cloud.cang.open.api.ws;


import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.rmq.RmqProducer;
import com.cloud.cang.core.rmq.message.RmqMessage;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.mq.QueueEnum;
import com.cloud.cang.mq.message.Mq_Zlqf_ImgRec;
import com.cloud.cang.open.api.cr.service.RecognitionServerService;
import com.cloud.cang.openapi.APIConstant;
import com.cloud.cang.open.api.common.SubCodeEnum;

import com.cloud.cang.openapi.ImageParamsDto;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 中林清风 识别服务
 */
@RestController
@RequestMapping("/vis")
@RegisterRestResource
public class RecognitionService {
    private final static Logger logger = LoggerFactory.getLogger(RecognitionService.class);
    @Autowired
    RmqProducer rmqProducer;
    @Autowired
    private ICached iCached;
    @Autowired
    RecognitionServerService recognitionServerService;



    /**
     * 中林清风 图片识别接口
     * @param
     * @return
     */
    @RequestMapping(value = "/detectImage")
    public void recognition(HttpServletRequest request, HttpServletResponse response) {

        SubCodeEnum subCodeEnum = SubCodeEnum.SYSTEM_ERROR;
        try {
            //验证参数，保存图片
            long startTime = System.currentTimeMillis();
            ImageParamsDto imageParams = verifyParams(request);
            long verifyTime = System.currentTimeMillis();
            logger.info("第三方接口验证参数，保存图片耗时：{}ms", (verifyTime - startTime));

            //发送mq
            this.sendMQMessageToVis(imageParams);
            long sendMqTime = System.currentTimeMillis();
            logger.info("第三方接口发送mq耗时：{}ms", (sendMqTime - verifyTime));

            //识别返回
            waitRecongition(response, imageParams.getImgCode());
            long detectTime = System.currentTimeMillis();
            logger.info("第三方接口识别图片耗时：{}ms", (detectTime - sendMqTime));


        } catch (ServiceException e) {
            logger.error("第三方接口参数验证异常, 错误编码: {}", e.getMessage());
            subCodeEnum = SubCodeEnum.getSubCodeEnum(e.getMessage());
            if (null == subCodeEnum) {
                subCodeEnum = SubCodeEnum.UNKNOWN_ERROR;
            }
            // 错误信息返回客户端
            sendErrorResponse("-200", subCodeEnum, response);

        } catch (Exception e1) {
            logger.error("第三方接口处理异常：{}", e1);
            sendErrorResponse("-500", subCodeEnum, response);
        }

    }

    /**
     * 发送mq
     * @param imageParams
     */
    private void sendMQMessageToVis(ImageParamsDto imageParams){
        Mq_Zlqf_ImgRec  mq_imgRec = Mq_Zlqf_ImgRec.builder()
                .imgCode(imageParams.getImgCode())
                .imgPath(imageParams.getImgPath())
                .imgFormat(imageParams.getImgFormat())
                .responseType(Integer.valueOf(imageParams.getRequestType()))
                .build();
        RmqMessage<Mq_Zlqf_ImgRec> message = RmqMessage.<Mq_Zlqf_ImgRec>builder()
                .queueName(QueueEnum.IMG_MODEL_DARKNET.getName() + imageParams.getModelCode())
                .message(mq_imgRec)
                .flagDB(false)
                .build();
        rmqProducer.sendMessage(message);
    }


    /**
     * 验证参数，保存图片
     * @param request
     * @return
     */
    public ImageParamsDto verifyParams(HttpServletRequest request){
        String modelCode = request.getParameter("modelCode");
        String imgCode = request.getParameter("imgCode");
        String imgBase64 = request.getParameter("imgBase64");
        String imgFormat = request.getParameter("imgFormat");
        String requestType = request.getParameter("requestType");
        logger.info("第三方识别图片请求参数[modelCode：{},imgCode：{},imgFormat:{}]",modelCode, imgCode, imgFormat);
        if (modelCode == null || StringUtils.isEmpty(modelCode)){
            throw new ServiceException("REQUEST-PARAMETER-ERROR");
        }
        // 验证模型
        RecognitionServer entity = new RecognitionServer();
        entity.setIdelFlag(0);
        entity.setIauditStatus(20);
        entity.setSmodelCode(modelCode);
        List<RecognitionServer> servers = recognitionServerService.selectByEntityWhere(entity);
        if(CollUtil.isEmpty(servers)) {
            throw new ServiceException("MODEL-NOT-EXIST");
        }

        if (imgBase64 == null || StringUtils.isEmpty(imgBase64)){
            throw new ServiceException("NO-DATA");
        }
        if (imgCode == null || StringUtils.isEmpty(imgCode)){
            throw new ServiceException("REQUEST-PARAMETER-ERROR");
        }
        if (imgFormat == null || StringUtils.isEmpty(imgFormat)){
            throw new ServiceException("REQUEST-PARAMETER-ERROR");
        }
        if (!imgFormat.toLowerCase().equals("jpg") && !imgFormat.toLowerCase().equals("png")){
            throw new ServiceException("INVALID_PARAMETER_IMAGE_FORMAT_ERROR");
//            SubCodeEnum subCodeEnum = SubCodeEnum.INVALID_PARAMETER_IMAGE_FORMAT_ERROR;
//            sendErrorResponse("-400", subCodeEnum, response);
        }
        if (requestType == null || StringUtils.isEmpty(requestType)){
            throw new ServiceException("REQUEST-PARAMETER-ERROR");
        }
        if (!requestType.equals("10") && !requestType.equals("20")){
            throw new ServiceException("REQUEST-PARAMETER-ERROR");
        }
        //保存图片
        String imgPath = saveImage(imgCode, imgBase64, imgFormat);

        ImageParamsDto imageParams = ImageParamsDto.builder()
                                    .modelCode(modelCode)
                                    .imgCode(imgCode)
                                    .imgPath(imgPath)
                                    .imgFormat(imgFormat)
                                    .requestType(requestType)
                                    .imgBase64(null)
                                    .build();
        return imageParams;
    }


    /**
     * 保存待识别图片
     * @param
     */
    public String saveImage(String imgCode, String imgBase64, String imgFormat){
        String filePathType = "linux";
        String prefix = EvnUtils.getValue("upload.image.path.prefix");//服务器路径前缀
        String folder = "zlqf_imgs";
        String imgPath = ""; // 图片保存全路径
        String fileName = imgCode + "." + imgFormat; //图片名称
        String dateStr = DateUtils.dateParseShortString(new Date()); //日期
        if (filePathType.equals("windows")) {
            imgPath = prefix + "\\" + folder + "\\" + dateStr + "\\" + fileName;
        } else {//linux 直接保存到nfs文件共享目录
            imgPath = prefix + "/" + folder +  "/" + dateStr + "/" + fileName;
        }
        if (base64StrToImage(imgBase64,imgPath)){
            logger.info("第三方图片保存路径：{}", imgPath);
            return imgPath;
        }else {
            logger.error("第三方图片保存异常！");
            throw new ServiceException("RECOGNITION-SERVER-ERROR");
        }
    }

    /**
     * base64编码字符串转换为图片
     * @param imgStr base64编码字符串
     * @param path 图片路径
     * @return
     */
    public static boolean base64StrToImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 等待返回结果
     * @param response
     * @param imgCode
     */
    private void waitRecongition(HttpServletResponse response, String imgCode) {
        //新建线程等待返回
       /* ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {*/
        int time = 0;
        boolean flag = true;
        String encryptBackBody = "";
        int timeOut = 300;
        do {
            try {
                encryptBackBody = (String) iCached.get(APIConstant.ZLQF_RECOGNITION_RESULT + imgCode);
                if (StringUtil.isNotBlank(encryptBackBody) || time >= timeOut) {
                    flag = false;
                    //删除同步方法识别结果
                    iCached.remove(com.cloud.cang.openapi.APIConstant.ZLQF_RECOGNITION_RESULT + imgCode);
                    break;
                }
                time++;
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (flag);
        try {
            if (StringUtil.isNotBlank(encryptBackBody)) {
                response.getWriter().write(encryptBackBody);
            } else {
                SubCodeEnum subCodeEnum = SubCodeEnum.RECOGNITION_SERVER_ERROR;
                // 错误信息返回客户端
                sendErrorResponse("-500", subCodeEnum, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
          /*  }
        });*/
    }


    /**
     * 发送返回错误消息
     *
     * @param response
     * @throws Exception
     */
    protected void sendErrorResponse(String status, SubCodeEnum subCodeEnum,
                                    HttpServletResponse response) {

        try {
            Map<String, Object> backMap = new HashMap<String, Object>();
            Map<String, Object> tempMap = new HashMap<String, Object>();

            tempMap.put("status", status);
            tempMap.put("msgCode", subCodeEnum.getCode());
            tempMap.put("msgContent", subCodeEnum.getReturnMsg());
//            tempMap.put("imgCode", imgResult.getImgCode());
//            tempMap.put("imgResultDetail", null);

            backMap.put("body", tempMap);
            String backStr = JSONObject.toJSONString(backMap);
            logger.info("错误返回信息：{}", backStr);
            response.getWriter().write(backStr);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write("failure");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


}
