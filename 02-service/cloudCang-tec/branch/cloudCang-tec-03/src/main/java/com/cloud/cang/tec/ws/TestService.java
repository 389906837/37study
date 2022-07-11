package com.cloud.cang.tec.ws;


import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.pay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.*;


/**
 * 付款申请 创建付款订单 退款申请
 * @author zhouhong
 *
 */
@RestController
@RequestMapping("/testService")
@RegisterRestResource
public class TestService {

    @Autowired
    private ICached iCached;
    /**
     * 测试下载账单
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/testWechatDownloadBill", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo<String> testWechatDownloadBill() {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            DownloadBillDto dto = new DownloadBillDto();
            dto.setBillDate("20180530");
            dto.setBillType("ALL");
            dto.setSmerchantCode("OP201801220037");
            dto.setItype(30);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.DOWNLOAD_BILL);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
            invoke.setRequestObj(dto); // post 参数
            responseVo = (ResponseVo<String>) invoke.invoke();
            return responseVo;
        } catch (Exception e) {
           e.printStackTrace();
        }
        responseVo.setSuccess(false);
        return responseVo;
    }

    /**
     * 测试下载账单
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/testAlipayDownloadBill", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo<String> testAlipayDownloadBill() {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            DownloadBillDto dto = new DownloadBillDto();
            dto.setBillDate("2018-06-01");
            dto.setBillType("trade");
            dto.setSmerchantCode("OP201801220037");
            dto.setItype(40);
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(PayServicesDefine.DOWNLOAD_BILL);// 服务名称
            // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() { });
            invoke.setRequestObj(dto); // post 参数
            responseVo = (ResponseVo<String>) invoke.invoke();
            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseVo.setSuccess(false);
        return responseVo;
    }


    /**
     * 删除统计缓存数据
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/removeSummarizationData")
    @ResponseBody
    public ResponseVo<String> removeSummarizationData(String scode) {
        ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();
        try {
            iCached.remove(RedisConst.YESTERDAY_PLATFORM_DATA_INFO + scode);
            return responseVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        responseVo.setSuccess(false);
        return responseVo;
    }
}
