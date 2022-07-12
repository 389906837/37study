package com.cloud.cang.mgr.vr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.vr.domain.CommoditySkuDomain;
import com.cloud.cang.mgr.vr.vo.CommoditySkuVo;
import com.cloud.cang.model.vr.CommoditySku;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 视觉识别商品标准SKU(VR_COMMODITY_SKU)
 **/
public interface CommoditySkuDao extends GenericDao<CommoditySku, String> {


    /**
     * 分页查询商品SKU信息
     * @param commoditySkuVo 查询条件
     * @return 分页结果
     */
    Page<CommoditySkuDomain> selectPageByDomainWhere(CommoditySkuVo commoditySkuVo);

    /**
     * 根据商户ID分页查询商品SKU信息
     * @param commoditySkuVo
     * @return
     */
    Page<CommoditySkuDomain> selectPageByMerchantId(CommoditySkuVo commoditySkuVo);

    /**
     * 查询sku类型为全部的商户下的SKU商品
     *
     * @param commoditySkuVo
     * @return
     */
    Page<CommoditySkuDomain> selectPageByValidStatus(CommoditySkuVo commoditySkuVo);

    /**
     * 自定义方法
     * @param commoditySku
     * @return
     */
    int updateByIdSelectiveVo(CommoditySku commoditySku);

    /**
     * 将对应视觉商品的品牌清空
     * @param commoditySku
     */
    void emptyBrand(CommoditySku commoditySku);

    /**
     * 根据分类ID将视觉商品大类信息清除
     * @param commoditySku
     */
    void emptyVrCommodityBigCategory(CommoditySku commoditySku);

    /**
     *
     * @param commoditySku
     */
    void emptyVrCommoditySmallCategory(CommoditySku commoditySku);

    /**
     * 根据商户查询视觉商品信息
     * @param map
     * @return
     */
    List<CommoditySku> selectByMerchantId(Map<String, String> map);

    /**
     * 根据品牌ID修改对应视觉商品信息
     *
     * @param commoditySku
     */
    void updateBrandInfo(CommoditySku commoditySku);


    /** codegen **/
}