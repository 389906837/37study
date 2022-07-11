package com.cloud.cang.api.sl.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.sl.LoginLog;
import com.cloud.cang.generic.GenericService;

public interface LoginLogService extends GenericService<LoginLog, String> {


    /**
     * 刷脸登录
     *
     * @param aiId         设备ID
     * @param key          通信密钥
     * @param base64String 图片字符串
     * @param userIds      用户ID集合
     * @param ip           访问IP地址
     * @return 成功返回用户ID，失败返回错误原因
     */
    ResponseVo<String> faceLogin(String aiId, String key, String base64String, String userIds, String ip);



}