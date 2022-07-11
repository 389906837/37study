package com.cloud.cang.rec.op.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.common.utils.IdGenerator;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.rec.common.utils.RSAUtils;
import com.cloud.cang.rec.op.dao.AppManageDao;
import com.cloud.cang.rec.op.domain.AppManageDomain;
import com.cloud.cang.rec.op.service.AppManageService;
import com.cloud.cang.rec.op.vo.AppManageVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AppManageServiceImpl extends GenericServiceImpl<AppManage, String> implements
        AppManageService {

    @Autowired
    AppManageDao appManageDao;
    @Autowired
    ICached iCached;

    @Override
    public GenericDao<AppManage, String> getDao() {
        return appManageDao;
    }

    /**
     * 查询列表
     *
     * @param page
     * @param appManageDomain
     * @return
     */
    @Override
    public Page<AppManageVo> selectPageByDomainWhere(Page<AppManageVo> page, AppManageDomain appManageDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return appManageDao.selectPageByDomainWhere(appManageDomain);
    }

    /**
     * 审核
     *
     * @param appManage
     * @return
     */
    @Override
    public void appManageAudit(AppManage appManage) throws Exception {
        appManage.setTauditTime(DateUtils.getCurrentDateTime());
        appManage.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        if (20 == appManage.getIauditStatus()) {
            appManage.setIstatus(20);
        }
        appManageDao.updateByIdSelective(appManage);
        //审核通过将应用放入缓存
        AppManage temp = appManageDao.selectByPrimaryKey(appManage.getId());
        if (20 == appManage.getIauditStatus()) {
            String catcheKey = RedisConst.OP_APP_MANAGE + temp.getSappId();
            iCached.put(catcheKey, JSON.toJSONString(temp));
        }
    }

    /**
     * 保存新增平台应用
     *
     * @param appManage
     * @return AppManage
     */
    @Override
    public AppManage saveAppManage(AppManage appManage) throws Exception {
        appManage.setSappId(CoreUtils.newCode(EntityTables.OP_APP_MANAGE_SAPP_ID));
        appManage.setSappSecretKey(IdGenerator.randomUUID(16));//平台秘钥
        Map<String, Object> keyMap = RSAUtils.genKeyPair();
        String publicKey = RSAUtils.getPublicKey(keyMap);
        String privateKey = RSAUtils.getPrivateKey(keyMap);
        appManage.setSplatformKey(privateKey);//平台私钥
        appManage.setSplatformPublicKey(publicKey);//平台公钥
        keyMap = RSAUtils.genKeyPair();
        publicKey = RSAUtils.getPublicKey(keyMap);
        privateKey = RSAUtils.getPrivateKey(keyMap);
        appManage.setSappPrivateKey(privateKey);//应用私钥
        appManage.setSappPublicKey(publicKey);//应用公钥
        appManage.setScode(CoreUtils.newCode(EntityTables.OP_APP_MANAGE));
        appManage.setIstatus(10);
        appManage.setIauditStatus(10);
        appManage.setIdelFlag(0);
        appManage.setTcreateTime(DateUtils.getCurrentDateTime());
        appManage.setTaddTime(DateUtils.getCurrentDateTime());
        appManage.setTupdateTime(DateUtils.getCurrentDateTime());
        appManageDao.insert(appManage);
        return appManage;
    }

    /**
     * 保存编辑平台应用
     *
     * @param appManage
     * @return AppManage
     */
    @Override
    public void upAppManage(AppManage appManage) {
        appManage.setTupdateTime(DateUtils.getCurrentDateTime());
        appManageDao.updateByIdSelective(appManage);
    }

    /**
     * 根据Id查看domain
     *
     * @param id
     * @return
     */
    @Override
    public AppManageVo selectVoById(String id) {
        return appManageDao.selectVoById(id);
    }

    /**
     * 删除平台应用管理信息
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                AppManage appManage = new AppManage();
                appManage.setId(id);
                appManage.setIdelFlag(1);
                appManageDao.updateByIdSelective(appManage);
            }
        }
    }
}