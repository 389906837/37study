package com.cloud.cang.message.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.message.xx.domain.TemplateDomain;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.model.xx.MsgTemplateMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 模板主表表(XX_MSG_TEMPLATE_MAIN)
 **/
public interface MsgTemplateMainDao extends GenericDao<MsgTemplateMain, String> {

    /**
     * 查询所有的模板
     * @return
     */
    List<TemplateDomain> selectAllTemplate();

    /**
     * 根据商户Code
     * @param map
     * @return
     */
    MsgTemplateMain selectByMerchantId(Map<String, Object> map);
}