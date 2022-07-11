package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.message.SetClockResponse;
import org.springframework.stereotype.Component;

@Component
public class SetClockResponseHandler extends BoxMessageHandler<SetClockResponse, SetClockResponse> {

    @Override
    public void handle(BoxContext ctx, SetClockResponse msg) {
        super.notifyListeners(ctx, msg);
    }

}
