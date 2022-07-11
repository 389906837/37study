package com.cloud.cang.api.antbox.constant;


import com.cloud.cang.api.antbox.BoxContext;
import io.netty.util.AttributeKey;

/**
 * 全局常量
 *
 * @author yong.ma
 * @since 0.0.1
 */
public interface ChannelAttrKey {

    AttributeKey<BoxContext> ANTBOX_CHANNEL_CONTEXT = AttributeKey.newInstance("ANTBOX_CHANNEL_CONTEXT");
}
