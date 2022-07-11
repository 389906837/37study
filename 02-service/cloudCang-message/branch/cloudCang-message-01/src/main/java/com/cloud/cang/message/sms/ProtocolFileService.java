package com.cloud.cang.message.sms;

import com.cloud.cang.message.ProtocolBuildDto;
import com.cloud.cang.message.exception.ProtocolException;

public interface ProtocolFileService {
    /**
     * 创建HTML协议
     *
     * @param protocolBuildDto
     * @return 返回生成HTML字符串
     */
    String buildProtocol2HTML(ProtocolBuildDto protocolBuildDto) throws ProtocolException;

}
