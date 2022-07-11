package com.cloud.cang.api.common;

import com.cloud.cang.core.common.EnumInterface;

/**
 * 对枚举数据的定义
 *  消息类型 :MemberStatus
 *  --：--
 */
public class TypeEnum {

    public enum MsgType implements EnumInterface {
        //10=心跳消息 20=普通消息 30=异常消息 40=注册消息
        HEARTBEAT(10,"心跳包"),
        COMMON(20,"普通消息包"),
        EXCEPTION(30,"异常消息"),
        REGISTER(40,"注册消息"),
        TEST(50, "测试消息");
        private int code;
        private String desc;

        MsgType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public int getCode() {
            return this.code;
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public static String getDescByCode(int code) {
            for (MsgType type : MsgType.values()) {
                if (type.getCode() == code)
                    return type.getDesc();
            }
            return "";
        }
    }
}
