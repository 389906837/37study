package com.cloud.cang.api.ws;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.LogUtils;
import com.cloud.cang.api.netty.service.MgrSendMsgService;
import com.cloud.cang.api.netty.vo.http.DeviceDomain;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceModelService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.ws.domain.*;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.AdListModelTemp;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.sb.*;
import com.cloud.cang.utils.EncryptUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceService
 * @Description 设备控制层 接口
 * @Author zengzexiong
 * @Date 2018年1月25日11:26:16
 */
@RestController
@RequestMapping("/device")
@RegisterRestResource
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    DeviceRegisterService registerService;

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    MgrSendMsgService mgrSendMsgService;

    @Autowired
    DeviceModelService deviceModelService;


    /**
     * 获取连接但未注册的TCP信息
     *
     * @return TCP集合
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getConnectedTcp", method = RequestMethod.POST)
    public ResponseVo<TcpResult> getConnectedTcp(@RequestBody TcpDto tcpDto) {
        try {
            return mgrSendMsgService.getConnectedTcp();
        } catch (Exception e) {
            logger.error("获取连接的TCP信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，获取连接的TCP信息失败");
        }
    }

    /**
     * 获取已经注册到服务器的TCP信息
     *
     * @return TCP集合
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getRegisterTcp", method = RequestMethod.POST)
    public ResponseVo<TcpResult> getRegisterTcp(@RequestBody TcpDto tcpDto) {
        try {
            return mgrSendMsgService.getRegisterTcp();
        } catch (Exception e) {
            logger.error("获取已经注册到服务器的TCP信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，获取已经注册到服务器的TCP信息失败");
        }
    }


    /**
     * 断开未注册的TCP连接
     *
     * @return TCP集合
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/disconnectUnregisterTcp", method = RequestMethod.POST)
    public ResponseVo<String> disconnectUnregisterTcp(@RequestBody UnRegisterTcpDto unRegisterTcpDto) {
        if (StringUtils.isBlank(unRegisterTcpDto.getChannelId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空！");
        }
        try {
            return mgrSendMsgService.disconnectUnregisterTcp(unRegisterTcpDto);
        } catch (Exception e) {
            logger.error("获取已经注册到服务器的TCP信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，获取已经注册到服务器的TCP信息失败");
        }
    }

    /**
     * 断开注册到服务器的TCP连接
     *
     * @return TCP集合
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/disconnectRegisterTcp", method = RequestMethod.POST)
    public ResponseVo<String> disconnectRegisterTcp(@RequestBody RegisterTcpDto registerTcpDto) {
        if (StringUtils.isBlank(registerTcpDto.getDeviceId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空！");
        }
        try {
            return mgrSendMsgService.disconnectRegisterTcp(registerTcpDto);
        } catch (Exception e) {
            logger.error("获取已经注册到服务器的TCP信息出现异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，获取已经注册到服务器的TCP信息失败");
        }
    }

    /**
     * 向客户端发送消息
     *
     * @param deviceDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/operateDevice", method = RequestMethod.POST)
    public ResponseVo<String> operateDevice(@RequestBody DeviceDto deviceDto) {
        try {
            logger.debug("mgr向客户端发送消息开始");
            String deviceIds = deviceDto.getId();
            String methodType = deviceDto.getFunction();
            //校验基础参数
            if (StringUtils.isBlank(deviceIds)) {
                logger.debug("操作客户端参数校验失败,ID不能为空");
                return new ResponseVo<>(false, ReturnCodeEnum.ERROR_PARAM.getCode(), "操作客户端ID不能为空");
            }
            if (StringUtils.isBlank(methodType)) {
                logger.debug("操作客户端参数校验失败，方法不能为空");
                return new ResponseVo<>(false, ReturnCodeEnum.ERROR_PARAM.getCode(), "操作客户端方法不能为空");
            }

            //将拼接的设备ID分割
//            List<String> deviceIdList = CommonUtils.stringToList(deviceIds);

            //所选设备列表中包含运行状态不正常的设备
//            if (!validDevice(deviceIdList)) {
//                return new ResponseVo<>(false, ReturnCodeEnum.ERROR_PARAM.getCode(), "所选设备列表中包含运行状态不正常的设备");
//            }

            //向客户端发送消息
            return mgrSendMsgService.sendMsgToMany(deviceDto);

        } catch (Exception e) {
            logger.error("发送消息服务调用失败:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，向终端设备发送消息失败");
        }
    }


    /**
     * mgr后台操作设备通用类
     *
     * @param deviceOperatingDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/deviceOperating", method = RequestMethod.POST)
    public ResponseVo<String> deviceOperating(@RequestBody DeviceOperatingDto deviceOperatingDto) {
        try {
            logger.debug("mgr后台操作设备通用类，接收到mgr发送的消息，请求参数为:{}", deviceOperatingDto);
            String method = deviceOperatingDto.getMethod();
            String operator = deviceOperatingDto.getOperator();
            String smerchantCode = deviceOperatingDto.getSmerchantCode();
            String smerchantId = deviceOperatingDto.getSmerchantId();
            String data = deviceOperatingDto.getData();
            //校验基础参数
            if (StringUtils.isBlank(method)) {
                logger.debug("操作客户端参数校验失败,方法不能为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("操作客户端参数校验失败,方法不能为空");
            }
            if (StringUtil.isBlank(data)) {
                logger.debug("操作客户端参数校验失败，操作参数不能为空");
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("操作客户端参数校验失败，操作参数不能为空");
            }

            // 向安卓设备发送消息
            return mgrSendMsgService.sendDeviceOperatingMsg(deviceOperatingDto);
        } catch (Exception e) {
            logger.error("向售货机设备发送消息出现异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常，mgr后台操作设备发送消息失败");
        }
    }

    /**
     * 如果设备连接，将设备从map中移除
     * 并断开连接
     *
     * @param setDeviceOfflineDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/setDeviceOffline", method = RequestMethod.POST)
    public ResponseVo<String> setDeviceOffline(@RequestBody SetDeviceOfflineDto setDeviceOfflineDto) {
        String deviceIds = setDeviceOfflineDto.getId();
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceIds)) {
                logger.debug("参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空");
            }

            ResponseVo<String> responseVo1 = mgrSendMsgService.disconnectDevice(deviceIds); // 将设备断开连接
            if (BooleanUtils.isTrue(responseVo1.isSuccess())) {
                return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("将设备断开连接出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("将设备断开连接出现异常");
    }

    /**
     * 根据商户ID--获取离线设备(定时任务)
     *
     * @param offlineDeviceDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getOfflineDevice", method = RequestMethod.POST)
    public ResponseVo getOfflineDevice(@RequestBody OfflineDeviceDto offlineDeviceDto) {
        logger.debug("<===开始获取离线设备信息==>");
        ResponseVo responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()), null);
        String merchantId = offlineDeviceDto.getMerchantId();
        try {
            //校验基础参数
            if (StringUtils.isBlank(merchantId)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //获取离线设备
            responseVo = mgrSendMsgService.getOfflineDevice(merchantId);
        } catch (Exception e) {
            logger.error("获取离线设备出现异常:{}", e);
        }
        logger.debug("<===获取离线设备成功===>");
        return responseVo;
    }


    /**
     * 根据设备ID向设备发送盘货指令(定时任务)
     *
     * @param inventoryDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getInventoryResult", method = RequestMethod.POST)
    public ResponseVo<String> getInventoryResult(@RequestBody InventoryDto inventoryDto) {
        String merchantId = inventoryDto.getMerchantId();
        try {
            //校验基础参数
            if (StringUtils.isBlank(merchantId)) {
                logger.debug("参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空");
            }

            ResponseVo<String> responseVo1 = mgrSendMsgService.timingInventoryByMerchantId(merchantId); //向设备发送定时盘货消息
//            ResponseVo<String> responseVo1 = mgrSendMsgService.inventory(deviceId, ""); //向设备发送消息
            if (BooleanUtils.isTrue(responseVo1.isSuccess())) {
                return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("盘货出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("发送盘货指令出现异常");
    }

    /**
     * 根据设备ID向设备发送盘货指令(主动盘货)
     *
     * @param inventoryDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/activeInventory", method = RequestMethod.POST)
    public ResponseVo<String> activeInventory(@RequestBody InventoryDto inventoryDto) {
        String deviceId = inventoryDto.getDeviceId();


        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空");
            }
            ResponseVo<String> responseVo1 = mgrSendMsgService.inventory(deviceId, ""); //向设备发送消息
            if (BooleanUtils.isTrue(responseVo1.isSuccess())) {
                return ResponseVo.getSuccessResponse(NettyConst.SUCCESS);
            }
        } catch (Exception e) {
            logger.error("盘货出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("主动盘货出现异常");
    }


    /**
     * 检查所有的设备状态是否为正常使用
     * @param deviceIdList 设备ID集合
     * @return 所有设备正常使用返回true，否则返回false
     */
//    private Boolean validDevice(List<String> deviceIdList) {
//        if (CollectionUtils.isNotEmpty(deviceIdList)) {
//            for (String id : deviceIdList) {
//                DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(id);
//
//                // 没有查询到设备相关信息 || 查询到设备相关信息 ，但是设备状态不对
//                if (null == deviceInfo || !Integer.valueOf(20).equals(deviceInfo.getIstatus())) {  // 10=未注册 20=正常 30=故障 40=报废
//                    return false;
//                }
//
//            }
//        }
//        return true;
//    }

    /**
     * 模拟HTML5页面向设备发送开门指令
     *
     * @param id
     * @param msg
     * @param userId
     * @return
     */
    @RequestMapping("/testSendOneMsg")
    public ResponseVo<String> testSendOneMsg(String id, String msg, String userId) {
        try {
//            ResponseVo<String> result = mgrSendMsgService.sendMsgToOne(id, msg, null, userId);
//            logger.debug(result.getMsg());
            return ResponseVo.getSuccessResponse("发送消息成功" + msg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("testSendOneMsg出现异常");
        }
        return new ResponseVo(false, 1111, "发送消息出现异常");
    }

    /**
     * 新设备注册
     *
     * @param reqIp 设备ID
     * @return 注册成功返回“设备ID_安全密钥”
     */
    @RequestMapping("/register")
    public @ResponseBody
    ResponseVo<String> register(String reqIp) {
        logger.debug("<===新设备注册开始，注册码-->" + reqIp + "===>");
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {
            //校验基础参数
            if (StringUtils.isBlank(reqIp)) {
                responseVo.setMsg("客户端ID不能为空");
                logger.debug("客户端注册参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //注册设备，若未注册，返回“设备ID_安全密钥”；否则返回已经false
            responseVo = registerService.register(reqIp);
        } catch (Exception e) {
            logger.error("注册服务调用失败:{}", e);
        }
        logger.debug("<===设备注册服务完成，注册码-->" + reqIp + "===>");
        return responseVo;
    }

    /**
     * 上传文件
     *
     * @param logFlie 设备ID
     * @return
     */
    @RequestMapping("/uploadLog")
    public @ResponseBody
    ResponseVo<String> uploadLog(@RequestParam(value = "file", required = false) MultipartFile logFlie) {
        logger.info("<===开始接收上传文件===>");
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()));
        try {


        } catch (Exception e) {
            logger.error("文件上传出现异常", e);
        }
        logger.info("<===文件上传完成===>");
        return responseVo;
    }


    /**
     * 已注册设备获取设备信息
     *
     * @param deviceId 设备ID
     * @param key      密钥
     * @return 设备信息----“设备编号_设备类型”
     */
    @RequestMapping("/getDevice")
    public @ResponseBody
    ResponseVo<DeviceDomain> getDevice(String deviceId, String key) {
        ResponseVo<DeviceDomain> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()), null);
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //校验密钥，获取设备信息
            responseVo = registerService.getDeviceInfo(deviceId, key);

        } catch (Exception e) {
            logger.error("注册服务调用失败:{}", e);
        }
        logger.debug("<===查询设备信息服务完成，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        return responseVo;
    }

    /**
     * 获取服务器地址和端口号
     *
     * @param deviceId
     * @param key
     * @return
     */
    @RequestMapping("/getUlrAndPort")
    public @ResponseBody
    ResponseVo<DeviceDomain> getUlrAndPort(String deviceId, String key) {
        logger.debug("<===获取服务器地址与端口号，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        ResponseVo<DeviceDomain> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()), null);
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            // 校验参数是否正确
            Boolean result = registerService.checkDeviceIdAndKey(deviceId, key);
            if (BooleanUtils.isFalse(result)) {
                responseVo.setMsg("设备ID或者通信秘钥错误");
                return responseVo;
            }

            DeviceDomain deviceDomain = new DeviceDomain();
            String ip = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "nettyIp"); //数据字典配置----IP地址
            String port = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG, "nettyPort"); //数据字典配置----端口号
            if (StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port)) {
                deviceDomain.setUrl(ip);
                deviceDomain.setPort(port);
                return ResponseVo.getSuccessResponse(deviceDomain);
            }

        } catch (Exception e) {
            logger.error("获取服务器地址出现异常:{}", e);
        }
        logger.debug("<===获取服务器地址完成，服务器地址-->" + responseVo.getData().getUrl() + "====端口号：" + responseVo.getData().getPort() + "===>");
        return responseVo;
    }

    /**
     * 获取语音更新包
     *
     * @param deviceId 设备ID
     * @param key      密钥
     * @return 语音包模型
     */
    @RequestMapping("/updateVoice")
    public @ResponseBody
    ResponseVo<String> getUpdateVoice(String deviceId, String key) {
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()), null);
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            //校验密钥，获取设备信息
            responseVo = registerService.getUpdateVoice(deviceId, key);

        } catch (Exception e) {
            logger.error("获取语音包出现异常:{}", e);
        }
        logger.debug("<===获取语音包完成，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        return responseVo;
    }

    /**
     * 设备上传故障日志
     *
     * @param deviceId       设备ID
     * @param key            密钥
     * @param msg            故障信息
     * @param time           申报时间
     * @param code           错误码
     * @param exceptionGrade 故障等级
     * @return 上传设备故障日志是否成功
     */
    @RequestMapping("/uploadMalfunction")
    public @ResponseBody
    ResponseVo<String> uploadMalfunction(String deviceId, String key, String msg, String time, Integer code, String exceptionGrade) {
        ResponseVo<String> responseVo = new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_FAILED.getCode()), null);
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }

            //校验密钥
            if (!verifyByDeviceIdAndKey(deviceId, key)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备ID与token不正确");
            }
            LogUtils.httpMalfunctionLog(deviceId, msg, code, time, exceptionGrade);
            responseVo = ResponseVo.getSuccessResponse();

        } catch (Exception e) {
            logger.error("设备上传故障日志失败:{}", e);
        }
        logger.debug("<===设备上传故障日志失败，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        return responseVo;
    }

    /**
     * 获取运营位位置one广告信息
     * 主屏幕广告
     *
     * @param deviceId
     * @param key
     * @return
     */
    @RequestMapping("/getOperatingAdOne")
    @ResponseBody
    public ResponseVo<AdListModel> getOperatingAdOne(String deviceId, String key) {
        logger.debug("<===获取运营位位置one广告信息，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        ResponseVo<AdListModel> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            DeviceRegister deviceRegister = new DeviceRegister();
            deviceRegister.setSdeviceId(deviceId);
            deviceRegister.setSsecurityKey(key);
            List<DeviceRegister> deviceRegisterList = registerService.selectByEntityWhere(deviceRegister);
            if (CollectionUtils.isEmpty(deviceRegisterList)) {
                return responseVo;
            }
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (null != deviceInfo) {
                ResponseVo<AdListModelTemp> tempResponseVo = deviceInfoService.selectOperatingAdOne(deviceInfo.getSmerchantCode());
                return dealWithAdListModel(deviceInfo, responseVo, tempResponseVo);
            }
        } catch (Exception e) {
            logger.error("获取运营位位置one广告信息出现异常:{}", e);
        }
        logger.debug("<===获取运营位位置one广告信息完成===>");
        return responseVo;
    }

    /**
     * 获取运营位位置two广告信息
     * 右侧棚格广告位
     *
     * @param deviceId
     * @param key
     * @return
     */
    @RequestMapping("/getOperatingAdTwo")
    public @ResponseBody
    ResponseVo<AdListModel> getOperatingAdTwo(String deviceId, String key) {
        logger.debug("<===获取运营位位置two广告信息，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        ResponseVo<AdListModel> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            DeviceRegister deviceRegister = new DeviceRegister();
            deviceRegister.setSdeviceId(deviceId);
            deviceRegister.setSsecurityKey(key);
            List<DeviceRegister> deviceRegisterList = registerService.selectByEntityWhere(deviceRegister);
            if (CollectionUtils.isEmpty(deviceRegisterList)) {
                return responseVo;
            }
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (null != deviceInfo) {
                ResponseVo<AdListModelTemp> tempResponseVo = deviceInfoService.selectOperatingAdTwo(deviceInfo.getSmerchantCode());
                return dealWithAdListModel(deviceInfo, responseVo, tempResponseVo);
            }
        } catch (Exception e) {
            logger.error("获取运营位位置one广告信息出现异常:{}", e);
        }
        logger.debug("<===获取运营位位置one广告信息完成===>");
        return responseVo;
    }

    /**
     * 获取运营位位置three广告信息
     * 最下方轮播广告位
     *
     * @param deviceId
     * @param key
     * @return
     */
    @RequestMapping("/getOperatingAdThree")
    @ResponseBody
    public ResponseVo<AdListModel> getOperatingAdThree(String deviceId, String key) {
        logger.debug("<===获取运营位位置three广告信息，设备ID-->" + deviceId + ",通信密钥-->" + key + "===>");
        ResponseVo<AdListModel> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到该设备的广告");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId) || StringUtils.isBlank(key)) {
                responseVo.setMsg("参数不能为空");
                logger.debug("客户端参数校验失败：{}", responseVo.getMsg());
                return responseVo;
            }
            DeviceRegister deviceRegister = new DeviceRegister();
            deviceRegister.setSdeviceId(deviceId);
            deviceRegister.setSsecurityKey(key);
            List<DeviceRegister> deviceRegisterList = registerService.selectByEntityWhere(deviceRegister);
            if (CollectionUtils.isEmpty(deviceRegisterList)) {
                return responseVo;
            }
            DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
            if (null != deviceInfo) {
                ResponseVo<AdListModelTemp> tempResponseVo = deviceInfoService.selectOperatingAdThree(deviceInfo.getSmerchantCode());
                return dealWithAdListModel(deviceInfo, responseVo, tempResponseVo);
            }
        } catch (Exception e) {
            logger.error("获取运营位位置three广告信息出现异常:{}", e);
        }
        logger.debug("<===获取运营位位置three广告信息完成===>");
        return responseVo;
    }

    /***
     * 处理设备获取广告数据
     * @param deviceInfo 设备信息
     * @param responseVo 返回数据
     * @param tempResponseVo 查询的广告数据
     * @return
     */
    private ResponseVo<AdListModel> dealWithAdListModel(DeviceInfo deviceInfo, ResponseVo<AdListModel> responseVo, ResponseVo<AdListModelTemp> tempResponseVo) {
        if (tempResponseVo.isSuccess()) {
            AdListModelTemp temp = tempResponseVo.getData();
            if (null == temp) {
                temp = new AdListModelTemp();
            }
            if (null == temp.getAdModelList()) {
                temp.setAdModelList(new ArrayList<AdModel>());
            }
            if (null == temp.getVcadModelList()) {
                temp.setVcadModelList(new ArrayList<AdModel>());
            }
            AdListModel adListModel = new AdListModel();
            adListModel.setAdModelList(temp.getAdModelList());
            if (null != deviceInfo.getIscreenDisplayType() && deviceInfo.getIscreenDisplayType().intValue() == 20) {
                adListModel.setAdModelList(temp.getVcadModelList());
            }
            responseVo.setSuccess(true);
            responseVo.setMsg("获取运营位位置three广告信息成功");
            responseVo.setData(adListModel);
            responseVo.setErrorCode(ReturnCodeEnum.SUCCESS.getCode());
        } else {
            responseVo.setSuccess(false);
            responseVo.setMsg(responseVo.getMsg());
            responseVo.setErrorCode(responseVo.getErrorCode());
        }
        return responseVo;
    }

    /**
     * 获取年会人脸识别配置信息
     *
     * @return
     */
    @RequestMapping("/getAmFaceConfig")
    @ResponseBody
    public ResponseVo<AndroidConfigInfo> getAmFaceConfig() {
        ResponseVo<AndroidConfigInfo> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查询到年会人脸识别配置信息");
        try {
            return deviceInfoService.getAmFaceConfig();
        } catch (Exception e) {
            logger.error("获取年会人脸识别配置信息出现异常:{}", e);
        }
        logger.debug("获取年会人脸识别配置信息信息完成");
        return responseVo;
    }

    /**
     * 获取云端识别柜子openSDK配置信息
     *
     * @return
     */
    @RequestMapping("/getCloudOpenSDKConfig")
    @ResponseBody
    public ResponseVo<OpenSdkConfigInfo> getCloudOpenSDKConfig(String deviceId, String key) {
        ResponseVo<OpenSdkConfigInfo> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("获取云端识别柜子openSDK配置信息失败");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("deviceId 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数deviceId不能为空");
            }
            if (StringUtils.isBlank(key)) {
                logger.debug("key 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数key不能为空");
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                return responseVo;
            }
            return deviceInfoService.getCloudOpenSDKConfig(deviceId);
        } catch (Exception e) {
            logger.error("获取云端识别柜子openSDK配置信息出现异常:{}", e);
        }
        logger.debug("获取云端识别柜子openSDK配置信息完成");
        return responseVo;
    }

    /**
     * 安卓发送请求移除map中的通道
     *
     * @return
     */
    @RequestMapping("/removeChannel")
    @ResponseBody
    public ResponseVo<String> removeChannel(String data) {
        ResponseVo<String> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("安卓发送请求移除map中的通道失败");
        try {
            if (StringUtils.isBlank(data)) {
                logger.debug("data参数不能为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("data不能为空");
            }
            String encryptStr = EncryptUtils.decodeStringByData(data);
            if (StringUtils.isBlank(encryptStr)) {
                logger.debug("加密参数不能为空");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("加密参数不能为空");
            }
            int deviceIdpre = encryptStr.indexOf("=");
            int deviceIdsub = encryptStr.indexOf("&");
            int keyLen = encryptStr.lastIndexOf("=");
            if (deviceIdpre == -1 || deviceIdsub == -1 || keyLen == -1) {
                logger.debug("data解析失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("data解析失败");
            }
            String deviceId = encryptStr.substring(deviceIdpre + 1, deviceIdsub);
            String key = encryptStr.substring(keyLen + 1);
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("deviceId 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数deviceId不能为空");
            }
            if (StringUtils.isBlank(key)) {
                logger.debug("key 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数key不能为空");
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                return responseVo;
            }
            return mgrSendMsgService.removeChannelByDeviceId(deviceId);
        } catch (Exception e) {
            logger.error("安卓发送请求移除map中的通道出现异常:{}", e);
        }
        logger.debug("安卓发送请求移除map中的通道完成");
        return responseVo;
    }

    /**
     * 根据设备ID与运营位位置向设备发送运营位广告
     *
     * @param sendOperatingAdvertisingDto
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/sendOperatingAdvertising", method = RequestMethod.POST)
    public ResponseVo<String> sendOperatingAdvertising(@RequestBody SendOperatingAdvertisingDto sendOperatingAdvertisingDto) {
        String deviceIds = sendOperatingAdvertisingDto.getDeviceIds();
        String position = sendOperatingAdvertisingDto.getPosition();
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceIds)) {
                logger.debug("参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空");
            }
            if (StringUtils.isBlank(position)) {
                logger.debug("参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数不能为空");
            }
            return mgrSendMsgService.sendOperatingAdvertising(sendOperatingAdvertisingDto);

        } catch (Exception e) {
            logger.error("盘货出现异常:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("发送盘货指令出现异常");
    }


    /**
     * 根据设备ID与key校验该设备信息是否正确
     *
     * @param deviceId 设备ID
     * @param key      安全密钥
     * @return true校验通过，否则不通过
     */
    private boolean verifyByDeviceIdAndKey(String deviceId, String key) {
        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setSdeviceId(deviceId);
        deviceRegister.setSsecurityKey(key);
        deviceRegister.setIdelFlag(0);
        List<DeviceRegister> deviceRegisterList = registerService.selectByEntityWhere(deviceRegister);

        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 云端设备 配置参数调整
     *
     * @param configDto 配置参数
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/cloudDeviceParamConfig", method = RequestMethod.POST)
    public ResponseVo<String> cloudDeviceParamConfig(@RequestBody CloudParamConfigDto configDto) {
        try {
            //校验基础参数
            if (null == configDto.getType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("推送类型不能为空");
            }
            if (StringUtil.isBlank(configDto.getMerchantId()) || StringUtil.isBlank(configDto.getMerchantCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("推送商户数据不能为空");
            }
            if (configDto.getType().intValue() == 10 && (configDto.getDevices() == null || configDto.getDevices().size() <= 0)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("推送设备数据不能为空");
            }
            ResponseVo<String> responseVo = mgrSendMsgService.cloudDeviceParamConfig(configDto); //配置参数调整
            return responseVo;
        } catch (Exception e) {
            logger.error("云端设备配置参数调整:{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("发送盘货指令出现异常");
    }

    /**
     * 设备详细信息配置 -- 摄像头相关参数 = 摄像头+品牌+型号+方法
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getDeviceCaremaConfig", method = RequestMethod.POST)
    public ResponseVo<String> getDeviceCaremaConfig(String deviceId, String key) {
        ResponseVo<String> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("获取摄像头相关参数信息失败");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("deviceId 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数deviceId不能为空");
            }
            if (StringUtils.isBlank(key)) {
                logger.debug("key 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数key不能为空");
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请求参数校验失败");
            }
            return deviceModelService.getDeviceCaremaConfigInfo(deviceId);
        } catch (Exception e) {
            logger.error("获取摄像头相关参数信息出现异常:{}", e);
        }
        logger.debug("获取摄像头相关参数信息失败");
        return responseVo;
    }


    /**
     * 门引脚序号
     * 霍尔引脚序号
     * 开门引脚序号
     * 锁芯引脚序号
     * 摄像头相关参数 = 摄像头+品牌+型号+方法
     * 是否使用扩展GPIO 0否1是
     * 是否检测霍尔值 0否1是
     * 视觉服务提供商
     * PCB板型号
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getDeviceModelConfigInfo", method = RequestMethod.POST)
    public ResponseVo<DeviceModelConfigInfo> getDeviceModelConfigInfo(String deviceId, String key) {
        ResponseVo<DeviceModelConfigInfo> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("获取摄像头配置--锁引脚参数信息失败");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("deviceId 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数deviceId不能为空");
            }
            if (StringUtils.isBlank(key)) {
                logger.debug("key 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数key不能为空");
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请求参数校验失败");
            }
            return deviceModelService.getDeviceModelConfigInfo(deviceId);
        } catch (Exception e) {
            logger.error("获取摄像头配置--锁引脚参数信息出现异常:{}", e);
        }
        logger.debug("获取摄像头配置--锁引脚参数信息失败");
        return responseVo;
    }


    /**
     * 设备芯片引脚配置 --
     * 开门引脚序号
     * 锁芯引脚序号
     * 门引脚序号
     * 霍尔引脚序号
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/getLockPinConfigInfo", method = RequestMethod.POST)
    public ResponseVo<LockPinConfigInfo> getLockPinConfigInfo(String deviceId, String key) {
        ResponseVo<LockPinConfigInfo> responseVo = ReturnCodeEnum.ERROR_NO_AD.getResponseVo("获取设备芯片引脚配置信息失败");
        try {
            //校验基础参数
            if (StringUtils.isBlank(deviceId)) {
                logger.debug("deviceId 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数deviceId不能为空");
            }
            if (StringUtils.isBlank(key)) {
                logger.debug("key 参数校验失败");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("参数key不能为空");
            }
            if (BooleanUtils.isFalse(verifyByDeviceIdAndKey(deviceId, key))) {
                return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("请求参数校验失败");
            }
            return deviceModelService.getLockPinConfigInfo(deviceId);
        } catch (Exception e) {
            logger.error("获取设备芯片引脚配置信息异常:{}", e);
        }
        logger.debug("获取设备芯片引脚配置信息失败");
        return responseVo;
    }

    /**
     * 测试连接是否成功
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/testConnection", method = RequestMethod.POST)
    public ResponseVo testConnection() {
        ResponseVo responseVo = ResponseVo.getSuccessResponse();
        responseVo.setMsg("测试连接成功");
        responseVo.setErrorCode(200);
        return responseVo;
    }

    /***
     * 更新设备广告信息
     * @param deviceDto 更新设备编号集合 运营区域数据
     * @return
     */
    @RequestMapping(value = "/updateDeviceAdvertis", method = RequestMethod.POST)
    public ResponseVo<String> updateDeviceAdvertis(@RequestBody UpdateDeviceAdvertisDto deviceDto) {
        logger.debug("更新设备广告信息服务开始...");
        try {
            logger.info("更新设备广告信息请求参数：{}", JSON.toJSONString(deviceDto));
            // 校验基础参数
            if (StringUtil.isBlank(deviceDto.getSregionCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("广告位信息不能为空");
            }
            if (null == deviceDto.getDeviceCodes() || deviceDto.getDeviceCodes().size() <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("更新广告设备信息不能为空");
            }
            ResponseVo<String> responseVo = deviceInfoService.updateDeviceAdvertis(deviceDto);
            logger.info("更新设备广告信息调用完成，返回参数：{}", JSON.toJSONString(responseVo));
            return responseVo;
        } catch (ServiceException e) {
            logger.error("更新设备广告信息业务异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("更新设备广告信息系统异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("处理异常，系统繁忙");
        }
    }
}