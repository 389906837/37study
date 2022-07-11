package com.cloud.cang.vis.cr.service;

import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.generic.GenericService;

public interface RecognitionServerService extends GenericService<RecognitionServer, String> {
 
    int getRunningServerNum(String modelCode);
}