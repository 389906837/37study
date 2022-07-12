package com.cloud.cang.mgr.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sh.domain.PurviewDomain;
import com.cloud.cang.mgr.sys.domain.OperatorDomain;
import com.cloud.cang.mgr.sys.domain.RoleDomain;
import com.cloud.cang.mgr.sys.vo.OperatorVo;
import com.cloud.cang.mgr.sys.vo.RoleVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Role;
import com.github.pagehelper.Page;
import org.apache.zookeeper.Op;

/** 角色表(SYS_ROLE) **/
public interface RoleDao extends GenericDao<Role, String> {

    /**
     * 根据用户id查角色信息
     * @param userId
     * @return
     */
     List<Role> selectByUserId(String userId);

    /**
     * 根据用户ID查询用户没有的角色
     * @param userId
     * @return
     */
     List<Role> selectOperatorNoCheckRole(String userId);
    /**
     * 查询所有角色
     * @param roleDomain
     * @return
     */
     Page<RoleVo> selectByDomainWhere( RoleDomain roleDomain);

    /**
     *查询用户商会下所有角色
     * @param operator
     * @return
     */
      List<Role> selectAllRole (Operator operator);
    /**
     *查询用户商会下所有角色
     * @param operator
     * @return
     */
     List<RoleVo> selectOperatorRole(Operator operator);
    /**
     * 获取商户权限
     * @param paramMap
     * @return
     */
    List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap);

}