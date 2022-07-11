package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.RandomStringGenerator;
import com.cloud.cang.pay.wechat.common.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@XStreamAlias("xml")
public class UnifiedQuerySignReqData {

    private String appid;//公众账号ID
    private String mch_id;//商户号
    private String plan_id;//模板id
    private String contract_id;//委托代扣协议id
    private String contract_code;//签约协议号
    private String sign;//签名
    private String version;//版本号
    private String openid;//用户标识
    private MerchantConf conf;

    private UnifiedQuerySignReqData(UnifiedQuerySignReqDataBuilder builder) {
        this.appid = builder.appid;
        this.mch_id = builder.mch_id;
        this.plan_id = builder.plan_id;
        this.contract_id = builder.contract_id;
        this.contract_code = builder.contract_code;
        this.version = builder.version;
        this.openid = builder.openid;
        this.conf = builder.conf;
        this.sign = Signature.getSign(toMap(), this.conf);
        this.conf = null;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }

    public MerchantConf getConf() {
        return conf;
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


    public static class UnifiedQuerySignReqDataBuilder {

        private String appid;//公众账号ID
        private String mch_id;//商户号
        private String plan_id;//模板id
        private String contract_id;//委托代扣协议id
        private String contract_code;//签约协议号
        private String version;//版本号
        private String openid;//用户标识
        private MerchantConf conf;

        public UnifiedQuerySignReqDataBuilder(MerchantConf conf, String contract_code, String contract_id) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }

            if (contract_code == null) {
                throw new IllegalArgumentException("传入参数签约协议号不能为null");
            }

            this.appid = conf.getSappId();
            this.mch_id = conf.getSpid();
            this.plan_id = conf.getSplanId();
            this.version = "1.0";
            this.contract_code = contract_code;
            this.contract_id = contract_id;
            this.conf = conf;
        }
        public UnifiedQuerySignReqDataBuilder setOpenid(String openid) {
            this.openid = openid;
            return this;
        }
        public UnifiedQuerySignReqData build() {
            return new UnifiedQuerySignReqData(this);
        }
    }


}
