package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;
import com.qiqi.avatareye.bean.CellBean;

import java.util.ArrayList;

/**
 * Created by Alex on 2018/4/2.
 */
public class OpenChanelModel extends SuperDto {
    private ArrayList<CellBean> Cells;


    public ArrayList<CellBean> getCells() {
        return Cells;
    }

    public void setCells(ArrayList<CellBean> cells) {
        Cells = cells;
    }
}
