package com.cloud.cang.api.hy.service;

import com.cloud.cang.model.hy.TrashAuthorise;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface TrashAuthoriseService extends GenericService<TrashAuthorise, String> {

    TrashAuthorise selectByUserIdAndTrash(Map<String, Object> map);
}