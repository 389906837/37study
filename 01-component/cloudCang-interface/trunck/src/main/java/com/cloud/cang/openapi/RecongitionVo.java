package com.cloud.cang.openapi;

import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import lombok.Data;

/**
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-18 18:37
 **/
@Data
public class RecongitionVo<T> {

    private String clientIp;

    private String batchNo;

    private AppManage appManage;

    private InterfaceInfo interfaceInfo;

    private CommonParam commonParam;

    private T bizContent;
}
