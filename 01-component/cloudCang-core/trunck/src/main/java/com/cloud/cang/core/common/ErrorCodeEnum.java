
package com.cloud.cang.core.common;


import com.cloud.cang.common.ResponseVo;

import java.util.HashMap;
import java.util.Map;


/**
 * 请严格准命名规范
 * ERROR_系统代号_错误类型 <br />
 * 1001 -2000 通用错误码
 * 2001 - 2500 MSC系统错误码
 *
 * @author zhouhong
 * @version 2.0
 */
public enum ErrorCodeEnum {
    ERROR_COMMON_PARAM(1001, "参数不符合规则"),
    ERROR_COMMON_SYSTEM(1002, "系统错误"),
    ERROR_COMMON_HANDING(1003, "系统处理中"),
    ERROR_COMMON_SAVE(1004, "保存数据失败"),
    ERROR_COMMON_UNIQUE(1005, "违反了唯一约束"),
    ERROR_COMMON_CHECK(1006, "条件验证异常"),
    ERROR_COMMON_REPEAT_HANDLER(1007, "重复处理"),
    ERROR_COMMON_NETWORK_ERROR(1008, "网络超时"),
    ERROR_COMMON_OPEN_FREEPAY(1009, "未开通免密支付"),
    ERROR_COMMON_OPEN_WECHAT_POINT(1010, "未开通微信支付分"),


    ERROR_MGC_TEMPLATE(2001, "短信模板信息有误"),
    ERROR_MGC_TEMPLATE_GEN_CONTENT(2002, "模板生成内容出错"),
    ERROR_MGC_SENDMAIL(2003, "发送邮件错误"),
    ERROR_MGC_SEND(2004, "消息发送失败"),
    ERROR_PROTOCOL_BULID(2005, "生成协议失败"),


    ERROR_PAC_OPEN_ACCOUNT(3001, "积分账户开户异常"),
    ERROR_PAC_CHANGE_INTEGRAL(3002, "积分账户积分变更异常"),
    ERROR_PAC_CHANGE_CHECK(3003, "积分账户积分变更会员校验"),


    //----------------积分商城错误码 liuzhuo-----------------------
    ERROR_JMALL_MOBILENUM(10001, "手机号格式错误"),
    ERROR_JMALL_SYSTEM(10002, "系统错误"),
    ERROR_JMALL_TIME_OUT(10003, "Http请求超时"),
    ERROR_JMALL_HTTP(10004, "Http请求异常"),
    ERROR_JMALL_PARAM(10005, "参数错误"),
    ERROR_JMALL_INTERFACE_RETURN(10006, "接口返回的错误信息"),//此信息直接显示于页面
    ERROR_JMALL_RECHARGEING(10007, "充值中"),
    ERROR_JMALL_RECHARGE_FAIL(10008, "充值失败"),
    ERROR_JMALL_RECHARGE_NO_ORDER(10009, "不存在的订单"),
    ERROR_JMALL_RECHARGE_ORDER_TIMEOUT(10010, "订单超时"),
    ERROR_JMALL_INFO_QUERY(10011, "查询失败"),
    ERROR_JMALL_NO_PRODUCT(10012, "没有找到相应商品"),
    ERROR_JMALL_OUT_OF_STOCK(10013, "库存不足"),
    ERROR_JMALL_OUT_OF_INTEGER(10014, "积分不足"),
    ERROR_JMALL_CAN_NOT_RECHARGE(10015, "不能充值"),
    //签到
    ERROR_JMALL_SIGNIN_TOO(10016, "今天已经签到了"),
    ERROR_JMALL_SIGNIN_DISENABLED(10017, "签到未启用"),

    //公益确认
    ERROR_CONFIRMDONATIONS_ERROR(10048, "公益支付确认失败"),

    //合并券异常
    ERROR_ACT_MERGECOUPON_ERROR(10049, "合并券异常"),//直接在页面展示
    //----------------------------------------------------------------

    //支付中心
    ERROR_PAY_RECHARGE_ERROR(4001, "充值失败"),
    ERROR_PAY_TXCODE_ERROR(4002, "业务渠道获取异常"),
    ERROR_TX_PROCESS_ERROR(4003, "支付报文处理异常"),
    ERROR_PAY_SYSTEM_ERROR(4004, "业务处理失败"),
    ERROR_PAY_NEKWORD_ERROR(4005, "网络异常，错误失败"),
    ERROR_PAY_NOTFOUND_ERROR(4006, "数据未找到"),
    ERROR_PAY_OPEN_ERROR(4007, "开户失败"),
    ERROR_PAY_OPEN_ACTIVITY_ERROR(4008, "调用活动中心失败"),
    ERROR_PAY_BANK_NO_NOTFOUND_ERROR(4009, "获取绑定银行卡卡号失败"),
    ERROR_PAY_BANDING_CARD_ERROR(4010, "绑卡失败"),
    ERROR_PAY_THIRDPART_ERROR(4020, "与第三方支付交互失败"),
    ERROR_PAY_USER_TRADE_INVENTMENT(4021, "投资失败"),
    ERROR_PAY_INVESTMENT_BACKERROR(4022, "投资失败且退款失败"),
    ERROR_PAY_INVESTMENT_BACKOK(4023, "项目已满标，投资金额已退回到您的账户中"),
    ERROR_PAY_INVESTMENT_CHANGE_ACCOUNT_BACKOK(4024, "投资人账户异常，投资金额已退回到您的账户中"),
    ERROR_PAY_INVESTMENT_COUPON(4025, "您的投资金额已退回到您的账户中"),
    ERROR_PAY_WITHDRW_ERROR(4026, "提现处理失败"),
    ERROR_PAY_USER_UNOPEN(4027, "用户未开通第三方支付账号"),
    ERROR_PAY_USER_SETTLEMENT_INV_BATCH_INVENTMENT(4028, "给投资人批量结算失败"),
    ERROR_PAY_EXTPAY_BACKOK(4029, "支付退款"),
    ERROR_PAY_EXTPAY_ERROR_BACKOK(4030, "支付异常退款"),

    //app错误代码
    ERROR_APP_EXCEPTION(5004, "网络异常，请重新操作"),
    ERROR_APP_PARAM_EXCEPTION(5001, "请求参数异常"),
    ERROR_APP_PARAM_TAMPER(5002, "请求参数被篡改"),
    ERROR_APP_USER_NOT_LOGIN(5003, "会员未登录或登录已失效，请重新登录"),
    EMPTY_MEMBER(5007, "会员不存在"),
    STOP_MEMBER(5005, "会员已停用"),
    FREEZE_MEMBER(5006, "会员已冻结"),

    //活动服务错误代码
    ERROR_ACT_NO_ACTIVITY(6001, "没有找到相应活动"),
    ERROR_ACT_NOT_FULFIL(6002, "不满足活动条件"),
    ERROR_ACT_REC_REPEAT(6003, "重复推荐"),
    ERROR_ACT_NOT_USERANGE(6004, "活动范围明细不存在"),

    //会员及会员账户
    ERROR_FAC_FUND_ACCOUNT_OPEN_AGE_CHECK(7001, "用户年龄未满18岁"),
    ERROR_FAC_FUND_ACCOUNT_OPEN_IDEXIST(7003, "实名认证身份证号已经存在"),
    ERROR_FAC_MOBULE_EXISTING(7004, "手机号已存在"),
    ERROR_FAC_FUND_ACCOUNT_FREEZEN(7005, "资金账号被冻结"),
    ERROR_FAC_FUND_ACCOUNT_CANCEL(7006, "资金账号被注销"),

    ERROR_FUND_ACCOUNT(7007, "资金账户不可用"),
    ERROR_FUND_ACCOUNT_AMOUNT(7008, "账户资金不足"),
    ERROR_FUND_ACCOUNT_CHANGE(7009, "资金账户变更异常"),
    ERROR_FUND_ACCOUNT_ID_NOT_SAME(7010, "身份证号不一致"),

    //交易中心
    ERROR_TRADE_XSL_NOT_NEW_RIGISTER(8001, "非新注册用户"),
    ERROR_TRADE_XSL_IS_INVESTED(8002, "会员已经有过投资行为"),
    ERROR_TRADE_XSL_NOT_FIRST_BIND_BANK(8003, "会员非首次绑卡"),
    ERROR_TRADE_XSL_IS__EXIST(8004, "已经存在新手乐参与资格"),
    ERROR_TRADE_XSL_HAED_JOIN(8005, "已经参与过新手乐"),
    ERROR_TRADE_INVESTMENT_PROCESSING(8006, "投资请求处理中"),

    ERROR_TRADE_INVESTMENT(8007, "生成投资记录时异常"),
    ERROR_TRADE_INVESTMENT_BACKERROR(8008, "超募退款失败"),
    ERROR_TRADE_INVESTMENT_BACKOK(8009, "超募退款成功"),
    ERROR_TRADE_INVESTMENT_CHANGE_ACCOUNT_BACKOK(8010, "投资人账户异常，投资金额已退回到您的账户中"),
    ERROR_TRADE_INVESTMENT_COUPON_BACKERROR(8011, "使用券异常，退款失败"),
    ERROR_TRADE_INVESTMENT_COUPON_BACKOK(8012, "使用券异常，退款成功"),

    ERROR_TRADE_INVESTMENT_ERROR_BACKERROR(8013, "生成投资记录失败，退款成功"),
    ERROR_TRADE_INVESTMENT_ERROR_BACKOK(8014, "生成投资记录失败，退款成功"),

    ERROR_REPAYMENT_EXIST(8015, "还款记录已经存在"),
    ERROR_REPAYMENT_NOT_SETTLE(8016, "还款成功，结算给投资人失败"),

    ERROR_TRADE_INVESTMENT_AGAIN_BACKOK(8017, "补投资处理退款成功"),
    ERROR_TRADE_INVESTMENT_NOT_USE_ADD_COUPON(8018, "项目不支持加息券"),
    //债权转让
    ERROR_TRADE_DEBT_NOT_IN_LISTTING(8020, "不在转让时间内"),
    ERROR_TRADE_DEBT_NOT_TRANSFER_PROJ(8021, "不可转让项目"),
    ERROR_TRADE_DEBT_NOT_INCOMING_PROJ(8022, "不在收益中的项目不可转让"),
    ERROR_TRADE_DEBT_MUST_SETTLE_ONEMORE_TIMES(8023, "必须结过一次息"),
    ERROR_TRADE_DEBT_MUST_ADVANCE_THREE_DAYS_SETTLE(8024, "每次结算前三天不能转让"),
    ERROR_TRADE_DEBT_PREMIUM_OVER_INTEREST(8025, "溢价不得超过总余下利息"),
    ERROR_TRADE_DEBT_DISCOUNT_OVER_INTEREST(8026, "折价不得多余已付利息"),
    //预约
    ERROR_TRADE_ORDER_BACKERROR(8031, "可预约金额不足平台退款失败"),
    ERROR_TRADE_ORDER_BACKOK(8032, "可预约金额不足平台退款成功"),
    ERROR_TRADE_ORDER_ERROR_BACKERROR(8033, "预约请求处理失败且退款失败"),
    ERROR_TRADE_ORDER_ERROR_BACKOK(8034, "预约请求处理失败退款成功"),
    ERROR_TRADE_ORDER_AGAIN_BACKOK(8035, "补预约处理退款成功"),
    ERROR_TRADE_ORDER_RUFUND_BACKOK(8036, "预约退款失败"),
    ERROR_TRADE_NOT_MEET_START_TIME(8038, "未到投资开始时间"),

    ERROR_SETTLEMENT_AMOUNT(9001, "结算支付金额不足"),
    ERROR_SETTLEMENT_NOTEXIST(9002, "没有可用的结算记录"),
    ERROR_SETTLEMENT_NOTPAY_AFTER(9003, "前一期有未结算记录"),
    ERROR_SETTLEMENT_NOTPAY(9008, "项目当期没有进行还款"),
    ERROR_SETTLEMENT_PAY(9004, "结算支付失败"),
    ERROR_SETTLEMENT_NOT_PAY_ALL(9005, "部分结算成功"),
    ERROR_SETTLEMENT_NOT_PAYRECORD(9006, "提前还款记录不存在"),
    ERROR_SETTLEMENT_MANUAL(9007, "手动请求结算异常"),

    ERROR_CONFIRM_PASS_NOT_REFUND(10010, "融资确认成功退预约金异常"),
    ERROR_FLOW_PASS_NOT_REFUND(10011, "流标成功退预约金异常"),

    //智能垃圾箱异常
    ERROR_MEMBER_NOT_EXIST(401, "会员不存在"),
    ERROR_INTERFACE_AUTH(410, "接口鉴权失败"),
    ERROR_UNKNOWN(404, "未知错误");
    private int code;
    String returnMsg;

    ErrorCodeEnum(int code, String returnMsg) {
        this.code = code;
        this.returnMsg = returnMsg;
    }

    public int getCode() {
        return this.code;
    }

    public <T> ResponseVo<T> getResponseVo(T t) {
        return new ResponseVo(false, this.code, this.returnMsg, t);
    }

    public ResponseVo getResponseVo() {
        return new ResponseVo(false, this.code, this.returnMsg);
    }

    public ResponseVo getResponseVo(String msg) {
        return new ResponseVo(false, this.code, msg);
    }

    private static Map<Integer, ErrorCodeEnum> params;
    private static Object lock = new Object();

    static {
        synchronized (lock) {
            if (params == null) {
                params = new HashMap<Integer, ErrorCodeEnum>();
                for (ErrorCodeEnum e : ErrorCodeEnum.values()) {
                    params.put(e.code, e);
                }
            }
        }
    }


    public static String getNameByCode(int code) {
        return params.get(code).returnMsg;
    }

    public static ErrorCodeEnum getEnumByCode(int code) {
        return params.get(code);
    }
}
