package com.antbox.rfidmachine.dto;

import com.antbox.domain.RfidProduct;

import java.util.List;

/**
 * Created by DK on 17/5/10.
 */
public class RfidProductDto extends RfidProduct {

    private List<String> rfidList;

    private String productName;

    private String batchNo;

    public List<String> getRfidList() {
        return rfidList;
    }

    public void setRfidList(List<String> rfidList) {
        this.rfidList = rfidList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
