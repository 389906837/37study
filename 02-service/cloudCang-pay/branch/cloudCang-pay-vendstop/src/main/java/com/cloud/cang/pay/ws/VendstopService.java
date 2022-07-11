package com.cloud.cang.pay.ws;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.rm.ReplenishmentCommodity;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.om.service.OrderCommodityService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.rm.service.ReplenishmentCommodityService;
import com.cloud.cang.pay.vendstop.VendstopClient;
import com.cloud.cang.pay.vendstop.VendstopConstant;
import com.cloud.cang.pay.vendstop.VendstopResponse;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.vendstop.dto.AuthUserDto;
import com.cloud.cang.vendstop.dto.AuthUserResponseDto;
import com.cloud.cang.vendstop.dto.PushOrderResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @program: 37cang
 * @description: 调用vendstop请求
 * @author: qzg
 * @create: 2019-08-08 13:13
 **/
@RestController
@RequestMapping("/payService")
@RegisterRestResource
public class VendstopService {
    private static final Logger logger = LoggerFactory.getLogger(VendstopService.class);
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private OrderCommodityService orderCommodityService;
    @Autowired
    private ReplenishmentCommodityService replenishmentCommodityService;
    /**
     * 推送订单
     * @param freePaymentDto 推送订单参数
     * @return
     */
    @RequestMapping(value = "/pushOrder", method = RequestMethod.POST)
    public ResponseVo<FreePaymentResult> pushOrder(@RequestBody FreePaymentDto freePaymentDto) {
        logger.info("====推送订单,请求参数：{}", freePaymentDto);
        ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //正常购物
            if(StringUtil.isBlank(freePaymentDto.getSremark())){
                logger.info("====推送订单,正常购物");
                return pushOrder_shopping(freePaymentDto);
            }

            //商品没有变化（游客）
            if("visitor".equals(freePaymentDto.getSremark())){
                logger.info("====推送订单,商品没有变化（游客）");
                return pushOrder_visitor(freePaymentDto);
            }

            //后台异常订单，补处理
            if("dealwith".equals(freePaymentDto.getSremark())){
                logger.info("====推送订单,异常订单补处理");
                return pushOrder_dealwith(freePaymentDto);
            }

            //补货员补货
            logger.info("====推送订单,补货员补货");
            return pushOrder_publisher(freePaymentDto);

        } catch (Exception e) {
            logger.error("服务异常：", e);
        }
        responseVo.setSuccess(false);
        responseVo.setErrorCode(-1000);
        responseVo.setMsg("推送订单异常");
        return responseVo;
    }

    private ResponseVo<FreePaymentResult> pushOrder_shopping(FreePaymentDto freePaymentDto) throws Exception{
        ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
        ResponseVo<String> validateResult = validatePaymentParam(freePaymentDto);
        if (!validateResult.isSuccess()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
        }
        //2,创建订单付款申请
        ResponseVo<OrderRecord> order_res = orderRecordService.createVendstopPay(freePaymentDto);
        if(order_res == null || !order_res.isSuccess()){
            responseVo.setSuccess(false);
            responseVo.setMsg(order_res.getMsg());
            return responseVo;
        }
        //3,向vendstop，推送订单数据
        OrderRecord orderRecord = order_res.getData();
        Map<String,Object> param_pushorder = builParam_shopping(freePaymentDto,orderRecord);
        VendstopResponse vendRes = VendstopClient.invoke(VendstopConstant.API.PUSH_ORDER,param_pushorder);
        if(vendRes.isSuccess()){
            // 更新状态：istatus = 10
            orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode());
            orderRecordService.updateBySelective(orderRecord);
            // 返回
            PushOrderResponseDto responseDto = JSON.parseObject(vendRes.getData(), PushOrderResponseDto.class);
            param_pushorder.put("id",responseDto.getTransactionId());
            param_pushorder.put("orderDate",orderRecord.getTorderTime().getTime());
            FreePaymentResult result = new FreePaymentResult();
            result.setPushOrderParam(param_pushorder);
            responseVo.setData(result);
            return responseVo;
        }else{
            //更新状态：istatus = 20
            orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode());
            orderRecordService.updateBySelective(orderRecord);
            // 返回
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg(vendRes.getError());
            return responseVo;
        }
    }

    private ResponseVo<FreePaymentResult> pushOrder_publisher(FreePaymentDto freePaymentDto) throws Exception{
        ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
        Map<String,Object> param_pushorder = builParam_publisher(freePaymentDto);
        VendstopResponse vendRes = VendstopClient.invoke(VendstopConstant.API.PUSH_ORDER,param_pushorder);
        if(vendRes.isSuccess()){
            PushOrderResponseDto responseDto = JSON.parseObject(vendRes.getData(), PushOrderResponseDto.class);
            param_pushorder.put("id",responseDto.getTransactionId());
            param_pushorder.put("orderDate",System.currentTimeMillis());
            FreePaymentResult result = new FreePaymentResult();
            result.setPushOrderParam(param_pushorder);
            responseVo.setData(result);
            return responseVo;
        }else{
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg(vendRes.getError());
            return responseVo;
        }
    }

    private ResponseVo<FreePaymentResult> pushOrder_visitor(FreePaymentDto freePaymentDto) throws Exception{
        ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();
        Map<String,Object> param_pushorder = builParam_visitor(freePaymentDto);
        VendstopResponse vendRes = VendstopClient.invoke(VendstopConstant.API.PUSH_ORDER,param_pushorder);
        return responseVo;
       /* if(vendRes.isSuccess()){
            PushOrderResponseDto responseDto = JSON.parseObject(vendRes.getData(), PushOrderResponseDto.class);
            param_pushorder.put("id",responseDto.getTransactionId());
            param_pushorder.put("orderDate",System.currentTimeMillis());
            FreePaymentResult result = new FreePaymentResult();
            result.setPushOrderParam(param_pushorder);
            responseVo.setData(result);
            return responseVo;
        }else{
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg(vendRes.getError());
            return responseVo;
        }*/
    }
    /**
     * 开门成功通知vendstop,
     * @param appSessionId
     * @return
     */
    @RequestMapping(value = "/openSuccess", method = RequestMethod.POST)
    public ResponseVo openSuccess(@RequestBody String appSessionId) {
        logger.info("====开门成功通知vendstop,请求参数：{}", appSessionId);
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("sessionId",appSessionId);
            VendstopResponse vendRes = VendstopClient.invoke(VendstopConstant.API.OPEN_SUCCESS,param);
            if(vendRes !=null && vendRes.isSuccess()){
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo();
    }


    private ResponseVo<String> validatePaymentParam(FreePaymentDto freePaymentDto) {
        if (StringUtil.isBlank(freePaymentDto.getSmemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户编号不能为空");
        } else if (StringUtil.isBlank(freePaymentDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员不能为空");
        } else if (StringUtil.isBlank(freePaymentDto.getSorderId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单不能为空");
        } else if (StringUtil.isBlank(freePaymentDto.getSsubject())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("付款订单标题不能为空");
        } else if (freePaymentDto.getIpayWay() == null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付方式不能为空");
        } else if (StringUtil.isBlank(freePaymentDto.getApiKey()) ||
                    StringUtil.isBlank(freePaymentDto.getSessionID()) ||
                    StringUtil.isBlank(freePaymentDto.getUserAuthToken())){
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("apiKey不能为空");
        }
        return ResponseVo.getSuccessResponse();
    }

    private Map<String,Object> builParam_shopping(FreePaymentDto dto, OrderRecord order){
        Map<String,Object> param_pushorder = new HashMap<>();
        // userAuthToken
        param_pushorder.put("userAuthToken",dto.getUserAuthToken());
        // sessionID
        param_pushorder.put("sessionID",dto.getSessionID());
        // totalAmount
        param_pushorder.put("totalAmount",order.getFactualPayAmount()+"");
        // orderDetails : {"deviceId":"手机设备ID",
        //                 "merchantId":"",
        //                 "machineCode:"售货机CODE",
        //                 "machineId":"售货机ID",
        //                 "items":[{"name":"","unitPrice","","price","","qty":""},{...}]}
        Map<String,Object> orderDetails = new HashMap<>();
        orderDetails.put("deviceId",dto.getAppDeviceId());
        orderDetails.put("merchantId",order.getSmerchantId());
        orderDetails.put("machineId",dto.getDeviceId());
        orderDetails.put("machineCode",dto.getDeviceCode());

        List<Map<String,Object>> items = new ArrayList<>();
        getOrderCommodityList(order).forEach((commodity)->{
            Map<String,Object> temp = new HashMap<>();
            temp.put("name",commodity.getScommodityName());
            temp.put("unitPrice",commodity.getFcommodityPrice()+"");
            temp.put("price",commodity.getFactualAmount()+"");
            temp.put("qty",commodity.getForderCount());
            items.add(temp);
        });
        orderDetails.put("items",items);
        param_pushorder.put("orderDetails",orderDetails);

        // deviceDetails : {"machineCode":"手机设备ID",
        //                 "machineId":""}
        Map<String,Object> deviceDetails = new HashMap<>();
        deviceDetails.put("machineId",dto.getDeviceId());
        deviceDetails.put("machineCode",dto.getDeviceCode());

        param_pushorder.put("deviceDetails",deviceDetails);
        return param_pushorder;
    }

    private Map<String,Object> builParam_publisher(FreePaymentDto dto){
        Map<String,Object> param_pushorder = new HashMap<>();
        // userAuthToken
        param_pushorder.put("userAuthToken",dto.getUserAuthToken());
        // sessionID
        param_pushorder.put("sessionID",dto.getSessionID());
        // totalAmount
        param_pushorder.put("totalAmount","0.00");
        // orderDetails : {"deviceId":"手机设备ID",
        //                 "merchantId":"",
        //                 "machineCode:"售货机CODE",
        //                 "machineId":"售货机ID",
        //                 "items":[{"name":"","unitPrice","","price","","qty":""},{...}]}
        Map<String,Object> orderDetails = new HashMap<>();
        orderDetails.put("deviceId",dto.getAppDeviceId());
        orderDetails.put("machineId",dto.getDeviceId());
        orderDetails.put("machineCode",dto.getDeviceCode());

        List<Map<String,Object>> items = new ArrayList<>();
        getReplenishmentCommodityList(dto.getSremark()).forEach((commodity)->{
            Map<String,Object> temp = new HashMap<>();
            temp.put("name",commodity.getScommodityName());
            temp.put("unitPrice",commodity.getFcommodityPrice()+"");
            temp.put("price",commodity.getFcommodityPrice()+"");
            temp.put("qty",commodity.getForderCount());
            temp.put("itype",commodity.getItype());
            items.add(temp);
        });
        orderDetails.put("items",items);
        param_pushorder.put("orderDetails",orderDetails);

        // deviceDetails : {"machineCode":"手机设备ID",
        //                 "machineId":""}
        Map<String,Object> deviceDetails = new HashMap<>();
        deviceDetails.put("machineId",dto.getDeviceId());
        deviceDetails.put("machineCode",dto.getDeviceCode());

        param_pushorder.put("deviceDetails",deviceDetails);
        return param_pushorder;
    }

    private Map<String,Object> builParam_visitor(FreePaymentDto dto){
        Map<String,Object> param_pushorder = new HashMap<>();
        // userAuthToken
        param_pushorder.put("userAuthToken",dto.getUserAuthToken());
        // sessionID
        param_pushorder.put("sessionID",dto.getSessionID());
        // totalAmount
        param_pushorder.put("totalAmount","0.00");
        // orderDetails : {"deviceId":"手机设备ID",
        //                 "merchantId":"",
        //                 "machineCode:"售货机CODE",
        //                 "machineId":"售货机ID",
        //                 "items":[{"name":"","unitPrice","","price","","qty":""},{...}]}
        Map<String,Object> orderDetails = new HashMap<>();
        orderDetails.put("deviceId",dto.getAppDeviceId());
        orderDetails.put("machineId",dto.getDeviceId());
        orderDetails.put("machineCode",dto.getDeviceCode());

        orderDetails.put("items",new ArrayList<>());
        param_pushorder.put("orderDetails",orderDetails);

        // deviceDetails : {"machineCode":"手机设备ID",
        //                 "machineId":""}
        Map<String,Object> deviceDetails = new HashMap<>();
        deviceDetails.put("machineId",dto.getDeviceId());
        deviceDetails.put("machineCode",dto.getDeviceCode());

        param_pushorder.put("deviceDetails",deviceDetails);
        return param_pushorder;
    }

    private List<OrderCommodity> getOrderCommodityList(OrderRecord order){
        OrderCommodity entity = new OrderCommodity();
        entity.setSorderId(order.getId());
        List<OrderCommodity> list = orderCommodityService.selectByEntityWhere(entity);
        return list;
    }

    private List<ReplenishmentCommodity> getReplenishmentCommodityList(String recordCode){
        ReplenishmentCommodity entity = new ReplenishmentCommodity();
        entity.setSreplenishmentCode(recordCode);
        List<ReplenishmentCommodity> list = replenishmentCommodityService.selectByEntityWhere(entity);
        return list;
    }

    private ResponseVo<FreePaymentResult> pushOrder_dealwith(FreePaymentDto freePaymentDto) throws Exception{
        ResponseVo<FreePaymentResult> responseVo = ResponseVo.getSuccessResponse();

        //向vendstop，推送订单数据
        OrderRecord orderRecord =  orderRecordService.selectByPrimaryKey(freePaymentDto.getSorderId());
        Map<String,Object> param_pushorder = builParam_shopping(freePaymentDto,orderRecord);
        VendstopResponse vendRes = VendstopClient.invoke(VendstopConstant.API.PUSH_ORDER,param_pushorder);
        if(vendRes.isSuccess()){
            // 更新状态：istatus = 10
            orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode());
            orderRecordService.updateBySelective(orderRecord);
            // 返回
            PushOrderResponseDto responseDto = JSON.parseObject(vendRes.getData(), PushOrderResponseDto.class);
            param_pushorder.put("id",responseDto.getTransactionId());
            param_pushorder.put("orderDate",orderRecord.getTorderTime().getTime());
            FreePaymentResult result = new FreePaymentResult();
            result.setPushOrderParam(param_pushorder);
            responseVo.setData(result);
            return responseVo;
        }else{
            //更新状态：istatus = 20
            orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_FAIL.getCode());
            orderRecordService.updateBySelective(orderRecord);
            // 返回
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg(vendRes.getError());
            return responseVo;
        }
    }
}
