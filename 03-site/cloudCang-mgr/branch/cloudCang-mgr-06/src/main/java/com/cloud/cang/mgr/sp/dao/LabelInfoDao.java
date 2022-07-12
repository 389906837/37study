package com.cloud.cang.mgr.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sp.domain.LabelInfoDomain;
import com.cloud.cang.mgr.sp.vo.LabelInfoVo;
import com.cloud.cang.model.sp.LabelInfo;
import com.github.pagehelper.Page;

/** 商品标签管理信息表(SP_LABEL_INFO) **/
public interface LabelInfoDao extends GenericDao<LabelInfo, String> {

    /**
     * 分页查询商品标签
     * @param labelInfoVo
     */
    Page<LabelInfoDomain> selectByDomainWhere(LabelInfoVo labelInfoVo);

    /**
     * 根据商户ID查询商品标签集合
     * @param merchantId
     * @return
     */
    List<LabelInfo> selectLabelByMerchantId(String merchantId);

    /**
     * 根据商户ID查询父标签集合
     * @param merchantId
     * @return
     */
    List<LabelInfo> selectParentLabelByMerchantId(String merchantId);

    /**
     * 自定义编辑
     * @param labelInfo
     * @return
     */
    int updateByIdSelectiveVo(LabelInfo labelInfo);



    /** codegen **/
}