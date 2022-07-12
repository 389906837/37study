package com.cloud.cang.mgr.sp.service;

import com.cloud.cang.mgr.sp.domain.LabelInfoDomain;
import com.cloud.cang.mgr.sp.vo.LabelInfoVo;
import com.cloud.cang.model.sp.LabelInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface LabelInfoService extends GenericService<LabelInfo, String> {


    /**
     * 分页查询商品标签
     * @param page
     * @param labelInfoVo
     * @return
     */
    Page<LabelInfoDomain> selectPageByDomainWhere(Page<LabelInfoDomain> page, LabelInfoVo labelInfoVo);

    /**
     * 逻辑删除商品标签
     * @param checkboxId
     */
    void batchLogicDelByIds(String[] checkboxId);

    /**
     * 根据商户ID查询标签集合
     * @param merchantId 商户ID
     * @return 该商户下的标签集合
     */
    List<LabelInfo> selectLabelByMerchantId(String merchantId);

    /**
     * 根据商户ID查询父标签集合
     * @param merchantId
     * @return
     */
    List<LabelInfo> selectParentLabelByMerchantId(String merchantId);


    /**
     * 自定义修改
     * @param labelInfo
     */
    int updateBySelectiveVo(LabelInfo labelInfo);

}