package com.cloud.cang.mgr.sp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.om.vo.OrderCommodityEditVo;
import com.cloud.cang.mgr.sp.domain.CommodityDomain;
import com.cloud.cang.mgr.sp.vo.CommodityVo;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommodityInfoService extends GenericService<CommodityInfo, String> {


    /**
     * @return com.github.pagehelper.Page<com.cloud.cang.mgr.sp.domain.CommodityDomain>
     * @Author: zhouhong
     * @Description: 分页查询商品信息
     * @param: page 分页参数
     * @param: commodityVo 查询参数
     * @Date: 2018/2/10 12:38
     */
    Page<CommodityDomain> selectPageByDomainWhere(Page<CommodityDomain> page, CommodityVo commodityVo);

    /**
     * @Description: 编辑订单选择商品
     * @param: page 分页参数
     * @param: commodityVo 查询参数
     */
    Page<CommodityDomain> selectOrderCommodity(Page<CommodityDomain> page, CommodityVo commodityVo);

    /**
     * 编号获取商品信息
     *
     * @param commodityCode 商品编号
     * @return
     */
    CommodityInfo selectByCode(String commodityCode);

    /**
     * 根据ID批量删除（逻辑删除）
     *
     * @param checkboxId
     * @param operater
     * @param updateStamp
     * @param merchantId
     */
    void batchDelByIds(String[] checkboxId, String operater, Date updateStamp, String merchantId);


    /**
     * 根据ID批量查询商品信息
     *
     * @param commodityArray
     * @return
     */
    List<CommodityInfo> selectByKeys(String[] commodityArray);

    /**
     * 改变商品状态
     * 如果当前状态下架改为上架，否则将上架改为下架
     *
     * @param checkboxId
     * @param operate    操作类型
     * @param user       操作类型
     * @param updateTime 操作类型
     * @param merchantId
     */
    ResponseVo<String> updateStatusByIds(String[] checkboxId, String operate, String user, Date updateTime, String merchantId);

    /**
     * 查询该视觉商品是否被添加
     *
     * @param merchantId 商户ID
     * @param skuId      视觉商品ID
     * @return
     */
    List<CommodityInfo> selectUnDelById(String merchantId, String skuId);

    /**
     * 修改商品信息
     *
     * @param commodityInfo
     * @param scommodityImgfile
     */
    ResponseVo<String> updateCommodityById(CommodityInfo commodityInfo, MultipartFile scommodityImgfile);

    /**
     * 更新商品活动参与记录
     *
     * @param activityConf 活动信息
     */
    void updateCommodityJoinNum(final ActivityConf activityConf);

    /**
     * 同步商品单价
     *
     * @param checkboxId
     */
    void syncComFsalePrice(String[] checkboxId);

    /**
     * @return java.util.List<com.cloud.cang.model.sp.CommodityInfo>
     * @Author: ChangTanchang
     * @Description: 获取商户所有有效商品
     * @param: smerchantId 商户Id
     * @Date: 2018/5/12 16:58
     */
    List<CommodityInfo> selectAllCommodityByMerchantId(String smerchantId);

    /**
     * 查询所有商品
     *
     * @return
     */
    List<CommodityInfo> queryAllCom();

    /**
     * 查询商户商品
     *
     * @param id       订单商品表id
     * @param deviceId 订单设备id
     * @return CommodityInfo
     */
    OrderCommodityEditVo selectExisCommodity(String id, String deviceId);

    /**
     * 根据查询条件获取导出商品信息
     *
     * @param commodityVo
     * @return
     */
    List<Map<String, Object>> selectCommodityInfoByExport(CommodityVo commodityVo);
}