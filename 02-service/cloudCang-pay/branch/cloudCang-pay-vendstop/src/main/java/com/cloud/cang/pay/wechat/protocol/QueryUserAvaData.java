package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付分查询用户开启状态
 */
@XStreamAlias("xml")
public class QueryUserAvaData {
    private String service_id;
    private String openid;
    private String appid;
    private MerchantConf conf;

    private QueryUserAvaData(QueryUserAvaData.QueryUserAvaDataBuilder builder) {
        this.appid = builder.appid;
        this.service_id = builder.service_id;
        this.openid = builder.openid;
        this.conf = null;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public static class QueryUserAvaDataBuilder {
        private String service_id;
        private String appid;
        private MerchantConf conf;
        private String openid;

        public QueryUserAvaDataBuilder(MerchantConf conf, String openid) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            if (StringUtils.isBlank(openid)) {
                throw new IllegalArgumentException("用户签约Id不能为空");
            }
            this.appid = conf.getSwechatPointAppid();
            this.conf = conf;
            this.openid = openid;
            this.service_id = conf.getSserviceId();
        }

        public QueryUserAvaData build() {
            return new QueryUserAvaData(this);
        }
    }
}
