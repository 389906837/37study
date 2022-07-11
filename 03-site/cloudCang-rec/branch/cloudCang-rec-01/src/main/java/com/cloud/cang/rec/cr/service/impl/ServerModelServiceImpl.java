package com.cloud.cang.rec.cr.service.impl;

import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.dao.ServerModelDao;
import com.cloud.cang.rec.cr.domain.ServerModelDomain;
import com.cloud.cang.rec.cr.service.ServerModelService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.MessageFormat;

@Service
public class ServerModelServiceImpl extends GenericServiceImpl<ServerModel, String> implements
        ServerModelService {

    @Autowired
    ServerModelDao serverModelDao;


    @Override
    public GenericDao<ServerModel, String> getDao() {
        return serverModelDao;
    }

    /**
     * 查询列表
     *
     * @param page
     * @param serverModelDomain
     * @return
     */
    @Override
    public Page<ServerModel> selectPageByDomainWhere(Page<ServerModel> page, ServerModelDomain serverModelDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return serverModelDao.selectPageByDomainWhere(serverModelDomain);
    }

    /**
     * 新增
     *
     * @param serverModel
     * @return
     */
    @Override
    public void saveServerModel(ServerModel serverModel, File file) {
        serverModel.setSfileSize(String.valueOf(file.length() / 1024 * 1024));
        serverModel.setSfileSizeUnit("MB");
        serverModel.setScode(CoreUtils.newCode(EntityTables.CR_SERVER_MODEL));
        serverModel.setIstatus(10);
        serverModel.setIdelFlag(0);
        serverModel.setTaddTime(DateUtils.getCurrentDateTime());
        serverModel.setTupdateTime(DateUtils.getCurrentDateTime());
        serverModel.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModel.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModelDao.insert(serverModel);
        //操作日志
        String logText = MessageFormat.format("新增识别服务模型，模型编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }

    /**
     * 执行修改
     *
     * @param serverModel
     */
    @Override
    public void upServerModel(ServerModel serverModel, File file) {
        serverModel.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModel.setTupdateTime(DateUtils.getCurrentDateTime());
        serverModelDao.updateByIdSelective(serverModel);
        //操作日志
        String logText = MessageFormat.format("修改识别服务模型，模型编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
    }

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                ServerModel serverModel = new ServerModel();
                serverModel.setId(id);
                serverModel.setIdelFlag(1);
                serverModelDao.updateByIdSelective(serverModel);
            }
        }
    }

    /**
     * 审核
     *
     * @param serverModel
     * @return
     */
    @Override
    public void serverModelAudit(ServerModel serverModel) {
        serverModel.setTauditTime(DateUtils.getCurrentDateTime());
        serverModel.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        serverModelDao.updateByIdSelective(serverModel);
    }

    /**
     * 根据模型编号查询
     *
     * @param code
     * @return
     */
    @Override
    public ServerModel selectByCode(String code) {
        return serverModelDao.selectByCode(code);
    }


}