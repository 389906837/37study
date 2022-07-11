package com.cloud.cang.dispatcher.serializer.impl;

import java.io.ByteArrayOutputStream;

import com.cloud.cang.dispatcher.serializer.InstanceSerializer;
import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonInstanceSerializer implements InstanceSerializer {
	private final ObjectMapper mapper;
	private final JavaType type;

	public JsonInstanceSerializer() {
		mapper = new ObjectMapper();
		type = mapper.getTypeFactory().constructType(ServiceInstance.class);
	}

	@Override
	public ServiceInstance deserialize(byte[] bytes) throws Exception {
		ServiceInstance rawServiceInstance = mapper.readValue(bytes, type);
		return rawServiceInstance;
	}

	@Override
	public byte[] serialize(ServiceInstance instance) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, instance);
		return out.toByteArray();
	}
}
