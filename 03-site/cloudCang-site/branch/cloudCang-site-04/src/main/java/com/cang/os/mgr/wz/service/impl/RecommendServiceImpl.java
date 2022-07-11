package com.cang.os.mgr.wz.service.impl;

import com.cang.os.bean.wz.Recommend;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.wz.dao.RecommendDao;
import com.cang.os.mgr.wz.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by YLF on 2019/8/26.
 */
@Service
public class RecommendServiceImpl extends BaseServiceImpl<Recommend> implements
        RecommendService {

    @Autowired
    RecommendDao recommendDao;

    @Override
    public BaseMongoDao<Recommend> getDao() {
        return recommendDao;
    }
}

