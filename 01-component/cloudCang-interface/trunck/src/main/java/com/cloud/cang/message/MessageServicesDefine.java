package com.cloud.cang.message;

import com.cloud.cang.common.ResponseVo;

/**
 * 服务外服务定义
 *
 * @author zhouhong
 * @version 1.0
 */
public class MessageServicesDefine {

    /**
     * 单条消息发送服务
     * 请求参数：{@link MessageDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_SINGLE_MESSAGE_SEND_SERVICE = "com.cloud.cang.message.ws.SMSService.sendMessage";

    /**
     * 单条消息发送服务 短信数据不记录数据库
     * 请求参数：{@link MessageDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_SINGLE_SEC_MESSAGE_SEND_SERVICE = "com.cloud.cang.message.ws.SMSService.sendSecMessage";

    /**
     * 根据模板协议创建HTML
     * 请求参数：{@link ProtocolBuildDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String PROTOCOL_BULID_HTML_SERVICE = "com.cloud.cang.message.ws.ProtocolService.buildProtocol2HTML";

    /**
     * 群发消息服务
     * 请求参数：{@link MessageDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_BATCH_MESSAGE_SEND_SERVICE = "com.cloud.cang.message.ws.SMSService.batchSendMessage";
    /**
     * 发送营销短信
     * 请求参数：{@link SalesMsgDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_SALES_MESSAGE_SEND_SERVICE = "com.cloud.cang.message.ws.SMSService.salesSendMessage";

    /**
     * 发送消息给内部人
     * 请求参数：{@link InnerMessageDto}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_MESSAGE_SEND_INNER_SERVICE = "com.cloud.cang.message.ws.SMSService.sendMessageToInner";

    /**
     * 查询短信余额
     * 请求参数：{@link String supplierCode 供应商编号}
     * 返回数据：{@link ResponseVo <String>}
     */
    public static final String SMS_MESSAGE_QUERY_BALANCE_SERVICE = "com.cloud.cang.message.ws.SMSService.queryBalance";


}
