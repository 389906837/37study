package com.antbox.rfidmachine.helper;

import com.antbox.rfidmachine.service.LocalService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.text.SimpleDateFormat;

/**
 * Created by DK on 17/5/9.
 */
public enum RetrofitHelper {

    SINGLETON;

    private static LocalService localService;

    public Retrofit getRetrofit() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(ConstantHelper.RETROFIT_URL)
                .build();

        return retrofit;
    }

    public static LocalService getLocalService() {
        if (localService == null) {
            synchronized (RetrofitHelper.class) {
                Retrofit retrofit = RetrofitHelper.SINGLETON.getRetrofit();
                localService = retrofit.create(LocalService.class);
            }
        }
        return localService;
    }
}
