package com.cloud.cang.mgr.wz.domain;

import com.cloud.cang.model.wz.Region;

/**
 * @description 运营区域管理VO
 * @author ChangTanchang
 * @time 2018-03-29 17:16:15
 * @fileName RegionDomain.java
 * @version 1.0
 */
public class RegionDomain extends Region {

    // 编号和名称
    private String codeAndname;

    public String getCodeAndname() {
        return codeAndname;
    }

    public void setCodeAndname(String codeAndname) {
        this.codeAndname = codeAndname;
    }

    @Override
    public String toString() {
        return "RegionDomain{" +
                "codeAndname='" + codeAndname + '\'' +
                '}';
    }
}
