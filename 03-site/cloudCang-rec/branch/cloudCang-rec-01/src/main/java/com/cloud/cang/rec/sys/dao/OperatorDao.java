package com.cloud.cang.rec.sys.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.rec.sys.domain.OperatorDomain;
import com.cloud.cang.rec.sys.domain.UserPurviewDomain;
import com.cloud.cang.rec.sys.vo.OperatorVo;
import com.cloud.cang.rec.sys.vo.UserPurviewVo;
import com.cloud.cang.model.sys.Operator;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 后台用户表(SYS_OPERATOR)
 **/
public interface OperatorDao extends GenericDao<Operator, String> {

    Operator selectByUserName(@Param("suserName") String suserName, @Param("smerchantId") String smerchantId);

    void updateForLoginSuccess(String id);

    Page<OperatorDomain> selectByDomainWhere(OperatorVo operatorVo);

    void updateByIdSelectiveBIS_FREEZE(Operator operator);

    Page<UserPurviewVo> selectOpertorPurview(UserPurviewDomain userPurviewDomain);

    void upByIdSelective(Operator operator);

    Operator selectByUsernameAndMerchant(Map<String, Object> map);

    Operator selectByIdAndMerchantId(Map<String, String> map);

}