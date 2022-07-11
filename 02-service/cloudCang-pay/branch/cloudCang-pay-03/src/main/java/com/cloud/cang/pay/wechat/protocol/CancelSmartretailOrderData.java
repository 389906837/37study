package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.CancelSmartretailOrdersDto;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付分撤销订单
 */
@XStreamAlias("xml")
public class CancelSmartretailOrderData {
    private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
    private String service_id;//该服务ID有本接口对应产品的权限
    private String reason;//用户投诉	支持50个字符，按照字符计算 超过长度报错处理

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private CancelSmartretailOrderData(CancelSmartretailOrderData.CancelSmartretailOrderDataBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.reason = builder.reason;
    }

    public static class CancelSmartretailOrderDataBuilder {
        private String appid;
        private String service_id;
        private MerchantConf conf;
        private String reason;//用户投诉	支持50个字符，按照字符计算 超过长度报错处理

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

        public MerchantConf getConf() {
            return conf;
        }

        public void setConf(MerchantConf conf) {
            this.conf = conf;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public CancelSmartretailOrderDataBuilder(MerchantConf conf , CancelSmartretailOrdersDto cancelSmartretailOrdersDto ) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.conf = conf;
            this.service_id = conf.getSserviceId();
            this.reason = cancelSmartretailOrdersDto.getReason();
        }

        public CancelSmartretailOrderData build() {
            return new CancelSmartretailOrderData(this);
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


}
