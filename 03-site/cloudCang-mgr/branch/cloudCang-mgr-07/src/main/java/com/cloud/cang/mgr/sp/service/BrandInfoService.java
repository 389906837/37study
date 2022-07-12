package com.cloud.cang.mgr.sp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sp.domain.BrandInfoDomain;
import com.cloud.cang.mgr.sp.vo.BrandInfoVo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BrandInfoService extends GenericService<BrandInfo, String> {

    /**
     * 获取品牌
     * @param smerchantId 商户Id
     * @return
     */
    List<SelectVo> selectByMerchantId(String smerchantId);

    /**
     * 分页查询商品品牌
     * @param page
     * @param brandInfoVo 品牌
     * @return
     */
    Page<BrandInfoDomain> selectPageByDomainWhere(Page<BrandInfoDomain> page, BrandInfoVo brandInfoVo);

    /**
     * 删除商品品牌信息
     *
     * @param checkboxId
     * @param merchantId
     * @return
     */
    ResponseVo<String> batchLogicDelByIds(String[] checkboxId, String merchantId);

    /**
     * 根据商户ID获取品牌
     * @param smerchantId
     * @return
     */
    List<BrandInfo> selectBrandByMerchantId(String smerchantId);

    /**
     * 查询所有可用的品牌信息
     * @return
     */
    List<BrandInfo> selectAllValidBrand();


    /**
     * 修改品牌信息
     * 同步到视觉与商品列表
     *
     * @param brandInfo
     * @param brandLogo
     */
    void updateAndSynchToCommodity(BrandInfo brandInfo, MultipartFile brandLogo);

}