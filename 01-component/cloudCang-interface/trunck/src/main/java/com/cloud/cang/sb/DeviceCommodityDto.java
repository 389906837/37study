package com.cloud.cang.sb;

import com.cloud.cang.message.MessageDto;

/**
 * 长连接后台服务，设备商品信息
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年1月24日15:37:16
 */
public class DeviceCommodityDto extends MessageDto {
    /* ----------参数结束 ----------*/
    /* 商品ID */
    private String scommodityId;

    /* 10=在售
            20=下架 */
    private Integer istatus;

    /* 商品编号 */
    private String scommodityCode;

    /* 商品数量 */
    private Integer scommodityCount ;

    /* ----------参数结束 ----------*/
    public String getScommodityId() {
        return scommodityId;
    }

    public void setScommodityId(String scommodityId) {
        this.scommodityId = scommodityId;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public String getScommodityCode() {
        return scommodityCode;
    }

    public void setScommodityCode(String scommodityCode) {
        this.scommodityCode = scommodityCode;
    }

    public Integer getScommodityCount() {
        return scommodityCount;
    }

    public void setScommodityCount(Integer scommodityCount) {
        this.scommodityCount = scommodityCount;
    }
}
