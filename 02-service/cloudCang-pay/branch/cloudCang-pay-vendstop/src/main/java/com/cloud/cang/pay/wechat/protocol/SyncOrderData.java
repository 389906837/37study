package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.CancelSmartretailOrdersDto;
import com.cloud.cang.pay.SyncOrdersDto;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付分同步订单
 */
@XStreamAlias("xml")
public class SyncOrderData {
    private String appid;//微信公众平台分配的与传入的商户号建立了支付绑定关系的appid
    private String service_id;//该服务ID有本接口对应产品的权限
    private String type;//场景类型Order_Paid-用户付款成功
    private Object detail;//内容信息详情 场景类型=“Order_Paid”时，必填，详见detail表格说明


    private SyncOrderData(SyncOrderData.SyncSmartretailOrderDataBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.type = builder.type;
        this.detail = builder.detail;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public static class detail {
        private String paid_time;//用户通过其他方式付款成功的实际时间

        @Override
        public String toString() {
            return "detail{" +
                    "paid_time='" + paid_time + '\'' +
                    '}';
        }

        public String getPaid_time() {
            return paid_time;
        }

        public void setPaid_time(String paid_time) {
            this.paid_time = paid_time;
        }
    }

    public static class SyncSmartretailOrderDataBuilder {
        private String appid;
        private String service_id;
        private MerchantConf conf;
        private String type;//用户投诉	支持50个字符，按照字符计算 超过长度报错处理
        private Object detail;//

        public SyncSmartretailOrderDataBuilder(MerchantConf conf, SyncOrdersDto syncOrdersDto) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.conf = conf;
            this.service_id = conf.getSserviceId();
            this.type = syncOrdersDto.getType();
            this.detail = syncOrdersDto.getDetail();
        }

        public SyncOrderData build() {
            return new SyncOrderData(this);
        }


        @Override
        public String toString() {
            return "SyncSmartretailOrderDataBuilder{" +
                    "appid='" + appid + '\'' +
                    ", service_id='" + service_id + '\'' +
                    ", conf=" + conf +
                    ", type='" + type + '\'' +
                    ", detail=" + detail +
                    '}';
        }

        public Object getDetail() {
            return detail;
        }

        public void setDetail(Object detail) {
            this.detail = detail;
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

        public MerchantConf getConf() {
            return conf;
        }

        public void setConf(MerchantConf conf) {
            this.conf = conf;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Override
    public String toString() {
        return "SyncOrderData{" +
                "appid='" + appid + '\'' +
                ", service_id='" + service_id + '\'' +
                ", type='" + type + '\'' +
                ", detail=" + detail +
                '}';
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
