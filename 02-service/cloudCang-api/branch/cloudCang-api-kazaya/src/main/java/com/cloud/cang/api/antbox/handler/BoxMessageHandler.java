package com.cloud.cang.api.antbox.handler;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BoxMessageHandler<IN, OUT> {
    private final Logger LOG = LoggerFactory.getLogger(BoxMessageHandler.class);

    private List<MessageListener<OUT>> listeners = null;

    public abstract void handle(BoxContext ctx, IN msg) throws IOException;

    public void registerBizHandler(MessageListener<OUT> handler) {
        if (listeners == null) {
            listeners = new ArrayList<MessageListener<OUT>>(3);
        }

        listeners.add(handler);
        LOG.info("add listener: {} for handler: {}", handler.getClass().getSimpleName(),
                getClass().getSimpleName());
    }

    public int getListenerSize() {
        return listeners == null ? 0 : listeners.size();
    }

    public void notifyListeners(BoxContext ctx, OUT msg) {
        if (listeners == null || listeners.isEmpty())
            return;
        for (MessageListener<OUT> listener : listeners) {
            listener.onMessage(ctx.getBoxInfo(), msg);
        }
    }

}
