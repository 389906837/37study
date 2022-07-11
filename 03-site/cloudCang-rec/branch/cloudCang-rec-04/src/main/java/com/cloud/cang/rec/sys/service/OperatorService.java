package com.cloud.cang.rec.sys.service;

import com.cloud.cang.generic.GenericService;

import com.cloud.cang.rec.sys.domain.OperatorDomain;
import com.cloud.cang.rec.sys.domain.UserPurviewDomain;
import com.cloud.cang.rec.sys.vo.OperatorVo;
import com.cloud.cang.rec.sys.vo.UserPurviewVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.github.pagehelper.Page;

public interface OperatorService extends GenericService<Operator, String> {

    /**
     * 用户分配角色
     *
     * @param operatorId
     * @param operatorRoleIds
     */
    void saveOperatorRole(String operatorId, String[] operatorRoleIds);

    /**
     * 逻辑删除
     *
     * @param ids
     */
    void delete(String[] ids);

    /**
     * 修改密码
     *
     * @param operatorId
     * @param password
     */
    void updatePassword(String operatorId, String password);

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    boolean isUsernameExist(String username);

    /**
     * 判断手机号是否已存在
     *
     * @param mobile 手机号
     * @return
     */
    boolean isMobileExist(String mobile);

    /**
     * 分页查询
     *
     * @param page
     * @param operatorVo
     * @return
     */
    Page<OperatorDomain> selectPageByDomainWhere(Page<OperatorDomain> page, OperatorVo operatorVo);

    /**
     * 根据用户获取用户信息
     *
     * @param username
     * @return
     */
    Operator selectByUsername(String username);


    /**
     * 更新
     *
     * @param operator
     * @return
     */
    void updateByIdSelectiveBIS_FREEZE(Operator operator);

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    void resetPassword(String id) throws Exception;

    /***
     * 生成操作员权限 可查询sql
     * @param oper 系统用户
     * @param type 操作类型 10=商户查询 20 活动列表
     * @return
     */
    String generatePurviewSql(Operator oper, Integer type);

    /**
     * 查看用户权限
     *
     * @param userPurviewDomain
     * @return
     */
    Page<UserPurviewVo> selectUserPurview(Page<UserPurviewVo> page, UserPurviewDomain userPurviewDomain);


    /**
     * 添加商户同步系统用户
     *
     * @param merchantUserName 商户同步名
     * @return
     */
    void syncData(MerchantInfo merchantInfo, String merchantUserName);

    /**
     * 根据Id 修改系统用户
     *
     * @param operator
     * @return
     */
    void upByIdSelective(Operator operator);

    /***
     * 查询系统用户
     * @param username 用户名
     * @param merchantCode 商户编号
     * @return
     */
    Operator selectByUsernameAndMerchant(String username, String merchantCode);

    /***
     * 根据ID和商户ID查询系统用户
     * @param id
     * @param merchantId 商户ID
     * @return
     */
    Operator selectByIdAndMerchantId(String id, String merchantId);

}