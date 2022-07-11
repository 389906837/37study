package com.cloud.cang.dispatcher.utils;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializerUtils {

	public static byte[] serialize(Object o) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mapper.writeValue(out, o);

		return out.toByteArray();
	}

	public static <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		T rawServiceInstance = mapper.readValue(bytes, clazz);
		return rawServiceInstance;
	}
	
	
	public static <T> T deserialize(String json, Class<T> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		T rawServiceInstance = mapper.readValue(json, clazz);
		return rawServiceInstance;
	}


}
