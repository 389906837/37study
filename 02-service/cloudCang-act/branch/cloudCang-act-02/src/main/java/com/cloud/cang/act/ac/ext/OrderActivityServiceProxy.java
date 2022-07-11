package com.cloud.cang.act.ac.ext;

import com.cloud.cang.act.OrderInvocationActivityDto;
import com.cloud.cang.act.ac.service.ActivityConfService;
import com.cloud.cang.act.ac.service.CouponUserService;
import com.cloud.cang.act.ac.service.DiscountDetailService;
import com.cloud.cang.act.ac.service.DiscountRecordService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.ac.CouponUser;
import com.cloud.cang.model.ac.DiscountDetail;
import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单活动代理类
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Service
public class OrderActivityServiceProxy {

    private static final Logger logger = LoggerFactory.getLogger(OrderActivityServiceProxy.class);

    @Autowired
    private DiscountRecordService discountRecordService;
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private DiscountDetailService discountDetailService;


    @Transactional
    public ResponseVo upDiscountRecord(OrderInvocationActivityDto orderInvocationActivityDto) {
        try {
           /* 来源类型10:批量下发券 - 平台赠送
                      20:活动赠送 - 券服务
                      30:促销活动 - 下单*/
            int type = orderInvocationActivityDto.getUpType();
            //查看活动配置表
            //来源单据类型

            DiscountRecord discountRecord = new DiscountRecord();
            BeanUtils.copyProperties(discountRecord, orderInvocationActivityDto);

            if (10 == type) {
                //商户赠送,批量下发
                //1.新增活动优惠记录表状态
                String scode = CoreUtils.newCode(EntityTables.AC_DISCOUNT_RECORD);
                discountRecord.setTdiscountTime(DateUtils.getCurrentDateTime());
                discountRecord.setTupdateTime(DateUtils.getCurrentDateTime());
                discountRecord.setTaddTime(DateUtils.getCurrentDateTime());
                discountRecord.setIstatus(10);//10  未使用;20  已使用;30  已过期
                discountRecord.setScode(scode);
                discountRecordService.insert(discountRecord);
                logger.info("商户赠送,生成活动优惠记录表成功...");
            } else if (20 == type) {
                //活动赠送
                //1.新增活动优惠记录表
                //2.更新活动配置表参与人数

                ActivityConf activityConf = activityConfService.selectByKeyLocked(orderInvocationActivityDto.getSacId());
                if (null == activityConf) {
                    logger.error("活动不存在");
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠记录更新失败，活动不存在");
                }
              /*  List<DiscountDetail> discountDetails = discountDetailService.selectBySacCode(activityConf.getSconfCode());*/
                String scode = CoreUtils.newCode(EntityTables.AC_DISCOUNT_RECORD);
                discountRecord.setTdiscountTime(DateUtils.getCurrentDateTime());//参与优惠时间
                discountRecord.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
                discountRecord.setTaddTime(DateUtils.getCurrentDateTime());//添加时间
                discountRecord.setSacCode(activityConf.getSconfCode());//活动编号
                discountRecord.setIacType(activityConf.getItype());//活动分类10:场景活动20:促销活动
                discountRecord.setSacName(activityConf.getSconfName());//活动名
              /*  discountRecord.setIdiscountWay(activityConf.getIdiscountWay());*///优惠方式（大类）
               /* if (null != discountDetails && null != discountDetails.get(0)) {
                    discountRecord.setIdiscountType(discountDetails.get(0).getIdiscountType());//优惠类型(小类)
                }*/
                discountRecord.setIstatus(10);//10  未使用;20  已使用;30  已过期
                discountRecord.setScode(scode);
                discountRecordService.insert(discountRecord);
                logger.info("活动赠送服务,生成活动优惠记录表成功...");

                ActivityConf update = new ActivityConf();
                update.setId(activityConf.getId());
                update.setIjoinNum(activityConf.getIjoinNum() + 1);//已参与人数
                update.setTupdateTime(DateUtils.getCurrentDateTime());
                activityConfService.updateBySelective(update);
                logger.info("活动赠送服务,更新活动配置表参与人数成功...");

            } else if (30 == type) {
                //活动Id不为空,则新增
                //1.新增活动优惠记录表
                //2.更新活动配置表参与和使用人数
                if (StringUtil.isNotBlank(orderInvocationActivityDto.getSacId())) {
                    String[] sacid = orderInvocationActivityDto.getSacId().split(",");
                    for (int x = 0; x < sacid.length; x++) {
                        ActivityConf activityConf = activityConfService.selectByKeyLocked(orderInvocationActivityDto.getSacId());
                        if (null == activityConf) {
                            logger.error("优惠记录更新失败,活动不存在");
                            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠记录更新失败，活动不存在");
                        }
                        List<DiscountDetail> discountDetails = discountDetailService.selectBySacCode(activityConf.getSconfCode());
                        String scode = CoreUtils.newCode(EntityTables.AC_DISCOUNT_RECORD);
                        discountRecord.setTdiscountTime(DateUtils.getCurrentDateTime());//参与优惠时间
                        discountRecord.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
                        discountRecord.setTaddTime(DateUtils.getCurrentDateTime());//添加时间
                        discountRecord.setIstatus(20);//10  未使用;20  已使用;30  已过期
                        discountRecord.setSacId(sacid[x]);//活动Id
                        discountRecord.setSacCode(activityConf.getSconfCode());//活动编号
                        discountRecord.setIacType(activityConf.getItype());//活动分类10:场景活动20:促销活动
                        discountRecord.setSacName(activityConf.getSconfName());//活动名
                        discountRecord.setIdiscountWay(activityConf.getIdiscountWay());//优惠方式（大类）
                        if (null != discountDetails && null != discountDetails.get(0)) {
                            discountRecord.setIdiscountType(discountDetails.get(0).getIdiscountType());//优惠类型(小类)
                        }
                        discountRecord.setScode(scode);
                        discountRecord.setScouponCode(null);
                        discountRecord.setScouponId(null);
                        discountRecordService.insert(discountRecord);
                        logger.info("下单促销活动,生成活动优惠记录表成功...");

                        ActivityConf update = new ActivityConf();
                        update.setId(activityConf.getId());
                        update.setIjoinNum(activityConf.getIjoinNum() + 1);//已参与人数
                        update.setIusedNum(activityConf.getIusedNum() + 1);//已使用人数
                        update.setTupdateTime(DateUtils.getCurrentDateTime());
                        activityConfService.updateBySelective(update);

                        logger.info("下单促销活动,更新活动配置表参与、使用人数成功...");
                    }
                }
                if (StringUtil.isNotBlank(orderInvocationActivityDto.getScouponId())) { //券使用Id不为空
                    //优惠券下单
                    DiscountRecord discountRecord1 = discountRecordService.selectByMmberIdAndCouponId(discountRecord.getSmemberId(), orderInvocationActivityDto.getScouponId());
                    if (null != discountRecord1) {//1.更新活动优惠记录表状态
                        DiscountRecord upDiscountRecord = new DiscountRecord();
                        upDiscountRecord.setId(discountRecord1.getId());
                        upDiscountRecord.setIstatus(20);//10  未使用;20  已使用;30  已过期
                        upDiscountRecord.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
                        discountRecordService.updateBySelective(upDiscountRecord);
                        logger.info("优惠券下单,更新活动优惠记录表状态成功！");
                    }
                    //2.查看活动配置表 并且更新活动使用人数
                    if (StringUtils.isNotBlank(discountRecord1.getSacId())) {
                        ActivityConf activityConf = activityConfService.selectByKeyLocked(discountRecord1.getSacId());
                        if (null == activityConf) {
                            logger.error("活动不存在");
                            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("优惠记录更新失败，活动不存在");
                        }
                        ActivityConf update = new ActivityConf();
                        update.setId(activityConf.getId());
                        update.setIusedNum(activityConf.getIusedNum() + 1);//已使用人数
                        update.setTupdateTime(DateUtils.getCurrentDateTime());
                        activityConfService.updateBySelective(update);
                        logger.info("优惠券下单,更新活动配置表使用人数成功...");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("优惠记录更新失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("优惠记录更新失败");
        }
        return ResponseVo.getSuccessResponse();

    }
}
