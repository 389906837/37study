package com.cloud.cang.mgr.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.xx.vo.MsgTemplateMainVo;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 模板主表表(XX_MSG_TEMPLATE_MAIN) **/
public interface MsgTemplateMainDao extends GenericDao<MsgTemplateMain, String> {

    Page<Map<String,String>> selectMsgTemplate(MsgTemplateMainVo msgTemplateMainVo);

    List<Map<String,String>> selectDataForLeft(MsgTemplateMainVo msgTemplateMainVo);

    MsgTemplateMain selectByExist(MsgTemplateMain msgTemplateMain);

    List<MsgTemplateMain> selectAllMsgTemplateMain();

    List<MsgTemplateMain> selectGetMsgTemplateList(MsgTemplateMainVo msgTemplateMainVo);
}