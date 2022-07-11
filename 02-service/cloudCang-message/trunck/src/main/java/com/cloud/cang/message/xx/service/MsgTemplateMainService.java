package com.cloud.cang.message.xx.service;

import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.model.xx.MsgTemplate;

public interface MsgTemplateMainService {
 
	/**
     * 根据Code
     * @param code
     * @return
     */
    TemplateMain getTemplateByCode(String code);
    
    /**
     * 根据Templdate ID 查询 
     * @param templdateId
     * @return
     */
    MsgTemplate getMsgTemplateById(String templdateId);

    /**
     * 根据商户ID
     * @param merchantId
     * @return
     */
    TemplateMain getTemplateByMerchantId(String merchantId,String templateType);
 
}