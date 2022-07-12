package com.cloud.cang.mgr.vr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.vr.domain.CommoditySkuDomain;
import com.cloud.cang.mgr.vr.vo.CommoditySkuVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.vr.CommoditySku;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CommoditySkuService extends GenericService<CommoditySku, String> {

    /**
     * @Author: zengzexiong
     * @Description:  分页查询商品SKU信息
     * @param: page 分页参数
     * @param: commodityVo 查询参数
     * @Date: 2018年3月7日21:06:39
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.vr.domain.CommoditySkuDomain>
     */
    Page<CommoditySkuDomain> selectPageByDomainWhere(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo);

    /**
     * 根据ID批量删除（逻辑删除）
     * @param checkboxId
     */
    void batchDelByIds(String[] checkboxId);


    /**
     * 根据商户ID查询商户下的SKU商品
     * @param page
     * @param commoditySkuVo
     * @return
     */
    Page<CommoditySkuDomain> selectPageByMerchantId(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo);


    /**
     * 修改视觉商品信息
     * 如果修改状态中包含下架，则商品表中对应的状态也应该改变
     *
     * @param commoditySku
     * @param scommodityImgfile
     * @return
     */
    ResponseVo<String> updateVrCommodity(CommoditySku commoditySku, MultipartFile scommodityImgfile);


    /**
     * 新增视觉商品信息
     *
     * @param commoditySku
     * @return
     */
    ResponseVo<String> insertCommoditySku(CommoditySku commoditySku, MultipartFile scommodityImgfile);

    /**
     * 查询sku类型为全部的商户下的SKU商品
     * @param page
     * @param commoditySkuVo
     * @return
     */
    Page<CommoditySkuDomain> selectPageByValidStatus(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo);


    /**
     * 修改设备状态
     * @param checkboxId
     * @param status
     * @return
     */
    ResponseVo<String> updateCommodityStatus(String[] checkboxId, String status);

    /**
     * 修改视觉商品上线状态
     * @param checkboxId
     * @param onlineStatus
     * @return
     */
    ResponseVo<String> updateVrOnlineStatus(String[] checkboxId, String onlineStatus);


    /**
     * 同步视觉商品信息到商品列表
     *
     * @param checkboxId
     * @param user
     * @return
     */
    ResponseVo<String> synchVrToCommodityInfo(String[] checkboxId, String user);

    /**
     * 批量插入视觉商品信息
     *
     * @param file Excel文件（支持 2003，2007）
     * @return
     */
    ResponseVo<String> batchUploadVrComInfo(MultipartFile file) throws Exception;

}