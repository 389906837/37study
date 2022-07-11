package com.cloud.cang.api.common.utils;

import com.cloud.cang.api.sl.service.DeviceOperService;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.model.sl.DeviceOper;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  api设备日志工具类
 * @author zengzexiong
 * @version 1.0
 */
public class LogUtils {

    /**购物*/
    public static final int SHOPPING=10;
    /**补货*/
    public static final int REPLENISHMENT=20;
    /**游客*/
    public static final int TOURIST=30;



    static DeviceOperService deviceOperService;

    private static DeviceOperService getDeviceOperService() {
        if (deviceOperService == null) {
            deviceOperService = SpringContext.getBean(DeviceOperService.class);
        }
        return deviceOperService;
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
     * @param ip 操作IP
     */
    public static void addOPLog(SessionVo sessionVo, String ip) {
        if (null == sessionVo) {
            return;
        }
        final DeviceOper deviceOper = new DeviceOper();
        deviceOper.setTopenTime(DateUtils.getCurrentDateTime());                      //开门时间
        deviceOper.setSmemberId(sessionVo.getUserId());                            // 会员ID
        deviceOper.setSmemberCode(sessionVo.getUserCode());                        // 会员编号
        deviceOper.setSmemberName(sessionVo.getUserName());                        // 会员用户名（手机号）
        deviceOper.setItype(sessionVo.getTypes());//开门类型
        deviceOper.setSip(ip);                                      // 访问ip
        deviceOper.setSdeviceCode(sessionVo.getDeviceCode());                      // 设备编号
        deviceOper.setTaddTime(DateUtils.getCurrentDateTime());     // 添加时间
        deviceOper.setIclientType(sessionVo.getIsourceClientType());
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
     * @param deviceCode
     * @param userId
     * @param type
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
}
