package com.cloud.cang.rec.cr.service.impl;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.OpenGpuServer;
import com.cloud.cang.model.cr.OpenServerList;
import com.cloud.cang.rec.cr.dao.OpenGpuServerDao;
import com.cloud.cang.rec.cr.dao.OpenServerListDao;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.cloud.cang.rec.cr.service.OpenServerListService;
import com.cloud.cang.rec.cr.vo.OpenServerListVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpenServerListServiceImpl extends GenericServiceImpl<OpenServerList, String> implements
        OpenServerListService {

    @Autowired
    OpenServerListDao openServerListDao;
    @Autowired
    OpenGpuServerDao openGpuServerDao;

    @Override
    public GenericDao<OpenServerList, String> getDao() {
        return openServerListDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param openServerListDomain
     * @return
     */
    @Override
    public Page<OpenServerList> selectPageByDomainWhere(Page<OpenServerList> page, OpenServerListDomain openServerListDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return openServerListDao.selectPageByDomainWhere(openServerListDomain);
    }


    /**
     * 新增
     *
     * @param openServerList
     * @return
     */
    @Override
    @Transactional
    public OpenServerList saveOpenServer(OpenServerList openServerList) {
        openServerList.setScode(CoreUtils.newCode(EntityTables.CR_OPEN_SERVER_LIST));
        openServerList.setIstatus(10);
        openServerList.setIauditStatus(10);
        openServerList.setIdelFlag(0);
        openServerList.setTaddTime(DateUtils.getCurrentDateTime());
        openServerList.setTupdateTime(DateUtils.getCurrentDateTime());
        openServerList.setTaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        openServerList.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        openServerListDao.insert(openServerList);
       /* if (StringUtils.isNotBlank(sgroupDecList)) {
            String[] arr = sgroupDecList.split(",");
            if (null != arr && arr.length > 0) {
                OpenGpuServer openGpuServer = null;
                for (int a = 0; a < arr.length; a++) {
                    openGpuServer = new OpenGpuServer();
                    openGpuServer.setSgpuId(arr[a]);
                    openGpuServer.setSopenServerId(openServerList.getId());
                    openGpuServerDao.insert(openGpuServer);
                }
            }
        }*/
        return openServerList;

    }


    /**
     * 执行修改
     *
     * @param openServerList
     */
    @Override
    public OpenServerList upOpenServer(OpenServerList openServerList) {
        openServerList.setTupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        openServerList.setTupdateTime(DateUtils.getCurrentDateTime());
        openServerListDao.updateByIdSelective(openServerList);
      /*  if (StringUtils.isNotBlank(sgroupDecList)) {
            String[] arr = sgroupDecList.split(",");
            if (null != arr && arr.length > 0) {
                openGpuServerDao.deleteByOpenServerId(openServerList.getId());
                OpenGpuServer openGpuServer = null;
                for (int a = 0; a < arr.length; a++) {
                    openGpuServer = new OpenGpuServer();
                    openGpuServer.setSgpuId(arr[a]);
                    openGpuServer.setSopenServerId(openServerList.getId());
                    openGpuServerDao.insert(openGpuServer);
                }
            }
        }*/
        return openServerList;
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
                OpenServerList openServerList = new OpenServerList();
                openServerList.setId(id);
                openServerList.setIdelFlag(1);
                openServerListDao.updateByIdSelective(openServerList);
            }
        }
    }

    /**
     * 审核
     *
     * @param openServerList
     * @return
     */
    @Override
    public void openServerListAudit(OpenServerList openServerList) {
        if (30 == openServerList.getIauditStatus()) {
            openServerList.setIstatus(60);
        } else {
            openServerList.setIstatus(50);
        }
        openServerList.setTauditTime(DateUtils.getCurrentDateTime());
        openServerList.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        openServerListDao.updateByIdSelective(openServerList);
    }

    /**
     * 进入编辑页面查询API服务和GPU服务关联
     *
     * @param id
     * @return
     */
    @Override
    public OpenServerListVo selectOpenGpuServer(String id) {
        return openServerListDao.selectOpenGpuServer(id);
    }
}