package com.cloud.cang.rec.op.service.impl;

import java.util.List;

import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.hy.MemberConstants;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.op.domain.InterfaceInfoDomain;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.op.dao.InterfaceInfoDao;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.rec.op.service.InterfaceInfoService;

@Service
public class InterfaceInfoServiceImpl extends GenericServiceImpl<InterfaceInfo, String> implements
        InterfaceInfoService {

    @Autowired
    InterfaceInfoDao interfaceInfoDao;


    @Override
    public GenericDao<InterfaceInfo, String> getDao() {
        return interfaceInfoDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param interfaceInfoDomain
     * @return
     */
    @Override
    public Page<InterfaceInfo> selectPageByDomainWhere(Page<InterfaceInfo> page, InterfaceInfoDomain interfaceInfoDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return interfaceInfoDao.selectPageByDomainWhere(interfaceInfoDomain);
    }

    /**
     * 删除平台应用
     *
     * @param checkboxId
     */
    @Override
    public void delete(String[] checkboxId) {
        if (checkboxId != null && checkboxId.length > 0) {
            for (String id : checkboxId) {
                InterfaceInfo interfaceInfo = new InterfaceInfo();
                interfaceInfo.setId(id);
                interfaceInfo.setIdelFlag(1);
                interfaceInfoDao.updateByIdSelective(interfaceInfo);
            }
        }
    }

    /**
     * 添加平台接口
     *
     * @param interfaceInfo
     * @return
     */
    @Override
    public InterfaceInfo saveInterface(InterfaceInfo interfaceInfo) {
        interfaceInfo.setScode(CoreUtils.newCode(EntityTables.OP_INTERFACE_INFO));
        interfaceInfo.setIstatus(10);
        interfaceInfo.setIdelFlag(0);
        interfaceInfo.setAddTime(DateUtils.getCurrentDateTime());
        interfaceInfo.setAddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        interfaceInfo.setUpdateTime(DateUtils.getCurrentDateTime());
        interfaceInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
        interfaceInfoDao.insert(interfaceInfo);
        return interfaceInfo;
    }

    /**
     * 修改平台接口
     *
     * @param interfaceInfo
     */
    @Override
    public void upInterface(InterfaceInfo interfaceInfo) {
       /* if (10 == interfaceInfo.getIpayWay() || 20 == interfaceInfo.getIpayWay()) {
            interfaceInfo.setIpayType(null);
        }*/
        interfaceInfo.setUpdateTime(DateUtils.getCurrentDateTime());
        interfaceInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getUserName());
        interfaceInfoDao.updateByIdSelective(interfaceInfo);
    }
}