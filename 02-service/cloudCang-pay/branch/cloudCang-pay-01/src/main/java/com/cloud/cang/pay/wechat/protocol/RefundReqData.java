package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.RandomStringGenerator;
import com.cloud.cang.pay.wechat.common.Signature;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class RefundReqData {

	private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String transaction_id;
    private String out_trade_no;
    private String out_refund_no;
    private Integer total_fee = 0;
    private Integer refund_fee = 0;
    private String op_user_id;
    private String refund_desc;
    private MerchantConf conf;

    private RefundReqData(UnifiedRefundReqDataBuilder builder) {
    	
        this.appid = builder.appid;
        this.mch_id = builder.mch_id;
        this.op_user_id = builder.op_user_id;
        this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        this.out_trade_no = builder.out_trade_no;
        this.total_fee = builder.total_fee;
        this.refund_fee = builder.refund_fee;
        this.transaction_id = builder.transaction_id;
        this.out_refund_no = builder.out_refund_no;
        this.refund_desc = builder.refund_desc;
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


    public static class UnifiedRefundReqDataBuilder {
    	private String appid;
        private String mch_id;
        private String transaction_id;
        private String out_trade_no;
        private String out_refund_no;
        private Integer total_fee = 0;
		private Integer refund_fee = 0;
		private String op_user_id;
        private String refund_desc;
        private MerchantConf conf;

        public UnifiedRefundReqDataBuilder(MerchantConf conf, Integer total_fee, Integer refund_fee, String out_refund_no) {
            if (conf == null) {
                throw new IllegalArgumentException("商户配置参数不能为空");
            }
            if (out_refund_no == null) {
                throw new IllegalArgumentException("传入参数out_refund_no不能为null");
            }
            if (total_fee == null) {
                throw new IllegalArgumentException("传入参数total_fee不能为null");
            }
            if (refund_fee == null) {
                throw new IllegalArgumentException("传入参数refund_fee不能为null");
            }
            this.appid = conf.getSappId();
            this.mch_id = conf.getSpid();
            this.op_user_id = mch_id;
            this.total_fee = total_fee;
            this.refund_fee = refund_fee;
            this.out_refund_no = out_refund_no;
            this.conf = conf;
        }
		public UnifiedRefundReqDataBuilder setTransaction_id(String transaction_id) {
			this.transaction_id = transaction_id;
	        return this;
		}

		public UnifiedRefundReqDataBuilder setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
			return this;
		}
        public UnifiedRefundReqDataBuilder setRefund_desc(String refund_desc) {
            this.refund_desc = refund_desc;
            return this;
        }
		public RefundReqData build() {
            return new RefundReqData(this);
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


	public Integer getTotal_fee() {
		return total_fee;
	}


	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}


	public Integer getRefund_fee() {
		return refund_fee;
	}


	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}


	public String getOp_user_id() {
		return op_user_id;
	}


	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

    public MerchantConf getConf() {
        return conf;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }

    public String getRefund_desc() {
        return refund_desc;
    }

    public void setRefund_desc(String refund_desc) {
        this.refund_desc = refund_desc;
    }
}
