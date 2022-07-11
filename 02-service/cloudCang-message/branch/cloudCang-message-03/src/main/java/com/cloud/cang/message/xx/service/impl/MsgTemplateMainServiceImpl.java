package com.cloud.cang.message.xx.service.impl;

import com.cloud.cang.model.xx.MsgTemplate;
import com.cloud.cang.message.xx.domain.TemplateDomain;
import com.cloud.cang.message.xx.service.MsgTemplateMainService;
import com.cloud.cang.message.xx.dao.MsgTemplateMainDao;
import com.cloud.cang.message.xx.domain.TemplateMain;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.model.xx.MsgTemplateMain;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 模板服务
 *
 * @author zhouhong
 * @version 1.0
 */
@Service
public class MsgTemplateMainServiceImpl extends AbsConfigurationHandler implements
        MsgTemplateMainService {

    private static Logger LOGGER = LoggerFactory
            .getLogger(MsgTemplateMainServiceImpl.class);

    private final String ZKMSGTEMPLATEKEY = "cache_message_template";

    private ConcurrentHashMap<String, TemplateMain> cacheTemplate = new ConcurrentHashMap<String, TemplateMain>();
    //所有模板
    private ConcurrentHashMap<String, MsgTemplate> allTemplates = new ConcurrentHashMap<String, MsgTemplate>();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    @PostConstruct
    public void loadTemplate() {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.writeLock().lock();
                    cacheTemplate.clear();
                    allTemplates.clear();
                    List<TemplateDomain> list = msgTemplateMainDao.selectAllTemplate();
                    for (TemplateDomain templateDomain : list) {
                        TemplateMain templateMain = cacheTemplate.get(templateDomain.getMainCode());
                        if (templateMain == null) {
                            templateMain = new TemplateMain();
                            templateMain.setId(templateDomain.getMainId());
                            templateMain.setScode(templateDomain.getMainCode());
                            templateMain.setSmsgName(templateDomain.getMainName());
                            templateMain.setSremark(templateDomain.getMainRemark());
                        }

                        MsgTemplate msgTemplate = new MsgTemplate();
                        msgTemplate.setId(templateDomain.getTemplateId());
                        msgTemplate.setIisRealtime(templateDomain.getIsRealTime());
                        msgTemplate.setIisEnable(templateDomain.getIsEnable());
                        msgTemplate.setSendtime(templateDomain.getEndTime());
                        msgTemplate.setSstarttime(templateDomain.getStartTime());
                        msgTemplate.setSsupplierId(templateDomain.getSupplierId());
                        msgTemplate.setSsupplierCode(templateDomain.getSupplierCode());
                        msgTemplate.setStemplateContent(templateDomain.getTemplateContent());
                        msgTemplate.setStemplateTitle(templateDomain.getTemplateTitle());
                        msgTemplate.setItimeout(templateDomain.getTimeout());
                        msgTemplate.setIusage(templateDomain.getIusage());
                        msgTemplate.setImsgType(templateDomain.getImsgType());
                        msgTemplate.setSendCountLimit(templateDomain.getCountLimit());
                        msgTemplate.setSendUserCountLimit(templateDomain.getUserCountLimit());
                        templateMain.putMsgTemplate(msgTemplate);
                        cacheTemplate.put(templateDomain.getMainCode(), templateMain);
                        allTemplates.put(msgTemplate.getId(), msgTemplate);
                    }
                    LOGGER.info("load mssage template ok !!");
                } finally {
                    lock.writeLock().unlock();
                }

            }
        });

    }

    @Autowired
    MsgTemplateMainDao msgTemplateMainDao;

    @Override
    public boolean configurationHandler(String keyName, String value) {
        if (keyName.equalsIgnoreCase(ZKMSGTEMPLATEKEY)) {
            try {
                loadTemplate();
                return true;
            } catch (Exception e) {
                LOGGER.error("load mssage template error", e);
            }
        }
        return false;
    }

    @Override
    public TemplateMain getTemplateByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        try {

            lock.readLock().lock();
            if (cacheTemplate == null) {
                return null;
            }

            return cacheTemplate.get(code);


        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public MsgTemplate getMsgTemplateById(String templdateId) {
        if (StringUtils.isBlank(templdateId)) {
            return null;
        }
        try {

            lock.readLock().lock();
            if (allTemplates == null) {
                return null;
            }
            return allTemplates.get(templdateId);


        } finally {
            lock.readLock().unlock();
        }

    }

    /**
     * 根据商户ID
     *
     * @param merchantId
     * @return
     */
    public TemplateMain getTemplateByMerchantId(String merchantId,String templateType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("merchantId", merchantId);
        map.put("templateType", templateType);
        MsgTemplateMain msgTemplateMain = msgTemplateMainDao.selectByMerchantId(map);
        if (null != msgTemplateMain) {
            return this.getTemplateByCode(msgTemplateMain.getScode());
        }
        return null;
    }
}