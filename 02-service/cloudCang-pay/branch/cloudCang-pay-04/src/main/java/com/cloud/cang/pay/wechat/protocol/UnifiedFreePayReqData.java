package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.RandomStringGenerator;
import com.cloud.cang.pay.wechat.common.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@XStreamAlias("xml")
public class UnifiedFreePayReqData {

    private String appid;//公众账号ID
    private String mch_id;//商户号
    private String nonce_str;//随机字符串
    private String sign;//签名
    private String body;//商品描述
    private String detail;//商品详情
    private String attach;//附加数据
    private String out_trade_no;//商户订单号
    private int total_fee;//总金额
    private String fee_type;//货币类型
    private String spbill_create_ip;//终端IP
    private String goods_tag;//商品标记
    private String trade_type;//交易类型
    private String notify_url;//通知地址
    private String openid;//用户标识
    private String contract_id;//委托代扣协议id
    private String clientip;//客户端 IP
    private String deviceid;//设备ID
    private String mobile;//手机号
    private String email;//邮箱地址
    private String qq;//QQ号
    private String creid;//身份证号
    private String outerid;//商户侧用户标识
    private int timestamp;//商户侧用户标识
    private MerchantConf conf;

    private UnifiedFreePayReqData(UnifiedFreePayReqDataBuilder builder) {
        this.appid = builder.appid;
        this.mch_id = builder.mch_id;
        this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        this.body = builder.body;
        this.detail = builder.detail;
        this.attach = builder.attach;
        this.out_trade_no = builder.out_trade_no;
        this.fee_type = builder.fee_type;
        this.total_fee = builder.total_fee;
        this.spbill_create_ip = builder.spbill_create_ip;
        this.goods_tag = builder.goods_tag;
        this.notify_url = builder.notify_url;
        this.trade_type = builder.trade_type;
        this.openid = builder.openid;

        this.contract_id = builder.contract_id;
        this.clientip = builder.clientip;
        this.deviceid = builder.deviceid;
        this.mobile = builder.mobile;
        this.email = builder.email;
        this.qq = builder.qq;
        this.creid = builder.creid;
        this.outerid = builder.outerid;
        this.timestamp = builder.timestamp;

        this.conf = builder.conf;
        this.sign = Signature.getSign(toMap(), this.conf);
        this.conf = null;
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


    public static class UnifiedFreePayReqDataBuilder {
        private String appid;
        private String mch_id;
        private String body;
        private String detail;
        private String attach;
        private String out_trade_no;
        private String fee_type;
        private int total_fee;
        private String spbill_create_ip;
        private String goods_tag;
        private String notify_url;
        private String trade_type;
        private String openid;

        private String contract_id;//委托代扣协议id
        private String clientip;//客户端 IP
        private String deviceid;//设备ID
        private String mobile;//手机号
        private String email;//邮箱地址
        private String qq;//QQ号
        private String creid;//身份证号
        private String outerid;//商户侧用户标识
        private int timestamp;//商户侧用户标识
        private MerchantConf conf;


        public UnifiedFreePayReqDataBuilder(MerchantConf conf, String body, String out_trade_no, Integer total_fee,
                                          String spbill_create_ip, String notify_url, String trade_type, String contract_id) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            if (body == null) {
                throw new IllegalArgumentException("传入参数body不能为null");
            }
            if (out_trade_no == null) {
                throw new IllegalArgumentException("传入参数out_trade_no不能为null");
            }
            if (total_fee == null) {
                throw new IllegalArgumentException("传入参数total_fee不能为null");
            }
            if (spbill_create_ip == null) {
                throw new IllegalArgumentException("传入参数spbill_create_ip不能为null");
            }
            if (notify_url == null) {
                throw new IllegalArgumentException("传入参数notify_url不能为null");
            }
            if (trade_type == null) {
                throw new IllegalArgumentException("传入参数trade_type不能为null");
            }
            if (contract_id == null) {
                throw new IllegalArgumentException("传入参数contract_id不能为null");
            }
            this.appid = conf.getSappId();
            this.mch_id = conf.getSpid();
            this.body = body;
            this.out_trade_no = out_trade_no;
            this.total_fee = total_fee;
            this.spbill_create_ip = spbill_create_ip;
            this.notify_url = notify_url;
            this.trade_type = trade_type;
            this.contract_id = contract_id;
            this.conf = conf;
        }

        public UnifiedFreePayReqDataBuilder setDetail(String detail) {
            this.detail = detail;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setAttach(String attach) {
            this.attach = attach;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setFee_type(String fee_type) {
            this.fee_type = fee_type;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setGoods_tag(String goods_tag) {
            this.goods_tag = goods_tag;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setOpenid(String openid) {
            this.openid = openid;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setClientip(String clientip) {
            this.clientip = clientip;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setDeviceid(String deviceid) {
            this.deviceid = deviceid;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setQq(String qq) {
            this.qq = qq;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setCreid(String creid) {
            this.creid = creid;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setOuterid(String outerid) {
            this.outerid = outerid;
            return this;
        }

        public UnifiedFreePayReqDataBuilder setTimestamp(int timestamp) {
            this.timestamp = timestamp;
            return this;
        }


        public UnifiedFreePayReqData build() {
            return new UnifiedFreePayReqData(this);
        }
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

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getCreid() {
        return creid;
    }

    public void setCreid(String creid) {
        this.creid = creid;
    }

    public String getOuterid() {
        return outerid;
    }

    public void setOuterid(String outerid) {
        this.outerid = outerid;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }
}
