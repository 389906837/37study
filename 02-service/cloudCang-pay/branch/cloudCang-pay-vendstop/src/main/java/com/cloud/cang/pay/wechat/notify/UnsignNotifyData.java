package com.cloud.cang.pay.wechat.notify;

/**
 * 微信端 解约免密支付 通知
 */
public class UnsignNotifyData {

    //通知参数
    private String return_code;

    private String return_msg;

    //以下字段在return_code为SUCCESS的时候返回
    private String result_code;

    //以下字段在return_code 和result_code都为SUCCESS的时候有返回
    private String mch_id;

    private String contract_code;

    private String plan_id;

    private String openid;

    private String sign;

    private String change_type;

    private String operate_time;

    private String contract_id;

    private String contract_termination_mode;//解约时间

    private String request_serial;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getContract_code() {
        return contract_code;
    }

    public void setContract_code(String contract_code) {
        this.contract_code = contract_code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getChange_type() {
        return change_type;
    }

    public void setChange_type(String change_type) {
        this.change_type = change_type;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_termination_mode() {
        return contract_termination_mode;
    }

    public void setContract_termination_mode(String contract_termination_mode) {
        this.contract_termination_mode = contract_termination_mode;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getRequest_serial() {
        return request_serial;
    }

    public void setRequest_serial(String request_serial) {
        this.request_serial = request_serial;
    }
    @Override
    public String toString() {
        return "SignNotifyData{" +
                "return_code='" + return_code + '\'' +
                ", return_msg='" + return_msg + '\'' +
                ", result_code='" + result_code + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", contract_code='" + contract_code + '\'' +
                ", plan_id='" + plan_id + '\'' +
                ", openid='" + openid + '\'' +
                ", sign='" + sign + '\'' +
                ", change_type='" + change_type + '\'' +
                ", operate_time='" + operate_time + '\'' +
                ", contract_id='" + contract_id + '\'' +
                ", contract_termination_mode='" + contract_termination_mode + '\'' +
                ", request_serial='" + request_serial + '\'' +
                '}';
    }
}
