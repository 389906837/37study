package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.EndSmartretailOrderDto;
import com.cloud.cang.pay.QuerySmartretailOrderDto;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付分查询订单
 */
@XStreamAlias("xml")
public class QuerySmartretailOrderData {
    //必填
    private String service_id;//
    private String out_order_no;//
    private String appid;//
    private MerchantConf conf;

    private QuerySmartretailOrderData(QuerySmartretailOrderData.QuerySmartretailOrderDataBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.out_order_no = builder.out_order_no;
        this.conf = null;

    }

    public static class QuerySmartretailOrderDataBuilder {
        //必填
        private String service_id;//
        private String out_order_no;//
        private String appid;//
        private MerchantConf conf;

        public QuerySmartretailOrderDataBuilder(MerchantConf conf, QuerySmartretailOrderDto querySmartretailOrderDto) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.service_id = conf.getSserviceId();
            this.out_order_no = querySmartretailOrderDto.getOut_order_no();
            this.conf = conf;
        }

        public QuerySmartretailOrderData build() {
            return new QuerySmartretailOrderData(this);
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public MerchantConf getConf() {
        return conf;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }
}
