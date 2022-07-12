package com.cloud.cang.mgr.wz.service;

import com.cloud.cang.mgr.wz.domain.RegionDomain;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.wz.Region;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface RegionService extends GenericService<Region, String> {

    /**
     * 运营区域管理查询
     * @param page
     * @param regionVo
     * @return
     */
    Page<Region> selectRegionAll(Page<Region> page, RegionVo regionVo);

    /**
     * 根据ID删除运营数据
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 查询运营数据列表
     * @param regionVo
     * @return
     */
    List<Region> selectGetRerionList(RegionVo regionVo);



    /**
     * 查询运营数据列表
     * @param regionVo
     * @return
     */
    List<Region> selectGetRerionList1(RegionVo regionVo);

    /**
     * 查询运营区域 根据编号
     * @param sregionCode 区域编号
     * @return
     */
    Region selectByCode(String sregionCode);

}