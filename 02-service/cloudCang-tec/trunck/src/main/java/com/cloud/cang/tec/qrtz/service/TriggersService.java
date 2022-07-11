package com.cloud.cang.tec.qrtz.service;



import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.qrtz.Triggers;

import java.util.List;
import java.util.Map;



public interface TriggersService extends GenericService<Triggers, String> {
 
	List<com.cloud.cang.tec.quartz.vo.Triggers> selectVoByMapWhere(Map t);
}