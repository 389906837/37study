package com.cloud.cang.bzc.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.model.hy.MemberInfo;

/**
 * 会员服务类接口
 *
 * @Author: zengzexiong
 * @Date: 2017年12月27日
 * @version 1.0
 */

public interface MemberInfoService extends GenericService<MemberInfo, String> {

    /**
     * 会员账户注册
     * @param memDto
     * @return
     */
    public ResponseVo<MemberInfo> accountRegister(MemberInfoDto memDto);
}
