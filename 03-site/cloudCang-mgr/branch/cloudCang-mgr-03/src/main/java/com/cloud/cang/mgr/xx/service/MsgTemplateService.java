package com.cloud.cang.mgr.xx.service;

import com.cloud.cang.mgr.xx.domain.MsgTemplateDomain;
import com.cloud.cang.mgr.xx.vo.MsgTemplateVo;
import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface MsgTemplateService extends GenericService<MsgTemplate, String> {

    /**
     * 消息模板管理接口
     * 消息/协议模板从表列表数据
     * @param page
     * @param msgTemplateVo
     * @return
     */
    Page<MsgTemplate> selectMsgTemplate(Page<MsgTemplate> page, MsgTemplateVo msgTemplateVo);

    void delete(String[] checkboxId);

    List<MsgTemplate> selectBySsupplierId(String sid);

    Page<MsgTemplateDomain> selectByMainId(Page<MsgTemplateDomain> page, Map<String, Object> map);
}