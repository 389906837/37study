package com.cloud.cang.api.sb.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.netty.vo.http.DeviceDomain;
import com.cloud.cang.api.netty.vo.send.CellBean;
import com.cloud.cang.api.sb.dao.*;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.VoiceModel;
import com.cloud.cang.model.sb.*;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备注册业务
 *
 * @version 1.0
 * @Author: zengzexiong
 * @Date: 2018年2月2日14:55:22
 */
@Service
public class DeviceRegisterServiceImpl extends GenericServiceImpl<DeviceRegister, String> implements
        DeviceRegisterService {

    private static Logger LOGGER = LoggerFactory.getLogger(DeviceRegisterServiceImpl.class);

    @Autowired
    DeviceRegisterDao deviceRegisterDao;

    @Autowired
    DeviceInfoDao deviceInfoDao;

    @Autowired
    AiInfoDao aiInfoDao;

    @Autowired
    DeviceModelDao deviceModelDao;

    @Autowired
    MonitorDataConfDao monitorDataConfDao;

    @Autowired
    VrDeviceConfDao vrDeviceConfDao;

    @Autowired
    VrDeviceMotherboardDao vrDeviceMotherboardDao;


    @Override
    public GenericDao<DeviceRegister, String> getDao() {
        return deviceRegisterDao;
    }


    /**
     * 根据注册码从注册表中查询设备注册信息
     * 判断状态是否为20审核通过或者40已注册，否则返回错误
     * 修改注册状态及设备状态为注册
     * 返回设备ID_KEY
     *
     * @param reqIp 注册码
     * @return 设备ID_KEY
     */
    @Override
    public ResponseVo<String> register(String reqIp) {
        LOGGER.info("service开始注册设备信息");
        StringBuffer stringBuffer = new StringBuffer();//用于拼接设备ID与安全密钥

        //根据注册码查找该设备的注册信息
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByReqIp(reqIp);
        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            DeviceRegister deviceRegister = deviceRegisterList.get(0);
            Integer reqStatus = deviceRegister.getIstatus();                        //设备注册状态，10 待审核 20  审核通过  30 审核拒绝 40 已注册

            //判断设备是否已经注册过
            if (Integer.valueOf(20).equals(reqStatus) || Integer.valueOf(40).equals(reqStatus)) {                            //审核通过，可以进行注册
                //修改注册信息
                DeviceRegister tempDeviceRegister = new DeviceRegister();
                tempDeviceRegister.setId(deviceRegister.getId());
                tempDeviceRegister.setIstatus(40);                                  //修改设备注册状态为： 40 已注册
                tempDeviceRegister.setTregTime(DateUtils.getCurrentDateTime());     //注册时间
                deviceRegisterDao.updateByIdSelective(tempDeviceRegister);          //设备注册表

                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setId(deviceRegister.getSdeviceId());                    //设备ID
                deviceInfo.setIstatus(20);                                          //设备状态：10 未注册，20 正常， 30 故障，40 报废
                deviceInfoDao.updateByIdSelective(deviceInfo);

                //返回设备ID_安全密钥
                stringBuffer.append(deviceRegister.getSdeviceId());                 //设备ID
                stringBuffer.append("_");
                stringBuffer.append(deviceRegister.getSsecurityKey());              //安全密钥
                return new ResponseVo(stringBuffer.toString());
            } else { //非法状态
                return new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_STATUS.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_STATUS.getCode()));
            }
        }
        LOGGER.info("service注册设备信息完成");
        return new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_STATUS.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_STATUS.getCode()));
    }

    /**
     * 根据设备ID和安全密钥查询设备注册信息
     * @param deviceID 设备ID
     * @param securityKey 安全密钥
     * @return 设备注册信息
     */
    @Override
    public DeviceRegister selectRegister(String deviceID,String securityKey) {
        System.out.println("-----------------------------------------");
        LOGGER.debug("根据设备ID与安全密钥" + securityKey + "开始查询注册设备信息!");
        Map<String, String> map = new HashMap<>();
        map.put("ssecurityKey", securityKey);
        map.put("sdeviceId", deviceID);
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.queryBySecurityKey(map);
        //查询到设备注册信息
        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            LOGGER.debug("查询注册设备信息结束!");
            return deviceRegisterList.get(0);
        }
        return null;
    }

    /**
     * 查询所有注册设备token
     * @return 设备tokenList
     */
    @Override
    public List<String> selectAll() {
        LOGGER.debug("开始查询所有注册设备token!");
        List<String> tokenList = deviceRegisterDao.selectAll();

        LOGGER.debug("查询注册设备信息结束!" + tokenList.toString());
        return tokenList;
    }

    /**
     * 根据设备ID从设备注册表中获取注册信息
     * @param id 设备ID
     * @return
     */
    @Override
    public DeviceRegister selectOneById(String id) {
        LOGGER.debug("开始查询设备token!");
        DeviceRegister deviceRegister = deviceRegisterDao.selectByPrimaryKey(id);

        LOGGER.debug("查询设备结束!设备ID是：" + deviceRegister.getId() + "设备编号是：" + deviceRegister.getSdeviceCode());
        return deviceRegister;
    }

    /**
     * 根据设备ID与密钥查询设备信息
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return 设备属性
     */
    @Override
    public ResponseVo<DeviceDomain> getDeviceInfo(String deviceId, String key) throws Exception {
        //根据设备密钥查询设备是否注册
        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setSdeviceId(deviceId);      // 设备ID
        deviceRegister.setSsecurityKey(key);        // 设备注册安全密钥
//        deviceRegister.setIstatus(40);              // 设备状态 40-已注册
        deviceRegister.setIdelFlag(0);              // 未删除
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByEntityWhere(deviceRegister);

        //查询到该设备注册信息
        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            DeviceDomain deviceDomain = new DeviceDomain();
            String deviceCode = null;                                                   //  设备编号
            Integer type = null;                                                        //  设备类型
            Integer voiceValue = null;                                                  //  音量值
            Integer inventoryNum = null;                                                //  盘货次数
            String lockType = null;                                                     //  锁型号
            String qrCodeUrl = null;                                                    //  设备二维码图片地址

            DeviceRegister tempDeviceRegister = deviceRegisterList.get(0);              //  设备注册信息

            DeviceInfo tempDeviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);     //  设备基础信息
            if (null != tempDeviceInfo) {
                deviceCode = tempDeviceRegister.getSdeviceCode();                       //  设备编号
                type = tempDeviceInfo.getItype();                                       //  设备类型
                String sufUrl = tempDeviceInfo.getSqrUrl();                            //  设备二维码图片地址后缀
                String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  图片服务器地址前缀
                if (StringUtils.isNotBlank(sufUrl) && StringUtils.isNotBlank(preUrl)) {
                    qrCodeUrl = preUrl + sufUrl;
                }
            }

            DeviceModel tempDeviceModel = deviceModelDao.selectByDeviceId(deviceId);    //  设备详细信息
            if (null != tempDeviceModel) {
                lockType = tempDeviceModel.getSlocksModel();
            }

            MonitorDataConf monitorDataConf = monitorDataConfDao.selectByDeviceId(deviceId);  //设备监控信息
            if (null != monitorDataConf) {
                voiceValue = monitorDataConf.getIactualVolume();
                inventoryNum = monitorDataConf.getIinventoryNum();

            }

            VrDeviceMotherboard vrDeviceMotherboard = vrDeviceMotherboardDao.selectByDeviceId(deviceId);    //视觉设备主板配置
            List<CellBean> cellBeanList = new ArrayList<>();
            if (null != vrDeviceMotherboard) {
                String vrIp = vrDeviceMotherboard.getSregisterIp();             //注册IP
                Integer vrPort = vrDeviceMotherboard.getIport();                //注册端口
                List<VrDeviceConf> vrDeviceConfList = vrDeviceConfDao.selectByDeviceId(deviceId); //视觉设备配置
                if (CollectionUtils.isNotEmpty(vrDeviceConfList)) {
                    for (VrDeviceConf vr : vrDeviceConfList) {
                        CellBean cellBean = new CellBean();                     //通道实体对象
                        cellBean.setIp(vrIp);                                   //通道IP
                        cellBean.setPort(vrPort.toString());                    //通道端口号
                        cellBean.setCellid(vr.getSuniqueCode());                //设备某一层识别单元编号
                        cellBean.setStatus(vr.getIstatus().toString());         //通道状态
                        cellBean.setChannel(vr.getIserialNumber().toString());  //通道序号
                        cellBeanList.add(cellBean);
                    }
                }
            }

            if (null == inventoryNum) {
                inventoryNum = 1;
            }
            if (null == voiceValue) {
                voiceValue = 50;
            }
            deviceDomain.setDeviceId(deviceId);             //设备id
            deviceDomain.setDeviceCode(deviceCode);         //设备编号
            deviceDomain.setVoiceValue(voiceValue);         //音量值
            deviceDomain.setInventoryNum(inventoryNum);     //盘货次数
            deviceDomain.setType(type);                     //设备类型
            deviceDomain.setListcell(cellBeanList);         //各层通道信息
            deviceDomain.setLockType(lockType);             //电子锁型号
            deviceDomain.setQrCodeUrl(qrCodeUrl);           //设备二维码图片地址
            return new ResponseVo(true, 200, "根据设备ID与密钥查询设备信息成功", deviceDomain);
        }

        //返回参数不合法的错误信息
        return new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), "未查询到相关设备信息", null);
    }

    /**
     * 人脸识别设备信息
     * @param reqIp 注册码
     * @return
     */
    @Override
    public ResponseVo<String> faceRegister(String reqIp) {
        LOGGER.debug( "人脸识别设备开始注册设备" );
        StringBuffer stringBuffer = new StringBuffer();//用于拼接设备ID与安全密钥

        //根据注册码查找该设备的注册信息
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByReqIp(reqIp);
        if (CollectionUtils.isNotEmpty(deviceRegisterList)) {
            DeviceRegister deviceRegister = deviceRegisterList.get(0);
            Integer reqStatus = deviceRegister.getIstatus();                        //设备注册状态，10 待审核 20  审核通过  30 审核拒绝 40 已注册

            //判断设备是否已经注册过
            if (Integer.valueOf(20).equals(reqStatus) || Integer.valueOf(40).equals(reqStatus)) {                            //审核通过，可以进行注册
                //修改注册信息
                DeviceRegister tempDeviceRegister = new DeviceRegister();
                tempDeviceRegister.setId(deviceRegister.getId());
                tempDeviceRegister.setIstatus(40);                                  //修改设备注册状态为： 40 已注册
                tempDeviceRegister.setTregTime(DateUtils.getCurrentDateTime());     //注册时间
                deviceRegisterDao.updateByIdSelective(tempDeviceRegister);          //设备注册表

                AiInfo aiInfo = new AiInfo();
                aiInfo.setId(deviceRegister.getSdeviceId());                    //设备ID
                aiInfo.setIstatus(20);                                          //设备状态：10 未注册，20 正常， 30 故障，40 报废
                aiInfoDao.updateByIdSelective(aiInfo);

                //返回设备ID_安全密钥
                stringBuffer.append(deviceRegister.getSdeviceId());                 //设备ID
                stringBuffer.append("_");
                stringBuffer.append(deviceRegister.getSsecurityKey());              //安全密钥
                stringBuffer.append("_");
                stringBuffer.append(aiInfo.getSaiCode());                           //设备编号
                return new ResponseVo(true, 200, "人脸识别设备注册成功", stringBuffer.toString());

            } else { //非法状态
                return new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_STATUS.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_STATUS.getCode()));
            }
        }
        LOGGER.debug( "人脸识别设备注册完成" );
        return new ResponseVo(false, ReturnCodeEnum.ERROR_PARAM_STATUS.getCode(), ReturnCodeEnum.getNameByCode(ReturnCodeEnum.ERROR_PARAM_STATUS.getCode()));
    }

    /**
     * 获取语音更新包
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @return
     */
    @Override
    public ResponseVo<String> getUpdateVoice(String deviceId, String key) {
        //根据设备密钥查询设备是否注册
        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setSdeviceId(deviceId);      // 设备ID
        deviceRegister.setSsecurityKey(key);        // 设备注册安全密钥
        deviceRegister.setIdelFlag(0);              // 未删除
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByEntityWhere(deviceRegister);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo("未匹配到对应设备信息");
        }

        String openDoorUrl = "noUrl";
        String shoppingUrl= "noUrl";
        String thanksUrl= "noUrl";
        // 从静态服务器获取语默认语音包地址
        String openDoorUrlTemp = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG,"openDoorVoiceUrl");  // 开门语音包
        String shoppingUrlTemp  = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG,"shoppingVoiceUrl");  // 购物语音包
        String thanksUrlTemp  = GrpParaUtil.getValue(GrpParaUtil.UPDATE_URL_PREX_CONFIG,"thanksVoiceUrl");      // 购物完成语音包
        if (StringUtils.isNotBlank(openDoorUrlTemp)) {
            openDoorUrl = openDoorUrlTemp;
        }
        if (StringUtils.isNotBlank(shoppingUrlTemp)) {
            shoppingUrl = shoppingUrlTemp;
        }
        if (StringUtils.isNotBlank(thanksUrlTemp)) {
            thanksUrl = thanksUrlTemp;
        }
        VoiceModel voiceModel = new VoiceModel();
        voiceModel.setOpenDoorUrl(openDoorUrl);
        voiceModel.setShoppingUrl(shoppingUrl);
        voiceModel.setThanksUrl(thanksUrl);
        return ResponseVo.getSuccessResponse(JSON.toJSONString(voiceModel));
    }

    /**
     * 通过设备ID与通信秘钥校验设备是否存在
     *
     * @param deviceId 设备ID
     * @param key      通信秘钥
     * @return 正确返回true，否则返回false
     */
    @Override
    public Boolean checkDeviceIdAndKey(String deviceId, String key) {
        DeviceRegister deviceRegister = new DeviceRegister();
        deviceRegister.setSdeviceId(deviceId);      // 设备ID
        deviceRegister.setSsecurityKey(key);        // 设备注册安全密钥
        deviceRegister.setIdelFlag(0);              // 未删除
        List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByEntityWhere(deviceRegister);
        if (CollectionUtils.isEmpty(deviceRegisterList)) {
            return false;
        }
        return true;
    }


}