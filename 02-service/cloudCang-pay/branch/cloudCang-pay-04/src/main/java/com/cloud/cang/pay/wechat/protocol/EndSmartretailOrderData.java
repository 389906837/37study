package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.DiscountDto;
import com.cloud.cang.pay.EndSmartretailOrderDto;
import com.cloud.cang.pay.FeeDto;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YLF on 2019/8/8.
 */
public class EndSmartretailOrderData {
    //必填
    private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
    private String service_id;//该服务ID有本接口对应产品的权限
    private Integer finish_type;//标识用户订单使用情况：1 未使用服务，取消订单；2 完成服务使用，结束订单
    private Integer total_amount;//大于等于0的数字，单位为分需满足：总金额=付费项目金额之和-商户优惠项目金额之和<=订单风险金额未使用服务，取消订单时，该字段必须填0.
    private String finish_ticket;//用于完结订单时传入（每次获取到的字段内容可能变化，但之前获取的字段始终有效，可一直使用）,请确保数据完整性，无需对字段再做处理。
    private Boolean profit_sharing;//是否指定服务商分账，true-需要分账，false-不需要分账
    private MerchantConf conf;
    //非必填
    private String cancel_reason;//不超过30个字符，超出报错处理；取消订单时必填，说明取消订单的原因
    private String real_service_start_time;//实际服务开始时间
    private String real_service_end_time;//服务结束时间
    private String real_service_end_location;//不超过20个字符，超出报错处理 实际结束使用服务的地点
    /*1.服务开始地点与结束地点一致时：不可填写，否则报错
    2.服务开始地点与结束地点不一致时：
                1）用户下单时未填写预计服务结束地点：需填写
    2）用户下单时已填写预计服务结束地点
        与预计服务结束地点一致时：不可填写
        与预计服务结束地点不一致时：需填写
        未使用服务，取消订单时，不可填写该字段*/
    private List<FeeDto> fees;//	后付费，最多包含100条付费项目。
    private List<DiscountDto> discounts;//	商户优惠，最多包含5条商户优惠。


    private EndSmartretailOrderData(EndSmartretailOrderData.EndSmartretailOrderDataBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.finish_type = builder.finish_type;
        this.total_amount = builder.total_amount;
        this.finish_ticket = builder.finish_ticket;
        this.profit_sharing = builder.profit_sharing;
        this.conf = null;
        this.cancel_reason = builder.cancel_reason;
        this.real_service_start_time = builder.real_service_start_time;
        this.real_service_end_time = builder.real_service_end_time;
        this.real_service_end_location = builder.real_service_end_location;
        this.fees = builder.fees;
        this.discounts = builder.discounts;
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

    public static class EndSmartretailOrderDataBuilder {
        private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
        private String service_id;//该服务ID有本接口对应产品的权限
        private Integer finish_type;//标识用户订单使用情况：1 未使用服务，取消订单；2 完成服务使用，结束订单
        private Integer total_amount;//大于等于0的数字，单位为分需满足：总金额=付费项目金额之和-商户优惠项目金额之和<=订单风险金额未使用服务，取消订单时，该字段必须填0.
        private String finish_ticket;//用于完结订单时传入（每次获取到的字段内容可能变化，但之前获取的字段始终有效，可一直使用）,请确保数据完整性，无需对字段再做处理。
        private Boolean profit_sharing;//是否指定服务商分账，true-需要分账，false-不需要分账
        private MerchantConf conf;
        //非必填
        private String cancel_reason;//不超过30个字符，超出报错处理；取消订单时必填，说明取消订单的原因
        private String real_service_start_time;//实际服务开始时间
        private String real_service_end_time;//服务结束时间
        private String real_service_end_location;//不超过20个字符，超出报错处理 实际结束使用服务的地点
        /*1.服务开始地点与结束地点一致时：不可填写，否则报错
        2.服务开始地点与结束地点不一致时：
                    1）用户下单时未填写预计服务结束地点：需填写
        2）用户下单时已填写预计服务结束地点
            与预计服务结束地点一致时：不可填写
            与预计服务结束地点不一致时：需填写
            未使用服务，取消订单时，不可填写该字段*/
        private List<FeeDto> fees;//	后付费，最多包含100条付费项目。
        private List<DiscountDto> discounts;//	商户优惠，最多包含5条商户优惠。


        public EndSmartretailOrderDataBuilder(MerchantConf conf, EndSmartretailOrderDto endSmartretailOrderDto) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            if (endSmartretailOrderDto == null) {
                throw new IllegalArgumentException("微信支付分完结订单信息不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.service_id = conf.getSserviceId();
            this.finish_type = endSmartretailOrderDto.getFinish_type();
            this.total_amount = endSmartretailOrderDto.getTotal_amount();
            this.finish_ticket = endSmartretailOrderDto.getFinish_ticket();
            this.profit_sharing = endSmartretailOrderDto.isProfit_sharing();
            this.conf = conf;
            this.cancel_reason = endSmartretailOrderDto.getCancel_reason();
            this.real_service_start_time = endSmartretailOrderDto.getReal_service_start_time();
            this.real_service_end_time = endSmartretailOrderDto.getReal_service_end_time();
            this.real_service_end_location = endSmartretailOrderDto.getReal_service_end_location();
            this.fees = endSmartretailOrderDto.getFees();
            this.discounts = endSmartretailOrderDto.getDiscounts();
        }

        public EndSmartretailOrderData build() {
            return new EndSmartretailOrderData(this);
        }
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public Integer getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(Integer finish_type) {
        this.finish_type = finish_type;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public String getFinish_ticket() {
        return finish_ticket;
    }

    public void setFinish_ticket(String finish_ticket) {
        this.finish_ticket = finish_ticket;
    }

    public Boolean getProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(Boolean profit_sharing) {
        this.profit_sharing = profit_sharing;
    }

    public MerchantConf getConf() {
        return conf;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }

    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public String getReal_service_start_time() {
        return real_service_start_time;
    }

    public void setReal_service_start_time(String real_service_start_time) {
        this.real_service_start_time = real_service_start_time;
    }

    public String getReal_service_end_time() {
        return real_service_end_time;
    }

    public void setReal_service_end_time(String real_service_end_time) {
        this.real_service_end_time = real_service_end_time;
    }

    public String getReal_service_end_location() {
        return real_service_end_location;
    }

    public void setReal_service_end_location(String real_service_end_location) {
        this.real_service_end_location = real_service_end_location;
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
}
