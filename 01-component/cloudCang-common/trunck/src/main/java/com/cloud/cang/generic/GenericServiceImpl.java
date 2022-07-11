package com.cloud.cang.generic;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * <p/>
 * Model : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型
 *
 */
public abstract class GenericServiceImpl<Model, PK> implements GenericService<Model, PK> {

    /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract GenericDao<Model, PK> getDao();

    /**
     * 插入对象
     *
     * @param model 对象
     */
    public int insert(Model model) {
        return getDao().insert(model);
    }

    /**
     * 更新对象
     *
     * @param model 对象
     */
    public int update(Model model) {
        return getDao().updateByPrimaryKey(model);
    }

    /**
     * 通过主键, 删除对象
     *
     * @param id 主键
     */
    public int delete(PK id) {
        return getDao().deleteByPrimaryKey(id);
    }

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键
     * @return
     */
    public Model selectById(PK id) {
        return getDao().selectByPrimaryKey(id);
    }

	public int updateByPrimaryKey(Model model) {
		return getDao().updateByPrimaryKey(model);
	}
	
	/**
     * 选择更新，对象属性不为空则更新
     * @param model 对象
     */
	public int updateBySelective(Model model) {
	    return getDao().updateByIdSelective(model);
	}
	/**
     * 通过主键批量删除
     *
     * @param id 主键
     */
	public int batchDeleteByIds(String[] ids){
	    return this.getDao().batchDeleteByIds(ids);
    	
    }
	/**
	 * 根据字键删除
	 */
	public int deleteByPrimaryKey(PK id) {
	    return this.getDao().deleteByPrimaryKey(id);
	}

	/**
	 * 根据主键查询
	 */
	public Model selectByPrimaryKey(PK id) {
		return this.getDao().selectByPrimaryKey(id);
	}

	/**
	 * @param t 对应做为查询条件。
	 */
	public List<Model> selectByEntityWhere(Model t) {
		return this.getDao().selectByEntityWhere(t);
	}
	 /**
     * 通过map做为参数查询
     * @param t where参数
     * @return
     */
	public List<Model> selectByMapWhere(Map<String,Object> t){
    	return this.getDao().selectByMapWhere(t);
    }
	/**
     * 通过map做为参数查询
     * @param t where参数
     * @return
     */
	public List<Model> queryPage(Page<Model> page,Map<String,Object> t){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
    	return this.getDao().selectByMapWhere(t);
    }
	/**
	 * 分页查询，内部调用 selectByEntityWhere 方法 
	 */
	public Page<Model> queryPage(Page<Model> page,Model t) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<Model>)this.selectByEntityWhere(t);
	}


}