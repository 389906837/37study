package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.domain.MemberInfoDomain;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.model.hy.MemberInfo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/** 会员基础信息表(HY_MEMBER_INFO) **/
public interface MemberInfoDao extends GenericDao<MemberInfo, String> {


    //查询列表
    Page<MemberInfoDomain> selectAllMemberInfo(MemberInfoVo memberInfoVo);

    MemberInfo selectByMobile(@Param("mobile") String mobile, @Param("merchantCode") String merchantCode);
    /**
     * 根据查询条件获取导出用户信息
     * @param memberInfoVo
     * @return
     */
    List<Map<String,Object>> selectMemberInfoByExport(MemberInfoVo memberInfoVo);
}