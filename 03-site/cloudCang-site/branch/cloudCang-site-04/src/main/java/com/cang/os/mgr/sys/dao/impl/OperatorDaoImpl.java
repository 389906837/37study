/**
 * 
 */
package com.cang.os.mgr.sys.dao.impl;

import com.cang.os.common.dao.BaseMongoDaoImpl;
import com.cang.os.mgr.sys.dao.OperatorDao;
import org.springframework.stereotype.Repository;

import com.cang.os.bean.sys.Operator;

/**
 * @author zhouhong
 *
 */
 @Repository
public class OperatorDaoImpl extends BaseMongoDaoImpl<Operator> implements OperatorDao {

}
