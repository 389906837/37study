package com.cloud.cang.pay.om.service.impl;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sys.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.pay.om.dao.OrderPayDao;
import com.cloud.cang.model.om.OrderPay;
import com.cloud.cang.pay.om.service.OrderPayService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class OrderPayServiceImpl extends GenericServiceImpl<OrderPay, String> implements
        OrderPayService {

    @Autowired
    OrderPayDao orderPayDao;


    @Override
    public GenericDao<OrderPay, String> getDao() {
        return orderPayDao;
    }


    @Override
    @Transactional
    public OrderPay insertOrderPay(OrderRecord orderRecord, Integer payType, Integer iisWechatPayPointOrder) throws Exception {
        OrderPay orderPay = new OrderPay();
        orderPay.setIpayType(payType);
        if (null == iisWechatPayPointOrder) {
            iisWechatPayPointOrder = 0;
        }
        orderPay.setIisWechatPayPoint(iisWechatPayPointOrder);
        orderPay.setScode(CoreUtils.newCode("om_order_pay"));
        orderPay.setSorderCode(orderRecord.getSorderCode());
        orderPay.setSorderId(orderRecord.getId());
        orderPay.setIstatus(10);
        orderPay.setTaddTime(new Date());
        orderPayDao.insert(orderPay);
        return orderPay;
    }

    @Override
    @Transactional
    public OrderPay insertOrderPayN(OrderRecord orderRecord, Integer payType, Integer iisWechatPayPointOrder,String code) throws Exception {
        OrderPay orderPay = new OrderPay();
        orderPay.setIpayType(payType);
        if (null == iisWechatPayPointOrder) {
            iisWechatPayPointOrder = 0;
        }
        orderPay.setIisWechatPayPoint(iisWechatPayPointOrder);
        orderPay.setScode(code);
        orderPay.setSorderCode(orderRecord.getSorderCode());
        orderPay.setSorderId(orderRecord.getId());
        orderPay.setIstatus(10);
        orderPay.setTaddTime(new Date());
        orderPayDao.insert(orderPay);
        return orderPay;
    }

    /**
     * ???????????????
     *
     * @param outTradePayNo ????????????????????????
     * @return
     */
    @Override
    public String selectOrderCodeByPayNo(String outTradePayNo) {
        return orderPayDao.selectOrderCodeByPayNo(outTradePayNo);
    }

    /**
     * ????????????
     *
     * @param map
     */
    @Override
    public void updateDataByMap(Map<String, Object> map) {
        orderPayDao.updateDataByMap(map);
    }

    /**
     * ??????????????????????????????
     *
     * @param outTradeNo ??????????????????
     * @return
     */
    @Override
    public String selectOutTradeNoByOrderCode(String outTradeNo) {
        return orderPayDao.selectOutTradeNoByOrderCode(outTradeNo);
    }

    /**
     * ??????????????????????????????
     *
     * @param outTradeNo ??????????????????
     * @return
     */
    @Override
    public String selectOutTradeNoByOrderCodeAsc(String outTradeNo) {
        return orderPayDao.selectOutTradeNoByOrderCodeAsc(outTradeNo);
    }
}