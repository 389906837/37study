package com.cloud.cang.api.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.generic.GenericService;

public interface MemberInfoService extends GenericService<MemberInfo, String> {


    /**
     * 根据手机尾号查找用户是否存在
     *
     * @param deviceId 设备ID
     * @param key      通信密钥
     * @param tailNum  尾号
     * @return 查询到匹配用户时，返回userId拼接字符串
     */
    ResponseVo<String> searchUser(String deviceId, String key, String tailNum);


}