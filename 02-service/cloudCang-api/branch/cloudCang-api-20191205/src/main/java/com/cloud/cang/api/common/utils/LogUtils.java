package com.cloud.cang.api.common.utils;

import com.cloud.cang.api.fr.service.FaceOperLogService;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.netty.vo.ClientVo;
import com.cloud.cang.api.sb.service.AiInfoService;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceMalfunctionRecordService;
import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.utils.AiFaceUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.message.InnerMessageDto;
import com.cloud.cang.message.MessageServicesDefine;
import com.cloud.cang.model.fr.FaceOperLog;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceMalfunctionRecord;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * api设备日志工具类
 *
 * @author zengzexiong
 * @version 1.0
 */
public class LogUtils {

    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    /**
     * 购物
     */
    public static final int SHOPPING = 10;
    /**
     * 补货
     */
    public static final int REPLENISHMENT = 20;
    /**
     * 游客
     */
    public static final int TOURIST = 30;


    /**
     * 长连接异常
     */
    public static final int TCP_ERROR = 10;
    /**
     * 短连接异常
     */
    public static final int HTTP_ERROR = 20;
    /**
     * 手动异常
     */
    public static final int OPERATE_ERROR = 30;

    static FaceOperLogService faceOperLogService;                           // 人脸识别操作日志
    static DeviceOperService deviceOperService;
    static DeviceMalfunctionRecordService deviceMalfunctionRecordService;
    static DeviceInfoService deviceInfoService;
    static MemberInfoService memberInfoService;
    static AiInfoService aiInfoService;
    static ICached iCached;
//    static MerchantInfoService merchantInfoService;

    private static FaceOperLogService getFaceOperLogService() {
        if (faceOperLogService == null) {
            faceOperLogService = SpringContext.getBean(FaceOperLogService.class);
        }
        return faceOperLogService;
    }

    private static MemberInfoService getMemberInfoService() {
        if (memberInfoService == null) {
            memberInfoService = SpringContext.getBean(MemberInfoService.class);
        }
        return memberInfoService;
    }

    private static AiInfoService getAiInfoService() {
        if (aiInfoService == null) {
            aiInfoService = SpringContext.getBean(AiInfoService.class);
        }
        return aiInfoService;
    }

    private static DeviceOperService getDeviceOperService() {
        if (deviceOperService == null) {
            deviceOperService = SpringContext.getBean(DeviceOperService.class);
        }
        return deviceOperService;
    }

    private static DeviceMalfunctionRecordService getDeviceMalfunctionRecordService() {
        if (deviceMalfunctionRecordService == null) {
            deviceMalfunctionRecordService = SpringContext.getBean(DeviceMalfunctionRecordService.class);
        }
        return deviceMalfunctionRecordService;
    }

    private static DeviceInfoService getDeviceInfoService() {
        if (deviceInfoService == null) {
            deviceInfoService = SpringContext.getBean(DeviceInfoService.class);
        }
        return deviceInfoService;
    }

//    private static MerchantInfoService getMerchantInfoService() {
//        if (merchantInfoService == null) {
//            merchantInfoService = SpringContext.getBean(MerchantInfoService.class);
//        }
//        return merchantInfoService;
//    }

    private static ICached getiCached() {
        if (iCached == null) {
            iCached = SpringContext.getBean(ICached.class);
        }
        return iCached;
    }

    /**
     * 设备操作日志--记录开门时间
     *
     * @param deviceCode   设备编号
     * @param userId       会员ID
     * @param userCode     会员编号
     * @param userName     会员用户名
     * @param ip           访问IP
     * @param openDoorTime 开门时间
     */
    public static void addOPLog(String deviceCode, String userId, String userCode, String userName, String ip, Date openDoorTime) {
        final DeviceOper deviceOper = new DeviceOper();
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userCode) || StringUtils.isBlank(userName)) {
            return;
        }
        deviceOper.setTopenTime(openDoorTime);                      //开门时间
        deviceOper.setSmemberId(userId);                            // 会员ID
        deviceOper.setSmemberCode(userCode);                        // 会员编号
        deviceOper.setSmemberName(userName);                        // 会员用户名（手机号）
        deviceOper.setSip(ip);                                      // 访问ip
        deviceOper.setSdeviceCode(deviceCode);                      // 设备编号
        deviceOper.setTaddTime(DateUtils.getCurrentDateTime());     // 添加时间
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                getDeviceOperService().insert(deviceOper);
            }
        });

    }

    /***
     * 新增日志
     * @param sessionVo 客户端连接信息
     */
    public static void addOPLog(SessionVo sessionVo) {
        if (null == sessionVo) {
            return;
        }
        final DeviceOper deviceOper = new DeviceOper();
        deviceOper.setTopenTime(DateUtils.getCurrentDateTime());                      //开门时间
        deviceOper.setSmemberId(sessionVo.getUserId());                            // 会员ID
        deviceOper.setSmemberCode(sessionVo.getUserCode());                        // 会员编号
        deviceOper.setSmemberName(sessionVo.getUserName());                        // 会员用户名（手机号）
        deviceOper.setItype(sessionVo.getTypes());//开门类型
        //deviceOper.setSip(ip);                                      // 访问ip
        deviceOper.setSip(sessionVo.getSip());                                      // 访问ip
        deviceOper.setSdeviceCode(sessionVo.getDeviceCode());                      // 设备编号
        deviceOper.setTaddTime(DateUtils.getCurrentDateTime());     // 添加时间
        deviceOper.setIclientType(sessionVo.getIsourceClientType());

        try {
            iCached = getiCached();
            String picList = (String) iCached.get("cloudOpenPicUrl_" + sessionVo.getDeviceId() + "_" + sessionVo.getUserId());
            deviceOper.setSopenPicUrl(picList);
        } catch (Exception e) {
            logger.info("获取开门图片信息异常：{}", e);
        }
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                getDeviceOperService().insert(deviceOper);
            }
        });

    }

    /**
     * 设备操作日志--关门时修改开门记录
     * 增加关门时间
     *
     * @param deviceCode 设备编号
     * @param userId     用户ID
     * @param type       10 购物 ，20 补货
     */
    public static void updateOPLog(final String deviceCode, final String userId, Integer type) {
        final DeviceOper deviceOper = new DeviceOper();

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(deviceCode)) {
            return;
        }
        deviceOper.setTcloseTime(DateUtils.getCurrentDateTime());               // 关门时间
        if (null != type) {
            deviceOper.setItype(type);
        }
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<>();
                map.put("sdeviceCode", deviceCode);
                map.put("smemberId", userId);
                String id = getDeviceOperService().selectLastOpLog(map);
                if (StringUtils.isNotBlank(id)) {
                    deviceOper.setId(id);
                    getDeviceOperService().updateBySelective(deviceOper);
                }
            }
        });
    }

    /**
     * 设备操作日志--关门时修改开门记录
     * 增加关门时间
     *
     * @param deviceCode 设备编号
     * @param userId     用户ID
     * @param type       10 购物 ，20 补货
     */
    public static void updateOPPicLog(final String deviceCode, final String userId, Integer type, String urlList) {
        final DeviceOper deviceOper = new DeviceOper();

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(deviceCode)) {
            return;
        }
        deviceOper.setTcloseTime(DateUtils.getCurrentDateTime());               // 关门时间
        if (null != type) {
            deviceOper.setItype(type);
        }
        deviceOper.setSclosePicUrl(urlList);
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<>();
                map.put("sdeviceCode", deviceCode);
                map.put("smemberId", userId);
                String id = getDeviceOperService().selectLastOpLog(map);
                if (StringUtils.isNotBlank(id)) {
                    deviceOper.setId(id);
                    getDeviceOperService().updateBySelective(deviceOper);
                }
            }
        });
    }

    /**
     * 设备故障记录日志
     *
     * @param deviceId       设备ID
     * @param type           故障申报类型 10 长连接，20短连接，30手动
     * @param errorCode      故障错误码
     * @param errorMsg       故障错误信息
     * @param time           申报时间
     * @param exceptionGrade 故障等级
     */
    public static void insertMalfunctionLog(final String deviceId, final Integer type, final Integer errorCode, final String errorMsg, final String time, final String exceptionGrade) {
        final DeviceMalfunctionRecord deviceMalfunctionRecord = new DeviceMalfunctionRecord();
        if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(errorMsg) || null == errorCode) {
            return;
        }

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DeviceInfo deviceInfo = getDeviceInfoService().selectByPrimaryKey(deviceId);
                if (null != deviceInfo) {
                    String address = deviceInfo.getSprovinceName() + deviceInfo.getScityName() + deviceInfo.getSareaName() + deviceInfo.getSputAddress();
                    deviceMalfunctionRecord.setSdeviceId(deviceId);                             // 设备ID
                    deviceMalfunctionRecord.setSdeviceCode(deviceInfo.getScode());              // 设备编号
                    deviceMalfunctionRecord.setSdeviceAddress(address);                         // 设备地址
                    deviceMalfunctionRecord.setSmerchantId(deviceInfo.getSmerchantId());        // 商户ID
                    deviceMalfunctionRecord.setSmerchantCode(deviceInfo.getSmerchantCode());    // 商户编号
                    deviceMalfunctionRecord.setSerrorCode(errorCode.toString());                // 错误代码
                    deviceMalfunctionRecord.setIdelFlag(0);                                     // 删除 0否 ，1是
                    deviceMalfunctionRecord.setIstatus(10);                                     // 处理状态 10待处理，20已处理,30已废弃
                    deviceMalfunctionRecord.setItype(type);                                     // 故障申报类型 10长连接，20短连接，30手动
                    deviceMalfunctionRecord.setSlevel(exceptionGrade);                          // 故障等级
                    deviceMalfunctionRecord.setSmalfunctionDesc(errorMsg);                      // 错误信息
                    deviceMalfunctionRecord.setTaddTime(DateUtils.getCurrentDateTime());        // 添加时间
                    deviceMalfunctionRecord.setTupdateTime(DateUtils.getCurrentDateTime());     // 修改时间
                    if (StringUtils.isNotBlank(time)) {                                         // 申报时间
                        deviceMalfunctionRecord.setTdeclareTime(DateUtils.convertToDateTime(time));
                    } else {
                        deviceMalfunctionRecord.setTdeclareTime(DateUtils.getCurrentDateTime());
                    }

                    getDeviceMalfunctionRecordService().insert(deviceMalfunctionRecord);

                }


            }
        });
    }

    /**
     * 长连接故障消息记录日志
     *
     * @param deviceId       设备ID
     * @param msg            故障消息
     * @param errorCode      错误码
     * @param exceptionGrade 异常等级
     */
    public static void tcpMalfunctionLog(String deviceId, String msg, Integer errorCode, String exceptionGrade) {
        insertMalfunctionLog(deviceId, TCP_ERROR, errorCode, msg, null, exceptionGrade);
    }

    /**
     * 短连接故障消息记录日志
     *
     * @param deviceId  设备ID
     * @param msg       故障消息
     * @param errorCode 错误码
     * @param time
     */
    public static void httpMalfunctionLog(String deviceId, String msg, Integer errorCode, String time, String exceptionGrade) {
        insertMalfunctionLog(deviceId, HTTP_ERROR, errorCode, msg, time, exceptionGrade);
    }


    /**
     * 接收到异常消息发送短信给内部维护人员
     *
     * @param deviceId  设备ID
     * @param msg       异常信息
     * @param errorCode 错误代码
     */
    public static void sendMessageTointernal(final String deviceId, final String msg, final Integer errorCode) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String merchantId = "";
                    String merchantCode = "";
                    String deviceCode = "";
                    ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (null != clientVo) {
                        merchantId = clientVo.getMerchantId();
                        merchantCode = clientVo.getMerchantCode();
                        deviceCode = clientVo.getDeviceCode();
                    }


                    // 调用短信接口
                    if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {


                        // 调用发送短信接口
                        InnerMessageDto innerMessageDto = new InnerMessageDto();
                        innerMessageDto.setTemplateType("device_malfunction_message");
                        // 模板编号
                        // 内容
                        Map<String, Object> contentParamMap = new HashMap<>();
                        contentParamMap.put("deviceCode", deviceCode);
                        contentParamMap.put("msg", msg);
                        contentParamMap.put("errorCode", errorCode);
                        innerMessageDto.setContentParam(contentParamMap);
                        // 权限编码
                        innerMessageDto.setPurviewCode("DEVICE_MALFUNCTION_MESSAGE");
                        //商户Id
                        innerMessageDto.setSmerchantId(merchantId);
                        innerMessageDto.setSmerchantCode(merchantCode);

                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                        invoke.setRequestObj(innerMessageDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                        });
                        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                        if (responseVo.isSuccess()) {
                            logger.debug("设备故障短信发送成功！");
                        } else {
                            logger.error("设备故障短信发送失败");
                        }
                    }
                } catch (Exception e) {
                    logger.error("设备故障短信发送异常：{}", e);
                }

            }
        });
    }

    /**
     * 发送故障消息给内部人员
     *
     * @param faultTime 故障时间
     * @param deviceId  设备ID
     * @param msg       故障原因
     * @param errorCode 错误代码
     */
    public static void sendMessageTointernal(final String faultTime, final String deviceId, final String msg, final Integer errorCode) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String merchantId = "";
                    String merchantCode = "";
                    String deviceCode = "";
                    String address = "";
                    ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (null != clientVo) {
                        merchantId = clientVo.getMerchantId();
                        merchantCode = clientVo.getMerchantCode();
                        deviceCode = clientVo.getDeviceCode();
                    }

                    DeviceInfo deviceInfoTemp = (DeviceInfo) iCached.hget(NettyConst.DEVICE_INFO, deviceId);
                    if (null != deviceInfoTemp) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(deviceInfoTemp.getSprovinceName());
                        stringBuffer.append(deviceInfoTemp.getScityName());
                        stringBuffer.append(deviceInfoTemp.getSareaName());
                        stringBuffer.append(deviceInfoTemp.getSputAddress());
                        address = stringBuffer.toString();
                    }

                    // 调用短信接口
                    if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {


                        // 调用发送短信接口
                        InnerMessageDto innerMessageDto = new InnerMessageDto();
                        innerMessageDto.setTemplateType("device_malfunction_message");
                        // 模板编号
                        // 内容
                        Map<String, Object> contentParamMap = new HashMap<>();
                        contentParamMap.put("faultTime", faultTime);
                        contentParamMap.put("deviceCode", deviceCode);
                        contentParamMap.put("msg", msg);
                        contentParamMap.put("address", address);
                        innerMessageDto.setContentParam(contentParamMap);
                        // 权限编码
                        innerMessageDto.setPurviewCode("DEVICE_MALFUNCTION_MESSAGE");
                        //商户Id
                        innerMessageDto.setSmerchantId(merchantId);
                        innerMessageDto.setSmerchantCode(merchantCode);

                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                        invoke.setRequestObj(innerMessageDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                        });
                        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                        if (responseVo.isSuccess()) {
                            logger.debug("设备故障短信发送成功！");
                        } else {
                            logger.error("设备故障短信发送失败");
                        }
                    }
                } catch (Exception e) {
                    logger.error("设备故障短信发送异常：{}", e);
                }

            }
        });
    }


    /**
     * 在线设备扫描
     *
     * @param deviceId 设备ID
     */
    public static void sendDeviceOfflineMsgToInternal(final String deviceId) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String merchantId = "";
                    String merchantCode = "";
                    String deviceCode = "";
                    ClientVo clientVo = (ClientVo) iCached.hget(NettyConst.SYN_CLIENT_MAP, deviceId);
                    if (null != clientVo) {
                        merchantId = clientVo.getMerchantId();
                        merchantCode = clientVo.getMerchantCode();
                        deviceCode = clientVo.getDeviceCode();
                    }


                    // 调用短信接口
                    if (StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(merchantCode)) {

                        // 调用发送短信接口
                        InnerMessageDto innerMessageDto = new InnerMessageDto();
                        innerMessageDto.setTemplateType("device_online_scanning");
                        // 模板编号
                        // 内容
                        Map<String, Object> contentParamMap = new HashMap<>();
                        contentParamMap.put("deviceCode", deviceCode);
                        innerMessageDto.setContentParam(contentParamMap);
                        // 权限编码
                        innerMessageDto.setPurviewCode("DEVICE_ONLINE_SCANNING");
                        //商户Id
                        innerMessageDto.setSmerchantId(merchantId);
                        innerMessageDto.setSmerchantCode(merchantCode);

                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MessageServicesDefine.SMS_MESSAGE_SEND_INNER_SERVICE);
                        invoke.setRequestObj(innerMessageDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                        });
                        ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
                        if (responseVo.isSuccess()) {
                            logger.debug("设备在线扫描短信发送成功！");
                        } else {
                            logger.error("设备在线扫描短信发送失败");
                        }
                    }
                } catch (Exception e) {
                    logger.error("设备在线扫描短信发送异常：{}", e);
                }

            }
        });
    }

    /**
     * 人脸识别操作日志
     *
     * @param deviceId     设备ID
     * @param memberId     会员ID
     * @param type         操作类型
     * @param base64String base64图片字符串
     * @param operDesc     操作描述
     * @param result       操作结果
     * @param ip           操作IP
     * @param source       操作来源
     */
    public static void insertFaceOperLog(final String deviceId, final String memberId, final Integer type, final String base64String,
                                         final String operDesc, final Integer result, final String ip, final Integer source) {

        ExecutorManager.getInstance().execute(() -> {

            // base64转图片地址url
            /* 上传用户图片到本地服务器 */
            InputStream inputStream = null;
            String imgUrl = "";
            try {
                // base64转图片,存储到本地服务器
                String path = System.getProperty("catalina.home") + File.separator + "temp" + File.separator + DateUtils.getCurrentSTimeNumber() + ".jpg";//指定输出路径
                AiFaceUtils.genetareImg(base64String, path);
                File file = new File(path);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile("file", IOUtils.toByteArray(inputStream));
                    //上传图片到图片服务器
                    if (null != file) {
                        //图片上传
                        String pathName = "userFaceImg";
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
                        String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
                        String tempName = DateUtils.getCurrentSTimeNumber() + ".png";//文件名=="时间"+"."+"原图片名后缀"
                        // 返回URL地址
                        if (FtpUtils.uploadToFtpServer(multipartFile.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                            StringBuffer stringBuffer1 = new StringBuffer();
                            stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                            imgUrl = stringBuffer1.toString();// 路径为------> /userFaceImg/2018-03-07/20180307151504492.jpg
                        }

                        if (StringUtils.isNotBlank(imgUrl)) {
                            if (inputStream != null) {      //关闭流
                                inputStream.close();
                            }
                            if (file.exists() && file.isFile()) {
                                file.delete();
                                System.out.println("已经删除文件：" + file.getPath());
                            }
                        }
                    }
                } else {
                    logger.error("base64图片转换失败");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            MemberInfo memberInfo = getMemberInfoService().selectByPrimaryKey(memberId);
            DeviceInfo deviceInfo = getDeviceInfoService().selectByPrimaryKey(deviceId);
            if (null != memberInfo && null != deviceInfo) {
                FaceOperLog faceOperLog = new FaceOperLog();
                faceOperLog.setSmemberId(memberId);
                faceOperLog.setSmemberCode(memberInfo.getScode());
                faceOperLog.setSmemberName(memberInfo.getSmemberName());
                faceOperLog.setSoperIp(ip);             // 登录ip
                faceOperLog.setIsourceType(source);     // 10 设备端，20支付宝，30微信
                faceOperLog.setType(type);              // 操作类型操作类型  10：注册 20：登录 30：解绑 40：绑定 50：删除
                faceOperLog.setSaiCode(deviceInfo.getSaiCode());         // 设备编号
                faceOperLog.setSfaceImgUrl(imgUrl);     // 图片地址
                faceOperLog.setIoperResult(result);     // 操作结果
                faceOperLog.setSoperDesc(operDesc);     // 操作描述
                faceOperLog.setToperTime(DateUtils.getCurrentDateTime());
                faceOperLog.setTaddTime(DateUtils.getCurrentDateTime());
                getFaceOperLogService().insert(faceOperLog);
            }
        });
    }


}
