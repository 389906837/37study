package com.cloud.cang.api.antbox.constant;

public enum InventoryType {
    CLOSED((byte) 0x00, "关门盘点"), CMD((byte) 0x01, "指令盘点");

    byte code;
    String desc;

    InventoryType(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static InventoryType getByCode(byte code) {
        for (InventoryType type : InventoryType.values()) {
            if (Byte.parseByte(Integer.toHexString(type.code)) == code) return type;
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
