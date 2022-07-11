/**
 * 
 */
package com.cang.os.mgr.sys.service;

import com.cang.os.common.service.BaseService;
import com.cang.os.bean.sys.Operator;

/**
 * @author zhouhong
 *
 */
public interface OperatorService extends BaseService<Operator> {
	
	public void deleteOperator(String id);

}
