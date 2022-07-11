package com.cloud.cang.generic;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;

/**
 * 所有自定义Service的顶级接口,封装常用的增删查改操作
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 */
public interface GenericService<Model, PK> {

	/**
     * 插入对象
     *
     * @param model 对象
     */
    int insert(Model model);

    /**
     * 更新对象
     *
     * @param model 对象
     */
    int updateByPrimaryKey(Model model);
    
    /**
     * 选择更新，对象属性不为空则更新
     * @param model 对象
     */
    int updateBySelective(Model model); 

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    int deleteByPrimaryKey(PK id);
    /**
     * 通过主键批量删除
     *
     * @param id 主键
     */
    int batchDeleteByIds(String[] ids);
    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    Model selectByPrimaryKey(PK id);
    
    /**
     * 
     * @param t where参数
     * @return
     */
    List<Model> selectByEntityWhere(Model t);
    /**
     * 通过map做为参数查询
     * @param t where参数
     * @return
     */
    List<Model> selectByMapWhere(Map<String,Object> t);
    /**
	 * 分页查询，内部调用 selectByEntityWhere 方法 
	 */
    Page<Model> queryPage(Page<Model> page, Model t);
    

}