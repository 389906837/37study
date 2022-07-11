package com.cloud.cang.api.ws;

import com.cloud.cang.api.common.ParamPage;
import com.cloud.cang.api.fr.service.FaceRegisterInfoService;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.netty.service.AiFaceMsgService;
import com.cloud.cang.api.om.service.OrderRecordService;
import com.cloud.cang.api.om.vo.OrderDomian;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.sl.service.LoginLogService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/larder")
public class KazayaService {

    private static final Logger logger = LoggerFactory.getLogger(KazayaService.class);

    @Autowired
    DeviceRegisterService registerService;

    @Autowired
    FaceRegisterInfoService faceRegisterInfoService;

    @Autowired
    MemberInfoService memberInfoService;

    @Autowired
    LoginLogService loginLogService;

    @Autowired
    AiFaceMsgService aiFaceMsgService;

    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    OrderRecordService orderRecordService;

    /**
     * 我的订单列表
     * @param paramPage
     * @return
     * @author zhouhong
     */
    @RequestMapping("/myOrderList")
    @ResponseBody
    ResponseVo<List<OrderDomian>> myOrderList(String userId,ParamPage paramPage) {
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

        map.put("thirdUserId", userId);
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
     * 补货员登录
     * @param phoneNumber
     * @param password
     * @return
     * @author zhouhong
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseVo<MemberInfo> login(@Param("phoneNumber") String phoneNumber,
                                        @Param("password") String password) {
        ResponseVo resVo = ResponseVo.getSuccessResponse();
        if(StringUtil.isBlank(phoneNumber) || StringUtil.isBlank(password)){
            resVo.setMsg("Mobile phone number or password cannot be empty");
            resVo.setSuccess(false);
            resVo.setErrorCode(-1000);
            return resVo;
        }

        MemberInfo entity = new MemberInfo();
        entity.setSmobile(phoneNumber);
        entity.setSloginPwd(MD5.encode(password.trim()));
        entity.setImemberType(BizTypeDefinitionEnum.MemberType.M2_MEMBER.getCode());
        List<MemberInfo> list = memberInfoService.selectByEntityWhere(entity);
        if(CollectionUtils.isEmpty(list)){
            resVo.setMsg("Error in account number and password");
            resVo.setSuccess(false);
            resVo.setErrorCode(-1001);
            return resVo;
        }

        return ResponseVo.getSuccessResponse(list.get(0));
    }

}
