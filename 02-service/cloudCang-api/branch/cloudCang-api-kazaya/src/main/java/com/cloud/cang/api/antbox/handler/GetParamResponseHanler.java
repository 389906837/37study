package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.ParamKey;
import com.cloud.cang.api.antbox.message.GetParamResponse;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GetParamResponseHanler extends BoxMessageHandler<GetParamResponse, GetParamResponse> {

    @Override
    public void handle(BoxContext ctx, GetParamResponse msg) {
        List<ParamKey> keys = ctx.pollLastFromGetParamQueue();
        if (keys == null) {
            ctx.logIncredible("pollLast from getParamQueue return null.");
            return;
        }

        ByteBuf buf = msg.getCmdData();
        Map<String, String> dict = new HashMap<String, String>();
        for (ParamKey key : keys) {
            dict.put(Integer.toHexString(key.getCode()), key.getValueReader().read(buf));
        }

        StringBuilder sb = new StringBuilder();
        for (String key : dict.keySet()) {
            sb.append(key).append("=").append(dict.get(key)).append(",");
        }

        if (!dict.isEmpty())
            sb.deleteCharAt(sb.length() - 1);

        ctx.logInfo("Receive device params: {}", sb.toString());

        super.notifyListeners(ctx, msg);
    }

}
