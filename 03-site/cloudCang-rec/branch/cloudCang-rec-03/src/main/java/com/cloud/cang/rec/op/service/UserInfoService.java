package com.cloud.cang.rec.op.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.github.pagehelper.Page;

import java.util.List;

public interface UserInfoService extends GenericService<UserInfo, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param userInfoDomain
     * @return
     */
    Page<UserInfoVo> selectPageByDomainWhere(Page<UserInfoVo> page, UserInfoDomain userInfoDomain);


    /**
     * 删除开放平台用户
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);


    /**
     * 根据Id查看Vo
     *
     * @param id
     * @return
     */
    UserInfoVo selectVoById(String id);

    /**
     * 保存平台用户
     *
     * @param userInfo
     * @return
     */
    UserInfo saveUserInfo(UserInfo userInfo);

    /**
     * 编辑平台用户
     *
     * @param userInfo
     */
    UserInfo upUserInfo(UserInfo userInfo);

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    void updatePassword(String userId, String password);

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    void resetPassword(String id);

    /**
     * 查询用户接口权限
     */
    List<UserInterfaceAuthVo> selectUserInterfaceAuthVoList(String sid);

    /**
     * 保存接口权限
     *
     * @param pkIds
     * @param userInfo
     */
    void saveInterfaceAuth(String[] pkIds, UserInfo userInfo);

}