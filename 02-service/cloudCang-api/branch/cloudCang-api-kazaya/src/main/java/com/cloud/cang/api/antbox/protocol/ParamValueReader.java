package com.cloud.cang.api.antbox.protocol;

import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ParamValueReader {

    public static ParamValueReader SINGLE_BYTE_READER = new ParamValueReader() {

        @Override
        public String read(ByteBuf buf) {
            return Integer.toHexString(buf.readByte());
        }
    };

    public static ParamValueReader LONG_READER = new ParamValueReader() {

        @Override
        public String read(ByteBuf buf) {
            return String.valueOf(buf.readLong());
        }
    };

    public static ParamValueReader DATE_READER = new ParamValueReader() {

        @Override
        public String read(ByteBuf buf) {
            Date date = AntboxUtil.readDate(buf);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        }
    };

    public String read(ByteBuf buf);
}
