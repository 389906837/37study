package com.cloud.cang.tec.om.vo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yan on 2018/6/5.
 */
public class WechantReconciliationsVo {
    private Map<String, OfBillWechantVo> ofBillVoMap;
    private List<OfBillWechantVo> ofBillWechantVoList;
    private Integer ofOrderNums;//订单总数
    private Double ofTotalMoney;//总订单额

    @Override
    public String toString() {
        return "WechantReconciliationsVo{" +
                "ofBillVoMap=" + ofBillVoMap +
                ", ofOrderNums=" + ofOrderNums +
                ", ofTotalMoney=" + ofTotalMoney +
                '}';
    }


    public Map<String, OfBillWechantVo> getOfBillVoMap() {
        return ofBillVoMap;
    }

    public void setOfBillVoMap(Map<String, OfBillWechantVo> ofBillVoMap) {
        this.ofBillVoMap = ofBillVoMap;
    }

    public List<OfBillWechantVo> getOfBillWechantVoList() {
        return ofBillWechantVoList;
    }

    public void setOfBillWechantVoList(List<OfBillWechantVo> ofBillWechantVoList) {
        this.ofBillWechantVoList = ofBillWechantVoList;
    }

    public void setOfTotalMoney(Double ofTotalMoney) {
        this.ofTotalMoney = ofTotalMoney;
    }


    public Double getOfTotalMoney() {
        return ofTotalMoney;
    }

    public Integer getOfOrderNums() {
        return ofOrderNums;
    }

    public void setOfOrderNums(Integer ofOrderNums) {
        this.ofOrderNums = ofOrderNums;
    }
}
