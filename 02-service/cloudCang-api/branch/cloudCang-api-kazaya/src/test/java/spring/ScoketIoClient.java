package spring;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.Arrays;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-13 19:29
 **/
public class ScoketIoClient {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        IO.Options options = new IO.Options();
        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = 5;     // 重连尝试次数
        options.reconnectionDelay = 1000;     // 失败重连的时间间隔(ms)
        options.timeout = 20000;              // 连接超时时间(ms)
        options.forceNew = true;
       // options.query = "deviceId=73199912922&userId=1&deviceCode=1&userName=1&phoneNumber=1&apiKey=1&userAuthToken=1&sessionId=1&types=10";
        socket = IO.socket("http://149.129.134.55:28860/"/*, options*/);

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("======EVENT_CONNECT=连接服务端成功========");
                socket.emit("socketEvent","你好，服务端！我是客户端");
            }
        });
        socket.on("event_open_door", new Emitter.Listener() {
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
        socket.connect();
    }
}
