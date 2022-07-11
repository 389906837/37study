package com.cloud.cang.act.ws.domain;

import java.util.List;

/**
 * Created by YLF on 2020/5/27.
 */
public class LicenseInfo {
    private String totalCount;
    private List <LicenseParamInfo> licenseParamLists;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<LicenseParamInfo> getLicenseParamLists() {
        return licenseParamLists;
    }

    public void setLicenseParamLists(List<LicenseParamInfo> licenseParamLists) {
        this.licenseParamLists = licenseParamLists;
    }

    @Override
    public String toString() {
        return "LicenseInfo{" +
                "totalCount='" + totalCount + '\'' +
                ", licenseParamLists=" + licenseParamLists +
                '}';
    }
}
