package com.cloud.cang.api.sb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.api.common.ReturnCodeEnum;
import com.cloud.cang.api.common.utils.CommonUtils;
import com.cloud.cang.api.netty.vo.send.ControlDeviceModel;
import com.cloud.cang.api.netty.vo.send.MessageBody;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.NettyConst;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.SysParaUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.AdListModel;
import com.cloud.cang.model.AdModel;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.wz.Advertis;
import com.cloud.cang.sb.DeviceDto;
import com.cloud.cang.utils.StringUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoServiceImpl extends GenericServiceImpl<DeviceInfo, String> implements
        DeviceInfoService {

    @Autowired
    DeviceInfoDao deviceInfoDao;

    @Autowired
    private ICached iCached;

    @Autowired
    private DeviceRegisterService deviceRegisterService;


    @Override
    public GenericDao<DeviceInfo, String> getDao() {
        return deviceInfoDao;
    }


    /**
     * 向设备发送消息
     *
     * @param deviceDto 服务
     * @return 发送消息结果，成功返回success，失败返回错误码
     * @throws Exception
     */
    @Override
    public ResponseVo<String> operateDevice(DeviceDto deviceDto) throws Exception {
        //从redis中获取客户端连接的通道
        Map<String, ChannelHandlerContext> channelMap = (Map<String, ChannelHandlerContext>) iCached.get("synclientMap");

        //缓存中设备通信channel不为空
        if (MapUtils.isNotEmpty(channelMap)) {
            List<String> clientIds = CommonUtils.stringToList(deviceDto.getId());//分割设备ID----ID为“，”拼接字符串
            String method = deviceDto.getFunction();//要执行的操作名称
            String params = deviceDto.getOperateParam();//方法参数
            if (CollectionUtils.isNotEmpty(clientIds)) {
                if (clientIds.size() == 1) {    //一台设备
                    ControlDeviceModel msg = new ControlDeviceModel();
                    DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(clientIds.get(0));
                    if (deviceInfo.getIstatus() == 20) {    /* 状态：10=未注册 20=正常 30=故障 40=报废 */
                        MessageBody messageBody = new MessageBody();
                        messageBody.setType(1);
                        msg.setData(messageBody);
                        String resultMsg = JSON.toJSONString(msg);
//                        msgVo.setData(method);//发送给客户端的需要执行的操作
//                        msgVo.setParams(params);//方法参数
                        ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                        ChannelFuture channelFuture = ctx.writeAndFlush(resultMsg + System.lineSeparator());//消息+系统换行符
                        for (; ; ) {
                            if (channelFuture.isSuccess()) {
                                //查询数据库看消息是否发送成功

                                return ResponseVo.getSuccessResponse(deviceDto.getId());//
                            }
                        }
                    }
                } else {
                    for (String id : clientIds) {   //多台设备
                        ControlDeviceModel msg = new ControlDeviceModel();

                        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(id);
                        if (deviceInfo.getIstatus() == 20) {    /* 状态：10=未注册 20=正常 30=故障 40=报废 */
                            MessageBody messageBody = new MessageBody();

                            messageBody.setType(1);//发送给客户端的需要执行的操作

                            msg.setData(messageBody);
                            ChannelHandlerContext ctx = channelMap.get(clientIds);
//                        ctx.writeAndFlush(msgVo + "\r\n");
                            ctx.writeAndFlush(msg + System.lineSeparator());//消息+系统换行符
                        }
                    }
                }
                return ResponseVo.getSuccessResponse(deviceDto.getId());//发送消息完成后返回
            } else {
                return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_PARAM_FAILED.getCode(), "ID不存在");
            }
        } else {
            return new ResponseVo<String>(false, ReturnCodeEnum.ERROR_CHANNEL.getCode(), "设备未连接到服务器");
        }
    }


    /**
     * 通过商户ID查询商户下正常使用的设备ID
     * @param merchantId 商户ID
     * @return
     */
    @Override
    public List<String> selectDeviceIdByMerchantId(String merchantId) {
        return deviceInfoDao.selectIdByMerchantId(merchantId);
    }

    /**
     * 查询商户下的设备运营位1的广告信息
     * @param deviceId
     * @return
     */
    @Override
    public ResponseVo<AdListModel> selectOperatingAdOne(String deviceId) throws Exception {
        return getAdListModelByRegion(deviceId, NettyConst.AD_ONE_NUMBER);
    }

    /**
     * 查询商户下的设备运营位2的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModel> selectOperatingAdTwo(String deviceId) throws Exception {
        return getAdListModelByRegion(deviceId, NettyConst.AD_TWO_NUMBER);
    }

    /**
     * 查询商户下的设备运营位3的广告信息
     * @param deviceId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<AdListModel> selectOperatingAdThree(String deviceId) throws Exception {
        return getAdListModelByRegion(deviceId, NettyConst.AD_THREE_NUMBER);
    }

    /**
     * 转换从缓存中获取的数据对象
     *
     * @param deviceId
     * @return
     */
    private ResponseVo<AdListModel> getAdListModelByRegion(String deviceId, String region) throws Exception {
        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(deviceId);
        List<Advertis> advertisList = new ArrayList<>();
        if (null != deviceInfo) {
            String smerchantCode = deviceInfo.getSmerchantCode();
            if (StringUtils.isNotBlank(smerchantCode)) {
                String catcheKey = RedisConst.WZ_REGIO_DETAIL_ + region + "_" + smerchantCode;
                String json = (String) iCached.hget(RedisConst.WZ_REGIO_ADVERTIS + smerchantCode, catcheKey); // 从缓存中取值
                if (StringUtil.isNotBlank(json)) {
                    advertisList = JSONObject.parseArray(json, Advertis.class);
                    if (CollectionUtils.isNotEmpty(advertisList)) {
                        List<AdModel> adModelList = new ArrayList<>();
                        AdModel adModel = null;
                        String preUrl = SysParaUtil.getValue(NettyConst.FTP_IMAGE_PATH);        //  图片服务器地址前缀
                        for (Advertis ad : advertisList) {
                            adModel = new AdModel();
                            String adId = ad.getId();
                            String adUrl = preUrl + ad.getScontentUrl();
                            Integer istatus = ad.getIstatus();  // 0: INVALID:已过期 1: USING:投放中 2: WAIT:待投放 3: PAUSE:暂停
                            if (StringUtils.isNotBlank(adId) && StringUtils.isNotBlank(adUrl) && istatus.intValue() == 1) {
                                adModel.setAdId(adId);
                                adModel.setScontentUrl(adUrl);
                                adModel.setIadvType(ad.getIadvType());
                                adModel.setShref(ad.getShref());
                                adModel.setIlinkType(ad.getIlinkType());
                                adModelList.add(adModel);
                            }
                        }
                        AdListModel adListModel = new AdListModel();
                        if (CollectionUtils.isNotEmpty(adModelList)) {
                            adListModel.setAdModelList(adModelList);
                            return ResponseVo.getSuccessResponse(adListModel);
                        }
                    }
                }

            }
        }

        return ReturnCodeEnum.ERROR_NO_AD.getResponseVo("没有查到广告");
    }

}