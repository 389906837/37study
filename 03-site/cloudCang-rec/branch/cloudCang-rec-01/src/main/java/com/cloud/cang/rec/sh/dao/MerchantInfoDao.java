package com.cloud.cang.rec.sh.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.rec.sh.domain.MerchantInfoDomain;
import com.cloud.cang.rec.sh.vo.MerchantInfoVo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.github.pagehelper.Page;

/**
 * 商户基础信息表(SH_MERCHANT_INFO)
 **/
public interface MerchantInfoDao extends GenericDao<MerchantInfo, String> {


    /**
     * 查询商户列表
     *
     * @param merchantInfoDomain
     * @return
     */
    Page<MerchantInfoVo> selectPageByDomainWhere(MerchantInfoDomain merchantInfoDomain);

    MerchantInfo selectByCode(String code);
}