package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

/**
 * 设置设备为离线的参数
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年4月13日13:49:57
 */
public class SetDeviceOfflineDto extends SuperDto {

    // 设备ID,如果是多台用逗号“，”拼接
    private String id;  //必填

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
