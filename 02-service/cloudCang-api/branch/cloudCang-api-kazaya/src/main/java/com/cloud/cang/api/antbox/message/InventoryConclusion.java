package com.cloud.cang.api.antbox.message;

import com.cloud.cang.api.antbox.BoxContext;
import com.cloud.cang.api.antbox.constant.InventoryType;
import com.cloud.cang.api.antbox.dto.CustomerDto;

import java.util.Set;

public class InventoryConclusion {

    // 当前购物用户
    private CustomerDto currentCustomer;
    // 最后购物用户
    private CustomerDto lastCustomer;
    // 预期盘点时间
    private int expectedInventoryTimes;
    // 实际盘点时间
    private int actualInventoryTimes;
    // 同一次盘点的轮次开始时间
    private long elapsedMills;
    // 是否为测试盘点
    private boolean isTesting;
    // 盘点类型
    private InventoryType inventoryType;
    // rfid 差集
    private Set<String> lossLabels = null;
    // rfid 差集
    private Set<String> newLabels = null;
    // rfid 所有
    private Set<String> currentInventoryLabels = null;

    // 当前售货机 上下文
    private BoxContext boxContext;

    public CustomerDto getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(CustomerDto currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public int getExpectedInventoryTimes() {
        return expectedInventoryTimes;
    }

    public void setExpectedInventoryTimes(int expectedInventoryTimes) {
        this.expectedInventoryTimes = expectedInventoryTimes;
    }

    public int getActualInventoryTimes() {
        return actualInventoryTimes;
    }

    public void setActualInventoryTimes(int actualInventoryTimes) {
        this.actualInventoryTimes = actualInventoryTimes;
    }

    public long getElapsedMills() {
        return elapsedMills;
    }

    public void setElapsedMills(long elapsedMills) {
        this.elapsedMills = elapsedMills;
    }

    public Set<String> getLossLabels() {
        return lossLabels;
    }

    public void setLossLabels(Set<String> lossLabels) {
        this.lossLabels = lossLabels;
    }

    public Set<String> getNewLabels() {
        return newLabels;
    }

    public void setNewLabels(Set<String> newLabels) {
        this.newLabels = newLabels;
    }

    public Set<String> getCurrentInventoryLabels() {
        return currentInventoryLabels;
    }

    public void setCurrentInventoryLabels(Set<String> currentInventoryLabels) {
        this.currentInventoryLabels = currentInventoryLabels;
    }

    public boolean isTesting() {
        return isTesting;
    }

    public void setTesting(boolean isTesting) {
        this.isTesting = isTesting;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public CustomerDto getLastCustomer() {
        return lastCustomer;
    }

    public void setLastCustomer(CustomerDto lastCustomer) {
        this.lastCustomer = lastCustomer;
    }

    public BoxContext getBoxContext() {
        return boxContext;
    }

    public void setBoxContext(BoxContext boxContext) {
        this.boxContext = boxContext;
    }
}
