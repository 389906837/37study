package com.cloud.cang.bzc.ws;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.bzc.om.service.OrderRecordService;
import com.cloud.cang.bzc.sm.service.DeviceStockService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.inventory.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouhong
 * @ClassName: OrderService
 * @Description: 底层盘点服务
 */
@RestController
@RequestMapping("/inventoryService")
@RegisterRestResource
public class InventoryService {


    @Autowired
    private DeviceStockService deviceStockService;
    @Autowired
    private OrderRecordService orderRecordService;
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    /**
     * 处理底层盘点服务
     * @param inventoryDto 盘点参数
     * @return Integer 10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dealwith", method = RequestMethod.POST)
    public ResponseVo<InventoryResult> dealwithInventory(@RequestBody InventoryDto inventoryDto) {
        logger.debug("处理底层盘点服务开始...");
        try {
            ResponseVo<InventoryResult> responseVo = null;
            // 校验基础参数
            ResponseVo<Integer> validateResult = validateParam(inventoryDto);
            if (!validateResult.isSuccess()) {
                logger.info("底层盘点服务参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            // 处理底层盘点业务
            responseVo = deviceStockService.dealwithInventory(inventoryDto);
            if (!responseVo.isSuccess()) {
                logger.info("处理底层盘点服务失败：{}", JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(responseVo.getMsg());
            }

           /* if (responseVo.getData().getItype().intValue() == 10) {//创建代扣支付订单成功
                orderRecordService.createPayOrder(responseVo.getData().getMerchantCode(), responseVo.getData().getOrderRecords());
            }*/
            return responseVo;
        } catch (ServiceException e) {
            logger.error("处理底层盘点系统处理异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("处理底层盘点系统异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("处理异常，系统繁忙");
        }
    }


    /**
     * 关门时，根据商品数量差生成订单服务
     *
     * @param inventoryDiffDto 盘点参数
     * @return Integer 10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功
     *
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/dealwithCommodityDiff", method = RequestMethod.POST)
    public ResponseVo<InventoryDiffResult> dealwithCommodityDiff(@RequestBody InventoryDiffDto inventoryDiffDto) {
        logger.debug("根据商品数量差生成订单服务开始...");
        try {
            ResponseVo<InventoryDiffResult> responseVo = null;
            // 校验基础参数
            ResponseVo<Integer> validateResult = validateParam(inventoryDiffDto);
            if (!validateResult.isSuccess()) {
                logger.info("根据商品数量差生成订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            // 根据商品数量差生成订单
            responseVo = deviceStockService.dealwithCommodityDiff(inventoryDiffDto);
            if (!responseVo.isSuccess()) {
                logger.info("根据商品数量差生成订单服务失败：{}", JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(responseVo.getMsg());
            }
            return responseVo;
        } catch (ServiceException e) {
            logger.error("根据商品数量差生成订单系统处理异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("根据商品数量差生成订单系统异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("处理异常，系统繁忙");
        }
    }

    /**
     * 开门前盘货对比上一次关门库存
     *
     * @param beforeOpenDoorInventoryDto 盘点参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/beforeOpenDoorInventoryService", method = RequestMethod.POST)
    public ResponseVo<String> beforeOpenDoorInventoryService(@RequestBody BeforeOpenDoorInventoryDto beforeOpenDoorInventoryDto) {
        logger.debug("开门前盘货对比上一次关门库存开始...");
        try {
            ResponseVo<String> responseVo = null;
            // 校验基础参数
            if (StringUtils.isBlank(beforeOpenDoorInventoryDto.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备ID不能为空");
            }
            // 开门前盘货对比上一次关门库存
            responseVo = deviceStockService.dealwithBeforeOpenDoorInventory(beforeOpenDoorInventoryDto);
            if (!responseVo.isSuccess()) {
                logger.info("开门前盘货对比上一次关门库存服务失败：{}", JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(responseVo.getMsg());
            }
            return responseVo;
        } catch (ServiceException e) {
            logger.error("开门前盘货对比上一次关门库存处理异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("开门前盘货对比上一次关门库存异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("处理异常，系统繁忙");
        }
    }


    /**
     * 创建实时订单
     * @param orderDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createRealTimeOrder", method = RequestMethod.POST)
    public ResponseVo<RealTimeOrderResult> createRealTimeOrder(@RequestBody RealTimeOrderDto orderDto) {
        logger.debug("创建实时订单服务开始...");
        try {
            ResponseVo<RealTimeOrderResult> responseVo = null;
            // 校验基础参数
            if (StringUtils.isBlank(orderDto.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
            }
            if (StringUtils.isBlank(orderDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员信息不能为空");
            }
            if (null == orderDto.getIsourceClientType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单来源类型不能为空");
            }
            // 处理底层盘点业务
            responseVo = orderRecordService.createRealTimeOrder(orderDto);
            if (!responseVo.isSuccess()) {
                logger.info("创建实时订单服务失败：{}", responseVo);
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建实时订单服务失败");
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("创建实时订单系统异常：", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建异常，系统繁忙");
        }
    }

    /**
     * 创建补货实时订单
     * @param orderDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createReplenishmentRealtimeOrder", method = RequestMethod.POST)
    public ResponseVo<ReplenishRealTimeOrderResult> createReplenishmentRealtimeOrder(@RequestBody ReplenishRealTimeOrderDto orderDto) {
        logger.debug("创建实时订单服务开始...");
        try {
            ResponseVo<ReplenishRealTimeOrderResult> responseVo = null;
            // 校验基础参数
            if (StringUtils.isBlank(orderDto.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
            }
            if (StringUtils.isBlank(orderDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员信息不能为空");
            }
            if (null == orderDto.getIsourceClientType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单来源类型不能为空");
            }
            // 处理底层盘点业务
            responseVo = orderRecordService.createReplenishRealTimeOrder(orderDto);
            if (!responseVo.isSuccess()) {
                logger.info("创建实时订单服务失败：{}", responseVo);
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建实时订单服务失败");
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("创建实时订单系统异常：", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建异常，系统繁忙");
        }
    }



    /**
     * 验证底层盘点服务 参数
     * @param inventoryDto 盘点参数
     * @return
     */
    private ResponseVo<Integer> validateParam(InventoryDto inventoryDto) {
        logger.info("底层盘点服务校验参数开始.....参数：{}", inventoryDto);
        if (StringUtils.isBlank(inventoryDto.getDeviceId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点设备信息不能为空");
        }
        if (null == inventoryDto.getInventoryType()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点类型不能为空");
        }
        if (inventoryDto.getInventoryType().intValue() == 10 && null == inventoryDto.getCloseType()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点关门类型不能为空");
        }

        logger.debug("底层盘点服务校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 验证关门商品数量差值计算服务 参数
     * @param inventoryDiffDto 盘点参数
     * @return
     */
    private ResponseVo<Integer> validateParam(InventoryDiffDto inventoryDiffDto) {
        logger.info("底层盘点服务校验参数开始.....参数：{}", inventoryDiffDto);
        if (StringUtils.isBlank(inventoryDiffDto.getDeviceId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点设备信息不能为空");
        }
        if (null == inventoryDiffDto.getInventoryType()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点类型不能为空");
        }
        if (inventoryDiffDto.getInventoryType().intValue() == 10 && null == inventoryDiffDto.getCloseType()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("盘点关门类型不能为空");
        }

        logger.debug("底层盘点服务校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 根据商品差创建实时订单
     * @param realTimeCommodityDiffOrderDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createCommodityDiffRealTimeOrder", method = RequestMethod.POST)
    public ResponseVo<RealTimeOrderResult> createCommodityDiffRealTimeOrder(@RequestBody RealTimeCommodityDiffOrderDto realTimeCommodityDiffOrderDto) {
        logger.debug("创建实时订单服务开始...");
        try {
            ResponseVo<RealTimeOrderResult> responseVo = null;
            // 校验基础参数
            if (StringUtils.isBlank(realTimeCommodityDiffOrderDto.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
            }
            if (StringUtils.isBlank(realTimeCommodityDiffOrderDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员信息不能为空");
            }
            if (null == realTimeCommodityDiffOrderDto.getIsourceClientType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单来源类型不能为空");
            }
            if (CollectionUtils.isEmpty(realTimeCommodityDiffOrderDto.getInventoryCommodityDiffVoList())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品集合不能为空");
            }
            // 处理底层盘点业务
            responseVo = orderRecordService.createRealTimeOrderByCommodityDiff(realTimeCommodityDiffOrderDto);
            if (!responseVo.isSuccess()) {
                logger.info("根据商品差创建实时订单服务失败：{}", responseVo);
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("根据商品差创建实时订单服务失败");
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("根据商品差创建实时订单系统异常：", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建异常，系统繁忙");
        }
    }

    /**
     * 根据商品差创建补货实时订单
     *
     * @param orderDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createCommodityDiffReplenishmentRealtimeOrder", method = RequestMethod.POST)
    public ResponseVo<ReplenishRealTimeOrderResult> createCommodityDiffReplenishmentRealtimeOrder(@RequestBody RealTimeCommodityDiffOrderDto orderDto) {
        logger.debug("创建实时订单服务开始...");
        try {
            ResponseVo<ReplenishRealTimeOrderResult> responseVo = null;
            // 校验基础参数
            if (StringUtils.isBlank(orderDto.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备信息不能为空");
            }
            if (StringUtils.isBlank(orderDto.getMemberId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员信息不能为空");
            }
            if (null == orderDto.getIsourceClientType()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单来源类型不能为空");
            }
            // 处理底层盘点业务
            responseVo = orderRecordService.createReplenishRealTimeOrderByCommodityDiff(orderDto);
            if (!responseVo.isSuccess()) {
                logger.info("创建实时订单服务失败：{}", responseVo);
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建实时订单服务失败");
            }
            return responseVo;
        } catch (Exception e) {
            logger.error("创建实时订单系统异常：", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建异常，系统繁忙");
        }
    }



}
