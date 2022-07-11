package com.cloud.cang.api.antbox.command;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Command Object
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class Command {
    private final String name;
    private final Object param;

    private Command(String name, Object param) {
        Assert.hasText(name, "command name must not be empty");
        this.name = name;
        this.param = param;
    }

    public static Command valueOf(String name) {
        return new Command(name, null);
    }

    public static Command valueOf(String name, String param) {
        return new Command(name, param);
    }

    public static Command valueOf(String name, byte[] param) {
        if (param == null || param.length == 0) {
            return new Command(name, null);
        } else {
            return new Command(name, param);
        }
    }

    public String getName() {
        return name;
    }

    public Object getParam() {
        return param;
    }

    @Override
    public String toString() {
        if (Objects.isNull(param)) {
            return name;
        }
        if (param instanceof byte[]) {
            return name + "0x" + ByteBufUtil.hexDump((byte[]) param);
        }
        return name + param;
    }

    public ByteBuf toByteBuf() {
        if (Objects.isNull(param)) {
            return Unpooled.wrappedBuffer((name + '\n').getBytes());
        } else if (param instanceof byte[]) {
            final byte[] tmp = name.getBytes();
            ByteBuf buf = Unpooled.buffer(tmp.length + ((byte[]) param).length + 1);
            buf.writeBytes(tmp);
            buf.writeBytes((byte[]) param);
            buf.writeByte('\n');
            return buf;
        } else {
            return Unpooled.wrappedBuffer((name + param + '\n').getBytes());
        }
    }
}
