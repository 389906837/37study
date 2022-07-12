package com.cloud.cang.mgr.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sp.domain.BrandInfoDomain;
import com.cloud.cang.mgr.sp.vo.BrandInfoVo;
import com.cloud.cang.model.sp.BrandInfo;
import com.github.pagehelper.Page;

/** 商品品牌管理信息表(SP_BRAND_INFO) **/
public interface BrandInfoDao extends GenericDao<BrandInfo, String> {
    /**
     * 获取品牌
     * @param smerchantId 商户Id
     * @return
     */
    List<SelectVo> selectByMerchantId(String smerchantId);


    /**
     * 分页查询品牌
     * @param brandInfoVo
     */
    Page<BrandInfoDomain> selectByDomainWhere(BrandInfoVo brandInfoVo);

    /**
     * 获取品牌
     * @param smerchantId 商户Id
     * @return
     */
    List<BrandInfo> selectBrandByMerchantId(String smerchantId);

    /**
     * 获取所有的有效的品牌信息
     * @return
     */
    List<BrandInfo> selectAllValidBrand();


}