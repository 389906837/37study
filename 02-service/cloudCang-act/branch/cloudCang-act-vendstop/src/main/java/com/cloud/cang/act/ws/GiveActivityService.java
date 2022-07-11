package com.cloud.cang.act.ws;

import com.cloud.cang.act.CouponGiveResult;
import com.cloud.cang.act.GiveActivityDto;
import com.cloud.cang.act.GiveActivityResult;
import com.cloud.cang.act.GiveResultQueryDto;
import com.cloud.cang.act.ac.ext.GiveActivityProxy;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.act.common.ActivityConstants;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.ac.CouponUser;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 活动赠送服务
 *
 * @author yanlingfeng
 * @version 1.0
 */
@RestController
@RequestMapping("/giveActivityService")
@RegisterRestResource
public class GiveActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiveActivityService.class);

    @Autowired
    private GiveActivityProxy giveActivityProxy;
    @Autowired
    private CouponUserService couponUserService;

    private Interner<String> pool = Interners.newWeakInterner();

    /**
     * 活动赠送服务
     *
     * @return ResponseVo<GiveActivityResult>
     * @Param GiveActivityDto
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/give", method = RequestMethod.POST)
    public ResponseVo<GiveActivityResult> give(
            @RequestBody GiveActivityDto giveActivityDto) {
        LOGGER.info("##############活动赠送服务开始###########：{}", giveActivityDto);
        try {
            //校验基础参数
            ResponseVo baseParamValidateResult = baseParamValidate(giveActivityDto);
            if (!baseParamValidateResult.isSuccess()) {
                LOGGER.debug("校验基础参数失败：{}", baseParamValidateResult.getMsg());
                return baseParamValidateResult;
            }
            LOGGER.info("活动赠送服务参数校验成功！");
            synchronized (pool.intern(giveActivityDto.getMemberId())) {
                ResponseVo<GiveActivityResult> result = giveActivityProxy.activeGive(giveActivityDto);
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("活动奖励发放异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }


    }

    /**
     * 根据条件查询赠送券
     *
     * @param giveResultQueryDto
     * @return
     */
    @SuppressWarnings({"unchecked"})
    @RequestMapping(value = "/queryActiveGiveResult", method = RequestMethod.POST)
    public ResponseVo<GiveActivityResult> queryActiveGiveResult(
            @RequestBody GiveResultQueryDto giveResultQueryDto) {
        LOGGER.debug("查询活动发放结果开始,参数：{}", giveResultQueryDto);
        if (StringUtils.isBlank(giveResultQueryDto.getSourceCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("来源单据号不能为空！");
        }
        try {
            GiveActivityResult activeGiveResult = new GiveActivityResult();
            //查券
            CouponUser couponUserCond = new CouponUser();
            couponUserCond.setSsourceCode(giveResultQueryDto.getSourceCode());    //来源编号
            couponUserCond.setIsourceType(giveResultQueryDto.getSourceType());    //来源类型
            couponUserCond.setSmemberId(giveResultQueryDto.getMemberId());        //会员ID
            List<CouponUser> couponUserList = couponUserService.selectByEntityWhere(couponUserCond);
            if (couponUserList != null && couponUserList.size() > 0) {
                Map<String, List<CouponUser>> couponUserMap = new HashMap<String, List<CouponUser>>();

                List<CouponUser> list = null;
                //一次有不一样的活动 ，根据活动编号分组
                for (CouponUser couponUser : couponUserList) {
                    String key = couponUser.getSsourceAcCode();
                    if (couponUserMap.containsKey(key)) {
                        list = couponUserMap.get(key);
                    } else {
                        list = new ArrayList<CouponUser>();
                    }
                    list.add(couponUser);
                    couponUserMap.put(key, list);
                }
                List<CouponGiveResult> couponGiveResultList = new ArrayList<CouponGiveResult>();
                Set<Map.Entry<String, List<CouponUser>>> entryseSet = couponUserMap.entrySet();
                for (Map.Entry<String, List<CouponUser>> entry : entryseSet) {
                    List<CouponUser> resultList = entry.getValue();
                    //目前多张券只支持同一类型
                    CouponUser couponUser = resultList.get(0);
                    CouponGiveResult couponGiveResult = new CouponGiveResult();
                    couponGiveResult.setCount(resultList.size());//券的数量
                    couponGiveResult.setActivityCode(entry.getKey());//活动编号
                    couponGiveResult.setActivityName(couponUser.getSsourceAcName());//活动Name
                    couponGiveResult.setSbriefDesc(couponUser.getSbriefDesc());//活动描述
                    couponGiveResult.setActivityInstruction(couponUser.getScouponInstruction());//券说明
                    couponGiveResult.setCouponType(couponUser.getIcouponType());//券类型
                    couponGiveResult.setCouponTypeName(ActivityConstants.couponTypeMap.get(couponUser.getIcouponType()));//券类型名称
                    if (BizTypeDefinitionEnum.CouponType.COMMODITY.getCode() != couponUser.getIcouponType()) {
                        couponGiveResult.setCouponValue(couponUser.getFcouponValue().doubleValue());//券面值
                    } else {
                        couponGiveResult.setCouponValue(null);//券面值
                    }
                    couponGiveResult.setSourceInstruction(couponUser.getSsourceInstruction());//来源说明
                    couponGiveResult.setExpiryDate(couponUser.getDcouponExpiryDate());
                    couponGiveResult.setEffectiveDate(couponUser.getDcouponEffectiveDate());
                    couponGiveResultList.add(couponGiveResult);
                }

                activeGiveResult.setCouponGiveResultList(couponGiveResultList);
            }

            LOGGER.debug("查询活动发放结果结束。activeGiveResult：{}", activeGiveResult);
            return ResponseVo.getSuccessResponse(activeGiveResult);
        } catch (Exception e) {
            LOGGER.error("查询活动发放结果异常！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
        }
    }


    /**
     * 基础参数校验
     *
     * @param giveActivityDto
     * @return
     */
    @SuppressWarnings({"rawtypes"})
    private ResponseVo baseParamValidate(GiveActivityDto giveActivityDto) {
        LOGGER.info("基础参数校验开始...");
        if (giveActivityDto == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("基础参数对象不能为null！");
        }
        if (StringUtils.isBlank(giveActivityDto.getActiveConfCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("活动配置编号不能为空！");
        }
        if (giveActivityDto.getSsourceType() == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM
                    .getResponseVo("来源单据类型不能为空！");
        }

        if (StringUtils.isBlank(giveActivityDto.getMemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员编号不能为空！");
        }
        if (StringUtils.isBlank(giveActivityDto.getMemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员ID不能为空！");
        }
        if (StringUtils.isBlank(giveActivityDto.getMemberRealName())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员名不能为空！");
        }
        if (StringUtils.isBlank(giveActivityDto.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户Id不能为空");
        }
        if (StringUtils.isBlank(giveActivityDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户Code不能为空！");
        }

        LOGGER.info("基础参数校验成功...");
        return ResponseVo.getSuccessResponse();
    }

}
