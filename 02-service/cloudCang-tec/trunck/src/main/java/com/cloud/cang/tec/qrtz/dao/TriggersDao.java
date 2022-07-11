package com.cloud.cang.tec.qrtz.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.qrtz.Triggers;

import java.util.List;
import java.util.Map;



/** (QRTZ_TRIGGERS) **/
public interface TriggersDao extends GenericDao<Triggers, String> {
	/**
     * 通过map做为参数查询
     * @param t where参数
     * @return
     */
    List<com.cloud.cang.tec.quartz.vo.Triggers> selectVoByMapWhere(Map t);

}