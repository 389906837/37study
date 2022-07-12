package com.cloud.cang.mgr.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sh.domain.MerchantInfoDomain;
import com.cloud.cang.mgr.sh.vo.MerchantInfoVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.github.pagehelper.Page;

/** 商户基础信息表(SH_MERCHANT_INFO) **/
public interface MerchantInfoDao extends GenericDao<MerchantInfo, String> {


    void updateByIdSelectiveBIS_FREEZE(MerchantInfo merchantInfo);

    /**
     * 查询商户列表
     * @param merchantInfoDomain
     * @return
     */
    Page<MerchantInfoVo> selectPageByDomainWhere(MerchantInfoDomain merchantInfoDomain);

    /**
     * 查询商户名是否存在
     * @param sname
     * @return
     */
    int isNameExist(String sname);
    /**
     * 根据Id修改商户
     * @param merchantInfo
     * @return
     */
    void updateById(MerchantInfo merchantInfo);

    /**
     * 查询商户列表更新缓存
     *
     * @param merchantInfoDomain
     */
    List<MerchantInfo> selectByDomainWhere(MerchantInfoDomain merchantInfoDomain);
    /**
     * 获取商户信息
     * @param merchantCode 商户编号
     * @return
     */
    MerchantInfo selectByCode(String merchantCode);
}