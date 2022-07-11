package com.cloud.cang.api.antbox.listener;

import com.cloud.cang.api.antbox.dto.BoxInfo;
import com.cloud.cang.api.antbox.message.GetBoxInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class GetBoxInfoResponseListener implements MessageListener<GetBoxInfoResponse> {


    @Override
    public void onMessage(BoxInfo boxInfo, GetBoxInfoResponse msg) {
        if (boxInfo == null || boxInfo.getBoxId() == null)
            return;

    }

}
