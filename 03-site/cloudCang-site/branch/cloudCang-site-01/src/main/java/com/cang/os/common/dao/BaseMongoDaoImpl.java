package com.cang.os.common.dao;
import java.lang.reflect.Field;
import java.util.List;

import com.cang.os.common.core.Page;
import com.cang.os.common.core.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;


public abstract class BaseMongoDaoImpl<T> implements BaseMongoDao<T>{
    /**
     * spring mongodb　集成操作类　
     */
	@Autowired
	@Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    /**
     * 注入mongodbTemplate
     * 
     * @param mongoTemplate
     */
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public T save(T entity) {
        mongoTemplate.save(entity);
        return entity;
    }
    
    /**
	 * 插入
	 */
	public T insert(T entity){
		mongoTemplate.insert(entity);
		return entity;
	}

    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    public List<T> findAll() {
        return mongoTemplate.findAll(this.getEntityClass());
    }

    public List<T> findAll(String collectionName) {
        return mongoTemplate.findAll(this.getEntityClass(), collectionName);
    }

    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    public Page<T> findPage(Page<T> page, Query query) {
        //如果没有条件 则所有全部
        query=query==null?new Query(Criteria.where("_id").exists(true)):query;
        long count = this.count(query);
        // 总数
        page.setTotal(count);
        int currentPage = page.getPageNum();
        int pageSize = page.getPageSize();
        query.skip((currentPage - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setResults(rows);
        return page;
    }

    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    public WriteResult update(Query query, Update update) {
        if (update==null) {
            return null;
        }
        return mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    public T updateOne(Query query, Update update) {
        if (update==null) {
            return null;
        }
        return mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    public WriteResult update(T entity) {
        Field[] fields = this.getEntityClass().getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            return null;
        }
        Field idField = null;
        // 查找ID的field
        for (Field field : fields) {
            if (field.getName() != null
                    && "id".equals(field.getName().toLowerCase())) {
                idField = field;
                break;
            }
        }
        if (idField == null) {
            return null;
        }
        idField.setAccessible(true);
        String id=null;
        try {
            id = (String) idField.get(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (id == null || "".equals(id.trim()))
            return null;
        // 根据ID更新
        Query query = new Query(Criteria.where("_id").is(id));
        // 更新
        // Update update = new Update();
        // for (Field field : fields) {
        // // 不为空 不是主键 不是序列化号
        // if (field != null
        // && field != idField
        // && !"serialversionuid"
        // .equals(field.getName().toLowerCase())) {
        // field.setAccessible(true);
        // Object obj = field.get(entity);
        // if (obj == null)
        // continue;
        // update.set(field.getName(), obj);
        // }
        // }
        Update update = ReflectionUtils.getUpdateObj(entity);
        if (update == null) {
            return null;
        }
        return mongoTemplate.updateFirst(query, update, getEntityClass());
    }

    public void remove(Query query) {
        mongoTemplate.remove(query, this.getEntityClass());
    }
    
    /**
	 * 根据ID删除
	 * @param id
	 */
	public void removeById(String id) {
		 Query query = new Query(Criteria.where("id").is(id));
		 mongoTemplate.remove(query, this.getEntityClass());
	}
    /**
     * 获得泛型类
     */
    private Class<T> getEntityClass() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }

}