package com.cloud.cang.pay.wechat.notify;

/**
 * Created by YLF on 2019/8/6.
 */
public class ConfirmOrderResouceDecrypt {
    //必填
    private String appid;//调用接口提交的公众账号ID
    private String mchid;//调用接口提交的商户号
    private String out_order_no;//调用接口提交的商户服务订单号
    private String service_id;//调用该接口提交的服务ID
    private String state;//表示当前单据状态.
    /* CREATED:商户下单已受理
     USER_ACCEPTED:用户成功使用服务
     FINISHED:商户完结订单
     USER_PAID:用户订单支付成功
     REVOKED:商户撤销订单
     EXPIRED:订单已失效. “商户下单已受理”状态超过1小时未变动，则订单失效*/
    private String service_start_time;//支持两种格式:yyyyMMddHHmmss和yyyyMMdd
    private String pay_succ_time;//支持两种格式:yyyyMMddHHmmss和yyyyMMdd
    private String service_start_location;//开始使用服务的地点. 不超过20个字符，超出报错处理
    private String service_introduction;//服务信息，用于介绍本订单所提供的服务不超过20个字符，超出报错处理
    private Integer risk_amount;//大于0的数字，单位为分当填写了【优惠金额】字段时，订单风险金额需满足：付费项目金额之和-优惠项目金额之和<订单风险金额订单风险金额不能超出每个服务ID的风险金额上限
    private String finish_ticket;//完结凭证
    //非必填
    private Integer finish_type;//标识用户订单使用情况： 1 未使用服务，取消订单； 2 完成服务使用，结束订单
    private String service_end_time;//支持两种格式:yyyyMMddHHmmss和yyyyMMdd
    private String real_service_start_time;//支持两种格式:yyyyMMddHHmmss和yyyyMMdd
    private String real_service_end_time;//支持两种格式:yyyyMMddHHmmss和yyyyMMdd
    private String service_end_location;//预计服务结束的地点，用户下单时未确认服务结束地点时，可不填写. 不超过20个字符，超出报错处理
    private String real_service_end_location;//服务结束的地点 不超过20个字符，超出报错处理
    private Integer total_amount;//大于等于0的数字，单位为分需满足：总金额=付费项目金额之和-商户优惠项目金额之和<=订单风险金额未使用服务，取消订单时，该字段必须填0.
    private String attach;//商户数据包,可存放本订单所需信息. 需要先urlencode后传入. 总长度不大于200字符,超出报错处理.
    private String finish_transaction_id;//结单交易单号,可以使用该订单号在APP支付->API列表->查询订单进行查询订单、申请退款，只有单据状态为USER_PAID才会返回结单交易单号


    public String getFinish_ticket() {
        return finish_ticket;
    }

    public void setFinish_ticket(String finish_ticket) {
        this.finish_ticket = finish_ticket;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getOut_order_no() {
        return out_order_no;
    }

    public void setOut_order_no(String out_order_no) {
        this.out_order_no = out_order_no;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getService_start_time() {
        return service_start_time;
    }

    public void setService_start_time(String service_start_time) {
        this.service_start_time = service_start_time;
    }

    public String getPay_succ_time() {
        return pay_succ_time;
    }

    public void setPay_succ_time(String pay_succ_time) {
        this.pay_succ_time = pay_succ_time;
    }

    public String getService_start_location() {
        return service_start_location;
    }

    public void setService_start_location(String service_start_location) {
        this.service_start_location = service_start_location;
    }

    public String getService_introduction() {
        return service_introduction;
    }

    public void setService_introduction(String service_introduction) {
        this.service_introduction = service_introduction;
    }

    public Integer getRisk_amount() {
        return risk_amount;
    }

    public void setRisk_amount(Integer risk_amount) {
        this.risk_amount = risk_amount;
    }

    public Integer getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(Integer finish_type) {
        this.finish_type = finish_type;
    }

    public String getService_end_time() {
        return service_end_time;
    }

    public void setService_end_time(String service_end_time) {
        this.service_end_time = service_end_time;
    }

    public String getReal_service_start_time() {
        return real_service_start_time;
    }

    public void setReal_service_start_time(String real_service_start_time) {
        this.real_service_start_time = real_service_start_time;
    }

    public String getReal_service_end_time() {
        return real_service_end_time;
    }

    public void setReal_service_end_time(String real_service_end_time) {
        this.real_service_end_time = real_service_end_time;
    }

    public String getService_end_location() {
        return service_end_location;
    }

    public void setService_end_location(String service_end_location) {
        this.service_end_location = service_end_location;
    }

    public String getReal_service_end_location() {
        return real_service_end_location;
    }

    public void setReal_service_end_location(String real_service_end_location) {
        this.real_service_end_location = real_service_end_location;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFinish_transaction_id() {
        return finish_transaction_id;
    }

    public void setFinish_transaction_id(String finish_transaction_id) {
        this.finish_transaction_id = finish_transaction_id;
    }

    @Override
    public String toString() {
        return "ConfirmOrderResouceDecrypt{" +
                "appid='" + appid + '\'' +
                ", mchid='" + mchid + '\'' +
                ", out_order_no='" + out_order_no + '\'' +
                ", service_id='" + service_id + '\'' +
                ", state='" + state + '\'' +
                ", service_start_time='" + service_start_time + '\'' +
                ", pay_succ_time='" + pay_succ_time + '\'' +
                ", service_start_location='" + service_start_location + '\'' +
                ", service_introduction='" + service_introduction + '\'' +
                ", risk_amount=" + risk_amount +
                ", finish_ticket='" + finish_ticket + '\'' +
                ", finish_type=" + finish_type +
                ", service_end_time='" + service_end_time + '\'' +
                ", real_service_start_time='" + real_service_start_time + '\'' +
                ", real_service_end_time='" + real_service_end_time + '\'' +
                ", service_end_location='" + service_end_location + '\'' +
                ", real_service_end_location='" + real_service_end_location + '\'' +
                ", total_amount=" + total_amount +
                ", attach='" + attach + '\'' +
                ", finish_transaction_id='" + finish_transaction_id + '\'' +
                '}';
    }
}
