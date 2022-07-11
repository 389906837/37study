package com.cloud.cang.api.ws;

import com.cloud.cang.api.common.ParamPage;
import com.cloud.cang.api.om.service.OrderRecordService;
import com.cloud.cang.api.om.vo.OrderDomian;
import com.cloud.cang.api.sh.service.MerchantInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * vendstop
 */
@RestController
@RequestMapping("/vendstop")
public class VendstopService {

    private static final Logger logger = LoggerFactory.getLogger(VendstopService.class);
    @Autowired
    OrderRecordService orderRecordService;
    @Autowired
    MerchantInfoService merchantInfoService;

    /**
     * 我的订单列表
     * @param paramPage
     * @return
     * @author qzg
     */
    @RequestMapping("/myOrderList")
    @ResponseBody
    public ResponseVo<List<OrderDomian>> myOrderList(String userId,ParamPage paramPage) {
        String type = "All";
        logger.debug("获取我的订单记录{}", type);
        //All 全部 Fail 待付款支付失败  Success 已完成 Refund 退款订单
        if (StringUtil.isBlank(type)) {
            type = "All";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (!type.equals("All") && !type.equals("Fail") && !type.equals("Success") && !type.equals("Refund")) {
            ResponseVo<List<OrderDomian>> responseVo = ResponseVo.getSuccessResponse();
            responseVo.setSuccess(false);
            return responseVo;
        }
        Page<OrderDomian> page = new Page<OrderDomian>();
        page.setPageNum(paramPage.pageNo() > 0 ? paramPage.pageNo() : 1);
        page.setPageSize(paramPage.getLimit() > 0 ? paramPage.getLimit() : 10);

        map.put("memberId", userId);
        if (type.equals("Fail")) {
            map.put("itype", 20);
        } else if (type.equals("Success")) {
            map.put("itype", 30);
        }
        if (type.equals("Refund")) {
            //page = refundAuditService.selectOrderListByPage(page, map);
        } else {
            page = orderRecordService.selectOrderListByPage(page, map);
        }
        return ResponseVo.getSuccessResponse(page.getTotal(), page.getResult());
    }

    /**
     * 商户列表
     * @param pageSize
     * @param pageNum
     * @return
     * @author qzg
     */
    @RequestMapping("/getMerchants")
    @ResponseBody
    public ResponseVo<List<Map<String,Object>>> getMerchants(int pageSize,int pageNum) {
        Page page = new Page();
        page.setPageSize(pageSize < 1 ? pageSize: 10);
        page.setPageNum(pageNum < 1 ? pageNum : 1);
        Page<MerchantInfo> result = merchantInfoService.selectMerchantInfoListByPage(page,null);
        List<Map<String,Object>> list = new ArrayList<>();
        result.getResult().forEach((merchant)->{
            Map<String,Object> temp = new HashMap<>();
            temp.put("merchantId",merchant.getId());
            temp.put("merchantCode",merchant.getScode());
            temp.put("merchantName",merchant.getSname());
            temp.put("merchantType",merchant.getItype());//商户类型 10:个人 20:企业
            temp.put("contactName",merchant.getScontactName());
            temp.put("contactMobile",merchant.getScontactMobile());
            temp.put("cooperationMode",merchant.getIcooperationMode());//合作模式 10=采购 20=租用

            list.add(temp);
        });
        return ResponseVo.getSuccessResponse(page.getTotal(), list);
    }


    /**
     * 商户详情
     * @param merchantId
     * @return
     * @author qzg
     */
    @RequestMapping("/getMerchantDetail")
    @ResponseBody
    public ResponseVo<Map<String,Object>> getMerchantDetail(String merchantId) {
        MerchantInfo merchant = merchantInfoService.selectByPrimaryKey(merchantId);
        if(merchant ==null){
            ResponseVo resVo = ResponseVo.getSuccessResponse();
            resVo.setSuccess(false);
            resVo.setMsg("Merchant does not exiHttpSession st");
            return resVo;
        }
        Map<String,Object> temp = new HashMap<>();
        temp.put("merchantId",merchant.getId());
        temp.put("merchantCode",merchant.getScode());
        temp.put("merchantName",merchant.getSname());
        temp.put("merchantType",merchant.getItype());//商户类型 10:个人 20:企业
        temp.put("contactName",merchant.getScontactName());
        temp.put("contactAddress",merchant.getScontactAddress());
        temp.put("contactMobile",merchant.getScontactMobile());
        temp.put("contactEmail",merchant.getScontactEmail());
        temp.put("cooperationMode",merchant.getIcooperationMode());//合作模式 10=采购 20=租用
        temp.put("signTime",merchant.getDsignDate());//签约时间
        temp.put("expireTime",merchant.getDexpireDate());//合约到期时间
        return ResponseVo.getSuccessResponse(temp);
    }

}
