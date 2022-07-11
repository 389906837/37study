package com.cloud.cang.open.api.cr.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.open.api.cr.dao.ServerListDao;
import com.cloud.cang.open.api.cr.service.ServerListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerListServiceImpl extends GenericServiceImpl<ServerList, String> implements
        ServerListService {

    @Autowired
    ServerListDao serverListDao;
    private static final Logger logger = LoggerFactory.getLogger(ServerListServiceImpl.class);

    @Override
    public GenericDao<ServerList, String> getDao() {
        return serverListDao;
    }
}