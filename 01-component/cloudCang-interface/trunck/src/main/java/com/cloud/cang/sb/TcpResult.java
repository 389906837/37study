package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * Created by Alex on 2018/6/2.
 */
public class TcpResult extends SuperDto {

    private List<TcpVo> tcpVoList;

    public List<TcpVo> getTcpVoList() {
        return tcpVoList;
    }

    public void setTcpVoList(List<TcpVo> tcpVoList) {
        this.tcpVoList = tcpVoList;
    }
}
