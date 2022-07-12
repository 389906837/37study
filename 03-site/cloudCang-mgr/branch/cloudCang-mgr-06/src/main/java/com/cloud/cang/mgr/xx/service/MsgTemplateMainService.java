package com.cloud.cang.mgr.xx.service;

import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface MsgTemplateMainService extends GenericService<MsgTemplateMain, String> {

    /**
     * @description 模板主表表 服务
     * @author ChangTanchang
     * @time 2018-01-19 10:00:08
     * @fileName MsgTaskService.java
     * @version 1.0
     */

    /**
     * 模板主表表列表数据
     */
    Page<Map<String,String>> selectMsgTemplate(Page<Map<String,String>> page, MsgTemplateMainVo msgTemplateMainVo);

    /**
     *  根据ID删除模板主表
     */
    void delete(String [] checkboxId);

    /**
     * 查询列表用于左边导航
     * @param msgTemplateMainVo
     * @return
     */
    List<Map<String,String>> selectDataForLeft(MsgTemplateMainVo msgTemplateMainVo);

    List<MsgTemplateMain> selectAllMsgTemplateMain();

    MsgTemplateMain selectByExist(MsgTemplateMain msgTemplateMain);

    /**
     * 查询模板主表
     * @param msgTemplateMainVo
     * @return
     */
    List<MsgTemplateMain> selectGetMsgTemplateList(MsgTemplateMainVo msgTemplateMainVo);
}