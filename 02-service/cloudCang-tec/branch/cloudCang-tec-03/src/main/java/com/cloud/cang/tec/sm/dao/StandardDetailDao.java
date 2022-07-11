package com.cloud.cang.tec.sm.dao;

import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sm.StandardDetail;
import com.cloud.cang.tec.sm.vo.StandardDetailVo;

/** 设备标准库存配置明细表(SM_STANDARD_DETAIL) **/
public interface StandardDetailDao extends GenericDao<StandardDetail, String> {


	List<StandardDetailVo> selectStockWarningByMerchant(Map map);
}