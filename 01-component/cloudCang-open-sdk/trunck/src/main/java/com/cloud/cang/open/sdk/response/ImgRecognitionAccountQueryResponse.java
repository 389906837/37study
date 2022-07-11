package com.cloud.cang.open.sdk.response;

import com.cloud.cang.open.sdk.mapping.BaseField;

/**
 * 视觉识别账户查询响应
 */
public class ImgRecognitionAccountQueryResponse extends BaseResponse  {

    @BaseField("accountNo")
    private String accountNo;
    @BaseField("transferNum")
    private String transferNum;
    @BaseField("deductionNum")
    private String deductionNum;
    @BaseField("accountType")
    private String accountType;
    @BaseField("balanceNum")
    private String balanceNum;
    @BaseField("expireDate")
    private String expireDate;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(String transferNum) {
        this.transferNum = transferNum;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBalanceNum() {
        return balanceNum;
    }

    public void setBalanceNum(String balanceNum) {
        this.balanceNum = balanceNum;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getDeductionNum() {
        return deductionNum;
    }

    public void setDeductionNum(String deductionNum) {
        this.deductionNum = deductionNum;
    }

    @Override
    public String toString() {
        return "ImgRecognitionAccountQueryResponse{" +
                "accountNo='" + accountNo + '\'' +
                ", transferNum='" + transferNum + '\'' +
                ", deductionNum='" + deductionNum + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balanceNum='" + balanceNum + '\'' +
                ", expireDate='" + expireDate + '\'' +
                '}';
    }
}