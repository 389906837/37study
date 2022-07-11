package com.cloud.cang.pay;

/**
 * Created by YLF on 2019/7/23.
 */
public class DiscountDto {
    //必填

    private String discount_name;//优惠名称,不超过20个字符，超出报错处理，各优惠项目名称不能重复
    private Integer discount_amount;//大于等于0的数字，单位为分
    //非必填
    private String discount_desc;//优惠说明,不超过30个字符，超出报错处理在填写【优惠名称】的情况下，【优惠说明】也必须填写

    public Integer getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(Integer discount_amount) {
        this.discount_amount = discount_amount;
    }

    @Override
    public String toString() {
        return "DiscountDto{" +
                "discount_name='" + discount_name + '\'' +
                ", discount_desc='" + discount_desc + '\'' +
                '}';
    }

    public String getDiscount_name() {
        return discount_name;
    }

    public void setDiscount_name(String discount_name) {
        this.discount_name = discount_name;
    }

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }
}
