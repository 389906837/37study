package spring;


import com.alibaba.fastjson.JSON;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-13 19:29
 **/
public class ScoketIoClient {
    private static Socket socket;
    static String usertoken="eyJhbGciOiJSUzI1NiIsImtpZCI6ImM4NDI1Zjk0YmVlMWU0N2YxNGQ3OWZhYmQ4MTIyNTRiMWJmOTE4YzAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vdmVuZHN0b3AtMzU5YzYiLCJhdWQiOiJ2ZW5kc3RvcC0zNTljNiIsImF1dGhfdGltZSI6MTU2Njk2NzI5NSwidXNlcl9pZCI6IklxSEpQbGhXVUhjNmdESWlWQlNnbkZqTTZlRzIiLCJzdWIiOiJJcUhKUGxoV1VIYzZnRElpVkJTZ25Gak02ZUcyIiwiaWF0IjoxNTY2OTkxOTI5LCJleHAiOjE1NjY5OTU1MjksInBob25lX251bWJlciI6Iis5MTcwMTY1MDA3MDUiLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7InBob25lIjpbIis5MTcwMTY1MDA3MDUiXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwaG9uZSJ9fQ.c9SpvXzM88Zf78UNpPKXt3I9IwADb5AKB0iTmICKLpxOJee0TRCdLBG11MplPkcalZBqNfyqcBzmqmNYceFbpR4GcdUH_Cu2Zxgl-SXn_AOtIE3kNFAy25g3_OOmQrDPeYbVLHxN6xZoBCA2kzHNy_I6JmrotKOJkDcnsldo9Fng7AJNl-LtU3ZUmXsNJggaoIUSsANVK4IOIOGh15MI4SBJrKcnQBYG3erf-AvHuOyHvVT5Ij69MuELPRPU9iGfHRMyUEHTu7CS4FYMTL_kPlljkRR3bMu-ciEfr1F3aTbHVpB_xDD-Kjc3OTjmQ4M9fG7kLGEB6NslGQiNeP0Vbg";
    public static void main(String[] args) throws Exception {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = 5;     // 重连尝试次数
        options.reconnectionDelay = 1000;     // 失败重连的时间间隔(ms)
        options.timeout = 20000;              // 连接超时时间(ms)
        options.forceNew = true;
        options.query = "apiKey=57b0ddf3-87ef-4ff3-a11f-48b8534accb9" +
                        "&userAuthToken="+usertoken+
                        "&machineId=121" +
                        "&machineCode=D2323" +
                        "&userName=Test" +
                        "&phoneNumber=1234567890"+
                        "&deviceId=b60e0783f9c34460"+
                        "&userId=IqHJPlhWUHc6gDIiVBSgnFjM6eG2"+
                        "&sessionId=4CJSX9cPQyUCHXH-2NR9yMEo9YX4EwXC";
        socket = IO.socket("http://149.129.174.142:28860/",options);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_CONNECT=连接服务端成功========");
                Map<String,Object> map= new HashMap<>();
                map.put("deviceId","1");
                map.put("userId","1");
                map.put("types","1");
                socket.emit("event_open_door",JSON.toJSONString(map));
                socket.emit("socketEvent","你好，服务端！我是客户端");
                socket.emit("event_close_door","你好，服务端！我是客户端");

            }
        });
        socket.on("socketEvent", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                System.out.println("======socketEvent=接收到消息========" + Arrays.toString(args));
            }
        });
        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_CONNECT_ERROR========");
            }
        });
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_CONNECT_TIMEOUT========");
                socket.disconnect();
            }
        });
        socket.on(Socket.EVENT_PING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_PING========");
            }
        });
        socket.on(Socket.EVENT_PONG, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_PONG========");
            }
        });
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("====EVENT_DISCONNECT==服务端拒绝连接========");
            }
        });
        socket.on("event_close_door", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======event_close_door=接收到消息========" + Arrays.toString(args));
            }
        });

        socket.connect();
    }
}
