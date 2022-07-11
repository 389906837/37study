package com.cloud.cang.tec.om.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by yan on 2018/6/5.
 */
public class AlipayReconciliationsVo {
    private List<OfBillAlipayVo> ofBillAlipayVoList;
    private Map<String, OfBillAlipayVo> ofBillAlipayVoMap;

    public Map<String, OfBillAlipayVo> getOfBillAlipayVoMap() {
        return ofBillAlipayVoMap;
    }

    public void setOfBillAlipayVoMap(Map<String, OfBillAlipayVo> ofBillAlipayVoMap) {
        this.ofBillAlipayVoMap = ofBillAlipayVoMap;
    }

    public List<OfBillAlipayVo> getOfBillAlipayVoList() {
        return ofBillAlipayVoList;
    }

    public void setOfBillAlipayVoList(List<OfBillAlipayVo> ofBillAlipayVoList) {
        this.ofBillAlipayVoList = ofBillAlipayVoList;
    }
}
