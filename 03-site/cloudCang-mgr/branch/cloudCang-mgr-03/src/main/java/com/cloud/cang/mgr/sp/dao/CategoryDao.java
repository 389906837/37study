package com.cloud.cang.mgr.sp.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sp.domain.CategoryDomain;
import com.cloud.cang.mgr.sp.vo.CategoryVo;
import com.cloud.cang.model.sp.Category;
import com.github.pagehelper.Page;

/** 商品分类表(SP_CATEGORY) **/
public interface CategoryDao extends GenericDao<Category, String> {
    /**
     * 获取分类
     * @param smerchantId 商户Id
     * @return
     */
    List<SelectVo> selectByMerchantId(String smerchantId);

    /**
     * 分页查询商品分类信息
     * @param categoryVo
     * @return
     */
    Page<CategoryDomain> selectByDomainWhere(CategoryVo categoryVo);

    /**
     * 根据商户ID查询商品父类集合
     * @param merchantId
     * @return
     */
    List<Category> selectBigCategoryByMerchantId(String merchantId);

    /**
     * 根据商户ID查询商品子类集合
     * @param merchantId
     * @return
     */
    List<Category> selectSmallCategoryByMerchantId(String merchantId);

    /**
     * 根据大类ID查询小类
     * @param pid
     * @return
     */
    List<Category> selectSmallCategoryByPid(String pid);

    /**
     * 自定义编辑方法
     * @param category
     * @return
     */
    int updateByIdSelectiveVo(Category category);

    /**
     * 查询商品大类信息
     *
     * @return
     */
    List<Category> queryBigCategor();

    /**
     * 查询商品分类表
     * @return
     */
    List<Category> queryData(String name);

    /**
     * 根据商品大类查询商品小类信息
     * @param map
     * @return
     */
    List<Category> selectSmallCategoryByBigCategory(Map map);

    /**
     * 根据商品主分类查询商品大类信息
     * @param map
     * @return
     */
    List<Category> selectBigCategoryByCategoryCode(Map map);

    /**
     * 根据商户ID查询所有有效
     * @param category
     * @return
     */
    List<Category> selectValidBigCategory(Category category);

    /**
     * 查询所有有效小类，
     * 不根据商户ID查询
     *
     * @return
     */
    List<Category> selectAllValidSmallCategory();

    /**
     * 查询所有有效的大类信息（不区分商户）
     *
     * @return
     */
    List<Category> selectAllValidBigCategory();


}