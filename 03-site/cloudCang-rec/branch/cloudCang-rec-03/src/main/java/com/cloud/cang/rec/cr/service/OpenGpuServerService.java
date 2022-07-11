package com.cloud.cang.rec.cr.service;

import com.cloud.cang.model.cr.OpenGpuServer;
import com.cloud.cang.generic.GenericService;

public interface OpenGpuServerService extends GenericService<OpenGpuServer, String> {

    /**
     * 根据OpenServerId删除
     *
     * @param id
     */
    void deleteByOpenServerId(String id);
}