package com.cloud.cang.mgr.sp.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.vo.OrderCommodityEditVo;
import com.cloud.cang.mgr.sp.domain.CommodityDomain;
import com.cloud.cang.mgr.sp.vo.CommodityVo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * 商品信息表(SP_COMMODITY_INFO)
 **/
public interface CommodityInfoDao extends GenericDao<CommodityInfo, String> {
    /**
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.sp.domain.CommodityDomain>
     * @Author: zhouhong
     * @Description: 分页查询商品信息
     * @param: commodityVo 查询参数
     * @Date: 2018/2/10 12:38
     */
    Page<CommodityDomain> selectPageByDomainWhere(CommodityVo commodityVo);

    /**
     * @Description: 编辑订单选择商品
     * @param: page 分页参数
     * @param: commodityVo 查询参数
     * @param: deviceId 设备ID
     */
    Page<CommodityDomain> selectOrderCommodity(CommodityVo commodityVo);

    /**
     * 编号获取商品信息
     *
     * @param commodityCode 商品编号
     * @return
     */
    CommodityInfo selectByCode(String commodityCode);

    /**
     * 查询该视觉商品是否被添加
     *
     * @param commodityInfo 视觉商品Bean
     * @return
     */
    List<CommodityInfo> selectUnDelById(CommodityInfo commodityInfo);

    /**
     * 视觉商品状态修改为停用或者已下架时
     * 修改商品状态为失效
     *
     * @param id 视觉商品ID
     */
    void updateStatusToInvalid(String id);


    /**
     * 视觉商品状态修改为在售并且已上架时
     * 修改商品状态为正常
     *
     * @param commodityId
     */
    void updateStatusTovalid(String commodityId);

    /**
     * 自定义修改方法
     *
     * @param t
     * @return
     */
    int updateByIdSelectiveVo(CommodityInfo t);

    /**
     * 更新商品 参与活动记录次数
     *
     * @param smerchantId 商户ID
     */
    void updateJoinNumByMerchantId(String smerchantId);

    /**
     * 更新商品 参与活动记录次数
     *
     * @param updateParams 参数集合
     */
    void updateJoinNumByCommodityIds(Map<String, String> updateParams);

    /**
     * 获取商户所有有效商品
     *
     * @param smerchantId
     * @return
     */
    List<CommodityInfo> selectAllCommodityByMerchantId(String smerchantId);

    /**
     * 根据视觉商品修改结果修改对应商品表中对应商品
     *
     * @param commodityInfo
     */
    void updateByVrIdSelectiveVo(CommodityInfo commodityInfo);

    List<CommodityInfo> queryAllCom();

    /**
     * 根据商品标签ID清空该商品的标签
     *
     * @param slabelId
     */
    void emptyCommodityLabel(String slabelId);

    /**
     * 根据商品品牌ID清空该商品的品牌信息
     *
     * @param commodityInfo
     */
    void emptyBrand(CommodityInfo commodityInfo);

    /**
     * 根据分类ID清空对应商品表大类信息
     *
     * @param commodityInfo
     */
    void emptyCommodityBigCategory(CommodityInfo commodityInfo);


    /**
     * 根据分类ID清空对应商品表小类信息
     *
     * @param commodityInfo
     */
    void emptyCommoditySmallCategory(CommodityInfo commodityInfo);

    /**
     * 根据视觉商品ID逻辑删除
     *
     * @param id
     */
    void logicDeleteVrCommodityByVrId(String id);

    /**
     * 查询商户商品
     *
     * @param map
     * @return CommodityInfo
     */
    OrderCommodityEditVo selectExisCommodity(Map map);

    /**
     * 根据查询条件获取导出商品信息
     * @param commodityVo
     * @return
     */
    List<Map<String,Object>> selectCommodityInfoByExport(CommodityVo commodityVo);

    /**
     * 根据品牌ID修改对应商品信息
     *
     * @param commodityInfo
     */
    void updateBrandInfo(CommodityInfo commodityInfo);

}