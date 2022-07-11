package com.cloud.cang.pay;

/**
 * Created by YLF on 2019/7/23.
 */
public class FeeDto {
    //必填
    private String fee_name;//不超过20个字符，超出报错处理，各付费项目名称不能重复
    //非必填 【金额】、【计费说明】二者必填其一，也可以同时填写
    private Integer fee_count;//数量，付费项目数量，大于等于1且小于等于100，不填默认为1
    private Integer fee_amount;//金额，大于等于0，单位为分，等于0时代表不需要扣费
    private String fee_desc;//计费说明，不超过30个字符，超出报错处理


    @Override
    public String toString() {
        return "FeeDto{" +
                "fee_name='" + fee_name + '\'' +
                ", fee_count='" + fee_count + '\'' +
                ", fee_amount=" + fee_amount +
                ", fee_desc=" + fee_desc +
                '}';
    }

    public String getFee_name() {
        return fee_name;
    }

    public void setFee_name(String fee_name) {
        this.fee_name = fee_name;
    }

    public Integer getFee_count() {
        return fee_count;
    }

    public void setFee_count(Integer fee_count) {
        this.fee_count = fee_count;
    }

    public Integer getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(Integer fee_amount) {
        this.fee_amount = fee_amount;
    }

    public String getFee_desc() {
        return fee_desc;
    }

    public void setFee_desc(String fee_desc) {
        this.fee_desc = fee_desc;
    }
}
