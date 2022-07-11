package com.cloud.cang.api.hy.service;

import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.model.hy.MemberInfo;

public interface VendstopService {
    MemberInfo registUser(SessionVo sessionVo);
}
