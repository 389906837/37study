package com.cloud.cang.api.antbox.util;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Created by oyhk on 2017/11/17.
 */
public class RelayServerUtils {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 调用继电器服务器，重启server
     *
     * @param boxId
     * @param log
     */
    public static void reboot(Long boxId, Logger log) {
        // 异步调用继电器重启api
        try {
            okhttp3.Request request = new okhttp3.Request.Builder().url("http://relay.mayihezi.com:9080/relay-server/reboot/" + boxId + "/reader/3").build();
            okhttp3.Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            log.info("server monitor >> call relay api , boxId : {}  result : {}", boxId, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
