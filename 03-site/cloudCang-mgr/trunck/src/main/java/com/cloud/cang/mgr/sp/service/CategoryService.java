package com.cloud.cang.mgr.sp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sp.domain.CategoryDomain;
import com.cloud.cang.mgr.sp.vo.CategoryVo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface CategoryService extends GenericService<Category, String> {

    /**
     * 获取分类
     * @param smerchantId 商户Id
     * @return
     */
    List<SelectVo> selectByMerchantId(String smerchantId);

    /**
     * 分页查询商品分类信息
     * @param page
     * @param categoryVo
     * @return
     */
    Page<CategoryDomain> selectPageByDomainWhere(Page<CategoryDomain> page, CategoryVo categoryVo);


    /**
     * 逻辑删除
     * @param checkboxId
     * @param merchantId
     */
    void batchLogicDelByIds(String[] checkboxId, String merchantId);


    /**
     * 根据商户ID查询商品父类集合
     * @param merchantId 商户ID
     * @return 父类集合
     */
    List<Category> selectBigCategoryByMerchantId(String merchantId);

    /**
     * 根据商户ID查询商品子类集合
     * @param merchantId 商户ID
     * @return 父类集合
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
    int updateBySelectiveVo(Category category);

    /**
     * 查询所有商品分类信息
     * @return
     */
    List<Category> selectAllValidBigCategory(String merchantId);

    /**
     * 查询大类信息
     * @return
     */
    List<Category> queryBigCategor();

    /**
     * 查询商品分类表
     * @return
     */
    List<Category> queryData(String name);

    /**
     * 根据数据字典配置的主类查询大类
     * @param svalue
     * @param merchantId
     * @return
     */
    List<Category> selectBigCategoryByCategoryCode(String svalue,String merchantId);


    /**
     * 根据商品大类查询小类
     * @param id
     * @param merchantId
     * @return
     */
    List<Category> selectSmallCategoryByBigCategory(String id, String merchantId);

    /**
     * 初始化数据
     *
     * @return
     */
    ResponseVo<String> initCategory();

}