package com.cloud.cang.rec.sys.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Purview;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 后台权限码表(SYS_PURVIEW)
 **/
public interface PurviewDao extends GenericDao<Purview, String> {

    List<Purview> selectByUserId(String userId);

    /**
     * 判断权限编码是否存在
     *
     * @param param
     * @return
     */
    Integer isNameExist(Purview param);

    /**
     * 获取商户所有权限码
     *
     * @param merchantId 商户ID
     * @return
     */
    List<Purview> selectByAllPurviewByMerchantId(String merchantId);

    /**
     * 获取商户权限
     *
     * @param paramMap
     * @return
     */
    List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap);

    /**
     * 获取后台用户
     *
     * @param id 用户ID
     * @return
     */
    Operator selectOperatorByPrimaryKey(String id);

    /**
     * 查询商家对应的权限
     */
    List<Purview> selectMerchantPurview(String merchantId);


    /**
     * 删除商户权限码关联表数据
     *
     * @param purviewId 权限码ID
     */
    void deletePurviewMerchantByPurviewId(String purviewId);

    /**
     * 删除角色权限关联信息
     *
     * @param purviewId 权限码ID
     */
    void deleteRolePurviewByPurviewId(String purviewId);

    /**
     * 查询用户所有角色下的权限
     */
     List<String> selectOperatorPurview(String operatorId);
    /**
     * 分页查询权限数据
     * @param map 查询参数
     * @return
     */
    Page<Purview> selectPageByMap(Map<String, Object> map);
}