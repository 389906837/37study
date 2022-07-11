package com.cloud.cang.api.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.TrashAuthorise;

import java.util.Map;

/** 智能垃圾箱授权表(HY_TRASH_AUTHORISE) **/
public interface TrashAuthoriseDao extends GenericDao<TrashAuthorise, String> {


    TrashAuthorise selectByUserIdAndTrash(Map<String, Object> map);
}