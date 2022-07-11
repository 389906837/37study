package com.cloud.cang.dispatcher.serializer.impl;

import java.io.ByteArrayOutputStream;

import com.cloud.cang.dispatcher.serializer.ServerSerializer;
import com.cloud.cang.dispatcher.server.Server;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonServerSerializer implements ServerSerializer {
	private final ObjectMapper mapper;
	private final JavaType type;

	public JsonServerSerializer() {
		mapper = new ObjectMapper();
		type = mapper.getTypeFactory().constructType(Server.class);
	}

	@Override
	public Server deserialize(byte[] bytes) throws Exception {
		Server server = mapper.readValue(bytes, type);
		return server;
	}

	@Override
	public byte[] serialize(Server server) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, server);
		return out.toByteArray();
	}
}
