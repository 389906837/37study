package com.cloud.cang.act.ws;

import com.cloud.cang.act.OrderInvocationActivityDto;
import com.cloud.cang.act.ac.ext.OrderActivityServiceProxy;
import com.cloud.cang.act.ac.service.ActivityConfService;
import com.cloud.cang.act.ac.service.DiscountRecordService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 查看设备活动
 *
 * @author yanlingfeng
 * @version 1.0
 */
@RestController
@RequestMapping("/orderActivityService")
@RegisterRestResource
public class OrderActivityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderActivityService.class);
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private OrderActivityServiceProxy orderActivityServiceProxy;

    /**
     * 查看设备活动
     *
     * @param deviceId 设备Id
     * @return ResponseVo<List <String>> 设备活动信息
     */
    @SuppressWarnings({"unchecked"})

    @RequestMapping(value = "/queryDeviceActivity", method = RequestMethod.POST)
    public ResponseVo<List<String>> queryDeviceActivity(@RequestBody String deviceId) {
        LOGGER.info("开始查询设备活动,设备ID{}" + deviceId);
        try {
            if (StringUtil.isBlank(deviceId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备Id不能为空");
            }
            List<String> lists = activityConfService.viewActivityInformation(deviceId);
            LOGGER.info("查询设备活动成功,活动有{}" + lists);
            return ResponseVo.getSuccessResponse(lists);
        } catch (Exception e) {
            LOGGER.error("查看设备活动信息失败", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("查看设备活动信息失败");
        }
    }

    /**
     * 更新活动优惠记录服务
     * 1.增加活动优惠记录表
     * 2.更新活动配置表参与人数
     *
     * @param orderInvocationActivityDto
     * @return ResponseVo 设备活动信息
     */
    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/upDiscountRecord", method = RequestMethod.POST)
    public ResponseVo upDiscountRecord(@RequestBody OrderInvocationActivityDto orderInvocationActivityDto) {
        LOGGER.info("创建活动优惠记录开始");
        ResponseVo<List<String>> responseVo = null;
        try {
            // 校验基础参数
            ResponseVo<String> validateResult = validateCreateParam(orderInvocationActivityDto);
            if (!validateResult.isSuccess()) {
                LOGGER.info("创建活动优惠记录参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            responseVo = orderActivityServiceProxy.upDiscountRecord(orderInvocationActivityDto);
            if (!responseVo.isSuccess()) {
                LOGGER.info("调用活动失败.....");
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用活动失败");
            }
            return responseVo;
        } catch (Exception e) {
            LOGGER.error("调用活动异常.....", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("调用活动失败");
        }

    }

    /**
     * 创建活动优惠记录参数是否有效
     *
     * @param orderInvocationActivityDto
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<String> validateCreateParam(OrderInvocationActivityDto orderInvocationActivityDto) {
        LOGGER.info("创建订单校验参数开始.....参数：{}", orderInvocationActivityDto);
        if (StringUtils.isBlank(orderInvocationActivityDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员Id不能为空");
        }
        if (StringUtils.isBlank(orderInvocationActivityDto.getSmemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员Code不能为空");
        }
        if (StringUtils.isBlank(orderInvocationActivityDto.getSmemberName())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员名不能为空");
        }
        Integer itype = orderInvocationActivityDto.getUpType();
        if (null == itype || (itype.intValue() != 10 && itype.intValue() != 20 && itype.intValue() != 30)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("更新类型不能为空");
        }
        if (itype.intValue() != 30) {//不是下单更新 用户持有券信息不能为空
            if (StringUtils.isBlank(orderInvocationActivityDto.getScouponId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户持有券Id不能为空");
            }
            if (StringUtils.isBlank(orderInvocationActivityDto.getScouponCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户持有券Code不能为空");
            }
        }
        LOGGER.info("创建订单校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }
}
