package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.hy.MbrRole;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface MbrPurviewService extends GenericService<MbrPurview, String> {

    /**
     * 会员权限码查询接口
     * @param page
     * @param mbrPurviewVo
     * @return
     */
    Page<MbrPurview> selectPageAll(Page<MbrPurview> page, MbrPurviewVo mbrPurviewVo);

    /**
     * 所有权限,根据角色标记
     * @param mbrPurviewVo
     * @return
     */
    List<Map<String,String>> selectAllByRole(MbrPurviewVo mbrPurviewVo);

    /**
     * 判断权限码不能重复
     * @param mbrPurview
     * @return
     */
    MbrPurview selectByExist(MbrPurview mbrPurview);

    /**
     * 根据ID删除权限码
     * @param checkboxId
     */
    void delete(String [] checkboxId);

    /**
     * 会员权限分配
     * @param mbrRole
     * @return
     */
    List<MbrPurviewVo> selectMbrRole(MbrRole mbrRole);
}