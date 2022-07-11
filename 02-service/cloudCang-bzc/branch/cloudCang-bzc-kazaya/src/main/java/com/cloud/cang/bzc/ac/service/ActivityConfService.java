package com.cloud.cang.bzc.ac.service;

import com.cloud.cang.bzc.om.vo.ActivityVo;
import com.cloud.cang.model.ac.ActivityConf;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface ActivityConfService extends GenericService<ActivityConf, String> {


    List<ActivityVo> selectAllInfo(String smerchantCode);



}