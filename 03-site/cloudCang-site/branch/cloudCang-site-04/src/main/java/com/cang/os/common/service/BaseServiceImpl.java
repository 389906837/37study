/**
 * 
 */
package com.cang.os.common.service;

import java.util.List;

import com.cang.os.common.core.Page;
import com.cang.os.common.dao.BaseMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

/**
 * @author zhouhong
 *
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
	
	 /**
     * 定义成抽象方法,由子类实现,完成dao的注入
     *
     * @return GenericDao实现类
     */
    public abstract BaseMongoDao<T>
	getDao();

	@Override
	public T save(T entity) {
		return getDao().save(entity);
	}
	
	/**
	 * 插入
	 */
	public T insert(T entity){
		return getDao().insert(entity);
	}
	

	@Override
	public T findById(String id) {
		return getDao().findById(id);
	}

	@Override
	public T findById(String id, String collectionName) {
		return getDao().findById(id,collectionName);
	}

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}

	@Override
	public List<T> findAll(String collectionName) {
		return getDao().findAll(collectionName);
	}

	@Override
	public List<T> find(Query query) {
		return getDao().find(query);
	}

	@Override
	public T findOne(Query query) {
		return getDao().findOne(query);
	}

	@Override
	public Page<T> findPage(Page<T> page, Query query) {
		return getDao().findPage(page, query);
	}

	@Override
	public long count(Query query) {
		return getDao().count(query);
	}

	@Override
	public WriteResult update(Query query, Update update) {
		return getDao().update(query,update);
	}

	@Override
	public T updateOne(Query query, Update update) {
		return getDao().updateOne(query, update);
	}

	@Override
	public WriteResult update(T entity) {
		return getDao().update(entity);
	}

	@Override
	public void remove(Query query) {
		 getDao().remove(query);
		
	}
	
	@Override
	public void removeById(String id) {
		 getDao().removeById(id);
		
	}

}
