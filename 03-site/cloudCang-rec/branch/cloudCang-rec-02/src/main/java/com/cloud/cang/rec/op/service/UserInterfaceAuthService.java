package com.cloud.cang.rec.op.service;

import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.github.pagehelper.Page;

public interface UserInterfaceAuthService extends GenericService<UserInterfaceAuth, String> {


    /**
     * 查询列表
     *
     * @param page
     * @param userInterfaceAuthDomain
     * @return
     */
    Page<UserInterfaceAuthVo> selectPageByDomainWhere(Page<UserInterfaceAuthVo> page, UserInterfaceAuthDomain userInterfaceAuthDomain);

    /**
     * 关闭开放平台接口权限
     *
     * @param checkboxId
     */
    UserInterfaceAuth close(String checkboxId);

    /**
     * 开通开放平台接口权限
     *
     * @param checkboxId
     */
    UserInterfaceAuth open(String checkboxId);

    /**
     * 根据应用Id接口Code用户Id查询权限
     *
     * @param appId
     * @param interfaceCode
     * @param userId
     * @return
     */
    UserInterfaceAuth selectByAppIdAndInterfaceCodeAndUserId(String appId, String interfaceCode, String userId);


    /**
     * 根据用户Id
     * @param userId
     * @return
     */
    int selectUserAuthNum(String userId);
}