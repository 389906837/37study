package com.cloud.cang.mgr.wz.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.wz.domain.RegionDomain;
import com.cloud.cang.mgr.wz.vo.RegionVo;
import com.cloud.cang.model.wz.Region;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 运营区域表(MYSQL)(WZ_REGION)
 **/
public interface RegionDao extends GenericDao<Region, String> {

    Page<Region> selectRegionAll(RegionVo regionVo);

    List<Region> selectGetRerionList(RegionVo regionVo);

    List<Region> selectGetRerionList1(RegionVo regionVo);

    void deleteRegionById(String aid);

    /**
     * 查询运营区域 根据编号
     *
     * @param sregionCode 区域编号
     * @return
     */
    Region selectByCode(String sregionCode);

}