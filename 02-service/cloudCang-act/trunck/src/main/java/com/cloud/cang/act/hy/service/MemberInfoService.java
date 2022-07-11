package com.cloud.cang.act.hy.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.hy.MemberInfo;

import java.util.Date;

public interface MemberInfoService extends GenericService<MemberInfo, String> {

    public Date selectRegisterDate(String id);
}