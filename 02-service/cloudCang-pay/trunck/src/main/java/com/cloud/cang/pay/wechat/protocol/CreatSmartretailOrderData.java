package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.CreatSmartretailOrderDto;
import com.cloud.cang.pay.DiscountDto;
import com.cloud.cang.pay.FeeDto;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信支付分查询用户开启状态
 */
@XStreamAlias("xml")
public class CreatSmartretailOrderData {
    private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
    private String out_order_no;//商户系统内部服务订单号（不是交易单号）
    private String service_id;//该服务ID有本接口对应产品的权限
    private String service_start_time;//用户下单时确认的服务开始时间
    private String service_start_location;//开始使用服务的地点
    private String service_introduction;//服务信息，用于介绍本订单所提供的服务，不超过20个字符
    private Integer risk_amount;//该笔订单会产生的最大金额，不能大于服务风险金额大于0的数字，单位为分
    //非必填
    private String service_end_time;//用户下单时确定的预计服务结束时间，如果超出此时间服务方还未完结订单，用户的本订单将会进入待处理状态,可不填写
    private String service_end_location;//预计服务结束的地点，用户下单时未确认服务结束地点时，可不填写。
    private List<FeeDto> fees;//	后付费，最多包含100条付费项目。
    private List<DiscountDto> discounts;//	商户优惠，最多包含5条商户优惠。
    private String attach;//	商户数据包,可存放本订单所需信息.
    private boolean need_user_confirm;//	true：使用需用户确认订单类型；false：使用免确认订单类型；默认为true.
    private String openid;//微信用户在商户对应appid下的唯一标识，使用免确认订单类型必须填写，使用需用户确认订单类型不允许填写

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private CreatSmartretailOrderData(CreatSmartretailOrderData.CreatSmartretailOrderBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.out_order_no = builder.out_order_no;
        this.service_start_time = builder.service_start_time;
        this.service_start_location = builder.service_start_location;
        this.service_introduction = builder.service_introduction;
        this.risk_amount = builder.risk_amount;
        this.service_end_time = builder.service_end_time;
        this.service_end_location = builder.service_end_location;
        this.fees = builder.fees;
        this.discounts = builder.discounts;
        this.attach = builder.attach;
        this.need_user_confirm = builder.need_user_confirm;
        this.openid = builder.openid;
    }

    public static class CreatSmartretailOrderBuilder {
        private String appid;
        private String out_order_no;
        private String service_id;
        private String service_start_time;
        private String service_start_location;
        private String service_introduction;
        private Integer risk_amount;
        private MerchantConf conf;
        //非必填
        private String service_end_time;
        private String service_end_location;
        private List<FeeDto> fees;
        private List<DiscountDto> discounts;
        private String attach;
        private boolean need_user_confirm;
        private String openid;

        public CreatSmartretailOrderBuilder(MerchantConf conf, String openid, CreatSmartretailOrderDto creatSmartretailOrderDto) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            if (StringUtils.isBlank(openid)) {
                throw new IllegalArgumentException("用户签约Id不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.conf = conf;
            this.service_id = conf.getSserviceId();
            this.openid = openid;
            this.out_order_no = creatSmartretailOrderDto.getOut_order_no();
            this.service_start_time = creatSmartretailOrderDto.getService_start_time();
            this.service_start_location = creatSmartretailOrderDto.getService_start_location();
            this.service_introduction = creatSmartretailOrderDto.getService_introduction();
            this.risk_amount = creatSmartretailOrderDto.getRisk_amount();
            if (StringUtils.isNotBlank(creatSmartretailOrderDto.getService_end_time())) {
                this.service_end_time = creatSmartretailOrderDto.getService_end_time();
            }
            if (StringUtils.isNotBlank(creatSmartretailOrderDto.getService_end_location())) {
                this.service_end_location = creatSmartretailOrderDto.getService_end_location();
            }
            if (null != creatSmartretailOrderDto.getFees()) {
                this.fees = creatSmartretailOrderDto.getFees();
            }
            if (null != creatSmartretailOrderDto.getDiscounts()) {
                this.discounts = creatSmartretailOrderDto.getDiscounts();
            }
            if (StringUtils.isNotBlank(creatSmartretailOrderDto.getAttach())) {
                this.attach = creatSmartretailOrderDto.getAttach();
            }
            this.need_user_confirm = creatSmartretailOrderDto.isNeed_user_confirm();
        }

        public CreatSmartretailOrderData build() {
            return new CreatSmartretailOrderData(this);
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if (obj != null) {
                    if (!field.getName().equals("conf")) {
                        map.put(field.getName(), obj);
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService_start_time() {
        return service_start_time;
    }

    public void setService_start_time(String service_start_time) {
        this.service_start_time = service_start_time;
    }

    public String getService_start_location() {
        return service_start_location;
    }

    public void setService_start_location(String service_start_location) {
        this.service_start_location = service_start_location;
    }

    public String getService_introduction() {
        return service_introduction;
    }

    public void setService_introduction(String service_introduction) {
        this.service_introduction = service_introduction;
    }

    public Integer getRisk_amount() {
        return risk_amount;
    }

    public void setRisk_amount(Integer risk_amount) {
        this.risk_amount = risk_amount;
    }

    public String getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(String service_end_time) {
        this.service_end_time = service_end_time;
    }

    public String getService_end_location() {
        return service_end_location;
    }

    public void setService_end_location(String service_end_location) {
        this.service_end_location = service_end_location;
    }

    public List<FeeDto> getFees() {
        return fees;
    }

    public void setFees(List<FeeDto> fees) {
        this.fees = fees;
    }

    public List<DiscountDto> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDto> discounts) {
        this.discounts = discounts;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public boolean isNeed_user_confirm() {
        return need_user_confirm;
    }

    public void setNeed_user_confirm(boolean need_user_confirm) {
        this.need_user_confirm = need_user_confirm;
    }

}
