package com.cloud.cang.model;

import com.cloud.cang.common.SuperDto;
import com.qiqi.avatareye.bean.CellListBean;

/**
 * Created by Alex on 2018/4/2.
 */
public class QureyCellBeanModel extends SuperDto {

    private CellListBean cellListBean;

    public CellListBean getCellListBean() {
        return cellListBean;
    }

    public void setCellListBean(CellListBean cellListBean) {
        this.cellListBean = cellListBean;
    }
}
