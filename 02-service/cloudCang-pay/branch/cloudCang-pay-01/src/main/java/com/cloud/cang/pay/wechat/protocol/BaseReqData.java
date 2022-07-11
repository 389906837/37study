package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.RandomStringGenerator;
import com.cloud.cang.pay.wechat.common.Signature;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class BaseReqData {

	private String appid;//公众账号ID
    private String mch_id;//商户号
    private String nonce_str;//随机字符串
    private String sign;//签名类型
    private String transaction_id;//微信订单号
    private String out_trade_no;//商户订单号
    private String out_refund_no;//商户退款单号
    private String refund_id;//微信退款单号
	private MerchantConf conf;

    private BaseReqData(UnifiedBaseReqDataBuilder builder) {
    	
        this.appid = builder.appid;
        this.mch_id = builder.mch_id;
        this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        this.out_trade_no = builder.out_trade_no;
        this.transaction_id = builder.transaction_id;
        this.out_refund_no = builder.out_refund_no;
        this.refund_id = builder.refund_id;
        this.conf = builder.conf;
        this.sign = Signature.getSign(toMap(), this.conf);
		this.conf = null;
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


    public static class UnifiedBaseReqDataBuilder {
    	private String appid;
        private String mch_id;
        private String transaction_id;
        private String out_trade_no;
        private String out_refund_no;
		private String refund_id;
		private MerchantConf conf;


        public UnifiedBaseReqDataBuilder(MerchantConf conf, String out_trade_no) {
            if (conf == null) {
                throw new IllegalArgumentException("商户支付配置参数不能为空");
            }
            if (out_trade_no == null) {
                throw new IllegalArgumentException("传入参数out_trade_no不能为null");
            }
            this.appid = conf.getSappId();
            this.mch_id = conf.getSpid();
            this.out_trade_no = out_trade_no;
            this.conf = conf;
        }
		public UnifiedBaseReqDataBuilder setTransaction_id(String transaction_id) {
			this.transaction_id = transaction_id;
	        return this;
		}
		public UnifiedBaseReqDataBuilder setOut_refund_no(String out_refund_no) {
			this.out_refund_no = out_refund_no;
			return this;
		}

		public UnifiedBaseReqDataBuilder setRefund_id(String refund_id) {
			this.refund_id = refund_id;
			return this;
		}
		public UnifiedBaseReqDataBuilder setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
			return this;
		}
		public BaseReqData build() {
            return new BaseReqData(this);
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


	public String getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getOut_trade_no() {
		return out_trade_no;
	}


	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}


	public String getOut_refund_no() {
		return out_refund_no;
	}


	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}


	public String getRefund_id() {
		return refund_id;
	}


	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public MerchantConf getConf() {
		return conf;
	}

	public void setConf(MerchantConf conf) {
		this.conf = conf;
	}
}
