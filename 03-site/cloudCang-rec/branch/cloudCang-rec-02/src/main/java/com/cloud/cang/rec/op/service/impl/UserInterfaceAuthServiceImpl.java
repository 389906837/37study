package com.cloud.cang.rec.op.service.impl;

import java.util.List;

import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.service.InterfaceAccountService;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.cloud.cang.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.op.dao.UserInterfaceAuthDao;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInterfaceAuthServiceImpl extends GenericServiceImpl<UserInterfaceAuth, String> implements
        UserInterfaceAuthService {

    @Autowired
    UserInterfaceAuthDao userInterfaceAuthDao;
    @Autowired
    private InterfaceAccountService interfaceAccountService;

    @Override
    public GenericDao<UserInterfaceAuth, String> getDao() {
        return userInterfaceAuthDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param userInterfaceAuthDomain
     * @return
     */
    @Override
    public Page<UserInterfaceAuthVo> selectPageByDomainWhere(Page<UserInterfaceAuthVo> page, UserInterfaceAuthDomain userInterfaceAuthDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return userInterfaceAuthDao.selectPageByDomainWhere(userInterfaceAuthDomain);
    }

    /**
     * 关闭开放平台接口权限
     *
     * @param checkboxId
     */
    @Override
    @Transactional
    public UserInterfaceAuth close(String checkboxId) {
        UserInterfaceAuth userInterfaceAuth = userInterfaceAuthDao.selectByPrimaryKey(checkboxId);
        UserInterfaceAuth temp = new UserInterfaceAuth();
        temp.setId(checkboxId);
        temp.setIauthStatus(20);//10=已开通    20=已关闭
        temp.setIlastCloseTime(DateUtils.getCurrentDateTime());
        userInterfaceAuthDao.updateByIdSelective(temp);
        //关闭接口账户信息
        interfaceAccountService.closeOrOpenByUserIdAndInterfaceCode(userInterfaceAuth.getSuserId(), userInterfaceAuth.getSinterfaceCode(), 20);
        return userInterfaceAuth;
    }

    /**
     * 开通开放平台接口权限
     *
     * @param checkboxId
     */
    @Override
    @Transactional
    public UserInterfaceAuth open(String checkboxId) {
        UserInterfaceAuth userInterfaceAuth = new UserInterfaceAuth();
        userInterfaceAuth.setId(checkboxId);
        userInterfaceAuth.setUpdateTime(DateUtils.getCurrentDateTime());
        userInterfaceAuth.setIauthStatus(10);
        this.updateBySelective(userInterfaceAuth);
        UserInterfaceAuth temp = this.selectByPrimaryKey(checkboxId);
        //把接口账户打开
        interfaceAccountService.closeOrOpenByUserIdAndInterfaceCode(temp.getSuserId(), temp.getSinterfaceCode(), 10);
        return temp;
    }

    /**
     * 根据应用Id接口Code用户Id查询权限
     *
     * @param appId
     * @param interfaceCode
     * @param userId
     * @return
     */
    @Override
    public UserInterfaceAuth selectByAppIdAndInterfaceCodeAndUserId(String appId, String interfaceCode, String userId) {
        return userInterfaceAuthDao.selectByAppIdAndInterfaceCodeAndUserId(appId, interfaceCode, userId);
    }


    /**
     * 根据用户Id
     *
     * @param userId
     * @return
     */
    @Override
    public int selectUserAuthNum(String userId) {
        return userInterfaceAuthDao.selectUserAuthNum(userId);
    }
}