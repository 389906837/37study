package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;

import java.util.ArrayList;

/**
 * Created by Alex on 2018/4/2.
 */
public class OffChanelModel extends SuperDto {
    private ArrayList<String> cells;

    public ArrayList<String> getCells() {
        return cells;
    }

    public void setCells(ArrayList<String> cells) {
        this.cells = cells;
    }
}
