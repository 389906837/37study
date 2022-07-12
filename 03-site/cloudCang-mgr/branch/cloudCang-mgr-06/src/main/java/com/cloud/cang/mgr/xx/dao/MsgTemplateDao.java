package com.cloud.cang.mgr.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.mgr.xx.vo.MsgTemplateVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 消息/协议  模板从表(XX_MSG_TEMPLATE) **/
public interface MsgTemplateDao extends GenericDao<MsgTemplate, String> {

    /**
     * 分页查询所有
     * @param msgTemplateVo
     * @return
     */
    Page<MsgTemplate> selectMsgTemplate(MsgTemplateVo msgTemplateVo);

    /**
     * 根据主表ID查询模板从表
     * @return
     */
    List<MsgTemplate> selectBySmainId(String smainId);

    List<MsgTemplate> selectBySsupplierId(String sid);

    Page<MsgTemplateDomain> selectByMainId(Map<String,Object> map);
}