package com.cloud.cang.mgr.common;

/**
 * 对枚举数据的定义
 */
public class EnumDefinition {
    /**
     * 用户状态
     */
    public enum SysUserStatus {

        /**
         * 用户状态:0正常 1停用 2:冻结
         */
        FREEZE(0,"冻结");

        private int code;
        private String desc;

        SysUserStatus(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode(){
            return this.code;
        }
        public String getDesc(){
            return this.desc;
        }


    }

    /**
     * 设备操作类型
     */
    public enum DeviceOperateType {

        /**
         * 类型:0:音量 1:盘货 2:升级语音 3:系统升级 4:重启 5:关机 6:定时关机 7:停用
         */
        VOLUME(0,"音量"),
        INVENTORY(1,"盘货"),
        UPGRADEVOICE(2,"升级语音"),
        systemupgrade(3,"系统升级"),
        REBOOT(4,"重启"),
        SHUTDOWN(5,"关机"),
        TIMINGBOOT(6,"定时关机"),
        DISABLED(7,"停用");

        private int code;
        private String desc;

        DeviceOperateType(int code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public int getCode(){
            return this.code;
        }
        public String getDesc(){
            return this.desc;
        }
    }

    /**券类型 数据字典*/
    public static final String SCOUPON_TYPE_PARAM_CODE="SP000022";

    //--------------------优惠券批量下发状态---------
    /**优惠券批量下发状态：草稿*/
    public static final int  COUPON_ISTATUS_DRAFT=10;
    /**优惠券批量下发状态：待审核*/
    public static final int  COUPON_ISTATUS_NOT=11;
    /**优惠券批量下发状态：审核通过*/
    public static final int  COUPON_ISTATUS_PASS=20;

    //中间状态
    public static final int  COUPON_ISTATUS_ING=22;

    /**优惠券批量下发状态：审核拒绝REFUSED*/
    public static final int  COUPON_ISTATUS_REFUSED=21;
    //----------------------------------------------

}
