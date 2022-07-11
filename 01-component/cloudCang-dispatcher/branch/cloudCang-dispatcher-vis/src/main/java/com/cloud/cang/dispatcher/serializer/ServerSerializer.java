package com.cloud.cang.dispatcher.serializer;

import com.cloud.cang.dispatcher.server.Server;

public interface ServerSerializer
{
    byte[]             serialize(Server server) throws Exception;

    Server deserialize(byte[] bytes) throws Exception;
}
