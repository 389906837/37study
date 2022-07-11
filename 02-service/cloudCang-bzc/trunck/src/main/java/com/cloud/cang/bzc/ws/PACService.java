package com.cloud.cang.bzc.ws;

import com.cloud.cang.bzc.hy.service.IntegralAccountService;
import com.cloud.cang.bzc.hy.service.IntegralSerialService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.jy.OrderCommDto;
import com.cloud.cang.jy.OrderCommodityDto;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sp.CommodityInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 积分变更服务
 * Created by yan on 2018/1/10.
 */

@RestController
@RequestMapping("/pACService")
@RegisterRestResource
public class PACService {
    private static final Logger logger = LoggerFactory.getLogger(PACService.class);
@Autowired
IntegralAccountService integralAccountService;
@Autowired
IntegralSerialService integralSerialService;
    /**
     * 积分变更服务
     *
     * @param changeIntegralDto
     * @return ResponseVo
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/changeIntegral", method = RequestMethod.POST)
    public ResponseVo changeIntegral(ChangeIntegralDto changeIntegralDto) {
        logger.debug("积分变更服务开始...");
        ResponseVo<OrderRecord> responseVo = ResponseVo.getSuccessResponse();
        try {
            // 校验基础参数
            ResponseVo<String> validateResult = validateCreateParam(changeIntegralDto);
            if (!validateResult.isSuccess()) {
                logger.info("积分变更服务参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
           ResponseVo res= integralAccountService.changeIntegralAccoun(changeIntegralDto);//更新积分主表
            if (!res.isSuccess()) {
                logger.info("更新积分主表失败：{}", res.getMsg());
                return ErrorCodeEnum.ERROR_PAC_CHANGE_INTEGRAL.getResponseVo(res.getMsg());
            }
            ResponseVo res2=   integralSerialService.changeIntegralSeria(changeIntegralDto);//更新积分从表
            if(!res2.isSuccess()){
                logger.info("更新积分从表失败：{}", res.getMsg());
                return ErrorCodeEnum.ERROR_PAC_CHANGE_INTEGRAL.getResponseVo(res.getMsg());
            }
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("积分变更服务异常");
            logger.error("积分变更服务异常：", e);
        }
        return responseVo;
    }

    /**
     * 验证积分变更参数是否有效
     *
     * @param changeIntegralDto
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<String> validateCreateParam(ChangeIntegralDto changeIntegralDto) {
        logger.info("积分变更校验参数开始.....参数：{}", changeIntegralDto);
        if(null==changeIntegralDto){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("变更积分类不能为空！");
        }
        if(StringUtils.isBlank(changeIntegralDto.getSmemberId())){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户ID不能为空！");
        }
        if(StringUtils.isBlank(changeIntegralDto.getSmemberName())){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户姓名不能为空！");
        }
        if(null == changeIntegralDto.getFvalue()){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("积分值不能为空！");
        }
        if(null==changeIntegralDto.getIrequestSource()){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("积分来源不能为空！");
        }
        if(null==(changeIntegralDto.getIsourceType())){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户来源业务类型不能为空！");
        }

        logger.debug("积分变更服务校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }
}