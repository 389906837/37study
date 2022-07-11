package com.cloud.cang.open.api.ws;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.eye.sdk.bean.ResultBean;
import com.cloud.cang.eye.sdk.bean.UpdateBean;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.open.api.cr.service.ServerListService;
import com.cloud.cang.open.api.init.InitServer;
import com.cloud.cang.open.api.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.open.api.sb.service.DeviceUpgradeService;
import com.cloud.cang.recongition.ModelUpdateDto;
import com.cloud.cang.recongition.ServerUpdateDto;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentMap;


/**
 * 视觉服务操作
 */
@RestController
@RequestMapping("/vision")
@RegisterRestResource
public class RecongitionOperService {

    private final static Logger logger = LoggerFactory.getLogger(RecongitionOperService.class);
    @Autowired
    private ICached iCached;
    @Autowired
    private DeviceUpgradeService deviceUpgradeService;
    @Autowired
    private DeviceUpgradeDetailsService deviceUpgradeDetailsService;
    @Autowired
    private ServerListService serverListService;

    @RequestMapping(value = "/modelUpdate", method = RequestMethod.POST)
    public ResponseVo<String> modelUpdate(ModelUpdateDto updateDto) {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            logger.info("视觉模型更新，开始处理, 更新参数：{}", updateDto);
            if (null == updateDto || StringUtil.isBlank(updateDto.getModelAddress())
                    || StringUtil.isBlank(updateDto.getUpdateRecodeCode())) {
                responseVo.setSuccess(false);
                responseVo.setMsg("参数异常，请重新操作");
                return responseVo;
            }
            String addressType = "";
            if (null != updateDto.getAddressType()) {
                addressType = String.valueOf(updateDto.getAddressType());
            }
            UpdateBean update = new UpdateBean();
            update.setPath(updateDto.getModelAddress());
            update.setAddressType(addressType);
            String param = VisualUtils.getGson().toJson(update);
            Connection conn = null;
            ResultBean result = null;
            String resultStr = "";
            if (null == updateDto.getGpuCodes() || updateDto.getGpuCodes().size() == 0) {//本服务所有服务更新
                ConcurrentMap<String, Connection> map = VisualUtils.getGoodsRecognition().getConnsMap();
                if (null != map && map.size() > 0) {
                    List<String> gpuCodes = new ArrayList<>();
                    for (String serverCode : map.keySet()) {
                        gpuCodes.add(serverCode);
                    }
                    updateDto.setGpuCodes(gpuCodes);
                }
            }
            if (null != updateDto.getGpuCodes() && updateDto.getGpuCodes().size() > 0) {
                for (String serverCode : updateDto.getGpuCodes()) {
                    conn = VisualUtils.getGoodsRecognition().getConnsMap().get(serverCode);
                    if (null != conn) {
                        DeviceUpgrade deviceUpgrade = deviceUpgradeService.selectSyncByPrimaryKey(updateDto.getUpdateRecodeCode());
                        if (deviceUpgrade.getIstatus().intValue() != 40) {
                            resultStr = VisualUtils.getGoodsRecognition().updateFeatures(param, conn);
                            if (StringUtil.isNotBlank(resultStr)) {
                                logger.debug("视觉模型更新结果：{}", resultStr);
                                result = VisualUtils.getGson().fromJson(resultStr, ResultBean.class);
                                updateLogging(result, deviceUpgrade, serverCode);
                            }
                        }
                    }
                }
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("视觉模型更新异常：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setMsg("视觉模型更新异常，请重新操作");
        return responseVo;
    }

    @RequestMapping(value = "/serverUpdate", method = RequestMethod.POST)
    public ResponseVo<String> serverUpdate(ServerUpdateDto updateDto) {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            logger.info("视觉服务更新，开始处理, 更新参数：{}", updateDto);
            if (null == updateDto || StringUtil.isBlank(updateDto.getServerAddress())
                    || StringUtil.isBlank(updateDto.getUpdateRecodeCode())) {
                responseVo.setSuccess(false);
                responseVo.setMsg("参数异常，请重新操作");
                return responseVo;
            }
            String addressType = "";
            if (null != updateDto.getAddressType()) {
                addressType = String.valueOf(updateDto.getAddressType());
            }
            UpdateBean update = new UpdateBean();
            update.setPath(updateDto.getServerAddress());
            update.setAddressType(addressType);
            String param = VisualUtils.getGson().toJson(update);
            Connection conn = null;
            ResultBean result = null;
            String resultStr = "";
            if (null == updateDto.getGpuCodes() || updateDto.getGpuCodes().size() == 0) {//本服务所有服务更新
                ConcurrentMap<String, Connection> map = VisualUtils.getGoodsRecognition().getConnsMap();
                if (null != map && map.size() > 0) {
                    List<String> gpuCodes = new ArrayList<>();
                    for (String serverCode : map.keySet()) {
                        gpuCodes.add(serverCode);
                    }
                    updateDto.setGpuCodes(gpuCodes);
                }
            }
            if (null != updateDto.getGpuCodes() && updateDto.getGpuCodes().size() > 0) {
                for (String serverCode : updateDto.getGpuCodes()) {
                    conn = VisualUtils.getGoodsRecognition().getConnsMap().get(serverCode);
                    if (null != conn) {
                        DeviceUpgrade deviceUpgrade = deviceUpgradeService.selectSyncByPrimaryKey(updateDto.getUpdateRecodeCode());
                        if (null != deviceUpgrade && null != deviceUpgrade.getIstatus()
                                && deviceUpgrade.getIstatus().intValue() != 40) {
                            iCached.put(APIConstant.UPDATE_SERVER_MODEL + conn.getServerCode(), updateDto.getUpdateRecodeCode(), 60 * 60 * 2);
                            resultStr = VisualUtils.getGoodsRecognition().updateServer(param, conn);
                            if (StringUtil.isNotBlank(resultStr)) {
                                logger.debug("视觉服务更新结果：{}", resultStr);
                                result = VisualUtils.getGson().fromJson(resultStr, ResultBean.class);
                                updateLogging(result, deviceUpgrade, serverCode);
                            }
                        }
                    }
                }
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("视觉服务更新异常：{}", e);
        }
        responseVo.setSuccess(false);
        responseVo.setMsg("视觉服务更新异常，请重新操作");
        return responseVo;
    }

    /***
     * 更新 升级记录表
     * @param result 更新返回结果
     * @param deviceUpgrade 升级记录主表
     * @param serverCode 服务编号
     */
    private void updateLogging(ResultBean result, DeviceUpgrade deviceUpgrade, String serverCode) {
        String errorMsg = result.getMessage();
        Integer status = 20;
        if (!result.getCode().equals("0")) {//更新失败日志
            status = 30;
        }
        //修改更新日志
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("status", status);
        paramMap.put("errorMsg", errorMsg);
        paramMap.put("tendTime", new Date());
        paramMap.put("serverCode", serverCode);
        paramMap.put("smainId", deviceUpgrade.getId());
        Integer updateFlag = deviceUpgradeDetailsService.updateByMap(paramMap);
        if (null != updateFlag && updateFlag.intValue() > 0) {//更新主表
            if (deviceUpgrade.getIstatus().intValue() != 30) {
                DeviceUpgrade updateObj = new DeviceUpgrade();
                updateObj.setId(deviceUpgrade.getId());
                int total = 0;
                if (status == 30) {
                    updateObj.setIupgradeFailNum(deviceUpgrade.getIupgradeFailNum() != null ? (deviceUpgrade.getIupgradeFailNum() + 1) : 1);
                    if (null == deviceUpgrade.getIupgradeSuccessNum()) {
                        deviceUpgrade.setIupgradeSuccessNum(0);
                    }
                    total = deviceUpgrade.getIupgradeSuccessNum() + updateObj.getIupgradeFailNum();
                } else if (status == 20) {
                    updateObj.setIupgradeSuccessNum(deviceUpgrade.getIupgradeSuccessNum() != null ? (deviceUpgrade.getIupgradeSuccessNum() + 1) : 1);
                    if (null == deviceUpgrade.getIupgradeFailNum()) {
                        deviceUpgrade.setIupgradeFailNum(0);
                    }
                    total = updateObj.getIupgradeSuccessNum() + deviceUpgrade.getIupgradeFailNum();
                    updateObj.setIstatus(50);
                }
                if (total >= deviceUpgrade.getInoticeNum()) {
                    updateObj.setIstatus(30);
                }
                deviceUpgradeService.updateBySelective(updateObj);
            }
        }
    }

    /**
     * GPU服务器初始化
     *
     * @return ResponseVo
     */
    @RequestMapping(value = "/gpuServerInit", method = RequestMethod.POST)
    public ResponseVo<String> gpuServerInit(@RequestBody  String ip) {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            logger.info("GPU服务器初始化开始！");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sip", ip);
            serverListService.serverInit(map);
        } catch (Exception e) {
            logger.error("GPU服务器初始化异常：{}", e);
            responseVo.setSuccess(false);
            responseVo.setMsg("GPU服务器初始化异常！");
            return responseVo;
        }
        return responseVo;
    }
}
