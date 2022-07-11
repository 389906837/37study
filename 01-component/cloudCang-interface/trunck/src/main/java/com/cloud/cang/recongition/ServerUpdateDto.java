package com.cloud.cang.recongition;


import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 视觉服务更新DTO
 * @author zhouhong
 * @version 1.0
 */
public class ServerUpdateDto extends SuperDto {

    /**
     * 更新记录编号
     */
    private String updateRecodeCode;
    /**
     * 视觉服务地址 zip包
     */
    private String serverAddress;
    /**
     * 操作人
     */
    private String operMan;
    /**
     * 模型地址类型  服务器本地 10  远程地址 20
     */
    private Integer addressType;
    /**
     * 更新视觉服务集合
     */
    private List<String> gpuCodes;

    public String getUpdateRecodeCode() {
        return updateRecodeCode;
    }

    public void setUpdateRecodeCode(String updateRecodeCode) {
        this.updateRecodeCode = updateRecodeCode;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public List<String> getGpuCodes() {
        return gpuCodes;
    }

    public void setGpuCodes(List<String> gpuCodes) {
        this.gpuCodes = gpuCodes;
    }

    public String getOperMan() {
        return operMan;
    }

    public void setOperMan(String operMan) {
        this.operMan = operMan;
    }

    @Override
    public String toString() {
        return "ServerUpdateDto{" +
                "updateRecodeCode='" + updateRecodeCode + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", operMan='" + operMan + '\'' +
                ", addressType=" + addressType +
                ", gpuCodes=" + gpuCodes +
                '}';
    }
}
