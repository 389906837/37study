package com.cloud.cang.pay.wechat.protocol;

import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.wechat.common.RandomStringGenerator;
import com.cloud.cang.pay.wechat.common.Signature;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@XStreamAlias("xml")
public class DownloadbillReqData {

	private String appid;
    private String mch_id;
    private String nonce_str;
    private String sign;
    private String device_info;
    private String bill_date;
    private String bill_type;
    private String tar_type;
    private MerchantConf conf;

    private DownloadbillReqData(UnifiedDownloadbillReqDataBuilder builder) {
    	
        this.appid = builder.appid;
        this.mch_id = builder.mch_id;
        this.nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        this.device_info = builder.device_info;
        this.bill_date = builder.bill_date;
        this.bill_type = builder.bill_type;
        this.tar_type = builder.tar_type;
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


    public static class UnifiedDownloadbillReqDataBuilder {
    	private String appid;
        private String mch_id;
        private String device_info;
        private String bill_type;
        private String bill_date;
		private String tar_type;
        private MerchantConf conf;

        public UnifiedDownloadbillReqDataBuilder(MerchantConf conf, String bill_type, String bill_date) {
            if (conf == null) {
                throw new IllegalArgumentException("商户配置参数不能为空");
            }
            if (bill_date == null) {
                throw new IllegalArgumentException("传入参数bill_date不能为null");
            }
            if (bill_type == null) {
                throw new IllegalArgumentException("传入参数bill_type不能为null");
            }

            this.appid = conf.getSappId();
            this.mch_id = conf.getSpid();
            this.bill_date = bill_date;
            this.bill_type = bill_type;
            this.conf = conf;
        }
		public UnifiedDownloadbillReqDataBuilder setTar_type(String tar_type) {
			this.tar_type = tar_type;
	        return this;
		}

		public UnifiedDownloadbillReqDataBuilder setDevice_info(String device_info) {
			this.device_info = device_info;
			return this;
		}

		public DownloadbillReqData build() {
            return new DownloadbillReqData(this);
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

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getTar_type() {
        return tar_type;
    }

    public void setTar_type(String tar_type) {
        this.tar_type = tar_type;
    }

    public MerchantConf getConf() {
        return conf;
    }

    public void setConf(MerchantConf conf) {
        this.conf = conf;
    }
}
